package com.example.fyp_found.setup;

import android.provider.Settings;


public final class staticclass {
    public static String login_account;
    public static String login_name;
    public static String current_dev_code = null;
    public static String currentUser = "";
    public static String currentUserID = "";
    public final static String TAG = "IAMLOG";
    public final static String url_initizal = "";
    public static String dev_code;
    public final static String localdb_URL = "http:192.168.137.1/dashboard/new/";
    // check ip is needed
    //    http://localhost/dashboard/new/php_get/runq.php
    public final static String localdb_URL_get = localdb_URL + "php_get/";
    public final static String localdb_URL_php = ".php";
    public final static String localdb_URL_test = localdb_URL_get + "runq.php";
//    public final static String localdb_URL_


    // db col name
    //chat
    public final static String final_static_str_db_name_chat = "chat_record";
    public final static String final_static_str_Chat_ID = "char_ID";
    public final static String final_static_str_Chat_sender_ID = "Chat_sender_id";
    public final static String final_static_str_Chat_rev_ID = "Chat_rev_ID";
    public final static String final_static_str_Chat_Time = "Chat_Time";
    public final static String final_static_str_Chat_Content = "chat_content";
    // User
    public final static String final_static_str_db_name_user = "user";
    public final static String final_static_str_User_Id = "user_id";
    public final static String final_static_str_User_Name = "User_Name";
    public final static String final_static_str_User_Account = "User_Account";
    public final static String final_static_str_User_Password = "User_Password";
    public final static String final_static_str_User_Email = "User_Email";
    public final static String final_static_str_User_Login_Method = "User_Login_Method";
    public final static String final_static_str_User_Level = "User_Level";
    public final static String final_static_str_User_dev_code = "User_dev_code";

    //Reward
    public final static String final_static_str_db_name_reward = "reward";
    public final static String final_static_str_Reward_ID = "Reward_ID";
    public final static String final_static_str_Reward_Title = "Reward_Title";
    public final static String final_static_str_Reward_Content = "Reward_Content";
    public final static String final_static_str_Reward_UserID = "Reward_UserID";
    public final static String final_static_str_Reward_property_Type = "Reward_Property_Type";
    public final static String final_static_str_Reward_Lost_Address = "Reward_Lost_Address";
    // Found_record
    public final static String final_static_str_db_name_found = "found_record";
    public final static String final_static_str_Found_Property_ID = "Found_property_ID ";
    public final static String final_static_str_Found_Property_Name = "Found_property_Name";
    public final static String final_static_str_Found_property_Content = "Found_property_content";
    public final static String final_static_str_Found_Property_UserID = "Found_property_UserID";
    public final static String final_static_str_Found_Property_OwnerID = "Found_property_owneriD";
    public final static String final_static_str_Found_Lost_Address = "Found_Lost_Address";
    public final static String final_static_str_Found_Property_MainType = "Found_property_MainType";
    public final static String final_static_str_Found_Property_type2 = "Found_Property_type2";
    public final static String final_static_str_Found_Property_type3 = "Found_Property_type3";
    public final static String final_static_str_Found_Property_type4 = "Found_Property_type4";
    public final static String final_static_str_Found_Property_type5 = "Found_property_type5";
    // current lost record
    public final static String final_static_str_db_name_current = "current_lost_record";
    public static final String final_static_str_Current_Lost_ID = "Current_Lost_ID";
    public static final String final_static_str_Current_Lost_Name = "Current_Lost_Name";
    public static final String final_static_str_Current_Lost_Content = "Current_Lost_Content";
    public static final String final_static_str_Current_Lost_UserID_Post = "Current_Lost_UserID_post";
    public static final String final_static_str_Current_Lost_Address = "Current_Lost_Address";
    public static final String final_static_str_Current_Lost_Property_MainType = "Current_Lost_Property_MainType";
    public static final String final_static_str_Current_Lost_type2 = "Current_Lost_Property_type2";
    public static final String final_static_str_Current_Lost_type3 = "Current_Lost_Property_type3";
    public static final String final_static_str_Current_Lost_type4 = "Current_Lost_Property_type4";
    public static final String final_static_str_Current_Lost_type5 = "Current_Lost_Property_type5";
    // firebase
    public static final String final_static_str_firebasedatabase_child_users = "Users";
    public static final String final_static_str_firebasedatabase_child_chat = "Chats";


    // internal file
    public static final String final_static_str_file_name_chat = "Chater";

    //Intent String
    public static final String final_static_str_Intent_Chatoppun = "receiver";

    // chat configure
    public static final int final_static_int_MSG_TYPE_LEFT = 0;
    public static final int final_static_int_MSG_TYPE_REGHT = 1;


}
