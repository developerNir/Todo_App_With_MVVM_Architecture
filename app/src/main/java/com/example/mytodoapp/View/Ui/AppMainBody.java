package com.example.mytodoapp.View.Ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytodoapp.R;
import com.example.mytodoapp.Service.Model.LoginModel.LoginUser;
import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.ViewModel.UserViewModel;

import java.util.List;

public class AppMainBody extends AppCompatActivity {


    TextView textView;
    UserViewModel userViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_main_body);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        textView = findViewById(R.id.textView3);


        // view Model Provider set -------------------------------------
        userViewModel = new ViewModelProvider(AppMainBody.this).get(UserViewModel.class);


        // Live data check and Null save ----------------------------
        LiveData<List<LoginUser>> loginLiveData = userViewModel.getLoginLiveData();
        if (loginLiveData != null) {
            userViewModel.getLoginLiveData().observe(this, new Observer<List<LoginUser>>() {
                @Override
                public void onChanged(List<LoginUser> loginUser) {
                    Log.d("myLog", "Login Info == " + loginUser.get(0).getName());
                    textView.setText(loginUser.get(0).getName());
                }
            });
        }else {
            Toast.makeText(this, "Live data is null", Toast.LENGTH_SHORT).show();
        }


        // get Token form View model --------------------------------
        LiveData<RegisterModel> tokenAndRegisterData = userViewModel.getTokenWithRegister();
        if (tokenAndRegisterData !=null){
            userViewModel.getTokenWithRegister().observe(this, new Observer<RegisterModel>() {
                @Override
                public void onChanged(RegisterModel registerModel) {

                    if (registerModel.getSuccess()){
                        textView.setText(registerModel.getToken());
                    }



                }
            });
        }

















    }
}