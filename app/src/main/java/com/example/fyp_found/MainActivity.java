package com.example.fyp_found;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp_found.datastru.non_str.User;
import com.example.fyp_found.setup.staticclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.fyp_found.setup.staticclass.final_static_str_User_Account;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Email;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Id;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Level;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Login_Method;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Password;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_dev_code;
import static com.example.fyp_found.setup.staticclass.final_static_str_db_name_user;
import static com.example.fyp_found.setup.staticclass.localdb_URL_test;

public class MainActivity extends AppCompatActivity {

    // please defind some static variable here
    // test page//

    Button b1,b2,b3,b4;
    Button ba[] = new Button[4] ;
    int str[] = new int[4];
    TextView show;
    Context context;
    StringRequest stringRequest;
    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        ba[0] = findViewById(R.id.b1);
        ba[1] = findViewById(R.id.b2);
        ba[2] = findViewById(R.id.b3);
        ba[3] = findViewById(R.id.b4);
        show = findViewById(R.id.show);

        str[0] = 0;
        str[1] = 1;
        str[2] = 2;
        str[3] = 3;

        getText t = new getText();
        t.execute("hi");


        for(int i = 0; i< 4; i++){
            ba[i].setText(String.valueOf(str[i]));
        }
        // use array to show some data -- such as Machine learning kit
        // this is setup activity for inittial SDK(facebook and firebase and google sdk
        // please dont coding here


    }


    public class getText extends AsyncTask<String , Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            volley_get();
            return strings[0];
        }
    }
    public void volley_get(){
        requestQueue = Volley.newRequestQueue(context);
        stringRequest = new StringRequest(localdb_URL_test, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(staticclass.TAG, response);
                jsontoarry(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    public void jsontoarry(String json){
        try {
            JSONObject jo = new JSONObject(json);
            JSONArray jsonArray = jo.getJSONArray(final_static_str_db_name_user);
            if(jsonArray.length() >1){}
            for(int i = 0; i<=jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String User_Id           =  jsonObject.getString(final_static_str_User_Id);
                String User_Name         =  jsonObject.getString(final_static_str_User_Name);
                String User_Account      = jsonObject.getString(final_static_str_User_Account);
                String User_Password     =  jsonObject.getString(final_static_str_User_Password);
                String User_Email        = jsonObject.getString(final_static_str_User_Email);
                String User_Login_Method = jsonObject.getString(final_static_str_User_Login_Method);
                String User_Level        =  jsonObject.getString(final_static_str_User_Level);
                String User_dev_code     =  jsonObject.getString(final_static_str_User_dev_code);
                User user=  new User(User_Id,User_Name,User_Account,User_Password,User_Email,User_Login_Method,User_Level,User_dev_code);
                Log.i(staticclass.TAG,user.toString());

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(staticclass.TAG,e.toString());
        }
    }



}
