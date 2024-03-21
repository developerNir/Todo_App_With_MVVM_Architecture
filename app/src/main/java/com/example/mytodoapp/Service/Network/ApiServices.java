package com.example.mytodoapp.Service.Network;

import com.example.mytodoapp.Service.Model.LoginModel.LoginResponse;
import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.Service.Model.RegisterRequestBody;
import com.example.mytodoapp.Service.Model.TodoModel.AllTodoResponse;
import com.example.mytodoapp.Service.Model.TodoModel.DeleteModel.TodoDeleteModel;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreate;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreateResponse;
import com.example.mytodoapp.Service.Model.UserDeleteModel.DeleteUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServices {


    // User Api ================================call =============
    // Register -------------------------------------------
    @POST("register")
    Call<RegisterModel> getRegisterUserData(@Body RegisterRequestBody registerRequestBody);

    // user verify ----------------------------------------
    @GET("user-verify/{email}/{otp}")
    Call<LoginResponse> getLoginUserData(@Path("email") String email, @Path("otp") String otp);

    // user profile delete -------------------------------
    @DELETE("user-delete")
    Call<DeleteUser> deleteUserProfile(@Header("Authorization") String AuthorizationToken);



    //Todo api call =================================================
    @POST("todo-create")
    Call<TodoCreateResponse> createTodo(@Header("Authorization") String AuthorizationToken, @Body TodoCreate todoCreate);

    // get all Todo ================================================
    @GET("all-todo")
    Call<AllTodoResponse> getAllTodo(@Header("Authorization") String token);

    // Todo delete =================================================
    @DELETE("todo-delete/{id}")
    Call<TodoDeleteModel> deleteTodo(@Header("Authorization")String token, @Path("id")String id);

}
