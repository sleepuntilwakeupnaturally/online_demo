package com.ljx.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljx.eduservice.entity.frontvo.CourseFrontVo;
import com.ljx.eduservice.entity.frontvo.CourseWebVo;
import com.ljx.eduservice.entity.vo.CourseInfoVo;
import com.ljx.eduservice.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
public interface EduCourseService extends IService<EduCourse> {

    // 添加课程基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    // 查询所有课程（展示）
    CoursePublishVo publishCourseInfo(String id);

    // 删除课程（全部删除  根据课程id）
    void deleteCourseById(String courseId);

    //查询前8条热门课程
    List<EduCourse> listCourse();


    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);

}
