package com.nut2014.newtech.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nut2014.newtech.R;

public  class ToolBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_layout);
    }

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout root_lin = findViewById(R.id.root_lin);
        View inflate = LayoutInflater.from(this).inflate(layoutResID, null);
        root_lin.addView(inflate);
    }
}
