package com.example.tharapk.ui.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.tharapk.R;
import com.example.tharapk.adapters.EventCategoryAdapter;
import com.example.tharapk.adapters.EventListAdapter;
import com.example.tharapk.models.EventCategory;
import com.example.tharapk.models.EventList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity implements EventListAdapter.OnEventListener {

    private static final String TAG = "EventListActivity";

    private Context mContext = EventListActivity.this;

    private String mCategoryName;

    private List<EventList> mEventsList;
    EventListAdapter adapter;

    private GridView mEvents;

    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        mCategoryName = getIntent().getStringExtra("category_name");
        mCategoryName = mCategoryName.toLowerCase();

        Log.d(TAG, "onCreate: "+ mCategoryName);

        mEvents = findViewById(R.id.Gv_events);
        mEventsList = new ArrayList<>();

        adapter = new EventListAdapter(mContext,mEventsList,this);

        //SET BACK BUTTON
        ImageView btBack = findViewById(R.id.iv_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("events").child(mCategoryName);
        mReference.keepSynced(true);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot eventSnap : dataSnapshot.getChildren()){

                    String eventName = eventSnap.child("name").getValue().toString();
                    Log.d(TAG, "onDataChange: " + eventName);
                    mEventsList.add(new EventList(eventName,mCategoryName,eventSnap.getKey()));

                }
                Log.d(TAG, "onDataChange: EVENTS LIST" + mEventsList.get(0).getEventName());

                //   SETTING ADAPTER FOR THE RECYCLER VIEW
                mEvents.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onEventClick(int position) {
        Intent i = new Intent(mContext, EventActivity.class);
        EventList eventList = mEventsList.get(position);
        i.putExtra("event_category",eventList.getCategoryName());
        i.putExtra("event_key",eventList.getEventKey());
        startActivity(i);
    }

}
