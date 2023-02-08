package com.example.puzzlegam.views;

import static com.example.puzzlegam.views.SettingsActivity.KEY_SOUND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.puzzlegam.R;
import com.example.puzzlegam.controller.SoundController;
import com.example.puzzlegam.databinding.ActivityMainBinding;
import com.example.puzzlegam.views.prefs.AppSharedPreference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    YoYo.YoYoString yoYoString;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mp = MediaPlayer.create(this,R.raw.click_sound);


        controllerAnimation(binding.btnStart);
        controllerAnimation(binding.btnSettings);
        controllerAnimation(binding.btnExite);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                yoYoString = YoYo.with(Techniques.Tada)
                        .duration(1000)
                        .repeat(100)
                        .playOn(binding.btnStart);
            }
        },1000);


    }


    @Override
    protected void onStart() {
        super.onStart();
        initializeView();
        controllerSound();
    }



    private void controllerSound(){
        boolean stats = AppSharedPreference.getInstance().getSharedPreferences().getBoolean(KEY_SOUND,false);
        if (stats){
            SoundController.getInstance().startService();
        }else {
            SoundController.getInstance().stopService();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        SoundController.getInstance().stopService();
    }

    private void controllerAnimation(View v){
         YoYo.with(Techniques.ZoomInUp)
                    .duration(1000)
                    .repeat(0)
                    .playOn(v);
    }
    public void initializeView(){
        binding.btnStart.setOnClickListener(this);
        binding.btnSettings.setOnClickListener(this);
        binding.btnExite.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                mp.start();
                Intent intent  = new Intent(getBaseContext(),StagesActivity.class);
                startActivity(intent);
                yoYoString.stop();
                break;
            case R.id.btn_settings:
                mp.start();
                Intent intent1 = new Intent(getBaseContext(),SettingsActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_exite:
                mp.start();
                finishAffinity();
                break;
        }
    }
}