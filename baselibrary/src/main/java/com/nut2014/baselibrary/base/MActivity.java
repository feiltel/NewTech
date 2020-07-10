package com.nut2014.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.nut2014.baselibrary.utils.KeyBoardUtils;
import com.nut2014.baselibrary.utils.MToast;
import com.nut2014.baselibrary.utils.TouchOutHideKeyBoard;

/**
 * @author feiltel 2020/4/9 0009
 * 基础Activity
 */
public abstract class MActivity extends AppCompatActivity {
    /**
     * 防止重复点击
     */
    long lastClick = 0;

    /**
     * 设置暗色模式
     */
    public void setDarkMode() {
        StatusBarUtil.setDarkMode(this);
    }

    /**
     * 设置为亮色模式
     */
    public void setLightMode() {
        StatusBarUtil.setLightMode(this);
    }

    protected AppCompatActivity getContext() {
        return this;
    }
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
        jumpActivity(cls, null);
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
     *
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
