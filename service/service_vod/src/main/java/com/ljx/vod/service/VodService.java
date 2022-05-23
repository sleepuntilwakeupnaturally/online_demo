package com.ljx.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    // 上传视频方法
    String uploadVideoAly(MultipartFile file);

    // 删除多个视频
    void removeMoreAlyVideo(List<String> videoIdList);
}
