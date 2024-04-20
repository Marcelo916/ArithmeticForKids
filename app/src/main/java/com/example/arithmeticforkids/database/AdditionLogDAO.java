package com.example.arithmeticforkids.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arithmeticforkids.database.entities.AdditionLog;

import java.util.List;

@Dao
public interface AdditionLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AdditionLog additionLog);

    @Delete
    void delete(AdditionLog additionLog);

    @Update
    void update(AdditionLog additionLog);

    @Query("SELECT * FROM " + AdditionLogDatabase.ADDITION_LOG_TABLE + " ORDER BY bestScore DESC LIMIT 1")
    List<AdditionLog> getAdditionRecords();

    @Query("SELECT * FROM " + AdditionLogDatabase.ADDITION_LOG_TABLE + " WHERE userId = :userId" + " ORDER BY bestScore DESC LIMIT 1")
    List<AdditionLog> getAdditionRecordByUserId(int userId);
}
