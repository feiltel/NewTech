package com.nut2014.newtech.login;


import com.nut2014.baselibrary.base.BaseMvpPresenter;
import com.nut2014.baselibrary.http.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author feiltel 2020/4/21 0021
 */
public class LoginPresenter extends BaseMvpPresenter<LoginView> {
    public LoginPresenter() {

    }

    public void login(String userName, String password) {
        RetrofitManager.create(ApiService.class).login(userName, password).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean body = response.body();
                getMvpView().jump2Main();
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                getMvpView().jump2Main();
            }
        });
    }
}