package com.example.fyp_found;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.fyp_found.datastru.Current_Lost_Record;
import com.example.fyp_found.datastru.Firebase_User;
import com.example.fyp_found.datastru.non_str.Machine_Learning_key;
import com.example.fyp_found.datastru.non_str.Text_Recognize;
import com.example.fyp_found.setup.staticclass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.fyp_found.datastru.Chat_record.getUnixTime;
import static com.example.fyp_found.datastru.Current_Lost_Record.bitmaptoString;
import static com.example.fyp_found.setup.staticclass.current_dev_code;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Boolean;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Text;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_URL;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Address;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_MainType;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA1;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA1_Ans;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA2;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA2_Ans;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type2;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type3;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type4;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type5;
import static com.example.fyp_found.setup.staticclass.final_static_str_db_name_current;
import static com.example.fyp_found.setup.staticclass.question_arr_final;

public class ImageClassification extends AppCompatActivity {
    ProgressBar progressBar;
    TextView text, text1 , showlocation;
    ImageView showimage;
    Button select, identitfy, upload;
    Context context;
    Bitmap bitmap;
    Spinner questionA, questionB;
    EditText questionA_ans, questionB_ans , location_edition, property_name, other_info;
    String questionA_str , questionB_str;
    LinearLayout linearLayout;

    EditText t_array[] = new EditText[5];

    ArrayList<Text_Recognize> text_re = new ArrayList<Text_Recognize>();
    ArrayList<Machine_Learning_key> machine_learning_keys = new ArrayList<>();
    // firebase store
    FirebaseUser firebaseUser = null;
    // get current location
    LocationManager locationManager;
    //user
    Firebase_User user;
    String text_re_Str = "";
    // get url for upload to firestore
    String url_uploaded = "";
    // pick up image
    private int picked_image_code = 1;
    private int take_carma_code = 2;
    private Uri image_uri;   // uri == url for android file
    // get gps  (get last location )
    private int PERMISSIONS_ID = 2;  // request code
    FusedLocationProviderClient fusedLocationProviderClient;
    // nav bar
    BottomNavigationView bottomNavigationView;
    View sublayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_classification);

        initui();
        initvarible();
        buttonclicked();
        setBottomNavigationView();
    }
    private void initvarible() {
        context = this;
        try {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser.getUid() != null && firebaseUser.getEmail()!=null&& firebaseUser.getDisplayName()!=null) {
            user = new Firebase_User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
        }
        if(LocationServices.getFusedLocationProviderClient(this)!=null){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(firebaseUser==null){
            startActivity(new Intent(ImageClassification.this,Login.class));
        }
    }
    private void initui() {
        // image's type
        t_array[0] = findViewById(R.id.image_label_imagetype_main);
        t_array[1] = findViewById(R.id.image_label_imagetype_sec);
        t_array[2] = findViewById(R.id.image_label_imagetype_thr);
        t_array[3] = findViewById(R.id.image_label_imagetype_fou);
        t_array[4] = findViewById(R.id.image_label_imagetype_fiv);
        //imageview
        showimage = findViewById(R.id.image_label_image);
        //button
        select = findViewById(R.id.image_label_select_btn);
        identitfy = findViewById(R.id.image_label_identitfy_btn);
        identitfy.setVisibility(View.GONE);
        upload = findViewById(R.id.image_label_upload);
        // nil via show image text but the function need debug...
        text1 = findViewById(R.id.image_label_image_text);
        text = findViewById(R.id.image_label_imagetype_text);
        // question
        questionA = findViewById(R.id.spinner_questionA);
        questionB = findViewById(R.id.spinner_questionB);
        questionA_ans = findViewById(R.id.questionA_Ans);
        questionB_ans = findViewById(R.id.questionB_Ans);
        spinnerAdapter();
        //property info
        linearLayout=findViewById(R.id.image_defind_layout);
        showlocation = findViewById(R.id.location);
        location_edition = findViewById(R.id.locationEdit);
        property_name = findViewById(R.id.property_name);
        other_info = findViewById(R.id.image_label_otherinfo);

        // other ui elements
        progressBar = findViewById(R.id.image_label_progressbar);
    }
    private void spinnerAdapter(){
        questionA.setPrompt("QuestionA");
        questionA.setSelection(0, true);
        questionA.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                questionA_str = question_arr_final[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                questionA_str = question_arr_final[0];
            }
        });
        ArrayAdapter<String> questionA_adapter = new ArrayAdapter<>(ImageClassification.this, R.layout.support_simple_spinner_dropdown_item, question_arr_final);
        questionA.setAdapter(questionA_adapter);
        questionB.setPrompt("QuestionB");
        questionB.setSelection(0, true);
        questionB.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                questionB_str = question_arr_final[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                questionB_str = question_arr_final[0];
            }
        });
        ArrayAdapter<String> questionB_adapter = new ArrayAdapter<>(ImageClassification.this, R.layout.support_simple_spinner_dropdown_item, question_arr_final);
        questionB.setAdapter(questionB_adapter);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////UPLOAD PART                                                ///////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void upload(){
        if(bitmap!=null){
            progressBar.setVisibility(View.VISIBLE);
            String bitmapToString = bitmaptoString(bitmap);
            if(firebaseUser==null){
                Snackbar.make(linearLayout,"you may need login before upload image", Snackbar.LENGTH_LONG ).setAction("Login?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ImageClassification.this, Login.class));
                    }
                }).show();
            }else{
                uploadimage();
                if(url_uploaded.isEmpty()){
                    Snackbar.make(linearLayout, "please try again after a few minutes" , Snackbar.LENGTH_LONG).show();
                }else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(final_static_str_db_name_current);
                    Current_Lost_Record record = getBasieData();
                    HashMap<String, String> hashMap = returnHashMapData(record);
                    Log.i(staticclass.TAG, hashMap.toString());
                    databaseReference.push().setValue(hashMap);
                    startActivity(new Intent(ImageClassification.this, HomePage.class));
                    progressBar.setVisibility(View.GONE);



                }
                progressBar.setVisibility(View.GONE);

            }
        }else{
            progressBar.setVisibility(View.GONE);
            Snackbar.make(linearLayout, " you may need select a picture for post", Snackbar.LENGTH_LONG).show();
        }
    }

    private HashMap<String, String> returnHashMapData(Current_Lost_Record current_lost_record){
        if(current_lost_record!=null) {
            Current_Lost_Record record = current_lost_record;
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(final_static_str_Current_Lost_ID, record.getCurrent_Lost_ID());
            hashMap.put(final_static_str_Current_Lost_User_Name, record.getCurrent_Lost_User_Name());
            hashMap.put(final_static_str_Current_Lost_Property_Name, record.getCurrent_Lost_Property_Name());
            hashMap.put(final_static_str_Current_Lost_Property_QA1, record.getCurrent_Lost_Property_QA1());
            hashMap.put(final_static_str_Current_Lost_Property_QA2, record.getCurrent_Lost_Property_QA2());
            hashMap.put(final_static_str_Current_Lost_Property_QA1_Ans, record.getCurrent_Lost_Property_QA1_Ans());
            hashMap.put(final_static_str_Current_Lost_Property_QA2_Ans, record.getCurrent_Lost_Property_QA2_Ans());
            hashMap.put(final_static_str_Current_Lost_Address, record.getCurrent_Lost_Address());
            hashMap.put(final_static_str_Current_Lost_Property_MainType, record.getCurrent_Lost_Property_MainType());
            hashMap.put(final_static_str_Current_Lost_type2, record.getCurrent_Lost_type2());
            hashMap.put(final_static_str_Current_Lost_type3, record.getCurrent_Lost_type3());
            hashMap.put(final_static_str_Current_Lost_type4, record.getCurrent_Lost_type4());
            hashMap.put(final_static_str_Current_Lost_type5, record.getCurrent_Lost_type5());
            hashMap.put(final_static_str_Current_Lost_Boolean, record.getFound().toString());
            hashMap.put(final_static_str_Current_Lost_URL, record.getImageURL());
            hashMap.put(final_static_str_Current_Lost_Text, record.getCurrent_Lost_Text());
            return hashMap;
        }else{
            return null;
        }


    }

    private Current_Lost_Record getBasieData(){
        Current_Lost_Record record = new Current_Lost_Record(String.valueOf(getUnixTime()) + firebaseUser.getUid(), firebaseUser.getDisplayName(), property_name.getText().toString(), questionA_str, questionB_str
                , questionA_ans.getText().toString(), questionB_ans.getText().toString(), location_edition.getText().toString(), t_array[0].getText().toString(), t_array[1].getText().toString(), t_array[2].getText().toString(),
                t_array[3].getText().toString(), t_array[4].getText().toString(), other_info.getText().toString(), url_uploaded, "false");
        return record;
    }

    private void uploadimage(){
        String filename = getUnixTime() + current_dev_code;
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + filename);
        if(image_uri !=null) {
            UploadTask uploadTask = storageReference.putFile(image_uri);
            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        return null;
                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Uri uri = task.getResult();
                        String string_uri = uri.toString();
                        url_uploaded = string_uri;
                    }
                }
            });
        }else if(bitmap != null && image_uri == null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = storageReference.putBytes(data);
            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(task.isSuccessful()){
                        return storageReference.getDownloadUrl();
                    }else{
                        return null;
                    }
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Uri uri = task.getResult();
                        String string_uri = uri.toString();
                        url_uploaded = string_uri;
                    }
                }
            });


        }else if(bitmap == null && image_uri == null){
            Log.i(staticclass.TAG, "image file is not exist");
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////END OF UPLOAD                                     //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void buttonclicked() {
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final CharSequence[] options = {"Take picture", "Select picture", "Cancel"};
                final String[] option =  {"Take picture", "Select picture", "Cancel"};
                builder.setTitle("Select options ...").setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(options[i].equals(option[0])){
                            dialogInterface.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, take_carma_code);
                        }else if(options[i].equals(option[1])){
                            dialogInterface.dismiss();
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Please select a image from gallary"), picked_image_code);
                        }else if(options[i].equals(option[2])){
                            dialogInterface.dismiss();
                        }
                    }
                }).show();
            }
        });
        select.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(context, ChatBoxMatrix.class));
                return false;
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlocation();
                upload();
            }
        });
    }
    // find out the image path on device
    // request code == the number OF intent action ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if the result code == picked _ image code
        if (requestCode == picked_image_code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            for(EditText t : t_array){
                t.setText("");
                t.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                text1.setText("");
                text.setText("");
            }
            image_uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                showimage.setImageBitmap(bitmap);
                showimage.setVisibility(View.VISIBLE);
                if(bitmap != null){
                    image_defin(bitmap);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // if the result code == take carma code
        }else if(requestCode == take_carma_code && data!=null){
            for(EditText t : t_array){
                t.setText("");
                t.setVisibility(View.GONE);
                text1.setText("");
                text.setVisibility(View.GONE);
                text.setText("");
            }
            try {
                image_uri = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                showimage.setImageBitmap(bitmap);
                showimage.setVisibility(View.VISIBLE);
                if(bitmap != null){
                    image_defin(bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Log.i(staticclass.TAG, "loss");
        }
    }
    // mlkit firebase
    // part of  image label and text recognation
    private void image_defin(Bitmap bitmap) {
        if (bitmap != null) {
            final FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            final FirebaseVisionImageLabeler firebaseVisionImageLabeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();
            firebaseVisionImageLabeler.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                @Override
                public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                    if (firebaseVisionImageLabels.size() > 0&& firebaseVisionImageLabels.size()<6) {
                        for (int i = 0; i < firebaseVisionImageLabels.size(); i++) {
                            if (firebaseVisionImageLabels.get(i).getText() != null) {
                                t_array[i].setText(firebaseVisionImageLabels.get(i).getText() + "\t");
                                t_array[i].setClickable(true);
                                t_array[i].setFocusable(true);

                                Machine_Learning_key mlclass = new Machine_Learning_key(firebaseVisionImageLabels.get(i).getEntityId(), firebaseVisionImageLabels.get(i).getText(), firebaseVisionImageLabels.get(i).getConfidence());
                                machine_learning_keys.add(mlclass);
                                if (!firebaseVisionImageLabels.get(i).getText().isEmpty()) {
                                    t_array[i].setVisibility(View.VISIBLE);
                                } else {
                                    t_array[i].setVisibility(View.GONE);
                                }
                            }
                        }




                        text_recognation();
                    }else if (firebaseVisionImageLabels.size() <= 0) {
                        createDialog(getResources().getString(R.string.cannotfindout));
                        text_recognation();
                    }
                }
            });
        }
    }
    private void text_recognation() {
        if (bitmap != null) {
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
            Task<FirebaseVisionText> result = firebaseVisionTextRecognizer.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                        Rect box = block.getBoundingBox();
                        Point[] points = block.getCornerPoints();
                        text_re_Str = text_re_Str + block.getText();
                        // this part should be add an error checking such as if else to confirm the firebasevision text array 0 position not null, if not  there will be crash
                        text_re.add(new Text_Recognize(block.getText()));
                        if(text_re.get(0).getText().length()>2 && text_re.get(0).getText().length()<14) {
                            text.setText(text_re.get(0).getText());
                            text.setVisibility(View.VISIBLE);
                        }else{
                            text.setText("");
                            text.setVisibility(View.GONE);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    text.setVisibility(View.GONE);
                }
            });
        } else {
            createDialog(getResources().getString(R.string.warring_no_select_picture));
        }
    }

    private void createDialog(String exception) {
        new AlertDialog.Builder(context).setTitle(getResources().getString(R.string.warning)).setMessage(getResources().getString(R.string.error) + "\n" + exception).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////START OF GET CURRENT LONGItUDE AND LATITUDE /////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////refer to https://www.androdocs.com/java/getting-current-location-latitude-longitude-in-android-using-java.html///////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // check permiisions (GPS)
    private boolean checkPermissions(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }
    private void requestPermissions(){
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_ID);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_ID ){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // todo     haha   do here
            }
        }
    }
    // checking network or GPS function enabled (MAYBE IS NETWORK provider?)
    public boolean isLocationEnabled(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);
    }
    // real get location current
    @SuppressLint("MissingPermission")
    private void getlocation() {
        final ArrayList<String> addressline = new ArrayList<>();
        if(checkPermissions()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if(location!=null){

//                               }
                            String formated = formatAddress(convertTheAddress(location.getLongitude(),location.getLatitude()));
                        }else{
                            requestNewLocations();
                        }
                    }
                });
            }else{
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }else{
            requestPermissions();
        }
    }
    @SuppressLint("requestPermissions")
    private void requestNewLocations(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, location_callback, Looper.myLooper());
        // location callback == update the ui
        // looper == like thread
    }
    private LocationCallback location_callback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            // TODO
            // if current locations changing which action will do ?
            // code here
            Log.i(staticclass.TAG, String.valueOf(locationResult.getLastLocation().getLongitude()));
            Log.i(staticclass.TAG, String.valueOf(locationResult.getLastLocation().getLatitude()));
            Log.i(staticclass.TAG, locationResult.getLastLocation().toString());
        }
    };
    // if user return the application the application will re-catch the current location via get location function ..
    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getlocation();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////END OF GET CURRENT LONGITUDE AND LATITUDE //////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////START  OF GET CURRENT LONGITUDE AND LATITUDE TO ADDRESS (USE API) //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // refer to https://stackoverflow.com/questions/9409195/how-to-get-complete-address-from-latitude-and-longitude
    private String convertTheAddress(double longitude, double lastitude){
        List<Address> addresses = null;
        String address = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lastitude, longitude, 1);
            if(addresses!=null){
                address = addresses.get(0).getAddressLine(0);
                return address;
            }else{
                return address + " address cannot identify";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return address;
        }
    }
    private String formatAddress(String address){
        if(address!=null) {
            String formate = "";
            ArrayList<String> address_arraylist_niv = new ArrayList<>();
            for (String i : address.split(",")) {
                    address_arraylist_niv.add(i);
            }
            if (address_arraylist_niv.size() > 3) {

                address_arraylist_niv.remove(address_arraylist_niv.size()-1);
                address_arraylist_niv.remove(0);
            }
            for(String i : address_arraylist_niv){
                formate = formate + i;
            }
            location_edition.setText(formate);
            location_edition.setVisibility(View.VISIBLE);

            return formate;
        }else{
            createDialog("We cannot find your current location ");
            return "";
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////END OF GET CURRENT LONGITUDE AND LATITUDE TO ADDRESS ////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setBottomNavigationView(){
            sublayout = findViewById(R.id.image_label_bottom_nav_bar);
            bottomNavigationView = sublayout.findViewById(R.id.bottom_nav_bar);
            // set post button at navigation bar enable
            bottomNavigationView.getMenu().findItem(R.id.bottom_nav_bar_post).setChecked(false).setEnabled(false).setCheckable(false);
            bottomNavigationView.getMenu().findItem(R.id.bottom_nav_bar_home).setCheckable(false);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.bottom_nav_bar_home:
                            startActivity(new Intent(ImageClassification.this, HomePage.class));
                            break;
                        case R.id.bottom_nav_bar_post:
                            break;
                        case R.id.bottom_nav_bar_profile:
                            break;
                            default:
                                Log.i(staticclass.TAG, "out of navigation bar menu");
                    }



                    return true;
                }
            });
    }

}