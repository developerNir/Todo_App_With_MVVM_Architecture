package com.example.mytodoapp.Service.Reposetory;

import com.example.mytodoapp.Service.Model.TodoModel.TodoCreate;

public interface TodoRepositoryInterface {


    public void createTodo(String token, TodoCreate todoCreate);


}
