package com.agroho.islamicapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rezaul on 2015-09-29.
 */
public class QAAdapter extends RecyclerView.Adapter<QAAdapter.ViewHolderQA> {

    private ArrayList<QAInfo> listQA = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;


    public QAAdapter(Context context){
        layoutInflater = layoutInflater.from(context);
        this.context = context;
    }

    public void setQAList(ArrayList<QAInfo> listQA){
        this.listQA = listQA;
        notifyItemRangeChanged(0,listQA.size());
    }

    @Override
    public ViewHolderQA onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.qa_list_item,parent,false);
        ViewHolderQA viewHolder = new ViewHolderQA(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderQA holder, int position) {
        QAInfo currentQA = listQA.get(position);
        holder.quesId.setText(currentQA.getId());
        holder.quesTitle.setText(currentQA.getqaTitle());
        holder.quesCategory.setText(currentQA.getCategory());

    }

    @Override
    public int getItemCount() {
        return listQA.size();
    }

     class ViewHolderQA extends RecyclerView.ViewHolder{

        private TextView quesId;
        private TextView quesTitle;
        private TextView quesCategory;


        public ViewHolderQA(View itemView) {
            super(itemView);
            quesId = (TextView)itemView.findViewById(R.id.question_id);
            quesTitle = (TextView)itemView.findViewById(R.id.question_title);
            quesCategory = (TextView)itemView.findViewById(R.id.question_category);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "SolaimanLipi.ttf");
            quesTitle. setTypeface(font);
            quesCategory. setTypeface(font);


            //itemView.setOnClickListener(this);


        }
    }
}
