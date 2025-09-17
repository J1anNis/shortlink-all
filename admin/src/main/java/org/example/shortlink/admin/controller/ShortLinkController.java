package org.example.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.shortlink.admin.common.convention.result.Result;
import org.example.shortlink.admin.common.convention.result.Results;
import org.example.shortlink.admin.remote.ShortLinkRemoteService;
import org.example.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import org.example.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import org.example.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import org.example.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import org.example.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.*;


/**
 * 短链接后管控制层
 */
@RestController
public class ShortLinkController {

    /**
     * 后续重做为SpringCloud Feign调用
     */
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 创建短链接
     * @param requestParam 创建短链接请求参数
     * @return 创建短链接返回参数
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam){
        return shortLinkRemoteService.createShortLink(requestParam);
    }

    /**
     * 分页查询短链接
     * @param requestParam 分页查询短链接请求参数
     * @return 分页查询短链接返回参数
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        return shortLinkRemoteService.pageShortLink(requestParam);
    }

    /**
     * 修改短链接
     * @param requestParam 短链接修改请求参数
     * @return 短链接修改响应参数
     */
    @PutMapping("/api/short-link/admin/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkRemoteService.updateShortLink(requestParam);
        return Results.success();
    }
}
