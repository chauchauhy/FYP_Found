package com.example.fyp_found.datastru;

public class Current_Lost_Record {
    String Current_Lost_ID;
    String Current_Lost_Name;
    String Current_Lost_Content;
    User   Current_Lost_UserID_Post;
    String Current_Lost_Address;
    String Current_Lost_Property_MainType;
    String Current_Lost_type2;
    String Current_Lost_type3;
    String Current_Lost_type4;
    String Current_Lost_type5;


    @Override
    public String toString() {
        return "Current_Lost_Record{" +
                "Current_Lost_ID='" + Current_Lost_ID + '\'' +
                ", Current_Lost_Name='" + Current_Lost_Name + '\'' +
                ", Current_Lost_Content='" + Current_Lost_Content + '\'' +
                ", Current_Lost_UserID_Post=" + Current_Lost_UserID_Post.toString() +
                ", Current_Lost_Address='" + Current_Lost_Address + '\'' +
                ", Current_Lost_Property_MainType='" + Current_Lost_Property_MainType + '\'' +
                ", Current_Lost_type2='" + Current_Lost_type2 + '\'' +
                ", Current_Lost_type3='" + Current_Lost_type3 + '\'' +
                ", Current_Lost_type4='" + Current_Lost_type4 + '\'' +
                ", Current_Lost_type5='" + Current_Lost_type5 + '\'' +
                '}';
    }

    public String getCurrent_Lost_ID() {
        return Current_Lost_ID;
    }

    public void setCurrent_Lost_ID(String current_Lost_ID) {
        Current_Lost_ID = current_Lost_ID;
    }

    public String getCurrent_Lost_Name() {
        return Current_Lost_Name;
    }

    public void setCurrent_Lost_Name(String current_Lost_Name) {
        Current_Lost_Name = current_Lost_Name;
    }

    public String getCurrent_Lost_Content() {
        return Current_Lost_Content;
    }

    public void setCurrent_Lost_Content(String current_Lost_Content) {
        Current_Lost_Content = current_Lost_Content;
    }

    public User getCurrent_Lost_UserID_Post() {
        return Current_Lost_UserID_Post;
    }

    public void setCurrent_Lost_UserID_Post(User current_Lost_UserID_Post) {
        Current_Lost_UserID_Post = current_Lost_UserID_Post;
    }

    public String getCurrent_Lost_Address() {
        return Current_Lost_Address;
    }

    public void setCurrent_Lost_Address(String current_Lost_Address) {
        Current_Lost_Address = current_Lost_Address;
    }

    public String getCurrent_Lost_Property_MainType() {
        return Current_Lost_Property_MainType;
    }

    public void setCurrent_Lost_Property_MainType(String current_Lost_Property_MainType) {
        Current_Lost_Property_MainType = current_Lost_Property_MainType;
    }

    public String getCurrent_Lost_type2() {
        return Current_Lost_type2;
    }

    public void setCurrent_Lost_type2(String current_Lost_type2) {
        Current_Lost_type2 = current_Lost_type2;
    }

    public String getCurrent_Lost_type3() {
        return Current_Lost_type3;
    }

    public void setCurrent_Lost_type3(String current_Lost_type3) {
        Current_Lost_type3 = current_Lost_type3;
    }

    public String getCurrent_Lost_type4() {
        return Current_Lost_type4;
    }

    public void setCurrent_Lost_type4(String current_Lost_type4) {
        Current_Lost_type4 = current_Lost_type4;
    }

    public String getCurrent_Lost_type5() {
        return Current_Lost_type5;
    }

    public void setCurrent_Lost_type5(String current_Lost_type5) {
        Current_Lost_type5 = current_Lost_type5;
    }

    public Current_Lost_Record(String current_Lost_ID, String current_Lost_Name, String current_Lost_Content, User current_Lost_UserID_Post, String current_Lost_Address, String current_Lost_Property_MainType, String current_Lost_type2, String current_Lost_type3, String current_Lost_type4, String current_Lost_type5) {
        Current_Lost_ID = current_Lost_ID;
        Current_Lost_Name = current_Lost_Name;
        Current_Lost_Content = current_Lost_Content;
        Current_Lost_UserID_Post = current_Lost_UserID_Post;
        Current_Lost_Address = current_Lost_Address;
        Current_Lost_Property_MainType = current_Lost_Property_MainType;
        Current_Lost_type2 = current_Lost_type2;
        Current_Lost_type3 = current_Lost_type3;
        Current_Lost_type4 = current_Lost_type4;
        Current_Lost_type5 = current_Lost_type5;
    }

    public Current_Lost_Record() {
    }
}
