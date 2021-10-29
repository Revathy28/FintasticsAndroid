package com.triton.fintastics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.triton.fintastics.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity {
    private String TAG = "ChangePasswordActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        ImageView img_back = include_header.findViewById(R.id.img_back);
        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.change_password));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashoardActivity.class));
        finish();
    }
}