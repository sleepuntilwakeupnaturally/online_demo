package com.ljx.cmsservice.controller;

import com.ljx.cmsservice.entity.CrmBanner;
import com.ljx.cmsservice.service.CrmBannerService;
import com.ljx.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/bannerfront")
@Api(description = "网站首页Banner列表")
//@CrossOrigin //跨域
public class BannerApiController {

    @Autowired
    private CrmBannerService bannerService;
    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R index() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("bannerList", list);
    }

}
