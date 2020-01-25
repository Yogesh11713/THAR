package com.example.tharapk.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

import com.example.tharapk.R;
import com.example.tharapk.ui.event.EventActivity;
import com.example.tharapk.ui.feed.FeedActivity;
import com.example.tharapk.ui.notification.NotificationActivity;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class ProfileActivity extends AppCompatActivity {

    private Context mContext = ProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    }



}
