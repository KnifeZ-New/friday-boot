package org.knifez.fridaybootcore.tools;

import java.io.File;

public class FileUtil {


    public static boolean exist(String path) {
        return cn.hutool.core.io.FileUtil.exist(path);
    }

    public static File mkParentDirs(String path) {
        return cn.hutool.core.io.FileUtil.mkParentDirs(path);
    }


    public static File newFile(String path) {
        return cn.hutool.core.io.FileUtil.newFile(path);
    }

}

