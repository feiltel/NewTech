package com.nut2014.newtech.mvp;


import com.nut2014.baselibrary.base.BaseResponse;
import com.nut2014.baselibrary.http.OkHttpManager;
import com.nut2014.baselibrary.utils.FLog;
import com.nut2014.baselibrary.utils.JsonUtil;
import com.nut2014.newtech.Constant;

public class ContentModel {
    private static final String TAG = "ContentModel";

    public static void loginRequest(String phone, String veriCode, LoginCallBack callback) {
        String url = Constant.baseUrl + "/loginWeb?phone=" + phone + "&veriCode=" + veriCode;

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
                    FLog.d(TAG, string);
                    BaseResponse responseBean = JsonUtil.parseBaseResponse(string, BaseResponse.class);
                    if (responseBean != null && responseBean.getCode() == 1) {
                        callback.success();
                    } else {
                        callback.error(responseBean.getMsg());
                    }
                }
            }
        });
    }

    public interface LoginCallBack {
        void success();

        void error(String msg);
    }
}
