package com.example.fyp_found.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp_found.EditPost;
import com.example.fyp_found.R;
import com.example.fyp_found.datastru.Current_Lost_Record;
import com.example.fyp_found.datastru.Reward;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Profile_Post_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Current_Lost_Record> records;
    Context context;
    LayoutInflater layoutInflater;


    public Profile_Post_Adapter(Context context, ArrayList<Current_Lost_Record> records){
        this.records = records;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cus_profile_list, parent, false);
        RecyclerView.ViewHolder vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH viewhelder = (VH) holder;
        final Current_Lost_Record record = records.get(position);
        Picasso.with(context).load(record.getImageURL()).error(R.drawable.icon_app).into(viewhelder.iconforPost);
        viewhelder.title.setText(record.getCurrent_Lost_Property_Name());
        viewhelder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(context, EditPost.class);
                i.putExtra("Position", records.get(position).getCurrent_Lost_ID());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        if (records.size()>0){
            return records.size();
        }else {
            return 0;
        }
    }


    public class VH extends RecyclerView.ViewHolder {
        ImageView iconforPost;
        TextView title;
        LinearLayout linearLayout;

        public VH(@NonNull View itemView) {
            super(itemView);
            iconforPost = itemView.findViewById(R.id.profile_list_image);
            title = itemView.findViewById(R.id.profile_list_title);
            linearLayout = itemView.findViewById(R.id.listMainLayout_profile);
        }
    }
}
