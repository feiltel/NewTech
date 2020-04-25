package com.nut2014.newtech.test;

import android.view.Menu;
import android.view.MenuItem;

import com.nut2014.baselibrary.base.BaseMvpActivity;
import com.nut2014.baselibrary.base.BaseParam;
import com.nut2014.newtech.R;

/**
 * @author feiltel 2020/4/16 0016
 */
public class TestActivity extends BaseMvpActivity<TestView, TestPresenter> implements TestView {

    @Override
    protected int getViewId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId() ) {
            case R.id.show:
                showToast(">>>");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_menu, menu);
        return true;
    }


    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    public BaseParam getBaseParam() {
        return new BaseParam().setFullScreen(true);
    }
}