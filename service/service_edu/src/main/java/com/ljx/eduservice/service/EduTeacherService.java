package com.ljx.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-02
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //查询前4条名师
    List<EduTeacher> listTeacher();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
