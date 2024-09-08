package com.tuixiaofei.spzx.manager.mapper;

import com.tuixiaofei.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper {

    SysUser selectUserInfoByUserName(@Param("userName") String userName);
}
