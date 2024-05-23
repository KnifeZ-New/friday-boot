package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootadmin.entity.AppUser;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(title = "AppUserModifyDTO")
public class AppUserModifyDTO extends AppUser {
    @Schema(title = "角色")
    private List<Integer> roles = new ArrayList<>();
}
