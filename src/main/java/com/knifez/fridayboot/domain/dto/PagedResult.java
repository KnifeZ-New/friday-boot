package com.knifez.fridayboot.domain.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * @author zhang
 */
@Getter
@Setter
public class PagedResult<T> {

    @ApiModelProperty("当前页码")
    private long current;

    @ApiModelProperty("总条数")
    private long total;

    @ApiModelProperty("分页大小")
    private long size;

    @ApiModelProperty("分页数")
    private long pages;

    @ApiModelProperty("列表数据")
    private List<T> records;

    public PagedResult(IPage<T> page){
        this.pages = page.getPages();
        this.size = page.getSize();
        this.total = page.getTotal();
        this.records=page.getRecords();
    }
}
