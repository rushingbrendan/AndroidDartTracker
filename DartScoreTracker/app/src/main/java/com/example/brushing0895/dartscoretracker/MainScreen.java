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

public class MainScreen extends AppCompatActivity {

    //private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_board);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_shots);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_win);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //mTextMessage = (TextView)findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*  Seek Bar for multiplier Settings   */
        /*  Multiplier: 1 , 2, 3          */

        SeekBar multiplierBar = (SeekBar)findViewById(R.id.seekBarMultiplier);
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


        Button addShot = (Button)(findViewById(R.id.addShotButton));
        addShot.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                //update button with total score

                Button btn1 = ((Button)findViewById(R.id.buttonShot1));
                Button btn2 = ((Button)findViewById(R.id.buttonShot2));
                Button btn3 = ((Button)findViewById(R.id.buttonShot3));

                //get multiplier value
                int multiplierValue = ((SeekBar) findViewById(R.id.seekBarMultiplier)).getProgress()+1;

                //get shot point value
                int shotPoint = ((SeekBar) findViewById(R.id.seekBarShotValue)).getProgress()+1;

                //multiply together
                int currentShotTotal = multiplierValue * shotPoint;

                if (btn1.getText().toString() == ""){
                    //button is empty so add total score to it
                    btn1.setText(Integer.toString(currentShotTotal));
                }
                else if (btn2.getText().toString() == ""){
                    //button is empty so add total score to it
                    btn2.setText(Integer.toString(currentShotTotal));
                }
                else if (btn3.getText().toString() == ""){
                    //button is empty so add total score to it
                    btn3.setText(Integer.toString(currentShotTotal));
                }
            }
        });
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
