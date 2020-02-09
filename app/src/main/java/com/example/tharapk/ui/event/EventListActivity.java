package com.example.tharapk.ui.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tharapk.R;

public class EventListActivity extends AppCompatActivity {

    private static final String TAG = "EventListActivity";

    private Context mContext = EventListActivity.this;

    private String mCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        mCategoryName = getIntent().getStringExtra("category_name");

        Toast.makeText(mContext, "Item clicked" + mCategoryName, Toast.LENGTH_SHORT).show();

    }

}
