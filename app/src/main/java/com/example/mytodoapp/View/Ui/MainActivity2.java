package com.example.mytodoapp.View.Ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mytodoapp.R;

public class MainActivity2 extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String AuthCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        sharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        AuthCheck = sharedPreferences.getString("token", null);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AuthCheck == null) {
                    Intent myIntent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(myIntent);
                    finish();
                }else {
                    startActivity(new Intent(MainActivity2.this, AppMainBody.class));
                    finish();
                }
            }
        }, 3000);





    }
}