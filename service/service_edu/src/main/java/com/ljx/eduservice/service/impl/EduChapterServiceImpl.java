package com.ljx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljx.eduservice.entity.EduSubject;
import com.ljx.eduservice.entity.chapter.VideoVo;
import com.ljx.eduservice.entity.subject.TwoSubject;
import com.ljx.servicebase.ex.GuliException;
import org.springframework.beans.BeanUtils;
import com.ljx.eduservice.entity.EduChapter;
import com.ljx.eduservice.entity.EduVideo;
import com.ljx.eduservice.entity.chapter.ChapterVo;
import com.ljx.eduservice.mapper.EduChapterMapper;
import com.ljx.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.eduservice.service.EduVideoService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;


    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {
        // 根据id查询所有章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper();
        wrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);

        // 根据id查询所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideos = videoService.list(wrapperVideo);

        // 遍历  封装
        // 创建最终集合 用于封装
        List<ChapterVo> ChapterVOList = new ArrayList<>();
        for (int i = 0; i < eduChapters.size(); i++) {
            // 每个章节
            EduChapter eduChapter = eduChapters.get(i);
            // eduChapter复制到ChapterVo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            // 将chapterVo放入list
            ChapterVOList.add(chapterVo);

            // 小节
            // 创建小节集合封装每一个小节
            List<VideoVo> VideoVoList = new ArrayList<>();
            // 遍历小节集合
            for (int m = 0; m < eduVideos.size(); m++) {
                // 获取每一个小节
                EduVideo eduVideo = eduVideos.get(m);

                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    // 把eduVideo值复制到videoVo里面，放到twoFinalSubjectList里面
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    VideoVoList.add(videoVo);
                }
            }
            // 把章节下面分小节放到小节里面
            chapterVo.setChildren(VideoVoList);
        }

        return ChapterVOList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据chapterId查询小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);

        System.out.println(count);

        if (count > 0){
            throw new GuliException(20001, "下有小节，不能删除");
        } else {
            int result = baseMapper.deleteById(chapterId);

            return result > 0;
        }
    }

    // 根据课程id删章节
    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }

    //课程大纲列表,根据课程id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1 根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2 根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断：小节里面chapterid和章节里面id是否一样
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到小节封装集合
                    videoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }
}
