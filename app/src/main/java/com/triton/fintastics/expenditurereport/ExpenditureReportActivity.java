package com.triton.fintastics.expenditurereport;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.triton.fintastics.R;
import com.triton.fintastics.activities.DashoardActivity;
import com.triton.fintastics.incomereport.IncomeReportActivity;
import com.triton.fintastics.incomereport.IncomeReportShowDataActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenditureReportActivity extends AppCompatActivity {
    private String TAG = "ExpenditureReportActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_report);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        ImageView img_back = include_header.findViewById(R.id.img_back);
        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.expenditure_report));

        img_back.setOnClickListener(view -> onBackPressed());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashoardActivity.class));
        finish();
    }

    public void gotoExpenditureReportShow(View view) {

        Intent i=new Intent(ExpenditureReportActivity.this,
                ExpenditureReportShowDataActivity.class);
        //Intent is used to switch from one activity to another.

        startActivity(i);
        //invoke the SecondActivity.

    }
}