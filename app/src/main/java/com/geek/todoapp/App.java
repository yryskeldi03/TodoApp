package com.geek.todoapp;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs(this);
    }
}
