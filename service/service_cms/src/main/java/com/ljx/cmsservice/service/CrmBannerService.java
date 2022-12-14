package com.ljx.cmsservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-14
 */
public interface CrmBannerService extends IService<CrmBanner> {


    CrmBanner getBannerById(String id);

    void saveBanner(CrmBanner banner);

    void updateBannerById(CrmBanner banner);

    void removeBannerById(String id);

    List<CrmBanner> selectAllBanner();
}
