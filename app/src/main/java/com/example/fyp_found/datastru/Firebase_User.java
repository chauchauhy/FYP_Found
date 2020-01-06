package com.example.fyp_found.datastru;

public class Firebase_User {
    String User_Id;
    String User_Name;
    String User_Email;
    String User_Login_Method;
    String User_dev_code;
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Firebase_User() {
    }

    public Firebase_User(String user_Id, String user_Name, String user_Email) {
        User_Id = user_Id;
        User_Name = user_Name;
        User_Email = user_Email;
        this.User_dev_code = "unknown";
        this.User_Login_Method = "unknown";
    }

    @Override
    public String toString() {
        return "Firebase_User{" +
                "User_Id='" + User_Id + '\'' +
                ", User_Name='" + User_Name + '\'' +
                ", User_Email='" + User_Email + '\'' +
                ", User_Login_Method='" + User_Login_Method + '\'' +
                ", User_dev_code='" + User_dev_code + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id ;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name ;
    }

    public Firebase_User(String user_Id, String user_Name) {
        User_Id = user_Id ;
        User_Name = user_Name ;
    }

    public void setUser_Email(String user_Email) {
        User_Email = user_Email ;
    }

    public void setUser_Login_Method(String user_Login_Method) {
        User_Login_Method = user_Login_Method ;
    }

    public void setUser_dev_code(String user_dev_code) {
        User_dev_code = user_dev_code ;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public String getUser_Email() {
        return User_Email;
    }

    public String getUser_Login_Method() {
        return User_Login_Method;
    }

    public String getUser_dev_code() {
        return User_dev_code;
    }

    public Firebase_User(String user_Id, String user_Name, String user_Email, String token) {
        User_Id = user_Id ;
        User_Name = user_Name ;
        User_Email = user_Email ;
        this.token = token;
    }

}
