/*
 *  FILE          : About.java
 *  PROJECT       : PROG3150 - Assignment #1
 *  PROGRAMMER    : Alex Kozak, Brendan Rushing
 *  FIRST VERSION : 2019-02-04
 *  DESCRIPTION   :
 *    The functions in this file are used to hook the back button up, so it closes the activity.
 */

package com.example.brushing0895.dartscoretracker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final Button btnBack = ((Button)findViewById(R.id.btnBack));

        btnBack.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                Function:       onClick
                Description:    This closes about screen
                Parameters:     N/A
                Returns:        VOID
                 */
                finish();
            }
        });

        final Button btnHelp = ((Button)findViewById(R.id.btnHelp));
        final WebView webView = ((WebView)findViewById(R.id.webView));
        webView.setVisibility(webView.GONE);
        btnHelp.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                Function:       onClick
                Description:    This closes about screen
                Parameters:     N/A
                Returns:        VOID
                 */
                String url = "https://www.mastersofgames.com/rules/darts-rules.htm";
                /*CustomTabsIntent.Builder builder = new  CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(url));*/
                webView.setVisibility(webView.VISIBLE);
                webView.loadUrl(url);
                /*Intent webOpen = new Intent(android.content.Intent.ACTION_VIEW);
                webOpen.setData(Uri.parse(url));
                startActivity(webOpen);*/
            }
        });
    }
}
