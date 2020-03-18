package com.example.fyp_found.datastru;


public class Reward {
    String Reward_ID;
    String Reward_Title;
    String Reward_Content;
    String Reward_UserID;
    String Reward_property_Type;
    String Reward_Lost_Address;

    public Reward() {
    }

    @Override
    public String toString() {
        return "Reward{" +
                "Reward_ID='" + Reward_ID + '\'' +
                ", Reward_Title='" + Reward_Title + '\'' +
                ", Reward_Content='" + Reward_Content + '\'' +
                ", Reward_UserID=" + Reward_UserID +
                ", Reward_property_Type='" + Reward_property_Type + '\'' +
                ", Reward_Lost_Address='" + Reward_Lost_Address + '\'' +
                '}';
    }

    public Reward(String reward_ID, String reward_Title, String reward_Content, String reward_UserID, String reward_property_Type, String reward_Lost_Address) {
        Reward_ID = reward_ID;
        Reward_Title = reward_Title;
        Reward_Content = reward_Content;
        Reward_UserID = reward_UserID;
        Reward_property_Type = reward_property_Type;
        Reward_Lost_Address = reward_Lost_Address;
    }

    public String getReward_ID() {
        return Reward_ID;
    }

    public void setReward_ID(String reward_ID) {
        Reward_ID = reward_ID;
    }

    public String getReward_Title() {
        return Reward_Title;
    }

    public void setReward_Title(String reward_Title) {
        Reward_Title = reward_Title;
    }

    public String getReward_Content() {
        return Reward_Content;
    }

    public void setReward_Content(String reward_Content) {
        Reward_Content = reward_Content;
    }

    public String getReward_UserID() {
        return Reward_UserID;
    }

    public void setReward_UserID(String reward_UserID) {
        Reward_UserID = reward_UserID;
    }

    public String getReward_property_Type() {
        return Reward_property_Type;
    }

    public void setReward_property_Type(String reward_property_Type) {
        Reward_property_Type = reward_property_Type;
    }

    public String getReward_Lost_Address() {
        return Reward_Lost_Address;
    }

    public void setReward_Lost_Address(String reward_Lost_Address) {
        Reward_Lost_Address = reward_Lost_Address;
    }
}
