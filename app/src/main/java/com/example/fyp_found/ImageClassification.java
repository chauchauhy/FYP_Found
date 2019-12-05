package com.example.fyp_found;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fyp_found.datastru.Firebase_User;
import com.example.fyp_found.datastru.non_str.Machine_Learning_key;
import com.example.fyp_found.datastru.non_str.Text_Recognize;
import com.example.fyp_found.setup.staticclass;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ImageClassification extends AppCompatActivity {
    TextView text, text1;
    ImageView showimage;
    Button select, identitfy, upload;
    Context context;
    Bitmap bitmap;
    TextView t_array[] = new TextView[5];
    List[] list;
    ArrayList<Text_Recognize> text_re = new ArrayList<Text_Recognize>();
    ArrayList<Machine_Learning_key> machine_learning_keys = new ArrayList<>();

    // firebase store
    FirebaseUser firebaseUser = null;
    DatabaseReference reference = null;

    // get current location
    LocationManager locationManager;

    //user
    Firebase_User user;
    String text_re_Str = "";
    // pick up image
    private int picked_image_code = 1;
    private Uri image_uri;   // uri == url for android file


    // get gps  (get last location )
    private int PERMISSIONS_ID = 2;  // request code
    FusedLocationProviderClient fusedLocationProviderClient;
    String current_City, current_Street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_classification);
        initui();
        initvarible();
        buttonclicked();



    }

    private void initvarible() {
        context = this;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser.getUid() != null && firebaseUser.getEmail()!=null&& firebaseUser.getDisplayName()!=null) {
            user = new Firebase_User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
        }
        if(LocationServices.getFusedLocationProviderClient(this)!=null){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
        upload = findViewById(R.id.image_label_upload);


        // nil via show image text but the function need debug...
        text1 = findViewById(R.id.image_label_image_text);
        text = findViewById(R.id.image_label_imagetype_text);

    }

    private void buttonclicked() {
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please select a image from gallary"), 1);
            }
        });
        identitfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    image_defin(bitmap);
                }
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
            }
        });
    }

    // find out the image path on device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == picked_image_code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            image_uri = data.getData();
            Log.i(staticclass.TAG, String.valueOf(image_uri));

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                showimage.setImageBitmap(bitmap);
                showimage.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                Log.i(staticclass.TAG, e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(staticclass.TAG, e.toString());
            }
        }
    }

    // mlkit firebase
    // part of  image label and text recognation
    private void image_defin(Bitmap bitmap) {
        if (bitmap != null) {
            final FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionImageLabeler firebaseVisionImageLabeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();
            firebaseVisionImageLabeler.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                @Override
                public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                    if (firebaseVisionImageLabels.size() > 0) {
                        for (int i = 0; i < firebaseVisionImageLabels.size(); i++) {
                            t_array[i].setText(firebaseVisionImageLabels.get(i).getText());
                            Machine_Learning_key mlclass = new Machine_Learning_key(firebaseVisionImageLabels.get(i).getEntityId(), firebaseVisionImageLabels.get(i).getText(), firebaseVisionImageLabels.get(i).getConfidence());
                            machine_learning_keys.add(mlclass);


                            if (t_array[i].getText().toString().trim().length() > 2) {
                                t_array[i].setVisibility(View.VISIBLE);
                            } else {
                                t_array[i].setVisibility(View.GONE);
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
//                            Log.i(staticclass.TAG, String.valueOf(text_re.size())+ String.valueOf(text_re.get(0).getText()));
                        text.setText(text_re.get(0).getText());
                        text.setVisibility(View.VISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {
            createDialog(getResources().getString(R.string.warring_no_select_picture));
        }
    }
    // bitmap to String for entering the firebase database ... so we dont need the local or non local database
    // start of bitmap convert (String to bitmap and bitmap to String)
    // input to firebase store ....
    private String bitmaptoString(Bitmap bitmap) {
        String s = null;
        // error checking
        if (bitmap != null) {
            try {
                ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayInputStream);
                byte[] b = byteArrayInputStream.toByteArray();
                String temp_Str = Base64.encodeToString(b, Base64.DEFAULT);
                // encode the bitmap
                return temp_Str;
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }
        return s;

    }

    private Bitmap Stringtobitmap(String convert_string) {
        Bitmap bitmap = null;
        // error checking
        if (convert_string != null) {
            try {
                // init byte and bitmap
                byte[] bytes;
                bytes = Base64.decode(convert_string, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // decode String
            } catch (Exception e) {
                e.printStackTrace();

            }


            return bitmap;
        } else {
            return bitmap;
        }
    }

    // end of bitmap convert
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
                               for(String s : convertTheAddress(location.getLongitude(), location.getLatitude()).split(",")){
                                   addressline.add(s);
                                   Log.i(staticclass.TAG, s);
                               }
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
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////END OF GET CURRENT LONGITUDE AND LATITUDE TO ADDRESS ////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    // store
//    private void firebaseStore(Current_Lost_Record current_lost_record){
//
//        if(current_lost_record!=null && firebaseUser !=null){
//            reference = FirebaseDatabase.getInstance().getReference(final_static_str_db_name_current);
//            HashMap<String, String > hashMap = new HashMap<>();
//
//        }
//
//    }

}
