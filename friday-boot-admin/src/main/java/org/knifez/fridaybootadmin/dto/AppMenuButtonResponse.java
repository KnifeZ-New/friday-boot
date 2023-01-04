package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootadmin.entity.AppMenu;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppMenuButtonResponse extends AppMenu {

    @Schema(defaultValue = "标签颜色")
    private String tagColor;
}
