package com.nut2014.newtech.mvp.base;

public abstract class BaseMvpPresenter<V extends BaseMvpView> {

    private V mMvpView;

    /**
     * 绑定V层
     * @param view
     */
    public void attachMvpView(V view){
        this.mMvpView = view;
    }

    /**
     * 解除绑定V层
     */
    public void detachMvpView(){
        mMvpView = null;
    }

    /**
     * 获取V层
     * @return
     */
    public V getMvpView() {
        return mMvpView;
    }
}
