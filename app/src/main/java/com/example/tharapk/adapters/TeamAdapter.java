package com.example.tharapk.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tharapk.listeners.TeamFireBaseLoadDone;
import com.example.tharapk.models.Team;
import com.example.tharapk.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamAdapter extends PagerAdapter {

    private static final String TAG = "TeamAdapter";

    Context context;
    List<Team> sponsorsList;
    LayoutInflater inflater;

    TeamFireBaseLoadDone iFirebaseLoadDone;

    public TeamAdapter(Context context, List<Team> sponsorsList,  TeamFireBaseLoadDone iFirebaseLoadDone) {
        this.context = context;
        this.sponsorsList = sponsorsList;
        inflater = LayoutInflater.from(context);
        this.iFirebaseLoadDone = iFirebaseLoadDone;
    }

    @Override
    public int getCount() {
        return sponsorsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final View view = inflater.inflate(R.layout.team_view_pager_items,container, false);

        ImageView sponsor_image = (ImageView)view.findViewById(R.id.sponsor_image);
        TextView sponsor_title = (TextView)view.findViewById(R.id.sponsor_title);
        TextView team_title = (TextView)view.findViewById(R.id.team_title);

        ImageView linkedin = (ImageView)view.findViewById(R.id.linkedin);
        ImageView insta = (ImageView)view.findViewById(R.id.instagram);
        ImageView github = (ImageView)view.findViewById(R.id.github);

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url =  sponsorsList.get(position).getLinkedin();

                Log.d(TAG, "onClick: " + url);
                iFirebaseLoadDone.onIconClick(url.trim());

            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =  sponsorsList.get(position).getInstagram();
                iFirebaseLoadDone.onIconClick(url.trim());
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =  sponsorsList.get(position).getGithub();
                iFirebaseLoadDone.onIconClick(url.trim());
            }
        });


        Picasso.get().load(sponsorsList.get(position).getImage()).into(sponsor_image);
        sponsor_title.setText(sponsorsList.get(position).getName());
        team_title.setText(sponsorsList.get(position).getTitle());

        container.addView(view);
        return view;

    }
}
