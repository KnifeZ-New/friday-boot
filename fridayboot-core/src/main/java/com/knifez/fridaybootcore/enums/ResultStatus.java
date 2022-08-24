package com.knifez.fridaybootcore.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * 结果状态
 *
 * @author zhang
 */
@Getter
public enum ResultStatus {
    //Http状态码
    SUCCESS(HttpStatus.OK),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),

    // 授权/登录验证失败
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "不存在的用户或已超期，请登录后再进行操作！"),

    UNAUTHORIZED_001(401001, "用户名或密码不正确。"),

    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "禁止访问! 请联系管理员确认账户权限"),

    FORBIDDEN_001(403001, "账户已锁定，请联系管理员解锁"),

    FORBIDDEN_002(403002, "登录失败次数过多，请稍后再试"),


    //    400 0001 - 400 9999 业务状态码
    CHECK_PARAMS_FAILED(400001, "参数校验失败");
    //    400 end

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 消息
     */
    private final String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultStatus(HttpStatus status) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
    }
}
