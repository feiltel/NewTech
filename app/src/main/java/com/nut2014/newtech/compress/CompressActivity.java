package com.nut2014.newtech.compress;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
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
    private SeekBar quality_sb;
    private TextView quality_number_tv;
    private EditText limit_h_et;
    private EditText limit_w_et;
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
        return new CompressPresenter();
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
        quality_sb = findViewById(R.id.quality_sb);
        quality_number_tv = findViewById(R.id.quality_number_tv);
        limit_h_et = findViewById(R.id.limit_h_et);
        limit_w_et = findViewById(R.id.limit_w_et);
    }

    @Override
    public void initEvent() {
        startBtn.setOnClickListener(v -> {
            String pathStr = pathEt.getText().toString();
            Log.d(TAG, pathStr);
            // int quality,int maxHeight,int maxWidth
            int quality = Integer.decode(quality_number_tv.getText().toString());
            int maxHeight = Integer.decode(limit_h_et.getText().toString());
            int maxWidth = Integer.decode(limit_w_et.getText().toString());
            getPresenter().starCompress(pathStr, quality, maxHeight, maxWidth);
        });
        quality_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quality_number_tv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
