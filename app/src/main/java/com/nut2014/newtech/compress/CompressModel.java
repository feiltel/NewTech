package com.nut2014.newtech.compress;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.nut2014.baselibrary.utils.CompressUtils;
import com.nut2014.baselibrary.utils.FLog;
import com.nut2014.baselibrary.utils.FileSizeUtil;
import com.nut2014.baselibrary.utils.FileUtil;
import com.nut2014.baselibrary.utils.PathUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class CompressModel {
    private static final String TAG = "CompressModel";
    private Context context;
    private static final int FINISH_KEY = 1;
    private String lubanTargetPath = PathUtils.getSDCardPath() + "/newtech1";
    private String compressorTargetPath = PathUtils.getSDCardPath() + "/newtech2";
    private CompressCallBack compressCallBack;

    public CompressModel(Context context) {
        this.context = context;
    }

    public interface CompressCallBack {
        void success();

        void compressInfo(String msg);

        void progress(int progress);
    }

    public void compressPictures(String path, int quality, int maxHeight, int maxWidth, CompressCallBack callBack) {
        FLog.d(TAG, path);
        compressCallBack = callBack;
        new Thread(() -> {
            try {
                int finishNum = 0;

                initFilePath();
                String fileParentPath = PathUtils.getSDCardPath() + path;
                File file = new File(fileParentPath);
                if (file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    List<File> photos = FileUtil.filterImage(listFiles);
                    int fileNum = photos.size();
                    if (fileNum < 1) {
                        handler.sendEmptyMessage(FINISH_KEY);
                        return;
                    }
                    for (File file1 : photos) {
                        FileUtil.isImage(file1);
                        sendMsg(file1.getName() + "\n压缩前大小：" + FileSizeUtil.getAutoFileOrFilesSize(file1.getPath()) + "\n");
                        File lubanFile = CompressUtils.compressWithLuban(context, file1, lubanTargetPath);
                        if (lubanFile != null) {
                            sendMsg("Luban压缩后大小：" + FileSizeUtil.getAutoFileOrFilesSize(lubanFile.getPath()) + "\n");
                            File file2 = CompressUtils.compressWithCompressor(context, lubanFile, quality, maxHeight, maxWidth, compressorTargetPath);
                            sendMsg("Compressor压缩后大小：" + FileSizeUtil.getAutoFileOrFilesSize(file2.getPath()) + "\n\n");
                        }
                        finishNum++;
                        int progress = (int) ((finishNum / (fileNum * 1.0)) * 100);
                        sendProgress(progress);
                    }
                    handler.sendEmptyMessage(FINISH_KEY);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendProgress(int progress) {
        Message message = new Message();
        message.arg1 = progress;
        handler.sendMessage(message);
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
            if (msg.what == FINISH_KEY) {
                if (compressCallBack != null) {
                    compressCallBack.success();
                }
            } else if (msg.arg1 > 0) {
                if (compressCallBack != null) {
                    compressCallBack.progress(msg.arg1);
                    FLog.d(TAG, msg.arg1 + ">>>");
                }
            } else {
                if (compressCallBack != null) {
                    stringBuilder.append(msg.obj);
                    compressCallBack.compressInfo(stringBuilder.toString());
                }
            }

        }
    };


}
