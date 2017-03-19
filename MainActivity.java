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

    boolean goingRight=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.test_layout);

        String[] allColors = getResources().getStringArray(R.array.colors);
        int len = allColors.length;
        Random r = new Random();
        myLayout.setBackgroundColor(Color.parseColor(allColors[r.nextInt(len)]));

        ImageView myImage = (ImageView) findViewById(R.id.imageLight);
        myImage.setAlpha(0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(myImage, "alpha", 0f, 0.4f);
        fadeIn.setDuration(10000);
        fadeIn.start();

        ImageView myImageMove = (ImageView) findViewById(R.id.imageLight);
        int x1 = (int) myImageMove.getX();
        int y1 = (int) myImageMove.getY();
        int x2 = r.nextInt(600);

        ObjectAnimator mover1 = ObjectAnimator.ofFloat(myImageMove, "x", x1, x2);

        Animator.AnimatorListener myAnimationListener = new AnimationAdaptor();
        AnimatorSet animatorSet = new AnimatorSet();

        mover1.setDuration(10000);
        mover1.addListener(myAnimationListener);
        animatorSet.play(mover1);
        animatorSet.start();


    }


    public class AnimationAdaptor implements Animator.AnimatorListener {

        AnimationAdaptor() {

        }

        public void onAnimationEnd(Animator animation) {
                //ObjectAnimator fadeIn = ObjectAnimator.ofFloat(myCard, "alpha", 0f, 1f);
                //fadeIn.setDuration(2000);
                //fadeIn.start();
                Random r = new Random();

                ImageView myImageMove = (ImageView) findViewById(R.id.imageLight);
                if (goingRight==true){
                    myImageMove.setScaleX(-1f);
                    goingRight=false;
                    int x1 = (int) myImageMove.getX();
                    int y1 = (int) myImageMove.getY();
                    int x2 = r.nextInt(50);

                    ObjectAnimator mover1 = ObjectAnimator.ofFloat(myImageMove, "x", x1, x2);
                    AnimatorSet animatorSet = new AnimatorSet();
                    Animator.AnimatorListener myAnimationListener = new AnimationAdaptor();
                    mover1.addListener(myAnimationListener);

                    mover1.setDuration(10000);
                    animatorSet.play(mover1);
                    animatorSet.start();
                }
                else {
                    myImageMove.setScaleX(1f);
                }
            }



        @Override
        public void onAnimationCancel(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animator animation) {
            // TODO Auto-generated method stub

        }
    }
}
