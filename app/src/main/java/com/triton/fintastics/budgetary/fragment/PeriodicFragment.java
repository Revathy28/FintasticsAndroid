package com.triton.fintastics.budgetary.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.triton.fintastics.R;
import com.triton.fintastics.budgetary.AddBudgetaryActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PeriodicFragment#} factory method to
 * create an instance of this fragment.
 */
public class PeriodicFragment extends Fragment implements View.OnClickListener {


    View view;

    private static final String TAG = "PeriodicFragment";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_periodic_budgetary)
    RecyclerView rv_periodic_budgetary;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_no_budget)
    LinearLayout ll_no_budget;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fab_add_budgetary)
    FloatingActionButton fab_add_budgetary;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_weekly)
    RelativeLayout rl_weekly;
    String Previous7days,currentdate,currentDate,start_date,end_date;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_monthly)
    RelativeLayout rl_monthly;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_annually)
    RelativeLayout rl_annually;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_weekly)
    TextView txt_lbl_weekly;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_monthly)
    TextView txt_lbl_monthly;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_annually)
    TextView txt_lbl_annually;

    public PeriodicFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_periodic, container, false);

        Log.w(TAG,"onCreateView-->");

        ButterKnife.bind(this,view);

        fab_add_budgetary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AddBudgetaryActivity.class);

                intent.putExtra("budgetary_mode","Periodic");

                startActivity(intent);
            }
        });

        rl_weekly.setOnClickListener(this);
        rl_monthly.setOnClickListener(this);
        rl_annually.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.rl_weekly:
                txt_lbl_weekly.setTextColor(getResources().getColor(R.color.white));
                txt_lbl_monthly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_annually.setTextColor(getResources().getColor(R.color.txt_black));
                rl_weekly.setBackgroundResource(R.drawable.rounded_corner_bg_color);
                rl_monthly.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_annually.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    Date myDate = simpleDateFormat.parse(currentdate);
//                    Calendar calendar = Calendar.getInstance();
//                    if (myDate != null) {
//                        calendar.setTime(myDate);
//                    }
//                    calendar.add(Calendar.DAY_OF_YEAR, -7);
//                    Date newDate = calendar.getTime();
//                    Previous7days = simpleDateFormat.format(newDate);
//
//                    @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
//                    String inputDateStr = Previous7days;
//                    Date date = inputFormat.parse(inputDateStr);
//                    String outputDateStr = null;
//                    if (date != null) {
//                        outputDateStr = outputFormat.format(date);
//                    }
//                 //   txt_date.setText(outputDateStr+" to "+currentDate);
//                 //   txt_transcperiod.setText("Weekly");
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                start_date = currentdate;
//                end_date = Previous7days;
//
//              //  dashboardDataResponseCall(end_date,start_date);
//                Log.w(TAG,"rl_weekly start_date : "+start_date+" Previous7days : "+Previous7days);
                break;

            case R.id.rl_monthly:
              //  txt_lbl_daily.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_weekly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_monthly.setTextColor(getResources().getColor(R.color.white));
                txt_lbl_annually.setTextColor(getResources().getColor(R.color.txt_black));
               // rl_daily.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_weekly.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_monthly.setBackgroundResource(R.drawable.rounded_corner_bg_color);
                rl_annually.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int month1 =(month + 1);
                String strMonth = null;
                if(month1 == 9 || month1 <9){
                    strMonth = "0"+month1;
                    Log.w(TAG,"Selected month1-->"+strMonth);
                }else{
                    strMonth = String.valueOf(month1);
                }
                String monthlystartdate = year+"-"+strMonth+"-"+"01";
                String monthlyenddate = year+"-"+strMonth+"-"+"31";

                Calendar cal=Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                String month_name = month_date.format(cal.getTime());

               // txt_date.setText(month_name);
               // txt_transcperiod.setText("Montly");

                Log.w(TAG,"rl_monthly : "+"monthlystartdate : "+monthlystartdate+" monthlyenddate : "+monthlyenddate);

                start_date = monthlystartdate;
                end_date = monthlyenddate;
              //  dashboardDataResponseCall(start_date,end_date);

                break;

            case R.id.rl_annually:
              //  txt_lbl_daily.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_weekly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_monthly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_annually.setTextColor(getResources().getColor(R.color.white));
              //  rl_daily.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_weekly.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_monthly.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_annually.setBackgroundResource(R.drawable.rounded_corner_bg_color);
                int yearannually = Calendar.getInstance().get(Calendar.YEAR);
                String annuallystartdate = yearannually+"-"+"01"+"-"+"01";
                String annuallyenddate = yearannually+"-"+"12"+"-"+"31";

                Log.w(TAG,"rl_annually : "+"annuallystartdate : "+annuallystartdate+" annuallyenddate : "+annuallyenddate);

               // txt_date.setText(yearannually+"");
              //  txt_transcperiod.setText("Annually");
                start_date = annuallystartdate;
                end_date = annuallyenddate;

               // dashboardDataResponseCall(start_date,end_date);

                break;


        }
    }
}