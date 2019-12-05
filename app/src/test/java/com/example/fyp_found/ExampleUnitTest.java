package com.example.fyp_found;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.fyp_found.datastru.Chat_record;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.fyp_found.datastru.Chat_record.getUnixTime;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Address;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_ID;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_MainType;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA1;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA1_Ans;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA2;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Property_QA2_Ans;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_Text;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_User_Name;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type2;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type3;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type4;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_type5;
import static com.example.fyp_found.setup.staticclass.final_static_str_db_name_current;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        int i =9;
       HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(final_static_str_Current_Lost_ID, "s");
        hashMap.put(final_static_str_Current_Lost_User_Name, "s");
        hashMap.put(final_static_str_Current_Lost_Property_QA1 , "s");
        hashMap.put(final_static_str_Current_Lost_Property_QA2 , "s");
        hashMap.put(final_static_str_Current_Lost_Property_QA1_Ans , "s");
        hashMap.put(final_static_str_Current_Lost_Property_QA2_Ans , "s");
        hashMap.put(final_static_str_Current_Lost_Property_Name , "s");
        hashMap.put(final_static_str_Current_Lost_Address, "s");
        hashMap.put(final_static_str_Current_Lost_Property_MainType , "s");
        hashMap.put(final_static_str_Current_Lost_type2 , "s");
        hashMap.put(final_static_str_Current_Lost_type3 , "s");
        hashMap.put(final_static_str_Current_Lost_type4 , "s");
        hashMap.put(final_static_str_Current_Lost_type5 , "s");
        hashMap.put(final_static_str_Current_Lost_Text, "s");
        String s = new String("RM. 1514, 15/F, BLK. A, SHEK TO HSE., SHEK WAI KOK EST., TSUEN WAN, NT, 香港");
        assert s!=null;
        ArrayList<String> list = new ArrayList<>();
        for (String str : s.split(",")){
            list.add(str);
        }
        for(String str: list){

        }
        System.out.println(list.get(list.size()-1));

    }
}