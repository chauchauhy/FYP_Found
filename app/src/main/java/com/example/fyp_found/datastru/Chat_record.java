package com.example.fyp_found.datastru;

public class Chat_record {
    String Chat_ID;
    String Chat_sender_ID;
    User   Chat_rev_ID;
    User   Chat_Time;
    String Chat_Content;

    public Chat_record() {
    }

    @Override
    public String toString() {
        return "Chat_record{" +
                "Chat_ID='" + Chat_ID + '\'' +
                ", Chat_sender_ID='" + Chat_sender_ID + '\'' +
                ", Chat_rev_ID=" + Chat_rev_ID +
                ", Chat_Time=" + Chat_Time +
                ", Chat_Content='" + Chat_Content + '\'' +
                '}';
    }

    public Chat_record(String chat_ID, String chat_sender_ID, User chat_rev_ID, User chat_Time, String chat_Content) {
        Chat_ID = chat_ID;
        Chat_sender_ID = chat_sender_ID;
        Chat_rev_ID = chat_rev_ID;
        Chat_Time = chat_Time;
        Chat_Content = chat_Content;
    }

    public String getChat_ID() {
        return Chat_ID;
    }

    public void setChat_ID(String chat_ID) {
        Chat_ID = chat_ID;
    }

    public String getChat_sender_ID() {
        return Chat_sender_ID;
    }

    public void setChat_sender_ID(String chat_sender_ID) {
        Chat_sender_ID = chat_sender_ID;
    }

    public User getChat_rev_ID() {
        return Chat_rev_ID;
    }

    public void setChat_rev_ID(User chat_rev_ID) {
        Chat_rev_ID = chat_rev_ID;
    }

    public User getChat_Time() {
        return Chat_Time;
    }

    public void setChat_Time(User chat_Time) {
        Chat_Time = chat_Time;
    }

    public String getChat_Content() {
        return Chat_Content;
    }

    public void setChat_Content(String chat_Content) {
        Chat_Content = chat_Content;
    }
}
