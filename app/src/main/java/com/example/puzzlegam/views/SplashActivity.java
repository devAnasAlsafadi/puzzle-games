package com.example.puzzlegam.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.puzzlegam.controller.AppController;
import com.example.puzzlegam.databinding.ActivitySplashBinding;
import com.example.puzzlegam.db.MyViewModel;
import com.example.puzzlegam.db.models.Questions;
import com.example.puzzlegam.db.models.Stage;
import com.example.puzzlegam.views.prefs.AppSharedPreference;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    MyViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = new ViewModelProvider(this).get(MyViewModel.class);
        insertJustOne();
        YoYo.with(Techniques.RotateIn).repeat(0).duration(2000).playOn(binding.imageView2);
        controllerSplash();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "onStart: "+getStageJson().size());
    }

    public String loadJsonFromAssets(){
        String json = null;
        try {
            try (InputStream inputStream = AppController.getInstance().getAssets().open("puzzleGameData.json")) {
                int size  = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
    public List<Stage> getStageJson() {
        List<Stage> stageList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(loadJsonFromAssets());
            Gson gson = new Gson();
            for (int i = 0; i <jsonArray.length() ; i++) {
                Stage stage1 = gson.fromJson(jsonArray.getJSONObject(i).toString(),Stage.class);
                stageList.add(stage1);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stageList;

    }
    public List<Questions> getQuestionJson(){
        List<Questions> listQuestions = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(loadJsonFromAssets());
            for (int s = 0; s <jsonArray.length() ; s++) {
                int idStage = jsonArray.getJSONObject(s).getInt("level_no");
                JSONArray jsonArray1 = jsonArray.getJSONObject(s).getJSONArray("questions");

                for (int i = 0; i < jsonArray1.length(); i++) {

                    int idQuestion = jsonArray1.getJSONObject(i).getInt("id");
                    String title = jsonArray1.getJSONObject(i).getString("title");
                    String answers1 = jsonArray1.getJSONObject(i).getString("answer_1");
                    String answers2 = jsonArray1.getJSONObject(i).getString("answer_2");
                    String answers3 = jsonArray1.getJSONObject(i).getString("answer_3");
                    String answers4 = jsonArray1.getJSONObject(i).getString("answer_4");
                    String trueAnswers = jsonArray1.getJSONObject(i).getString("true_answer");
                    int  pointQuestions = jsonArray1.getJSONObject(i).getInt("points");
                    int  durations = jsonArray1.getJSONObject(i).getInt("duration");
                    String  hint = jsonArray1.getJSONObject(i).getString("hint");
                    Questions questions = new Questions(idQuestion,idStage,title,answers1,answers2,answers3,answers4,trueAnswers,pointQuestions,durations,hint);
                    listQuestions.add(questions);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listQuestions;


    }
    private void controllerSplash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        },3000);
    }






    private void insertJustOne(){
        boolean result = AppSharedPreference.getInstance().getSharedPreferences().getBoolean("storage",false);
        if (result){

        } else {
            insertInDatabase();
            AppSharedPreference.getInstance().getEditor().putBoolean("storage",true).apply();

        }

    }






    
    private void insertInDatabase(){
        for (int i = 0; i < getStageJson().size(); i++) {
            model.insertStage(getStageJson().get(i));
        }
        for (int i = 0; i < getQuestionJson().size(); i++) {
            model.insertQuestion(getQuestionJson().get(i));
        }


    }



}