package com.example.tharapk.ui.event;

import android.os.Bundle;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tharapk.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventDetailActivity extends AppCompatActivity {

    private ImageView MovieThumbnailImg,MovieCoverImg;
    private TextView tv_title,tv_description;
    private FloatingActionButton play_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        // ini views
        iniViews();


    }

    void iniViews() {
        play_fab = findViewById(R.id.play_fab);

        String movieTitle = getIntent().getExtras().getString("title");
        int imageResourceId = getIntent().getExtras().getInt("imgURL");
        int imagecover = getIntent().getExtras().getInt("imgCover");

        MovieThumbnailImg = findViewById(R.id.detail_event_img);
        Glide.with(this).load(imageResourceId).into(MovieThumbnailImg);
        MovieThumbnailImg.setImageResource(imageResourceId);

        MovieCoverImg = findViewById(R.id.detail_event_cover);
        Glide.with(this).load(imagecover).into(MovieCoverImg);

        tv_title = findViewById(R.id.detail_event_title);
        tv_title.setText(movieTitle);
       // getSupportActionBar().setTitle(movieTitle);

        tv_description = findViewById(R.id.detail_event_desc);
        // setup animation
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));


    }

}
