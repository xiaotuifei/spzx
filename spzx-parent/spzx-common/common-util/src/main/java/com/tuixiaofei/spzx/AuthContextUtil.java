package com.tuixiaofei.spzx;

import com.tuixiaofei.spzx.model.entity.system.SysUser;

/**
 * @Author : tuixiaofei
 * @Date: 2024/9/8 12:40
 * @Description:
 */
public class AuthContextUtil {

    // 创建ThreadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    // 添加数据
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    // 获取数据
    public static SysUser get() {
        return threadLocal.get();
    }

    // 删除数据
    public static void remove() {
        threadLocal.remove();
    }
}
