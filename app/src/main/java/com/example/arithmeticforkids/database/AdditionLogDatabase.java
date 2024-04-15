package com.example.arithmeticforkids.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arithmeticforkids.MainActivity;
import com.example.arithmeticforkids.database.entities.AdditionLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AdditionLog.class}, version = 1, exportSchema = false)
public abstract class AdditionLogDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "AdditionLogDatabase";
    public static final String ADDITION_LOG_TABLE = "additionLogTable";

    private static volatile AdditionLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static AdditionLogDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AdditionLogDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AdditionLogDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(MainActivity.TAG, "DATABASE CREATED!");
            //TODO: add databaseWriteExecutor.execute(() -> {...}
        }
    };

    public abstract AdditionLogDAO additionLogDAO();
}
