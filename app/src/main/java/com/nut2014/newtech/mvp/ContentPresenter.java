package com.nut2014.newtech.mvp;

import com.nut2014.baselibrary.base.BaseMvpPresenter;

public class ContentPresenter extends BaseMvpPresenter<ContentView> {


    public ContentPresenter() {

    }

    public void login(String userName, String password) {
        if (getMvpView() != null) {
            getMvpView().showLoad();
        }
        ContentModel.loginRequest(userName, password, new ContentModel.LoginCallBack() {
            @Override
            public void success() {
                if (getMvpView() != null) {
                    getMvpView().hideLoad();
                    getMvpView().jumpMain();
                }
            }

            @Override
            public void error(String msg) {
                if (getMvpView() != null) {
                    getMvpView().hideLoad();
                    getMvpView().showInfo(msg);
                }
            }
        });
    }

}
