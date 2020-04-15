package com.nut2014.baselibrary.utils;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;
import top.zibin.luban.Luban;

public class CompressUtils {
    private static final String TAG="CompressUtils";
    //使用Luban 压缩
    public static List<File> compressWithLuban(Context context, List<File> files,String targetPath) throws IOException {
        FLog.d(TAG, files.size() + "");
        return Luban.with(context).ignoreBy(1).setRenameListener(filePath -> {
            File file = new File(filePath);
            return file.getName();
        }).load(files).setTargetDir(targetPath).get();
    }

    public static File compressWithLuban(Context context, File file,String targetPath) throws IOException {
        if (file.isDirectory()) {
            return null;
        }
        FLog.d(TAG, file.getName());
        List<File> list = Luban.with(context).ignoreBy(1).setRenameListener(filePath -> {
            File mfile = new File(filePath);
            return mfile.getName();
        }).load(file).setTargetDir(targetPath).get();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    //使用Compressor压缩
    public static File compressWithCompressor(Context context, File file, int quality, int maxHeight, int maxWidth,String targetPath) throws IOException {
        FLog.d(TAG, file.getName() + "");
        return new Compressor(context)
                .setQuality(quality)
                .setMaxHeight(maxHeight)
                .setMaxWidth(maxWidth)
                .setDestinationDirectoryPath(targetPath)
                .compressToFile(file);
    }
}
