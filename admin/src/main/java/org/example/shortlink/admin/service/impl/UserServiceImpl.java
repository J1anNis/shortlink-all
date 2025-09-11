package org.example.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.shortlink.admin.common.convention.exception.ClientException;
import org.example.shortlink.admin.common.enums.UserErrorCodeEnum;
import org.example.shortlink.admin.dao.entity.UserDO;
import org.example.shortlink.admin.dao.mapper.UserMapper;
import org.example.shortlink.admin.dto.req.UserLoginReqDTO;
import org.example.shortlink.admin.dto.req.UserRegisterReqDTO;
import org.example.shortlink.admin.dto.req.UserUpdateReqDTO;
import org.example.shortlink.admin.dto.resp.UserLoginRespDTO;
import org.example.shortlink.admin.dto.resp.UserRespDTO;
import org.example.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.example.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static org.example.shortlink.admin.common.enums.UserErrorCodeEnum.USER_NAME_EXIST;
import static org.example.shortlink.admin.common.enums.UserErrorCodeEnum.USER_SAVE_ERROR;
import static org.example.shortlink.admin.common.enums.UserErrorCodeEnum.USER_EXIST;

/**
 * 用户接口实现层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 根据用户名查询用户信息方法主体
     * @param username 用户名
     * @return
     */
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username); // queryWrapper 内部存储的是 查询条件的结构化元数据，通过下一步selectOne能够取出符合条件的一条数据
        // baseMapper 就是 UserMapper 的实例
        UserDO userDO = baseMapper.selectOne(queryWrapper); // baseMapper 是 MyBatis-Plus 提供的基础 Mapper 接口，用于执行数据库操作。
        if(userDO == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }
        // 创建数据库实体对象，将结果复制进去，UserRespDTO中包含加密字段
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return 存在返回True，不存在返回False
     */
    @Override
    public Boolean hasUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }


    /**
     * 注册用户
     * @param requestParam 注册用户请求参数
     */
    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if(!hasUsername(requestParam.getUsername())) {
            throw new ClientException(USER_NAME_EXIST);
        }
        // 分布式锁，防止大量请求同时注册同一个用户名导致数据库压力过大
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try {
            if (lock.tryLock()) {
                // insert是用于向数据库插入数据的方法
                // 如果失败会返回 0
                // 如果成功会返回 1
                try {
                    int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                    if (inserted < 1) { // 插入失败
                        throw new ClientException(USER_SAVE_ERROR);
                    }
                } catch (DuplicateKeyException ex) { // 捕获数据库唯一约束异常
                    throw new ClientException(USER_EXIST);
                }

                // 注册成功后，将用户名添加到布隆过滤器中
                // 布隆过滤器的 add 方法用于添加元素
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException(USER_NAME_EXIST); // 未获取到锁：抛出“用户名已存在”
        } finally {
            lock.unlock();
        }
    }

    /**
     * 根据用户名更新用户信息
     * @param requestParam 更新用户请求参数
     */
    @Override
    public void update(UserUpdateReqDTO requestParam) {
        // TODO 验证当前用户名是否为登录用户
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class), updateWrapper);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if(userDO == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }

        String uuid = UUID.randomUUID().toString(); // 由算法生成的唯一字符串，作为登录凭证Token

        // 将登录token与json串形式的用户信息存储到Redis中，并设置30分钟过期时间
        stringRedisTemplate.opsForHash().put("login_" + requestParam.getUsername(), uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire("login_" + requestParam.getUsername(), 30L, TimeUnit.DAYS);
        // 将登录凭证Token返回给客户端
        return new UserLoginRespDTO(uuid);

    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get("login_" + username, token) != null;
    }

    @Override
    public void logout(String username, String token) {
        if(checkLogin(username, token)) {
            stringRedisTemplate.delete("login_" + username);
            return;
        }
        throw new ClientException(UserErrorCodeEnum.USER_LOGOUT_ERROR);
    }
}
