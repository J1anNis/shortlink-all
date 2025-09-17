package org.example.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.shortlink.project.dao.entity.ShortLinkDO;
import org.example.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import org.example.shortlink.project.dto.req.ShortLinkPageReqDTO;
import org.example.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import org.example.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import org.example.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.example.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 短链接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     * 创建短链接
     * @param requestParam 短链接创建请求参数
     * @return 短链接创建响应参数
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam);

    /**
     * 分页查询短链接
     * @param requestParam 短链接分页查询请求参数
     * @return 短链接分页查询响应参数
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(@RequestBody ShortLinkPageReqDTO requestParam);

    /**
     * 短链接分组内数量统计
     * @param requestParam 短链接分组内数量统计请求参数
     * @return 短链接分组内数量统计响应参数
     */
    List<ShortLinkGroupCountQueryRespDTO> groupShortLinkCount(List<String> requestParam);

    /**
     * 修改短链接
     * @param requestParam 短链接修改请求参数
     */
    void updateShortLink(ShortLinkUpdateReqDTO requestParam);
}
