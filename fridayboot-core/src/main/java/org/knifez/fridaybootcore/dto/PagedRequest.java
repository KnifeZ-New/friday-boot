package org.knifez.fridaybootcore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求参数
 *
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "PagedRequest", description = "分页查询通用请求参数")
public class PagedRequest {

    @Schema(title = "当前页码")
    private int current;

    @Schema(title = "分页大小")
    private int size;
}
