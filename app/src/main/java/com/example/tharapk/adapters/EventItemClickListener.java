package com.example.tharapk.adapters;

import android.widget.ImageView;

import com.example.tharapk.models.Event;

public interface EventItemClickListener {

    void onMovieClick(Event movie, ImageView movieImageView); // we will need the imageview to make the shared animation between the two activity

}
