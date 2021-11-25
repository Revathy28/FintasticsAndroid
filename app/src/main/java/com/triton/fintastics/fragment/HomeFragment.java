package com.triton.fintastics.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.activities.ChangePasswordActivity;
import com.triton.fintastics.adapter.TransactionListAdapter;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.ChangePasswordRequest;
import com.triton.fintastics.requestpojo.DashboardDataRequest;
import com.triton.fintastics.responsepojo.DashboardDataResponse;
import com.triton.fintastics.responsepojo.PaymentTypeListResponse;
import com.triton.fintastics.responsepojo.SignupResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.transaction.AddTransactionActivity;
import com.triton.fintastics.utils.ConnectionDetector;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;

    private static final String TAG = "HomeFragment";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_expense)
    LinearLayout ll_expense;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_income)
    LinearLayout ll_income;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_expensetab)
    TextView txt_lbl_expensetab;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_incometab)
    TextView txt_lbl_incometab;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_transacation_type)
    Spinner spr_transacation_type;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_transaction)
    RecyclerView rv_transaction;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_latest_transaction)
    TextView txt_latest_transaction;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_daily)
    RelativeLayout rl_daily;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_weekly)
    RelativeLayout rl_weekly;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_monthly)
    RelativeLayout rl_monthly;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_annually)
    RelativeLayout rl_annually;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_daily)
    TextView txt_lbl_daily;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_weekly)
    TextView txt_lbl_weekly;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_monthly)
    TextView txt_lbl_monthly;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_annually)
    TextView txt_lbl_annually;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_income)
    TextView txt_income;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_expense)
    TextView txt_expense;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_balance)
    TextView txt_balance;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_date)
    TextView txt_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_transcperiod)
    TextView txt_transcperiod;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_total_expense)
    LinearLayout ll_total_expense;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_total_income)
    LinearLayout ll_total_income;

    boolean showincome = true;
    private Context mContext;
    private List<PaymentTypeListResponse.DataBean> paymenttypeList;

    HashMap<String,String> hashMap_PaymentTypevalue = new HashMap<>();
    private String strTransactionType;
    private Object strTransactionTypeId;

    private String transaction_type = "";
    private String transaction_way = "Credit";
    private String user_id;
    private String start_date;
    private String end_date;
    private Dialog alertDialog;
    private String currentdate = "";
    private String Previous7days;
    private String currentDate;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.w(TAG,"onCreateView-->");
        ButterKnife.bind(this,view);

        mContext = getActivity();
        avi_indicator.setVisibility(View.GONE);
        ll_total_expense.setVisibility(View.GONE);


        SessionManager sessionManager = new SessionManager(mContext);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        user_id = user.get(SessionManager.KEY_ID);
        String account_type = user.get(SessionManager.KEY_ACCOUNT_TYPE);
        String roll_type = user.get(SessionManager.KEY_ROLL_TYPE);
        Log.w(TAG,"userid-->"+user_id+" account_type : "+account_type+" roll_type : "+roll_type);


        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        String currentDateandTime12hrs = simpleDateFormat.format(new Date());
        String currenttime = currentDateandTime12hrs.substring(currentDateandTime12hrs.indexOf(' ') + 1);
        currentdate =  currentDateandTime12hrs.substring(0, currentDateandTime12hrs.indexOf(' '));
        start_date = currentdate;
        end_date = currentdate;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
         currentDate = simpleDateFormat1.format(new Date());
        txt_date.setText(currentDate);
        txt_transcperiod.setText("Daily");



        if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
            paymentTypeListResponseCall();
        }



        spr_transacation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.text_black));
                strTransactionType = spr_transacation_type.getSelectedItem().toString();
                strTransactionTypeId = hashMap_PaymentTypevalue.get(strTransactionType);

                Log.w(TAG,"strTransactionType : "+strTransactionType+" strTransactionTypeId :"+strTransactionTypeId);

                if(strTransactionType != null && strTransactionType.equalsIgnoreCase("Select Transaction Type")){
                    transaction_type = "";
                }else{
                    transaction_type = strTransactionType;
                    dashboardDataResponseCall(start_date,end_date);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        ll_expense.setOnClickListener(this);
        ll_income.setOnClickListener(this);

        rl_daily.setOnClickListener(this);
        rl_weekly.setOnClickListener(this);
        rl_monthly.setOnClickListener(this);
        rl_annually.setOnClickListener(this);





        return view;
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_expense:
                showExpenseTab();
                dashboardDataResponseCall(start_date,end_date);
                break;

            case R.id.ll_income:
                showincomeTab();
                dashboardDataResponseCall(start_date,end_date);
                break;
            case R.id.rl_daily:
                txt_date.setText(currentDate);
                txt_transcperiod.setText("Daily");
                txt_lbl_daily.setTextColor(getResources().getColor(R.color.white));
                txt_lbl_weekly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_monthly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_annually.setTextColor(getResources().getColor(R.color.txt_black));
                rl_daily.setBackgroundResource(R.drawable.rounded_corner_bg_color);
                rl_weekly.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_monthly.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_annually.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                Log.w(TAG,"rl_daily currentdate : "+currentdate);
                start_date = currentdate;
                end_date = currentdate;
                dashboardDataResponseCall(start_date,end_date);
                break;

            case R.id.rl_weekly:
                txt_lbl_daily.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_weekly.setTextColor(getResources().getColor(R.color.white));
                txt_lbl_monthly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_annually.setTextColor(getResources().getColor(R.color.txt_black));
                rl_daily.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_weekly.setBackgroundResource(R.drawable.rounded_corner_bg_color);
                rl_monthly.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_annually.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date myDate = simpleDateFormat.parse(currentdate);
                    Calendar calendar = Calendar.getInstance();
                    if (myDate != null) {
                        calendar.setTime(myDate);
                    }
                    calendar.add(Calendar.DAY_OF_YEAR, -7);
                    Date newDate = calendar.getTime();
                    Previous7days = simpleDateFormat.format(newDate);

                    @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String inputDateStr = Previous7days;
                    Date date = inputFormat.parse(inputDateStr);
                    String outputDateStr = null;
                    if (date != null) {
                        outputDateStr = outputFormat.format(date);
                    }
                    txt_date.setText(outputDateStr+" to "+currentDate);
                    txt_transcperiod.setText("Weekly");

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                start_date = currentdate;
                end_date = Previous7days;

                dashboardDataResponseCall(end_date,start_date);
                Log.w(TAG,"rl_weekly start_date : "+start_date+" Previous7days : "+Previous7days);
           break;

            case R.id.rl_monthly:
                txt_lbl_daily.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_weekly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_monthly.setTextColor(getResources().getColor(R.color.white));
                txt_lbl_annually.setTextColor(getResources().getColor(R.color.txt_black));
                rl_daily.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
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

                txt_date.setText(month_name);
                txt_transcperiod.setText("Montly");

                Log.w(TAG,"rl_monthly : "+"monthlystartdate : "+monthlystartdate+" monthlyenddate : "+monthlyenddate);

                start_date = monthlystartdate;
                end_date = monthlyenddate;
                dashboardDataResponseCall(start_date,end_date);

                break;

            case R.id.rl_annually:
                txt_lbl_daily.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_weekly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_monthly.setTextColor(getResources().getColor(R.color.txt_black));
                txt_lbl_annually.setTextColor(getResources().getColor(R.color.white));
                rl_daily.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_weekly.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_monthly.setBackgroundResource(R.drawable.rounded_cornerwithcolor);
                rl_annually.setBackgroundResource(R.drawable.rounded_corner_bg_color);
                int yearannually = Calendar.getInstance().get(Calendar.YEAR);
                String annuallystartdate = yearannually+"-"+"01"+"-"+"01";
                String annuallyenddate = yearannually+"-"+"12"+"-"+"31";

                Log.w(TAG,"rl_annually : "+"annuallystartdate : "+annuallystartdate+" annuallyenddate : "+annuallyenddate);

                txt_date.setText(yearannually+"");
                txt_transcperiod.setText("Annually");
                start_date = annuallystartdate;
                end_date = annuallyenddate;

                dashboardDataResponseCall(start_date,end_date);

                break;



        }
    }

    @SuppressLint("SetTextI18n")
    private void showincomeTab() {
        ll_total_expense.setVisibility(View.GONE);
        ll_total_income.setVisibility(View.VISIBLE);
        transaction_way = "Credit";
        showincome = true;
        txt_lbl_expensetab.setBackgroundResource(R.color.white);
        txt_lbl_incometab.setBackgroundResource(R.drawable.rectangle_corner_bg_thicblue);
        txt_lbl_incometab.setTextColor(getResources().getColor(R.color.white));
        txt_lbl_expensetab.setTextColor(getResources().getColor(R.color.text_black));

    }

    @SuppressLint("SetTextI18n")
    private void showExpenseTab() {
        ll_total_expense.setVisibility(View.VISIBLE);
        ll_total_income.setVisibility(View.GONE);
        transaction_way = "Debit";
        showincome = false;
        txt_lbl_expensetab.setBackgroundResource(R.drawable.rectangle_corner_bg_thicblue);
        txt_lbl_incometab.setBackgroundResource(R.color.white);
        txt_lbl_incometab.setTextColor(getResources().getColor(R.color.text_black));
        txt_lbl_expensetab.setTextColor(getResources().getColor(R.color.white));

    }

    @SuppressLint("LogNotTimber")
    public void paymentTypeListResponseCall(){

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PaymentTypeListResponse> call = apiInterface.paymentTypeListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PaymentTypeListResponse>() {

            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<PaymentTypeListResponse> call, @NonNull Response<PaymentTypeListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()) {
                        Log.w(TAG, "PaymentTypeListResponse" + new Gson().toJson(response.body()));

                        dashboardDataResponseCall(start_date,end_date);
                        if (response.body().getData() != null) {
                            paymenttypeList = response.body().getData();
                        }

                        if (paymenttypeList != null && paymenttypeList.size() > 0) {
                            setPaymenttype(paymenttypeList);
                        }


                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<PaymentTypeListResponse> call, @NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"PaymentTypeListResponse flr"+t.getMessage());
            }
        });

    }
    private void setPaymenttype(List<PaymentTypeListResponse.DataBean> paymenttypeList) {
        ArrayList<String> paymenttypeArrayList = new ArrayList<>();
        paymenttypeArrayList.add("Select Transaction Type");
        for (int i = 0; i < paymenttypeList.size(); i++) {

            String paymenttype = paymenttypeList.get(i).getPayment_type();
            hashMap_PaymentTypevalue.put(paymenttypeList.get(i).getPayment_type(), paymenttypeList.get(i).get_id());

            Log.w(TAG,"paymenttype-->"+paymenttype);
            paymenttypeArrayList.add(paymenttype);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, paymenttypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spr_transacation_type.setAdapter(spinnerArrayAdapter);



        }
    }



    @SuppressLint("LogNotTimber")
    private void dashboardDataResponseCall(String start_date,String end_date) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<DashboardDataResponse> call = apiInterface.dashboardDataResponseCall(RestUtils.getContentType(), dashboardDataRequest(start_date,end_date));
        Log.w(TAG,"DashboardDataResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<DashboardDataResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<DashboardDataResponse> call, @NonNull Response<DashboardDataResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"DashboardDataResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null && response.body().getData().size()>0) {
                            Log.w(TAG,"DashboardDataResponse size : " + response.body().getData().size());
                            txt_latest_transaction.setText("Latest "+response.body().getData().size()+" Transactions");
                            rv_transaction.setVisibility(View.VISIBLE);
                            txt_no_records.setVisibility(View.GONE);
                            setTransactionListView(response.body().getData());

                        }
                        else{
                            rv_transaction.setVisibility(View.GONE);
                            txt_no_records.setVisibility(View.GONE);
                            txt_no_records.setText(getResources().getString(R.string.no_new_transactions_are_available));
                            txt_latest_transaction.setText("Latest "+0+" Transactions");

                        }

                        if(response.body().getBalance() != null){
                            if(response.body().getBalance().getCredit_amount() != 0) {
                                txt_income.setText("\u20B9 " + response.body().getBalance().getCredit_amount());
                            }else{
                                txt_income.setText("\u20B9 " + 0);
                            }
                            if(response.body().getBalance().getDebit_amount() != 0) {
                                txt_expense.setText("\u20B9 " + response.body().getBalance().getDebit_amount());
                            }else{
                                txt_expense.setText("\u20B9 " + 0);
                            }
                            if(response.body().getBalance().getBalance_amount() != 0) {
                                txt_balance.setText("\u20B9 " + response.body().getBalance().getBalance_amount());
                            }else{
                                txt_balance.setText("\u20B9 " + 0);
                            }
                        }


                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<DashboardDataResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("DashboardDataRe flr", "--->" + t.getMessage());
            }
        });

    }
    private DashboardDataRequest dashboardDataRequest(String start_date,String end_date) {
        /*
         * transaction_type : Cash
         * transaction_way : Debit
         * user_id : 617a7c37eeb3a520395e2f15
         * start_date : 23-10-2021
         * end_date : 23-10-2021
         */


        DashboardDataRequest dashboardDataRequest = new DashboardDataRequest();
        dashboardDataRequest.setTransaction_type(transaction_type);
        dashboardDataRequest.setTransaction_way(transaction_way);
        dashboardDataRequest.setUser_id(user_id);
        dashboardDataRequest.setStart_date(start_date);
        dashboardDataRequest.setEnd_date(end_date);

        Log.w(TAG,"dashboardDataRequest "+ new Gson().toJson(dashboardDataRequest));
        return dashboardDataRequest;
    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
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

    private void setTransactionListView(List<DashboardDataResponse.DataBean> data) {
        rv_transaction.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_transaction.setItemAnimator(new DefaultItemAnimator());
        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(mContext, data);
        rv_transaction.setAdapter(transactionListAdapter);

    }
}