package com.example.fyp_found.setup;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.fyp_found.MainActivity;
import com.example.fyp_found.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import static com.example.fyp_found.setup.staticclass.FCM_TOKEN;
import static com.example.fyp_found.setup.staticclass.TAG;
import static com.example.fyp_found.setup.staticclass.final_static_str_db_name_user;
import static com.example.fyp_found.setup.staticclass.final_static_str_firebasedatabase_child_users;


public class cloudmes extends FirebaseMessagingService {
    String Newtoken;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            // ("message title", "MessageFirebase ;" + remoteMessage.getNotification().getTitle());
            // ("message body", "MessageFirebase ;" + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());


        }



    }

    public void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()){
                }
                onNewToken(task.getResult().getToken());
                setToken(task.getResult().getToken());
            }
        });
    }

    public void setToken(String token){
        if (!token.isEmpty()){
            Newtoken =  token;

        }else{
        }
    }

    public String returnToken(){return Newtoken;}



    public void uploadToken(String newToken){
        if (!newToken.isEmpty()) {
            try {
                Log.i(staticclass.TAG, newToken + "@@QQ");

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(final_static_str_firebasedatabase_child_users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(staticclass.firebase_FCM_Token).setValue(newToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.i(TAG, " failure" + task.getException());
                        }
                        if (task.getResult() != null) {
                            Log.i(TAG, "Successful" + task.getResult().toString());
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.i(TAG, "Success upload");
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        FCM_TOKEN = s;
        setToken(s);

    }




    private void sendNotification (String messageTitle , String messageBody){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,PendingIntent.FLAG_ONE_SHOT);

        String channelId = "default_notification_channel_id";
        Uri defaultSoundUri =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)
                .setContentTitle(messageTitle)
                .setSmallIcon(R.drawable.icon_app)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,"Channel Human Readable Title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());


    }
}
