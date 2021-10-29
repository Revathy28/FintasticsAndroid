package com.triton.fintastics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.triton.fintastics.R;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";

    Context context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.w("Oncreate", TAG);
    }

    public void gotosignup(View view){

        Intent i=new Intent(LoginActivity.this,
                SignUpActivity.class);
        //Intent is used to switch from one activity to another.

        startActivity(i);
        //invoke the SecondActivity.
    }

    public void gotoDashboard(View view) {

        Intent i=new Intent(LoginActivity.this,
                DashoardActivity.class);
        //Intent is used to switch from one activity to another.

        startActivity(i);
        //invoke the SecondActivity.
    }
}