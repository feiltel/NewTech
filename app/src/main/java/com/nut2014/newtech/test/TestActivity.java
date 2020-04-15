package com.nut2014.newtech.test;

import android.view.Menu;
import android.view.MenuItem;

import com.nut2014.newtech.R;
import com.nut2014.baselibrary.base.BaseActivity;
import com.nut2014.baselibrary.base.BaseParam;

public class TestActivity extends BaseActivity {

    @Override
    protected void initView() {
    }

    @Override
    protected void initEvent() {
        setTitle("测试");
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_test;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.show){
            showToast(">>>>>>>>>>>>>>>");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public BaseParam getBaseParam() {
        return super.getBaseParam().setFullScreen(true);
    }
}
