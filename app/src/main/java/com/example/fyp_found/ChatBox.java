package com.example.fyp_found;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fyp_found.datastru.FIrebase_User;
import com.example.fyp_found.datastru.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ChatBox extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FIrebase_User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);





    }
    // initial the user interface cells and the variable
    private void inituiandvar(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        user = new FIrebase_User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());


    }

}
