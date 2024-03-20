package com.example.mytodoapp.Service.Model.TodoModel;

public class TodoCreate {


    public String title;
    public String description;
    public String priority;

    public TodoCreate(String title, String description, String priority, String setTime) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.setTime = setTime;
    }

    public String setTime;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSetTime() {
        return setTime;
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }



}
