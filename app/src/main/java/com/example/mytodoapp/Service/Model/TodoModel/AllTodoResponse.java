package com.example.mytodoapp.Service.Model.TodoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllTodoResponse {



        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("data")
        @Expose
        private AllTodoArrayData data;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public AllTodoArrayData getData() {
            return data;
        }

        public void setData(AllTodoArrayData data) {
            this.data = data;
        }



}
