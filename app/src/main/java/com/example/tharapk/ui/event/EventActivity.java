package com.example.tharapk.ui.event;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tharapk.R;
import com.example.tharapk.models.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity";

    private ImageView eventImage;
    private TextView tv_title,tv_description;

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

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("events").child(mCategoryName).child(eventKey);
        mReference.keepSynced(true);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                event.setTitle(dataSnapshot.child("name").getValue().toString());
                iniViews();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    void iniViews() {

        eventImage = findViewById(R.id.detail_event_img);

       // Glide.with(this).load(imagecover).into(MovieCoverImg);

        tv_title = findViewById(R.id.detail_event_title);
        tv_title.setText(event.getTitle());
       // getSupportActionBar().setTitle(movieTitle);

        tv_description = findViewById(R.id.detail_event_desc);
        // setup animation
        
    }

}
