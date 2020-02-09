package com.example.tharapk.listeners;

import com.example.tharapk.models.TeamModel;

import java.util.List;

public interface TeamFireBaseLoadDone {

    void onFirebaseLoadSuccess(List<TeamModel> sponsorsList);
    void onFirebaseLoadFailed(String message);
}
