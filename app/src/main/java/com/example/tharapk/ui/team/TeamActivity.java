package com.example.tharapk.ui.team;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

    DatabaseReference teams;

    TeamFireBaseLoadDone iFirebaseLoadDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        //SET BACK BUTTON
        ImageView btBack = findViewById(R.id.iv_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        teams = FirebaseDatabase.getInstance().getReference("Teams/Teams");
        teams.keepSynced(true);

        iFirebaseLoadDone = this;

        loadSponsor();

        viewPager = (ViewPager)findViewById(R.id.view_pager_team);
        viewPager.setPageTransformer(true,new DepthPageTransformer());
    }


    private void loadSponsor() {

        teams.addListenerForSingleValueEvent(new ValueEventListener() {

            List<TeamModel> teamList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sponsorSnapShot:dataSnapshot.getChildren())
                    teamList.add(sponsorSnapShot.getValue(TeamModel.class));
                iFirebaseLoadDone.onFirebaseLoadSuccess(teamList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<TeamModel> teamList) {
        adapter = new TeamAdapter(this, teamList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
}
