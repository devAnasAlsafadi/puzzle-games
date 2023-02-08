package com.example.puzzlegam.views;

import static com.example.puzzlegam.views.DetailsStage.KEY_ALL_POINT;
import static com.example.puzzlegam.views.DetailsStage.KEY_LEVEL;
import static com.example.puzzlegam.views.DetailsStage.KEY_POINT;
import static com.example.puzzlegam.views.SettingsActivity.KEY_MUSIC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.puzzlegam.R;
import com.example.puzzlegam.databinding.ActivityCheckStatsStageBinding;

import com.example.puzzlegam.db.MyViewModel;
import com.example.puzzlegam.db.database_interface.ListenerDetailsQuestion;
import com.example.puzzlegam.db.models.DetailsQuestion;
import com.example.puzzlegam.views.prefs.AppSharedPreference;

import javax.xml.validation.Validator;


public class CheckStatsStageActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCheckStatsStageBinding binding;
    int myPoint , level , allPoint;
    MyViewModel model;
    MediaPlayer win , lose;
    int allValue= 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckStatsStageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        win = MediaPlayer.create(this,R.raw.finish_stage);
        lose = MediaPlayer.create(this,R.raw.win_stage);
        model = new ViewModelProvider(this).get(MyViewModel.class);


        Intent intent = getIntent();
        myPoint = intent.getIntExtra(KEY_POINT,0);
        allPoint = intent.getIntExtra(KEY_ALL_POINT,0);
        level = intent.getIntExtra(KEY_LEVEL,0);
        String pointStr =getString(R.string.my_point);
        String allPointStr = getString(R.string.point_stage);



        boolean stats =    AppSharedPreference.getInstance().getSharedPreferences().getBoolean("stats",false);

        model.getPointByStage((level+ 1), new ListenerDetailsQuestion() {
            @Override
            public void onChangeValue(int value) {
                allValue = value;
                Log.d("TAG", "onChangeValue: "+value);
                if (myPoint >= value){
                    controllerSound(win);
                    binding.ivCompleteFailed.setImageResource(R.drawable.complete);
                    binding.ivSadStar.setImageResource(R.drawable.stars);
                    binding.btnReplayNewStage.setText(R.string.next_stage);
                    binding.tvDescription.setText(R.string.stage_complete);
                }else {
                    controllerSound(lose);
                    model.insertDetailsQuestion(new DetailsQuestion(level,-myPoint));
                    binding.ivCompleteFailed.setImageResource(R.drawable.failed);
                    binding.ivSadStar.setImageResource(R.drawable.sad_smile);
                    binding.btnReplayNewStage.setText(R.string.replay);
                    binding.tvDescription.setText(R.string.not_stage_complete);

                }


                binding.tvMypoint.setText(pointStr+": "+myPoint);
                binding.tvAllPoint.setText(allPointStr+": "+value);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListener();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_replay_newStage:
                Intent intent = new Intent(getApplicationContext(),StagesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_main:
                Intent intentMain = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intentMain);
                finish();
                break;
            case R.id.btn_appraise:
                Toast.makeText(this, "لا بتوفر على المتجر", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    private void setOnClickListener(){
        binding.btnReplayNewStage.setOnClickListener(this);
        binding.btnMain.setOnClickListener(this);
        binding.btnAppraise.setOnClickListener(this);
    }


    private void controllerSound(MediaPlayer mediaPlayer){
        boolean stats = AppSharedPreference.getInstance().getSharedPreferences().getBoolean(KEY_MUSIC,false);
        if (stats){
            mediaPlayer.start();
        }else {
            mediaPlayer.stop();
        }
    }
}