package org.example.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.shortlink.project.dao.entity.ShortLinkDO;
import org.example.shortlink.project.dto.req.RecycleBinSaveReqDTO;

/**
 *
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存到回收站
     * @param requestParam 保存回收站请求参数
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);
}
