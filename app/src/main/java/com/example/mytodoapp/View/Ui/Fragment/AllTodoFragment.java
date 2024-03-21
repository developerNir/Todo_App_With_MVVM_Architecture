package com.example.mytodoapp.View.Ui.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.example.mytodoapp.R;
import com.example.mytodoapp.Service.Model.TodoModel.AllTodoData;
import com.example.mytodoapp.Service.Model.TodoModel.DeleteModel.TodoDeleteModel;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreate;
import com.example.mytodoapp.Service.Model.TodoModel.TodoCreateResponse;
import com.example.mytodoapp.ViewModel.TodoViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class AllTodoFragment extends Fragment {

    SharedPreferences sharedPreferences;
    String token;

    TextInputLayout desLayout, titleLayout;
    TextInputEditText des, title;

    Button saveButton;
    RecyclerView recyclerView;
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

        recyclerView = view.findViewById(R.id.recyclerView);


        // introduce SharePreferences ---------------------------
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        // editor = sharedPreferences.edit();

        token = sharedPreferences.getString("token",null);


        // view model Provider ---------------------------------------
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        // get all todo api call ==========================================
        todoViewModel.getAllTodoApiCall(token);

        // Linear layout add -------------------------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // all todo observer ===========================================
        setAllTodoObserver();



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





















        return view;
    }// onCreate end ===============================================


    // todo create Observer add ---------------------------------------
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

                        todoViewModel.getAllTodoApiCall(token);
                        setAllTodoObserver();

                    }


                }
            });
        } else {
            Toast.makeText(getContext(), "Live data is Null", Toast.LENGTH_SHORT).show();
        }

    }

//    all todo observer -----------------------------------------------------------

    private void setAllTodoObserver(){
        // get all todo Observer add and return AllTodoData ======================================
        LiveData<List<AllTodoData>> allTodoResponseLiveData = todoViewModel.getAllTodoDataFromLiveData();
        if (allTodoResponseLiveData!=null){

            todoViewModel.getAllTodoDataFromLiveData().observe(getViewLifecycleOwner(), new Observer<List<AllTodoData>>() {
                @Override
                public void onChanged(List<AllTodoData> allTodoData) {

                    if (allTodoData.size() >0) {

                        TodoAdapter todoAdapter = new TodoAdapter(allTodoData);
                        recyclerView.setAdapter(todoAdapter);
                    }

                }
            });

        }else {
            Toast.makeText(getContext(), "Live data not return", Toast.LENGTH_SHORT).show();
        }
    }


    // todo adapter =========================================

    public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {


        private final List<AllTodoData> mList;


        public TodoAdapter(List<AllTodoData> mList) {
            this.mList = mList;
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.todo_layout, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.title.setText(mList.get(position).getTitle());
            holder.des.setText(mList.get(position).getDescription());
            holder.time.setText(mList.get(position).getSetTime());

            String todoId = mList.get(position).getId();


            // Todo delete ===================================================
            holder.delete.setOnClickListener(view -> {

                todoViewModel.ApiCallDeleteTodo(token, todoId);

                todoViewModel.getResponseDeleteTodo().observe(getViewLifecycleOwner(), new Observer<TodoDeleteModel>() {
                    @Override
                    public void onChanged(TodoDeleteModel todoDeleteModel) {

                        if (todoDeleteModel.getSuccess()){
                            Toast.makeText(getContext(), "Delete success", Toast.LENGTH_SHORT).show();

                            todoViewModel.getAllTodoApiCall(token);
                            setAllTodoObserver();
                        }


                    }
                });


            });

            holder.edit.setOnClickListener(view -> {
                Toast.makeText(getContext(), "Call delete edit", Toast.LENGTH_SHORT).show();
            });

            holder.note.setOnClickListener(view -> {
                Toast.makeText(getContext(), "Call delete note", Toast.LENGTH_SHORT).show();
            });


        }

        @Override
        public int getItemCount() {
            return mList.size();
        }



        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView title, des, time;
            ImageView delete, edit, note;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                time = itemView.findViewById(R.id.timeTv);
                des = itemView.findViewById(R.id.desTv);
                title = itemView.findViewById(R.id.titleTv);

                delete = itemView.findViewById(R.id.imageDelete);
                edit = itemView.findViewById(R.id.imageEdit);
                note = itemView.findViewById(R.id.imageNote);

            }
        }

    }



}
