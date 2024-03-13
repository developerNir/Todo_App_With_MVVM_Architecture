package com.example.mytodoapp.Service.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("user")
        @Expose
        private UserData userData;
        @SerializedName("token")
        @Expose
        private String token;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public UserData getUser() {
            return userData;
        }

        public void setUser(UserData user) {
            this.userData = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }


}
