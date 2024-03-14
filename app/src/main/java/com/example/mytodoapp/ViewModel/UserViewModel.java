package com.example.mytodoapp.ViewModel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytodoapp.Service.Model.LoginModel.LoginUser;
import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.Service.Model.RegisterRequestBody;
import com.example.mytodoapp.Service.Model.UserData;
import com.example.mytodoapp.Service.Reposetory.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;



    public UserViewModel() {
        super();
        userRepository = UserRepository.getUserRepositoryInstance();
    }

    // create User Use Register -------------------------------
    public void createUserRegister(RegisterRequestBody registerRequestBody){
        // this is Api call method call from ViewModel class
        userRepository.getUserInfoFromApi(registerRequestBody);

    }

    public LiveData<UserData> getLiveData(){
        // this is returnable Live data get from Repository -------
        return userRepository.getLiveUserData();
    }

    public LiveData<RegisterModel> getTokenWithRegister(){
        return userRepository.getTokenRegisterData();
    }

    // login ====================================================

    // login with Verify ---------------------------------
    public void loginVerify(String email, String otp){
        userRepository.callApiForLogin(email, otp);
    }

    public LiveData<List<LoginUser>> getLoginLiveData(){
        Log.d("myLog", "getLoginLiveData: +"+userRepository.getLoginUserLiveData());
        return userRepository.getLoginUserLiveData();
    }





}
