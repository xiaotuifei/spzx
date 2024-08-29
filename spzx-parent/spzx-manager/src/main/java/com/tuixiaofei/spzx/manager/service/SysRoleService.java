package com.tuixiaofei.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.tuixiaofei.spzx.model.dto.system.SysRoleDto;
import com.tuixiaofei.spzx.model.entity.system.SysRole;

public interface SysRoleService {

    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);
}
