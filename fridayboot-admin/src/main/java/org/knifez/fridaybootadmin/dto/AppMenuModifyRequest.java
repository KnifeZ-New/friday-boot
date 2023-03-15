package org.knifez.fridaybootadmin.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootadmin.entity.AppMenu;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "AppMenuModifyRequest")
public class AppMenuModifyRequest extends AppMenu {

}
