package com.example.fyp_found;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    View toolbar_subview, nav_bar_subView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout main_Layout;
    Context context;
    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initVar();
        initUI();


    }
    // error checking if user not login
    private void setPortal(){
        if (firebaseUser == null){
            startActivity(new Intent(context, LoadingPage.class));
        }
    }
    private void initVar(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        context = this;
        setPortal();
    }
    private void initUI(){
        toolbar_subview = findViewById(R.id.profile_toolbar);
        nav_bar_subView = findViewById(R.id.profile_bottom_nav_bar);
        toolbar = toolbar_subview.findViewById(R.id.app_toolbar_nosearch_1);
        main_Layout = findViewById(R.id.profile_main_layout);
        setToolbar();
        setBottomNavigationView();
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
                        loadData();
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
                startActivity(new Intent(context, HomePage.class));
            }
        });

    }
    private void loadData(){

    }
    private void reload(){
        Intent i = new Intent(Profile.this, Profile.class);
        finish();
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_toolbar_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(context, LoadingPage.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
