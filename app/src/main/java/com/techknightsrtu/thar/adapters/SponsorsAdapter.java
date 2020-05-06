package com.techknightsrtu.thar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.techknightsrtu.thar.models.Sponsor;
import com.techknightsrtu.thar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SponsorsAdapter extends PagerAdapter {

    Context context;
    List<Sponsor> sponsorsList;
    LayoutInflater inflater;

    public SponsorsAdapter(Context context, List<Sponsor> sponsorsList) {
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
        View view = inflater.inflate(R.layout.view_pager_item,container, false);

        ImageView sponsor_image = view.findViewById(R.id.sponsor_image);
        TextView sponsor_title = view.findViewById(R.id.sponsor_title);
        TextView sponsor_description = view.findViewById(R.id.sponsor_description);

        Picasso.get().load(sponsorsList.get(position).getImage()).into(sponsor_image);
        sponsor_title.setText(sponsorsList.get(position).getName());
        sponsor_description.setText(sponsorsList.get(position).getDescription());

        container.addView(view);
        return view;

    }
}
