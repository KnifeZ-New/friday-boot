package org.knifez.fridaybootcore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求参数
 *
 * @author zhang
 */
@Getter
@Setter
public class PageRequest {

    @Schema(defaultValue = "当前页码")
    private int page;

    @Schema(defaultValue = "分页大小")
    private int pageSize;
}
