package org.example.shortlink.admin.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.example.shortlink.admin.common.serialize.PhoneDesensitizationSerializer;

/**
 * 用户返回参数响应
 *
 * DTO是一种用于在不同层之间传输数据的容器，通常用于将数据库实体类转换为前端需要的格式。
 * 这里的UserRespDTO就是将UserDO转换为前端需要的格式。
 * 作用是避免将敏感信息暴露给前端。能够按需封装字段
 */
@Data
public class UserRespDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String real_name;

    /**
     * 手机号
     * 在返回给前端时，进行才会脱敏处理，用于复制处理不会脱敏
     */
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;

    /**
     * 邮箱
     */
    private String mail;


}
