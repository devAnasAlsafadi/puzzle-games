package com.example.puzzlegam.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.puzzlegam.db.database_interface.ListenerDetailsQuestion;
import com.example.puzzlegam.db.models.DetailsQuestion;
import com.example.puzzlegam.db.models.Questions;
import com.example.puzzlegam.db.models.Stage;
import com.example.puzzlegam.db.models.User;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    static Repository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    //Stage Method


    public void deleteStage(){
        repository.deleteStage();
    }
    public void insertStage(Stage stage) {
       repository.insertStage(stage);

    }

    public LiveData<List<Stage>> getAllStage() {
        return repository.getAllStage();
    }

    public void getPointByStage(int level , ListenerDetailsQuestion listenerDetailsQuestion){
        repository.getPointByStage(level,listenerDetailsQuestion);
    }


    //Question Method
    public void deleteQuestion(){
        repository.deleteQuestion();
    }

    public void insertQuestion(Questions questions) {
        repository.insertQuestion(questions);

    }

    public LiveData<List<Questions>> getAllQuestion() {
        return repository.getAllQuestion();
    }

    public LiveData<List<Questions>> getAllQuestionByIdLevel(int levelNum){
        return repository.getAllQuestionByIdLevel(levelNum);
    }

    //details question method

    public void deleteDetailsQuestion(){
        repository.deleteDetailsQuestion();
    }
    public void insertDetailsQuestion(DetailsQuestion detailsQuestion){
     repository.insertDetailsQuestion(detailsQuestion);
    }

    public LiveData<List<DetailsQuestion>> getAllDetailsQuestion(){
       return repository.getAllDetailsQuestion();
    }

    public void   getAllTrueAnswers(boolean stats , ListenerDetailsQuestion listener){
         repository.getAllTrueAnswers(stats,listener);
    }
    public void   getAllFalseAnswers(boolean stats , ListenerDetailsQuestion listener){
        repository.getAllFalseAnswers(stats,listener);
    }

    public static void getAllTrueAnswersByLevel(boolean stats , int level , ListenerDetailsQuestion listener){
      repository.getAllTrueAnswersByLevel(stats,level,listener);
    }
    public void getAllFalseAnswersByLevel(boolean stats , int level , ListenerDetailsQuestion listener){
        repository.getAllFalseAnswersByLevel(stats,level,listener);

    }

    public static void getPointByLevel(int level , ListenerDetailsQuestion listener){
        repository.getPointByLevel(level,listener);
    }

    public void getAllPoint(ListenerDetailsQuestion listener){
       repository.getAllPoint(listener);
    }

    public static void getAllCountQuestionByLevel(int level , ListenerDetailsQuestion listenerDetailsQuestion){
        repository.getAllCountQuestionByLevel(level,listenerDetailsQuestion);
    }


    //User Method

    public void deleteUser(){
        repository.deleteUser();
    }
    public void insertUser(User user) {

        repository.insertUser(user);
    }

    public void updateUser(User user){
         repository.updateUser(user);
    }

    public LiveData<List<User>> getUser() {
        return repository.getUser();
    }


}
