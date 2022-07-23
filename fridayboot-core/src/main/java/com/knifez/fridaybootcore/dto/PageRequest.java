package com.knifez.fridaybootcore.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求参数
 * @author zhang
 */
@Getter
@Setter
public class PageRequest {

    @ApiModelProperty("当前页码")
    private int page;

    @ApiModelProperty("分页大小")
    private int pageSize;
}
