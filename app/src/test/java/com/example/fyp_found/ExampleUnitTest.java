package com.example.fyp_found;

import com.example.fyp_found.class_Package.CompareText;
import com.example.fyp_found.datastru.Current_Lost_Record;
import com.example.fyp_found.datastru.non_str.Text_Recognize;

import com.example.fyp_found.setup.staticclass;

import org.junit.Test;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;

import static com.example.fyp_found.setup.staticclass.final_static_str_array_Current_Lost_Array;
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
        HashMap<String, String> hashMap = new HashMap<>();
        Current_Lost_Record record = new Current_Lost_Record("ID " + getRandomIndex(),"Name" + getRandomIndex(),"TITLE" + getRandomIndex(),
                "QA1 " + getRandomIndex(),"QA2 " + getRandomIndex(),"QA1A " + getRandomIndex(),
                "QA2A " + getRandomIndex(),
                "ADDRESS" + getRandomIndex(),"T1" + getRandomIndex(),
                "T2" + getRandomIndex(),"T3" + getRandomIndex(),
                "T4" + getRandomIndex(),"T5" + getRandomIndex(),
                "OTHER" + getRandomIndex(),"URL " + getRandomIndex(),"BOOLEAN" + getRandomIndex());
        String[] array = record.getArray();
        for (int i =0; i<array.length; i++){
            hashMap.put(staticclass.final_static_str_array_Current_Lost_Array[i], array[i]);
        }
//        System.out.println(hashMap.toString());
        for(String key: final_static_str_array_Current_Lost_Array){
            System.out.println(hashMap.get(key));

        }
    }
        private int getRandomIndex(){
            Random random = new Random();
            int randomnum = random.nextInt(10);
            System.out.println(randomnum);
            return randomnum;
        }
        private void basicTextTast(){
            String s = new String("RM. 1514, 15/F, BLK. A, SHEK TO HSE., SHEK WAI KOK EST., TSUEN WAN, NT, Hong Kong");
            String b = s;
            assert s != null;
            String a = " shek wai kok";
            String aa = "shek kok house";
            CompareText compareText = new CompareText(s, a);
            System.out.println(compareText.compareText());

            System.out.println(s.toLowerCase().contains(a));

            Text_Recognize text_recognize = new Text_Recognize();
            for (String key : final_static_str_array_Current_Lost_Array){
                System.out.println(key);
            }
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