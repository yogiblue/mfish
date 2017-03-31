package com.muwuprojects.mfish;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends Activity {

    FishThing myFish = new FishThing();

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

        myFish.initialiseImage(getResources());

    }



}
