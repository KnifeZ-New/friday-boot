package org.knifez.fridaybootgenerator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
    @Override
    protected void outputCustomFile(List<CustomFile> customFiles, TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String otherPath = this.getPathInfo(OutputFile.parent) + File.separator + "dto";
        customFiles.forEach((key) -> {
            String fileName = String.format(otherPath + File.separator + entityName + "%s", key.getFileName());
            this.outputFile(new File(fileName), objectMap, key.getTemplatePath(), false);
        });
    }
}
