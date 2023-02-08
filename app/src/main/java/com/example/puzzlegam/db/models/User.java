package com.example.puzzlegam.db.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.puzzlegam.db.converter.DateConvert;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "user_base")
@TypeConverters({DateConvert.class})
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId_base")
    private int userId;
    @ColumnInfo(name = "userName_base")
    @NonNull
    private String userName;
    @ColumnInfo(name = "userEmail_base")
    @NonNull
    private String email;
    @ColumnInfo(name = "userDate_base")
    @NonNull
    private Date date;
    @ColumnInfo(name = "userGender_base")
    private String gender;
    @ColumnInfo(name = "userCountry_base")
    private String country;

    @Ignore
    public User(int userId, String userName, String email, Date date, String gender, String country) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.date = date;
        this.gender = gender;
        this.country = country;
    }

    public User(String userName, String email, Date date, String gender, String country) {
        this.userName = userName;
        this.email = email;
        this.date = date;
        this.gender = gender;
        this.country = country;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
