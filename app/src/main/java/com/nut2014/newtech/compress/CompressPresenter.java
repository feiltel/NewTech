package com.nut2014.newtech.compress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.nut2014.newtech.mvp.base.BaseMvpPresenter;
import com.nut2014.newtech.utils.FileSizeUtil;
import com.nut2014.newtech.utils.PathUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.zelory.compressor.Compressor;
import top.zibin.luban.Luban;

public class CompressPresenter extends BaseMvpPresenter<CompressView> {
    private static final String TAG = "CompressPresenter";
    private String lubanTargetPath = PathUtils.getSDCardPath() + "/newtech1";
    private String compressorTargetPath = PathUtils.getSDCardPath() + "/newtech2";

    public CompressPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);

    }

    private StringBuilder stringBuilder = new StringBuilder();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (getMvpView() != null) {
                stringBuilder.append(msg.obj + "\n");
                getMvpView().setLogInfo(stringBuilder.toString());
            }
        }
    };

    public void starCompress(String path, Context context) {
        new Thread(() -> {
            try {
                initFilePath();
                String fileParentPath = PathUtils.getSDCardPath() + path;
                File file = new File(fileParentPath);
                if (file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    List<File> photos = new ArrayList<>(Arrays.asList(listFiles));
                    List<File> fileList = compressWithLuban(context, photos);
                    for (File file1 : fileList) {
                        Message message1 = new Message();
                        message1.obj = file1.getName() + "\nLuban压缩后大小：" + FileSizeUtil.getAutoFileOrFilesSize(file1.getPath());
                        handler.sendMessage(message1);

                        File file2 = compressWithCompressor(context, file1, 20,300,300);
                        Message message = new Message();
                        message.obj = file2.getName() + "\nCompressor压缩后大小：" + FileSizeUtil.getAutoFileOrFilesSize(file2.getPath());
                        handler.sendMessage(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();


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

    //使用Luban 压缩
    private List<File> compressWithLuban(Context context, List<File> files) throws IOException {
        return Luban.with(context).setRenameListener(filePath -> {
            File file = new File(filePath);
            return file.getName();
        }).load(files).setTargetDir(lubanTargetPath).get();
    }

    //使用Compressor压缩
    private File compressWithCompressor(Context context, File file, int quality,int maxHeight,int maxWidth) throws IOException {
        return new Compressor(context)
                .setQuality(quality)
                .setMaxHeight(maxHeight)
                .setMaxWidth(maxWidth)
                .setDestinationDirectoryPath(compressorTargetPath)
                .compressToFile(file);
    }

}
