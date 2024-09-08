package com.tuixiaofei.spzx.manager.service;

import com.tuixiaofei.spzx.model.vo.system.ValidateCodeVo;

/**
 * @Author : tuixiaofei
 * @Date: 2024/9/8 0:44
 * @Description:
 */
public interface ValidateCodeService {

    /**
     * 生成验证码
     * @return ValidateCodeVo
     */
    ValidateCodeVo generateValidateCode();
}
