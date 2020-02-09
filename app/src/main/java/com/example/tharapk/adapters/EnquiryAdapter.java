package com.example.tharapk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tharapk.R;
import com.example.tharapk.models.Enquiry;
import com.example.tharapk.models.EventCategory;

import java.util.List;

public class EnquiryAdapter extends RecyclerView.Adapter<EnquiryAdapter.MyViewHolder> {

    private Context mContext;
    List<Enquiry> mEnquiryList;

    private OnEnquiryListener mOnEnquiryListener;

    public EnquiryAdapter(Context context, List<Enquiry> list, OnEnquiryListener onEnquiryListener){
        mContext = context;
        mEnquiryList = list;
        this.mOnEnquiryListener = onEnquiryListener;
    }

    @NonNull
    @Override
    public EnquiryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_enquiry,parent,false);
        return new MyViewHolder(view, mOnEnquiryListener);

    }

    @Override
    public void onBindViewHolder(@NonNull EnquiryAdapter.MyViewHolder holder, int position) {

        holder.tvName.setText(mEnquiryList.get(position).getName());
        holder.tvPosition.setText(mEnquiryList.get(position).getPosition());
        holder.tvPhoneNumber.setText(mEnquiryList.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return mEnquiryList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName, tvPosition, tvPhoneNumber;
        private LinearLayout llCall;

        OnEnquiryListener onEnquiryListener;

        public MyViewHolder(@NonNull View itemView, OnEnquiryListener onEnquiryListener) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvPosition = itemView.findViewById(R.id.tv_position);
            tvPhoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
            llCall = itemView.findViewById(R.id.iv_call);

            this.onEnquiryListener = onEnquiryListener;
            llCall.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onEnquiryListener.onEnquiryClick(getAdapterPosition());
        }
    }

    public interface OnEnquiryListener {

       void onEnquiryClick(int position);

    }
}
