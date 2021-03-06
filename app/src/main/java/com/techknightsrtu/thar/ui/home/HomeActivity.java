package com.techknightsrtu.thar.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.techknightsrtu.thar.R;

import com.techknightsrtu.thar.SignInActivity;
import com.techknightsrtu.thar.ui.enquiry.EnquiryActivity;
import com.techknightsrtu.thar.ui.event.EventCategoryActivity;
import com.techknightsrtu.thar.ui.admin.AdminSignInActivity;
import com.techknightsrtu.thar.ui.explore.ExploreActivity;
import com.techknightsrtu.thar.ui.feed.FeedActivity;
import com.techknightsrtu.thar.ui.map.MapActivity;
import com.techknightsrtu.thar.ui.notification.NotificationActivity;
import com.techknightsrtu.thar.ui.profile.ProfileActivity;
import com.techknightsrtu.thar.ui.sponsors.SponsorsActivity;
import com.techknightsrtu.thar.ui.team.TeamActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class HomeActivity extends AppCompatActivity {

    private  Context mContext  = HomeActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setActivityNavigation();
        setupDashboard();

    }

    // SETUP INTENT FOR ALL THE ACTIVITIES
    void setupDashboard(){
        RelativeLayout enquiry, sponsors, team, explore, collegeMap,adminBlock;

        enquiry = findViewById(R.id.ll_enquiry);
        sponsors = findViewById(R.id.ll_sponsors);
        team = findViewById(R.id.ll_team);
        explore = findViewById(R.id.ll_explore);
        collegeMap = findViewById(R.id.ll_collegeMap);
        adminBlock = findViewById(R.id.ll_adminblock);

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

        adminBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(mContext, AdminSignInActivity.class);
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

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(mContext, ExploreActivity.class);
                i1.putExtra("url","http://www.rtuthar.in/");
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
                        Intent i1 = new Intent(mContext, EventCategoryActivity.class);
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
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
    }


   public void setSocialIcon(View v){

        String url ;

        switch(v.getId()){
            case R.id.website:
                url = "https://wwww.rtuthar.in/";break;
            case R.id.fb:
                url = "https://www.facebook.com/rtuthar/";break;
            case R.id.insta:
                url = "https://www.instagram.com/thar.rtu/";break;
            case R.id.linkedin:
                url = "https://www.linkedin.com/company/thar-rtu-kota/";break;
            default:
                url = "https://wwww.rtuthar.in";break;
        }


        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }



}
