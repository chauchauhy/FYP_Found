package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp_found.setup.staticclass;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.fyp_found.datastru.Chat_record.getUnixTime;
import static com.example.fyp_found.datastru.Current_Lost_Record.Stringtobitmap;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Account;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Email;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Id;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Level;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Login_Method;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Password;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_dev_code;
import static com.example.fyp_found.setup.staticclass.final_static_str_db_name_user;

public class MainActivity extends AppCompatActivity {

    // please defind some static variable here
    // test page//
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////THIS ACTIVITY IS USE FOR TEST UI AND GET SOME VARIABLE ON NETWORK///////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Button b1,b2,b3,b4;
    Button ba[] = new Button[4] ;
    int str[] = new int[4];
    TextView show;
    Context context;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ImageView imageView;
    DatabaseReference databaseReference;
    ArrayList<String> strings = new ArrayList<>();
    Uri uri_;
    Bitmap bitmap;
    String uriStr = null;
    ProgressDialog  progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        te1();
        context = this;

        // use array to show some data -- such as Machine learning kit
        // this is setup activity for inittial SDK(facebook and firebase and google sdk
        // please dont coding here


    }
    public void te1(){
        context = this;

        ba[0] = findViewById(R.id.b1);
        ba[1] = findViewById(R.id.b2);
        ba[2] = findViewById(R.id.b3);
        ba[3] = findViewById(R.id.b4);
        show = findViewById(R.id.show);
        imageView = findViewById(R.id.textimage);

        progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setMessage("Loading..");
        progressBar.setTitle("Get Data");
        progressBar.setIndeterminate(false);
        progressBar.setCancelable(true);
        str[0] = 0;
        str[1] = 1;
        str[2] = 2;
        str[3] = 3;

        ba[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
        ba[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please select a image from gallary"), 2);
            }
        });
        ba[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 &&  data != null){

            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);

        }else if(requestCode ==2 && data !=null && data.getData()!=null){
            Uri uri = data.getData();
            String uriString = uri.toString();
            Log.i(staticclass.TAG,uriString);
            File mFile =  new File(uriString);
            String path = mFile.getAbsolutePath();
            Log.i(staticclass.TAG,path);
            String displayName = null;
            Uri image_uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
//                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                uri_ = data.getData();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // if there no any exception the uri path should be content:// and file://
            // error checking
            if(uriString.startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = mFile.getName();
            }

            Log.i(staticclass.TAG,displayName);
        }

    }

    private void upload(){
        progressBar.show();
        String test = null;
        final String time = String.valueOf(getUnixTime());

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        StorageReference mountainsRef = storageRef.child(time);

        //
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i(staticclass.TAG, exception.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(staticclass.TAG, String.valueOf(taskSnapshot.getUploadSessionUri()));

            }
        });
//        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        final StorageReference ref = storageRef.child("images/"+ time );
        // put file ####################
        uploadTask = ref.putFile(uri_);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    uriStr = downloadUri.toString();
                    if(String.valueOf(uriStr).startsWith("https://firebasestorage.googleapis.com")) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+ time);

                        String url = downloadUri.toString();


                        Log.i(staticclass.TAG, "ASAS         " + downloadUri.toString());
                        Log.i(staticclass.TAG, "ASAS         " + storageReference.getDownloadUrl().toString());
                    }else{
                        Log.i(staticclass.TAG, "non");
                    }
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

        Picasso.with(context).load(uriStr).error(R.mipmap.blue_circle).into(imageView);
        progressBar.dismiss();

//        load(imageView, uriStr);
        // use this!


    }

    private void load(ImageView i, String s){
        dow d = new dow(i);
        d.execute(s);
    }

    private class dow extends AsyncTask<String,Void,Bitmap>{
        ImageView i;
        public dow(ImageView imageView){
            i = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            String u = strings[0];
            try{
                InputStream in = new URL(u).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } ;
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            i.setImageBitmap(bitmap);
            progressBar.dismiss();
        }
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
        stringRequest = new StringRequest("", new Response.Listener<String>() {
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
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(staticclass.TAG,e.toString());
        }
    }

    // test get image String from firebase
    public void te2(){
        databaseReference = FirebaseDatabase.getInstance().getReference("current_lost_record");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    for(String i : hashMap.keySet()){
                        Object o = hashMap.get(i);
                        try {
                            HashMap<String, Object> hashMap1 = (HashMap<String, Object>) o;
                            String aa = (String) hashMap1.get("text");
                            imageView.setImageBitmap(Stringtobitmap(aa));
                            imageView.setVisibility(View.VISIBLE);
                            Log.i(staticclass.TAG,aa );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    // test edittext disable
    public void te3(){}



}
