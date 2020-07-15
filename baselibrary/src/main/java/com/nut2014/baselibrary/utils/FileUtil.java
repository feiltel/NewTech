package com.nut2014.baselibrary.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author feiltel 2020/4/10 0010
 */
public class FileUtil {
    public static String getExtension(File file) {
        if (file == null) {
            return null;
        }
        String fileName = file.getName();
        int dot = fileName.lastIndexOf(".");
        if (dot >= 0) {
            return fileName.substring(dot);
        } else {
            return "";
        }
    }

    public static List<File> filterImage(List<File> files) {
        List<File> fileList = new ArrayList<>();
        for (File file : files) {
            if (isImage(file)) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    public static List<File> filterImage(File[] files) {
        List<File> fileList = new ArrayList<>();
        for (File file : files) {
            if (isImage(file)) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    public static boolean isImage(File file) {
        String[] imageType = {".png", ".jpg", ".jpeg"};
        String extension = getExtension(file);
        if (extension != null) {
            for (String s : imageType) {
                System.out.println(s + ">>>" + extension);
                if (extension.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

}
