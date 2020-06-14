package com.example.vegantranslations;

import android.app.Application;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;

import com.example.vegantranslations.model.repository.FirebaseRepository;
import com.example.vegantranslations.model.repository.Repository;

public class MyApp extends Application {
    private Repository firestoreRepository;

    @Override
    public void onCreate() {
        super.onCreate();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        firestoreRepository = new FirebaseRepository(this);
    }
}
