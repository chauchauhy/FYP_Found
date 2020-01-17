package com.example.fyp_found;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_found.setup.staticclass;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.DataOutputStream;
import java.util.HashMap;

import static com.example.fyp_found.setup.staticclass.FCM_TOKEN;
import static com.example.fyp_found.setup.staticclass.current_dev_code;
import static com.example.fyp_found.setup.staticclass.final_static_str_Chat_Content;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Email;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Id;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Login_Method;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_dev_code;

public class Login extends AppCompatActivity  {

    private FirebaseAuth firebaseAuth;

    ImageButton submit;
    EditText ac, pw;
    ImageView signup;
    String account,password;
    Context context;
    SignInButton google;
    LoginButton facebook;
    private FirebaseAuth firebaseAuth_google;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth_facebook;
    private CallbackManager callbackManager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initui();
        buttononclick();
    }
    private void initui(){
        submit = findViewById(R.id.btn_enter_login_page);
        ac = findViewById(R.id.ac_login_page);
        pw = findViewById(R.id.pw_login_page);
        signup = findViewById(R.id.Login_page_signup);
        google = findViewById(R.id.login_button_google);
        facebook = findViewById(R.id.login_button_facebook);
        firebaseAuth = FirebaseAuth.getInstance();
        context = this;
        callbackManager = CallbackManager.Factory.create();
        firebaseAuth_facebook = FirebaseAuth.getInstance();
        firebaseAuth_google = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.login_progressbar);
        progressBar.setVisibility(View.GONE);
        initmethodapi();


    }
    private void buttononclick(){

        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                account = ac.getText().toString().trim();
                password = pw.getText().toString().trim();
                if(account.isEmpty() || password.isEmpty()){
                    Toast.makeText(context, "Account or Email and password can not empty", Toast.LENGTH_LONG).show();
                }else {
                    firebaseAuth.signInWithEmailAndPassword(account, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Success login", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this, HomePage.class));

                            } else {
                                Toast.makeText(context, "login failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this , Signuppage.class));
            }
        });

        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
                firebaseAuth_facebook.signInWithCredential(credential)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth_facebook.getCurrentUser();
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put(final_static_str_User_Id, user.getUid());
                                    hashMap.put(final_static_str_User_dev_code, Settings.Secure.getString(getContentResolver(),
                                            Settings.Secure.ANDROID_ID));
                                    hashMap.put(final_static_str_User_Name, user.getDisplayName());
                                    if(user.getEmail().contains("@")&& user.getEmail().contains(".com")){
                                        hashMap.put(final_static_str_User_Email, user.getEmail());
                                    }else{
                                        hashMap.put(final_static_str_User_Email, "Invalid");
                                    }
                                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Login.this, " Current user is " + String.valueOf(firebaseAuth.getCurrentUser().getDisplayName()), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent (Login.this,HomePage.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, getResources().getString(R.string.sorry), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                } else {
                                    Toast.makeText(Login.this, "login Fail " + task.getException(), Toast.LENGTH_SHORT).show();
                                    // ("asdfg", String.valueOf(task.getException()));
                                }
                            }
                        });

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        });
    }
    private void initmethodapi(){
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

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i, 1);            }
        });

    }


    // onActivityResult is use for when user click the sign in with google, the ViewController will be jump to google auth page and login
    // the firebase Authwith google is follow the on Activity Result method , if user click sign in on google the firebase auth with google will be catch the user google data to firebase auth sdk to authen....
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth_google.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth_google.getCurrentUser();
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
                            startActivity(new Intent(Login.this, HomePage.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, getResources().getString(R.string.sorry), Toast.LENGTH_LONG).show();
                        }
                    });

                    Toast.makeText(Login.this, "Google sign in success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Login.this, "Sign in faild",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



    private void ex(){
        getToken g = new getToken();
        g.execute();
    }

    public class getToken extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            final String[] token = new String[1];
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()){
                        Log.i(staticclass.TAG, String.valueOf(task.getException()));

                    }
                    token[0] = task.getResult().getToken();
                    Log.i(staticclass.TAG, token[0]);
                    FCM_TOKEN = token[0];

                }
            });
            return token[0];
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(s);
        }
    }



}
