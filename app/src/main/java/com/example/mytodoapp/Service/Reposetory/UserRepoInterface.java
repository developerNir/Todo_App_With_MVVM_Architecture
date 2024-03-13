package com.example.mytodoapp.Service.Reposetory;

import androidx.lifecycle.MutableLiveData;

import com.example.mytodoapp.Service.Model.UserData;

import java.util.List;

public interface UserRepoInterface {

    public MutableLiveData<UserData> getUserInfo();

}
