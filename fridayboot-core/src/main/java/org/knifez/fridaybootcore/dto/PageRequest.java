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
@Schema(title = "PageRequest", description = "分页查询通用请求参数")
public class PageRequest {

    @Schema(title = "当前页码")
    private int page;

    @Schema(title = "分页大小")
    private int pageSize;
}
