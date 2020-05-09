package com.nut2014.baselibrary.base;

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
 * 基础Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getViewId();

    protected abstract void initView();

    protected abstract void initEvent();

    public BaseParam getBaseParam() {
        return new BaseParam();
    }
    private Bundle savedInstanceState=null;
    public Bundle getSavedInstanceState(){
        return savedInstanceState;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
        if (getBaseParam() == null) {
            throw new NullPointerException("配置参数不能返回空");
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
        //沉浸式状态栏
        if (getBaseParam().isTransparent()) {
            StatusBarUtil.setTransparent(this);
        } else {
            //默认状态栏全透明
            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        }
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    /**
     * 设置暗色模式
     */
    public void setDarkMode(){
        StatusBarUtil.setDarkMode(this);
    }

    /**
     * 设置为亮色模式
     */
    public void setLightMode(){
        StatusBarUtil.setLightMode(this);
    }
    /**
     * 设置toolBar title
     * @param title 标题
     */
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

    /**
     * 显示加载进度条
     * @param msg 进度信息
     * @param hasProgress 是否显示进度
     */
    protected void showProgress(String msg, boolean hasProgress) {
        FProgressDialog.getInstance().show(this, msg, hasProgress);
    }

    /**
     * 设置对话框进度
     * @param progress 进度
     */
    protected void setDialogProgress(int progress) {
        FProgressDialog.getInstance().setProgress(progress);
    }
    /**
     * 显示加载进度条
     * @param msg 进度信息
     */
    protected void showProgress(String msg) {
        FProgressDialog.getInstance().show(this, msg, false);
    }

    /**
     * 隐藏进度
     */
    protected void hideProgress() {
        FProgressDialog.getInstance().dismiss();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    protected AppCompatActivity getContext() {
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
    protected void jumpActivity(Class<?> cls) {
        jumpActivity(cls,null);
    }
    protected void jumpActivity(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 显示toast
     * @param msg toast信息
     */
    protected void showToast(String msg) {
        MToast.show(this, msg);
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
