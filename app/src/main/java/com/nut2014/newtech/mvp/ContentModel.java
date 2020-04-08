package com.nut2014.newtech.mvp;



import com.alibaba.fastjson.JSON;
import com.nut2014.newtech.Constant;
import com.nut2014.newtech.base.ResponseBean;
import com.nut2014.newtech.utils.OkHttpManager;

public class ContentModel {
    public interface LoginCallBack {
        void success();
        void error(String msg);
    }

    public static void loginRequest(String userName, String password, LoginCallBack callback) {
        String url = Constant.baseUrl + "login?userName=" + userName + "&password=" + password;
        OkHttpManager.getAsyn(url, new OkHttpManager.ResultCallback() {
            @Override
            public void onError(Exception e) {
                if (callback != null) {
                    callback.error(e.getMessage());
                }
            }

            @Override
            public void onResponse(String string) {
                if (callback != null) {
                    ResponseBean responseBean = JSON.parseObject(string, ResponseBean.class);
                    if (responseBean.getCode() == 1) {
                        callback.success();
                    } else {
                        callback.error(responseBean.getMsg());
                    }
                }
            }
        });
    }
}
