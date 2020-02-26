package com.example.tharapk.ui.team;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.tharapk.R;
import com.example.tharapk.adapters.TeamAdapter;
import com.example.tharapk.listeners.TeamFireBaseLoadDone;
import com.example.tharapk.models.Team;
import com.example.tharapk.transformer.DepthPageTransformer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity implements TeamFireBaseLoadDone {

    private static final String TAG = "TeamActivity";

    ViewPager viewPager;
    TeamAdapter adapter;

    DatabaseReference teams;

    TeamFireBaseLoadDone iFirebaseLoadDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

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

            List<Team> teamList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sponsorSnapShot:dataSnapshot.getChildren())
                    teamList.add(sponsorSnapShot.getValue(Team.class));
                Log.d(TAG, "onDataChange: " + teamList.get(2).getLinkedin());

                iFirebaseLoadDone.onFirebaseLoadSuccess(teamList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<Team> teamList) {
        adapter = new TeamAdapter(this, teamList,iFirebaseLoadDone);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIconClick(String url) {
        Log.d(TAG, "onIconClick: URL" + url);
        if(!url.equals("")){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }


    }
}
