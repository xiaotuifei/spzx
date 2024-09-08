package com.tuixiaofei.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.tuixiaofei.spzx.common.exception.TuixiaofeiException;
import com.tuixiaofei.spzx.manager.mapper.SysUserMapper;
import com.tuixiaofei.spzx.manager.service.SysUserService;
import com.tuixiaofei.spzx.model.dto.system.LoginDto;
import com.tuixiaofei.spzx.model.entity.system.SysUser;
import com.tuixiaofei.spzx.model.vo.common.Result;
import com.tuixiaofei.spzx.model.vo.common.ResultCodeEnum;
import com.tuixiaofei.spzx.model.vo.system.LoginVo;
import jakarta.annotation.Resource;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    private final String USER_LOGIN = "user:login";
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {

        // 获取输入的验证码和存储到redis的key名称
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();

        // 根据获取的redis里面key，查询redis里面存储验证码
        String redisCaptcha = redisTemplate.opsForValue().get("user:validate" + key);

        // 比较输入的验证码和redis存储验证码是否一致
        // 如果不一致，提示用户，校验失败
        if (StrUtil.isEmpty(redisCaptcha) || !StrUtil.equalsIgnoreCase(captcha, redisCaptcha)) {
            throw new TuixiaofeiException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        // 如果一致，删除redis里面验证码
        redisTemplate.delete("user:validate" + key);


        String username = loginDto.getUserName();
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(username);
        if (null == sysUser) {
            throw new TuixiaofeiException(ResultCodeEnum.LOGIN_ERROR);
        }
        String database_password = sysUser.getPassword();
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());

        if (!database_password.equals(input_password)) {
            throw new TuixiaofeiException(ResultCodeEnum.LOGIN_ERROR);
        }

        String token = UUID.randomUUID().toString().replaceAll("_", "");

        redisTemplate.opsForValue().set(USER_LOGIN + token,
                JSON.toJSONString(sysUser),
                7,
                TimeUnit.HOURS);

        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;

    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get(USER_LOGIN + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(USER_LOGIN + token);
    }
}





























/*
 // 1、获取提交的用户名，loginDto中得到
        String userName = loginDto.getUserName();

        // 2、根据用户名查询数据库 sys_user表
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);

        // 3、如果查不到，说明用户不存在，返回错误信息
        if(null == sysUser) {
           throw new RuntimeException("用户名不存在");
        }

        // 4、如果能查到用户信息，说明用户存在
        String database_password = sysUser.getPassword();
        // 5、获取输入的密码，与数据库存储的用户密码作比较
        // 把输入的密码进行加密，再比较数据库密码
        String input_password= DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        // 比较
        if(!StrUtil.equals(database_password, input_password)) {
            throw new RuntimeException("密码错误");
        }
        // 6、密码一致，登录成功，密码不一致登录失败
        // 7、生成用户的唯一标识token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // 8、把登录成功的用户信息放到redis里
        redisTemplate.opsForValue().set("user:login"+token, JSON.toJSONString(sysUser), 7, TimeUnit.DAYS);

        // 9、返回LoginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
 */