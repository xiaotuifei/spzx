package com.tuixiaofei.spzx.manager.controller;

import com.tuixiaofei.spzx.AuthContextUtil;
import com.tuixiaofei.spzx.manager.service.SysUserService;
import com.tuixiaofei.spzx.manager.service.ValidateCodeService;
import com.tuixiaofei.spzx.manager.service.impl.ValidateCodeServiceImpl;
import com.tuixiaofei.spzx.model.dto.system.LoginDto;
import com.tuixiaofei.spzx.model.entity.system.SysUser;
import com.tuixiaofei.spzx.model.vo.common.Result;
import com.tuixiaofei.spzx.model.vo.common.ResultCodeEnum;
import com.tuixiaofei.spzx.model.vo.system.LoginVo;
import com.tuixiaofei.spzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private ValidateCodeService validateCodeService;

    @Operation(summary = "登录的方法")
    @PostMapping("login")
    public Result login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    // 生成图片的验证码
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    // 获取当前登录的用户信息
//    @GetMapping(value = "/getUserInfo")
//    private Result getUserInfo(@RequestHeader(name = "token") String token) {
//        // 1、从请求头里获取到token
//        // 2、根据token查询redis获取用户信息
//        SysUser sysUser = sysUserService.getUserInfo(token);
//
//        // 3、返回用户信息
//        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
//
//    }

    @GetMapping(value = "/getUserInfo")
    private Result getUserInfo() {
        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);

    }

    // 用户退出
    @GetMapping(value = "/logout")
    private Result logout(@RequestHeader(name = "token") String token) {
        // 1、从请求头里获取到token
        sysUserService.logout(token);
        // 3、返回用户信息
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }


}
