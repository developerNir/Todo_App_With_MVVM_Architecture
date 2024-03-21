package com.example.mytodoapp.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytodoapp.Service.Model.TodoModel.AllTodoData;
import com.example.mytodoapp.Service.Model.TodoModel.AllTodoResponse;
import com.example.mytodoapp.Service.Model.TodoModel.DeleteModel.TodoDeleteModel;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreate;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreateResponse;
import com.example.mytodoapp.Service.Reposetory.TodoRepository;
import com.example.mytodoapp.Service.Reposetory.UserRepository;

import java.io.Closeable;
import java.util.List;

public class TodoViewModel extends ViewModel {


    private final TodoRepository todoRepository;


    // ======================= todo view model and todo repo add ===================
    public TodoViewModel() {
        super();
        todoRepository = TodoRepository.getTodoRepositoryInstance();
    }

    // create todo response Live data ===================create Todo======================
    public LiveData<TodoCreateResponse> getCreateTodoResponse(){
        return todoRepository.TodoCreateResponse();
    }

    // create todo api call ====================================
    public void createTodo(String token ,TodoCreate todoCreate){
        todoRepository.createTodo(token, todoCreate);
    }

    // get all Todo  ==============================Get All Todo =========================
    public LiveData<List<AllTodoData>> getAllTodoDataFromLiveData(){
        return todoRepository.getAllTodoResponseMutableLiveData();
    }

    // api call todo all ===========================================
    public void getAllTodoApiCall(String token){
        todoRepository.getAllTodoList(token);
    }

    // delete Todo ==================================================

    public LiveData<TodoDeleteModel> getResponseDeleteTodo(){
        return todoRepository.getResponseDeleteTodo();
    }
    public void ApiCallDeleteTodo(String token, String id){
        todoRepository.deleteTodoApiCall(token, id);
    }





}
