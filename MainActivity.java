package com.muwuprojects.mfish;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    FishThing myFish = new FishThing();
    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.test_layout);

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
            myText.setText("Back again you little sausage");

        }
        
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
