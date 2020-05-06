package com.techknightsrtu.thar.listeners;

import com.techknightsrtu.thar.models.Sponsor;

import java.util.List;

public interface IFirebaseLoadDone {

    void onFirebaseLoadSuccess(List<Sponsor> sponsorsList);
    void onFirebaseLoadFailed(String message);
}
