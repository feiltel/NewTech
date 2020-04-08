package com.nut2014.newtech.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.nut2014.newtech.R;
import com.nut2014.newtech.compress.CompressActivity;
import com.nut2014.newtech.constraint.ConstraintActivity;
import com.nut2014.newtech.mvp.ContentActivity;
import com.nut2014.newtech.utils.FPermission;
import com.nut2014.newtech.utils.MToast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView listRv = findViewById(R.id.list_rv);
        listRv.setLayoutManager(new LinearLayoutManager(this));
        List<String> titleList=new ArrayList<>();
        titleList.add("约束布局");
        titleList.add("MVP架构");
        titleList.add("压缩图片工具");
        MainListAdapter mainListAdapter = new MainListAdapter(titleList);
        listRv.setAdapter(mainListAdapter);
        mainListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, ConstraintActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, ContentActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, CompressActivity.class));
                        break;
                }
            }
        });

        //请求权限
        String[] permissionStr = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        FPermission.getInstance().checkPermission(MainActivity.this, permissionStr, 1, new FPermission.FPermissionCallBack() {
            @Override
            public void granted() {
                MToast.show(MainActivity.this,"granted");
            }

            @Override
            public void refuse() {
                MToast.show(MainActivity.this,"refuse");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FPermission.getInstance().onRequestResults(requestCode,permissions,grantResults);
    }
}
