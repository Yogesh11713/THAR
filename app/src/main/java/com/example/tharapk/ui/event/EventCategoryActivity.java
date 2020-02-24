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
import android.widget.ImageView;

import com.example.tharapk.adapters.EventCategoryAdapter;
import com.example.tharapk.models.EventCategory;
import com.example.tharapk.ui.home.HomeActivity;
import com.example.tharapk.R;
import com.example.tharapk.ui.feed.FeedActivity;
import com.example.tharapk.ui.notification.NotificationActivity;
import com.example.tharapk.ui.profile.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class EventCategoryActivity extends AppCompatActivity implements EventCategoryAdapter.OnCategoryListener {

    private static final String TAG = "EventCategoryActivity";

    private Context mContext = EventCategoryActivity.this;

    private List<EventCategory> mCategoryList;

    private RecyclerView mEventCategories;

    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_category);

        setActivityNavigation();

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("events");
        mReference.keepSynced(true);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                setEventCategoryList();
                for(DataSnapshot eventSnap : dataSnapshot.getChildren()){

                    String categoryName = eventSnap.getKey();
                    int bannerId;

                    if(categoryName.equals("aeromodelling")){

                        bannerId = R.drawable.aeromodelling;

                    }else if(categoryName.equals("automobile")){

                        bannerId = R.drawable.automobile;

                    }else if(categoryName.equals("business")){

                        bannerId = R.drawable.business;

                    }else if(categoryName.equals("community")){

                        bannerId = R.drawable.wordsworth;

                    }else if(categoryName.equals("construction")){

                        bannerId = R.drawable.construction;

                    }else if(categoryName.equals("marketing")){

                        bannerId = R.drawable.marketing;

                    }else if(categoryName.equals("programming")){

                        bannerId = R.drawable.programming;

                    }else if(categoryName.equals("robotics")){

                        bannerId = R.drawable.robotics;

                    }else if(categoryName.equals("wordsworth")){

                        bannerId = R.drawable.wordsworth;

                    }else{

                        bannerId = R.drawable.aeromodelling;
                    }

                    mCategoryList.add(new EventCategory(categoryName.toUpperCase(),bannerId));

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                setEventCategoryList();
            }
        });

    }

   void setEventCategoryList(){

       mEventCategories = findViewById(R.id.Rv_events);

       mCategoryList = new ArrayList<>();

       //   SETTING ADAPTER FOR THE RECYCLER VIEW
       EventCategoryAdapter adapter = new EventCategoryAdapter(mContext,mCategoryList,this);
       mEventCategories.setAdapter(adapter);

       //   SETTING ORIENTATION FOR THE RECYCLER VIEW
       mEventCategories.setLayoutManager(
               new LinearLayoutManager(
                       mContext,
                       RecyclerView.VERTICAL,
                       false)
       );
    }


    //  SETUP ACTIVITY NAVIGATION BAR
    void setActivityNavigation(){

        // SETTING BOTTOM NAVIGATION
        SmoothBottomBar bottomNavigation;
        bottomNavigation = findViewById(R.id.bottomBar);

        bottomNavigation.setActiveItem(1);
        bottomNavigation.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {

                switch(i){

                    case 0 :
                        Intent i1 = new Intent(mContext, HomeActivity.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i1);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case 2 :
                        Intent i2 = new Intent(mContext, FeedActivity.class);
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
                Intent intent;intent = new Intent(mContext, NotificationActivity.class);
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


    @Override
    public void onCategoryClick(int position) {

        Intent i = new Intent(mContext, EventListActivity.class);

        EventCategory eventCategory = mCategoryList.get(position);
        i.putExtra("category_name",eventCategory.getCategoryName());
        startActivity(i);

    }
}
