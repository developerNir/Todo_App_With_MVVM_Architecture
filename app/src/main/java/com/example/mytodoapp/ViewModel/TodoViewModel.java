package com.example.mytodoapp.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytodoapp.Service.Model.TodoModel.TodoCreate;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreateResponse;
import com.example.mytodoapp.Service.Reposetory.TodoRepository;
import com.example.mytodoapp.Service.Reposetory.UserRepository;

import java.io.Closeable;

public class TodoViewModel extends ViewModel {


    private final TodoRepository todoRepository;


    // ======================= todo view model and todo repo add ===================
    public TodoViewModel() {
        super();
        todoRepository = TodoRepository.getTodoRepositoryInstance();
    }

    // create todo response Live data =========================================
    public LiveData<TodoCreateResponse> getCreateTodoResponse(){
        return todoRepository.TodoCreateResponse();
    }

    // create todo api call ====================================
    public void createTodo(String token ,TodoCreate todoCreate){
        todoRepository.createTodo(token, todoCreate);
    }





}
