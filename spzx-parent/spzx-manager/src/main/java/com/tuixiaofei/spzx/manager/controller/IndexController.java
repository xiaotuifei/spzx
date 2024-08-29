package com.tuixiaofei.spzx.manager.controller;

import com.tuixiaofei.spzx.manager.service.SysUserService;
import com.tuixiaofei.spzx.model.dto.system.LoginDto;
import com.tuixiaofei.spzx.model.vo.common.Result;
import com.tuixiaofei.spzx.model.vo.common.ResultCodeEnum;
import com.tuixiaofei.spzx.model.vo.system.LoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Resource
    private SysUserService sysUserService;

    @Operation(summary = "登录的方法")
    @PostMapping("login")
    public Result login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
}
