package com.techknightsrtu.thar.listeners;

import com.techknightsrtu.thar.models.Team;

import java.util.List;

public interface TeamFireBaseLoadDone {

    void onFirebaseLoadSuccess(List<Team> sponsorsList);
    void onFirebaseLoadFailed(String message);

    void onIconClick(String url);
}
