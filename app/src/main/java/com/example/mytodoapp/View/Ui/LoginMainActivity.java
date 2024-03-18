package com.example.mytodoapp.View.Ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.example.mytodoapp.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoginMainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    TextView textView, textNextPageMove;
    Button loginButton;
    TextInputEditText emailEdText, otpEdText;
    TextInputLayout EmailInputLayout, OtpInputLayout;
    ProgressBar progressBar;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        textView = findViewById(R.id.textView2);

        // login Element introduce -------------------------
        loginButton = findViewById(R.id.ButtonLogin);

        emailEdText = findViewById(R.id.emailEditText);
        otpEdText = findViewById(R.id.OtpEditText);

        EmailInputLayout = findViewById(R.id.emailEditTextLayout);
        OtpInputLayout = findViewById(R.id.OtpEditTextLayout);

        textNextPageMove = findViewById(R.id.LoginPageTextBtn);
        progressBar = findViewById(R.id.progress_circular);


        // User View Model Provider ----------------------------------------
        userViewModel = new ViewModelProvider(LoginMainActivity.this).get(UserViewModel.class);


        // Login Button click --------------------------
        loginButton.setOnClickListener(view -> {
            String email = emailEdText.getText().toString();
            String otp = otpEdText.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(otp)){
                OtpInputLayout.setError("Enter Your Valid Otp");
                EmailInputLayout.setError("Enter Your Valid email");
            }else {
                progressBar.setVisibility(View.VISIBLE);

                EmailInputLayout.setError(null);
                OtpInputLayout.setError(null);
                Log.d("myLog", "my Input "+email +" "+otp);

                // create login Verify -----------------
                userViewModel.loginVerify(email, otp);
                setObserver();

            }

        });

        textNextPageMove.setOnClickListener(view -> {
            startActivity(new Intent(LoginMainActivity.this, MainActivity.class));
            finish();
        });







    }
    // OnCreate End =========================================

    public void setObserver(){
        LiveData<List<LoginUser>> loginLiveData = userViewModel.getLoginLiveData();
        if (loginLiveData!=null) {

            userViewModel.getLoginLiveData().observe(this, new Observer<List<LoginUser>>() {
                @Override
                public void onChanged(List<LoginUser> loginUser) {

                    Log.d("myLog", "live Data login User==== " + loginUser.get(0).getName());
                    if (loginUser.get(0).getId().length() > 0) {
                        progressBar.setVisibility(View.GONE);
                        textView.setText(loginUser.get(0).getName());
                        Toast.makeText(LoginMainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginMainActivity.this, AppMainBody.class));
                        finish();
                    } else {

                        Toast.makeText(LoginMainActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }else {
            Toast.makeText(LoginMainActivity.this, "Live data is Null", Toast.LENGTH_SHORT).show();
        }


    }


}