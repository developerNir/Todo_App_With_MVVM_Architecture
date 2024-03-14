package com.example.mytodoapp.Service.Model.LoginModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("user")
        @Expose
        private List<LoginUser> user;
        @SerializedName("token")
        @Expose
        private String token;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public List<LoginUser> getUser() {
            return user;
        }

        public void setUser(List<LoginUser> user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

}
