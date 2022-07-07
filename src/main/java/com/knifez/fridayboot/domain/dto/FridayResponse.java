package com.knifez.fridayboot.domain.dto;

import com.knifez.fridayboot.domain.enums.ResponseCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhang
 */
@Data
public class FridayResponse <T>{

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("状态码")
    private String message;

    @ApiModelProperty("是否成功")
    private Boolean isSuccess;

    @ApiModelProperty("数据")
    private T data;


    @ApiModelProperty("请求时间")
    private LocalDateTime timestamp=LocalDateTime.now();

    public static FridayResponse<Void> success(){
        FridayResponse<Void> response=new FridayResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(ResponseCode.SUCCESS.getMessage());
        response.setIsSuccess(true);
        return response;
    }
    public static <T> FridayResponse<T> success(T data){
        FridayResponse<T> response=new FridayResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(ResponseCode.SUCCESS.getMessage());
        response.setData(data);
        response.setIsSuccess(true);
        return response;
    }

    public static <T> FridayResponse<T> fail(int code, String message){
        FridayResponse<T> response=new FridayResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setIsSuccess(false);
        return response;
    }
}
