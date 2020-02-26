package com.example.tharapk.listeners;

import com.example.tharapk.models.Team;

import java.util.List;

public interface TeamFireBaseLoadDone {

    void onFirebaseLoadSuccess(List<Team> sponsorsList);
    void onFirebaseLoadFailed(String message);

    void onIconClick(String url);
}
