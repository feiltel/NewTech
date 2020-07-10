package com.nut2014.newtech.test;


import com.nut2014.baselibrary.base.BaseMvpActivity;

/**
 * @author feiltel 2020/7/10 0010
 */
public class TestActivity extends BaseMvpActivity<TestView, TestPresenter> implements TestView {
    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

}