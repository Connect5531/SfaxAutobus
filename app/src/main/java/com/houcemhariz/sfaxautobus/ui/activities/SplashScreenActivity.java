package com.houcemhariz.sfaxautobus.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.houcemhariz.sfaxautobus.R;
import com.houcemhariz.sfaxautobus.persistence.preferences.AppPreferences;

public class SplashScreenActivity extends AppCompatActivity {

    AppPreferences appPrefs;

    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            SplashScreenActivity.this.startActivity(i);
            SplashScreenActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        appPrefs = new AppPreferences(this);
        if (appPrefs.getFirst_run()){
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);


            try {
                setContentView(R.layout.activity_splash_screen);
                Thread.sleep(1000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            SplashScreenActivity.this.startActivity(i);
            SplashScreenActivity.this.finish();
        }else{
            setContentView(R.layout.activity_splash_screen);
            appPrefs.setFirst_run(true);
            handler.sendEmptyMessageDelayed(0, 4000);
        }
    }
}
