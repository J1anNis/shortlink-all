package org.example.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.shortlink.admin.common.convention.result.Result;
import org.example.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import org.example.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.stereotype.Service;

/**
 * URL 回收站接口
 */

public interface RecycleBinService {

    /**
     * 分页查询回收站短链接
     * @param requestParam
     * @return
     */
    Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam);
}
