package com.example.puzzlegam.db.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "stage_base")
public class Stage {


        @ColumnInfo(name = "levelNumber_base")
        @PrimaryKey
        @NonNull
        @SerializedName("level_no")
        @Expose
        private Integer levelNo;


        @NonNull
        @ColumnInfo(name = "pointNumber_base")
        @SerializedName("unlock_points")
        @Expose
        private Integer unlockPoints;


    public Stage() {
    }


    public Stage(Integer levelNo, @NonNull Integer unlockPoints) {
        this.levelNo = levelNo;
        this.unlockPoints = unlockPoints;
    }


    public Integer getLevelNo() {
            return levelNo;
        }

        public void setLevelNo(Integer levelNo) {
            this.levelNo = levelNo;
        }

        public Integer getUnlockPoints() {
            return unlockPoints;
        }

        public void setUnlockPoints(Integer unlockPoints) {
            this.unlockPoints = unlockPoints;
        }


}
