package com.example.fyp_found.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp_found.Checking;
import com.example.fyp_found.R;
import com.example.fyp_found.datastru.Current_Lost_Record;
import com.example.fyp_found.setup.staticclass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

import static com.example.fyp_found.setup.staticclass.TAG;
import static com.example.fyp_found.setup.staticclass.final_static_str_Current_Lost_ID;

public class HomePage_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements Filterable {
    LayoutInflater layoutInflater;
    List<Current_Lost_Record> records ;
    Context context;
    Boolean logined;
    List<Current_Lost_Record> records_All;

    public HomePage_Adapter(ArrayList<Current_Lost_Record> records, Context context, Boolean logined){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.records = records;
        records_All =  records;
        this.logined = logined;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_homepage, parent, false);
        RecyclerView.ViewHolder viewHolder = new VH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        final Current_Lost_Record current_lost_record = records.get(position);
        Picasso.with(context).load(current_lost_record.getImageURL()).into(vh.imageview);
        vh.title.setText(current_lost_record.getCurrent_Lost_Property_Name());
        vh.otherinfo.setText("Type : " + current_lost_record.getCurrent_Lost_Property_MainType());
        vh.constraintLayout.setClickable(true);
        vh.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Checking.class);
                i.putExtra(final_static_str_Current_Lost_ID, current_lost_record.getCurrent_Lost_ID());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(records.size()>1){
            return records.size();
        }else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return newFilter;
    }

   private Filter newFilter = new Filter() {
       @Override
       protected FilterResults performFiltering(CharSequence charSequence) {
            List<Current_Lost_Record> filterlist = new ArrayList<>();
            // get search view text
            if(charSequence == null || charSequence.toString().length() == 0){
                filterlist.addAll(records_All);

            }else{
                String filterString = charSequence.toString().toLowerCase().trim();
                for(Current_Lost_Record record : records_All){
                    if(record.getForFilter().toLowerCase().trim().contains(filterString)){
                        filterlist.add(record);
                        Log.i(TAG , record.getForFilter());
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterlist;
            return filterResults;


       }

       @Override
       protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            records.clear();
            records.addAll((List) filterResults.values);
            Log.i(TAG, String.valueOf(records.size()) + "QQQ");
            notifyDataSetChanged();
       }
   };



    public class VH extends RecyclerView.ViewHolder{
        ImageView imageview;
        TextView title;
        TextView otherinfo;
        ConstraintLayout constraintLayout;

        public VH(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageView);
            title     = itemView.findViewById(R.id.title);
            otherinfo = itemView.findViewById(R.id.otherinfo);
            constraintLayout = itemView.findViewById(R.id.postView);

        }
    }





}
