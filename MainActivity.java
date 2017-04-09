package com.muwuprojects.mfish;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    FishThing myFish = new FishThing();
    private DatabaseHandler db = new DatabaseHandler(this);
    private boolean showingHelp=true;
   // private messageObject currentMessage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO clear and set new notifications at this point

        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.test_layout);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String storedPreference = preferences.getString("fish_name", "Mr Fish");

        String[] allColors = getResources().getStringArray(R.array.colors);
        int len = allColors.length;
        Random r = new Random();
        myLayout.setBackgroundColor(Color.parseColor(allColors[r.nextInt(len)]));

        myFish.setBodyImage((ImageView) findViewById(R.id.imageLight));
        myFish.setEyeImage((ImageView) findViewById(R.id.imageEyes));

        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();

        long lastVisitLong = db.getLastDate();


        TextView myText = (TextView) findViewById(R.id.textMessage);

        if(lastVisitLong==0) {
            db.createDate();
            myText.setText("Welcome");
        }
        else {
            //long numDays = db.getNumDaysSinceLastVisit();
            long numHours = db.getNumHoursSinceLastVisit();
            db.updateDate();
            myText.setText(storedPreference);

        }

        ImageView helpImage = (ImageView) findViewById(R.id.imageMore);

        findViewById(R.id.imageMore).setOnClickListener(
                new View.OnClickListener() {


                    public void onClick(View v) {
                        Context context = getApplicationContext();


                        Intent i = new Intent(context, HelpActivity.class);

                        if(showingHelp==true)
                            i.putExtra("which", -1);
                        //else
                        //    i.putExtra("which", currentMessage.getId());

                        startActivity(i);

                    }
                });

        ImageView settingsImage = (ImageView) findViewById(R.id.imageSettings);

        findViewById(R.id.imageSettings).setOnClickListener(
                new View.OnClickListener() {


                    public void onClick(View v) {
                        Context context = getApplicationContext();

                        Intent i = new Intent(context, SettingsActivityNew.class);

                        startActivity(i);

                    }
                });

        //make the circle buttons clickable/touchable
        // one launches settings
        // one launches extended information
        
        // choose which items to show
        
        // choose message to display
        // if it is not a new day then display last message
        // db.getMessageNumber()
        // display
        // db.incMessageNumber()

        // show the fish and let it do it's thing
        // if it has been a while, display bones
        myFish.initialiseImage(getResources(), width, height);

    }



}
