package com.example.arithmeticforkids.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.arithmeticforkids.database.AdditionLogDatabase;

import java.time.LocalDate;
import java.util.Objects;

@Entity(tableName = AdditionLogDatabase.ADDITION_LOG_TABLE)
public class AdditionLog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int bestScore;
    private LocalDate date;

    public AdditionLog(int bestScore) {
        this.bestScore = bestScore;
        date = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionLog)) return false;
        AdditionLog that = (AdditionLog) o;
        return getId() == that.getId() && getBestScore() == that.getBestScore() && Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBestScore(), getDate());
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
