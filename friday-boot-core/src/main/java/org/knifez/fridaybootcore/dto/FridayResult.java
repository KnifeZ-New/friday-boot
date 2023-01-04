package org.knifez.fridaybootcore.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.enums.ResultStatus;

import java.time.LocalDateTime;

/**
 * 统一包装返回结果
 *
 * @author zhang
 */
@Getter
@Setter
public class FridayResult<T> {
    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("操作消息")
    private String message;

    @ApiModelProperty("返回结果")
    private T data;

    @ApiModelProperty("是否成功")
    private Boolean success;


    @ApiModelProperty("请求时间")
    private LocalDateTime timestamp = LocalDateTime.now();

    private FridayResult(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
        this.success = resultStatus == ResultStatus.SUCCESS;
    }

    public FridayResult(Integer code, String message) {
        this.code = code;
        this.message = message;
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
                "    \"message\": \"" + this.message + "\",\n" +
                "    \"data\": " + this.data + ",\n" +
                "    \"timestamp\": \"" + this.timestamp + "\"\n" +
                "}";
    }
}
