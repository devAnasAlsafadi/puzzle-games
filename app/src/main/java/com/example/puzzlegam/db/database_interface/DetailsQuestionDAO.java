package com.example.puzzlegam.db.database_interface;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.puzzlegam.db.models.DetailsQuestion;
import com.example.puzzlegam.db.models.Questions;
import java.util.List;

@Dao
public interface DetailsQuestionDAO {


    @Insert
    long insertDetailsQuestion(DetailsQuestion detailsQuestion);


    @Query("select * from DetailsQuestion_base")
    LiveData<List<DetailsQuestion>> getAllDetailsQuestion();


    //عدد الاسئلة الصحيحة لكل المراحل
    @Query("select count(id_base) from DetailsQuestion_base where  statsQuestion_base=:stats")
    int getAllTrueAnswers(boolean stats);

    //عدد الاسئلة الخاطئة لكل المراحل
    @Query("select count(id_base) from DetailsQuestion_base where statsQuestion_base =:stats")
    int getAllFalseAnswers(boolean stats);


    //عدد الاسئلة الخاطئة لمرحلة معينة
    @Query("select count(id_base) from DetailsQuestion_base where statsQuestion_base =:stats and levelNum=:level")
    int getAllFalseAnswersByLevel(boolean stats , int level);

    @Query("SELECT count(id_base) from DetailsQuestion_base where levelNum=:level")
    int getAllCountQuestionByLevel(int level);

    //عدد الاسئلة الصحيحة لمرحلة معينة
    @Query("select count(id_base) from DetailsQuestion_base where statsQuestion_base =:stats and levelNum=:level")
    int getAllTrueAnswersByLevel(boolean stats , int level);


    //مجموع النقاط الخاصة بمرحلة معينة
    @Query("select sum(points_base) from DetailsQuestion_base where levelNum=:id ")
    int getPointByLevelId(int id);


    // جميع النقاط التي تحصل عليها المستخدم
    @Query("select sum(points_base) from DetailsQuestion_base ")
    int getAllPoints();


    //نقاط الاسئلة الصحيحة
//    @Query("select sum(points_base) from DetailsQuestion_base where statsQuestion_base=:res ")
//    int getPointForTrueAnswers(boolean res);


    @Query("DELETE FROM DetailsQuestion_base")
     void deleteDetailsQuestion();

}
