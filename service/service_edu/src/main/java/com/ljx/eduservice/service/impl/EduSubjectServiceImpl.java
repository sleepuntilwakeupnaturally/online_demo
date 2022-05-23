package com.ljx.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljx.eduservice.entity.EduSubject;
import com.ljx.eduservice.entity.excel.SubjectData;
import com.ljx.eduservice.entity.subject.OneSubject;
import com.ljx.eduservice.entity.subject.TwoSubject;
import com.ljx.eduservice.listener.SubjectExcelListener;
import com.ljx.eduservice.mapper.EduSubjectMapper;
import com.ljx.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {

        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e){

        }
    }

    @Override
    public List<OneSubject> getAllOneAndTwoSubject() {
        // 查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        // 查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创建集合，用于最终封装的数据
        List<OneSubject> finalSubject = new ArrayList<>();

        // 一级封装
        for (int i = 0; i < oneSubjectList.size(); i++) {//遍历
            // 获取onesubject对象
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            // eduSubject值复制到oneSubject （对应值）
            BeanUtils.copyProperties(eduSubject, oneSubject);

            finalSubject.add(oneSubject);

            // 二级分类
            // 创建二级分类集合封装每一个二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            // 遍历二级分类集合
            for (int m = 0; m < twoSubjectList.size(); m++) {
                // 获取每一个二级分类
                EduSubject tSubject = twoSubjectList.get(m);

                if (tSubject.getParentId().equals(eduSubject.getId())){
                    // 把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }

            // 把一级下面分二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);




        }





        return finalSubject;
    }
}
