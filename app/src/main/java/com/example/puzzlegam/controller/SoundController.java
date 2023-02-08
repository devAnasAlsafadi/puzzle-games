package com.example.puzzlegam.controller;

import android.content.Intent;
import android.media.MediaPlayer;

import com.example.puzzlegam.R;
import com.example.puzzlegam.views.prefs.AppSharedPreference;
import com.example.puzzlegam.service.SoundService;

public class SoundController {

    private static SoundController instance;
    private MediaPlayer mp;


    private SoundController(){
        mp = MediaPlayer.create(AppController.getInstance(), R.raw.always_run);

    }

    public static  synchronized SoundController getInstance() {
        if (instance == null){
            instance = new SoundController();
        }
        return instance;
    }

    public void startService(){
        boolean result = AppSharedPreference.getInstance().getSharedPreferences().getBoolean("sound",false);
        if (result){

        }else {
            Intent intent = new Intent(AppController.getInstance(),SoundService.class);
            AppController.getInstance().startService(intent);
        }

    }
    public void stopService(){
        Intent intent = new Intent(AppController.getInstance(),SoundService.class);
        AppController.getInstance().stopService(intent);
        AppSharedPreference.getInstance().getEditor().putBoolean("sound",false).apply();
    }



}
