package com.nut2014.newtech.login;


import com.nut2014.baselibrary.base.BaseMvpView;

/**
 * @author feiltel 2020/4/21 0021
 */
public interface LoginView extends BaseMvpView {


    void jump2Main();

    void showToast(String msg);
}