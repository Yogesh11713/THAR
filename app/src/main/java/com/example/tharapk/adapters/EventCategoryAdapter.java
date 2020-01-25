package com.example.tharapk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tharapk.R;
import com.example.tharapk.models.EventCategory;

import java.util.List;

public class EventCategoryAdapter extends RecyclerView.Adapter<EventCategoryAdapter.MyViewHolder> {

    private Context mContext;
    List<EventCategory> mCategoryList;

    public EventCategoryAdapter(Context context, List<EventCategory> list){
        mContext = context;
        mCategoryList = list;
    }

    @NonNull
    @Override
    public EventCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_event_category,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventCategoryAdapter.MyViewHolder holder, int position) {

        holder.tvTitle.setText(mCategoryList.get(position).getCategoryName());
        holder.imgEvent.setImageResource(mCategoryList.get(position).getResourceId());

    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView imgEvent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_category_title);
            imgEvent = itemView.findViewById(R.id.iv_category_img);
        }
    }
}
