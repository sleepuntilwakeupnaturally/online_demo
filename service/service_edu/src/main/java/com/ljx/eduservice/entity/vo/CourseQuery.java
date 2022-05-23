package com.ljx.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "Coursse查询对象", description = "课程查询对象封装")
@Data
public class CourseQuery {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "状态 Normal以发布 Draft未发布")
    private String status;

}
