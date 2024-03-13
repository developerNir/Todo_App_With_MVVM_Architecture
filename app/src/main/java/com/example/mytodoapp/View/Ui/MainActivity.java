package com.example.mytodoapp.View.Ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
    TextView textView, loginPageTextBtn;
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


        textView = findViewById(R.id.textView);

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
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)){
                nameInputLayout.setError("Name is Required");
                passwordInputLayout.setError("password is Required");
                emailInputLayout.setError("email is Required");
            }else {
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
        });




    }



    // =======================================================

    public void UiDataObserverSet(){
        // view mode Observer set ----------------------------
        userViewModel.getLiveData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData.getId().length() >0){
                    textView.setText(userData.getName()+"\n"+userData.getOtp());
                    Toast.makeText(MainActivity.this, "Register Success", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this, "Register Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




}