package com.example.mmmalthaf.momsmenu;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //enable offline persistance
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
