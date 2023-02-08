package com.example.puzzlegam.db.database_interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.puzzlegam.db.models.Questions;

import java.util.List;

@Dao
public interface QuestionDAO {

    @Insert
    long insertQuestion(Questions questions);

    @Query("select * from questions_base")
    LiveData<List<Questions>> getAllQuestion();


    @Query("select * from questions_base where idStageFor_base =:levelNumber order by NumberQuestion_base")
    LiveData<List<Questions>> getAllQuestionByIdLevel(int levelNumber);

    @Query("DELETE FROM questions_base")
    void deleteQuestion();
}
