package com.example.tharapk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tharapk.models.TeamModel;
import com.example.tharapk.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamAdapter extends PagerAdapter {

    Context context;
    List<TeamModel> sponsorsList;
    LayoutInflater inflater;

    public TeamAdapter(Context context, List<TeamModel> sponsorsList) {
        this.context = context;
        this.sponsorsList = sponsorsList;
        inflater = LayoutInflater.from(context);
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
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.team_view_pager_items,container, false);

        ImageView sponsor_image = (ImageView)view.findViewById(R.id.sponsor_image);
        TextView sponsor_title = (TextView)view.findViewById(R.id.sponsor_title);
        TextView team_title = (TextView)view.findViewById(R.id.team_title);
//        TextView sponsor_description = (TextView)view.findViewById(R.id.sponsor_description);


        Picasso.get().load(sponsorsList.get(position).getImage()).into(sponsor_image);
        sponsor_title.setText(sponsorsList.get(position).getName());
        team_title.setText(sponsorsList.get(position).getTitle());
//        sponsor_description.setText(sponsorsList.get(position).getDescription());



//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Film Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

        container.addView(view);
        return view;

    }
}
