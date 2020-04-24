package com.nut2014.newtech.login;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author feiltel 2020/4/21 0021
 */
public interface ApiService {
    @POST("login")
    Call<LoginBean> login(@Query("username") String username, @Query("password") String password);

}
