package com.example.arithmeticforkids.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.arithmeticforkids.database.AdditionLogDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
//import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Entity(tableName = AdditionLogDatabase.ADDITION_LOG_TABLE)
public class AdditionLog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int bestScore;
    private LocalDateTime date;
    //private Date date;

    public AdditionLog(int bestScore) {
        this.bestScore = bestScore;
        //date = new Date();
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        //String formattedDate = dateFormat.format(date);
        return "Your all-time best score is: " + bestScore;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
