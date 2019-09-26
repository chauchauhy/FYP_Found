package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    private void noitication() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful())
                    return;
                if (task.getResult() == null)
                    return;
                String token = task.getResult().getToken();
                // ("TokenMain:", token);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "default_notification_channel_id";
                    String channelName = "default_notification_channel_name";
                    NotificationManager notificationManager =
                            getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW));
                }
            }
        });
    }

}
