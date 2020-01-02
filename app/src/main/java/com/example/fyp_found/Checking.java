package com.example.fyp_found;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp_found.Adapter.Checking_Adapter;
import com.example.fyp_found.datastru.Current_Lost_Record;
import com.example.fyp_found.setup.staticclass;
import com.example.fyp_found.datastru.questionAndAns;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Intent_Chatoppun;

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
         TextView forward = view.findViewById(R.id.checking_toolbar_forward);
         forward.setClickable(true);
         forward.setVisibility(View.VISIBLE);
         forward.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (checking_adapter.getQuestionAndAnsArrayList()!=null) {
                     int trueofans = 0;
                     ArrayList<questionAndAns> qa = checking_adapter.getQuestionAndAnsArrayList();
                     for (int i = 0 ; i<qa.size(); i++) {
                         Log.i(staticclass.TAG, qa.get(i).toString());
                         if(qa.get(i).getResult()){
                             trueofans++;
                         }
                     }

                     if(trueofans>=2){
                         new AlertDialog.Builder(context).setTitle(getResources().getString(R.string.congratulation)).setMessage(getResources().getString(R.string.meet_ans)).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 if(record.getCurrent_Lost_User_ID().trim().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                     dialogInterface.dismiss();
                                     Toast.makeText(context, "The action is invalid", Toast.LENGTH_LONG).show();
                                 }else{
                                     dialogInterface.dismiss();
                                     Intent intent = new Intent(Checking.this, ChatBox.class);
                                     intent.putExtra(final_static_str_Intent_Chatoppun, record.getCurrent_Lost_User_ID().trim());
                                     startActivity(intent);
                                 }
                             }
                         }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 dialogInterface.dismiss();
                             }
                         }).show();
                     }else{
                         new AlertDialog.Builder(context).setTitle(getResources().getString(R.string.sorry_check)).setMessage(getResources().getString(R.string.not_Meet_ans)).setPositiveButton("Back", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 dialogInterface.dismiss();
                                 startActivity(new Intent(context, HomePage.class));
                             }
                         }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 dialogInterface.dismiss();
                             }
                         }).show();
                     }
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
            setFinish();






        }


    }

}
