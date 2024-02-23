package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppDictionaryConfigTreeSetQueryRequest {
    @Schema(title = "字典编码集合")
    private List<String> dictCodes;
}
