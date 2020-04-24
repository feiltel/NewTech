package com.nut2014.newtech.login;

import com.nut2014.baselibrary.utils.SpManager;
import com.nut2014.newtech.MyApp;


/**
 * @author feiltel 2020/4/22 0022
 */
public class UserDataUtil {
    public static void saveAuthKey(String auth) {
        SpManager.getInstance().setString(MyApp.context, "auth", "Authorization", auth);
    }
    public static String getAuthKey() {
       return SpManager.getInstance().getString(MyApp.context, "auth", "Authorization");
    }
}
