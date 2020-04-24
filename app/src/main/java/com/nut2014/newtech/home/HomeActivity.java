package com.nut2014.newtech.home;

import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nut2014.baselibrary.base.BaseMvpActivity;
import com.nut2014.baselibrary.base.BaseParam;
import com.nut2014.newtech.R;
import com.nut2014.newtech.home.tab1.Tab1Fragment;

import butterknife.BindView;

public class HomeActivity extends BaseMvpActivity<HomeView, HomePresenter> implements HomeView {
    private static final String TAG = "MainActivity";
    @BindView(R.id.main_fl)
    FrameLayout main_fl;
    @BindView(R.id.bottom_nv)
    BottomNavigationView bottom_nv;
    private Tab1Fragment tab1 = null;
    private Tab1Fragment tab2 = null;
    private Tab1Fragment tab3 = null;
    @Override
    protected int getViewId() {
        return R.layout.activity_tab1;
    }

    @Override
    public void initView() {
        setLightMode();
        tab1 = new Tab1Fragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fl, tab1, "f1")
                .commit();

        bottom_nv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_blue:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fl, tab1, "f1")
                            .commit();
                    return true;
                case R.id.yun_ying_center:
                    if (tab2 == null) {
                        tab2 = new Tab1Fragment();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fl, tab2, "f2")
                            .commit();
                    return true;
                case R.id.bottom_navigation_red:
                    if (tab3 == null) {
                        tab3 = new Tab1Fragment();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fl, tab3, "f3")
                            .commit();
                    return true;
            }
            return false;
        });
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
        return super.getBaseParam().setFullScreen(true);
    }
}