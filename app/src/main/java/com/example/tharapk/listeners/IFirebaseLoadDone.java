package com.example.tharapk.listeners;

import com.example.tharapk.models.Sponsor;

import java.util.List;

public interface IFirebaseLoadDone {

    void onFirebaseLoadSuccess(List<Sponsor> sponsorsList);
    void onFirebaseLoadFailed(String message);
}
