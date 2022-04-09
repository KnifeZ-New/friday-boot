package com.knifez.fridayboot.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
public class LoginBean {

    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("密码")
    private String password;
}