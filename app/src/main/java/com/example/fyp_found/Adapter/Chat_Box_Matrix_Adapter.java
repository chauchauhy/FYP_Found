package com.example.fyp_found.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp_found.ChatBox;
import com.example.fyp_found.R;
import com.example.fyp_found.datastru.Firebase_User;
import com.example.fyp_found.setup.staticclass;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Chat_Box_Matrix_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Firebase_User> firebase_users;
    LayoutInflater layoutInflater;
    Context context;






    // const
    public  Chat_Box_Matrix_Adapter(ArrayList<Firebase_User> firebase_users, Context context){
        this.firebase_users = firebase_users;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);

    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_chat_box_matrix, parent, false);
        RecyclerView.ViewHolder viewHolder =new VH(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh =(VH) holder;
        final Firebase_User  firebase_user = firebase_users.get(position);
        vh.username.setText(firebase_user.getUser_Name());
        vh.username.setClickable(true);
        vh.imageView.setClickable(true);
        // icon url needed
        vh.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ChatBox.class);
                i.putExtra(staticclass.final_static_str_Intent_Chatoppun, firebase_user.getUser_Id());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(firebase_users.size()>=1){
            return firebase_users.size();
        }else {
            return 0;
        }
    }

    public class VH extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView username;
        LinearLayout linearLayout;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.username = itemView.findViewById(R.id.chatbox_matrix_username);
            this.imageView = itemView.findViewById(R.id.chatbox_matrix_userimage);
            this.linearLayout = itemView.findViewById(R.id.chatbox_matrix_layout);
            username.setClickable(true);
            imageView.setClickable(true);





        }
    }
}
