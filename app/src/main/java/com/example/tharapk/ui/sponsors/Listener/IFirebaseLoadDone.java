package com.example.tharapk.ui.sponsors.Listener;

import com.example.tharapk.ui.sponsors.Model.Sponsor;

import java.util.List;

public interface IFirebaseLoadDone {

    void onFirebaseLoadSuccess(List<Sponsor> sponsorsList);
    void onFirebaseLoadFailed(String message);
}
