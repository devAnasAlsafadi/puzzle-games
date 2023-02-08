package com.example.puzzlegam.views;


import static com.example.puzzlegam.views.SettingsActivity.KEY_MUSIC;
import static com.example.puzzlegam.views.prefs.AppSharedPreference.FALSE_KEY;
import static com.example.puzzlegam.views.prefs.AppSharedPreference.MY_POINT_KEY;
import static com.example.puzzlegam.views.prefs.AppSharedPreference.NUMBER_LEVEL_PAUSE;
import static com.example.puzzlegam.views.prefs.AppSharedPreference.NUMBER_QUESTION;
import static com.example.puzzlegam.views.prefs.AppSharedPreference.POSITION_PAGER;
import static com.example.puzzlegam.views.prefs.AppSharedPreference.TRUE_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.puzzlegam.R;
import com.example.puzzlegam.adapter.adapterPager.PagerAdapter;
import com.example.puzzlegam.databinding.ActivityDetailsStageBinding;
import com.example.puzzlegam.db.MyViewModel;
import com.example.puzzlegam.db.database_interface.ListenerDetailsQuestion;
import com.example.puzzlegam.db.models.DetailsQuestion;
import com.example.puzzlegam.db.models.Questions;
import com.example.puzzlegam.dialogFragment.LoseFragment;
import com.example.puzzlegam.dialogFragment.WinFragment;
import com.example.puzzlegam.fragments.PagerFragment;
import com.example.puzzlegam.interfaces.CheckAnswersListener;
import com.example.puzzlegam.views.prefs.AppSharedPreference;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class DetailsStage extends AppCompatActivity implements CheckAnswersListener  {
    public static final String KEY_POINT = "points";
    public static final String KEY_ALL_POINT = "all_point";
    public static final String KEY_LEVEL = "level";
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";
    private ActivityDetailsStageBinding binding;
    private MyViewModel model;
    int numberQuestion = 1;
    private List<Questions> questionsList = new ArrayList<>();
    int countTrue,countFalse;
    private int myPoints;
    private  int positionPager;
    private MediaPlayer mpWin , mpLose ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "onCreate: ");
        binding = ActivityDetailsStageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mpLose = MediaPlayer.create(this, R.raw.lose);
        mpWin = MediaPlayer.create(this, R.raw.win);
        model = new ViewModelProvider(this).get(MyViewModel.class);
        List<Fragment> list = new ArrayList<>();
        binding.tvAllPoint.setText(String.valueOf(getAllPoint()));
        binding.tvLevelNumber.setText(String.valueOf(getId()));


        //التحكم بعمليات ارسال الاسئلة الي القيو بيجر عن طريق جلبها من قاعدة البيانات
        model.getAllQuestionByIdLevel(getId()).observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                setQuestionsList(questions);
                for (int i = 0; i < questions.size(); i++) {
                    list.add(PagerFragment.newInstance(questions.get(i)));
                }
                PagerAdapter adapter = new PagerAdapter(DetailsStage.this, list);
                binding.viewPager.setAdapter(adapter);
                binding.viewPager.setUserInputEnabled(false);
                binding.tvIndexQuestion.setText(1+"/"+questions.size());

        int position = AppSharedPreference.getInstance().getSharedPreferences().getInt(POSITION_PAGER,0);
        int level = AppSharedPreference.getInstance().getSharedPreferences().getInt(NUMBER_LEVEL_PAUSE,0);
        int point = AppSharedPreference.getInstance().getSharedPreferences().getInt(MY_POINT_KEY,0);
        int countTrue = AppSharedPreference.getInstance().getSharedPreferences().getInt(TRUE_KEY,0);
        int countFalse = AppSharedPreference.getInstance().getSharedPreferences().getInt(FALSE_KEY,0);
        int num = AppSharedPreference.getInstance().getSharedPreferences().getInt(NUMBER_QUESTION,0);

        if (position !=questions.size()-1  && level == getId()){
            binding.tvIndexQuestion.setText((position+1)+"/"+questions.size());
            binding.viewPager.setCurrentItem(position);
            binding.tvMyPoint.setText(String.valueOf(point));
            binding.tvTrueValue.setText(String.valueOf(countTrue));
            binding.tvFalseValue.setText(String.valueOf(countFalse));
        }
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-7664687005864283/1860201990",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.toString());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });

        binding.ivLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedAd != null) {
                    mRewardedAd.show(DetailsStage.this, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                            Log.d(TAG, "onUserEarnedReward: "+rewardAmount);

                        }
                    });
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }
        });

    }



    //دالة تقوم بجلب رقم المرحلة عند الضغط عليها عن طريق الانتنت
    private int getId(){
        return getIntent().getIntExtra("numberId",0);
    }
    //دالة تقوم بجلب النقاط اللازمة لاجتياز هذه المرحلة عن طريق الانتنت
    private int getAllPoint(){
        return getIntent().getIntExtra("allPoint",0);
    }




    //يتم استدائها في حال كانت الاجابة خاطئة
    @Override
    public void trueAnswers(int counterTrue,int point) {
        numberQuestion++;
        binding.tvIndexQuestion.setText(numberQuestion+"/"+questionsList.size());
        DetailsQuestion detailsQuestion = new DetailsQuestion(getId(),true,point);
        model.insertDetailsQuestion(detailsQuestion);
        controllerSound(mpWin);
        countTrue+=counterTrue;
        WinFragment fragment = WinFragment.newInstance();
        fragment.show(getSupportFragmentManager(),null);
        myPoints+=point;
        binding.tvMyPoint.setText(String.valueOf(myPoints));
        binding.tvTrueValue.setText(String.valueOf(countTrue));
        positionPager = binding.viewPager.getCurrentItem();
        positionPager++;
        controllerIndexPager(positionPager);

    }


    //يتم استدعائها اذا كانت الاجابة صحيحة
    @Override
    public void falseAnswers(String hint , int counterFalse ) {
        numberQuestion++;
        binding.tvIndexQuestion.setText(numberQuestion+"/"+questionsList.size());
        DetailsQuestion detailsQuestion = new DetailsQuestion(getId(),false);
        model.insertDetailsQuestion(detailsQuestion);
        controllerSound(mpLose);
        LoseFragment fragment = LoseFragment.newInstance(hint);
        fragment.show(getSupportFragmentManager(),null);
        countFalse+=counterFalse;
        binding.tvFalseValue.setText(String.valueOf(countFalse));
        positionPager = binding.viewPager.getCurrentItem();
        positionPager++;
        controllerIndexPager(positionPager);
    }



    //يتم استدعائها تلقائيا عند تغيير index الفيو بيجر
    @Override
    public void otherValues(int numberQuestion, int point,int duration) {
        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionPager = binding.viewPager.getCurrentItem();
                positionPager++;
                controllerSkip(positionPager);

            }
        });
    }

    @Override
    public void finishTimer(String hint) {
        DetailsQuestion detailsQuestion = new DetailsQuestion(getId(),false);
        model.insertDetailsQuestion(detailsQuestion);
        LoseFragment fragment = LoseFragment.newInstance(hint);
        fragment.show(getSupportFragmentManager(),null);
        positionPager = binding.viewPager.getCurrentItem();
        positionPager++;
        controllerIndexPager(positionPager);
    }



    //controller index pager
    private void controllerIndexPager(int position){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.viewPager.setCurrentItem(position);
                int length = questionsList.size();
                if (position ==length){
                    controllerIntentToCheckStageComplete();
                }
            }
        },1000);
    }


    //controller button skip
    private void controllerSkip(int position){
        int length = questionsList.size();
        int pointSkip = Integer.parseInt(binding.tvMyPoint.getText().toString());
        if (pointSkip<3 && position ==length){
            Toast.makeText(DetailsStage.this,R.string.dont_point, Toast.LENGTH_SHORT).show();
        }else {
            if (position == length){
                controllerIntentToCheckStageComplete();
            }else {

                if (pointSkip<3){
                    Toast.makeText(DetailsStage.this, R.string.dont_point, Toast.LENGTH_SHORT).show();
                }else {
                    model.insertDetailsQuestion(new DetailsQuestion(getId(),-3));
                    myPoints = myPoints-3;
                    binding.tvMyPoint.setText(String.valueOf(myPoints));
                    binding.viewPager.setCurrentItem(position);
                }
            }


        }
    }

    private void controllerIntentToCheckStageComplete(){
        int point = Integer.parseInt(binding.tvMyPoint.getText().toString());
        Intent intent = new Intent(getBaseContext(),CheckStatsStageActivity.class);
        intent.putExtra(KEY_POINT,point);
        intent.putExtra(KEY_ALL_POINT,getAllPoint());
        intent.putExtra(KEY_LEVEL,getId());
        startActivity(intent);
        finish();
    }


    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }




    private void controllerSound(MediaPlayer mediaPlayer){
        boolean stats = AppSharedPreference.getInstance().getSharedPreferences().getBoolean(KEY_MUSIC,false);
        if (stats){
            mediaPlayer.start();
        }else {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppSharedPreference.getInstance().saveInterfaceStats(binding.viewPager.getCurrentItem(),getId(),countTrue,countFalse, Integer.parseInt(binding.tvMyPoint.getText().toString()),numberQuestion);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
