package com.nut2014.baselibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jaeger.library.StatusBarUtil;
import com.nut2014.baselibrary.R;
import com.nut2014.baselibrary.utils.FProgressDialog;
import com.nut2014.baselibrary.utils.KeyBoardUtils;
import com.nut2014.baselibrary.utils.MToast;
import com.nut2014.baselibrary.utils.TouchOutHideKeyBoard;

import butterknife.ButterKnife;

/**
 * @author feiltel 2020/4/9 0009
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getViewId();

    protected abstract void initView();

    protected abstract void initEvent();

    public BaseParam getBaseParam() {
        return new BaseParam();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getBaseParam() == null) {
            throw new NullPointerException("配置参数不能返回空");
        }
        //沉浸式状态栏
        if (getBaseParam().isFullScreen()) {
            StatusBarUtil.setTranslucent(this);
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        }
        //是否加载默认toolbar
        if (getBaseParam().isHaveToolbar()) {
            super.setContentView(R.layout.base_layout);
            setToolbarContentView(getViewId());
            setTitle(getBaseParam().getTitle());
            if (getBaseParam().isHaveBackAction() && getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } else {
            setContentView(getViewId());
        }
        ButterKnife.bind(this);
        initView();
        initEvent();
    }
    public void setDarkMode(){
        StatusBarUtil.setDarkMode(this);
    }
    public void setLightMode(){
        StatusBarUtil.setLightMode(this);
    }
    protected void setTitle(String title) {
        if (getBaseParam().isHaveToolbar()) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (getBaseParam().isHaveToolbar() && getBaseParam().isHaveBackAction()) {
            if (id == android.R.id.home) {
                super.onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void setToolbarContentView(int layoutResID) {
        FrameLayout root_lin = findViewById(R.id.fl_content);
        View inflate = View.inflate(this, layoutResID, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        root_lin.addView(inflate, params);
    }

    protected void showProgress(String msg, boolean hasProgress) {
        FProgressDialog.getInstance().show(this, msg, hasProgress);
    }

    protected void showProgress(String msg) {
        FProgressDialog.getInstance().show(this, msg, false);
    }

    protected void hideProgress() {
        FProgressDialog.getInstance().dismiss();
    }

    protected void setDialogProgress(int progress) {
        FProgressDialog.getInstance().setProgress(progress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
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


    /**
     * 以下是关于软键盘的处理 点击输入框外自动隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length < 1) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (TouchOutHideKeyBoard.isTouchView(filterViewByIds(), ev)) {
                return super.dispatchTouchEvent(ev);
            }
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0) {
                return super.dispatchTouchEvent(ev);
            }
            View v = getCurrentFocus();
            if (TouchOutHideKeyBoard.isFocusEditText(v, hideSoftByEditViewIds())) {
                KeyBoardUtils.hideSoftInput(this);
                TouchOutHideKeyBoard.clearViewFocus(v, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }


}
