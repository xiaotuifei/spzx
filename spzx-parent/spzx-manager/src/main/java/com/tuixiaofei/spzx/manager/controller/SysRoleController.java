package com.tuixiaofei.spzx.manager.controller;


import com.github.pagehelper.PageInfo;
import com.tuixiaofei.spzx.manager.service.impl.SysRoleServiceImpl;
import com.tuixiaofei.spzx.model.dto.system.SysRoleDto;
import com.tuixiaofei.spzx.model.entity.system.SysRole;
import com.tuixiaofei.spzx.model.vo.common.Result;
import com.tuixiaofei.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Resource
    SysRoleServiceImpl sysRoleService;

    @PostMapping("findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto) {
        // pageHelper
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
