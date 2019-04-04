/*
 *  FILE          : MainScreen.java
 *  PROJECT       : PROG3150 - Assignment #1
 *  PROGRAMMER    : Alex Kozak, Brendan Rushing
 *  FIRST VERSION : 2019-02-04
 *  DESCRIPTION   :
 *    The functions in this file are used for the hooks in the main game screen.
 */

package com.example.brushing0895.dartscoretracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.arch.persistence.room.*;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainScreen extends AppCompatActivity {
    private static final String TAG = "MainScreen";
    private ScoreDatabase scoreDatabase;
    private ScoreDao scoreDao;
    private List<Scores> lScores;
    private int gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        Function:       MainScreen.onCreate
        Description:    On creation of the activity, this code is executed
        Parameters:     N/A
        Returns:        VOID
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        scoreDatabase = ScoreDatabase.getScoreDatabase(MainScreen.this);
        scoreDao = scoreDatabase.dataAccess();

        gameID = scoreDao.getLastGameID()+1;

        /*  Seek Bar for multiplier Settings   */
        /*  Multiplier: 1 , 2, 3          */

        final SeekBar multiplierBar = (SeekBar)findViewById(R.id.seekBarMultiplier);
        multiplierBar.incrementProgressBy(1);
        multiplierBar.setMax(2);
        multiplierBar.setProgress(0);

        /*  Seek Bar for Score Value   */
        /*  Points: 1 to 20          */

        final SeekBar pointBar = (SeekBar)findViewById(R.id.seekBarShotValue);
        pointBar.incrementProgressBy(1);
        pointBar.setMax(19);
        pointBar.setProgress(0);

        multiplierBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //update shot preview score
                updateShotPreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        pointBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //update shot preview score
                updateShotPreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        final Button btn1 = ((Button)findViewById(R.id.btnShot1));
        final Button btn2 = ((Button)findViewById(R.id.btnShot2));
        final Button btn3 = ((Button)findViewById(R.id.btnShot3));
        final Button btn50 = ((Button)findViewById(R.id.btn50));
        final Button btn25 = ((Button)findViewById(R.id.btn25));
        final Button btn0 = ((Button)findViewById(R.id.btn0));
        final Button addShot = (Button)(findViewById(R.id.addShotButton));
        final Button btnSubmitRound = (Button)(findViewById(R.id.btnSubmitRound));
        final Button btnNewGame = (Button)(findViewById(R.id.btnNewGame));

        btnNewGame.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                Function:       btnNewGame.onClick
                Description:    When new game is clicked, the board is reset, starting a new game.
                Parameters:     N/A
                Returns:        VOID
                 */
                clearShots();
                multiplierBar.setProgress(0);
                pointBar.setProgress(0);
                ((TextView)findViewById(R.id.lblRoundScorePreview)).setText("501");
                updateFinishes();
                Intent intent = getIntent();
                String str = intent.getStringExtra("GAME_SCORE");
                ((TextView)findViewById(R.id.lblRoundScorePreview)).setText(str);
            }
        });

        btnSubmitRound.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                Function:       btnSubmitRound.onClick
                Description:    Submits the current set of three darts thrown, saving them to the current score.
                Parameters:     N/A
                Returns:        VOID
                 */
                if (btn1.getText().toString() != "" && btn2.getText().toString() != "" && btn3.getText().toString() != ""){
                    int s1, s2, s3, roundScore, totalScore;
                    s1 = Integer.parseInt(btn1.getText().toString());
                    s2 = Integer.parseInt(btn2.getText().toString());
                    s3 = Integer.parseInt(btn3.getText().toString());
                    clearShots();

                    roundScore = s1 + s2 + s3;
                    totalScore = Integer.parseInt(((TextView)findViewById(R.id.lblRoundScorePreview)).getText().toString());

                    Scores sc = new Scores();
                    sc.scoreAtStart = totalScore;
                    sc.dart1Score = s1;
                    sc.dart2Score = s2;
                    sc.dart3Score = s3;
                    sc.gameID = gameID;
                    sc.scoreID = 0;

                    scoreDao.addScore(sc);

                    List<Scores> ls = scoreDao.getScoresForGame(0);
                    int l = scoreDao.getLastGameID();

                    ((TextView)findViewById(R.id.lblRoundScorePreview)).setText(Integer.toString(totalScore - roundScore));
                    ((TextView)findViewById(R.id.btn25)).setText(Integer.toString(l));

                    updateFinishes();
                }
            }
        });

        btn50.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                addShot(50);
                checkScore(true);
            }
        });

        btn25.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                addShot(25);
                checkScore(false);
            }
        });

        btn0.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                addShot(0);
            }
        });
        btn0.setTextColor(Color.parseColor("white"));

        addShot.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                /*
                Function:       addShot.onClick
                Description:    When the addShot is clicked, it adds the currently selected score to the shot tiles
                Parameters:     N/A
                Returns:        VOID
                 */

                //update button with total score

                //get multiplier value
                int multiplierValue = ((SeekBar) findViewById(R.id.seekBarMultiplier)).getProgress()+1;

                //get shot point value
                int shotPoint = ((SeekBar) findViewById(R.id.seekBarShotValue)).getProgress()+1;

                //multiply together
                int currentShotTotal = multiplierValue * shotPoint;

                addShot(currentShotTotal);
                checkScore(multiplierValue == 2);
            }
        });

        btn1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn1.setText("");
            }
        });
        btn2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn2.setText("");
            }
        });
        btn3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn3.setText("");
            }
        });

        // set the main value from the given starting point from the intent
        Intent intent = getIntent();
        String str = intent.getStringExtra("GAME_SCORE");
        ((TextView)findViewById(R.id.lblRoundScorePreview)).setText(str);
    }

    void checkScore(boolean isWinShot){
        /*
        Function:       CheckScore
        Description:    This checks to see the current score to see any special circumstances like a win or a bust.
        Parameters:     boolean: isWinShot -> If the shot can be the final shot of the round or not.
        Returns:        VOID
         */
        final Button btn1 = ((Button)findViewById(R.id.btnShot1));
        final Button btn2 = ((Button)findViewById(R.id.btnShot2));
        final Button btn3 = ((Button)findViewById(R.id.btnShot3));

        int pr = pointsRemaining();
        if (pr == 0 && isWinShot){
            Toast.makeText(getApplicationContext(),"You Win!",Toast.LENGTH_SHORT).show();
        }
        else if (pr == 0 && !isWinShot)
        {
            // remove bust items one by one until valid, then clear
            Button bSubmit = (Button)(findViewById(R.id.btnSubmitRound));
            Toast.makeText(getApplicationContext(),"BUST, You MUST double out or hit a bullseye!",Toast.LENGTH_SHORT).show();

            btn3.setText("0");
            if (pointsRemaining() > 1){
                bSubmit.callOnClick();
            }

            btn2.setText("0");
            if (pointsRemaining() > 1){
                bSubmit.callOnClick();
            }
            Log.i(TAG, "Bust! Did not double out");
            clearShots();
        }
        else if (pr < 0 || pr == 1){
            Button bSubmit = (Button)(findViewById(R.id.btnSubmitRound));
            Toast.makeText(getApplicationContext(),"Bust!",Toast.LENGTH_SHORT).show();

            btn3.setText("0");
            if (pointsRemaining() > 1){
                bSubmit.callOnClick();
            }

            btn2.setText("0");
            if (pointsRemaining() > 1){
                bSubmit.callOnClick();
            }
            Log.i(TAG, "Bust! score is too much");
            clearShots();
        }
    }

    void clearShots()
    {
        /*
        Function:       clearShots
        Description:    This clears the current shot buttons
        Parameters:     VOID
        Returns:        VOID
         */

        final Button btn1 = ((Button)findViewById(R.id.btnShot1));
        final Button btn2 = ((Button)findViewById(R.id.btnShot2));
        final Button btn3 = ((Button)findViewById(R.id.btnShot3));
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        Log.i(TAG, "Shots cleared.");
    }

    void addShot(int shotValue)
    {
        /*
        Function:       addShot
        Description:    This adds a shot value to the list of currently executed shots
        Parameters:     int shotValue: the value to add to the shots
        Returns:        VOID
         */

        final Button btn1 = ((Button)findViewById(R.id.btnShot1));
        final Button btn2 = ((Button)findViewById(R.id.btnShot2));
        final Button btn3 = ((Button)findViewById(R.id.btnShot3));

        if (btn1.getText().toString() == ""){
            //button is empty so add total score to it
            btn1.setText(Integer.toString(shotValue));
            Log.i(TAG, "Added to btn1");
        }
        else if (btn2.getText().toString() == ""){
            //button is empty so add total score to it
            btn2.setText(Integer.toString(shotValue));
            Log.i(TAG, "Added to btn2");
        }
        else if (btn3.getText().toString() == ""){
            //button is empty so add total score to it
            btn3.setText(Integer.toString(shotValue));
            Log.i(TAG, "Added to btn3");
        }
    }

    int pointsRemaining(){
        /*
        Function:       pointsRemaining
        Description:    This returns the current points remaining
        Parameters:     VOID
        Returns:        int: The current number of points remaining given the current shots on the board
         */

        int s1 = 0, s2 = 0, s3 = 0, totalScore = 0;
        Button btn1 = ((Button)findViewById(R.id.btnShot1));
        Button btn2 = ((Button)findViewById(R.id.btnShot2));
        Button btn3 = ((Button)findViewById(R.id.btnShot3));

        if (btn1.getText().toString() != ""){
            //button is empty so add total score to it
            s1 = Integer.parseInt(btn1.getText().toString());
        }
        if (btn2.getText().toString() != ""){
            //button is empty so add total score to it
            s2 = Integer.parseInt(btn2.getText().toString());
        }
        if (btn3.getText().toString() != ""){
            //button is empty so add total score to it
            s3 = Integer.parseInt(btn3.getText().toString());
        }
        totalScore = Integer.parseInt(((TextView)findViewById(R.id.lblRoundScorePreview)).getText().toString());
        int pointsRemain = totalScore - (s1 + s2 + s3);
        Log.i(TAG, "Current Points remaining: " + pointsRemain);

        return pointsRemain;
    }

    void updateFinishes() {
        /*
        Function:       updateFinishes
        Description:    This updates the possible finishes to the game, blank if none exist
        Parameters:     VOID
        Returns:        VOID
         */
        int totalScore = Integer.parseInt(((TextView)findViewById(R.id.lblRoundScorePreview)).getText().toString());
        TextView lblDartOut =  (TextView)findViewById(R.id.lblDartOut);
        String dartsOut = "";

        if (totalScore > 170 || totalScore == 169 || totalScore == 168 || totalScore == 166 || totalScore == 165 || totalScore == 163 || totalScore == 162 || totalScore == 159) {
            lblDartOut.setText("");
            Log.i(TAG, "No possible finishes.");
        }
        else {
            int numShots = 0;
            while (true) {
                numShots++;
                if (totalScore == 50){
                    dartsOut += "Bull";
                    totalScore = 0;
                }
                else if (totalScore <= 40 && totalScore % 2 == 0) {
                    dartsOut += "D" + Integer.toString(totalScore/2);
                    totalScore = 0;
                }
                else if (totalScore <= 20 && totalScore % 2 == 1){
                    for (int i = 19; i > 0; i -= 2) {
                        if (i + 2 < totalScore) {
                            totalScore -= (i);
                            dartsOut += Integer.toString(i);
                            break;
                        }
                    }                }
                else if (totalScore % 2 == 0) {
                    for (int i = 20; i > 0; i -= 2) {
                        if (((i * 3) + 2) < totalScore) {
                            totalScore -= (i * 3);
                            dartsOut += "T" + Integer.toString(i);
                            break;
                        }
                    }
                }
                else if (totalScore % 2 == 1) {
                    for (int i = 19; i > 0; i -= 2) {
                        if (i * 3 + 2 < totalScore) {
                            totalScore -= (i * 3);
                            dartsOut += "T" + Integer.toString(i);
                            break;
                        }
                    }
                }
                if (numShots == 3 || totalScore == 0) {
                    break;
                }
                else{
                    dartsOut += " - ";
                }
            }
            lblDartOut.setText(dartsOut);
            Log.i(TAG, "finish possibility: " + dartsOut);
        }
    }

    void updateShotPreview(){
        /*
        Function:       updateShotPreview
        Description:    This updates the shot preview label
        Parameters:     VOID
        Returns:        VOID
         */

        //get multiplier value
        int multiplierValue = ((SeekBar) findViewById(R.id.seekBarMultiplier)).getProgress()+1;

        //get shot point value
        int shotPoint = ((SeekBar) findViewById(R.id.seekBarShotValue)).getProgress()+1;

        //multiply together
        int currentShotTotal = multiplierValue * shotPoint;

        //append items
        String shotPreviewString = Integer.toString(multiplierValue) + " x " + Integer.toString(shotPoint) + " = " +Integer.toString(currentShotTotal);

        //update
        ((TextView)findViewById(R.id.textViewScorePreview)).setText(shotPreviewString);
    }
}