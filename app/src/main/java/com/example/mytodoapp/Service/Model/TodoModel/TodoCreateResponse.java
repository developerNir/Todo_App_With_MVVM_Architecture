package com.example.mytodoapp.Service.Model.TodoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodoCreateResponse {



        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("data")
        @Expose
        private todoData data;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public todoData getData() {
            return data;
        }

        public void setData(todoData data) {
            this.data = data;
        }



}
