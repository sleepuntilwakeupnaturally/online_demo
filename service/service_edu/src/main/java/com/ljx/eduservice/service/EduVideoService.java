package com.ljx.eduservice.service;

import com.ljx.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
public interface EduVideoService extends IService<EduVideo> {

    // 根据课程id删除小节
    void deleteVideoByCourseId(String courseId);
}
