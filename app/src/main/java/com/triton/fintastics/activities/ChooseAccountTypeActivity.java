package com.triton.fintastics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.triton.fintastics.R;

public class ChooseAccountTypeActivity extends AppCompatActivity {

    private static final String TAG = "ChooseAccountTypeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account_type);

        Log.w("Oncreate", TAG);

    }

    public void personal(View view){
        Intent i = new Intent(ChooseAccountTypeActivity.this,
                LoginActivity.class);
       i.putExtra("accounttype","Personal");
       startActivity(i);
    }

    public void family(View view){
        Intent i = new Intent(ChooseAccountTypeActivity.this,
                LoginActivity.class);
        i.putExtra("accounttype","Family");
        startActivity(i);
    }
}