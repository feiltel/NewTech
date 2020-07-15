package com.nut2014.newtech.login;

import com.nut2014.baselibrary.base.BaseResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author feiltel 2020/4/21 0021
 */
public interface ApiService {
    @POST("/user/loginWeb")
    Call<BaseResponse<User>> login(@Query("phone") String username, @Query("veriCode") String password);

}
