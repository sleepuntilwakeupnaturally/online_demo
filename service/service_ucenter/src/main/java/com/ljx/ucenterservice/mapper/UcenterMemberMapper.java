package com.ljx.ucenterservice.mapper;

import com.ljx.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-04-21
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
