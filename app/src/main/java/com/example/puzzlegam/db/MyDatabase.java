package com.example.puzzlegam.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.puzzlegam.db.database_interface.DetailsQuestionDAO;
import com.example.puzzlegam.db.database_interface.QuestionDAO;
import com.example.puzzlegam.db.database_interface.StageDAO;
import com.example.puzzlegam.db.database_interface.UserDAO;
import com.example.puzzlegam.db.models.DetailsQuestion;
import com.example.puzzlegam.db.models.Questions;
import com.example.puzzlegam.db.models.Stage;
import com.example.puzzlegam.db.models.User;
import com.example.puzzlegam.views.DetailsStage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Stage.class, Questions.class, DetailsQuestion.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract QuestionDAO questionDAO();
    public abstract UserDAO userDAO();
    public abstract StageDAO stageDAO();
    public abstract DetailsQuestionDAO detailsQuestionDAO();



    private static volatile MyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "show_product_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
