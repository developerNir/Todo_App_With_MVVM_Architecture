package com.example.mytodoapp.Service.Reposetory;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mytodoapp.Service.Model.LoginModel.LoginResponse;
import com.example.mytodoapp.Service.Model.LoginModel.LoginUser;
import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.Service.Model.RegisterRequestBody;
import com.example.mytodoapp.Service.Model.UserData;
import com.example.mytodoapp.Service.Model.UserDeleteModel.DeleteUser;
import com.example.mytodoapp.Service.Network.ApiServices;
import com.example.mytodoapp.Service.Network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository implements UserRepoInterface {


    private static UserRepository userRepository;
    private RegisterModel registerModel;
    private UserData userData;
    private ApiServices apiServices;
    private MutableLiveData<UserData> UserLiveData;
    private MutableLiveData<RegisterModel> RegisterDataWithToke;


    public static UserRepository getUserRepositoryInstance(){
        if (userRepository == null){
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public MutableLiveData<UserData> getLiveUserData(){
        // live data return value in the ViewModel
        return UserLiveData;
    }

    // with Token register ------------------------
    public MutableLiveData<RegisterModel> getTokenRegisterData(){
        return RegisterDataWithToke;
    }


    @Override
    public void getUserInfoFromApi(RegisterRequestBody registerRequestBody) {

        // live data instance create --------------------------
        if (UserLiveData == null){
            UserLiveData = new MutableLiveData<>();
        }

        // with token ----------Live Data---------------------
        if (RegisterDataWithToke == null){
            RegisterDataWithToke = new MutableLiveData<>();
        }




        // Api call --------------------------------------------
        apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        // get data use model Class ----------------------------
        Call<RegisterModel> call = apiServices.getRegisterUserData(registerRequestBody);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                 if (response.isSuccessful()){
                     registerModel = response.body();
                     RegisterDataWithToke.postValue(registerModel);
                     userData = registerModel.getUser();
                     // Live data post Value ------------------
                     UserLiveData.postValue(userData);
                 }


            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Log.d("myLog", "onFailure: Api Call Failure"+t.getMessage());
            }
        });


    }

    // login ==============================================

    private LoginResponse loginResponse;
    private List<LoginUser> loginUser;
    private MutableLiveData<LoginResponse> loginResponseMutableLiveData;
    private MutableLiveData loginLiveData;

    public MutableLiveData<List<LoginUser>> getLoginUserLiveData(){
        return loginLiveData;
    }

    public MutableLiveData<LoginResponse> getLoginResponseMutableLiveData(){
        return loginResponseMutableLiveData;
    }

    @Override
    public void callApiForLogin(String email, String otp) {

        if (loginLiveData == null){
            loginLiveData = new MutableLiveData<>();
        }

        if (loginResponseMutableLiveData == null){
            loginResponseMutableLiveData = new MutableLiveData<>();
        }



        // create Retrofit Instance ---------------------
         apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        // call -----------------------
        Call<LoginResponse> call = apiServices.getLoginUserData(email, otp);

        // call api ----------------------
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("myLog", "onResponse: "+response.body());

                if (response.isSuccessful()){
                    loginResponse = response.body();
                    loginResponseMutableLiveData.postValue(loginResponse);
                    loginUser = loginResponse.getUser();
                    loginLiveData.postValue(loginUser);
                }


            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("myLog", "onFailure: api Login"+t.getMessage());
            }
        });


    }




    // user Account Delete ------------------------------------------------

    private MutableLiveData<DeleteUser> deleteUserLiveData;
    private DeleteUser deleteUser;


    public MutableLiveData<DeleteUser> getDeleteUser(){
        return deleteUserLiveData;
    }

    @Override
    public void UserAccountDelete(String token) {

        if (deleteUserLiveData == null){
            deleteUserLiveData = new MutableLiveData<>();
        }

        apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);

        Call<DeleteUser> call = apiServices.deleteUserProfile(token);

        call.enqueue(new Callback<DeleteUser>() {
            @Override
            public void onResponse(Call<DeleteUser> call, Response<DeleteUser> response) {

                Log.d("myLog", "onResponse: Api User Account delete"+response);
                deleteUser = response.body();
                deleteUserLiveData.postValue(deleteUser);


            }

            @Override
            public void onFailure(Call<DeleteUser> call, Throwable t) {
                Log.d("myLog", "onFailure: User Delete "+t.getMessage());
            }
        });



    }


}
