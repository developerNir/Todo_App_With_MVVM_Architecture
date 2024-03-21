package com.example.mytodoapp.Service.Model.TodoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllTodoArrayData {


        @SerializedName("allTodo")
        @Expose
        private List<AllTodoData> allTodo;

        public List<AllTodoData> getAllTodo() {
            return allTodo;
        }

        public void setAllTodo(List<AllTodoData> allTodo) {
            this.allTodo = allTodo;
        }


}
