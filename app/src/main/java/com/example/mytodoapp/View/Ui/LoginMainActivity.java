package com.example.mytodoapp.View.Ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytodoapp.R;
import com.example.mytodoapp.Service.Model.UserData;
import com.example.mytodoapp.ViewModel.UserViewModel;

public class LoginMainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    TextView textView;


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

        userViewModel = new ViewModelProvider(LoginMainActivity.this).get(UserViewModel.class);

        userViewModel.getLiveData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                textView.setText(userData.getName()+"\n"+userData.getId());
            }
        });








    }
}