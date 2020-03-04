package com.example.fyp_found.setup;

import android.provider.Settings;

import com.example.fyp_found.datastru.Chat_record;

// this class is use for save some KEY for hashmap or intent
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;


public final class staticclass {
    public static String current_dev_code = null;
    public static String currentUserID = "";
    public final static String TAG = "IAMLOG";
    public final static String IntentTAG = "IntentTAG";
    public final static int home_Anchor = 0;
    public final static int image_Anchor = 1;
    public final static int post_Anchor = 2;



    // db col name
    // should use array to store below key ....
    //chat
    public final static String final_static_str_db_name_chat = "Chat_record";
    public final static String final_static_str_Chat_ID = "char_ID";
    public final static String final_static_str_Chat_sender_ID = "Chat_sender_id";
    public final static String final_static_str_Chat_rev_ID = "Chat_rev_ID";
    public final static String final_static_str_Chat_Time = "Chat_Time";
    public final static String final_static_str_Chat_Content = "chat_content";
    public final static String final_static_str_Chat_Date = "Chat_Date";
    public final static String final_static_str_Chat_unixTime = "unixTime";
    // User'
    public final static String final_static_str_db_name_user = "Users";
    public final static String final_static_str_User_Id = "user_id";
    public final static String final_static_str_User_Name = "User_Name";
    public final static String final_static_str_User_Account = "User_Account";
    public final static String final_static_str_User_Password = "User_Password";
    public final static String final_static_str_User_Email = "User_Email";
    public final static String final_static_str_User_Login_Method = "User_Login_Method";
    public final static String final_static_str_User_Level = "User_Level";
    public final static String final_static_str_User_dev_code = "User_dev_code";
    public final static String final_static_str_User_Icon_URL = "User_Icon_URL";

    //Reward
    public final static String final_static_str_db_name_reward = "Reward";
    public final static String final_static_str_Reward_ID = "Reward_ID";
    public final static String final_static_str_Reward_Title = "Reward_Title";
    public final static String final_static_str_Reward_Content = "Reward_Content";
    public final static String final_static_str_Reward_UserID = "Reward_UserID";
    public final static String final_static_str_Reward_property_Type = "Reward_Property_Type";
    public final static String final_static_str_Reward_Lost_Address = "Reward_Lost_Address";
    // Found_record
    public final static String final_static_str_db_name_found = "Found_record";
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
    public final static String final_static_str_db_name_current = "Current_lost_record";
    public static final String final_static_str_Current_Lost_ID = "Current_Lost_ID";
    public static final String final_static_str_Current_Lost_User_ID = "Current_Lost_User_ID";
    public static final String final_static_str_Current_Lost_Property_QA1 = "Current_Lost_Property_QA1";
    public static final String final_static_str_Current_Lost_Property_QA2 = "Current_Lost_Property_QA2";
    public static final String final_static_str_Current_Lost_Property_QA1_Ans = "Current_Lost_Property_QA1_Ans";
    public static final String final_static_str_Current_Lost_Property_QA2_Ans = "Current_Lost_Property_QA2_Ans";
    public static final String final_static_str_Current_Lost_Property_Name = "Current_Lost_Property_Name";
    public static final String final_static_str_Current_Lost_Address = "Current_Lost_Address";
    public static final String final_static_str_Current_Lost_Property_MainType = "Current_Lost_Property_MainType";
    public static final String final_static_str_Current_Lost_type2 = "Current_Lost_Property_type2";
    public static final String final_static_str_Current_Lost_type3 = "Current_Lost_Property_type3";
    public static final String final_static_str_Current_Lost_type4 = "Current_Lost_Property_type4";
    public static final String final_static_str_Current_Lost_type5 = "Current_Lost_Property_type5";
    public static final String final_static_str_Current_Lost_Text = "Current_Lost_Property_Text";
    public static final String final_static_str_Current_Lost_URL = "ImageURL";
    public static final String final_static_str_Current_Lost_Boolean = "Found";

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

    // spinner
    public static final  String[] question_arr_final =  {"What is the name of this item?", "How much of this item (for publish time)?", "Where find this item? (in locker or somewhere?)" ,"What is the feature of this item?" ,"What is the texture of this item?"};

    // firebase
    public static final String firebase_FCM_Token = "Token";
    public static String FCM_TOKEN = "";


    // array
    public static final String[] final_static_str_array_Current_Lost_Array = {final_static_str_Current_Lost_ID,
            final_static_str_Current_Lost_User_ID,
            final_static_str_Current_Lost_Property_Name,
            final_static_str_Current_Lost_Property_QA1,
            final_static_str_Current_Lost_Property_QA2,
            final_static_str_Current_Lost_Property_QA1_Ans,
            final_static_str_Current_Lost_Property_QA2_Ans,
            final_static_str_Current_Lost_Address,
            final_static_str_Current_Lost_Property_MainType,
            final_static_str_Current_Lost_type2,
            final_static_str_Current_Lost_type3,
            final_static_str_Current_Lost_type4,
            final_static_str_Current_Lost_type5,
            final_static_str_Current_Lost_Boolean,
            final_static_str_Current_Lost_Text,
            final_static_str_Current_Lost_URL
         };
    public static final String[] final_static_str_array_reward = {final_static_str_db_name_reward,
            final_static_str_Reward_ID,
            final_static_str_Reward_Title,
            final_static_str_Reward_Content,
            final_static_str_Reward_UserID,
            final_static_str_Reward_property_Type,
            final_static_str_Reward_Lost_Address};
}
