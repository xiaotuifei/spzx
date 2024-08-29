package com.tuixiaofei.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuixiaofei.spzx.manager.mapper.SysRoleMapper;
import com.tuixiaofei.spzx.manager.service.SysRoleService;
import com.tuixiaofei.spzx.model.dto.system.SysRoleDto;
import com.tuixiaofei.spzx.model.entity.system.SysRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    SysRoleMapper sysRoleMapper;
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {

        // 设置分页的相关参数
        PageHelper.startPage(current, limit);
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);

        return new PageInfo<>(list);

    }
}
