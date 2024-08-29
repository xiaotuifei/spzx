package com.tuixiaofei.spzx.manager.service;

import com.tuixiaofei.spzx.model.dto.system.LoginDto;
import com.tuixiaofei.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);
}
