package com.ljx.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.commonutils.R;
import com.ljx.eduservice.entity.EduTeacher;
import com.ljx.eduservice.entity.vo.TeacherQuery;
import com.ljx.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-02
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
// 解决跨域问题
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //查询讲师所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findall")
    public R findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //逻辑删除讲师
    //swagger中文说明
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable("id") String id){
        boolean b = teacherService.removeById(id);

        if (b){
            return R.ok();
        } else {
            return R.error();
        }

    }

    //分页查询
    @GetMapping("pageTeacher/{current}/{limit}")
    /**
     * current : 当前页
     * limit : 每页记录数
     */
    public R pageTeacher(@PathVariable("current") long current,
                         @PathVariable("limit") long limit){

        //创建page对象
        Page<EduTeacher> page = new Page<>(current,limit);
        //调用方法实现分页
        teacherService.page(page, null);

        //总记录数
        long total = page.getTotal();
        // 数据list集合
        List<EduTeacher> records = page.getRecords();

        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);

        return R.ok().data(map);
    }

    // 条件查询带分页
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  // @RequestBody 使用json传递数据，把json数据传递到对应对象 required = false表示参数可以为空
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // 判断条件值是否为空， 不为空拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            // 构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            // 构建条件
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            // 构建条件
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            // 构建条件
            wrapper.le("gmt_create", end);
        }

        // 排序
        wrapper.orderByDesc("gmt_create");
        // 调用方法实现条件查询分页
        teacherService.page(page, wrapper);
        //总记录数
        long total = page.getTotal();
        // 数据list集合
        List<EduTeacher> records = page.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);

        return R.ok().data(map);

    }

    // 添加讲师接口
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据讲师id查询讲师信息
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean update = teacherService.updateById(eduTeacher);
        if (update){
            return R.ok();
        } else  {
            return R.error();
        }
    }


}

