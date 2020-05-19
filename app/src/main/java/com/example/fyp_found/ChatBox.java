package com.example.fyp_found;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cenkgun.chatbar.ChatBarView;
import com.example.fyp_found.Adapter.Chat_Box_Adapter;
import com.example.fyp_found.datastru.Chat_record;
import com.example.fyp_found.datastru.Firebase_User;
import com.example.fyp_found.setup.staticclass;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.fyp_found.setup.staticclass.FCM_TOKEN;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_Content;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_Date;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_Time;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_rev_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_sender_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_unixTime;
import static com.example.fyp_found.setup.staticclass.final_static_str_Found_Property_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Email;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Id;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_firebasedatabase_child_chat;
import static com.example.fyp_found.setup.staticclass.final_static_str_firebasedatabase_child_users;
import static com.example.fyp_found.setup.staticclass.firebase_FCM_Token;



public class ChatBox extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Firebase_User sender, receiver;
    DatabaseReference databaseReference;
    String receiverID;
    EditText message_edit;
    ImageButton send;
    RecyclerView recyclerView;
    Intent intent;
    Context context;
    Chat_Box_Adapter chat_box_adapter;
    ArrayList<Chat_record> records = new ArrayList<Chat_record>();
    View view;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        intent = getIntent();
        receiverID = intent.getStringExtra(staticclass.final_static_str_Intent_Chatoppun);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Log.i(staticclass.TAG, time);
        initui();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // message checking
            Toast.makeText(context,"you may need login to us this action", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ChatBox.this,Login.class));
        }else {
            initvar();
        }



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(message_edit.getText().toString().trim().isEmpty() || message_edit.getText().toString().trim().equals("")){
                    Toast.makeText(context, "The message cannot empty", Toast.LENGTH_LONG).show();
                }else{
                    send_Message(sender.getUser_Id(),receiverID, message_edit.getText().toString().trim());
                    message_edit.setText(null);

                }

            }
        });

    }

    public void initui() {
        recyclerView = findViewById(R.id.chat_box_recyclerview);
        message_edit = findViewById(R.id.chat_box_input_editview);
        send = findViewById(R.id.chat_box_send_btn);
        view = findViewById(R.id.chat_box_subview);
        toolbar = view.findViewById(R.id.app_toolbar_nosearch_1);
        setSupportActionBar(toolbar);
        context = this;
        setToolbar();
    }
    private void setToolbar(){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, HomePage.class));
            }
        });
        ImageView icon = view.findViewById(R.id.user_icon);
        icon.setVisibility(View.VISIBLE);


    }



    private void setToolbarTitle(String name){
        TextView username = view.findViewById(R.id.user_name);
        username.setText(name.trim());
        username.setVisibility(View.VISIBLE);
    }

    // initial the user interface cells and the variable
    private void initvar() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sender = new Firebase_User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
        if(firebaseUser.getDisplayName() == null){
            sender.setUser_Name(firebaseUser.getEmail());
            Log.i(staticclass.TAG, sender.getUser_Name());

        }
        databaseReference = FirebaseDatabase.getInstance().getReference(staticclass.final_static_str_firebasedatabase_child_users);
        context = this;
        // initial the Firebase user for FCM
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    for (String key : hashMap.keySet()) {
                        Object data = hashMap.get(key);
                        try {
                            HashMap<String, Object> h = (HashMap<String, Object>) data;
                            if (receiverID.equals(h.get(final_static_str_User_Id))) {
                                Firebase_User firebase_user = new Firebase_User();
                                firebase_user.setUser_Id((String)h.get(final_static_str_User_Id));
                                firebase_user.setUser_Email((String)h.get(final_static_str_User_Email));
                                // receiver icon url needed
                                if (h.get(firebase_FCM_Token) != null){
                                    firebase_user.setToken((String) h.get(firebase_FCM_Token));
                                }
                                if(h.get(final_static_str_User_Name) == null){
                                    firebase_user.setUser_Name((String)h.get(final_static_str_User_Email));

                                }else{
                                    firebase_user.setUser_Name((String) h.get(final_static_str_User_Name));
                                }
                                receiver = firebase_user;
                                setToolbarTitle(receiver.getUser_Name());
                                restrict_message(sender.getUser_Id(),receiverID);


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

    private void send_Message(String senderID, String receiverID, String message) {
        //error checking
        if (message.equals("")) {

        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference(final_static_str_firebasedatabase_child_chat);
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(final_static_str_Chat_sender_ID, senderID);
            hashMap.put(final_static_str_Chat_Content, message);
            hashMap.put(final_static_str_Chat_rev_ID, receiver.getUser_Id());
            hashMap.put(final_static_str_Chat_unixTime, String.valueOf(Chat_record.getUnixTime()));
            hashMap.put(final_static_str_Chat_ID,  (Chat_record.getUnixTime())+ senderID + receiverID);
            Log.i(staticclass.TAG, receiver.getUser_Id());

            databaseReference.push().setValue(hashMap);
            send_Message_Notification(message);
        }

    }

    private void send_Message_Notification(String message){
        String token = receiver.getToken();
        String name = "";
        if (sender.getUser_Name().isEmpty()){
            name = "Someone";

        }else if (sender.getUser_Name().length()>10 && sender.getUser_Name().contains("@gmail.com")){
            name = sender.getUser_Name().replace("@gmail.com","");
        }else{
            name = sender.getUser_Name();
        }
        Log.i(staticclass.TAG, name);

        String message_content = message;
        new Notification().execute(token, name, message_content);
//        Notification notification = new Notification();
//        notification.execute(token, name, message_content);

    }

    public class Notification extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                // this url is connect to firebase FCM send message interface
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                // the key of server (latency)
                // maybe can use volley to connect?
                conn.setRequestProperty("Authorization", "key=AIzaSyCl3UHa8r4nXO9xLWKXu3fAZsoMHRBI9o0"); // get request property detail to firebase interface(?)
                conn.setRequestProperty("Content-Type", "application/json"); // set data type

                // the interface allows json object so convert message body to json object and output it in this async task

                JSONObject json = new JSONObject();

                json.put("to", strings[0]);

                String content = strings[1] + " : " + strings[2];// for combine content of message
                String title = "You have a message";


                JSONObject info = new JSONObject();
                info.put("title", title);   // Notification title
                info.put("body", content); // Notification body like Joy : Hi  this part only allow a String

                json.put("notification", info);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();
                conn.getInputStream();
                return json.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            Log.i(staticclass.TAG, s);
        }
    }

    private void restrict_message(final String mid, final String userid) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference(final_static_str_firebasedatabase_child_chat);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                records.clear();

                if (dataSnapshot.exists()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    for (String key : hashMap.keySet()) {
                        Object data = hashMap.get(key);

                        try {
                            Chat_record chat_record ;
                            HashMap<String, Object> chat = (HashMap<String, Object>) data;
                            chat_record = new Chat_record((String) chat.get(final_static_str_Chat_ID),(String)chat.get(final_static_str_Chat_sender_ID),
                                    (String) chat.get(final_static_str_Chat_rev_ID),(String) chat.get(final_static_str_Chat_Content),(String)chat.get(final_static_str_Chat_unixTime));
                            if((chat_record.getChat_sender_ID().equals(mid)&& chat_record.getChat_rev_ID().equals(userid)) ||
                                    chat_record.getChat_sender_ID().equals(userid)&&chat_record.getChat_rev_ID().equals(mid)){

                                records.add(chat_record);
                                adapter();
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

    private void adapter(){
        chat_box_adapter = new Chat_Box_Adapter(records,context);
        recyclerView.setAdapter(chat_box_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



}
