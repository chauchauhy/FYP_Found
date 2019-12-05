package com.example.fyp_found.datastru.non_str;

public class User {
    String User_Id;
    String User_Name;
    String User_Account;
    String User_Password;
    String User_Email;
    String User_Login_Method;
    String User_Level;
    String User_dev_code;

    public User() {
    }

    public User(String user_Id, String user_Name, String user_Account, String user_Password, String user_Email, String user_Login_Method, String user_Level, String user_dev_code) {
        User_Id = user_Id;
        User_Name = user_Name;
        User_Account = user_Account;
        User_Password = user_Password;
        User_Email = user_Email;
        User_Login_Method = user_Login_Method;
        User_Level = user_Level;
        User_dev_code = user_dev_code;
    }

    @Override
    public String toString() {
        return "User{" +
                "User_Id='" + User_Id + '\'' +
                ", User_Name='" + User_Name + '\'' +
                ", User_Account='" + User_Account + '\'' +
                ", User_Password='" + User_Password + '\'' +
                ", User_Email='" + User_Email + '\'' +
                ", User_Login_Method='" + User_Login_Method + '\'' +
                ", User_Level='" + User_Level + '\'' +
                ", User_dev_code='" + User_dev_code + '\'' +
                '}';
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Account() {
        return User_Account;
    }

    public void setUser_Account(String user_Account) {
        User_Account = user_Account;
    }

    public String getUser_Password() {
        return User_Password;
    }

    public void setUser_Password(String user_Password) {
        User_Password = user_Password;
    }

    public String getUser_Email() {
        return User_Email;
    }

    public void setUser_Email(String user_Email) {
        User_Email = user_Email;
    }

    public String getUser_Login_Method() {
        return User_Login_Method;
    }

    public void setUser_Login_Method(String user_Login_Method) {
        User_Login_Method = user_Login_Method;
    }

    public String getUser_Level() {
        return User_Level;
    }

    public void setUser_Level(String user_Level) {
        User_Level = user_Level;
    }

    public String getUser_dev_code() {
        return User_dev_code;
    }

    public void setUser_dev_code(String user_dev_code) {
        User_dev_code = user_dev_code;
    }
}
