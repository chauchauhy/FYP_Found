package com.example.fyp_found.datastru;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Chat_record implements Comparator<Chat_record> {
    String Chat_ID;
    String Chat_sender_ID;
    String   Chat_rev_ID;
    String Chat_Date;
    String   Chat_Time;
    String Chat_Content;
    String Chat_DateTime;
    // Time
    Date date;
    Long unix;
    SimpleDateFormat simpleDateFormat;


    public Chat_record() {
        AutoSetUnix();
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
        return getYear() + "-" + getMouth() + "-" + getDay();
    }

    public void setChat_Date(String chat_Date) {
        Chat_Date = chat_Date;
    }

    public String getChat_DateTime() {
        return  getHours() + ":" + getMinutes() + ":" + getSecounds();
    }

    public void setChat_DateTime(String chat_DateTime) {
        Chat_DateTime = chat_DateTime;
    }

    public Chat_record(String chat_ID, String chat_sender_ID, String chat_rev_ID, String chat_Content) {
        AutoSetUnix();
        Chat_ID = chat_ID;
        Chat_sender_ID = chat_sender_ID;
        Chat_rev_ID = chat_rev_ID;
        Chat_Content = chat_Content;
        this.Chat_DateTime =getAllTime();

    }

    public Chat_record(String chat_ID, String chat_sender_ID, String chat_rev_ID, String chat_Content, String unixTime) {
        Chat_ID = chat_ID;
        Chat_sender_ID = chat_sender_ID;
        Chat_rev_ID = chat_rev_ID;
        setUnix(unixTime);
        Chat_Content = chat_Content;
        this.Chat_Date = getAllDate();
        this.Chat_Time = getTime();
        this.Chat_DateTime =getAllTime();


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
        return getHours() + ":" + getMinutes() ;
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
        int com = chat_record.getAllTime().compareTo(t1.getAllTime());
        return com;
    }


    // Set time :////
    /////////////////////


    public void setUnix(String unixTime){
        this.unix = Long.valueOf(unixTime);
        this.date = new Date(unix*1000L);
    }
    public String getUnix(){return String.valueOf(this.unix);}

    public long getUnix_long_type(){return this.unix;}


    public void AutoSetUnix(){
        this.unix =  System.currentTimeMillis() / 1000L;
        this.date = new Date(unix*1000L);
    }
    public static long getUnixTime(){
        return System.currentTimeMillis() / 1000L;

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


















}
