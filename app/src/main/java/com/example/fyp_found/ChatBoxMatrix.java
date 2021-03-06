package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.fyp_found.Adapter.Chat_Box_Matrix_Adapter;
import com.example.fyp_found.datastru.Firebase_User;
import com.example.fyp_found.setup.staticclass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.fyp_found.setup.staticclass.final_static_str_Intent_Chatoppun;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Email;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Id;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Login_Method;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_dev_code;

public class ChatBoxMatrix extends AppCompatActivity{


    RecyclerView recyclerView;
    Firebase_User user;
    ArrayList<Firebase_User> firebase_users = new ArrayList<Firebase_User>();
    Context context;
    Chat_Box_Matrix_Adapter adapter;
    View view;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box_matrix);
        initvariable();
        initui();

    }


    private void initui() {
        recyclerView = findViewById(R.id.chatbox_user_recyclerview);
        view = findViewById(R.id.chatbox_matrix_nav_bar);
        setBottomNavigationView();

         final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
         final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(staticclass.final_static_str_db_name_user);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // error checking
                if (dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    // convert the datasnapshot value to hashmap
                    for (String key : hashMap.keySet()) {
                        Object data = hashMap.get(key);
                        try {
                            HashMap<String, Object> users = (HashMap<String, Object>) data;
                            Firebase_User f = new Firebase_User();
                            f.setUser_Id((String)users.get(final_static_str_User_Id));
                            f.setUser_Email((String)users.get(final_static_str_User_Email));
                            // icon url needed
                            if(users.get(final_static_str_User_Name) == null){
                                f.setUser_Name((String)users.get(final_static_str_User_Email));

                            }else{
                                f.setUser_Name((String)users.get(final_static_str_User_Name));
                            }

                            if(!firebaseUser.getUid().equals(f.getUser_Id())) {
                                firebase_users.add(f);
                                adapter = new Chat_Box_Matrix_Adapter(firebase_users, context);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, getResources().getString(R.string.error_404), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initvariable() {
        context = this;
    }


    private void setBottomNavigationView(){
        bottomNavigationView = view.findViewById(R.id.bottom_nav_bar);
        context = this;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.bottom_nav_bar_home:
                        startActivity(new Intent(context, HomePage.class));
                        break;
                    case R.id.bottom_nav_bar_post:
                        startActivity(new Intent(context, ImageClassification.class));
                        break;
                    case R.id.bottom_nav_bar_profile:
                        startActivity(new Intent(context, Profile.class));
                        break;

                }
                return false;
            }
        });

    }
}