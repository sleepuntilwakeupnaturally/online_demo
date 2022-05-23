package com.ljx.eduservice.service;

import com.ljx.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljx.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoById(String courseId);

    // 删除章节
    boolean deleteChapter(String chapterId);

    // 根据课程id删章节
    void deleteChapterByCourseId(String courseId);

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

}
