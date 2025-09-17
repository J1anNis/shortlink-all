package org.example.shortlink.project.toolkit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import java.util.Date;
import java.util.Optional;

import static org.example.shortlink.project.common.constant.ShortLinkConstant.DEFAULT_LINK_CACHE_VALID_DATE;

public class LinkUtil {

    /**
     * 计算链接缓存有效期
     * @param validDate
     * @return
     */
    public static long getLinkCacheValidTime(Date validDate) {
        return Optional.ofNullable(validDate)
                .map(each -> DateUtil.between(new Date(), each, DateUnit.MS))
                .orElse(DEFAULT_LINK_CACHE_VALID_DATE);
    }
    /**
     * 第一步：Optional.ofNullable(validDate)
     * 把validDate包起来，方便安全处理 “可能为 null” 的情况（避免直接操作null导致报错）。
     * 第二步：map(...)
     * 若validDate不为 null，就用DateUtil.between计算：
     * 当前时间（new Date()）到validDate（过期时间）之间的毫秒差（即 “还剩多少毫秒过期”），这个值就是缓存的有效时间（缓存应和短链接同时过期）。
     * 第三步：orElse(...)
     * 若validDate为 null（即短链接没有设置过期时间），就直接返回DEFAULT_LINK_CACHE_VALID_DATE（一个提前定义好的默认缓存时间，比如 24 小时的毫秒数）。
     */
}
