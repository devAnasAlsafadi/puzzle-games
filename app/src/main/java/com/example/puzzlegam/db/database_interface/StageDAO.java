package com.example.puzzlegam.db.database_interface;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.puzzlegam.db.models.Stage;

import java.util.List;

@Dao
public interface StageDAO {

    @Insert
    long insertStage(Stage stage);

    @Query("select * from stage_base")
    LiveData<List<Stage>> getAllStage();

    @Query("DELETE FROM stage_base")
    void deleteStage();


    @Query("select pointNumber_base from stage_base where levelNumber_base=:level")
    int getPointByStage(int level);
}
