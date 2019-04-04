package com.example.restaurantcalculator;

import java.io.Serializable;
import androidx.room.*;

@Entity
public class Scores implements Serializable {
    @PrimaryKey(autoGenerate = true)
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