package com.example.brushing0895.dartscoretracker;

import java.io.Serializable;
import android.arch.persistence.room.*;

@Entity (tableName = "Scores")
public class Scores implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int scoreID;

    @ColumnInfo(name = "gameID")
    public int gameID;

    @ColumnInfo(name = "dart1")
    public int dart1Score;

    @ColumnInfo(name = "dart2")
    public int dart2Score;

    @ColumnInfo(name = "dart3")
    public int dart3Score;

    @ColumnInfo(name = "scoreAtStart")
    public int scoreAtStart;
}
