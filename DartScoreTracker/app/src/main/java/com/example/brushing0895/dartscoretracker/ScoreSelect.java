/*
 *  FILE          : ScoreSelect.java
 *  PROJECT       : PROG3150 - Assignment #1
 *  PROGRAMMER    : Alex Kozak, Brendan Rushing
 *  FIRST VERSION : 2019-02-04
 *  DESCRIPTION   :
 *    The functions in this file are used as the main menu, allowing you to navigate to either the main game or the about screen.
 */

package com.example.brushing0895.dartscoretracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

// image credits: https://www.123rf.com/stock-photo/darts_logo.html?sti=o2aq5ksciwaqlct7wg|&mediapopup=39897487

public class ScoreSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_select);
        final Button btnStartGame = ((Button)findViewById(R.id.btnStartGame));
        final Button btnAbout = ((Button)findViewById(R.id.btnAbout));
        final Button btnScoreHistory = ((Button)findViewById(R.id.btnScoreHistory));

        btnStartGame.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                Function:       btnStartGame.onClick
                Description:    This navigates to the game screen with the selected starting score
                Parameters:     N/A
                Returns:        VOID
                 */

                // navigate to game screen\
                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                intent.putExtra("GAME_SCORE", ((Spinner)findViewById(R.id.cbStartScore)).getSelectedItem().toString());
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                Function:       btnAbout.onClick
                Description:    This navigates to the about screen
                Parameters:     N/A
                Returns:        VOID
                 */

                Intent intent = new Intent(getApplicationContext(), About.class);
                startActivity(intent);
            }
        });

        btnScoreHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ScoreHistory.class);
                startActivity(intent);

            }
        });

    }
}
