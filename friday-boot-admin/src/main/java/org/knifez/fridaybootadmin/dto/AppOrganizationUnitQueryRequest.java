package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "AppOrganizationUnitQueryRequest")
public class AppOrganizationUnitQueryRequest {

    @Schema(title = "机构编码")
    private String unitCode;

    @Schema(title = "名称")
    private String name;
}
