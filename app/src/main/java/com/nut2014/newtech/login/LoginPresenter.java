package com.nut2014.newtech.login;


import com.nut2014.baselibrary.base.BaseMvpPresenter;
import com.nut2014.baselibrary.base.BaseResponse;
import com.nut2014.baselibrary.http.RetrofitManager;
import com.nut2014.baselibrary.utils.FLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author feiltel 2020/4/21 0021
 */
public class LoginPresenter extends BaseMvpPresenter<LoginView> {
    private static String TAG = "LoginPresenter";
    public LoginPresenter() {

    }

    public void login(String userName, String password) {
        RetrofitManager.create(ApiService.class).login(userName, password).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                LoginView mvpView = getMvpView();
                if (mvpView != null) {
                    FLog.d(TAG, "onResponse");
                    BaseResponse<User> body = response.body();
                    if (body != null) {
                        if (body.getCode() == 1) {
                            mvpView.jump2Main();
                        }
                        mvpView.showToast(body.getMsg());
                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                LoginView mvpView = getMvpView();
                if (mvpView != null) {
                    mvpView.showToast(t.getMessage());
                    FLog.d(TAG, "onFailure");
                }
            }
        });
    }
}