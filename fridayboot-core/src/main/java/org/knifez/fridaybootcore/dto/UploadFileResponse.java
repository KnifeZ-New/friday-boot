package org.knifez.fridaybootcore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 上传文件响应
 *
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "UploadFileResponse")
public class UploadFileResponse {

    @Schema(title = "文件名")
    private String fileName;

    @Schema(title = "上传路径")
    private String filePath;

    public UploadFileResponse(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}