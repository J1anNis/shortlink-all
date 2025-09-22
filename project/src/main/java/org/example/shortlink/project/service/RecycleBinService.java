package org.example.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.shortlink.project.dao.entity.ShortLinkDO;
import org.example.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import org.example.shortlink.project.dto.req.ShortLinkPageReqDTO;
import org.example.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 *
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存到回收站
     * @param requestParam 保存回收站请求参数
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);

    /**
     * 分页查询回收站短链接
     * @param requestParam
     * @return
     */
    IPage<ShortLinkPageRespDTO> pageRecycleBinShortLink(ShortLinkPageReqDTO requestParam);
}
