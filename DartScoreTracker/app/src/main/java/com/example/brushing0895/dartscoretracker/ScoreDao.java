package com.example.brushing0895.dartscoretracker;

import android.arch.persistence.room.*;
import java.util.List;

@Dao
public interface ScoreDao {
    @Query("SELECT * FROM Scores WHERE gameID = (:gameNum)")
    List<Scores> getScoresForGame(int gameNum);

    @Insert
    void addScore(Scores newScore);

    @Query("SELECT gameID FROM Scores ORDER BY gameID DESC Limit 1")
    int getLastGameID();

    @Query("SELECT gameID FROM Scores ORDER BY gameID ASC Limit 1")
    int getFirstGameID();
}
