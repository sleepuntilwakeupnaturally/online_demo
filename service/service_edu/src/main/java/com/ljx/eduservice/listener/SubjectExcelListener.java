package com.ljx.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljx.eduservice.entity.EduSubject;
import com.ljx.eduservice.entity.excel.SubjectData;
import com.ljx.eduservice.service.EduSubjectService;
import com.ljx.servicebase.ex.GuliException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // 因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    // 不能实现数据库操作
    public EduSubjectService subjectService;

    // 读取excel内容。一行一行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new GuliException(20001, "文件数据为空");
        }
        // 一行一行读取， 两个值：1 一级分类  2 二级分类
        // 判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectData.getOneSubjectName(), subjectService);
        // 没有相同的一级分类 添加
        if (existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            // 一级分类名称
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        // 添加二级分类
        // 判断二级分类是否重复
        // 获取一级分类id值
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), subjectService, pid);
        // 没有相同的二级分类 添加
        if (existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            // 二级分类名称
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }

    }

    // 判断一级分类不能重复添加
    private EduSubject existOneSubject(String name, EduSubjectService subjectService){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }

    // 判断二级分类不能重复添加
    private EduSubject existTwoSubject(String name, EduSubjectService subjectService, String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
