package com.example.puzzlegam.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.puzzlegam.R;
import com.example.puzzlegam.databinding.ActivitySettingsBinding;
import com.example.puzzlegam.db.MyViewModel;
import com.example.puzzlegam.dialogFragment.DeleteDataFragment;
import com.example.puzzlegam.interfaces.YesNoListener;
import com.example.puzzlegam.views.prefs.AppSharedPreference;
import com.example.puzzlegam.service.JobServiceNotification;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener , YesNoListener {
    public static final String KEY_MUSIC = "music_sw";
    public static final String KEY_SOUND = "sound_sw";
    private static final String KEY_NOTIFICATION = "notification_sw";
    ActivitySettingsBinding binding;
    MyViewModel model;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = new ViewModelProvider(this).get(MyViewModel.class);
        controllerSwitchMusic();
        controllerSwitchSound();
        controllerSwitchNotification();
        controllerSendNotification();


    }

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_setting:
                Intent intent  = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_replay_play:
                DeleteDataFragment deleteDataFragment = DeleteDataFragment.newInstance("Replay Game","are you sure to replay play");
                deleteDataFragment.show(getSupportFragmentManager(),null);
                break;
        }
    }

    private void setOnClickListener(){
        binding.ivBackSetting.setOnClickListener(this);
        binding.btnReplayPlay.setOnClickListener(this);
    }

    private void controllerSwitchMusic(){
        boolean result = AppSharedPreference.getInstance().getSharedPreferences().getBoolean(KEY_MUSIC,false);
        binding.switchMusic.setChecked(result);
        binding.switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppSharedPreference.getInstance().getEditor().putBoolean(KEY_MUSIC,true).apply();
                }else {
                    AppSharedPreference.getInstance().getEditor().putBoolean(KEY_MUSIC,false).apply();

                }

            }
        });
    }
    private void controllerSwitchSound(){
        boolean result = AppSharedPreference.getInstance().getSharedPreferences().getBoolean(KEY_SOUND,false);
        binding.switchSound.setChecked(result);
        binding.switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppSharedPreference.getInstance().getEditor().putBoolean(KEY_SOUND,true).apply();
                }else {
                    AppSharedPreference.getInstance().getEditor().putBoolean(KEY_SOUND,false).apply();

                }

            }
        });
    }
    private void controllerSwitchNotification(){
        boolean result = AppSharedPreference.getInstance().getSharedPreferences().getBoolean(KEY_NOTIFICATION,false);
        binding.switchNotification.setChecked(result);
        binding.switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppSharedPreference.getInstance().getEditor().putBoolean(KEY_NOTIFICATION,true).apply();
                    Toast.makeText(SettingsActivity.this, "start noti", Toast.LENGTH_SHORT).show();
                }else {
                    AppSharedPreference.getInstance().getEditor().putBoolean(KEY_NOTIFICATION,false).apply();

                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void controllerSendNotification(){
        boolean result = AppSharedPreference.getInstance().getSharedPreferences().getBoolean(KEY_NOTIFICATION,false);
        startJobService();
        if (result){
        }else {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startJobService(){
        ComponentName cn = new ComponentName(getBaseContext(), JobServiceNotification.class);
        JobInfo info ;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
            info = new JobInfo.Builder(10,cn).setPeriodic(1000,0).
                    setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).build();

        }else {
            info = new JobInfo.Builder(10,cn)
                    .setMinimumLatency(24*60*60*1000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
        }

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(info);
    }



    @Override
    public void chickYesListener() {
        model.deleteQuestion();
        model.deleteDetailsQuestion();
        model.deleteStage();
        AppSharedPreference.getInstance().getEditor().clear().apply();
        Intent intentDelete = new Intent(getApplicationContext(),SplashActivity.class);
        startActivity(intentDelete);
        finish();
    }

    @Override
    public void chickNoListener() {

    }

}