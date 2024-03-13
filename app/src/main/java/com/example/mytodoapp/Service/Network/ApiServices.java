package com.example.mytodoapp.Service.Network;

import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.Service.Model.RegisterRequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {

    @GET("register")
    Call<RegisterModel> getAllUserData();

    @POST("register")
    Call<RegisterModel> getRegisterUserData(@Body RegisterRequestBody registerRequestBody);


}
