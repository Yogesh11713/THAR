package com.example.tharapk.ui.sponsors;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.tharapk.R;
import com.example.tharapk.ui.sponsors.Adapter.MyAdapter;
import com.example.tharapk.ui.sponsors.Listener.IFirebaseLoadDone;
import com.example.tharapk.ui.sponsors.Model.Sponsor;
import com.example.tharapk.ui.sponsors.Transformer.DepthPageTransformer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SponsorsActivity extends AppCompatActivity implements IFirebaseLoadDone {

    ViewPager viewPager;
    MyAdapter adapter;

    DatabaseReference sponsors;

    IFirebaseLoadDone iFirebaseLoadDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        sponsors = FirebaseDatabase.getInstance().getReference("Sponsors");

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
        adapter = new MyAdapter(this, sponsorList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
}
