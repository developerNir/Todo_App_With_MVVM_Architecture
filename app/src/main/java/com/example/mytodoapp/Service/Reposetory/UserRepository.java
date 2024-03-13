package com.example.mytodoapp.Service.Reposetory;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.Service.Model.RegisterRequestBody;
import com.example.mytodoapp.Service.Model.UserData;
import com.example.mytodoapp.Service.Network.ApiServices;
import com.example.mytodoapp.Service.Network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository implements UserRepoInterface {


    private static UserRepository userRepository;
    private RegisterRequestBody registerRequestBody;
    private RegisterModel registerModel;
    private UserData userData;
    private MutableLiveData UserLiveData;


    public static UserRepository getUserRepositoryInstance(){
        if (userRepository == null){
            userRepository = new UserRepository();
        }
        return userRepository;
    }



    @Override
    public MutableLiveData<UserData> getUserInfo() {

        // live data instance create --------------------------
        if (UserLiveData == null){
            UserLiveData = new MutableLiveData<>();
        }


        registerRequestBody = new RegisterRequestBody("nira","948375983","loveyoupal16@gmail.com");



        // Api call --------------------------------------------
        ApiServices apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        // get data use model Class ----------------------------
        Call<RegisterModel> call = apiServices.getRegisterUserData(registerRequestBody);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                 if (response.isSuccessful()){
                     registerModel = response.body();
                     userData = registerModel.getUser();
                     UserLiveData.postValue(userData);

                 }



            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Log.d("myLog", "onFailure: Api Call Failure"+t.getMessage());
            }
        });



        return UserLiveData;
    }
}
