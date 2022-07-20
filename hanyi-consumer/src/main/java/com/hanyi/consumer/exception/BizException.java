package com.hanyi.consumer.exception;

import lombok.Getter;

/**
 * <p>
 * 全局异常类
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 18:13
 */
@Getter
public class BizException extends RuntimeException{

    private Integer code;

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String message) {
        super(message);
    }

}
