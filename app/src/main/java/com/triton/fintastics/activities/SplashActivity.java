package com.triton.fintastics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.triton.fintastics.R;
import com.triton.fintastics.sessionmanager.SessionManager;

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

        SessionManager session = new SessionManager(getApplicationContext());
        boolean islogedin = session.isLoggedIn();
        Log.w(TAG,"islogedin-->"+islogedin);




        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.



        new Handler().postDelayed(() -> {
            if(islogedin){
                Intent i = new Intent(SplashActivity.this, DashoardActivity.class);
                startActivity(i);
                finish();
            }else{
                Intent i = new Intent(SplashActivity.this, ChooseAccountTypeActivity.class);
                startActivity(i);
                finish();
            }


        }, SPLASH_SCREEN_TIME_OUT);

    }
}