package com.triton.fintastics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.triton.fintastics.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_usrname)
    EditText edt_usrname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email)
    EditText edt_email;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_passwd)
    EditText edt_passwd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_login)
    Button btn_login;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Log.w("Oncreate", TAG);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);
    }

    public void gotoDashboard(View view) {
        Intent i = new Intent(SignUpActivity.this, DashoardActivity.class);
        startActivity(i);
    }
}