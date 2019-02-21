package com.example.sportak.torpedodrop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    ConstraintLayout miLayout;
    AnimationDrawable animationDrawable;
    ImageView logo;
    SharedPreferences pref;
    ImageButton btnSpain,btnUk,btnBg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        miLayout=findViewById(R.id.layout_animado);
        this.btnBg=findViewById(R.id.btn_bg);
        this.btnSpain=findViewById(R.id.btn_spain);
        this.btnUk=findViewById(R.id.btn_uk);


        animationDrawable= (AnimationDrawable) miLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        this.btnSpain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarPreferencias("SP");
            }
        });

        this.btnUk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarPreferencias("UK");
            }
        });

        this.btnBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarPreferencias("BG");
            }
        });


        ImageView imageView = (ImageView) findViewById(R.id.logo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        });

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        ThreadBounce thread=new ThreadBounce(imageView,myAnim);
        thread.start();

    }

    public void modificarPreferencias(String pais){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("pais",pais);
        editor.commit();
        this.updateViews();

    }

    private void updateViews() {

        //Refresco de datos
        setTitle(ResourcesLocale.getResoruces(this).getString(R.string.app_name));
        ((TextView)findViewById(R.id.textView2)).setText(ResourcesLocale.getResoruces(this).getString(R.string.click_it));
        ((TextView)findViewById(R.id.lenguajes)).setText(ResourcesLocale.getResoruces(this).getString(R.string.avalaible_lang));


    }

    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.updateViews();
    }
}










class ThreadBounce extends Thread{
    ImageView image;
    Animation ani;

    public ThreadBounce(ImageView image, Animation ani) {
        this.image = image;
        this.ani = ani;
    }

    @Override
    public void run(){
        while(true){
            image.startAnimation(ani);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



class MyBounceInterpolator implements android.view.animation.Interpolator {
    private double mAmplitude = 1;
    private double mFrequency = 10;

    MyBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}

