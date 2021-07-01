package com.geek.todoapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.content.ContextCompat;

public class Prefs {

    private SharedPreferences preferences;
    private static Prefs instance;

    public Prefs(Context context){
        instance = this;
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public static Prefs getInstance() {
        return instance;
    }

    public void saveBoardState(){
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isBoardShown(){
        return preferences.getBoolean("isShown", false);
    }

    public void putImage(String image){
        preferences.edit().putString("imageSet",image).apply();
    }

    public String getImage(){
        return preferences.getString("imageSet",null);
    }

    public void deleteImg(){
        preferences.edit().remove("imageSet").apply();
    }
}
