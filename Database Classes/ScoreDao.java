package com.example.restaurantcalculator;

import java.util.List;

import androidx.room.*;

@Dao
public interface ScoreDao {
    @Query("SELECT * FROM Scores WHERE gameID = (:gameNum)")
    List<Scores> getScoresForGame(int gameNum);

    @Insert
    void addScore(Scores newScore);

    @Query("SELECT TOP (1) gameID FROM Scores ORDER BY gameID")
    int getLastGameID();
}
