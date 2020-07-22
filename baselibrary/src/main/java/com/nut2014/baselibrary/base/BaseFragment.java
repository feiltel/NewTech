package com.nut2014.baselibrary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.nut2014.baselibrary.utils.MToast;

/**
 * @author feiltel 2020/4/9 0009
 * 基础Activity
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 防止重复点击
     */
    long lastClick = 0;

    public Context getContext() {
        return requireContext();
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
        Intent intent = new Intent(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void jumpActivity(Class<?> cls) {
        jumpActivity(cls, null);
    }

    protected void jumpActivity(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getContext(), cls);
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
        MToast.show(getContext(), msg);
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
