package com.example.puzzlegam.controller;
import android.app.Application;

public class AppController extends Application {

    private static AppController instance;

    public static AppController getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
