package org.knifez.fridaybootapi.controller;

import cn.hutool.core.io.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootcore.common.annotation.ApiRestController;
import org.knifez.fridaybootcore.common.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootcore.dto.FridayResult;
import org.knifez.fridaybootcore.dto.UploadFileResponse;
import org.knifez.fridaybootcore.common.enums.ResultStatus;
import org.knifez.fridaybootcore.utils.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@AllowAuthenticated
@Tag(name = "附件管理")
@ApiRestController
@RequestMapping("/file")
public class AttachmentController {
    @PostMapping("/upload")
    @Operation(summary = "单文件上传")
    public FridayResult<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file) {

        String projectPath = System.getProperty("user.dir") + "/static";
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        var fileNameArray = originalFileName.split("\\.");
        String uploadFileName = System.currentTimeMillis() + String.valueOf(file.getSize()) + "." + fileNameArray[fileNameArray.length - 1];
        // todo 动态读取配置
        String uploadPath = "/uploads";
        try {
            if (!FileUtil.exist(projectPath + uploadPath)) {
                FileUtil.mkParentDirs(projectPath + uploadPath + "/" + uploadFileName);
            }
            uploadPath = uploadPath + "/" + uploadFileName;
            var uploadFile = FileUtil.newFile(projectPath + uploadPath);
            file.transferTo(uploadFile);
        } catch (IOException e) {
            return FridayResult.fail(ResultStatus.UPLOAD_NO_DIR);
        }

        return FridayResult.ok(new UploadFileResponse(file.getOriginalFilename(), ServletRequestUtils.getServletHost() + uploadPath));
    }
}
