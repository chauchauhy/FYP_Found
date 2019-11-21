package com.example.fyp_found.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp_found.R;
import com.example.fyp_found.datastru.Chat_record;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.fyp_found.setup.staticclass;
import java.util.ArrayList;
import java.util.Collections;

public class Chat_Box_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Chat_record> chat_records;
    LayoutInflater layoutInflater;
    FirebaseUser firebaseUser;

    public Chat_Box_Adapter(ArrayList<Chat_record> chat_records, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.chat_records = chat_records;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == staticclass.final_static_int_MSG_TYPE_LEFT) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_chat_box_left, parent, false);
                RecyclerView.ViewHolder vh = new VH(view);
                return vh;
            }else{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_chat_box_right, parent, false);
                RecyclerView.ViewHolder vh = new VH(view);
                return  vh;
            }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Collections.sort(chat_records, new Chat_record());
        Chat_record chat_record = chat_records.get(position);
        VH viewholder = (VH) holder;
        viewholder.sender.setText(chat_record.getChat_Content());
        viewholder.time.setText(chat_record.getChat_Time());
        if(position%5 == 0 && position> 5 || position ==1){
            viewholder.time.setVisibility(View.VISIBLE);
        }else{
            viewholder.time.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        if (this.chat_records.size() > 0) {
            return this.chat_records.size();
        } else {
            return 0;
        }
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView time,sender;

        public VH(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.chatbox_record_time);
            sender = itemView.findViewById(R.id.chatbox_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chat_records.get(position).getChat_sender_ID().equals(firebaseUser.getUid())){
            return staticclass.final_static_int_MSG_TYPE_REGHT;
        }else{
            return staticclass.final_static_int_MSG_TYPE_LEFT;
        }
    }
}
