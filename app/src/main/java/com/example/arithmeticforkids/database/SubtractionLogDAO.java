package com.example.arithmeticforkids.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arithmeticforkids.database.entities.SubtractionLog;

import java.util.List;

@Dao
public interface SubtractionLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SubtractionLog subtractionLog);

    @Delete
    void delete(SubtractionLog subtractionLog);

    @Update
    void update(SubtractionLog subtractionLog);

    @Query("SELECT * FROM " + AdditionLogDatabase.SUBTRACTION_LOG_TABLE + " ORDER BY bestScore DESC LIMIT 1")
    List<SubtractionLog> getSubtractionRecords();

    @Query("SELECT * FROM " + AdditionLogDatabase.SUBTRACTION_LOG_TABLE + " WHERE userId = :userId" + " ORDER BY bestScore DESC LIMIT 1")
    List<SubtractionLog> getSubtractionRecordByUserId(int userId);
}
