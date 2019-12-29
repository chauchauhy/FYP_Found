package com.example.fyp_found.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp_found.R;
import com.example.fyp_found.datastru.questionAndAns;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.fyp_found.setup.staticclass.TAG;

public class Checking_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<questionAndAns> questionAndAnsArrayList;
    LayoutInflater layoutInflater;
    int i;

    public Checking_Adapter(Context context, ArrayList<questionAndAns> questionAndAnsArrayList){
        this.context = context;
        this.questionAndAnsArrayList = questionAndAnsArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_checking_question_list, parent, false);
        RecyclerView.ViewHolder viewHolder = new VH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        this.i = position;
        questionAndAns questionAndAns = questionAndAnsArrayList.get(position);
        VH vh =  (VH) holder;
        vh.question.setText(questionAndAns.getQuestion().toString().trim());
        vh.ans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                questionAndAnsArrayList.get(position).setResponse(editable.toString());
            }
        });

    }

    public ArrayList<questionAndAns> getQuestionAndAnsArrayList(){
        return questionAndAnsArrayList;
    }

    public int getPostion(){
        return i;
    }

    @Override
    public int getItemCount() {
        if(questionAndAnsArrayList.size()>=1){
            return questionAndAnsArrayList.size();
        }else{
            return 0;
        }
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView question;
        EditText ans;

        public VH(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.checking_question_textView);
            ans = itemView.findViewById(R.id.checking_question_editText);
        }
    }
}
