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
import com.example.tharapk.models.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    Context context ;
    List<Event> mData;
    EventItemClickListener eventItemClickListener;


    public EventAdapter(Context context, List<Event> mData, EventItemClickListener listener) {
        this.context = context;
        this.mData = mData;
        eventItemClickListener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(context).inflate(R.layout.item_event,viewGroup,false);
        return new MyViewHolder(view);


        }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        myViewHolder.tvTitle.setText(mData.get(i).getTitle());
        myViewHolder.imgEvent.setImageResource(mData.get(i).getThumbnail());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView tvTitle;
        private ImageView imgEvent;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
//            tvTitle = itemView.findViewById(R.id.item_event_title);
//            imgEvent = itemView.findViewById(R.id.item_event_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Event event;
                    ImageView movieImageView;

                    event = mData.get(getAdapterPosition());
                    movieImageView = imgEvent;

                    eventItemClickListener.onMovieClick(event, movieImageView);

                }
            });

        }
    }
}
