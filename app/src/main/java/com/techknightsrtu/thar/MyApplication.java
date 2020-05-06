package com.techknightsrtu.thar;

import androidx.multidex.MultiDexApplication;

import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
