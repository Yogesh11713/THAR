package com.example.tharapk.ui.explore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tharapk.R;
import com.example.tharapk.adapters.EventAdapter;
import com.example.tharapk.adapters.EventItemClickListener;
import com.example.tharapk.adapters.SliderPagerAdapter;
import com.example.tharapk.models.Event;
import com.example.tharapk.models.Slide;
import com.example.tharapk.ui.event.EventDetailActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;

public class ExploreActivity extends AppCompatActivity implements EventItemClickListener {

    private Context mContext = ExploreActivity.this;

    RecyclerView updates;
    TabLayout indicator;
    ViewPager sliderPager;
    ArrayList lstSlides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        sliderPager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        updates = findViewById(R.id.Rv_events);

        setupSlider();

    }


    private void setupSlider(){

        // ITEMS IN THE SLIDES
        lstSlides = new ArrayList<Slide>();

        lstSlides.add(new Slide(R.drawable.thar_logo, "Slide Title \nmore text here"));
        lstSlides.add( new Slide(R.drawable.login_background, "Slide Title \nmore text here"));

        SliderPagerAdapter adapter = new SliderPagerAdapter(mContext, lstSlides);
        sliderPager.setAdapter(adapter);

        // SETUP TIMER FOR SLIDER
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
        indicator.setupWithViewPager(sliderPager,true);

        // EVENT RECYCLER VIEW SETUP
        ArrayList<Event> lstMovies = new ArrayList<>();
        lstMovies.add( new
                Event(
                        "Moana",
                        R.drawable.login_background,
                        R.drawable.thar_logo
                )
        );
        lstMovies.add( new
                Event(
                        "Moana",
                        R.drawable.login_background,
                        R.drawable.thar_logo
                )
        );
        lstMovies.add( new
                Event(
                        "Moana",
                        R.drawable.login_background,
                        R.drawable.thar_logo
                )
        );
        lstMovies.add( new
                Event(
                        "Moana",
                        R.drawable.login_background,
                        R.drawable.thar_logo
                )
        );

        EventAdapter movieAdapter = new EventAdapter(mContext, lstMovies, (EventItemClickListener) this);
        updates.setAdapter(movieAdapter);
        updates.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieClick(Event event, ImageView eventImageView) {

        Intent intent = new Intent(mContext, EventDetailActivity.class);
        // send movie information to detailActivity
        intent.putExtra("title", event.getTitle());
        intent.putExtra("imgURL", event.getThumbnail());
        intent.putExtra("imgCover",event.getThumbnail());

        // lets create the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) mContext,
                eventImageView,
                "sharedName");

        startActivity(intent, options.toBundle());

    }


    class SliderTimer extends TimerTask {
       @Override
       public void run() {

           runOnUiThread(new Runnable() {
               @Override
               public void run() {

                   if(sliderPager.getCurrentItem() < lstSlides.size() - 1){
                       sliderPager.setCurrentItem(sliderPager.getCurrentItem() + 1) ;
                   }else{
                       sliderPager.setCurrentItem(0);
                   }

               }
           });
       }
   }
}
