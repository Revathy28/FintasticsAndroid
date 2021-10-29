package com.triton.fintastics.familyaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.triton.fintastics.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateProfileActivity extends AppCompatActivity {

    private String TAG = "CreateProfileActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.create_profile));
    }

    public void gotoSharedLink(View view) {


        Intent i=new Intent(CreateProfileActivity.this,
                SharelinkFamilyMembersActivity.class);
        //Intent is used to switch from one activity to another.

        startActivity(i);


    }
}