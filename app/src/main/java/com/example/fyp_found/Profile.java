package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp_found.Adapter.Profile_Post_Adapter;
import com.example.fyp_found.datastru.Current_Lost_Record;
import com.example.fyp_found.datastru.Firebase_User;
import com.example.fyp_found.datastru.Reward;

import com.example.fyp_found.setup.staticclass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.fyp_found.setup.staticclass.IntentTAG;
import static com.example.fyp_found.setup.staticclass.TAG;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Address;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Boolean;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_MainType;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA1;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA1_Ans;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA2;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA2_Ans;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Text;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_URL;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_User_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type2;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type3;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type4;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type5;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_unix_time;
import static com.example.fyp_found.setup.staticclass.final_static_str_Found_property_Content;
import static com.example.fyp_found.setup.staticclass.final_static_str_Reward_Content;
import static com.example.fyp_found.setup.staticclass.final_static_str_Reward_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Reward_Lost_Address;
import static com.example.fyp_found.setup.staticclass.final_static_str_Reward_Title;
import static com.example.fyp_found.setup.staticclass.final_static_str_Reward_UserID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Reward_property_Type;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Email;
import static com.example.fyp_found.setup.staticclass.final_static_str_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_db_name_current;
import static com.example.fyp_found.setup.staticclass.final_static_str_firebasedatabase_child_users;
import static com.example.fyp_found.setup.staticclass.image_Anchor;
import static com.example.fyp_found.setup.staticclass.post_Anchor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class Profile extends AppCompatActivity {
    // Main UI View including edit user name and user email
    View toolbar_subview, nav_bar_subView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout main_Layout;

    Context context;
    FirebaseUser firebaseUser;
    Firebase_User firebase_user;
    int previousActivity;
    EditText username_edit, useremail_edit;
    ImageView usericon_imageView;
    ImageButton logout, editPersonalInfo;
    TextView done;
    public static ArrayList<Current_Lost_Record> records = new ArrayList<>();
    Profile_Post_Adapter profile_post_adapter;

// var
    boolean editmode = false;

    // other UI View
    // including display lost record and post


    RecyclerView post, reword;
    ArrayList<Reward> rewards = new ArrayList<>();
    boolean post_state = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;
        getprevious();
        firstCheckingUserInfo();

    }
    private void getprevious(){
        Intent i = getIntent();
        if (i.getStringExtra(IntentTAG)!=null){
            previousActivity = Integer.valueOf(i.getStringExtra(IntentTAG));
        }else{
            previousActivity = 0;

        }
    }
    private void loadData(){
//        loadPostData();
        loadRecordData();
    }

    private void firstCheckingUserInfo(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Toast.makeText(this, "You may need login or sign up to use this function", Toast.LENGTH_LONG).show();
            setToolbarNavBtn(0);
        }else{
            initVar();
            initUI();
            loadUserInfo();
            loadRecordData();
        }
    }

    private void setToolbarNavBtn(int id){
        switch (id){
            case image_Anchor:
                startActivity(new Intent(context, ImageClassification.class));
                break;
            case post_Anchor:
                startActivity(new Intent(context, Post.class));
                break;
             default:
                startActivity(new Intent(context, HomePage.class));
                break;
        }

    }

    private void loadUserInfo(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(staticclass.final_static_str_firebasedatabase_child_users);
        if (HomePage.firebase_user_current!=null) {
            firebase_user = HomePage.firebase_user_current;
        }else{
            firebase_user = new Firebase_User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    // real time update the user personal info
                    HashMap<String, Object> hashMap = (HashMap) dataSnapshot.getValue();
                    for (String key : hashMap.keySet()){
                        Object key_ = hashMap.get(key);
                        try {
                            HashMap<String, Object> userInfoCollection = (HashMap<String, Object>) key_;
                            if (userInfoCollection.get(staticclass.final_static_str_User_Id).equals(firebaseUser.getUid())){
                                firebase_user.setIcon_URL((String) userInfoCollection.get(staticclass.final_static_str_User_Icon_URL));
                                firebase_user.setUser_Email((String) userInfoCollection.get(staticclass.final_static_str_User_Email));
                                firebase_user.setUser_Name((String) userInfoCollection.get(staticclass.final_static_str_User_Name));
                                updateUI(firebase_user);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "it seem that there is some network issues", Toast.LENGTH_LONG).show();
            }
        });
        updateUI(firebase_user);
    }

    private void updateUI(Firebase_User firebase_user){
        if (firebase_user!=null){
            if(firebase_user.getIcon_URL()!=null){
                Picasso.with(context).load(firebase_user.getIcon_URL()).error(getResources().getDrawable(R.drawable.profile_imagebtn_icon)).into(usericon_imageView);
            }else{
                usericon_imageView.setImageDrawable(getDrawable(R.drawable.profile_imagebtn_icon));
            }
            if (firebase_user.getUser_Name() != null){
                username_edit.setText(firebase_user.getUser_Name());
            }else{
                username_edit.setHint(null);
            }
            if(firebase_user.getUser_Email() != null){
                useremail_edit.setText(firebase_user.getUser_Email());
            }else{
                useremail_edit.setHint(null);
            }

        }
    }
    private void setEditTextAble(boolean able){
                useremail_edit.setClickable(able);
                useremail_edit.setFocusableInTouchMode(able);
                useremail_edit.setFocusable(able);

                username_edit.setClickable(able);
                username_edit.setFocusableInTouchMode(able);
                username_edit.setFocusable(able);
    }
    // error checking if user not login he/she will turn to loading page for select login or sign up
    private void setPortal(){
        if (firebaseUser == null){
            startActivity(new Intent(context, LoadingPage.class));
        }
    }
    private void initVar(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        setPortal();
    }
    private void initUI(){
        toolbar_subview = findViewById(R.id.profile_toolbar);
        nav_bar_subView = findViewById(R.id.profile_bottom_nav_bar);
        toolbar = toolbar_subview.findViewById(R.id.app_toolbar_nosearch_1);
        main_Layout = findViewById(R.id.profile_main_layout);
        useremail_edit = findViewById(R.id.profile_email);
        username_edit = findViewById(R.id.profile_username);
        usericon_imageView = findViewById(R.id.profile_user_icon);
        editPersonalInfo = toolbar_subview.findViewById(R.id.toolbar_edit);
        logout = toolbar_subview.findViewById(R.id.toolbar_logout);
        editPersonalInfo.setVisibility(View.VISIBLE);
        logout.setVisibility(View.VISIBLE);
        done = toolbar_subview.findViewById(R.id.checking_toolbar_forward);
        done.setClickable(true);

        // other ui view
        post = findViewById(R.id.profile_lostPost);
        post.setVisibility(View.VISIBLE);

        setToolbar();
        setBottomNavigationView();

    }

    private void loadRecordData(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(final_static_str_db_name_current);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    records.clear();
                    HashMap<String, Object> hashMap;
                    hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    for (String key : hashMap.keySet()) {
                        Object key_ = hashMap.get(key);
                        try {
                            HashMap<String, Object> data = (HashMap<String, Object>) key_;
                            Current_Lost_Record record =
                                    new Current_Lost_Record(
                                            (String) data.get(final_static_str_Current_Lost_ID),
                                            (String) data.get(final_static_str_Current_Lost_User_ID),
                                            (String) data.get(final_static_str_Current_Lost_Property_Name),
                                            (String) data.get(final_static_str_Current_Lost_Property_QA1),
                                            (String) data.get(final_static_str_Current_Lost_Property_QA2),
                                            (String) data.get(final_static_str_Current_Lost_Property_QA1_Ans),
                                            (String) data.get(final_static_str_Current_Lost_Property_QA2_Ans),
                                            (String) data.get(final_static_str_Current_Lost_Address),
                                            (String) data.get(final_static_str_Current_Lost_Property_MainType),
                                            (String) data.get(final_static_str_Current_Lost_type2),
                                            (String) data.get(final_static_str_Current_Lost_type3),
                                            (String) data.get(final_static_str_Current_Lost_type4),
                                            (String) data.get(final_static_str_Current_Lost_type5),
                                            (String) data.get(final_static_str_Current_Lost_Text),
                                            (String) data.get(final_static_str_Current_Lost_URL),
                                            (String) data.get(final_static_str_Current_Lost_Boolean),
                                            (String) data.get(final_static_str_Current_Lost_unix_time)
                                    );
                                if (record.getCurrent_Lost_User_ID().equals(firebaseUser.getUid())){
                                    records.add(record);

                                }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    setPost_List(context, records);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setPost_List(Context context, ArrayList<Current_Lost_Record> records){

        ArrayList<Current_Lost_Record> orginList = new ArrayList<>();
        ArrayList<Current_Lost_Record> newList = new ArrayList<>();
        orginList = records;
        Collections.sort(orginList, Current_Lost_Record.getCombyTime());
        for (int i = orginList.size()-1; i>=0 ; i-- ){
            newList.add(orginList.get(i));
        }



        profile_post_adapter = new Profile_Post_Adapter(context, newList);
        post.setLayoutManager(new LinearLayoutManager(context));
        post.setAdapter(profile_post_adapter);

        // load post data from firebase realtime DB..
    }
    private void loadPostData(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(staticclass.final_static_str_db_name_reward);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    rewards.clear();
                    // real time update the user personal info
                    HashMap<String, Object> hashMap = (HashMap) dataSnapshot.getValue();
                    for (String key : hashMap.keySet()) {
                        Object key_ = hashMap.get(key);
                        try {
                            HashMap<String, Object> rewardCollection = (HashMap<String, Object>) key_;
                            if (rewardCollection.get(staticclass.final_static_str_Reward_UserID).equals(firebase_user.getUser_Id())){
                                Reward reward = new Reward();
                                reward.setReward_ID((String) rewardCollection.get(final_static_str_Reward_ID));
                                reward.setReward_UserID((String) rewardCollection.get(final_static_str_Reward_UserID));
                                reward.setReward_Content((String) rewardCollection.get(final_static_str_Reward_Content));
                                reward.setReward_Lost_Address((String) rewardCollection.get(final_static_str_Reward_Lost_Address));
                                reward.setReward_Title((String) rewardCollection.get(final_static_str_Reward_Title));

                                // need modify data type
                                reward.setReward_property_Type((String) rewardCollection.get(final_static_str_Reward_property_Type));
                                if (reward!=null) {
                                    rewards.add(reward);
                                }
                            }


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
    private void setBottomNavigationView(){
        bottomNavigationView = nav_bar_subView.findViewById(R.id.bottom_nav_bar);
        MenuItem profile = bottomNavigationView.getMenu().findItem(R.id.bottom_nav_bar_profile);
        profile.setCheckable(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        loadUserInfo();
                        reload();
                        break;
                        default:

                }
                return false;
            }
        });

    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editmode){
                    updateToolbarView(false);
                    setEditTextAble(false);
                    editmode = false;
                }else{
                    setToolbarNavBtn(previousActivity);
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(context, HomePage.class));
            }
        });

        editPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateToolbarView(true);
                setEditTextAble(true);
                editmode = true;

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateToolbarView(false);
                setEditTextAble(false);
                editmode = false;
                saveData(username_edit.getText().toString().trim(), useremail_edit.getText().toString().trim());
            }
        });

    }

    // new
    private void saveData(String name, String email){
        if(firebase_user.getUser_Login_Method().equals("Firebase Email Checking")) {
            firebase_user.setUser_Email(email);
        }else{
            setToast("Since you use google email or facebook to login, so the User email can not change");
        }
        firebase_user.setUser_Name(name);
        final boolean[] result = new boolean[3];

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    result[0] = true;
                }else{
                    result[0] = false;
                }
            }
        });
        databaseReference.child(staticclass.final_static_str_firebasedatabase_child_users).child(firebase_user.getUser_Id()).child(final_static_str_User_Email).setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    result[1]= true;
                }else{
                    result[1] = false;
                }

            }
        });
        databaseReference.child(final_static_str_firebasedatabase_child_users).child(firebase_user.getUser_Id()).child(final_static_str_User_Name).setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    result[2] = true;
                }else{
                    result[2] = false;
                }

            }
        });

        for(boolean results : result) {
            if (results == false){
                if (result[0] == false || result[2]) {
                    setSnackbar("Sorry, the email is invalid, please try again", 1);
                } else if (result[1] == false) {
                    setSnackbar("Sorry, the user name  is invalid, please try again", 1);
                } else {
                    setSnackbar("Sorry, the information is invalid or there is network issues", 1);
                }
            }


        }

        setSnackbar("The User information has been updata", 1);



    }

    private void setToast(String title){
        Toast.makeText(context, title, Toast.LENGTH_LONG).show();
    }

    private void setSnackbar(String message, int duration){
        Snackbar.make(main_Layout, message, duration).show();
    }

    private void updateToolbarView(boolean able){
        if (able){
            editPersonalInfo.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            done.setVisibility(View.VISIBLE);
            done.setClickable(true);

        }else{
            editPersonalInfo.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            done.setClickable(false);
            done.setVisibility(View.GONE);
        }


    }

    // re store the profile data


    // apply menu to toolbar ( logout function)
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.app_toolbar_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//
//    }
//
//
//    // toolbar menu item On click event
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.app_toolbar_logout:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(context, LoadingPage.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    // override superclass function to relaod

    private void reload(){
        Intent i = new Intent(Profile.this, Profile.class);
        finish();
        startActivity(i);

    }
    @Override
    public void finish() {
        super.finish();
    }
}
