package com.example.arithmeticforkids.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.arithmeticforkids.database.AdditionLogDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = AdditionLogDatabase.ADDITION_LOG_TABLE)
public class AdditionLog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int bestScore;
    private LocalDateTime date;

    private int userId;

    public AdditionLog(int bestScore, int userId) {
        this.bestScore = bestScore;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return "Your all-time best score is: " + bestScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionLog)) return false;
        AdditionLog that = (AdditionLog) o;
        return getId() == that.getId() && getBestScore() == that.getBestScore() && getUserId() == that.getUserId() && Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBestScore(), getDate(), getUserId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
