package com.example.arithmeticforkids.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.arithmeticforkids.database.entities.AdditionLog;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AdditionLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AdditionLog additionLog);

    @Query("SELECT * FROM " + AdditionLogDatabase.ADDITION_LOG_TABLE + " ORDER BY bestScore DESC LIMIT 1")
    List<AdditionLog> getAdditionRecords();
}
