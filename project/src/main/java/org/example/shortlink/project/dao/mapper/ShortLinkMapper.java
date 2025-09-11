package org.example.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.shortlink.project.dao.entity.ShortLinkDO;

/**
 * 短链接持久层
 * 提供短链接相关数据库操作
 * 包括短链接的创建、查询、更新、删除等操作
 * 与短链接相关的数据库操作都在该接口中定义
 */

public interface ShortLinkMapper extends BaseMapper<ShortLinkDO> {
}
