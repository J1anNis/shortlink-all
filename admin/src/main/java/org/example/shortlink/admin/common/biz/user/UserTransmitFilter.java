package org.example.shortlink.admin.common.biz.user;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.shortlink.admin.common.convention.exception.ClientException;
import org.example.shortlink.admin.common.convention.result.Results;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

import static org.example.shortlink.admin.common.enums.UserErrorCodeEnum.USER_TOKEN_FAIL;

/**
 * 用户信息传输过滤器
 *
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 忽略列表
     * 登录接口和检查用户名不需要token验证
     */
    private static final List<String> IGNORE_URI = Lists.newArrayList(
            "/api/short-link/admin/v1/user/login", // 登录接口
            "/api/short-link/admin/v1/user/has-username" // 检查用户名是否存在接口
            // 用户注册接口也不用，但是由于与修改是一个接口，需要下面方法进一步判断
    );

    /**
     * 过滤用户信息
     * 在请求进入业务逻辑前，通过请求头的身份标识从 Redis 加载用户信息并存入上下文。
     * 业务代码可直接从上下文获取用户信息，简化开发。
     * 请求结束后清理上下文，保证线程安全。
     */
    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();

        // 若请求路径不在忽略列表中
        // 则进行需要Token验证
        // 注册也不需要token，但是由于与修改用户名使用同一个接口，因此需要进一步判断是否为POST方法
        if(!IGNORE_URI.contains(requestURI)) {
            String method = httpServletRequest.getMethod();
            if(!(Objects.equals(requestURI, "/api/short-link/admin/v1/user") && Objects.equals(method, "POST"))) {
                String username = httpServletRequest.getHeader("username");
                String token = httpServletRequest.getHeader("token");
                // 这一步还没有到DispatcherServlet，因此全局异常拦截器失效

                // 判断用户名和token是否为空
                // 若为空，则抛出用户token异常
                if(!StrUtil.isAllNotBlank(username, token)) {
                    returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(Results.failure(new ClientException(USER_TOKEN_FAIL))));
                    return;
                }

                // 进一步判断用户名与token是否存在于Redis
                Object userInfoJsonStr;
                try {
                    userInfoJsonStr = stringRedisTemplate.opsForHash().get("login_" + username, token);
                    if(!(userInfoJsonStr != null)) {
                        throw new ClientException(USER_TOKEN_FAIL);
                    }
                } catch (Exception e) {
                    returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(Results.failure(new ClientException(USER_TOKEN_FAIL))));
                    return;
                }

                /**
                 * userInfoJsonStr：从 Redis 中查询到的用户信息（是一个 Object 类型，实际存储的是 JSON 格式的字符串，比如 {"userId": "123", "username": "admin", "realName": "张三"}）。
                 * userInfoJsonStr.toString()：将 Redis 中获取的 Object 转为字符串（因为 JSON.parseObject 需要字符串参数）。
                 * JSON.parseObject(..., UserInfoDTO.class)：借助 FastJSON 工具，将 JSON 字符串转换为 UserInfoDTO 类型的对象（自动映射 JSON 中的键到 UserInfoDTO 的属性，如 userId 对应 UserInfoDTO 的 userId 字段）。
                 */
                UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDTO.class);
                UserContext.setUser(userInfoDTO);

            }
        }


        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }

    /**
     * 向客户端返回JSON格式的数据
     * @param response
     * @param json
     * @throws Exception
     */
    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}