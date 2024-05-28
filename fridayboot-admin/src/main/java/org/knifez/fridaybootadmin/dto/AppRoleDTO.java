package org.knifez.fridaybootadmin.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootadmin.entity.AppRole;

import java.util.List;

@Getter
@Setter
@Schema(title = "AppRoleDTO")
public class AppRoleDTO extends AppRole {

    @TableField(exist = false)
    @Schema(title = "角色权限")
    private List<String> permissions;
}
