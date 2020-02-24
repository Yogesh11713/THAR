package com.example.tharapk.ui.event;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tharapk.R;
import com.example.tharapk.models.Event;
import com.example.tharapk.ui.explore.ExploreActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity";

    private ImageView eventImage;
    private TextView tv_title,tv_description;
    private Button bt_explore;

    private Event event;

    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;
    private String eventKey, mCategoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        event = new Event();

        eventKey = getIntent().getStringExtra("event_key");
        mCategoryName = getIntent().getStringExtra("event_category");

        //SET BACK BUTTON
        ImageView btBack = findViewById(R.id.iv_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("events").child(mCategoryName).child(eventKey);
        mReference.keepSynced(true);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                event.setTitle(dataSnapshot.child("name").getValue().toString());
                event.setDescription(dataSnapshot.child("description").getValue().toString());
                event.setCoverPhoto(dataSnapshot.child("image").getValue().toString());
                event.setStreamingLink(dataSnapshot.child("explore").getValue().toString());
                iniViews();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    void iniViews() {

        eventImage = findViewById(R.id.detail_event_img);
        tv_title = findViewById(R.id.detail_event_title);
        tv_description = findViewById(R.id.detail_event_desc);
        bt_explore = findViewById(R.id.bt_explore);

        tv_title.setText(event.getTitle());
        tv_description.setText(event.getDescription());
        Glide.with(this).load(event.getCoverPhoto()).into(eventImage);

        bt_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(EventActivity.this, ExploreActivity.class);
                i1.putExtra("url",event.getStreamingLink());
                startActivity(i1);
            }
        });

    }

}
