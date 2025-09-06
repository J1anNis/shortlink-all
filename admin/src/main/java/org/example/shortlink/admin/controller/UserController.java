package org.example.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.example.shortlink.admin.common.convention.result.Result;
import org.example.shortlink.admin.common.convention.result.Results;
import org.example.shortlink.admin.dto.req.UserRegisterReqDTO;
import org.example.shortlink.admin.dto.resp.UserActualRespDTO;
import org.example.shortlink.admin.dto.resp.UserRespDTO;
import org.example.shortlink.admin.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理层
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据用户名查询用户信息
     */
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        UserRespDTO result = userService.getUserByUsername(username);
        return Results.success(result);
    }

    @GetMapping("/api/short-link/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username){
        UserActualRespDTO result = BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDTO.class);
        return Results.success(result);
    }
    /**
     * toBean 方法用于将 UserRespDTO 转换为 UserActualRespDTO
     * 并且将 UserRespDTO 中的属性复制到 UserActualRespDTO 中
     * 输出的 UserActualRespDTO 中包含了 UserRespDTO 中的所有属性
     * 会有不同
     */

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return 存在返回True，不存在返回False
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 注册用户
     * @param requestParam 注册用户请求参数
     */
    @PostMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam){
        userService.register(requestParam);
        return Results.success();
    }
}
