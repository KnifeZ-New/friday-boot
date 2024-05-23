package org.knifez.fridaybootcore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.common.enums.ResultStatus;

import java.time.LocalDateTime;

/**
 * 统一包装返回结果
 *
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "FridayResult")
public class FridayResult<T> {

    @Schema(title = "状态码")
    private Integer code;

    @Schema(title = "操作消息")
    private String msg;

    @Schema(title = "返回结果")
    private T data;

    @Schema(title = "是否成功")
    private Boolean success;


    @Schema(title = "请求时间")
    private LocalDateTime timestamp = LocalDateTime.now();

    private FridayResult(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMessage();
        this.data = data;
        this.success = resultStatus == ResultStatus.SUCCESS;
    }

    public FridayResult(Integer code, String message) {
        this.code = code;
        this.msg = message;
        this.success = false;

    }

    public static <T> FridayResult<T> ok(T data) {
        return new FridayResult<>(ResultStatus.SUCCESS, data);
    }

    public static <T> FridayResult<T> fail(ResultStatus resultStatus, T data) {
        return new FridayResult<>(resultStatus, data);
    }

    public static FridayResult<Void> fail(Integer code, String message) {
        return new FridayResult<>(code, message);
    }

    public static <T> FridayResult<T> fail(ResultStatus resultStatus) {
        return fail(resultStatus, null);
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"success\": " + this.success + ",\n" +
                "    \"code\": " + this.code + ",\n" +
                "    \"message\": \"" + this.msg + "\",\n" +
                "    \"data\": " + this.data + ",\n" +
                "    \"timestamp\": \"" + this.timestamp + "\"\n" +
                "}";
    }
}
