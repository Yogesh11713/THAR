package com.example.tharapk.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tharapk.R;
import com.example.tharapk.models.EventCategory;
import com.example.tharapk.models.FeedPost;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private Context mContext;
    List<FeedPost> mFeedList;

    public FeedAdapter(Context context, List<FeedPost> list){
        mContext = context;
        mFeedList = list;

    }

    @NonNull
    @Override
    public FeedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.feed_post,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.MyViewHolder holder, int position) {

            holder.setPostDesc(mFeedList.get(position).getDesc());
            holder.tvTime.setText(getTimeDate(mContext, mFeedList.get(position).getTimestamp()));
            holder.setPostImage(mFeedList.get(position).getImage_uri());

    }


    public static String getTimeDate(Context ctx, long timestamp) {

        String time = DateUtils.formatDateTime(ctx, timestamp, DateUtils.FORMAT_SHOW_TIME);
        String date = DateUtils.formatDateTime(ctx, timestamp, DateUtils.FORMAT_SHOW_DATE);
        String timeData = date + " at " + time;
        return timeData;
    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvDesc,tvTime;
        private ImageView imgEvent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.feed_description);
            imgEvent = itemView.findViewById(R.id.feed_post_image);
            tvTime = itemView.findViewById(R.id.post_time);


        }

        void setPostDesc(final String desc) {

            if (!desc.equals("")) {
                tvDesc.setVisibility(View.VISIBLE);
                tvDesc.setText(desc);

            } else {
                tvDesc.setVisibility(View.GONE);
            }
        }


        void setPostImage(final String imageUri) {

            if (!imageUri.equals("none")) {
                imgEvent.setVisibility(View.VISIBLE);
                Picasso.get().load(imageUri).into(imgEvent);

            } else {
                imgEvent.setVisibility(View.GONE);
            }
        }


    }


}
