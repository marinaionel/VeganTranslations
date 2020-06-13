package com.example.vegantranslations;

import android.app.Application;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;

import com.example.vegantranslations.data.FirestoreRepository;
import com.example.vegantranslations.data.Repository;

public class MyApp extends Application {
    private Repository firestoreRepository;

    @Override
    public void onCreate() {
        super.onCreate();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        firestoreRepository = new FirestoreRepository(this);
    }
}
