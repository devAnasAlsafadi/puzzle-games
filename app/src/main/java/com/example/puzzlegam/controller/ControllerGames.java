package com.example.puzzlegam.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.puzzlegam.R;
import com.example.puzzlegam.databinding.CustomeCompleteBinding;
import com.example.puzzlegam.databinding.CustomeOptionsBinding;
import com.example.puzzlegam.databinding.CustomeTrueFalseBinding;
import com.example.puzzlegam.db.models.Questions;
import com.example.puzzlegam.interfaces.CheckAnswersListener;
import com.example.puzzlegam.interfaces.TrueFalseValue;
import com.example.puzzlegam.interfaces.TypeQuestions;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ControllerGames {
   private  CustomeOptionsBinding bindingOption;
   private  CustomeTrueFalseBinding bindingTrueFalse;
   private  CustomeCompleteBinding bindingComplete;
   public CheckAnswersListener listener;
   private int trueCounter = 0;
   private Questions questions;
   private int falseCounter=0;
    CountDownTimer countDownTimer;
   Context context;
   int seconds = 0;


    public ControllerGames(Context context) {
        this.context = context;
        if (context instanceof CheckAnswersListener) {
            listener = (CheckAnswersListener) context;
        } else {
            throw new ClassCastException("your activity not implement in CheckAnswersListener ");
        }
    }




    public  View getLayoutInflaterFragment(LayoutInflater inflater, ViewGroup container,Questions questions){
        this.questions = questions;
        listener.otherValues(questions.getId(),questions.getPoints(),questions.getDuration());
        if (questions.getPatternId() == TypeQuestions.options){
             bindingOption = CustomeOptionsBinding.inflate(inflater,container,false);
            setDataOption();
            controllerOption();
            return bindingOption.getRoot();
        }else if (questions.getPatternId() == TypeQuestions.complete){
            bindingComplete = CustomeCompleteBinding.inflate(inflater,container,false);
            setDataComplete();
            controllerComplete();
            return bindingComplete.getRoot();
        }else if (questions.getPatternId() == TypeQuestions.trueAndFalse){
            bindingTrueFalse = CustomeTrueFalseBinding.inflate(inflater,container,false);
            setDataTrueFalse();
            controllerTrueFalse();
            return bindingTrueFalse.getRoot();
        }
        return null;
    }

    private void controllerOption(){
        bindingOption.btnAnswers1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String answers1 = bindingOption.btnAnswers1.getText().toString();
                checkAnswers(answers1);
            }
        });

        bindingOption.btnAnswers2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String answers2 = bindingOption.btnAnswers2.getText().toString();
                checkAnswers(answers2);
            }
        });

        bindingOption.btnAnswers3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String answers3 = bindingOption.btnAnswers3.getText().toString();
                checkAnswers(answers3);
            }
        });

        bindingOption.btnAnswers4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String answers4 = bindingOption.btnAnswers4.getText().toString();
                checkAnswers(answers4);
            }
        });


    }

    private void controllerComplete(){
        bindingComplete.btnCheckComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ansComplete = bindingComplete.editTextTextPersonName.getText().toString();
                if (!ansComplete.isEmpty()){
                    checkAnswers(ansComplete);
                }else {
                    Snackbar.make(bindingComplete.getRoot(),"please Requried data",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void controllerTrueFalse(){

        bindingTrueFalse.btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String ansFalse = bindingTrueFalse.btnFalse.getText().toString();
                checkAnswers(ansFalse);
            }
        });

        bindingTrueFalse.btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String ansTrue = bindingTrueFalse.btnTrue.getText().toString();
                checkAnswers(ansTrue);
            }
        });
    }



    public void timerCancel(){
        countDownTimer.cancel();
    }





    public void checkAnswers(String answers){
        if (answers.equals(questions.getTrueAnswer())){
            countDownTimer.cancel();
            trueCounter++;
            listener.trueAnswers(trueCounter,questions.getPoints());
        }else {
            countDownTimer.cancel();
            falseCounter++;
            listener.falseAnswers(questions.getHint(),falseCounter);
        }
    }

    private void setDataOption(){
        bindingOption.tvQuestion.setText(questions.getTitle());
        bindingOption.btnAnswers1.setText(questions.getAnswer1());
        bindingOption.btnAnswers2.setText(questions.getAnswer2());
        bindingOption.btnAnswers3.setText(questions.getAnswer3());
        bindingOption.btnAnswers4.setText(questions.getAnswer4());
        int x = questions.getDuration();
        x *=100;
         countDownTimer = new CountDownTimer(x,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long min = (millisUntilFinished / 60000);
                long sec = (millisUntilFinished / 1000) %60;
                String x = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
                bindingOption.tvTimer.setText(x);
            }

            @Override
            public void onFinish() {
                listener.finishTimer(questions.getHint());
            }
        }.start();


    }
    private void setDataTrueFalse(){
        bindingTrueFalse.tvQuestion.setText(questions.getTitle());
        bindingTrueFalse.btnFalse.setText(TrueFalseValue.False);
        bindingTrueFalse.btnTrue.setText(TrueFalseValue.True);
        int x = questions.getDuration();
        x *=100;
         countDownTimer = new CountDownTimer(x,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long min = (millisUntilFinished / 60000);
                long sec = (millisUntilFinished / 1000) %60;
                String x = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
                bindingTrueFalse.tvTimer.setText(x);
            }

            @Override
            public void onFinish() {
                listener.finishTimer(questions.getHint());
            }
        }.start();
    }
    private void setDataComplete(){
        bindingComplete.tvQuestion.setText(questions.getTitle());
        int x = questions.getDuration();
        x *=100;
         countDownTimer = new CountDownTimer(x,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long min = (millisUntilFinished / 60000);
                long sec = (millisUntilFinished / 1000) %60;
                String x = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
                bindingComplete.tvTimer.setText(x);
            }

            @Override
            public void onFinish() {
                listener.finishTimer(questions.getHint());
            }
        }.start();

    }





}
