package com.example.brushing0895.dartscoretracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        /*  Seek Bar for multiplier Settings   */
        /*  Multiplier: 1 , 2, 3          */

        final SeekBar multiplierBar = (SeekBar)findViewById(R.id.seekBarMultiplier);
        multiplierBar.incrementProgressBy(1);
        multiplierBar.setMax(2);
        multiplierBar.setProgress(0);

        /*  Seek Bar for Score Value   */
        /*  Points: 1 to 20          */

        SeekBar pointBar = (SeekBar)findViewById(R.id.seekBarShotValue);
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

        btnSubmitRound.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (btn1.getText().toString() != "" && btn2.getText().toString() != "" && btn3.getText().toString() != ""){
                    int s1, s2, s3, roundScore, totalScore, oldRoundScore;
                    s1 = Integer.parseInt(btn1.getText().toString());
                    s2 = Integer.parseInt(btn2.getText().toString());
                    s3 = Integer.parseInt(btn3.getText().toString());
                    clearShots();

                    roundScore = s1 + s2 + s3;
                    Toast.makeText(getApplicationContext(),Integer.toString(roundScore),Toast.LENGTH_SHORT).show();
                    totalScore = Integer.parseInt(((TextView)findViewById(R.id.lblRoundScorePreview)).getText().toString());
                    ((TextView)findViewById(R.id.lblRoundScorePreview)).setText(Integer.toString(totalScore - roundScore));

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

        addShot.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
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
    }

    void checkScore(boolean isWinShot){
        final Button btn1 = ((Button)findViewById(R.id.btnShot1));
        final Button btn2 = ((Button)findViewById(R.id.btnShot2));
        final Button btn3 = ((Button)findViewById(R.id.btnShot3));

        int pr = pointsRemaining();
        if (pr == 0 && isWinShot){
            Toast.makeText(getApplicationContext(),"You Win!",Toast.LENGTH_SHORT).show();
        }
        else if (pr == 0 && !isWinShot)
        {
            Toast.makeText(getApplicationContext(),"BUST, You MUST double out or hit a bullseye!",Toast.LENGTH_SHORT).show();
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

            clearShots();
        }
    }

    void clearShots()
    {
        final Button btn1 = ((Button)findViewById(R.id.btnShot1));
        final Button btn2 = ((Button)findViewById(R.id.btnShot2));
        final Button btn3 = ((Button)findViewById(R.id.btnShot3));
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
    }

    void addShot(int shotValue)
    {
        final Button btn1 = ((Button)findViewById(R.id.btnShot1));
        final Button btn2 = ((Button)findViewById(R.id.btnShot2));
        final Button btn3 = ((Button)findViewById(R.id.btnShot3));

        if (btn1.getText().toString() == ""){
            //button is empty so add total score to it
            btn1.setText(Integer.toString(shotValue));
        }
        else if (btn2.getText().toString() == ""){
            //button is empty so add total score to it
            btn2.setText(Integer.toString(shotValue));
        }
        else if (btn3.getText().toString() == ""){
            //button is empty so add total score to it
            btn3.setText(Integer.toString(shotValue));
        }
    }

    int pointsRemaining(){
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

        return totalScore - (s1 + s2 + s3);
    }

    void updateShotPreview(){

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
