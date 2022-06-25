package com.knifez.fridayboot.model;

import cn.hutool.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
public class HttpResult {

    private int code = 200;
    private String msg;
    private Object data;

    public static HttpResult error() {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, "未知异常，请联系管理员");
    }

    public static HttpResult error(String msg) {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, msg);
    }

    public static HttpResult error(int code, String msg) {
        HttpResult r = new HttpResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static HttpResult ok(String msg) {
        HttpResult r = new HttpResult();
        r.setCode(200);
        r.setMsg(msg);
        return r;
    }

    public static HttpResult ok(Object data) {
        HttpResult r = new HttpResult();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    public static HttpResult ok() {
        return new HttpResult();
    }

}
