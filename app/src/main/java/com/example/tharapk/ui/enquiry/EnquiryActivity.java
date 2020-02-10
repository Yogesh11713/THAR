package com.example.tharapk.ui.enquiry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.tharapk.R;
import com.example.tharapk.adapters.EnquiryAdapter;
import com.example.tharapk.adapters.EventCategoryAdapter;
import com.example.tharapk.models.Enquiry;
import com.example.tharapk.models.EventCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EnquiryActivity extends AppCompatActivity implements EnquiryAdapter.OnEnquiryListener {

    private static final String TAG = "EnquiryActivity";
    private Context mContext = EnquiryActivity.this;

    private List<Enquiry> mEnquiryList;
    private RecyclerView mEnquiryView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //SET BACK BUTTON
        ImageView btBack = findViewById(R.id.iv_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Enquiry");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                setEventCategoryList();

                for(DataSnapshot child : dataSnapshot.getChildren()){

                    mEnquiryList.add(
                            new Enquiry(child.child("name").getValue().toString(),
                            child.child("position").getValue().toString(),
                            child.child("phoneNumber").getValue().toString()
                    ));

                }

                Log.d(TAG, "onComplete: Enquiries" + mEnquiryList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    void setEventCategoryList(){

        mEnquiryView = findViewById(R.id.Rv_enquiry);

        mEnquiryList = new ArrayList<>();

        //   SETTING ADAPTER FOR THE RECYCLER VIEW
        EnquiryAdapter adapter = new EnquiryAdapter(mContext,mEnquiryList,this);
        mEnquiryView.setAdapter(adapter);

        //   SETTING ORIENTATION FOR THE RECYCLER VIEW
        mEnquiryView.setLayoutManager(
                new LinearLayoutManager(
                        mContext,
                        RecyclerView.VERTICAL,
                        false)
        );
    }

    @Override
    public void onEnquiryClick(int position) {

        String phoneNumber = mEnquiryList.get(position).getPhoneNumber();

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
