package org.knifez.fridaybootcore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页查询结果
 *
 * @author zhang
 */
@Getter
@Setter
public class PageResult<T> {

    @Schema(defaultValue = "总条数")
    private long total;

    @Schema(defaultValue = "返回数据")
    private List<T> items;

    public static <T> PageResult<T> builder(List<T> items, long total) {
        var result = new PageResult<T>();
        result.setTotal(total);
        result.setItems(items);
        return result;
    }
}
