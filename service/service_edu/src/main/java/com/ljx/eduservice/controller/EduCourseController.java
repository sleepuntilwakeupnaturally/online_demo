package com.ljx.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.commonutils.R;
import com.ljx.eduservice.entity.EduCourse;
import com.ljx.eduservice.entity.EduTeacher;
import com.ljx.eduservice.entity.vo.CourseInfoVo;
import com.ljx.eduservice.entity.vo.CoursePublishVo;
import com.ljx.eduservice.entity.vo.CourseQuery;
import com.ljx.eduservice.entity.vo.TeacherQuery;
import com.ljx.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
@RestController
@RequestMapping("/eduservice/course")

//@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    // 课程列表接口
    @GetMapping("getCourseList")
    public R getCourseList() {
        List<EduCourse> courseList = courseService.list(null);

        return R.ok().data("courseList", courseList);
    }

    //分页查询
    @GetMapping("pageCourse/{current}/{limit}")
    /**
     * current : 当前页
     * limit : 每页记录数
     */
    public R pageCourse(@PathVariable("current") long current,
                         @PathVariable("limit") long limit){

        //创建page对象
        Page<EduCourse> page = new Page<>(current,limit);
        //调用方法实现分页
        courseService.page(page, null);

        //总记录数
        long total = page.getTotal();
        // 数据list集合
        List<EduCourse> records = page.getRecords();

        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);

        return R.ok().data(map);
    }

    // 条件查询带分页
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable("current") long current,
                                 @PathVariable("limit") long limit,
                                 // @RequestBody 使用json传递数据，把json数据传递到对应对象 required = false表示参数可以为空
                                 @RequestBody(required = false) CourseQuery courseQuery){
        //创建page对象
        Page<EduCourse> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // 判断条件值是否为空， 不为空拼接条件
        String title = courseQuery.getTitle();

        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            // 构建条件
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            // 构建条件
            wrapper.eq("status", status);
        }

        // 排序
        wrapper.orderByDesc("gmt_create");
        // 调用方法实现条件查询分页
        courseService.page(page, wrapper);
        //总记录数
        long total = page.getTotal();
        // 数据list集合
        List<EduCourse> records = page.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);

    }

    // 删除课程（全部删除  根据课程id）
    @DeleteMapping("deleteCourseById/{courseId}")
    public R deleteCourseById(@PathVariable("courseId") String courseId) {
        courseService.deleteCourseById(courseId);
        return R.ok();

    }


    // 添加课程基本信息
    @PostMapping("addCourseInfo")
    private R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        // 返回添加之后的课程id, 为了之后添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    // 根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);

        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // 修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    // 根据课程id查询最终课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable("id") String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("coursePublishVo", coursePublishVo);
    }

    // 课程最终发布 修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable("id") String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }


}

