package com.ljx.eduservice.service;

import com.ljx.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljx.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    // 课程分类列表（树形）
    List<OneSubject> getAllOneAndTwoSubject();
}
