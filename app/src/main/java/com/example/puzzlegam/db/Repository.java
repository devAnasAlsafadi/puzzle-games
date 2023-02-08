package com.example.puzzlegam.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.puzzlegam.db.database_interface.DetailsQuestionDAO;
import com.example.puzzlegam.db.database_interface.ListenerDetailsQuestion;
import com.example.puzzlegam.db.database_interface.QuestionDAO;
import com.example.puzzlegam.db.database_interface.StageDAO;
import com.example.puzzlegam.db.database_interface.UserDAO;
import com.example.puzzlegam.db.models.DetailsQuestion;
import com.example.puzzlegam.db.models.Questions;
import com.example.puzzlegam.db.models.Stage;
import com.example.puzzlegam.db.models.User;

import java.util.List;

public class Repository {

    UserDAO userDAO;
    StageDAO stageDAO;
    QuestionDAO questionDAO;
    DetailsQuestionDAO detailsQuestionDAO;

    public Repository(Application application){
        MyDatabase myDatabase = MyDatabase.getDatabase(application);
        stageDAO = myDatabase.stageDAO();
        userDAO = myDatabase.userDAO();;
        questionDAO = myDatabase.questionDAO();
        detailsQuestionDAO = myDatabase.detailsQuestionDAO();

    }



    //Stage Method

    public void deleteStage(){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                stageDAO.deleteStage();
            }
        });
    }

    public  void insertStage(Stage stage ){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                stageDAO.insertStage(stage);
            }
        });

    }


    public void getPointByStage(int level , ListenerDetailsQuestion listenerDetailsQuestion){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int value = stageDAO.getPointByStage(level);
                listenerDetailsQuestion.onChangeValue(value);
            }
        });
    }
    public LiveData<List<Stage>> getAllStage(){
        return stageDAO.getAllStage();
    }



    //Question Method


    public void deleteQuestion(){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                questionDAO.deleteQuestion();
            }
        });
    }
    public  void insertQuestion(Questions questions ){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                 questionDAO.insertQuestion(questions);
            }
        });

    }


    public LiveData<List<Questions>> getAllQuestion(){
        return questionDAO.getAllQuestion();
    }

    public LiveData<List<Questions>> getAllQuestionByIdLevel(int levelNum){
        return questionDAO.getAllQuestionByIdLevel(levelNum);
    }




    //details question method


    public void deleteDetailsQuestion(){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                detailsQuestionDAO.deleteDetailsQuestion();
            }
        });
    }

    public void insertDetailsQuestion(DetailsQuestion detailsQuestion){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                detailsQuestionDAO.insertDetailsQuestion(detailsQuestion);
            }
        });
    }

    public  LiveData<List<DetailsQuestion>> getAllDetailsQuestion(){
       return detailsQuestionDAO.getAllDetailsQuestion();
    }

    public void   getAllTrueAnswers(boolean stats , ListenerDetailsQuestion listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int result = detailsQuestionDAO.getAllTrueAnswers(stats);
                listener.onChangeValue(result);
            }
        });

    }
    public void   getAllFalseAnswers(boolean stats , ListenerDetailsQuestion listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int result = detailsQuestionDAO.getAllFalseAnswers(stats);
                listener.onChangeValue(result);
            }
        });

    }

    public void getAllTrueAnswersByLevel(boolean stats , int level , ListenerDetailsQuestion listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int value =   detailsQuestionDAO.getAllTrueAnswersByLevel(stats , level);
                listener.onChangeValue(value);

            }
        });
    }

    public void getAllCountQuestionByLevel(int level , ListenerDetailsQuestion listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int value = detailsQuestionDAO.getAllCountQuestionByLevel(level);
                listener.onChangeValue(value);
            }
        });
    }
    public void getAllFalseAnswersByLevel(boolean stats , int level , ListenerDetailsQuestion listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int value =   detailsQuestionDAO.getAllFalseAnswersByLevel(stats , level);
                listener.onChangeValue(value);

            }
        });
    }

    public void getPointByLevel(int level , ListenerDetailsQuestion listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int value = detailsQuestionDAO.getPointByLevelId(level);
                listener.onChangeValue(value);
            }
        });
    }

    public void getAllPoint(ListenerDetailsQuestion listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int value = detailsQuestionDAO.getAllPoints();
                listener.onChangeValue(value);
            }
        });
    }



    //User Method
    public void insertUser(User user){

        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
              userDAO.insertUser(user);
            }
        });
    }

    public void updateUser(User user){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.updateUser(user);
            }
        });
    }

    public LiveData<List<User>> getUser(){
        return userDAO.getUser();
    }


    public void deleteUser(){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.deleteUser();

            }
        });
    }





}
