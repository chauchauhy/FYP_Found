package com.example.fyp_found;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity  {

    private FirebaseAuth firebaseAuth;

    Button submit;
    EditText ac, pw;
    TextView signup;
    String account,password;
    Context context;
    SignInButton google;
    LoginButton facebook;
    private FirebaseAuth firebaseAuth_google;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth_facebook;
    private CallbackManager callbackManager;


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
        initmethodapi();


    }
    private void buttononclick(){

        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = ac.getText().toString().trim();
                password = pw.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(account,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Success login", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "login failed" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        submit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
             startActivity(new Intent(Login.this,ImageClassification.class));




                return false;
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this , welcomepage.class));
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
                                    Toast.makeText(Login.this, " Current user is " + String.valueOf(user.getDisplayName()), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Login.this, "Google sign in success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Login.this, "Sign in faild",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }






    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////TO DO complete the ui and ux here                                                                                            ////////////////////////////////////////////////////















    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
