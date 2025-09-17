package org.example.shortlink.project.common.constant;

/**
 * 短链接Redis缓存常量
 */
public class RedisKeyConstant {

    /**
     * 短链接跳转缓存key
     */
    public static final String GOTO_SHORT_LINK_KEY = "short_link_goto_%s";

    /**
     * 短链接跳转缓存锁key
     */
    public static final String LOCK_GOTO_SHORT_LINK_KEY = "short_link_lock_goto_%s";
}
