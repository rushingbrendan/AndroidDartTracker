package com.example.brushing0895.dartscoretracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.arch.persistence.room.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScoreHistory extends AppCompatActivity {

    private ScoreDatabase scoreDatabase;
    private ScoreDao scoreDao;
    private List<String> Scores;
    private int FirstGameID;
    private int LastGameID;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history);

        Scores = new ArrayList<String>();

        scoreDatabase = ScoreDatabase.getScoreDatabase(ScoreHistory.this);
        scoreDao = scoreDatabase.dataAccess();

        FirstGameID = scoreDao.getFirstGameID();
        LastGameID = scoreDao.getLastGameID();

        populateListView();
    }

    private void populateListView() {
        List<Scores> lScores;

        for (int i = FirstGameID; i < LastGameID; i++) {
            lScores = scoreDao.getScoresForGame(i);

            for (Scores score:lScores) {
                Scores.add(score.toString());
            }
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Scores);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }


}
