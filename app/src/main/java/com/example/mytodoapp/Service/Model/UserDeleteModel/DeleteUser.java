package com.example.mytodoapp.Service.Model.UserDeleteModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteUser {


        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("massage")
        @Expose
        private String massage;
        @SerializedName("data")
        @Expose
        private UserDeleteModel data;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMassage() {
            return massage;
        }

        public void setMassage(String massage) {
            this.massage = massage;
        }

        public UserDeleteModel getData() {
            return data;
        }

        public void setData(UserDeleteModel data) {
            this.data = data;
        }




}
