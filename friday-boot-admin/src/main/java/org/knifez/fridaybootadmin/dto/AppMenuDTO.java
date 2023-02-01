package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.knifez.fridaybootadmin.entity.AppMenu;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@ToString
@Schema(defaultValue = "AppMenuDTO")
public class AppMenuDTO extends AppMenu {

    @Schema(defaultValue = "标签颜色")
    private String tagColor;
}
