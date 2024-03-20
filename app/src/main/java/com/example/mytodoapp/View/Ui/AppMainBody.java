package com.example.mytodoapp.View.Ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.mytodoapp.Service.Model.LoginModel.LoginResponse;
import com.example.mytodoapp.Service.Model.LoginModel.LoginUser;
import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.Service.Model.UserDeleteModel.DeleteUser;
import com.example.mytodoapp.Service.SharePreferenceManager;
import com.example.mytodoapp.View.Ui.Fragment.AllTodoFragment;
import com.example.mytodoapp.ViewModel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppMainBody extends AppCompatActivity {


    NavigationView navigationView;
    FrameLayout frameLayout;
    MaterialToolbar materialToolbar;
    DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    View headerView;
    SharedPreferences.Editor editor;
    UserViewModel userViewModel;

    // SharePre---------
    String name, email, otp, token;

    // Model -------------------------
    SharePreferenceManager sharePreferenceManager;




    TextView headText, headTextOtp, headTextEmail;


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
        headTextEmail = headerView.findViewById(R.id.textViewEmail);



        // drawer Open and close --------------------------
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                AppMainBody.this, drawerLayout, materialToolbar, R.string.CloseDrawer, R.string.OpenDrawer
        );

        // Open drawer ------------------------
        drawerLayout.addDrawerListener(toggle);

        sharePreferenceManager = new SharePreferenceManager(AppMainBody.this);




        //sharePreference introduce ------------------------
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        editor = sharedPreferences.edit();


        // default Fragment --------------------------
        replaceFragment(new AllTodoFragment());

        userViewModel = new ViewModelProvider(AppMainBody.this).get(UserViewModel.class);


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

        //View Create Header View --------------------- and get data with SharePreference ---------------
        token = sharedPreferences.getString("token", null);
        otp = sharedPreferences.getString("otp", null);
        name = sharedPreferences.getString("name", null);
        email = sharedPreferences.getString("email", null);





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

                    // logout --------------------------------------------

                    editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    Log.d("myLog", "onNavigationItemSelected: "+token);

                    startActivity(new Intent(AppMainBody.this, LoginMainActivity.class));



                    Toast.makeText(AppMainBody.this, "LogOut Item", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() ==R.id.AccountDeleteItem) {
                    // User Account delete -----------------------------------------
                    if (token!=null) {
                        // delete User Account api call with view Model ---------------------
                        userViewModel.UserAccountDeleteApiCall(token);
                        // delete User data observer ---------------------------
                        LiveData<DeleteUser> myDeleteData = userViewModel.getDeleteUserAccount();
                        if (myDeleteData !=null) {

                            userViewModel.getDeleteUserAccount().observe(AppMainBody.this, new Observer<DeleteUser>() {
                                @Override
                                public void onChanged(DeleteUser deleteUser) {

                                    if (deleteUser.getSuccess()) {


                                        Log.d("myLog", "onChanged: delete log" + token);
                                        editor = sharedPreferences.edit();
                                        editor.clear();
                                        editor.apply();

                                        Toast.makeText(AppMainBody.this, "Account Delete Successful \n" + deleteUser.getData().getName(), Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(AppMainBody.this, MainActivity.class));
                                        finish();


                                    } else {
                                        Toast.makeText(AppMainBody.this, "Delete fail " + deleteUser.getSuccess(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }else {
                            Toast.makeText(AppMainBody.this, "UserNot delete", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AppMainBody.this, "Token is null", Toast.LENGTH_SHORT).show();
                    }
                    // live data Observer end ----------------------- delete account ----------

                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                // return false =don't select item ----------and return true = selected item
                return true;
            }
        });


        // add text profile section --------------------------
        headTextOtp.setText(otp);
        headText.setText(name);
        headTextEmail.setText(email);
















    }

    // fragment replace ------------------------------------
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }



}