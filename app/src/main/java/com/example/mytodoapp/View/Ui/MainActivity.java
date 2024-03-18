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
import com.example.mytodoapp.Service.Model.RegisterRequestBody;
import com.example.mytodoapp.Service.Model.UserData;
import com.example.mytodoapp.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {


    private UserViewModel userViewModel;
    RegisterRequestBody registerRequestBody;
    TextView loginPageTextBtn;
    ProgressBar progressBar;
    Button loginButton;
    TextInputLayout nameInputLayout, passwordInputLayout, emailInputLayout;
    TextInputEditText nameEdText, passwordEdText, emailEdText;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        progressBar = findViewById(R.id.progress_circular);

        // login view introduce    --------------------------

        // register Button ---------------------
        loginButton = findViewById(R.id.ButtonLogin);
        // register page to login  buttonText -----------
        loginPageTextBtn = findViewById(R.id.LoginPageTextBtn);
        // Edit Text input ------------------------------
        nameEdText = findViewById(R.id.nameEditText);
        passwordEdText = findViewById(R.id.passwordEditText);
        emailEdText = findViewById(R.id.emailEditText);
        // Edit text Layout ---------------------------
        nameInputLayout = findViewById(R.id.nameEditTextLayout);
        passwordInputLayout = findViewById(R.id.passwordEditTextLayout);
        emailInputLayout = findViewById(R.id.emailEditTextLayout);


        // view model class Provider set ------------------------
        userViewModel = new ViewModelProvider(MainActivity.this).get(UserViewModel.class);




        loginButton.setOnClickListener(view -> {

            String name = nameEdText.getText().toString();
            String password = passwordEdText.getText().toString();
            String email = emailEdText.getText().toString();

            // input Value validation ----------------------
            if (TextUtils.isEmpty(name)){
                nameInputLayout.setError("Name is Required");
            } else if (TextUtils.isEmpty(password)) {
                passwordInputLayout.setError("password is Required");
            } else if (TextUtils.isEmpty(email)) {
                emailInputLayout.setError("email is Required");
            } else {
                progressBar.setVisibility(View.VISIBLE);
                nameInputLayout.setError(null);
                passwordInputLayout.setError(null);
                emailInputLayout.setError(null);

                // Api request body Object create and name password and email set -----
                registerRequestBody = new RegisterRequestBody(name, password, email);
                // call view model create user Register method --------------------
                userViewModel.createUserRegister(registerRequestBody);
                // Ui Observer set Call and this Activity ui change ------------------
                UiDataObserverSet();
            }

            Log.d("myLog", "Input check "+name +" "+password+" "+email);


        });      // button end ----------



        loginPageTextBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, LoginMainActivity.class));
            finish();
        });




    }



    // =======================================================

    public void UiDataObserverSet(){
        // view mode Observer set ----------------------------
        // live data create a Variable ------------------------
        LiveData<UserData> myLiveData = userViewModel.getLiveData();
        // null pinter manage ------------------------------
        if (myLiveData!=null) {
            userViewModel.getLiveData().observe(this, new Observer<UserData>() {
                @Override
                public void onChanged(UserData userData) {
                    progressBar.setVisibility(View.GONE);
                    if (userData.getId().length() > 0) {

                        Toast.makeText(MainActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                        // change Activity -----------------------------------------
                        startActivity(new Intent(MainActivity.this, AppMainBody.class));
                        // Activity finish method call ---------------------------
                        finish();

                    } else {
                        emailInputLayout.setError("Invalid Email Please anther Email");
                        Toast.makeText(MainActivity.this, "Register Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(this, "live data is Null", Toast.LENGTH_SHORT).show();
        }

    }




}