package com.muwuprojects.mfish;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
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

    public void initialiseImage(Resources resIn)
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
        fadeIn.setDuration(1000);
        fadeIn.start();

        ImageView myImageMove = myBody;
        int x1 = (int) myImageMove.getX();
        int y1 = (int) myImageMove.getY();
        int x2 = r.nextInt(600);

        ObjectAnimator mover1 = ObjectAnimator.ofFloat(myImageMove, "x", x1, x2);

        Animator.AnimatorListener myAnimationListener = new AnimationAdaptor();
        AnimatorSet animatorSet = new AnimatorSet();

        mover1.setDuration(1000);
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

            if (movementMode==MOVEMENT_LEFT_RIGHT) {
                ImageView myImageMove = myBody;
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

                    mover1.setDuration(1000);
                    animatorSet.play(mover1);
                    animatorSet.start();
                } else {
                    myImageMove.setScaleX(1f);
                    blinkThing();
                }
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
