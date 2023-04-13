package com.fall.adminserver.model.vo;

import java.io.Serializable;

/**
 * @author FAll
 * @date 2023/4/13 下午2:57
 * AdminServer统一相应
 */
public record ResponseRecord<T>(int status, String msg, T data) implements Serializable {

    // 成功响应
    public static <T> ResponseRecord<T> success(T data) { return success(null, data); }

    public static <T> ResponseRecord<T> success() { return success(null, null); }

    public static <T> ResponseRecord<T> success(String msg,T data) { return new ResponseRecord<>(200,msg,data); }


    // 失败响应
    public static <T> ResponseRecord<T> fail(int code, String msg) {
        return fail(code, msg, null);
    }

    public static <T> ResponseRecord<T> fail(int code, String msg, T data) { return new ResponseRecord<>(code, msg, data); }

}
