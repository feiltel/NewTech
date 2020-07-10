package com.nut2014.newtech.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.jaeger.library.StatusBarUtil;
import com.nut2014.baselibrary.base.BaseMvpActivity;
import com.nut2014.baselibrary.utils.FProgressDialog;
import com.nut2014.newtech.R;
import com.nut2014.newtech.main.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity extends BaseMvpActivity<ContentView, ContentPresenter> implements ContentView {
    /**
     * MVP
     * https://blog.csdn.net/yulong0809/article/details/78622428
     */

    @BindView(R.id.user_name)
    AppCompatEditText user_name;
    @BindView(R.id.password)
    AppCompatEditText password;
    @BindView(R.id.login_btn)
    Button login_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        StatusBarUtil.setTransparent(this);
        ButterKnife.bind(this);
        initEvent();
    }


    public void initEvent() {
        login_btn.setOnClickListener(v -> {
            String userNameStr = Objects.requireNonNull(user_name.getText()).toString();
            String passwordStr = Objects.requireNonNull(password.getText()).toString();
            getPresenter().login(userNameStr, passwordStr);
        });
    }


    @Override
    protected ContentPresenter createPresenter() {
        return new ContentPresenter();
    }

    @Override
    public void showLoad() {
        FProgressDialog.getInstance().show(this, "登录中", false);
    }

    @Override
    public void hideLoad() {
        FProgressDialog.getInstance().dismiss();
    }

    @Override
    public void jumpMain() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showInfo(String msg) {
        showToast(msg);
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.user_name, R.id.password};
        return ids;
    }
}
