package com.example.fyp_found;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;

import com.example.fyp_found.datastru.Firebase_User;
import com.example.fyp_found.setup.cloudmes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.fyp_found.setup.staticclass.FCM_TOKEN;
import static com.example.fyp_found.setup.staticclass.current_dev_code;

public class LoadingPage extends AppCompatActivity {
    ImageButton login, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        setVar();
        setUI();



        // final action, getToken  from firebase cloud messaging and save it to realtime database
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            cloudmes FCM = new cloudmes();
            FCM.getToken();
            FCM_TOKEN = FCM.returnToken();
           startActivity(new Intent(LoadingPage.this, HomePage.class));
        }
    }
    private void setVar(){
        // load data
        current_dev_code = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    private void setUI(){
        login = findViewById(R.id.login_btn_loading);
        signup = findViewById(R.id.signup_btn_loading);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoadingPage.this, Login.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoadingPage.this, Signuppage.class));
            }
        });
    }

}
