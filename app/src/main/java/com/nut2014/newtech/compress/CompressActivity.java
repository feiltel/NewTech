package com.nut2014.newtech.compress;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nut2014.baselibrary.base.BaseMvpActivity;
import com.nut2014.baselibrary.utils.FPermission;
import com.nut2014.baselibrary.utils.FProgressDialog;
import com.nut2014.newtech.R;

import butterknife.BindView;
import butterknife.ButterKnife;

//压缩图片 调整图片尺寸
public class CompressActivity extends BaseMvpActivity<CompressView, CompressPresenter> implements CompressView {
    private static final String TAG = "CompressActivity";
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 12;

    @BindView(R.id.path_et)
    EditText pathEt;
    @BindView(R.id.start_btn)
    Button startBtn;
    @BindView(R.id.info_tv)
    TextView infoTv;
    @BindView(R.id.quality_sb)
    SeekBar quality_sb;
    @BindView(R.id.quality_number_tv)
    TextView quality_number_tv;
    @BindView(R.id.limit_h_et)
    EditText limit_h_et;
    @BindView(R.id.limit_w_et)
    EditText limit_w_et;
    @BindView(R.id.root_cl)
    ConstraintLayout rootCl;
    @BindView(R.id.info_sv)
    ScrollView info_sv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);
        ButterKnife.bind(this);
        initEvent();
    }

    @Override
    protected CompressPresenter createPresenter() {
        return new CompressPresenter();
    }

    @Override
    public void setLogInfo(String msg) {
        infoTv.setText(msg);
        info_sv.fullScroll(View.FOCUS_DOWN);
        // info_sv.smoothScrollBy(0,20);
    }

    @Override
    public void startCompress() {
        startBtn.setEnabled(false);
        FProgressDialog.getInstance().show(this, "压缩中...", true);
    }

    @Override
    public void endCompress() {
        startBtn.setEnabled(true);
        FProgressDialog.getInstance().dismiss();
        showToast("压缩完成");
    }

    @Override
    public void compressProgress(int progress) {
        FProgressDialog.getInstance().setProgress(progress);
    }


    public void initEvent() {
        startBtn.setOnClickListener(v -> {
            FPermission.getInstance().checkPermission(CompressActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE_REQUEST_CODE, new FPermission.CallBack() {
                @Override
                public void granted() {
                    compressAct();
                }

                @Override
                public void refuse() {
                    showToast("存储权限被拒绝");
                }
            });
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


    private void compressAct() {
        String pathStr = pathEt.getText().toString();
        Log.d(TAG, pathStr);
        // int quality,int maxHeight,int maxWidth
        int quality = Integer.decode(quality_number_tv.getText().toString());
        int maxHeight = Integer.decode(limit_h_et.getText().toString());
        int maxWidth = Integer.decode(limit_w_et.getText().toString());
        getPresenter().starCompress(pathStr, quality, maxHeight, maxWidth);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FPermission.getInstance().onRequestResult(requestCode, permissions, grantResults);
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.path_et, R.id.limit_h_et, R.id.limit_w_et};
        return ids;
    }


}

