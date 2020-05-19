package com.example.fyp_found.datastru;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Current_Lost_Record  {
    String Current_Lost_ID;
    String Current_Lost_User_ID;
    String Current_Lost_Property_Name;
    String Current_Lost_Property_QA1;
    String Current_Lost_Property_QA2;
    String Current_Lost_Property_QA1_Ans;
    String Current_Lost_Property_QA2_Ans;
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
    ArrayList<String> array;
    String[] tags = new String[5];

    // date time

    Date date;
    Long unix;
    SimpleDateFormat simpleDateFormat;


    public Current_Lost_Record(){}



    private void setTags(String tag, int position){
        if (position >= 0 && position < 5) {
            tags[position] = tag;
        }
    }

    public void setAllType(String[] tags){
        if (tags.length == 5){
            tags[0] = this.Current_Lost_Property_MainType;
            tags[1] = this.Current_Lost_type2;
            tags[2] = this.Current_Lost_type3;
            tags[3] = this.Current_Lost_type4;
            tags[4] = this.Current_Lost_type5;
        }else if (tags.length == 4 ){
            tags[0] = this.Current_Lost_Property_MainType;
            tags[1] = this.Current_Lost_type2;
            tags[2] = this.Current_Lost_type3;
            tags[3] = this.Current_Lost_type4;
        }else if (tags.length == 3){
            tags[0] = this.Current_Lost_Property_MainType;
            tags[1] = this.Current_Lost_type2;
            tags[2] = this.Current_Lost_type3;
        }else if (tags.length == 2){
            tags[0] = this.Current_Lost_Property_MainType;
            tags[1] = this.Current_Lost_type2;
        }else if (tags.length == 1){
            tags[0] = this.Current_Lost_Property_MainType;
        }
    }

    public String[] getTags(){
        return tags;
    }

    public String[] getArray(){
        String[] returnarray = new String[array.size()];
        returnarray = array.toArray(returnarray);
        return returnarray;
    }
    public void toArray(){
        array = new ArrayList<>();
        array.add(Current_Lost_ID);
        array.add(Current_Lost_User_ID);
        array.add(Current_Lost_Property_Name);
        array.add(Current_Lost_Property_QA1);
        array.add(Current_Lost_Property_QA2);
        array.add(Current_Lost_Property_QA1_Ans);
        array.add(Current_Lost_Property_QA2_Ans);
        array.add(Current_Lost_Address);
        array.add(Current_Lost_Property_MainType); //8
        array.add(Current_Lost_type2);
        array.add(Current_Lost_type3);
        array.add(Current_Lost_type4);
        array.add(Current_Lost_type5); //12
        array.add(getFound().toString());
        array.add(Current_Lost_Text);
        array.add(ImageURL);
        array.add(String.valueOf(unix));

    }

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
        if (current_Lost_Text!=null){
            Current_Lost_Text = current_Lost_Text;

        }else{
            Current_Lost_Text = "no any other information";
        }
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
                               String current_Lost_Text, String imageURL, String found, String time) {
        this.unix = Long.valueOf(time);
        setCurrent_Lost_ID(current_Lost_ID);
        setTime(time);
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
        setCurrent_Lost_Text(current_Lost_Text);
        this.Current_Lost_Property_QA1_Ans = current_Lost_Property_QA1_Ans ;
        this.Current_Lost_Property_QA2_Ans = current_Lost_Property_QA2_Ans ;
        this.ImageURL = imageURL;
        setFound(found);
        setForFilter();
        toArray();
    }



    public String getCurrent_Lost_ID() {
        return Current_Lost_ID;
    }

    public void setCurrent_Lost_ID(String id) {
        this.Current_Lost_ID = id;
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
            setTags(current_Lost_Property_MainType, 0);
        }else{
            this.Current_Lost_Property_MainType = "Unknown";
            setTags( "Unknown", 0);

        }
    }

    public String getCurrent_Lost_type2() {
        return Current_Lost_type2;
    }

    public void setCurrent_Lost_type2(String current_Lost_type2) {
        if (!current_Lost_type2.isEmpty()) {
            this.Current_Lost_type2 = current_Lost_type2;
            setTags(current_Lost_type2, 1);

        }else{
            this.Current_Lost_type2 = "Unknown";
            setTags( "Unknown", 1);

        }
    }

    public String getCurrent_Lost_type3() {
        return Current_Lost_type3;
    }

    public void setCurrent_Lost_type3(String current_Lost_type3) {
        if (!current_Lost_type3.isEmpty()) {
            this.Current_Lost_type3 = current_Lost_type3;
            setTags(current_Lost_type3, 2);
        }else{
            this.Current_Lost_type3 = "Unknown";
            setTags( "Unknown", 2);

        }
    }

    public String getCurrent_Lost_type4() {
        return Current_Lost_type4;
    }

    public void setCurrent_Lost_type4(String current_Lost_type4) {
        if (!current_Lost_type4.isEmpty()) {
            this.Current_Lost_type4 = current_Lost_type4;
            setTags(current_Lost_type4, 3);
        } else{
            this.Current_Lost_type4 = "Unknown";
            setTags( "Unknown", 3);

        }
    }

    public String getCurrent_Lost_type5() {
        return Current_Lost_type5;
    }

    public void setCurrent_Lost_type5(String current_Lost_type5) {
        if (!current_Lost_type5.isEmpty()) {
            this.Current_Lost_type5 = current_Lost_type5;
            setTags(current_Lost_type5, 4);
        } else{
            this.Current_Lost_type5 = "Unknown";
            setTags( "Unknown", 4);

        }
    }


    // bitmap to String for entering the firebase database ... so we don't need the local or non local database
    // start of bitmap convert (String to bitmap and bitmap to String)
    // input to firebase storage....


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


    public void setTime(String unix){
        this.unix = Long.valueOf(unix);
        this.date = new Date(this.unix*1000L);
    }

    public String getHours(){
        simpleDateFormat = new SimpleDateFormat("HH");
        return (simpleDateFormat.format(date));
    }
    public String getSecounds(){
        simpleDateFormat = new SimpleDateFormat("ss");
        return (simpleDateFormat.format(date));
    }
    public String getMinutes(){
        simpleDateFormat = new SimpleDateFormat("mm");
        return (simpleDateFormat.format(date));
    }
    public  String getTime(){
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return (simpleDateFormat.format(date));
    }
    public String getDay(){
        simpleDateFormat = new SimpleDateFormat("dd");
        return (simpleDateFormat.format(date));
    }
    public String getMouth(){
        simpleDateFormat = new SimpleDateFormat("MM");
        return (simpleDateFormat.format(date));
    }
    public String getYear(){
        simpleDateFormat = new SimpleDateFormat("yyyy");
        return (simpleDateFormat.format(date));
    }
    public String getAllDate(){
        return getYear() + "-" + getMouth() + "-" + getDay();
    }
    public String getAllTime(){
        return getYear() + "-" + getMouth() + "-" + getDay() + "  " + getHours() + ":" + getMinutes() + ":" + getSecounds();
    }

    public String getunix(){
        return String.valueOf(unix);
    }

    public static Comparator<Current_Lost_Record> getCombyTime(){
        Comparator comparator = new Comparator<Current_Lost_Record>() {
            @Override
            public int compare(Current_Lost_Record current_lost_record, Current_Lost_Record t1) {
                return current_lost_record.getunix().compareTo(t1.getunix());


            }
        };
        return comparator;


    }

}
