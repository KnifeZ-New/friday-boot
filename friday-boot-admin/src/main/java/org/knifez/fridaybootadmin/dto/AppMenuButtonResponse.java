package org.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootadmin.entity.AppMenu;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppMenuButtonResponse extends AppMenu {

    @ApiModelProperty("标签颜色")
    private String tagColor;
}
