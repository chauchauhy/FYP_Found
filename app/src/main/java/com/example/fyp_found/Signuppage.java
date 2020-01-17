package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp_found.setup.staticclass;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;

import static com.example.fyp_found.setup.staticclass.FCM_TOKEN;
import static com.example.fyp_found.setup.staticclass.currentUserID;
import static com.example.fyp_found.setup.staticclass.current_dev_code;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Email;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Id;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Login_Method;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_dev_code;


// sign up page ....

public class Signuppage extends AppCompatActivity {
    EditText  email,  password;
    ImageButton send;
    boolean userinformationisvaild = false;
    String  email_str, password_str;
    Context context;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;

    int GOOGLE_API_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);
        context = this;
        firebaseAuth = FirebaseAuth.getInstance();
        initui();
        initvariable();
        responsebutton();
    }



    private void initui(){
        email   = findViewById(R.id.signup_page_email);
        password = findViewById(R.id.signup_page_password);
        send     = findViewById(R.id.signup_page_send_button);
        signInButton = findViewById(R.id.signup_page_signwithgoogle);

    }

    private void initvariable(){
        email_str   = email.getText().toString().trim();
        password_str= password.getText().toString().trim();
    }

    // user email and password checking...
    private boolean checknullandstruction(){
        if(!email.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    // use email and password to create an user

    private void responsebutton(){
        initvariable();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checknullandstruction()) {
                    initvariable();

                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                currentUserID = firebaseUser.getUid();
                                reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
                                final HashMap<String,String> hashMap = new HashMap<String, String>();
                                hashMap.put(final_static_str_User_Id, currentUserID);
                                hashMap.put(final_static_str_User_Name, firebaseUser.getDisplayName());
                                hashMap.put(final_static_str_User_Email, firebaseUser.getEmail());
                                hashMap.put(final_static_str_User_dev_code, current_dev_code);
                                hashMap.put(final_static_str_User_Login_Method, "Firebase Email Checking");


                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, getResources().getString(R.string.success_signup), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Signuppage.this, HomePage.class));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    private void setSignInButton(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        })  .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i, GOOGLE_API_RESULT_CODE);
            }
        });

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount){
        if (googleSignInAccount!=null){
            AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        final HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(final_static_str_User_Id, firebaseUser.getUid());
                        hashMap.put(final_static_str_User_Name, firebaseUser.getDisplayName());
                        hashMap.put(final_static_str_User_Login_Method, "Google");
                        hashMap.put(final_static_str_User_dev_code, current_dev_code);

                        hashMap.put(staticclass.firebase_FCM_Token, FCM_TOKEN);
                        Log.i(staticclass.TAG, hashMap.toString());

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, getResources().getString(R.string.success_signup), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Signuppage.this, HomePage.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, getResources().getString(R.string.sorry), Toast.LENGTH_LONG).show();
                            }
                        });

                        Toast.makeText(Signuppage.this, "Google sign in success", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Signuppage.this, "Sign in faild",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }



    }



}



