package com.tuixiaofei.spzx.common.exception;

import com.tuixiaofei.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class TuixiaofeiException extends RuntimeException {
    private Integer code;

    private String message;

    private ResultCodeEnum resultCodeEnum;

    public TuixiaofeiException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

}
