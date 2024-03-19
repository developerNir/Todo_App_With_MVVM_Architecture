package com.example.mytodoapp.View.Ui.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytodoapp.R;
import com.example.mytodoapp.Service.Model.LoginModel.LoginResponse;
import com.example.mytodoapp.Service.Model.LoginModel.LoginUser;
import com.example.mytodoapp.Service.Model.RegisterModel;
import com.example.mytodoapp.View.Ui.AppMainBody;
import com.example.mytodoapp.ViewModel.UserViewModel;

import java.util.List;


public class AllTodoFragment extends Fragment {

    TextView textView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    UserViewModel userViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_todo, container, false);


        textView =view.findViewById(R.id.textView2);

        // introduce SharePreferences ---------------------------
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
       // editor = sharedPreferences.edit();

        // view Model Provider set -------------------------------------
        //userViewModel = new ViewModelProvider(this).get(UserViewModel.class);



        // Live data check and Null save --------------Login Model---------------------
//        LiveData<LoginResponse> loginLiveData = userViewModel.getloginResponseLiveData();
//        if (loginLiveData != null) {
//            userViewModel.getloginResponseLiveData().observe((LifecycleOwner) getContext(), new Observer<LoginResponse>() {
//                @Override
//                public void onChanged(LoginResponse loginUser) {
//                    Log.d("myLog", "Login Info == " + loginUser);
//                    textView.setText(loginUser.getToken()+"\n"+loginUser.getSuccess());
//
//                    editor.putString("token", loginUser.getToken());
//                    editor.apply();
//
//
//                }
//            });
//        }else {
//            Toast.makeText(getContext(), "Live data is null", Toast.LENGTH_SHORT).show();
//        }


        // get Token form View model ---------------------Register Model-----------
//        LiveData<RegisterModel> tokenAndRegisterData = userViewModel.getTokenWithRegister();
//        if (tokenAndRegisterData !=null){
//            userViewModel.getTokenWithRegister().observe(getViewLifecycleOwner(), new Observer<RegisterModel>() {
//                @Override
//                public void onChanged(RegisterModel registerModel) {
//
//                    if (registerModel.getSuccess()){
//                        textView.setText(registerModel.getToken());
//                        textView.append("\n"+registerModel.getUser().getEmail());
//
//
//                        editor.putString("token", registerModel.getToken());
//                        editor.apply();
//                    }
//
//
//
//                }
//            });
//        }



        String token = sharedPreferences.getString("token",null);

        textView.setText(token);












        return view;


    }
}