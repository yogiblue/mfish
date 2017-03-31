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

    ImageView myBody = null;
    ImageView myEyes = null;
    boolean goingRight=true;
    boolean blink=false;
    AnimationDrawable blinkAnimation;
    Resources res;


    public void setBodyImage(ImageView bodyImage)
    {
        myBody = bodyImage;
    }

    public void setEyeImage(ImageView eyeImage){
        myEyes = eyeImage;
        myEyes.setVisibility(View.INVISIBLE);
    }

    public void initialiseImage(Resources resIn)
    {
        res = resIn;
        Random r = new Random();

        Drawable[] layers = new Drawable[2];
        layers[0] = res.getDrawable(R.drawable.fishbody1);
        layers[1] = res.getDrawable(R.drawable.fisheyes1);

        LayerDrawable layerDrawable = new LayerDrawable(layers);
        myBody.setImageDrawable(layerDrawable);

        //myEyes.setBackgroundResource(R.drawable.fishblink);
        //blinkAnimation = (AnimationDrawable) myEyes.getBackground();

        ImageView myImage = myBody;
        myImage.setAlpha(0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(myImage, "alpha", 0f, 1f);
        fadeIn.setDuration(10000);
        fadeIn.start();

        ImageView myImageMove = myBody;
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

                mover1.setDuration(10000);
                animatorSet.play(mover1);
                animatorSet.start();
            } else {
                myImageMove.setScaleX(1f);
                blinkThing();
            }
        }

        public void blinkThing()
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(blink==false){

                        Drawable[] layers = new Drawable[2];
                        layers[0] = res.getDrawable(R.drawable.fishbody1);
                        layers[1] = res.getDrawable(R.drawable.fisheyesblink);

                        LayerDrawable layerDrawable = new LayerDrawable(layers);
                        myBody.setImageDrawable(layerDrawable);
                        myEyes.setImageDrawable(res.getDrawable(R.drawable.fisheyesblink));
                        blinkThing();
                        blink=true;

                    }else{
                        myEyes.setImageDrawable(res.getDrawable(R.drawable.fisheyes1));
                        Drawable[] layers = new Drawable[2];
                        layers[0] = res.getDrawable(R.drawable.fishbody1);
                        layers[1] = res.getDrawable(R.drawable.fisheyes1);

                        LayerDrawable layerDrawable = new LayerDrawable(layers);
                        myBody.setImageDrawable(layerDrawable);                    }
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
