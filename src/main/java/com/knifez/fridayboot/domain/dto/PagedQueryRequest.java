package com.knifez.fridayboot.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang
 */
@Getter
@Setter
public class PagedQueryRequest <T>{
    @ApiModelProperty("当前页码")
    private int current;
    @ApiModelProperty("总条数")
    private int total;
    @ApiModelProperty("分页大小")
    private int size;
    @ApiModelProperty("分页数")
    private int pages;
    @ApiModelProperty("查询条件")
    private T queryWrapper;
}
