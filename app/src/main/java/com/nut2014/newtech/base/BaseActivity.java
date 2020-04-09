package com.nut2014.newtech.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.nut2014.newtech.R;
import com.nut2014.newtech.utils.FProgressDialog;
import com.nut2014.newtech.utils.MToast;

import butterknife.ButterKnife;

/**
 * @author feiltel 2020/4/9 0009
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract int getViewId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        setContentView(getViewId());
        ButterKnife.bind(this);

        initView();
        initEvent();
    }

    protected void showProgress(String msg, boolean hasProgress) {
        FProgressDialog.getInstance().show(this, msg, hasProgress);
    }
    protected void showProgress(String msg) {
        FProgressDialog.getInstance().show(this, msg, false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    protected void hideProgress() {
        FProgressDialog.getInstance().dismiss();
    }

    protected void setDialogProgress(int progress) {
        FProgressDialog.getInstance().setProgress(progress);
    }

    protected Activity getContext() {
        return this;
    }


    long lastClick = 0;

    /**
     * 防止重复点击
     */
    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * 跳转到Activity
     *
     * @param cls
     * @param bundle
     */
    protected void jumpActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void showToast(String msg) {
        MToast.show(this, msg);
    }

    protected void jumpActivity(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
