package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
public class AppOrganizationUnitQueryRequest {

    @Schema(defaultValue = "机构编码")
    private String unitCode;

    @Schema(defaultValue = "名称")
    private String name;
}
