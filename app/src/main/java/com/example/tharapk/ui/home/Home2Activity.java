package com.example.tharapk.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tharapk.R;
import com.example.tharapk.SignExp;
import com.example.tharapk.ui.enquiry.EnquiryActivity;
import com.example.tharapk.ui.event.EventActivity;
import com.example.tharapk.ui.explore.ExploreActivity;
import com.example.tharapk.ui.feed.FeedActivity;
import com.example.tharapk.ui.map.MapActivity;
import com.example.tharapk.ui.notification.NotificationActivity;
import com.example.tharapk.ui.profile.ProfileActivity;
import com.example.tharapk.ui.sponsors.SponsorsActivity;
import com.example.tharapk.ui.team.TeamActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Home2Activity extends AppCompatActivity {

    private  Context mContext  = Home2Activity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        setActivityNavigation();
        setupDashboard();

    }

    // SETUP INTENT FOR ALL THE ACTIVITIES
    void setupDashboard(){
        RelativeLayout enquiry, sponsors, team, explore, collegeMap;

        enquiry = findViewById(R.id.ll_enquiry);
        sponsors = findViewById(R.id.ll_sponsors);
        team = findViewById(R.id.ll_team);
        explore = findViewById(R.id.ll_explore);
        collegeMap = findViewById(R.id.ll_collegeMap);

        enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(mContext, EnquiryActivity.class);
                startActivity(i1);
            }
        });

        sponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(mContext, SponsorsActivity.class);
                startActivity(i1);
            }
        });

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(mContext, TeamActivity.class);
                startActivity(i1);
            }
        });

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(mContext, ExploreActivity.class);
                startActivity(i1);
            }
        });

        collegeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(mContext, MapActivity.class);
                startActivity(i1);
            }
        });


    }


    //  SETUP ACTIVITY NAVIGATION BAR
    void setActivityNavigation(){

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


        // SETTING BOTTOM NAVIGATION
        SmoothBottomBar bottomNavigation;
        bottomNavigation = findViewById(R.id.bottomBar);

        bottomNavigation.setActiveItem(0);
        bottomNavigation.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {

                switch(i){

                    case 1 :
                        Intent i1 = new Intent(mContext, EventActivity.class);
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


    }


    @Override
    protected void onStart() {
        super.onStart();
        //  CHECK FOR USER AUTHENTICATION
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // USER NOT SIGNED-IN : REDIRECTING USER TO SIGNEXP ACTIVITY
            startActivity(new Intent(this, SignExp.class));
            finish();
        }
    }
}
