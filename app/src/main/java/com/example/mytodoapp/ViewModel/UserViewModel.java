package com.example.mytodoapp.ViewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytodoapp.Service.Model.RegisterRequestBody;
import com.example.mytodoapp.Service.Model.UserData;
import com.example.mytodoapp.Service.Reposetory.UserRepository;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;

    public UserViewModel() {
        super();
        userRepository = UserRepository.getUserRepositoryInstance();
    }

    public LiveData<UserData> getMutableLiveData(){

        return userRepository.getUserInfo();

    }



}
