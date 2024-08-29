package com.tuixiaofei.spzx.manager.mapper;

import com.tuixiaofei.spzx.model.dto.system.SysRoleDto;
import com.tuixiaofei.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);
}
