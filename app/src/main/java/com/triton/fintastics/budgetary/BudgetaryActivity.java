package com.triton.fintastics.budgetary;

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

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetaryActivity extends AppCompatActivity {

    private String TAG = "BudgetaryActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgetary);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        ImageView img_back = include_header.findViewById(R.id.img_back);
        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.budgetary));

        StackedBarChart mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);

        StackedBarModel s1 = new StackedBarModel("J");

        s1.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s1.addBar(new BarModel(2.3f, 0xFF56B7F1));
        s1.addBar(new BarModel(2.3f, 0xFFCDA67F));

        StackedBarModel s2 = new StackedBarModel("F");
        s2.addBar(new BarModel(1.1f, 0xFF63CBB0));
        s2.addBar(new BarModel(2.7f, 0xFF56B7F1));
        s2.addBar(new BarModel(0.7f, 0xFFCDA67F));

        StackedBarModel s3 = new StackedBarModel("M");

        s3.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s3.addBar(new BarModel(2.f, 0xFF56B7F1));
        s3.addBar(new BarModel(3.3f, 0xFFCDA67F));

        StackedBarModel s4 = new StackedBarModel("A");
        s4.addBar(new BarModel(1.f, 0xFF63CBB0));
        s4.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s4.addBar(new BarModel(2.1f, 0xFFCDA67F));

        StackedBarModel s5 = new StackedBarModel("M");
        s5.addBar(new BarModel(1.f, 0xFF63CBB0));
        s5.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s5.addBar(new BarModel(2.1f, 0xFFCDA67F));

        StackedBarModel s6 = new StackedBarModel("J");
        s6.addBar(new BarModel(1.f, 0xFF63CBB0));
        s6.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s6.addBar(new BarModel(2.1f, 0xFFCDA67F));

        StackedBarModel s7 = new StackedBarModel("J");
        s7.addBar(new BarModel(1.f, 0xFF63CBB0));
        s7.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s7.addBar(new BarModel(2.1f, 0xFFCDA67F));

        StackedBarModel s8 = new StackedBarModel("A");
        s8.addBar(new BarModel(1.f, 0xFF63CBB0));
        s8.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s8.addBar(new BarModel(2.1f, 0xFFCDA67F));

        StackedBarModel s9 = new StackedBarModel("S");
        s9.addBar(new BarModel(1.f, 0xFF63CBB0));
        s9.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s9.addBar(new BarModel(2.1f, 0xFFCDA67F));

        StackedBarModel s10 = new StackedBarModel("O");
        s10.addBar(new BarModel(1.f, 0xFF63CBB0));
        s10.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s10.addBar(new BarModel(2.1f, 0xFFCDA67F));

        StackedBarModel s11 = new StackedBarModel("N");
        s11.addBar(new BarModel(1.f, 0xFF63CBB0));
        s11.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s11.addBar(new BarModel(2.1f, 0xFFCDA67F));

        StackedBarModel s12 = new StackedBarModel("D");
        s12.addBar(new BarModel(1.f, 0xFF63CBB0));
        s12.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s12.addBar(new BarModel(2.1f, 0xFFCDA67F));



        mStackedBarChart.addBar(s1);
        mStackedBarChart.addBar(s2);
        mStackedBarChart.addBar(s3);
        mStackedBarChart.addBar(s4);
        mStackedBarChart.addBar(s5);
        mStackedBarChart.addBar(s6);
        mStackedBarChart.addBar(s7);
        mStackedBarChart.addBar(s8);
        mStackedBarChart.addBar(s9);
        mStackedBarChart.addBar(s10);
        mStackedBarChart.addBar(s11);
        mStackedBarChart.addBar(s12);


        //mStackedBarChart.startAnimation();


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