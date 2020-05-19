package com.example.fyp_found;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fyp_found.datastru.Firebase_User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Post extends AppCompatActivity {
    Context context;
    Firebase_User firebase_user;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initVar();

    }

    private void checkUserlogined(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null){
            startActivity(new Intent(context, HomePage.class));
        }

    }


    private void initVar(){
        context = this;
        checkUserlogined();
        firebase_user = HomePage.firebase_user_current;

    }

    private void initUI(){}

    private void userChecking(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Toast.makeText(context, getResources().getString(R.string.needlogin), Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(context).setTitle(getResources().getString(R.string.warning)).setMessage(getResources().getString(R.string.needlogin)).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    startActivity(new Intent(context, LoadingPage.class));
                }
            }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    startActivity(new Intent(context, HomePage.class));
                }
            }).show();
        }
    }

}
