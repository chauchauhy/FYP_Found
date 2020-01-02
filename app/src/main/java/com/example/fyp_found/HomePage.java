package com.example.fyp_found;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fyp_found.Adapter.HomePage_Adapter;
import com.example.fyp_found.datastru.Current_Lost_Record;
import com.example.fyp_found.datastru.Reward;
import com.example.fyp_found.setup.staticclass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.fyp_found.datastru.Current_Lost_Record.bitmaptoString;
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
import static com.example.fyp_found.setup.staticclass.final_static_str_db_name_current;

public class HomePage extends AppCompatActivity /*implements Toolbar.OnMenuItemClickListener */ {
    RecyclerView recyclerView, reward_recyclerView;
    public static ArrayList<Current_Lost_Record> records = new ArrayList<>();
    View subView_bottom_Nav_bar, subView_toolbar;
    BottomNavigationView navigationView;
    ConstraintLayout linearLayout;
    Context context;
    HomePage_Adapter homePage_adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Reward> rewards = new ArrayList<>();
    Button reward_View, lost_Property;
    ProgressBar progressBar;
    Toolbar toolbar;
    SearchView searchView;
    ImageButton messagebox;

    // firebase
    FirebaseUser firebaseUser;
    Boolean logined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initVariable();
        initUI();
        loadData();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //////////////initial the UI variable                           ///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setNavigationView() {
        navigationView.getMenu().findItem(R.id.bottom_nav_bar_home).setCheckable(true).setChecked(false);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_nav_bar_home:
                        progressBar.setVisibility(View.VISIBLE);
                        loadData();
                        recyclerView.scrollTo(0, 0);
                        progressBar.setVisibility(View.GONE);
                        break;
                    case R.id.bottom_nav_bar_post:
                        if (firebaseUser != null) {
                            startActivity(new Intent(HomePage.this, ImageClassification.class));
                        } else {
                            Snackbar.make(linearLayout, "You may need login or sign up before use this function", Snackbar.LENGTH_LONG).setAction("Login/Sign up", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(context, Login.class));
                                }
                            });
                        }
                        break;
                    case R.id.bottom_nav_bar_profile:
                        if (firebaseUser != null) {
                            startActivity(new Intent(context, Profile.class));
                        } else {
                            Snackbar.make(linearLayout, "You may need login or sign up before use this function", Snackbar.LENGTH_LONG).setAction("Login / Sign up", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(context, Profile.class));
                                }
                            });
                        }
                        break;

                    default:

                }


                return true;
            }
        });
    }

    private void setRecyclerView_Reward() {

    }

    private void setRecyclerView_View_Current_Lost() {
        homePage_adapter = new HomePage_Adapter(records, context, logined);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(homePage_adapter);
    }

    private void initUI() {
        // basic
        linearLayout = findViewById(R.id.home_linear);
        recyclerView = findViewById(R.id.home_recyclerview_current_lost);
        subView_bottom_Nav_bar = findViewById(R.id.home_nav_bttom_nav_bar);
        reward_recyclerView = findViewById(R.id.home_recyclerview_reward);
        context = this;
        //sub view
        navigationView = subView_bottom_Nav_bar.findViewById(R.id.bottom_nav_bar);
        setNavigationView();
        // srl view
        swipeRefreshLayout = findViewById(R.id.homePage_SRL);
        setSwipeRefreshLayout();
        lost_Property = findViewById(R.id.home_page_turn_View_CurrentLost);
        reward_View = findViewById(R.id.home_page_turn_View_Reward);
        // linear view
        reward_View.setClickable(true);
        lost_Property.setClickable(true);
        setUpTabTextView();
        // other
        progressBar = findViewById(R.id.homepage_progressbar);
        subView_toolbar = findViewById(R.id.toolbar_home);
        setToolbar();
        setSearchView();



    }
    private void setSearchView(){

        if(searchView!=null){
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    setRecyclerView_View_Current_Lost();
                    return true;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    homePage_adapter.getFilter().filter(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    homePage_adapter.getFilter().filter(s);
                    Toast.makeText(context,  s, Toast.LENGTH_LONG).show();
                    homePage_adapter.notifyDataSetChanged();
                    return false;
                }
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    homePage_adapter.reSetAdapter();
                    return false;
                }
            });
        }

    }

    private void setToolbar() {
        toolbar = subView_toolbar.findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        searchView = subView_toolbar.findViewById(R.id.app_toolbar_search);
        messagebox = subView_toolbar.findViewById(R.id.app_toolbar_messagebox);
        getSupportActionBar().setTitle(null);

        messagebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ChatBoxMatrix.class));
            }
        });


    }


    private void setUpTabTextView() {
        lost_Property.setClickable(true);
        reward_View.setClickable(true);

        lost_Property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                reward_recyclerView.setVisibility(View.GONE);
            }
        });

        reward_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reward_recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void setSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        loadData();
                    }
                }, 2000);
            }
        });
    }

    private void initVariable() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            logined = true;
        } else {
            firebaseUser = null;
            logined = false;
        }
        context = this;


    }

    private void loadData() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(final_static_str_db_name_current);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    records.clear();
                    HashMap<String, Object> hashMap = new HashMap<>();
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
                                            (String) data.get(final_static_str_Current_Lost_Boolean)

                                    );
                            if (!record.getFound()) {
                                records.add(record);
                                // Remarks :
                                //there need a error checking for same images which should not append into records!
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    setRecyclerView_View_Current_Lost();
                    Log.i(staticclass.TAG, String.valueOf(records.size()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, getResources().getString(R.string.error_404), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void ImageChecking(Current_Lost_Record record) {
        if (records.size() > 0 && !record.getImageURL().isEmpty()) {
            String url = record.getImageURL();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                String bi = bitmaptoString(bitmap);
                Log.i(staticclass.TAG, bi);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //////////////end of                     initial the UI variable///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void reload() {
        Intent i = new Intent(HomePage.this, HomePage.class);
        finish();
        startActivity(i);
    }

    @Override
    public void finish() {
        super.finish();
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.app_toolbar_messagebox:
//                startActivity(new Intent(context, ChatBoxMatrix.class));
//        }
//        return true;
//    }


}
