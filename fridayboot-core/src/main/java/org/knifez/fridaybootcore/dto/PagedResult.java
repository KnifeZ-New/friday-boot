package org.knifez.fridaybootcore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页查询结果
 *
 * @author KnifeZ
 */
@Getter
@Setter
public class PagedResult<T> {

    /**
     * 总条数
     */
    @Schema(title = "总条数")
    private long total;

    /**
     * 返回数据
     */
    @Schema(title = "返回数据")
    private List<T> items;

    public static <T> PagedResult<T> builder(List<T> items, long total) {

        var result = new PagedResult<T>();
        result.setTotal(total);
        result.setItems(items);
        return result;
    }
}
