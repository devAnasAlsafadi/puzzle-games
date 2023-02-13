package com.example.puzzlegam.db.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "questions_base" , foreignKeys = {@ForeignKey(entity = Stage.class,parentColumns = "levelNumber_base",childColumns = "idStageFor_base",onDelete = ForeignKey.CASCADE
,onUpdate = ForeignKey.CASCADE)})
public class Questions implements Serializable {

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "id_question_base")
        private int idQuestion;

        @ColumnInfo(name = "NumberQuestion_base")
        @SerializedName("id")
        @Expose
        private Integer id;

        @ColumnInfo(name = "idStageFor_base")
        private Integer idStageFor;

        @ColumnInfo(name = "title_question_base")
        @NonNull
        @SerializedName("title")
        @Expose
        private String title;

        @ColumnInfo(name = "answers1_base")
        @SerializedName("answer_1")
        @Expose
        private String answer1;

        @ColumnInfo(name = "answers2_base")
        @SerializedName("answer_2")
        @Expose
        private String answer2;

        @ColumnInfo(name = "answers3_base")
        @SerializedName("answer_3")
        @Expose
        private String answer3;

        @ColumnInfo(name = "answers4_base")
        @SerializedName("answer_4")
        @Expose
        private String answer4;


        @ColumnInfo(name = "true_answers_base")
        @SerializedName("true_answer")
        @Expose
        private String trueAnswer;

        @ColumnInfo(name = "point_question_base")
        @SerializedName("points")
        @Expose
        private Integer points;

        @ColumnInfo(name = "duration_base")
        @SerializedName("duration")
        @Expose
        private Integer duration;

        @ColumnInfo(name = "hint_base")
        @SerializedName("hint")
        @Expose
        private String hint;


    public Questions() {
    }




    @Ignore
    public Questions(Integer id, Integer idStageFor, @NonNull String title, String answer1, String answer2, String answer3, String answer4, String trueAnswer, Integer points,  Integer duration, String hint) {
        this.id = id;
        this.idStageFor = idStageFor;
        this.title = title;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.trueAnswer = trueAnswer;
        this.points = points;
        this.duration = duration;
        this.hint = hint;
    }


    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdStageFor() {
        return idStageFor;
    }

    public void setIdStageFor(Integer idStageFor) {
        this.idStageFor = idStageFor;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
