package com.example.arithmeticforkids.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.arithmeticforkids.database.entities.DivisionLog;

import java.util.List;

@Dao
public interface DivisionLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DivisionLog divisionLog);

    @Query("SELECT * FROM " + AdditionLogDatabase.DIVISION_LOG_TABLE + " ORDER BY bestScore DESC LIMIT 1")
    List<DivisionLog> getDivisionRecords();
}
