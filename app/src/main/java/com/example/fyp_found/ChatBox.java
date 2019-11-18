package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp_found.datastru.Firebase_User;
import com.example.fyp_found.setup.staticclass;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.fyp_found.setup.staticclass.current_dev_code;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_Content;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_Time;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_rev_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_sender_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Email;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Id;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_firebasedatabase_child_chat;

public class ChatBox extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Firebase_User sender, receiver;
    DatabaseReference databaseReference;
    String receiverID;
    TextView showusername;
    EditText message_edit;
    Button send;
    RecyclerView recyclerView;
    Intent intent;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        intent = getIntent();
        receiverID = intent.getStringExtra(staticclass.final_static_str_Intent_Chatoppun);
        initui();
        initvar();
        Log.i(staticclass.TAG,receiverID);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(message_edit.getText().toString().trim().equals("")){
                    Toast.makeText(context, "The message cannot empty", Toast.LENGTH_LONG).show();
                }else{
                    send_Message(sender.getUser_Id(),receiverID,message_edit.getText().toString().trim());
                    message_edit.setText(null);

                }

            }
        });
    }
    public void initui(){
        recyclerView = findViewById(R.id.chat_box_recyclerview);
        showusername = findViewById(R.id.chat_user_name_text);
        message_edit = findViewById(R.id.chat_box_input_editview);
        send = findViewById(R.id.chat_box_send_btn);
    }
    // initial the user interface cells and the variable
    private void initvar(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sender = new Firebase_User(firebaseUser.getUid(),firebaseUser.getDisplayName(),firebaseUser.getEmail());
        databaseReference = FirebaseDatabase.getInstance().getReference(staticclass.final_static_str_firebasedatabase_child_users);
        context = this;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    for(String key : hashMap.keySet()){
                        Object data = hashMap.get(key);
                        try {
                            HashMap<String, Object> h = (HashMap<String, Object>) data;
                            if(receiverID.equals(h.get(final_static_str_User_Id))){
                                Firebase_User firebase_user = new Firebase_User((String)h.get(final_static_str_User_Id),(String)h.get(final_static_str_User_Name),(String) h.get(final_static_str_User_Email));
                                receiver = firebase_user;

                                showusername.setText(receiver.getUser_Name());
                                                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void send_Message(String senderID, String receiverID,String message){
        if(message.equals("")){

        }else{
            Calendar c = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String strDate = simpleDateFormat.format(c.getTime());

            databaseReference = FirebaseDatabase.getInstance().getReference(final_static_str_firebasedatabase_child_chat);

            HashMap<String,String> hashMap = new HashMap<String,String>();
            hashMap.put(final_static_str_Chat_sender_ID, senderID);
            hashMap.put(final_static_str_Chat_Content,message);
            hashMap.put(final_static_str_Chat_rev_ID,receiver.getUser_Id());
            hashMap.put(final_static_str_Chat_Time, strDate);
            hashMap.put(final_static_str_Chat_ID, strDate + senderID + receiverID);
            Log.i(staticclass.TAG,receiver.getUser_Id());

            databaseReference.push().setValue(hashMap);
        }

    }

    private void restrict_message(){}






}
