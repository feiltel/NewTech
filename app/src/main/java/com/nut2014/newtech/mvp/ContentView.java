package com.nut2014.newtech.mvp;

import com.nut2014.newtech.mvp.base.BaseMvpView;

public interface ContentView extends BaseMvpView {
    void showLoad();
    void hideLoad();
    void jumpMain();
    void showToast();
}
