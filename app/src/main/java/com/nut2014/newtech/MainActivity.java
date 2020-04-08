package com.nut2014.newtech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nut2014.newtech.compress.CompressActivity;
import com.nut2014.newtech.constraint.ConstraintActivity;
import com.nut2014.newtech.mvp.ContentActivity;
import com.nut2014.newtech.utils.FPermission;
import com.nut2014.newtech.utils.MToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ConstraintActivity.class));
            //constraint
        });
        findViewById(R.id.btn2).setOnClickListener(view -> {
            //constraint
            startActivity(new Intent(MainActivity.this, ContentActivity.class));
        });
        findViewById(R.id.btn3).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CompressActivity.class));
        });
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
