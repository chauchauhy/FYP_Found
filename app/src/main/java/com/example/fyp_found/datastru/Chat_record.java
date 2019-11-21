package com.example.fyp_found.datastru;

import java.util.Comparator;

public class Chat_record implements Comparator<Chat_record> {
    String Chat_ID;
    String Chat_sender_ID;
    String   Chat_rev_ID;
    String Chat_Date;
    String   Chat_Time;
    String Chat_Content;
    String Chat_DateTime;

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

    public String getChat_Date() {
        return Chat_Date;
    }

    public void setChat_Date(String chat_Date) {
        Chat_Date = chat_Date;
    }

    public String getChat_DateTime() {
        return Chat_DateTime;
    }

    public void setChat_DateTime(String chat_DateTime) {
        Chat_DateTime = chat_DateTime;
    }

    public Chat_record(String chat_ID, String chat_sender_ID, String chat_rev_ID, String chat_Date, String chat_Time, String chat_Content) {
        Chat_ID = chat_ID;
        Chat_sender_ID = chat_sender_ID;
        Chat_rev_ID = chat_rev_ID;
        this.Chat_Date = chat_Date;
        Chat_Time = chat_Time;
        Chat_Content = chat_Content;
        this.Chat_DateTime = this.Chat_Date + this.Chat_Time;
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

    public String getChat_rev_ID() {
        return Chat_rev_ID;
    }

    public void setChat_rev_ID(String chat_rev_ID) {
        Chat_rev_ID = chat_rev_ID;
    }

    public String getChat_Time() {
        return Chat_Time;
    }

    public void setChat_Time(String chat_Time) {
        Chat_Time = chat_Time;
    }

    public String getChat_Content() {
        return Chat_Content;
    }

    public void setChat_Content(String chat_Content) {
        Chat_Content = chat_Content;
    }

    @Override
    public int compare(Chat_record chat_record, Chat_record t1) {
        int com = chat_record.getChat_DateTime().compareTo(t1.getChat_DateTime());

        return com;

    }
}
