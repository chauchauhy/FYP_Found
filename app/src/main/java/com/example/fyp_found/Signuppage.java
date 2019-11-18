package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp_found.setup.staticclass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.fyp_found.setup.staticclass.currentUserID;
import static com.example.fyp_found.setup.staticclass.current_dev_code;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Email;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Id;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Login_Method;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_dev_code;


// sign up page ....

public class Signuppage extends AppCompatActivity {
    EditText account, name, email, password;
    Button send;
    boolean userinformationisvaild = false;
    String account_str , name_str , email_str, password_str;
    TextView hint_account, hint_name, hint_email, hint_password;
    Context context;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);
        initui();
        initvariable();
        responsebutton();
    }



    private void initui(){
        account = findViewById(R.id.signup_page_account);
        name   = findViewById(R.id.signup_page_name);
        email   = findViewById(R.id.signup_page_email);
        password = findViewById(R.id.signup_page_password);
        send     = findViewById(R.id.signup_page_send_button);
        hint_account  = findViewById(R.id.signup_page_account_invalid);
        hint_name   = findViewById(R.id.signup_page_name_invalid);
        hint_email  = findViewById(R.id.signup_page_email_invalid);
        hint_password  = findViewById(R.id.signup_page_password_invalid);
        current_dev_code = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        Log.i(staticclass.TAG,current_dev_code);

    }

    private void initvariable(){
        account_str = account.getText().toString().trim();
        name_str   = name.getText().toString().trim();
        email_str   = email.getText().toString().trim();
        password_str  = password.getText().toString().trim();
        context = this;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    // user email and password checking...
    // if not checking, the app will crash
    private boolean checknullandstruction(){
            initvariable();
            if(account_str.length()<5 && password_str.length() < 5 ){
                return false;
            }
            return true;
    }


    private void responsebutton(){
        initvariable();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checknullandstruction()) {
                    firebaseAuth.createUserWithEmailAndPassword(account_str, password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                currentUserID = firebaseUser.getUid();
                                current_dev_code = Settings.Secure.getString(getContentResolver(),
                                        Settings.Secure.ANDROID_ID);
                                reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
                                HashMap<String,String> hashMap = new HashMap<String, String>();
                                hashMap.put(final_static_str_User_Id, currentUserID);
                                hashMap.put(final_static_str_User_Name, name.getText().toString().trim());
                                hashMap.put(final_static_str_User_Email, firebaseUser.getEmail());
                                hashMap.put(final_static_str_User_dev_code, current_dev_code);
                                hashMap.put(final_static_str_User_Login_Method, "Firebase Email Checking");


                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, getResources().getString(R.string.success_signup), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Signuppage.this, Login.class));

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, getResources().getString(R.string.sorry), Toast.LENGTH_LONG).show();
                                    }
                                });



                            } else {
                                Toast.makeText(context,  getResources().getString(R.string.signup_info) + " "+ getResources().getString(R.string.existed), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(context, getResources().getString(R.string.signup_info)  + " " + getResources().getString(R.string.existed),Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
