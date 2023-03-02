package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootadmin.entity.AppUser;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AppUserModifyDTO extends AppUser {
    @Schema(title = "角色")
    private List<Long> roles = new ArrayList<>();
}
