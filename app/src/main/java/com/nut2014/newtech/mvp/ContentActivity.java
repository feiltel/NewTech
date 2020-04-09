package com.nut2014.newtech.mvp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatEditText;

import com.nut2014.newtech.R;
import com.nut2014.newtech.base.BaseMvpActivity;
import com.nut2014.newtech.main.MainActivity;
import com.nut2014.newtech.utils.MToast;

import java.util.Objects;

import butterknife.BindView;

public class ContentActivity extends BaseMvpActivity<ContentView, ContentPresenter> implements ContentView {
    /**
     * MVP
     * https://blog.csdn.net/yulong0809/article/details/78622428
     */
    @BindView(R.id.pb_view)
    ProgressBar pb_view;
    @BindView(R.id.user_name)
    AppCompatEditText user_name;
    @BindView(R.id.password)
    AppCompatEditText password;
    @BindView(R.id.login_btn)
    Button login_btn;


    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
        login_btn.setOnClickListener(v -> {
            String userNameStr = Objects.requireNonNull(user_name.getText()).toString();
            String passwordStr = Objects.requireNonNull(password.getText()).toString();
            getPresenter().login(userNameStr, passwordStr);
        });
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_content;
    }

    @Override
    protected ContentPresenter createPresenter() {
        return new ContentPresenter();
    }

    @Override
    public void showLoad() {
        pb_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoad() {
        pb_view.setVisibility(View.GONE);
    }

    @Override
    public void jumpMain() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showInfo(String msg) {
        showToast(msg);
    }


}
