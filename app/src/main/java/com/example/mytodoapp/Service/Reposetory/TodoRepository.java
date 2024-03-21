package com.example.mytodoapp.Service.Reposetory;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mytodoapp.Service.Model.TodoModel.AllTodoArrayData;
import com.example.mytodoapp.Service.Model.TodoModel.AllTodoData;
import com.example.mytodoapp.Service.Model.TodoModel.AllTodoResponse;
import com.example.mytodoapp.Service.Model.TodoModel.DeleteModel.TodoDeleteModel;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreate;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreateResponse;
import com.example.mytodoapp.Service.Network.ApiServices;
import com.example.mytodoapp.Service.Network.RetrofitInstance;

import java.util.List;

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


    // Get all todo list ===================================================

    private MutableLiveData<List<AllTodoData>> allTodoResponseMutableLiveData;

    public AllTodoResponse allTodoResponse;
    private List<AllTodoData> allTodoData;
    private AllTodoArrayData allTodoArrayData;

    // todo response live data all todo ----------------------------------
    public MutableLiveData<List<AllTodoData>> getAllTodoResponseMutableLiveData() {
        return allTodoResponseMutableLiveData;
    }

    // all todo api call method ----------------------------------------
    @Override
    public void getAllTodoList(String token) {

        if (allTodoResponseMutableLiveData== null){
            allTodoResponseMutableLiveData = new MutableLiveData<>();
        }

        apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);

        Call<AllTodoResponse>call = apiServices.getAllTodo(token);
        call.enqueue(new Callback<AllTodoResponse>() {
            @Override
            public void onResponse(Call<AllTodoResponse> call, Response<AllTodoResponse> response) {

                Log.d("myLog", "onResponse: get all todo "+response.body());
                if (response.isSuccessful()){
                    allTodoResponse = response.body();
                    allTodoArrayData = allTodoResponse.getData();
                    allTodoData = allTodoArrayData.getAllTodo();
                    allTodoResponseMutableLiveData.postValue(allTodoData);
                }else {
                    Log.d("myLog", "onResponse: get api todo all call fail "+response);
                }


            }

            @Override
            public void onFailure(Call<AllTodoResponse> call, Throwable t) {

                Log.d("myLog", "onFailure: get all todo api "+t.getMessage());

            }
        });

    }

    // Todo delete ====================== Delete Api call ======================

    private TodoDeleteModel todoDeleteModel;
    private MutableLiveData<TodoDeleteModel> deleteModelMutableLiveData;

    public MutableLiveData<TodoDeleteModel> getResponseDeleteTodo(){
        return deleteModelMutableLiveData;
    }

    public void deleteTodoApiCall( String token ,String id){
        if (deleteModelMutableLiveData == null){
            deleteModelMutableLiveData = new MutableLiveData<>();
        }


        apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);

        Call<TodoDeleteModel> call = apiServices.deleteTodo(token, id);

        call.enqueue(new Callback<TodoDeleteModel>() {
            @Override
            public void onResponse(Call<TodoDeleteModel> call, Response<TodoDeleteModel> response) {

                if (response.isSuccessful()) {
                    todoDeleteModel = response.body();

                    deleteModelMutableLiveData.postValue(todoDeleteModel);
                }

            }

            @Override
            public void onFailure(Call<TodoDeleteModel> call, Throwable t) {
                Log.d("myLog", "onFailure: Todo Delete api call "+t.getMessage());
            }
        });



    }




}
