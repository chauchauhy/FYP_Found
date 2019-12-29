package com.example.fyp_found;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.fyp_found.Adapter.Checking_Adapter;
import com.example.fyp_found.datastru.Current_Lost_Record;
import com.example.fyp_found.setup.staticclass;
import com.example.fyp_found.datastru.questionAndAns;

import java.util.ArrayList;

import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_ID;

public class Checking extends AppCompatActivity {

    String id_string;
    Toolbar toolbar;
    View view;
    private static Current_Lost_Record record;
    Context context;
    ArrayList<questionAndAns> questions = new ArrayList<>();
    RecyclerView recyclerView;
    Checking_Adapter checking_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);
        initVar();
        initUI();

    }
    private void initUI(){
        view = findViewById(R.id.checking_subview);
        toolbar = view.findViewById(R.id.app_toolbar_nosearch_1);
        setToolbar();
    }
    private void setRecyclerView(){
        recyclerView = findViewById(R.id.checking_View_List);
        checking_adapter = new Checking_Adapter(context, questions);
        recyclerView.setAdapter(checking_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
     }

     private void setFinish(){
        TextView done = view.findViewById(R.id.checking_toolbar_forward);
        done.setClickable(true);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(questionAndAns i : checking_adapter.getQuestionAndAnsArrayList()){
                    Log.i(staticclass.TAG, i.toString());
                }
            }
        });


     }

    private void initVar(){

        Intent ient = getIntent();
        id_string = ient.getStringExtra(final_static_str_Current_Lost_ID);
        Log.i(staticclass.TAG, id_string);
        for(Current_Lost_Record record : HomePage.records){
            if(record.getCurrent_Lost_ID().equals(id_string)){
                this.record = record;
            }
        }
        Log.i(staticclass.TAG, record.toString());
        context = this;
        if(record!=null) {
            String addQuestion = getResources().getString(R.string.addressQuestion);
            questions.add(new questionAndAns(addQuestion, record.getCurrent_Lost_Address()));
            questions.add(new questionAndAns(record.getCurrent_Lost_Property_QA1(), record.getCurrent_Lost_Property_QA1_Ans()));
            questions.add(new questionAndAns(record.getCurrent_Lost_Property_QA2(), record.getCurrent_Lost_Property_QA2_Ans()));
            setRecyclerView();
        }


    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        if(toolbar!=null) {
            getSupportActionBar().setTitle("QA");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, HomePage.class));
                }
            });

            TextView forward = view.findViewById(R.id.checking_toolbar_forward);
            forward.setClickable(true);



        }


    }


}
