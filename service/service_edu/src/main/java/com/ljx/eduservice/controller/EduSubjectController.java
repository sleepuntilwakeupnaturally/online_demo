package com.ljx.eduservice.controller;


import com.ljx.commonutils.R;
import com.ljx.eduservice.entity.subject.OneSubject;
import com.ljx.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 添加课程分类
    // 获取上传过来的文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        // 上传过来的excel文件
        subjectService.saveSubject(file, subjectService);

        return R.ok();
    }

    // 课程分类列表功能
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        // list集合中泛型是一级分类 一级分类里有二级分类
        List<OneSubject> list = subjectService.getAllOneAndTwoSubject();
        return R.ok().data("list", list);
    }

}

