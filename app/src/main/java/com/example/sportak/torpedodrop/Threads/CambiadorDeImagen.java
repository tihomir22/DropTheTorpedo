package com.example.sportak.torpedodrop.Threads;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class CambiadorDeImagen extends Thread {

    Context context;
    View view;
    Drawable[] drawable;

    public CambiadorDeImagen(Context context, View view, Drawable[] drawable) {
        this.context = context;
        this.view = view;
        this.drawable = drawable;
    }

    @Override
    public void run(){

        while(true){
            this.ImageViewAnimatedChange(this.context,this.view,this.drawable);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void ImageViewAnimatedChange(Context c, final View v, final Drawable[] array) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {

                v.setBackground(array[(int) Math.round(Math.random()*(array.length-1))]);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }
}
