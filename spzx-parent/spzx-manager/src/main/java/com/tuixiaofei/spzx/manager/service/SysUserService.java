package com.tuixiaofei.spzx.manager.service;

import com.tuixiaofei.spzx.model.dto.system.LoginDto;
import com.tuixiaofei.spzx.model.entity.system.SysUser;
import com.tuixiaofei.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);
}
