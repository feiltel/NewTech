package com.nut2014.newtech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nut2014.newtech.compress.CompressActivity;
import com.nut2014.newtech.constraint.ConstraintActivity;
import com.nut2014.newtech.mvp.ContentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(view -> {
            //constraint
            startActivity(new Intent(MainActivity.this, ConstraintActivity.class));
        });
        findViewById(R.id.btn2).setOnClickListener(view -> {
            //constraint
            startActivity(new Intent(MainActivity.this, ContentActivity.class));
        });
        findViewById(R.id.btn3).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CompressActivity.class));
        });
    }
}
