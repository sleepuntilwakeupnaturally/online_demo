package com.ljx.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljx.commonutils.R;
import com.ljx.eduservice.entity.EduChapter;
import com.ljx.eduservice.entity.chapter.ChapterVo;
import com.ljx.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
@RestController
@RequestMapping("/eduservice/chapter")

//@CrossOrigin
public class EduChapterController {


    @Autowired
    private EduChapterService chapterService;

    // 根据courseId
    @GetMapping("getChapterVideo/{courseId}")
    public R geoChapterVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoById(courseId);

        return R.ok().data("allChapterVideo", list);
    }

    // 添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    // 根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable("chapterId") String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    // 修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    // 删除
    @DeleteMapping("deleteChapterId/{chapterId}")
    public R deleteChapterId(@PathVariable("chapterId") String chapterId){

        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }

}

