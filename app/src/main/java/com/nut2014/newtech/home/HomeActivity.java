package com.nut2014.newtech.home;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaeger.library.StatusBarUtil;
import com.nut2014.baselibrary.base.BaseMvpActivity;
import com.nut2014.baselibrary.base.BaseParam;
import com.nut2014.baselibrary.utils.FileSizeUtil;
import com.nut2014.baselibrary.utils.MToast;
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
    private final Tab1Fragment fragment1 = Tab1Fragment.newInstance();
    private final Tab2Fragment fragment2 = Tab2Fragment.newInstance();
    private final Tab3Fragment fragment3 = Tab3Fragment.newInstance();
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment activeFragment = fragment1;


    @Override
    protected int getViewId() {
        return R.layout.activity_home;
    }


    @Override
    public void initView() {
        Bundle savedInstanceState = getSavedInstanceState();

        setLightMode();
        StatusBarUtil.setColor(this, getResources().getColor(android.R.color.transparent), 0);
        StatusBarUtil.hideFakeStatusBarView(this);

        fm.beginTransaction().add(R.id.main_fl, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_fl, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_fl,fragment1, "1").commit();
        bottom_nv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_blue:
                    selectFragment(0);
                    return true;
                case R.id.yun_ying_center:
                    selectFragment(1);
                    return true;
                case R.id.bottom_navigation_red:
                    selectFragment(2);
                    return true;
            }
            return false;
        });
        MToast.show(this, FileSizeUtil.FormetFileSize(459952));
    }

    private void selectFragment(int selectPos) {
        System.out.println(selectPos);
        if (selectPos == 0) {
            fm.beginTransaction().hide(activeFragment).show(fragment1).commit();
            activeFragment = fragment1;
        } else if (selectPos == 1) {
            fm.beginTransaction().hide(activeFragment).show(fragment2).commit();
            activeFragment = fragment2;
        } else if (selectPos == 2) {
            fm.beginTransaction().hide(activeFragment).show(fragment3).commit();
            activeFragment = fragment3;
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //  outState.putInt("pos", selectPos);
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