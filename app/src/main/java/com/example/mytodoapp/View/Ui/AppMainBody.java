package com.example.mytodoapp.View.Ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytodoapp.R;
import com.example.mytodoapp.Service.Model.LoginModel.LoginUser;
import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.View.Ui.Fragment.AllTodoFragment;
import com.example.mytodoapp.ViewModel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class AppMainBody extends AppCompatActivity {



    NavigationView navigationView;
    FrameLayout frameLayout;
    MaterialToolbar materialToolbar;
    DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    View headerView;
    SharedPreferences.Editor editor;
    UserViewModel userViewModel;



    TextView headText, headTextOtp;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_main_body);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });




        frameLayout = findViewById(R.id.frameLayout);
        navigationView = findViewById(R.id.navigationView);
        materialToolbar = findViewById(R.id.materialToolBar);
        drawerLayout = findViewById(R.id.main);

        // header View set ------------------------------
        headerView = navigationView.getHeaderView(0);

        // header Text View -------------------------------
        headText = headerView.findViewById(R.id.textViewName);
        headTextOtp = headerView.findViewById(R.id.textViewOtp);


        // drawer Open and close --------------------------
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                AppMainBody.this, drawerLayout, materialToolbar, R.string.CloseDrawer, R.string.OpenDrawer
        );

        // Open drawer ------------------------
        drawerLayout.addDrawerListener(toggle);



        //sharePreference introduce ------------------------
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

        // default Fragment --------------------------
        replaceFragment(new AllTodoFragment());


        // tool bar item select -----------------------------------
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.add_item){
                    Toast.makeText(AppMainBody.this, "this to add item", Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });




        // navigationView Item select ----------------------------------
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() ==R.id.myTodoItem){
                    Toast.makeText(AppMainBody.this, "this is All todo item", Toast.LENGTH_SHORT).show();
                    // close drawer --------------------------------------
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.DeveloperItem) {
                    Toast.makeText(AppMainBody.this, "Developer Item", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.StartReteItem) {
                    Toast.makeText(AppMainBody.this, "Star Rete Item", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.logoutItem) {
                    Toast.makeText(AppMainBody.this, "LogOut Item", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    return false;
                }

                // return false =don't select item ----------and return true = selected item
                return true;
            }
        });


        // Live data Observer ---------------------------------

        // introduce SharePreferences ---------------------------


        // view Model Provider set -------------------------------------
        userViewModel = new ViewModelProvider(AppMainBody.this).get(UserViewModel.class);


        // Live data check and Null save ----------------------------
        LiveData<List<LoginUser>> loginLiveData = userViewModel.getLoginLiveData();
        if (loginLiveData != null) {
            userViewModel.getLoginLiveData().observe(this, new Observer<List<LoginUser>>() {
                @Override
                public void onChanged(List<LoginUser> loginUser) {
                    Log.d("myLog", "Login Info == " + loginUser.get(0).getName());
                    headText.setText(loginUser.get(0).getName());
                    editor = sharedPreferences.edit();
                    editor.putString("name", loginUser.get(0).getName());
//                    editor.putString("token", registerModel.getToken());
                    editor.apply();
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
                        editor = sharedPreferences.edit();
                        editor.putString("name", registerModel.getUser().getName());
                        editor.putString("token", registerModel.getToken());
                        editor.apply();
                    }



                }
            });
        }

        String token = sharedPreferences.getString("token", null);

        headTextOtp.setText(token);










    }

    // fragment replace ------------------------------------
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


}