package com.example.tharapk.ui.team;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.tharapk.R;
import com.example.tharapk.adapters.TeamAdapter;
import com.example.tharapk.listeners.TeamFireBaseLoadDone;
import com.example.tharapk.models.TeamModel;
import com.example.tharapk.ui.sponsors.Transformer.DepthPageTransformer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity implements TeamFireBaseLoadDone {

    ViewPager viewPager;
    TeamAdapter adapter;

    DatabaseReference sponsors;

    TeamFireBaseLoadDone iFirebaseLoadDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        sponsors = FirebaseDatabase.getInstance().getReference("Teams/Teams");

        iFirebaseLoadDone = this;

        loadSponsor();



        viewPager = (ViewPager)findViewById(R.id.view_pager_team);
        viewPager.setPageTransformer(true,new DepthPageTransformer());
    }


    private void loadSponsor() {
        sponsors.addListenerForSingleValueEvent(new ValueEventListener() {

            List<TeamModel> sponsorList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sponsorSnapShot:dataSnapshot.getChildren())
                    sponsorList.add(sponsorSnapShot.getValue(TeamModel.class));
                iFirebaseLoadDone.onFirebaseLoadSuccess(sponsorList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<TeamModel> sponsorList) {
        adapter = new TeamAdapter(this, sponsorList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
}
