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
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.fyp_found.setup.staticclass.LOGTAG;
import static com.example.fyp_found.setup.staticclass.dev_code;
import static java.security.AccessController.getContext;


// sign up page ....

public class welcomepage extends AppCompatActivity {
    EditText account, name, email, password;
    Button send;
    boolean userinformationisvaild = false;
    String account_str , name_str , email_str, password_str;
    TextView hint_account, hint_name, hint_email, hint_password;
    Context context;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;


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
        dev_code  = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.i(LOGTAG,dev_code);

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

    private void initvariable(){
        account_str = account.getText().toString().trim();
        name_str   = name.getText().toString().trim();
        email_str   = email.getText().toString().trim();
        password_str  = password.getText().toString().trim();
        context = this;
        firebaseAuth = FirebaseAuth.getInstance();

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
                                Toast.makeText(context, "success sign up", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(welcomepage.this, Login.class));

                            } else {
                                Toast.makeText(context, "the account or email are exist", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(context,"invalid",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
