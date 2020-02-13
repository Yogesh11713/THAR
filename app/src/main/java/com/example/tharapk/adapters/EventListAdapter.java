package com.example.tharapk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tharapk.R;
import com.example.tharapk.models.EventCategory;
import com.example.tharapk.models.EventList;

import java.util.List;

public class EventListAdapter extends BaseAdapter {

    private Context mContext;
    List<EventList> mEventList;
    LayoutInflater thisInflator;

    private OnEventListener mOnEventListener;

    public EventListAdapter(Context context, List<EventList> list, OnEventListener onEventListener){
        mContext = context;
        this.thisInflator = LayoutInflater.from(context);
        mEventList = list;
        this.mOnEventListener = onEventListener;
    }

    @Override
    public int getCount() {
        return mEventList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            convertView = thisInflator.inflate( R.layout.item_event, parent, false );
            TextView tvEventTitle = convertView.findViewById(R.id.tv_event_title);
            TextView tvCategory = convertView.findViewById(R.id.tv_category);

            tvEventTitle.setText(mEventList.get(position).getEventName());
            tvCategory.setText(mEventList.get(position).getCategoryName().toUpperCase());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnEventListener.onEventClick(position);
                }
            });

        }

        return convertView;
    }

    public interface OnEventListener {

       void onEventClick(int position);

    }
}
