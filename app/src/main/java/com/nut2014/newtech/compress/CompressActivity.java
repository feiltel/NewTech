package com.nut2014.newtech.compress;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nut2014.newtech.R;
import com.nut2014.newtech.mvp.base.BaseMvpActivity;

//压缩图片 调整图片尺寸
public class CompressActivity extends BaseMvpActivity<CompressView, CompressPresenter> implements CompressView {
    private static final String TAG = "CompressActivity";
    private EditText pathEt;
    private Button startBtn;
    private TextView infoTv;
    private ConstraintLayout rootCl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);
        initView();
        initEvent();
    }

    @Override
    protected CompressPresenter createPresenter() {
        return new CompressPresenter(this);
    }

    @Override
    public void setLogInfo(String msg) {
        infoTv.setText(msg);
    }

    @Override
    public void startCompress() {
        startBtn.setEnabled(false);
    }

    @Override
    public void endCompress() {
        startBtn.setEnabled(true);
    }

    @Override
    public void initView() {
        pathEt = findViewById(R.id.path_et);
        startBtn = findViewById(R.id.start_btn);
        infoTv = findViewById(R.id.info_tv);
        rootCl = findViewById(R.id.root_cl);
    }

    @Override
    public void initEvent() {
        startBtn.setOnClickListener(v -> {
            String pathStr = pathEt.getText().toString();
            Log.d(TAG, pathStr);
            getPresenter().starCompress(pathStr, this);
        });
    }
}
