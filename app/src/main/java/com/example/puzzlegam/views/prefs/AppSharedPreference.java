package com.example.puzzlegam.views.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.puzzlegam.controller.AppController;

public class AppSharedPreference {

    public static final String NUMBER_QUESTION = "number_question";
    private static AppSharedPreference instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static final String NUMBER_LEVEL_PAUSE = "num_level";
    public static final String POSITION_PAGER = "position_pager";
    public static final String TRUE_KEY = "key_true";
    public static final String FALSE_KEY = "key_false";
    public static final String MY_POINT_KEY = "mp_point_key";

    private AppSharedPreference(){
        preferences = AppController.getInstance().getSharedPreferences("prefs_puzzle", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public static AppSharedPreference getInstance(){
        if (instance == null){
            instance = new AppSharedPreference();
        }
        return instance;
    }

    public void saveInterfaceStats(int position , int level , int trueAns , int falseAns , int point , int numberQuestion){

        editor.putInt(POSITION_PAGER ,position);
        editor.putInt(NUMBER_LEVEL_PAUSE ,level);
        editor.putInt(TRUE_KEY ,trueAns);
        editor.putInt(FALSE_KEY ,falseAns);
        editor.putInt(MY_POINT_KEY ,point);
        editor.putInt(NUMBER_QUESTION ,numberQuestion);
        editor.apply();
    }


    public SharedPreferences.Editor getEditor(){
        return editor;
    }

    public SharedPreferences getSharedPreferences(){
        return preferences;
    }

}
