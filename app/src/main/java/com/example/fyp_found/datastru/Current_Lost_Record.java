package com.example.fyp_found.datastru;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Current_Lost_Record {
    String Current_Lost_ID;
    String Current_Lost_User_ID;         // user name
    String Current_Lost_Property_Name;      // property name
    String Current_Lost_Property_QA1;    // question1
    String Current_Lost_Property_QA2;    // question2
    String Current_Lost_Property_QA1_Ans;    // question1
    String Current_Lost_Property_QA2_Ans;    // question2
    String Current_Lost_Address;
    String Current_Lost_Property_MainType;
    String Current_Lost_type2;
    String Current_Lost_type3;
    String Current_Lost_type4;
    String Current_Lost_type5;
    String Current_Lost_Text;
    String ImageURL;
    Boolean found;
    String forFilter;

    public void setForFilter() {
        this.forFilter = Current_Lost_Address + Current_Lost_Property_MainType +  Current_Lost_type2 + Current_Lost_type3 + Current_Lost_type4 + Current_Lost_type5 + Current_Lost_Property_Name;

    }

    public String getForFilter(){
        return this.forFilter;
    }

    public String getCurrent_Lost_Text() {
        return Current_Lost_Text;
    }

    public void setCurrent_Lost_Text(String current_Lost_Text) {
        Current_Lost_Text = current_Lost_Text;
    }

    public String getCurrent_Lost_Property_QA1_Ans() {
        return Current_Lost_Property_QA1_Ans;
    }

    @Override
    public String toString() {
        return "Current_Lost_Record{" +
                "Current_Lost_ID='" + Current_Lost_ID + '\'' +
                ", Current_Lost_User_Name='" + Current_Lost_User_ID + '\'' +
                ", Current_Lost_Property_Name='" + Current_Lost_Property_Name + '\'' +
                ", Current_Lost_Property_QA1='" + Current_Lost_Property_QA1 + '\'' +
                ", Current_Lost_Property_QA2='" + Current_Lost_Property_QA2 + '\'' +
                ", Current_Lost_Property_QA1_Ans='" + Current_Lost_Property_QA1_Ans + '\'' +
                ", Current_Lost_Property_QA2_Ans='" + Current_Lost_Property_QA2_Ans + '\'' +
                ", Current_Lost_Address='" + Current_Lost_Address + '\'' +
                ", Current_Lost_Property_MainType='" + Current_Lost_Property_MainType + '\'' +
                ", Current_Lost_type2='" + Current_Lost_type2 + '\'' +
                ", Current_Lost_type3='" + Current_Lost_type3 + '\'' +
                ", Current_Lost_type4='" + Current_Lost_type4 + '\'' +
                ", Current_Lost_type5='" + Current_Lost_type5 + '\'' +
                ", Current_Lost_Text='" + Current_Lost_Text + '\'' +
                ", ImageURL='" + ImageURL + '\'' +
                ", found=" + found +
                '}';
    }

    public void setCurrent_Lost_Property_QA1_Ans(String current_Lost_Property_QA1_Ans) {
        Current_Lost_Property_QA1_Ans = current_Lost_Property_QA1_Ans;
    }

    public String getCurrent_Lost_Property_QA2_Ans() {
        return Current_Lost_Property_QA2_Ans;
    }

    public void setCurrent_Lost_Property_QA2_Ans(String current_Lost_Property_QA2_Ans) {
        Current_Lost_Property_QA2_Ans = current_Lost_Property_QA2_Ans;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(String found) {
        if(found.equals("true")) {
            this.found = true;
        }else{
            this.found = false;
        }
    }

    public Current_Lost_Record(String current_Lost_ID, String current_Lost_User_Name, String current_Lost_Property_Name,
                               String current_Lost_Property_QA1, String current_Lost_Property_QA2, String current_Lost_Property_QA1_Ans, String current_Lost_Property_QA2_Ans
            , String current_Lost_Address, String current_Lost_Property_MainType, String current_Lost_type2,
                               String current_Lost_type3, String current_Lost_type4, String current_Lost_type5,
                               String current_Lost_Text, String imageURL, String found) {
        Current_Lost_ID = current_Lost_ID;
        Current_Lost_User_ID = current_Lost_User_Name;
        Current_Lost_Property_Name = current_Lost_Property_Name;
        Current_Lost_Property_QA1 = current_Lost_Property_QA1;
        Current_Lost_Property_QA2 = current_Lost_Property_QA2;
        Current_Lost_Address = current_Lost_Address;
        setCurrent_Lost_Property_MainType(current_Lost_Property_MainType.trim());
        setCurrent_Lost_type2(current_Lost_type2.trim());
        setCurrent_Lost_type3(current_Lost_type3.trim());
        setCurrent_Lost_type4(current_Lost_type4.trim());
        setCurrent_Lost_type5(current_Lost_type5.trim());
        Current_Lost_Text = current_Lost_Text;
        this.Current_Lost_Property_QA1_Ans = current_Lost_Property_QA1_Ans ;
        this.Current_Lost_Property_QA2_Ans = current_Lost_Property_QA2_Ans ;
        this.ImageURL = imageURL;
        setFound(found);
        setForFilter();
    }



    public String getCurrent_Lost_ID() {
        return Current_Lost_ID;
    }

    public void setCurrent_Lost_ID(String current_Lost_ID) {
        Current_Lost_ID = current_Lost_ID;
    }

    public String getCurrent_Lost_User_ID() {
        return Current_Lost_User_ID;
    }

    public void setCurrent_Lost_User_ID(String current_Lost_User_Name) {
        Current_Lost_User_ID = current_Lost_User_Name;
    }

    public String getCurrent_Lost_Property_Name() {
        return Current_Lost_Property_Name;
    }

    public void setCurrent_Lost_Property_Name(String current_Lost_Property_Name) {
        Current_Lost_Property_Name = current_Lost_Property_Name;
    }

    public String getCurrent_Lost_Property_QA1() {
        return Current_Lost_Property_QA1;
    }

    public void setCurrent_Lost_Property_QA1(String current_Lost_Property_QA1) {
        Current_Lost_Property_QA1 = current_Lost_Property_QA1;
    }

    public String getCurrent_Lost_Property_QA2() {
        return Current_Lost_Property_QA2;
    }

    public void setCurrent_Lost_Property_QA2(String current_Lost_Property_QA2) {
        Current_Lost_Property_QA2 = current_Lost_Property_QA2;
    }

    public String getCurrent_Lost_Address() {
        return Current_Lost_Address;
    }

    public void setCurrent_Lost_Address(String current_Lost_Address) {
        if(!current_Lost_Address.isEmpty()){
            Current_Lost_Address = current_Lost_Address;
        }else{
            Current_Lost_Address = "Unknown";
        }

    }

    public String getCurrent_Lost_Property_MainType() {
        return Current_Lost_Property_MainType;
    }

    public void setCurrent_Lost_Property_MainType(String current_Lost_Property_MainType) {
        if (!current_Lost_Property_MainType.isEmpty()) {
            this.Current_Lost_Property_MainType = current_Lost_Property_MainType;
        }else{
            this.Current_Lost_Property_MainType = "Unknown";
        }
    }

    public String getCurrent_Lost_type2() {
        return Current_Lost_type2;
    }

    public void setCurrent_Lost_type2(String current_Lost_type2) {
        if (!current_Lost_type2.isEmpty()) {
            this.Current_Lost_type2 = current_Lost_type2;
        }else if (current_Lost_type2.equals("") ){
            this.Current_Lost_type2 = "Unknown";
        }
    }

    public String getCurrent_Lost_type3() {
        return Current_Lost_type3;
    }

    public void setCurrent_Lost_type3(String current_Lost_type3) {
        if (!current_Lost_type3.isEmpty()) {
            this.Current_Lost_type3 = current_Lost_type3;
        }else{
            this.Current_Lost_type3 = "Unknown";
        }
    }

    public String getCurrent_Lost_type4() {
        return Current_Lost_type4;
    }

    public void setCurrent_Lost_type4(String current_Lost_type4) {
        if (!current_Lost_type4.isEmpty()) {
            this.Current_Lost_type4 = current_Lost_type4;
        } else{
            this.Current_Lost_type4 = "Unknown";
        }
    }

    public String getCurrent_Lost_type5() {
        return Current_Lost_type5;
    }

    public void setCurrent_Lost_type5(String current_Lost_type5) {
        if (!current_Lost_type5.isEmpty()) {
            this.Current_Lost_type5 = current_Lost_type5;
        } else{
            this.Current_Lost_type5 = "Unknown";
        }
    }

    public Current_Lost_Record(String current_Lost_ID, String current_Lost_User_Name, String current_Lost_Property_Name, String current_Lost_Property_QA1, String current_Lost_Property_QA2, String current_Lost_Address, String current_Lost_Property_MainType, String current_Lost_type2, String current_Lost_type3, String current_Lost_type4, String current_Lost_type5) {
        Current_Lost_ID = current_Lost_ID;
        Current_Lost_User_ID = current_Lost_User_Name;
        Current_Lost_Property_Name = current_Lost_Property_Name;
        Current_Lost_Property_QA1 = current_Lost_Property_QA1;
        Current_Lost_Property_QA2 = current_Lost_Property_QA2;
        Current_Lost_Address = current_Lost_Address;
        Current_Lost_Property_MainType = current_Lost_Property_MainType;
        Current_Lost_type2 = current_Lost_type2;
        Current_Lost_type3 = current_Lost_type3;
        Current_Lost_type4 = current_Lost_type4;
        Current_Lost_type5 = current_Lost_type5;
    }

    public Current_Lost_Record(Object o) {
    }
    // bitmap to String for entering the firebase database ... so we dont need the local or non local database
    // start of bitmap convert (String to bitmap and bitmap to String)
    // input to firebase store ....
   final public static String bitmaptoString(Bitmap bitmap) {
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
    // need final static

   final public static Bitmap Stringtobitmap(String convert_string) {
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
}
