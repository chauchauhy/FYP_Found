package com.example.fyp_found.datastru;

import com.example.fyp_found.class_Package.CompareText;

public class questionAndAns<ans> {
    String question;
    String ans;
    String response;
    boolean result;

    public questionAndAns(String question, String ans){
        this.question = question;
        this.ans = ans.toLowerCase().trim();
    }

    @Override
    public String toString() {
        return "questionAndAns{" +
                "question='" + question + '\'' +
                ", ans='" + ans + '\'' +
                ", response='" + response + '\'' +
                ", result=" + result +
                '}';
    }

    public void setQuestion(String question){
        this.question = question;
    }
    public void setResult(boolean result){
        this.result = result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response.toLowerCase().trim();
        compareQA();
    }

    public void setAns(String ans){
        this.ans = ans;

    }
    public boolean getResult(){
        return result;
    }
    public String getQuestion(){
        return question;
    }
    public String getAns(){return ans;}

    public void compareQA(){
        CompareText compareText = new CompareText(ans, response);
        result = compareText.getResult();

    }
}
