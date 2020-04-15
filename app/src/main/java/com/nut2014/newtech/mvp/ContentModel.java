package com.nut2014.newtech.mvp;



import com.nut2014.baselibrary.utils.JsonUtil;
import com.nut2014.newtech.Constant;
import com.nut2014.baselibrary.base.ResponseBean;
import com.nut2014.baselibrary.utils.OkHttpManager;

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
                    ResponseBean responseBean = JsonUtil.parseObject(string, ResponseBean.class);
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
