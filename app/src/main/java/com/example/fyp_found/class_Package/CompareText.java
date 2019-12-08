package com.example.fyp_found.class_Package;

import java.util.ArrayList;

public class CompareText {
    String firstText;
    String secondText;
    double mark = 0;
    double total_Mark = 0;
    int mark_int = 0;
    ArrayList<String> first_arrayList = new ArrayList<>();
    ArrayList<String> second_arrayList = new ArrayList<>();

    public CompareText(String firstText, String secondText) {
        this.firstText = firstText;
        this.secondText = secondText;
        if(!sameText()){
            toArrayList(firstText,secondText);
            total_Mark = first_arrayList.size() ;
        }else if(sameText()){

        }


    }
    public boolean sameText(){
        if(firstText.toLowerCase().equals(secondText.toLowerCase())){
            return true;
        }
        return false;
    }

    public void toArrayList(String firstText, String secondText){
        final char[] first_array = new char[firstText.length()];
        final char[] second_array = new char[secondText.length()];
        for (int i = 0; i < firstText.length(); i++) {
            first_array[i] = firstText.toLowerCase().charAt(i);
        }
        for (int i = 0; i < secondText.length(); i++) {
            second_array[i] = secondText.toLowerCase().charAt(i);
        }
        for (char c : first_array) {
            if ((!String.valueOf(c).equals(",") && !String.valueOf(c).equals(" ") && !String.valueOf(c).equals(".") && !String.valueOf(c).equals("/"))) {
                this.first_arrayList.add(String.valueOf(c).toLowerCase());
            }
        }
        for (char c : second_array) {
            if ((!String.valueOf(c).equals(",") && !String.valueOf(c).equals(" ") && !String.valueOf(c).equals(".") && !String.valueOf(c).equals("/"))) {
                this.second_arrayList.add(String.valueOf(c).toLowerCase());
            }
        }

    }

    public double compareText() {
        int contarnsMark = 0;
        if(second_arrayList.size()>first_arrayList.size()) {
            for (int i = 0; i < first_arrayList.size(); i++) {
                if (first_arrayList.contains(second_arrayList.get(i))) {
                    first_arrayList.remove(second_arrayList.get(i));
                    mark = mark + 1;

                }
            }
        }else {
            for (int i = 0; i < second_arrayList.size(); i++) {
                if (first_arrayList.contains(second_arrayList.get(i))) {
                    first_arrayList.remove(second_arrayList.get(i));
                    mark = mark + 1;

                }
            }

        }

        if(mark == second_arrayList.size()){
            return 100;
        }

        int mark_int = (int) mark;
        if(mark_int >= second_arrayList.size() && second_arrayList.size()>first_arrayList.size()/4){
            System.out.println("the ans is " + true);

        }else {
            System.out.println("the ans is " + false);
        }
        System.out.println(mark / total_Mark );



        if(contarnsMark == second_arrayList.size()){
            return  100;
        }



        return mark;
        }
    }

