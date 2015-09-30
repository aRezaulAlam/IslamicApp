package com.agroho.islamicapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Collections;
import java.util.List;

/**
 * Created by ASUS on 7/17/2015.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ClickListener clickListener;
    List<Information> data = Collections.emptyList();
    public DataAdapter(Context context, List<Information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_nav_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView)itemView.findViewById(R.id.list_icon);
            title = (TextView)itemView.findViewById(R.id.list_title);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "SolaimanLipi.ttf");
            title. setTypeface(font);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if(clickListener!=null){
                clickListener.itemClicked(v,getAdapterPosition());
            }
        }
/*
            if (getAdapterPosition()==1){
                context.startActivity(new Intent(context, WriteQuestion_Activity.class));

            } else
            Toast.makeText(context,"Item Clicked on"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
        }

*/

    }

    public interface ClickListener{

        public void itemClicked(View view , int position);
    }





}



