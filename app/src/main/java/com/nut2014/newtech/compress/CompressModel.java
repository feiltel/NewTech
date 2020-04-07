package com.nut2014.newtech.compress;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.nut2014.newtech.utils.FLog;
import com.nut2014.newtech.utils.FileSizeUtil;
import com.nut2014.newtech.utils.PathUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.zelory.compressor.Compressor;
import top.zibin.luban.Luban;

public class CompressModel {
    private static final String TAG = "CompressModel";
    private Context context;
    private static final int FINSIH_KEY = 1;
    private String lubanTargetPath = PathUtils.getSDCardPath() + "/newtech1";
    private String compressorTargetPath = PathUtils.getSDCardPath() + "/newtech2";
    private CompressCallBack compressCallBack;

    public CompressModel(Context context) {
        this.context = context;
    }

    public interface CompressCallBack {
        void success();

        void compressInfo(String msg);
    }

    public void compressPictures(String path, int quality, int maxHeight, int maxWidth, CompressCallBack callBack) {
        FLog.e(TAG, path);
        compressCallBack = callBack;
        new Thread(() -> {
            try {
                initFilePath();
                String fileParentPath = PathUtils.getSDCardPath() + path;
                File file = new File(fileParentPath);
                if (file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    List<File> photos = new ArrayList<>(Arrays.asList(listFiles));

                    for (File file1 : photos) {
                        sendMsg(file1.getName() + "\n压缩前大小：" + FileSizeUtil.getAutoFileOrFilesSize(file1.getPath()) + "\n");
                        File lubanFile = compressWithLuban(context, file1);
                        if (lubanFile!=null){
                            sendMsg("Luban压缩后大小：" + FileSizeUtil.getAutoFileOrFilesSize(lubanFile.getPath()) + "\n");
                            File file2 = compressWithCompressor(context, lubanFile, quality, maxHeight, maxWidth);
                            sendMsg("Compressor压缩后大小：" + FileSizeUtil.getAutoFileOrFilesSize(file2.getPath()) + "\n\n");
                        }
                    }
                    handler.sendEmptyMessage(FINSIH_KEY);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMsg(String msg) {
        char[] chars = msg.toCharArray();
        for (char aChar : chars) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message1 = new Message();
            message1.obj = aChar;
            handler.sendMessage(message1);
        }

    }

    private void initFilePath() {
        File file = new File(lubanTargetPath);
        if (!file.exists()) {
            boolean newFile = file.mkdirs();
        }
        File file2 = new File(compressorTargetPath);
        if (!file2.exists()) {
            boolean newFile = file2.mkdirs();
        }
    }

    private StringBuilder stringBuilder = new StringBuilder();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FINSIH_KEY) {
                if (compressCallBack != null) {
                    compressCallBack.success();
                }
            } else {
                if (compressCallBack != null) {
                    stringBuilder.append(msg.obj);
                    compressCallBack.compressInfo(stringBuilder.toString());
                }
            }

        }
    };

    //使用Luban 压缩
    private List<File> compressWithLuban(Context context, List<File> files) throws IOException {
        FLog.d(TAG, files.size() + "");
        return Luban.with(context).ignoreBy(1).setRenameListener(filePath -> {
            File file = new File(filePath);
            return file.getName();
        }).load(files).setTargetDir(lubanTargetPath).get();
    }

    private File compressWithLuban(Context context, File file) throws IOException {
        FLog.d(TAG, file.getName());
        List<File> list = Luban.with(context).ignoreBy(1).setRenameListener(filePath -> {
            File mfile = new File(filePath);
            return mfile.getName();
        }).load(file).setTargetDir(lubanTargetPath).get();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    //使用Compressor压缩
    private File compressWithCompressor(Context context, File file, int quality, int maxHeight, int maxWidth) throws IOException {
        FLog.d(TAG, file.getName() + "");
        return new Compressor(context)
                .setQuality(quality)
                .setMaxHeight(maxHeight)
                .setMaxWidth(maxWidth)
                .setDestinationDirectoryPath(compressorTargetPath)
                .compressToFile(file);
    }
}
