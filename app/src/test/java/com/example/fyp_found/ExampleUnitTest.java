package com.example.fyp_found;

import com.example.fyp_found.class_Package.CompareText;
import com.example.fyp_found.datastru.non_str.Text_Recognize;
import com.example.fyp_found.datastru.non_str.User;

import org.junit.Test;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Enumeration;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        String s = new String("RM. 1514, 15/F, BLK. A, SHEK TO HSE., SHEK WAI KOK EST., TSUEN WAN, NT, Hong Kong");
        String b = s;
        assert s != null;
        String a = " shek wai kok";
        String aa = "shek kok house";
        CompareText compareText = new CompareText(s, a);
        System.out.println(compareText.compareText());

        System.out.println(s.toLowerCase().contains(a));

        Text_Recognize text_recognize = new Text_Recognize();
        Enumeration<User> e = text_recognize.getClasss();

    }

        private double compareText(String firstText , String secondText){
        double mark = 0;
        double total_Mark = 0;
        if(firstText.toLowerCase().equals(secondText.toLowerCase())){
            return 100;
        }else{
            String first = firstText.toLowerCase();
            final String second = secondText.toLowerCase();
            ArrayList<String> first_arrayList = new ArrayList<>();
            final ArrayList<String> second_arrayList = new ArrayList<>();
            char[] first_array = new char[first.length()];
            char[] second_array = new char[second.length()];
            for(int i = 0 ; i < first.length(); i++){
                first_array[i] = first.charAt(i);
            }
            for(int i = 0 ; i < second.length(); i++){
                second_array[i] = second.charAt(i);
            }
            for(char c : first_array){
                if((!String.valueOf(c).equals(",")&& !String.valueOf(c).equals(" ") && !String.valueOf(c).equals(".") && !String.valueOf(c).equals("/"))){
                    first_arrayList.add(String.valueOf(c));
                }
            }
            for(char c : second_array){
                if((!String.valueOf(c).equals(",")&& !String.valueOf(c).equals(" ") && !String.valueOf(c).equals(".") && !String.valueOf(c).equals("/"))){
                    second_arrayList.add(String.valueOf(c));
                }
            }
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
            int mark_int = (int) mark;
            if(mark_int >= second_arrayList.size() && second_arrayList.size()>first_arrayList.size()/2){
                System.out.println("the ans is " + true);
            }else {
                System.out.println("the ans is " + false);
            }

            System.out.println(mark);
            System.out.println(second_arrayList.size());
//            System.out.println(first_arrayList.remove(second_arrayList.get(0)));
            System.out.println(first_arrayList);


            total_Mark = first.length()/100 + first.length()%100;


            System.out.println((mark/total_Mark + mark% total_Mark) );
            System.out.println(mark);

            return mark;
        }



        }



}