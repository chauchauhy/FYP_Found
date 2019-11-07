package com.example.fyp_found;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_found.datastru.Text_Recognize;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.fyp_found.setup.staticclass.LOGTAG;

public class ImageClassification extends AppCompatActivity {
    TextView main, sec, thir, fou, fiv, text, text1;
    ImageView showimage;
    Button select, identitfy;
    String main_str , sec_str, thir_str, fou_str, fiv_str;
    Context context;
    Bitmap bitmap;
    TextView t_array[] = new TextView[5];
    List[] list;
    ArrayList<Text_Recognize> text_re = new ArrayList<Text_Recognize>();


   String text_re_Str = "" ;
    // pick up image
    private int picked_image_code = 1;
    private Uri image_uri;   // uri == url for android file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_classification);
        initui();
        initvarible();
        buttonclicked();

    }

    private void initvarible() {
        context  = this;
        t_array[0] = findViewById(R.id.image_label_imagetype_main);
        t_array[1] = findViewById(R.id.image_label_imagetype_sec);
        t_array[2] = findViewById(R.id.image_label_imagetype_thr);
        t_array[3] = findViewById(R.id.image_label_imagetype_fou);
        t_array[4] = findViewById(R.id.image_label_imagetype_fiv);
    }

    private void initui() {
        main = findViewById(R.id.image_label_imagetype_main);
        sec =    findViewById(R.id.image_label_imagetype_sec);
        thir =  findViewById(R.id.image_label_imagetype_thr);
        fou =   findViewById(R.id.image_label_imagetype_fou);
        fiv =  findViewById(R.id.image_label_imagetype_fiv);
        showimage = findViewById(R.id.image_label_image);
        select = findViewById(R.id.image_label_select_btn);
        identitfy = findViewById(R.id.image_label_identitfy_btn);
        text = findViewById(R.id.image_label_imagetype_text);
        // nil via
        text1 = findViewById(R.id.image_label_image_text);
        }

     private void buttonclicked(){
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Please select a image from gallary"),1);
            }
        });
        identitfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap!=null){
                    image_defin(bitmap);
                }
            }
        });
     }
     // find out the image path on device
         @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == picked_image_code && resultCode == RESULT_OK && data!= null && data.getData()!= null){
                image_uri =data.getData();
                Log.i(LOGTAG,String.valueOf(image_uri));

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                showimage.setImageBitmap(bitmap);
                showimage.setVisibility(View.VISIBLE);
            }catch (FileNotFoundException e) {
                Log.i(LOGTAG, e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(LOGTAG,e.toString());
            }
        }
    }
    // mlkit firebase
    private void image_defin(Bitmap bitmap){
        if(bitmap != null) {
            final FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionImageLabeler firebaseVisionImageLabeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();
            firebaseVisionImageLabeler.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                @Override
                public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                    if(firebaseVisionImageLabels.size()>0){
                        for (int i = 0; i < firebaseVisionImageLabels.size(); i++) {
                            t_array[i].setText(firebaseVisionImageLabels.get(i).getText());
                            t_array[i].setVisibility(View.VISIBLE);
                        }
                        text_recognation();
                    }
                    if(firebaseVisionImageLabels.size() <=0){
                        createDialog(getResources().getString(R.string.cannotfindout));
                    }


            }});
        }
        }



    private void createDialog(String exception ){
        new AlertDialog.Builder(context).setTitle(getResources().getString(R.string.warning)).setMessage(getResources().getString(R.string.error) + "\n" + exception).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }

    private void text_recognation(){
        if(bitmap!=null) {
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
            Task<FirebaseVisionText> result = firebaseVisionTextRecognizer.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()){
                            Rect box = block.getBoundingBox();
                            Point[] points = block.getCornerPoints();
                            text_re_Str = text_re_Str+ block.getText();
                            text_re.add(new Text_Recognize(block.getText()));
                            Log.i(LOGTAG, String.valueOf(text_re.size())+ String.valueOf(text_re.get(1).getText()));
//                            text.setText(text_re.get(1).getText());
                        }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else{
        }
    }


}
