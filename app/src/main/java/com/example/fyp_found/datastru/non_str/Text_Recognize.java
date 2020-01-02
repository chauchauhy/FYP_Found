package com.example.fyp_found.datastru.non_str;

// this class is using for text
// recognize and some test

import java.util.Enumeration;
import java.util.Vector;

public class Text_Recognize {

    String text;
    Vector vector;



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Text_Recognize(String text) {
        this.text = text;
    }

    public Text_Recognize() {
        vector = new Vector();
        vector.add(new User("aaa"));
    }
    public Enumeration getClasss(){
        return vector.elements();
    }

}
