package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.fyp_found.datastru.Current_Lost_Record;
import com.example.fyp_found.setup.staticclass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.example.fyp_found.setup.staticclass.final_static_str_db_name_current;
import static com.example.fyp_found.setup.staticclass.question_arr_final;

public class EditPost extends AppCompatActivity {
    Current_Lost_Record current_lost_record = new Current_Lost_Record();
    View nav_buttom_bar_view, tool_bar_view;
    BottomNavigationView navigationView;
    Toolbar toolbar;

    // main layout ui view
    Context context;
    EditText[] tags = new EditText[5];
    TextView addTags;
    EditText edit_Name, other_info;
    ImageView icon;
    Switch found;
    ConstraintLayout mainlayout_edit;

    // questions 1 and 2 and they answer
    TextView Q1, Q2;
    EditText A1, A2;

    // tool bar layout ui view
    ImageView edit_btn;
    TextView save_text;
    LinearLayout store_tags;


// var
    boolean editmode = false, found_formSwitch = false;
    String q1, q2, a1, a2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        getRecord();
        initUI();
    }
    private void getRecord(){
        context = this;
        Intent i = getIntent();
        String positionconverter = "Position";

        String id = i.getStringExtra(positionconverter);
        for (Current_Lost_Record x : HomePage.Allrecord){
            if (id.equals(x.getCurrent_Lost_ID())){
                current_lost_record = x;
                break;
            }else{

            }
        }
    }

    private void initUI(){
        context = this;
        nav_buttom_bar_view = findViewById(R.id.editPOst_bottom_nav_bar);
        tool_bar_view = findViewById(R.id.editPost_toolbar);
        toolbar = tool_bar_view.findViewById(R.id.app_toolbar_nosearch_1);
        navigationView = nav_buttom_bar_view.findViewById(R.id.bottom_nav_bar);
        icon = findViewById(R.id.edit_post_icon);
        found = findViewById(R.id.edit_post_switch);
        mainlayout_edit = findViewById(R.id.mainlayout_edit);

        Q1 = findViewById(R.id.editpost_spinner_q1);
        Q2 = findViewById(R.id.editpost_spinner_q2);
        A1 = findViewById(R.id.editpost_ans1);
        A2 = findViewById(R.id.editpost_ans2);
        other_info = findViewById(R.id.editPost_other);

        store_tags = findViewById(R.id.editpost_linearlayout);
        tags[0] = findViewById(R.id.editPost_imagetype_main);
        tags[1] = findViewById(R.id.editPost_imagetype_sec);
        tags[2] = findViewById(R.id.editPost_imagetype_thr);
        tags[3] = findViewById(R.id.editPost_imagetype_fou);
        tags[4] = findViewById(R.id.editPost_imagetype_fiv);
        addTags = findViewById(R.id.edit_tag);

        edit_Name = findViewById(R.id.edit_name_property);

        edit_Name.setText(current_lost_record.getCurrent_Lost_Property_Name());


        edit_btn = tool_bar_view.findViewById(R.id.toolbar_edit);
        save_text = tool_bar_view.findViewById(R.id.checking_toolbar_forward);

        Picasso.with(context).load(current_lost_record.getImageURL()).error(R.drawable.icon_app).into(icon);

        found.setChecked(current_lost_record.getFound());
        setToolbar();
        setNavigationView();
        setTags();
        setVar();
        setNextTag();
        setEditAble(false);
        setSwitch();
        setImageDialog();
    }
    private void setImageDialog(){
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mainlayout_edit, "The icon cannot change", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setSwitch(){
        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editmode) {

                    found_formSwitch = false;
                    found_formSwitch = found.isChecked();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(final_static_str_db_name_current);
                    reference.child(current_lost_record.getCurrent_Lost_ID()).child(staticclass.final_static_str_Current_Lost_Boolean).setValue(String.valueOf(found_formSwitch)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Snackbar.make(mainlayout_edit, " The information was updated", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    found_formSwitch = found.isChecked();

                }
            }
        });
    }

    private void setVar(){
        int q1_position = 0, q2_position = 0;
        q1 = current_lost_record.getCurrent_Lost_Property_QA1();
        q2 = current_lost_record.getCurrent_Lost_Property_QA2();
        a1 = current_lost_record.getCurrent_Lost_Property_QA1_Ans();
        a2 = current_lost_record.getCurrent_Lost_Property_QA2_Ans();
//        for (int i=0; i<question_arr_final.length; i++){
//            if (question_arr_final[i].trim().toLowerCase().equals(current_lost_record.getCurrent_Lost_Property_QA1().trim().toLowerCase())){
//                q1_position = i;
//            }else{
//                q1_position = 0;
//            }
//        }
//        for (int i=0; i<question_arr_final.length; i++){
//            if (question_arr_final[i].trim().toLowerCase().equals(current_lost_record.getCurrent_Lost_Property_QA2().trim().toLowerCase())){
//                q2_position = i;
//            }else{
//                q2_position = 0;
//            }
//        }
        A1.setText(a1);
        A2.setText(a2);
//        setupSpinner(q1_position, q2_position);
        Q1.setText(q1);
        Q2.setText(q2);
    }

//    private void setupSpinner(int q1_position, int q2_position){
//
//
//        String[] questionsA1 = question_arr_final;
//        String[] questionsB1 = question_arr_final;
//        if (q1_position != 0){
//            questionsA1[0] = question_arr_final[q1_position];
//            questionsA1[q1_position] = question_arr_final[0];
//        }
//        if (q2_position != 0){
//            questionsB1[0] = question_arr_final[q2_position];
//            questionsB1[q2_position] = question_arr_final[0];
//        }
//
//         final String[] questionsA = question_arr_final;
//         final String[] questionsB = question_arr_final;
//
//
//        Q1.setPrompt("Question A");
//        Q2.setPrompt("Question B");
//        Q1.setSelection(0, true);
//        Q2.setSelection(0, true);
//
//        Q1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                q1 = questionsA[i];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                if (q1.isEmpty()){
//                    q1 = questionsA[0];
//                }
//
//            }
//        });
//        Q2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                q2 = questionsB[i];
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                if (q2.isEmpty()) {
//                    q2 = questionsB[0];
//                }
//
//            }
//        });
//        ArrayAdapter<String> questionA_adapter = new ArrayAdapter<>(EditPost.this, R.layout.support_simple_spinner_dropdown_item, question_arr_final);
//        Q1.setAdapter(questionA_adapter);
//        ArrayAdapter<String> questionB_adapter = new ArrayAdapter<>(EditPost.this, R.layout.support_simple_spinner_dropdown_item, question_arr_final);
//        Q2.setAdapter(questionB_adapter);
//
//
//
//    }

    private void setTags(){
        String[] tags = current_lost_record.getTags();
        for (int i = 0; i < tags.length ; i++){
            if (tags[i]==null){
            }else {
                if (!tags[i].equals("Unknown")) {
                    this.tags[i].setText(tags[i]);
                    this.tags[i].setVisibility(View.VISIBLE);
                    this.tags[i].setFocusable(true);
                    this.tags[i].setClickable(true);
                    store_tags.setVisibility(View.VISIBLE);

                }
            }
        }
    }

    public void setNextTag(){

            addTags.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getNextTag();

                }

        });
    }

    private void getNextTag(){
        for (int i = 0; i <tags.length ; i++){
           if (tags[i].getText().toString().trim().isEmpty()){
               tags[i].setHint("Tag");
               tags[i].setFocusable(true);
               tags[i].setClickable(true);
               tags[i].setVisibility(View.VISIBLE);
               store_tags.setVisibility(View.VISIBLE);
               break;
           }else{
               continue;
           }
        }
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editmode){
                    setEditAble(false);
                    updateToolBarView(false);
                    editmode = false;
                }else{
                    startActivity(new Intent(context, Profile.class));
                }
            }
        });
        edit_btn.setVisibility(View.VISIBLE);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditAble(true);
                updateToolBarView(true);
                editmode = true;
            }
        });

        save_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditAble(false);
                updateToolBarView(false);
                editmode = false;
                saveData();
            }
        });

    }

    private void saveData(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(final_static_str_db_name_current);
        changeValueToClass();


//        databaseReference.child(current_lost_record.getCurrent_Lost_ID()).removeValue();
        databaseReference.child(current_lost_record.getCurrent_Lost_ID()).child(staticclass.final_static_str_Current_Lost_Property_QA1_Ans).setValue(A1.getText().toString().trim());
        databaseReference.child(current_lost_record.getCurrent_Lost_ID()).child(staticclass.final_static_str_Current_Lost_Property_QA2_Ans).setValue(A2.getText().toString().trim());
        databaseReference.child(current_lost_record.getCurrent_Lost_ID()).child(staticclass.final_static_str_Current_Lost_Property_Name).setValue(edit_Name.getText().toString().trim());
        databaseReference.child(current_lost_record.getCurrent_Lost_ID()).child(staticclass.final_static_str_Current_Lost_Text).setValue(other_info.getText().toString().trim()); // remark of property
        for (int i = 0; i<tags.length; i++) {   // the position 8 is the main type of property on the array from current lost record class, the 12 is the type 5
            if (tags[i].getText() != null) {
                if (!tags[i].getText().toString().trim().isEmpty()){
                    databaseReference.child(current_lost_record.getCurrent_Lost_ID()).child(staticclass.final_static_str_array_Current_Lost_Array[i + 8]).setValue(tags[i].getText().toString().trim());
                }else{
                    databaseReference.child(current_lost_record.getCurrent_Lost_ID()).child(staticclass.final_static_str_array_Current_Lost_Array[i + 8]).setValue("Unknown");

                }
            }
        }
        databaseReference.child(current_lost_record.getCurrent_Lost_ID()).child(staticclass.final_static_str_Current_Lost_Boolean).setValue(String.valueOf(found.isChecked())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Snackbar.make(mainlayout_edit, "The detail of the post was updated", Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(mainlayout_edit, getResources().getString(R.string.sorry), Snackbar.LENGTH_LONG).show();
                }
            }
        });







    }

    private void changeValueToClass(){
        String[] tags = new String[5];
            current_lost_record.setCurrent_Lost_Property_Name(edit_Name.getText().toString().trim());
            current_lost_record.setCurrent_Lost_Text(other_info.getText().toString().trim());
            current_lost_record.setCurrent_Lost_Property_QA1_Ans(A1.getText().toString().trim());
            current_lost_record.setCurrent_Lost_Property_QA2_Ans(A2.getText().toString().trim());
        for (int i =0; i<this.tags.length; i++){
            if (this.tags[i].getText() != null){
                tags[i] = this.tags[i].getText().toString().trim();
            }
        }
        current_lost_record.setAllType(tags);
        current_lost_record.setFound(String.valueOf(found.isChecked()));



    }


    private void setNavigationView(){
        MenuItem profile = navigationView.getMenu().findItem(R.id.bottom_nav_bar_profile);
        profile.setCheckable(true);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case  R.id.bottom_nav_bar_home:
                        startActivity(new Intent(context, HomePage.class));
                        break;
                    case R.id.bottom_nav_bar_post:
                        startActivity(new Intent(context, ImageClassification.class));
                        break;
                    case R.id.bottom_nav_bar_profile:
                        startActivity(new Intent(context, Profile.class));
                        break;
                    default:

                }
                return false;
            }
        });



    }

    private void setEditAble(boolean able){
        edit_Name.setClickable(able);
        edit_Name.setFocusable(able);
        edit_Name.setFocusableInTouchMode(able);

        addTags.setClickable(able);

//        found.setClickable(able);
//        found.setFocusable(able);

        Q1.setClickable(able);
        Q1.setFocusable(able);
        Q2.setClickable(able);
        Q2.setFocusable(able);
        A1.setClickable(able);
        A1.setFocusable(able);
        A1.setFocusableInTouchMode(able);
        A2.setClickable(able);
        A2.setFocusable(able);
        A2.setFocusableInTouchMode(able);

        for (EditText x : tags){
            x.setFocusableInTouchMode(able);
            x.setClickable(able);
            x.setFocusable(able);
        }
        other_info.setFocusable(able);
        other_info.setClickable(able);
        other_info.setFocusableInTouchMode(able);

    }

    private void updateToolBarView(boolean able){
        if (able){
            edit_btn.setVisibility(View.GONE);
            save_text.setVisibility(View.VISIBLE);
            save_text.setClickable(true);

        }else{
            edit_btn.setVisibility(View.VISIBLE);
            save_text.setClickable(false);
            save_text.setVisibility(View.GONE);
        }
    }
}
