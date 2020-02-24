package com.example.tharapk.ui.feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tharapk.adapters.EventCategoryAdapter;
import com.example.tharapk.adapters.FeedAdapter;
import com.example.tharapk.models.EventCategory;
import com.example.tharapk.models.FeedPost;
import com.example.tharapk.ui.event.EventCategoryActivity;
import com.example.tharapk.ui.home.HomeActivity;
import com.example.tharapk.R;
import com.example.tharapk.ui.notification.NotificationActivity;
import com.example.tharapk.ui.profile.ProfileActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class FeedActivity extends AppCompatActivity {

    private Context mContext = FeedActivity.this;


    private List<FeedPost> mFeedList;

    private RecyclerView mFeed;

    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setActivityNavigation();

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("event_post").child("THAR2k20");
        mReference.keepSynced(true);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                setFeedList();
                for(DataSnapshot eventSnap : dataSnapshot.getChildren()){

                    String desc;

                    if(eventSnap.child("desc").getValue()== null ){
                         desc = "";
                    }else{
                        desc = eventSnap.child("desc").getValue().toString();
                    }

                    mFeedList.add(
                            new FeedPost(
                                    eventSnap.child("image_uri").getValue().toString(),
                                    desc,
                                    eventSnap.child("timestamp").getValue().toString(),
                                    eventSnap.child("uid").getValue().toString(),
                                    eventSnap.child("team_id").getValue().toString()
                            )
                    );


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                setFeedList();
            }
        });


    }


    void setFeedList(){

        mFeed = findViewById(R.id.FeedView);

        mFeedList = new ArrayList<>();

        //   SETTING ADAPTER FOR THE RECYCLER VIEW
        FeedAdapter adapter = new FeedAdapter(mContext,mFeedList);
        mFeed.setAdapter(adapter);

        //   SETTING ORIENTATION FOR THE RECYCLER VIEW

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mFeed.setLayoutManager(linearLayoutManager);

    }


    //  SETUP ACTIVITY NAVIGATION BAR
    void setActivityNavigation(){

        // SETTING BOTTOM NAVIGATION
        SmoothBottomBar bottomNavigation;
        bottomNavigation = findViewById(R.id.bottomBar);

        bottomNavigation.setActiveItem(2);
        bottomNavigation.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {

                switch(i){

                    case 1 :
                        Intent i1 = new Intent(mContext, EventCategoryActivity.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i1);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case 0 :
                        Intent i2 = new Intent(mContext, HomeActivity.class);
                        i2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i2);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                }

            }
        });


        //SETTING TOP TOOLBAR
        ImageView ivNotification, ivProfile;

        ivNotification = findViewById(R.id.iv_notification);
        ivProfile = findViewById(R.id.iv_profile);

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, NotificationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0,0);

            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0,0);

            }
        });



    }
}
