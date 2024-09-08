package com.tuixiaofei.spzx.common.exception;


import com.tuixiaofei.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.build(null, 201, "出现了异常");
    }

    // 自定义异常处理
    @ExceptionHandler(TuixiaofeiException.class)
    @ResponseBody
    public Result error(TuixiaofeiException e) {
        return Result.build(null, e.getResultCodeEnum());
    }

}
