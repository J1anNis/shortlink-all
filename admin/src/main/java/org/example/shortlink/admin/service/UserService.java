package org.example.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.shortlink.admin.dao.entity.UserDO;
import org.example.shortlink.admin.dto.req.UserLoginReqDTO;
import org.example.shortlink.admin.dto.req.UserRegisterReqDTO;
import org.example.shortlink.admin.dto.req.UserUpdateReqDTO;
import org.example.shortlink.admin.dto.resp.UserLoginRespDTO;
import org.example.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return 存在返回True，不存在返回False
     */
    Boolean hasUsername(String username);

    /**
     * 注册用户
     * @param requestParam 注册用户请求参数
     */
    void register(UserRegisterReqDTO requestParam);

    /**
     * 根据用户名更新用户信息
     * @param requestParam 更新用户请求参数
     */
    void update(UserUpdateReqDTO requestParam);

    /**
     * 用户登录
     * @param requestParam 登录用户请求参数
     * @return Token 用户登录返回参数
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 检查用户是否登录
     * @param token 登录凭证
     * @return 是否登录
     */
    Boolean checkLogin(String username, String token);

    /**
     * 用户退出登录
     * @param username
     * @param token
     * @return
     */
    void logout(String username, String token);
}
