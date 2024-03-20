package com.example.mytodoapp.View.Ui.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.example.mytodoapp.R;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreate;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreateResponse;
import com.example.mytodoapp.ViewModel.TodoViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;




public class AllTodoFragment extends Fragment {

    SharedPreferences sharedPreferences;
    String token;

    TextInputLayout desLayout, titleLayout;
    TextInputEditText des, title;

    Button saveButton;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    FloatingActionButton floatingActionButton;


    TodoViewModel todoViewModel;
    TodoCreate todoCreate;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_todo, container, false);


        des = view.findViewById(R.id.desEditText);
        title = view.findViewById(R.id.titleEditText);
        desLayout = view.findViewById(R.id.desEditTextLayout);
        titleLayout = view.findViewById(R.id.titleEditTextLayout);

        linearLayout = view.findViewById(R.id.infoLayout);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        saveButton = view.findViewById(R.id.saveLogin);
        progressBar  = view.findViewById(R.id.progress_circular);


        // view model Provider ---------------------------------------
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);


        floatingActionButton.setOnClickListener(view1 -> {
            linearLayout.setVisibility(View.VISIBLE);
        });


        saveButton.setOnClickListener(view1 -> {

            String titleValue = title.getText().toString();
            String desValue = des.getText().toString();

            if (TextUtils.isEmpty(titleValue)){
                titleLayout.setError("title is required");
            } else if (TextUtils.isEmpty(desValue)) {
                desLayout.setError("description is Required");
            }else {
                progressBar.setVisibility(View.VISIBLE);
                titleLayout.setError(null);
                desLayout.setError(null);

                todoCreate = new TodoCreate(titleValue, desValue, "green", "3/90/2024");

                Log.d("myLog", "onCreateView: input Save "+titleValue+"\n"+desValue);


                // create todo call api form Live data View model --------------------
                todoViewModel.createTodo(token, todoCreate);
                setObserver();

            }




        });






        // introduce SharePreferences ---------------------------
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
       // editor = sharedPreferences.edit();

        token = sharedPreferences.getString("token",null);














        return view;
    }


    public void setObserver() {
        LiveData<TodoCreateResponse> todoCreateResponseLiveData = todoViewModel.getCreateTodoResponse();
        if (todoCreateResponseLiveData != null) {

            todoViewModel.getCreateTodoResponse().observe(getViewLifecycleOwner(), new Observer<TodoCreateResponse>() {
                @Override
                public void onChanged(TodoCreateResponse todoCreateResponse) {

                    if (todoCreateResponse.getSuccess()) {

                        Toast.makeText(getContext(), "Todo Create successful", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);

                    }


                }
            });
        } else {
            Toast.makeText(getContext(), "Live data is Null", Toast.LENGTH_SHORT).show();
        }

    }

}
