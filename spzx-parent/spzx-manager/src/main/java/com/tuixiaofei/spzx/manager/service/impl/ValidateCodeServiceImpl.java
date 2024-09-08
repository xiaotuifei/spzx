package com.tuixiaofei.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.tuixiaofei.spzx.manager.service.ValidateCodeService;
import com.tuixiaofei.spzx.model.vo.system.ValidateCodeVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author : tuixiaofei
 * @Date: 2024/9/8 0:44
 * @Description:
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public ValidateCodeVo generateValidateCode() {
        // 1、通过工具生成图片验证码
        // hutool
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 3, 4);

        // 5位验证码的值
        String codeValue = circleCaptcha.getCode();

        // 对生成的验证码图片进行Base64编码
        String imageBase64 = circleCaptcha.getImageBase64();


        // 2、把验证码存储到redis里面，社会redis的key：uuid redis的value： 验证码值
        // 设置过期时间
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:validate"+key,
                codeValue,
                5,
                TimeUnit.MINUTES);

        // 3、返回ValidateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:images/png;base64," + imageBase64);

        return validateCodeVo;
    }
}
