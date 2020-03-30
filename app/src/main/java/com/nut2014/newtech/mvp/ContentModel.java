package com.nut2014.newtech.mvp;


import android.os.Handler;

public class ContentModel {
    public void loginAct(String userName, String passWord, LoginCallBack callBack) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userName.equals(passWord)) {
                    callBack.success("user");
                } else {
                    callBack.error("error");
                }
            }
        },3000);

    }
}
