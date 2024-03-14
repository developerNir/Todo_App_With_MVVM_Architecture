package com.example.mytodoapp.Service.Network;

import com.example.mytodoapp.Service.Model.LoginModel.LoginResponse;
import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.Service.Model.RegisterRequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServices {

    @GET("register")
    Call<RegisterModel> getAllUserData();

    // Register -------------------------------------------
    @POST("register")
    Call<RegisterModel> getRegisterUserData(@Body RegisterRequestBody registerRequestBody);

    // user verify ----------------------------------------
    @GET("user-verify/{email}/{otp}")
    Call<LoginResponse> getLoginUserData(@Path("email") String email, @Path("otp") String otp);

}
