package com.ljx.cmsservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.cmsservice.entity.CrmBanner;
import com.ljx.cmsservice.mapper.CrmBannerMapper;
import com.ljx.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-14
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public CrmBanner getBannerById(String id) {
        CrmBanner crmBanner = baseMapper.selectById(id);
        return crmBanner;
    }

    @Override
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @Override
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @Override
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        List<CrmBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }
}
