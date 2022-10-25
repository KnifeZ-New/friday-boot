package com.knifez.fridaybootadmin.dto;

import com.knifez.fridaybootadmin.entity.AppMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppMenuButtonResponse extends AppMenu {

    @ApiModelProperty("标签颜色")
    private String tagColor;
}
