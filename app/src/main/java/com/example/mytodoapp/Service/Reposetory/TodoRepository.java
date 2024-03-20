package com.example.mytodoapp.Service.Reposetory;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mytodoapp.Service.Model.TodoModel.TodoCreate;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreateResponse;
import com.example.mytodoapp.Service.Network.ApiServices;
import com.example.mytodoapp.Service.Network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TodoRepository implements TodoRepositoryInterface{

    private static TodoRepository todoRepository;
    private ApiServices apiServices;
    private TodoCreateResponse todoCreateResponse;
    private MutableLiveData<TodoCreateResponse> todoCreateResponseMutableLiveData;




    // create Instance ------ todo Repository --------------------
    public static TodoRepository getTodoRepositoryInstance(){
        if ( todoRepository== null){
            todoRepository = new TodoRepository();
        }
        return todoRepository;
    }


    // create todo response live data ----------------------------------------
    public MutableLiveData<TodoCreateResponse> TodoCreateResponse(){
        return todoCreateResponseMutableLiveData;
    }


    // create todo api call ==============================================
    public void createTodo(String token, TodoCreate todoCreate){


        if (todoCreateResponseMutableLiveData ==null){
            todoCreateResponseMutableLiveData = new MutableLiveData<>();
        }


        // call api ------------------------------------------
        apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);

        Call<TodoCreateResponse> call = apiServices.createTodo(token, todoCreate);
        call.enqueue(new Callback<TodoCreateResponse>() {
            @Override
            public void onResponse(Call<TodoCreateResponse> call, Response<TodoCreateResponse> response) {
                if (response.isSuccessful()) {
                    todoCreateResponse = response.body();
                    todoCreateResponseMutableLiveData.postValue(todoCreateResponse);
                }

            }

            @Override
            public void onFailure(Call<TodoCreateResponse> call, Throwable t) {

                Log.d("myLog", "onFailure: "+t.getMessage());


            }
        });


    }







}
