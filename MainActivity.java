package com.muwuprojects.mfish;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView myImage = (ImageView)findViewById(R.id.imageLight);
        myImage.setAlpha(0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(myImage, "alpha", 0f, 0.4f);
        fadeIn.setDuration(10000);
        fadeIn.start();




    }
}
