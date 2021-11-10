package com.triton.fintastics.incomereport;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.activities.DashoardActivity;
import com.triton.fintastics.activities.SignUpActivity;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.DashboardDataRequest;
import com.triton.fintastics.requestpojo.ReportDataRequest;
import com.triton.fintastics.responsepojo.DashboardDataResponse;
import com.triton.fintastics.responsepojo.IncomeReportResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.ConnectionDetector;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomeReportActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "IncomeReportActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rb_alldays)
    RadioButton rb_alldays;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rb_ondate)
    RadioButton rb_ondate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_ondate)
    ImageView img_ondate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rb_fromdate)
    RadioButton rb_fromdate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_fromdate)
    ImageView img_fromdate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_todate)
    ImageView img_todate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_ondate)
    TextView txt_ondate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_fromdate)
    TextView txt_fromdate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_todate)
    TextView txt_todate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_submit)
    RelativeLayout rl_submit;

    ImageView img_back;


    private int ON_DATE_PICKER_ID = 0;
    private int year, month, day;
    private String SelectedOnDate;


    private int FROM_DATE_PICKER_ID = 1;
    private int fromyear, frommonth, fromday;
    private String SelectedFromDate;

    private int TO_DATE_PICKER_ID = 2;
    private int toyear, tomonth, today;
    private String SelectedToDate;
    private Dialog alertDialog;
    private String user_id;
    private String end_date = "";
    private String start_date = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_report);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");



        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        user_id = user.get(SessionManager.KEY_ID);

        img_back = include_header.findViewById(R.id.img_back);
        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.income_report));



        img_back.setOnClickListener(this);
        rb_alldays.setOnClickListener(this);
        rb_alldays.setOnClickListener(this);
        rb_ondate.setOnClickListener(this);
        rb_fromdate.setOnClickListener(this);
        img_ondate.setOnClickListener(this);
        img_fromdate.setOnClickListener(this);
        img_fromdate.setOnClickListener(this);
        img_todate.setOnClickListener(this);
        rl_submit.setOnClickListener(this);


        img_ondate.setVisibility(View.INVISIBLE);
        img_fromdate.setVisibility(View.INVISIBLE);
        img_todate.setVisibility(View.INVISIBLE);





    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashoardActivity.class));
        finish();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.rb_alldays:
                start_date = "";
                end_date = "";
                clearRadioChecked();
                txt_ondate.setText("");
                txt_fromdate.setText("");
                txt_todate.setText("");
                rb_alldays.setChecked(true);
                img_ondate.setVisibility(View.INVISIBLE);
                img_fromdate.setVisibility(View.INVISIBLE);
                img_todate.setVisibility(View.INVISIBLE);
                break;
            case R.id.rb_ondate:
                clearRadioChecked();
                txt_fromdate.setText("");
                txt_todate.setText("");
                rb_ondate.setChecked(true);
                img_ondate.setVisibility(View.VISIBLE);
                img_fromdate.setVisibility(View.INVISIBLE);
                img_todate.setVisibility(View.INVISIBLE);
                break;
            case R.id.rb_fromdate:
                txt_ondate.setText("");
                clearRadioChecked();
                rb_fromdate.setChecked(true);
                img_ondate.setVisibility(View.INVISIBLE);
                img_fromdate.setVisibility(View.VISIBLE);
                img_todate.setVisibility(View.VISIBLE);
                break;

            case R.id.img_ondate:
                gotoOnDate();
                break;

            case R.id.img_fromdate:
                gotoFromDate();
                break;

            case R.id.img_todate:
                gotoToDate();
                break;

                case R.id.rl_submit:
                    Validator();
                break;
        }

    }




    public void clearRadioChecked() {
        rb_alldays.setChecked(false);
        rb_ondate.setChecked(false);
        rb_fromdate.setChecked(false);
    }

    private void gotoOnDate() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        showDialog(ON_DATE_PICKER_ID);
    }
    private void gotoFromDate() {
        final Calendar c = Calendar.getInstance();
        fromyear = c.get(Calendar.YEAR);
        frommonth = c.get(Calendar.MONTH);
        fromday = c.get(Calendar.DAY_OF_MONTH);
        showDialog(FROM_DATE_PICKER_ID);
    }
    private void gotoToDate() {
        final Calendar c = Calendar.getInstance();
        toyear = c.get(Calendar.YEAR);
        tomonth = c.get(Calendar.MONTH);
        today = c.get(Calendar.DAY_OF_MONTH);
        showDialog(TO_DATE_PICKER_ID);
    }

    @SuppressLint("LogNotTimber")
    @Override
    protected Dialog onCreateDialog(int id) {
        Log.w(TAG,"onCreateDialog id : "+id);
        if (id == ON_DATE_PICKER_ID) {
            DatePickerDialog dialog = new DatePickerDialog(this, pickerListener, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dialog;
        }else if (id == FROM_DATE_PICKER_ID) {
            DatePickerDialog dialog = new DatePickerDialog(this, pickerFromListener, fromyear, frommonth, fromday);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dialog;
        }else if (id == TO_DATE_PICKER_ID) {
            DatePickerDialog dialog = new DatePickerDialog(this, pickerToListener, toyear, tomonth, today);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dialog;
        }
        return null;
    }


    private final DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @SuppressLint("LogNotTimber")
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;



            String strdayOfMonth;
            String strMonth;
            int month1 =(month + 1);
            if(day == 9 || day <9){
                strdayOfMonth = "0"+day;
                Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
            }else{
                strdayOfMonth = String.valueOf(day);
            }

            if(month1 == 9 || month1 <9){
                strMonth = "0"+month1;
                Log.w(TAG,"Selected month1-->"+strMonth);
            }else{
                strMonth = String.valueOf(month1);
            }

            SelectedOnDate = strdayOfMonth + "-" + strMonth + "-" + year;
            start_date = year + "-" + strMonth + "-" + strdayOfMonth;
            end_date = year + "-" + strMonth + "-" + strdayOfMonth;

            // Show selected date
            txt_ondate.setText(SelectedOnDate);


        }
    };
    private final DatePickerDialog.OnDateSetListener pickerFromListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @SuppressLint("LogNotTimber")
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            fromyear  = selectedYear;
            frommonth = selectedMonth;
            fromday   = selectedDay;



            String strdayOfMonth;
            String strMonth;
            int month1 =(frommonth + 1);
            if(fromday == 9 || fromday <9){
                strdayOfMonth = "0"+fromday;
                Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
            }else{
                strdayOfMonth = String.valueOf(fromday);
            }

            if(month1 == 9 || month1 <9){
                strMonth = "0"+month1;
                Log.w(TAG,"Selected month1-->"+strMonth);
            }else{
                strMonth = String.valueOf(month1);
            }

            SelectedFromDate = strdayOfMonth + "-" + strMonth + "-" + fromyear;
            Log.w(TAG,"SelectedFromDate : "+SelectedFromDate);
            start_date = fromyear + "-" + strMonth + "-" + strdayOfMonth;

            // Show selected date
            txt_fromdate.setText(SelectedFromDate);


        }
    };
    private final DatePickerDialog.OnDateSetListener pickerToListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @SuppressLint("LogNotTimber")
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            toyear  = selectedYear;
            tomonth = selectedMonth;
            today   = selectedDay;



            String strdayOfMonth;
            String strMonth;
            int month1 =(tomonth + 1);
            if(today == 9 || today <9){
                strdayOfMonth = "0"+today;
                Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
            }else{
                strdayOfMonth = String.valueOf(today);
            }

            if(month1 == 9 || month1 <9){
                strMonth = "0"+month1;
                Log.w(TAG,"Selected month1-->"+strMonth);
            }else{
                strMonth = String.valueOf(month1);
            }

            SelectedToDate = strdayOfMonth + "-" + strMonth + "-" + toyear;
            Log.w(TAG,"SelectedToDate : "+SelectedToDate);
            end_date = toyear + "-" + strMonth + "-" + strdayOfMonth;


            dateValidation();




        }
    };

    private void dateValidation() {
        try{
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); //edit here
            Date date1 = null,date2 = null;
            if(SelectedFromDate != null && SelectedToDate != null) {
                 date1 = sdf.parse(SelectedFromDate);
                 date2 = sdf.parse(SelectedToDate);
            }

            if(date1 != null && date2 != null){
                if(date1.compareTo(date2)>0){
                   Log.w(TAG,"Date1 is after Date2");
                    showErrorLoading("To date should be greater than From date");
                }else if(date1.compareTo(date2)<0){
                    Log.w(TAG,"Date1 is before Date2");
                    // Show selected date
                    txt_todate.setText(SelectedToDate);
                }else if(date1.compareTo(date2)==0){
                    Log.w(TAG,"Date1 is equal to Date2");
                    // Show selected date
                    showErrorLoading("To date should be greater than From date");
                }else{
                    Log.w(TAG,"How to get here?");
                }
            }





        }catch(ParseException ex){
            ex.printStackTrace();
        }
    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IncomeReportActivity.this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

    public void Validator() {
        boolean can_proceed = true;
        if(rb_ondate.isChecked()){
            if (txt_ondate.getText().toString().trim().equals("")) {
                showErrorLoading("Please select on date");
                can_proceed = false;
            }
        }else if(rb_fromdate.isChecked()){
            if (txt_fromdate.getText().toString().trim().equals("")) {
                showErrorLoading("Please select from date");
                can_proceed = false;
            }
            else if (txt_todate.getText().toString().trim().equals("")) {
                showErrorLoading("Please select to date");
                can_proceed = false;
            }
        }


        if (can_proceed) {
            gotoShowData();
        }




    }

    private void gotoShowData() {
        Intent intent = new Intent(getApplicationContext(),IncomeReportShowDataActivity.class);
        intent.putExtra("user_id",user_id);
        intent.putExtra("start_date",start_date);
        intent.putExtra("end_date",end_date);
        startActivity(intent);
    }




}