package com.nut2014.newtech.mvp;


import com.nut2014.newtech.retrofit.ResponseBean;
import com.nut2014.newtech.retrofit.RetrofitHelper;
import com.nut2014.newtech.retrofit.RetrofitService;

import io.reactivex.Observable;

public class ContentModel {
    private static ContentModel manager;
    public static ContentModel getInstance() {
        if (manager == null) {
            synchronized (ContentModel.class) {
                if (manager == null) {
                    manager = new ContentModel();
                }
            }
        }
        return manager;
    }
    private RetrofitService retrofitService;

    public ContentModel() {
        retrofitService = RetrofitHelper.getInstance().getApiService(RetrofitService.class);
    }

    public Observable<ResponseBean> loginAct(String userName, String password) {
        return retrofitService.login(userName, password);
    }

}
