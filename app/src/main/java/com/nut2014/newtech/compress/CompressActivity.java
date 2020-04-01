package com.nut2014.newtech.compress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.nut2014.newtech.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

//压缩图片 调整图片尺寸
public class CompressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);
       /* compressedImage = new Compressor(this)
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .compressToFile(actualImage);*/
        List<String> photos=new ArrayList<>();
        Flowable.just(photos)
                .observeOn(Schedulers.io())
                .map(list -> {
                    // 同步方法直接返回压缩后的文件
                    return Luban.with(CompressActivity.this).load(list).get();
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
