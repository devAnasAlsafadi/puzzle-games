package com.example.puzzlegam.interfaces;

public interface CheckAnswersListener {
    void trueAnswers(int trueCounter , int points );
    void falseAnswers(String hint , int falseCounter);
    void otherValues(int numberQuestion , int point ,int duration);
    void finishTimer(String hint);
}
