package com.nut2014.newtech.login;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.jaeger.library.StatusBarUtil;
import com.nut2014.baselibrary.base.BaseMvpActivity;
import com.nut2014.baselibrary.utils.MToast;
import com.nut2014.newtech.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author feiltel 2020/4/21 0021
 */
public class LoginActivity extends BaseMvpActivity<LoginView, LoginPresenter> implements LoginView {
    @BindView(R.id.user_name)
    AppCompatEditText user_name;
    @BindView(R.id.password)
    AppCompatEditText password;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.toolbar_root)
    ConstraintLayout toolbar_root;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.info_iv)
    ImageView info_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTransparent(this);
        ButterKnife.bind(this);
        initView();
    }


    public void initView() {
        setLightMode();
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        info_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    public void initEvent() {
        login_btn.setOnClickListener(v -> {
            String userNameStr = Objects.requireNonNull(user_name.getText()).toString();
            String passwordStr = Objects.requireNonNull(password.getText()).toString();
            getPresenter().login(userNameStr, passwordStr);
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    public void jump2Main() {
        super.onBackPressed();
    }

    @Override
    public void showToast(String msg) {
        MToast.show(this, msg);
    }
}