package com.techknightsrtu.thar.ui.sponsors;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.techknightsrtu.thar.R;
import com.techknightsrtu.thar.adapters.SponsorsAdapter;
import com.techknightsrtu.thar.listeners.IFirebaseLoadDone;
import com.techknightsrtu.thar.models.Sponsor;
import com.techknightsrtu.thar.transformer.DepthPageTransformer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SponsorsActivity extends AppCompatActivity implements IFirebaseLoadDone {

    ViewPager viewPager;
    SponsorsAdapter adapter;

    DatabaseReference sponsors;

    IFirebaseLoadDone iFirebaseLoadDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        //SET BACK BUTTON
        ImageView btBack = findViewById(R.id.iv_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sponsors = FirebaseDatabase.getInstance().getReference("Sponsors");
        sponsors.keepSynced(true);

        iFirebaseLoadDone = this;

        loadSponsor();

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setPageTransformer(true,new DepthPageTransformer());
    }


    private void loadSponsor() {
        sponsors.addListenerForSingleValueEvent(new ValueEventListener() {

            List<Sponsor> sponsorList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sponsorSnapShot:dataSnapshot.getChildren())
                    sponsorList.add(sponsorSnapShot.getValue(Sponsor.class));
                iFirebaseLoadDone.onFirebaseLoadSuccess(sponsorList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<Sponsor> sponsorList) {
        adapter = new SponsorsAdapter(this, sponsorList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
}
