package com.tuixiaofei.spzx.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.tuixiaofei.spzx.AuthContextUtil;
import com.tuixiaofei.spzx.model.entity.system.SysUser;
import com.tuixiaofei.spzx.model.vo.common.Result;
import com.tuixiaofei.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Author : tuixiaofei
 * @Date: 2024/9/8 12:47
 * @Description:
 */

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1、获取请求方式
        // 如果请求方式是options 预检请求，直接放行
        String method = request.getMethod();
        if ("OPTIONS".equals(method))
            return true;
        // 2、从请求头获取token
        String token = request.getHeader("token");

        // 3、如果token为空，返回错误提示
        if (StrUtil.isEmpty(token)) {
            responseNoLoginInfo(response);
            return false;
        }

        // 4、如果token不为空，拿着token去查redis
        String userInfoString = redisTemplate.opsForValue().get("user:login" + token);

        // 5、如果redis查询不到数据，返回错误提示
        if (StrUtil.isEmpty(userInfoString)) {
            responseNoLoginInfo(response);
        }

        // 6、如果redis能够查询到用户信息，将用户信息放到threadLocal中
        SysUser sysUser = JSON.parseObject(userInfoString, SysUser.class);
        AuthContextUtil.set(sysUser);

        // 7、更新redis中用户信息的过期时间
        redisTemplate.expire("user:login" + token, 30, TimeUnit.MINUTES);
        // 8、放行
        return true;
    }

    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 删除ThreadLocal中的用户信息
        AuthContextUtil.remove();
    }
}
