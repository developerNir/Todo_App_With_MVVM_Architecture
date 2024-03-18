package com.example.mytodoapp.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SharePreferenceManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Set<String> mySet;



    public SharePreferenceManager(Context context){
        sharedPreferences = context.getSharedPreferences("PrefeNave", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveData(Set<String> set){


        Log.d("myLog", "saveData: "+set);
        editor.putStringSet("data", set);
        editor.apply();


    }

    public Set<String> getData(){

        mySet = sharedPreferences.getStringSet("data", null);


        return mySet;
    }



}
