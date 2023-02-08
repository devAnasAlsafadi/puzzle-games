package com.example.puzzlegam.db.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "DetailsQuestion_base")
public class DetailsQuestion {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_base")
    private int id;

    @ColumnInfo(name = "levelNum")
    private int levelNum;

    @ColumnInfo(name = "statsQuestion_base")
    private boolean statsQuestion;

    @ColumnInfo(name = "points_base")
    private int points;


    @Ignore
    public DetailsQuestion(int id, int levelNum, boolean statsQuestion, int points) {
        this.id = id;
        this.levelNum = levelNum;
        this.statsQuestion = statsQuestion;
        this.points = points;
    }

    @Ignore
    public DetailsQuestion(int levelNum, boolean statsQuestion) {
        this.levelNum = levelNum;
        this.statsQuestion = statsQuestion;
    }


@Ignore
    public DetailsQuestion(int levelNum, boolean statsQuestion, int points) {
        this.levelNum = levelNum;
        this.statsQuestion = statsQuestion;
        this.points = points;
    }


    public DetailsQuestion(int levelNum, int points) {
        this.levelNum = levelNum;
        this.points = points;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public boolean isStatsQuestion() {
        return statsQuestion;
    }

    public void setStatsQuestion(boolean statsQuestion) {
        this.statsQuestion = statsQuestion;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
