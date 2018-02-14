package com.example.faizan.fuelapp;


import com.example.faizan.fuelapp.ForgetPasswordPOJO.ForgetPasswordBean;
import com.example.faizan.fuelapp.LoginPOJO.LoginBean;
import com.example.faizan.fuelapp.OtpPOJO.OtpBean;
import com.example.faizan.fuelapp.RegisterPOJO.RegisterBean;
import com.example.faizan.fuelapp.ResetPasswordPOJO.ResetPasswordBean;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by USER on 11/30/2017.
 */

public interface Allapi {


    @Multipart
    @POST("fuel/api/sign_up.php")
    Call<RegisterBean> register(
            @Part("name") String m,
            @Part("email") String P,
            @Part("password") String o,
            @Part("phone") String n
    );

    @Multipart
    @POST("fuel/api/varify_code.php")
    Call<OtpBean> verify(
            @Part("userId") String m,
            @Part("otp") String P
    );

    @Multipart
    @POST("fuel/api/mobile_signin.php")
    Call<LoginBean> login(
            @Part("phone") String m,
            @Part("password") String P
    );

    @Multipart
    @POST("fuel/api/forget-password.php")
    Call<ForgetPasswordBean> forget(
            @Part("phone") String m

    );

    @Multipart
    @POST("fuel/api/reset-password.php")
    Call<ResetPasswordBean> reset(
            @Part("resetCode") String m,
            @Part("phone") String n,
            @Part("newPassword") String o

    );

}
