package com.tuixiaofei.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.tuixiaofei.spzx.manager.mapper.SysUserMapper;
import com.tuixiaofei.spzx.manager.service.SysUserService;
import com.tuixiaofei.spzx.model.dto.system.LoginDto;
import com.tuixiaofei.spzx.model.entity.system.SysUser;
import com.tuixiaofei.spzx.model.vo.common.Result;
import com.tuixiaofei.spzx.model.vo.common.ResultCodeEnum;
import com.tuixiaofei.spzx.model.vo.system.LoginVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        String userName = loginDto.getUserName();
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);

        if (null == sysUser) {
            throw new RuntimeException("用户名或密码错误");
        }

        String database_password = sysUser.getPassword();
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if (!StrUtil.equals(database_password, input_password)) {
            throw new RuntimeException("用户名或密码错误");
        }
        String token = UUID.randomUUID().toString().replaceAll("_", "");
        redisTemplate.opsForValue().set("user:login"+token,
                sysUser.toString(),
                7,
                TimeUnit.DAYS);

        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;

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