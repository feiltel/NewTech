package com.nut2014.newtech.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.nut2014.newtech.MainActivity;
import com.nut2014.newtech.R;
import com.nut2014.newtech.mvp.base.BaseMvpActivity;

import java.util.Objects;

public class ContentActivity extends BaseMvpActivity<ContentView,ContentPresenter> implements ContentView{
    /**
     * MVP
     * https://blog.csdn.net/yulong0809/article/details/78622428
     */
    private ProgressBar pb_view;
    private AppCompatEditText user_name;
    private AppCompatEditText password;
    private Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView();
        initEvent();
    }
    @Override
    public void initView() {
        pb_view=findViewById(R.id.pb_view);
        user_name=findViewById(R.id.user_name);
        password=findViewById(R.id.password);
        login_btn=findViewById(R.id.login_btn);
    }

    @Override
    public void initEvent() {
        login_btn.setOnClickListener(v -> {
            String userNameStr= Objects.requireNonNull(user_name.getText()).toString();
            String passwordStr= Objects.requireNonNull(password.getText()).toString();
            getPresenter().login(userNameStr,passwordStr);
        });
    }
    @Override
    protected ContentPresenter createPresenter() {
        return new ContentPresenter(this);
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
    public void showToast() {
        Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
    }


}
