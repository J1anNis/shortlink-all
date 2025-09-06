package org.example.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 短链接分组保存请求参数
 */
@Data
public class ShortLinkGroupSaveReqDTO {

    /**
     * 分组名称
     */
    private String name;
}
