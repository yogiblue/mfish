package com.muwuprojects.mfish;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by mumu on 31/03/2017.
 */

public class FishThing {

    //different movement modes
    public static final int MOVEMENT_LEFT_RIGHT = 0;
    public static final int MOVEMENT_DESCENDING = 1;
    public static final int MOVEMENT_SCALE_AND_MOVE = 2;
    public static final int MOVEMENT_SQUASH_AND_MOVE = 3;
    public static final int MOVEMENT_ZOOM_IN = 4;
    public static final int MOVEMENT_ZOOM_OUT = 5;

    public static final int duration = 1000;

    ImageView myBody = null;
    ImageView myEyes = null;
    boolean goingRight=true;
    boolean blink=false;
    AnimationDrawable blinkAnimation;
    Resources res;
    int whichFish;
    int movementMode = MOVEMENT_LEFT_RIGHT;


    public void setBodyImage(ImageView bodyImage)
    {
        myBody = bodyImage;
    }

    public void setEyeImage(ImageView eyeImage){
        myEyes = eyeImage;
        myEyes.setVisibility(View.INVISIBLE);
    }
    
    public void buildFish()
    {
        // build the fish
        Drawable[] layers = new Drawable[4];
        if(whichFish==0)
            layers[0] = res.getDrawable(R.drawable.fishbody1);
        else if(whichFish==1)
            layers[0] = res.getDrawable(R.drawable.orcabody);
        else if(whichFish==2)
            layers[0] = res.getDrawable(R.drawable.clownbody);

        if(blink==true)
            layers[1] = res.getDrawable(R.drawable.fisheyesblink);
        else
            layers[1] = res.getDrawable(R.drawable.fisheyes1);
        layers[2] = res.getDrawable(R.drawable.fishmouth1);
        layers[3] = res.getDrawable(R.drawable.fishempty);

        LayerDrawable layerDrawable = new LayerDrawable(layers);
        myBody.setImageDrawable(layerDrawable);
        
    }

    public void initialiseImage(Resources resIn, int width, int height)
    {
        Random r = new Random();
        res = resIn;

        whichFish = r.nextInt(3);

        buildFish();
        
        //myEyes.setBackgroundResource(R.drawable.fishblink);
        //blinkAnimation = (AnimationDrawable) myEyes.getBackground();

        ImageView myImage = myBody;
        myImage.setAlpha(0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(myImage, "alpha", 0f, 1f);
        fadeIn.setDuration(duration);
        fadeIn.start();
        
        ImageView myImageMove = myBody;

        myBody.setOnTouchListener(new MyTouchListener());


        int whichMovement = r.nextInt(3);
        
        if(whichMovement==0)
        {
            movementMode=MOVEMENT_LEFT_RIGHT;
        }
        else if(whichMovement==1)
        {
            movementMode=MOVEMENT_DESCENDING;
        }
        else if(whichMovement==2)
        {
            movementMode=MOVEMENT_ZOOM_OUT;
        }
        
        if (movementMode==MOVEMENT_LEFT_RIGHT) {
            int x1 = (int) myImageMove.getX();
            int x2 = r.nextInt(600);

            ObjectAnimator mover1 = ObjectAnimator.ofFloat(myImageMove, "x", x1, x2);

            Animator.AnimatorListener myAnimationListener = new AnimationAdaptor();
            AnimatorSet animatorSet = new AnimatorSet();

            mover1.setDuration(duration);
            mover1.addListener(myAnimationListener);
            animatorSet.play(mover1);
            animatorSet.start();
        }
        else if(movementMode==MOVEMENT_DESCENDING)
        {
            int y1 = (int) myImageMove.getY();
            int y2 = (int) 0;

            // a bit of a fudge
            ObjectAnimator mover1 = ObjectAnimator.ofFloat(myImageMove, "y", -1000, -1000);

            Animator.AnimatorListener myAnimationListener = new AnimationAdaptor();
            AnimatorSet animatorSet = new AnimatorSet();

            mover1.setDuration(10);
            mover1.addListener(myAnimationListener);
            animatorSet.play(mover1);
            animatorSet.start();
        }
        else if(movementMode==MOVEMENT_ZOOM_OUT)
        {
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(myImageMove, "scaleX", 0.5f);
            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(myImageMove, "scaleY", 0.5f);
            scaleDownX.setDuration(duration);
            scaleDownY.setDuration(duration);

            Animator.AnimatorListener myAnimationListener = new AnimationAdaptor();
            AnimatorSet scaleDown = new AnimatorSet();
            scaleDown.addListener(myAnimationListener);
            scaleDown.play(scaleDownX).with(scaleDownY);
            scaleDown.start();
        }

    }

    public void blinkThing()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(blink==false){

                    blink=true;
                    buildFish();
                    blinkThing();

                }
                else
                {
                    blink=false;
                    buildFish();
                }
            }
        },500);
    }


    public class AnimationAdaptor implements Animator.AnimatorListener {

        AnimationAdaptor() {

        }

        public void onAnimationEnd(Animator animation) {
            //ObjectAnimator fadeIn = ObjectAnimator.ofFloat(myCard, "alpha", 0f, 1f);
            //fadeIn.setDuration(2000);
            //fadeIn.start();
            Random r = new Random();
            ImageView myImageMove = myBody;

            if (movementMode==MOVEMENT_LEFT_RIGHT) {
                if (goingRight == true) {
                    myImageMove.setScaleX(-1f);
                    goingRight = false;
                    int x1 = (int) myImageMove.getX();
                    int y1 = (int) myImageMove.getY();
                    int x2 = r.nextInt(50);

                    ObjectAnimator mover1 = ObjectAnimator.ofFloat(myImageMove, "x", x1, x2);
                    AnimatorSet animatorSet = new AnimatorSet();
                    Animator.AnimatorListener myAnimationListener = new AnimationAdaptor();
                    mover1.addListener(myAnimationListener);

                    mover1.setDuration(duration);
                    animatorSet.play(mover1);
                    animatorSet.start();
                } else {
                    myImageMove.setScaleX(1f);
                    blinkThing();
                }
            }
            else if (movementMode==MOVEMENT_DESCENDING)
            {
                blinkThing();
                int y1 = (int) myImageMove.getY();
                int y2 = (int) myEyes.getY();

                ObjectAnimator mover1 = ObjectAnimator.ofFloat(myImageMove, "y", y1, y2);

                AnimatorSet animatorSet = new AnimatorSet();

                mover1.setDuration(duration);
                animatorSet.play(mover1);
                animatorSet.start();
            }
            else if(movementMode==MOVEMENT_ZOOM_OUT)
            {

                //then move right off the screen and zoom back in?
                blinkThing();
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

    public class MyTouchListener implements View.OnTouchListener {

        public MyTouchListener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView myImageMove = (ImageView) v;

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    int y1 = (int) myImageMove.getY();
                    int y2 = (int) myEyes.getY();

                    ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(myImageMove, "scaleY", 0.25f);

                    AnimatorSet animatorSet = new AnimatorSet();

                    scaleDownY.setDuration(duration);
                    animatorSet.play(scaleDownY);
                    animatorSet.start();


                    blinkThing();
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    }
