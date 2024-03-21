package com.example.mytodoapp.Service.Model.TodoModel.DeleteModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodoDeleteModel {



        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("data")
        @Expose
        private DeleteData data;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public DeleteData getData() {
            return data;
        }

        public void setData(DeleteData data) {
            this.data = data;
        }

}
