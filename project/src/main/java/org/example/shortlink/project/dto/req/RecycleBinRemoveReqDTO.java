package org.example.shortlink.project.dto.req;

import lombok.Data;

/**
 * 回收站恢复请求参数
 */
@Data
public class RecycleBinRemoveReqDTO {

    /**
     * 分组标识
     */
    private String gid;


    /**
     * 短链接完整URL
     */
    private String fullShortUrl;
}
