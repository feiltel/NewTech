package com.nut2014.newtech.login;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nut2014.baselibrary.base.BaseMvpActivity;
import com.nut2014.baselibrary.base.BaseParam;
import com.nut2014.newtech.R;

import java.util.Objects;

import butterknife.BindView;

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
    protected int getViewId() {
        return R.layout.activity_login;
    }

    @Override
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
    @Override
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
    public BaseParam getBaseParam() {
        return super.getBaseParam().setTransparent(true);
    }

    @Override
    public void jump2Main() {
        super.onBackPressed();
    }
}