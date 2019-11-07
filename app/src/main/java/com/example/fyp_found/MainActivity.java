package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {

    // please defind some static variable here
    // test page//

    Button b1,b2,b3,b4;
    Button ba[] = new Button[4] ;
    int str[] = new int[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ba[0] = findViewById(R.id.b1);
        ba[1] = findViewById(R.id.b2);
        ba[2] = findViewById(R.id.b3);
        ba[3] = findViewById(R.id.b4);

        str[0] = 0;
        str[1] = 1;
        str[2] = 2;
        str[3] = 3;

        for(int i = 0; i< 4; i++){
            ba[i].setText(String.valueOf(str[i]));
        }
        // this is setup activity for inittial SDK(facebook and firebase and google sdk
        // please dont coding here


    }

}
