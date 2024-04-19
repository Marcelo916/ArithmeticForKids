package com.example.arithmeticforkids.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arithmeticforkids.database.entities.MultiplicationLog;

import java.util.List;

@Dao
public interface MultiplicationLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MultiplicationLog multiplicationLog);

    @Delete
    void delete(MultiplicationLog multiplicationLog);

    @Update
    void update(MultiplicationLog multiplicationLog);

    @Query("SELECT * FROM " + AdditionLogDatabase.MULTIPLICATION_LOG_TABLE + " ORDER BY bestScore DESC LIMIT 1")
    List<MultiplicationLog> getMultiplicationRecords();

    @Query("SELECT * FROM " + AdditionLogDatabase.MULTIPLICATION_LOG_TABLE + " WHERE userId = :userId" + " ORDER BY bestScore DESC LIMIT 1")
    List<MultiplicationLog> getMultiplicationRecordByUserId(int userId);
}
