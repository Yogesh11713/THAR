package com.example.tharapk.ui.feed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tharapk.ui.home.HomeActivity;
import com.example.tharapk.R;
import com.example.tharapk.ui.event.EventActivity;
import com.example.tharapk.ui.notification.NotificationActivity;
import com.example.tharapk.ui.profile.ProfileActivity;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class FeedActivity extends AppCompatActivity {

    private Context mContext = FeedActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setActivityNavigation();
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
                        Intent i1 = new Intent(mContext, EventActivity.class);
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
