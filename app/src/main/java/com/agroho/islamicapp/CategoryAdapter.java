package com.agroho.islamicapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rezaul on 2015-10-05.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolderCategory> {


    private ArrayList<CategoryPOJO> listCategory = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    public String Categoryid;

    public CategoryAdapter(Context context){
        layoutInflater = layoutInflater.from(context);
        this.context = context;
    }

    public void setCategoryList(ArrayList<CategoryPOJO> listCategory){
        this.listCategory = listCategory;
        notifyItemRangeChanged(0,listCategory.size());
    }

    @Override
    public ViewHolderCategory onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.category_item_list, parent, false);
        ViewHolderCategory viewHolder = new ViewHolderCategory(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderCategory holder, int position) {
        CategoryPOJO currentQA = listCategory.get(position);
        holder.catID.setText(currentQA.getcategoryCount());
        holder.catTitle.setText(currentQA.getGetCategoryName());

    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class ViewHolderCategory extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView catID;
        private TextView catTitle;
        CategoryPOJO catObj = new CategoryPOJO();

        public ViewHolderCategory(View itemView) {
            super(itemView);
            catID = (TextView) itemView.findViewById(R.id.category_id);
            catTitle = (TextView) itemView.findViewById(R.id.category_title);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "SolaimanLipi.ttf");
            catTitle.setTypeface(font);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent ii=new Intent(context, CategoryDetailsQuestionsActivity.class);
            CategoryPOJO ObjCategory = listCategory.get(getAdapterPosition());
            Categoryid = ObjCategory.getCategoryId();
            ii.putExtra("CAT_ID",Categoryid);
            context.startActivity(ii);
        }
    }
}
