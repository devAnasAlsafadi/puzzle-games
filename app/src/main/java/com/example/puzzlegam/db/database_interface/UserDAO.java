package com.example.puzzlegam.db.database_interface;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.puzzlegam.db.models.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    long insertUser(User user);

    @Update(onConflict = REPLACE)
    int updateUser(User user);

    @Query("select * from user_base")
    LiveData<List<User>> getUser();
    @Query("DELETE FROM user_base")
    void deleteUser();
}
