package com.triton.fintastics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.triton.fintastics.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=6000;
    private static final String TAG = "SplashActivity";

    Context context = SplashActivity.this;

  /*  After completion of 2000 ms, the next activity will get started.*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.w("Oncreate", TAG);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.


        new Handler().postDelayed(() -> {
            Intent i=new Intent(SplashActivity.this,
                    ChooseAccountTypeActivity.class);
            //Intent is used to switch from one activity to another.

            startActivity(i);
            //invoke the SecondActivity.

            finish();
            //the current activity will get finished.
        }, SPLASH_SCREEN_TIME_OUT);

    }
}