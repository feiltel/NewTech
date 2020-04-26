package com.nut2014.newtech.home;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaeger.library.StatusBarUtil;
import com.nut2014.baselibrary.base.BaseMvpActivity;
import com.nut2014.baselibrary.base.BaseParam;
import com.nut2014.newtech.R;
import com.nut2014.newtech.home.tab1.Tab1Fragment;
import com.nut2014.newtech.home.tab2.Tab2Fragment;
import com.nut2014.newtech.home.tab3.Tab3Fragment;

import butterknife.BindView;

public class HomeActivity extends BaseMvpActivity<HomeView, HomePresenter> implements HomeView {
    private static final String TAG = "MainActivity";
    @BindView(R.id.main_fl)
    FrameLayout main_fl;
    @BindView(R.id.bottom_nv)
    BottomNavigationView bottom_nv;
    private Tab1Fragment tab1Fragment;
    private Tab2Fragment tab2Fragment;
    private Tab3Fragment tab3Fragment;

    @Override
    protected int getViewId() {
        return R.layout.activity_home;
    }

    private int selectPos = 0;

    @Override
    public void initView() {
        Bundle savedInstanceState = getSavedInstanceState();
        if (savedInstanceState!=null){
            selectPos=savedInstanceState.getInt("pos");
        }
        setLightMode();
        StatusBarUtil.setColor(this, getResources().getColor(android.R.color.transparent), 0);
        StatusBarUtil.hideFakeStatusBarView(this);

        if (selectPos==0){
            if (tab1Fragment == null) {
                tab1Fragment = Tab1Fragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fl, tab1Fragment, "f1")
                    .commitNow();
        }else if (selectPos==1){
            if (tab2Fragment == null) {
                tab2Fragment = Tab2Fragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fl, tab2Fragment, "f2")
                    .commitNow();
        }else if (selectPos==2){
            if (tab3Fragment == null) {
                tab3Fragment = Tab3Fragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fl, tab3Fragment, "f3")
                    .commitNow();
        }


        bottom_nv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_blue:
                    selectPos = 0;
                    if (tab1Fragment == null) {
                        tab1Fragment = Tab1Fragment.newInstance();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fl, tab1Fragment, "f1")
                            .commitNow();
                    return true;
                case R.id.yun_ying_center:
                    selectPos = 1;
                    if (tab2Fragment == null) {
                        tab2Fragment = Tab2Fragment.newInstance();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fl, tab2Fragment, "f2")
                            .commitNow();
                    return true;
                case R.id.bottom_navigation_red:
                    selectPos = 2;
                    if (tab3Fragment == null) {
                        tab3Fragment = Tab3Fragment.newInstance();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fl, tab3Fragment, "f3")
                            .commitNow();
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", selectPos);
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public BaseParam getBaseParam() {
        return super.getBaseParam();
    }


}