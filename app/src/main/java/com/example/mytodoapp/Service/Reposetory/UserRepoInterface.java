package com.example.mytodoapp.Service.Reposetory;



import com.example.mytodoapp.Service.Model.RegisterRequestBody;


public interface UserRepoInterface {

    public void getUserInfoFromApi(RegisterRequestBody registerRequestBody);

    public void callApiForLogin(String email, String otp);

    public void UserAccountDelete(String token);



}
