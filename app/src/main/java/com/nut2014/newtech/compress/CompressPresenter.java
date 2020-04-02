package com.nut2014.newtech.compress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.nut2014.newtech.mvp.base.BaseMvpPresenter;
import com.nut2014.newtech.utils.PathUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import id.zelory.compressor.Compressor;
import top.zibin.luban.Luban;

public class CompressPresenter extends BaseMvpPresenter<CompressView> {
    private static final String TAG = "CompressPresenter";

    public CompressPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);

    }

    private int finishNum = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                finishNum++;
                if (getMvpView() != null) {
                    getMvpView().setLogInfo("FINISH " + finishNum);
                }
            }
        }
    };

    @SuppressLint("CheckResult")
    public void starCompress(String path, Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String fileParentPath = PathUtils.getSDCardPath() + path;
                    Log.d(TAG, fileParentPath);
                    File file = new File(fileParentPath);
                    if (file.isDirectory()) {
                        File[] listFiles = file.listFiles();
                        Queue<File> photos = new LinkedList<>();
                        for (File listFile : listFiles) {
                            photos.offer(listFile);
                        }
                        while (true) {
                            File file1 = photos.poll();
                            if (file1 != null) {
                                List<File> file2 = compressImage(context, file1);
                                for (File file3 : file2) {
                                    System.out.println(file3.getName());
                                    handler.sendEmptyMessage(1);
                                }

                            } else {
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    private  List<File> compressImage(Context context, File file) throws IOException {
        File compressToFile = new Compressor(context)
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setQuality(75)
                .setDestinationDirectoryPath(PathUtils.getSDCardPath() + "/newtech1")
                .compressToFile(file);
        return Luban.with(context).load(compressToFile).setTargetDir(PathUtils.getSDCardPath() + "/newtech2").get();
    }

}
