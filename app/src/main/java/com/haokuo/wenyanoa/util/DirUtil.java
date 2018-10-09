package com.haokuo.wenyanoa.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by zjf on 2018/9/5.
 */
public class DirUtil {
    public static final String EX_PARENT_DIR = "/wenyanOA";
    public static final String EX_IMAGE_DIR = "/Image";
    public static final String EX_UPDATE_DIR = "/UpdateApp";
    private static File exParentDir = new File(Environment.getExternalStorageDirectory(), EX_PARENT_DIR);

    public static void createDir() {
        exParentDir.mkdir();
        new File(exParentDir, EX_IMAGE_DIR).mkdir();
        new File(exParentDir, EX_UPDATE_DIR).mkdir();
    }

    public static String getImageDir() {
        return new File(exParentDir, EX_IMAGE_DIR).getAbsolutePath();
    }

    public static String getUpdateDir() {
        return new File(exParentDir, EX_UPDATE_DIR).getAbsolutePath();
    }
}
