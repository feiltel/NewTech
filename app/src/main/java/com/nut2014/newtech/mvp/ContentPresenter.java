package com.nut2014.newtech.mvp;

import com.nut2014.newtech.mvp.base.BaseMvpPresenter;

public class ContentPresenter extends BaseMvpPresenter<ContentView> {
    private final ContentModel contentModel;

    public ContentPresenter() {
        this.contentModel = new ContentModel();
    }
    public void login(String userName,String password){
        if (getmMvpView()!=null){
            getmMvpView().showLoad();
        }
        contentModel.loginAct(userName, password, new LoginCallBack() {
            @Override
            public void success(String msg) {
                if (getmMvpView()!=null){
                    getmMvpView().dissLoad();
                    getmMvpView().jumpMain();
                }
            }

            @Override
            public void error(String msg) {
                if (getmMvpView()!=null){
                    getmMvpView().dissLoad();
                    getmMvpView().showToast();
                }
            }
        });
    }
}
