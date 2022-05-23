package com.ljx.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljx.commonutils.R;
import com.ljx.eduservice.entity.EduCourse;
import com.ljx.eduservice.entity.EduTeacher;
import com.ljx.eduservice.service.EduCourseService;
import com.ljx.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public R index() {
        //查询前8条热门课程
        List<EduCourse> eduList = courseService.listCourse();
        //查询前4条名师
        List<EduTeacher> teacherList = teacherService.listTeacher();
        System.err.println(teacherList);
        return R.ok().data("eduList", eduList).data("teacherList", teacherList);
    }
}
