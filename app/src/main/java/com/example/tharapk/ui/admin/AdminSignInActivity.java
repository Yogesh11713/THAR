package com.example.tharapk.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.example.tharapk.R;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AdminSignInActivity extends AppCompatActivity  {

    private Context mContext = AdminSignInActivity.this;

    RecyclerView updates;
    TabLayout indicator;
    ViewPager sliderPager;
    ArrayList lstSlides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);

    }


}
