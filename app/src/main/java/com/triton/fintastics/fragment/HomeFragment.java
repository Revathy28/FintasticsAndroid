package com.triton.fintastics.fragment;

import static android.content.ContentValues.TAG;

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
import com.triton.fintastics.adapter.Dash_trans_DetailsAdapter;
import com.triton.fintastics.adapter.TransactionListAdapter;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.movementlist.MovementListActivity;
import com.triton.fintastics.requestpojo.ChangePasswordRequest;
import com.triton.fintastics.requestpojo.Dash_trans_DetailsRequest;
import com.triton.fintastics.requestpojo.DashboardDataRequest;
import com.triton.fintastics.requestpojo.FCMRequest;
import com.triton.fintastics.responsepojo.Dash_trans_DetailsResponse;
import com.triton.fintastics.responsepojo.DashboardDataResponse;
import com.triton.fintastics.responsepojo.FCMResponse;
import com.triton.fintastics.responsepojo.LoginResponse;
import com.triton.fintastics.responsepojo.PaymentTypeListResponse;
import com.triton.fintastics.responsepojo.Payment_type_listResponse;
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
    private String status;
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

    List<Dash_trans_DetailsResponse.DataBean> breedTypedataBeanList;
    Dash_trans_DetailsAdapter activityBasedListAdapter;

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
    List<Payment_type_listResponse.DataBean.Payment_typesDatabeanList> dataBeanList;

    HashMap<String,String> hashMap_PaymentTypevalue = new HashMap<>();
    private String strTransactionType;
    private Object strTransactionTypeId;

    private String transaction_type = "";
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
        FCMResponseCall();
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

      //  dashboardDataResponseCall(transaction_type,transaction_way,user_id,"",end_date,"");

     //   Log.d("value",transaction_type + "," + transaction_way + "," + user_id + "," + start_date + "," + end_date);


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

                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                    String currentDateandTime12hrs = simpleDateFormat1.format(new Date());

                    String transaction_way = "Credit";

                    dashboardDataResponseCall(transaction_type,transaction_way,user_id,"01-11-2022 02:48 pm",currentDateandTime12hrs,"Asia/Kolkata");

                    Log.d("value",transaction_type + "," + transaction_way + "," + user_id + "," + start_date + "," + end_date);

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

    private void dashboardDataResponseCall(String transaction_type, String transaction_way, String user_id, String start_date, String end_date, String time_zone) {
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<Dash_trans_DetailsResponse> call = apiInterface.dashboardDataResponseCall(RestUtils.getContentType(), serviceRequest(transaction_type,transaction_way,user_id,start_date,end_date,time_zone));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<Dash_trans_DetailsResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Dash_trans_DetailsResponse> call, @NonNull Response<Dash_trans_DetailsResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                  String  message = response.body().getMessage();
                    Log.d("message", message);

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();

                            setView(breedTypedataBeanList);
                            Log.d("dataaaaa", String.valueOf(breedTypedataBeanList));

                            rv_transaction.setVisibility(View.VISIBLE);
                            txt_no_records.setVisibility(View.GONE);
                        }
                        else {

                            rv_transaction.setVisibility(View.GONE);
                            txt_no_records.setVisibility(View.VISIBLE);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Dash_trans_DetailsResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Dash_trans_DetailsRequest serviceRequest(String transaction_type, String transaction_way, String user_id, String start_date, String end_date, String time_zone) {
        Dash_trans_DetailsRequest service = new Dash_trans_DetailsRequest();
        service.setTransaction_type(transaction_type);
        service.setTransaction_way(transaction_way);
        service.setUser_id(user_id);
        service.setStart_date(start_date);
        service.setEnd_date(end_date);
        service.setTimezone(time_zone);
        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(service));
        return service;
    }

    private void setView(List<Dash_trans_DetailsResponse.DataBean> dataBeanList) {
        rv_transaction.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        rv_transaction.setItemAnimator(new DefaultItemAnimator());
        activityBasedListAdapter = new Dash_trans_DetailsAdapter(getContext(), dataBeanList);
        rv_transaction.setAdapter(activityBasedListAdapter);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_expense:
                ll_total_expense.setVisibility(View.VISIBLE);
                ll_total_income.setVisibility(View.GONE);
                String transaction_way = "Debit";
                showincome = false;
                txt_lbl_expensetab.setBackgroundResource(R.drawable.rectangle_corner_bg_thicblue);
                txt_lbl_incometab.setBackgroundResource(R.color.white);
                txt_lbl_incometab.setTextColor(getResources().getColor(R.color.text_black));
                txt_lbl_expensetab.setTextColor(getResources().getColor(R.color.white));
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                String currentDateandTime12hrs = simpleDateFormat1.format(new Date());

                dashboardDataResponseCall(transaction_type,transaction_way,user_id,"01-11-2022 02:48 pm",currentDateandTime12hrs,"Asia/Kolkata");

                break;

            case R.id.ll_income:
                ll_total_expense.setVisibility(View.GONE);
                ll_total_income.setVisibility(View.VISIBLE);
                String transaction_way1 = "Credit";
                showincome = true;
                txt_lbl_expensetab.setBackgroundResource(R.color.white);
                txt_lbl_incometab.setBackgroundResource(R.drawable.rectangle_corner_bg_thicblue);
                txt_lbl_incometab.setTextColor(getResources().getColor(R.color.white));
                txt_lbl_expensetab.setTextColor(getResources().getColor(R.color.text_black));
                SimpleDateFormat simpleDateFormat11 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                String currentDateandTime12hrs1 = simpleDateFormat11.format(new Date());

                dashboardDataResponseCall(transaction_type,transaction_way1,user_id,"01-11-2022 02:48 pm",currentDateandTime12hrs1,"Asia/Kolkata");

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
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                String currentDateandTime12hrs2 = simpleDateFormat2.format(new Date());

             //   dashboardDataResponseCall(transaction_type,transaction_way,user_id,"01-11-2022 02:48 pm",currentDateandTime12hrs2,"Asia/Kolkata");

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

                SimpleDateFormat simpleDateFormat123 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                String currentDateandTime12hrs123 = simpleDateFormat123.format(new Date());

             //   dashboardDataResponseCall(transaction_type,transaction_way,user_id,"01-11-2022 02:48 pm",currentDateandTime12hrs123,"Asia/Kolkata");

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

                SimpleDateFormat simpleDateFormat21 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                String currentDateandTime12hrs121 = simpleDateFormat21.format(new Date());

              //  dashboardDataResponseCall(transaction_type,transaction_way,user_id,"01-11-2022 02:48 pm",currentDateandTime12hrs121,"Asia/Kolkata");

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

                SimpleDateFormat simpleDateFormat32 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                String currentDateandTime12hrs132 = simpleDateFormat32.format(new Date());

              //  dashboardDataResponseCall(transaction_type,transaction_way,user_id,"01-11-2022 02:48 pm",currentDateandTime12hrs132,"Asia/Kolkata");

                break;
        }
    }
    @SuppressLint("LogNotTimber")
    public void FCMResponseCall()
    {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<FCMResponse> call = apiInterface.fcmresponsecall(RestUtils.getContentType(), fcmRequest());
        Log.w(TAG,"url  :%sfcmRequestcall"+ call.request().url().toString());
        call.enqueue(new Callback<FCMResponse>() {

            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FCMResponse> call, @NonNull Response<FCMResponse> response) {
                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    status = response.body().getStatus();
                    if(200 == response.body().getCode()) {
                        Toasty.success(mContext,response.body().getMessage(), Toast.LENGTH_SHORT, true).show();



                    }
                }

            }

            @Override
            public void onFailure(Call<FCMResponse> call, Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("FBtoken flr", "--->" + t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }




        });

    }
    private FCMRequest fcmRequest()
    {
        FCMRequest fcmRequest=new FCMRequest();
        fcmRequest.setUser_id(user_id);
        Log.w(TAG, "FCMRequest" + new Gson().toJson(fcmRequest));
        return fcmRequest;
    }

    @SuppressLint("LogNotTimber")
    private void paymentTypeListResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<Payment_type_listResponse> call = apiInterface.payment_TypeListResponseCall(RestUtils.getContentType());
        Log.w(TAG, "getcurrencyresponsecall url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<Payment_type_listResponse>() {
            @Override
            public void onResponse(Call<Payment_type_listResponse> call, Response<Payment_type_listResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "GetCurrencyResponse" + new Gson().toJson(response.body()));
                Payment_type_listResponse cr = response.body();
                if (response.body() != null) {
                    String message = response.body().getMessage();
                    if (200 == response.body().getCode()) {

                        if (response.body().getData() != null) {
                            dataBeanList = response.body().getData().getPaymenttypeList();
                            if (dataBeanList != null && dataBeanList.size() > 0) {

                                Log.w(TAG, "Size--" + dataBeanList.size());
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add("Select Transaction Type");
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    String string = dataBeanList.get(i).getPayment_type();
                                    arrayList.add(string);

                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrayList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spr_transacation_type.setAdapter(adapter);

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Payment_type_listResponse> call, Throwable t) {

            }


        });
    }

//    private void dashboardDataResponseCall(String start_date,String end_date) {
//        avi_indicator.setVisibility(View.VISIBLE);
//        avi_indicator.smoothToShow();
//        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
//        Call<DashboardDataResponse> call = apiInterface.dashboardDataResponseCall(RestUtils.getContentType(), dashboardDataRequest(start_date,end_date));
//        Log.w(TAG,"DashboardDataResponse url  :%s"+" "+ call.request().url().toString());
//
//        call.enqueue(new Callback<DashboardDataResponse>() {
//            @SuppressLint({"LogNotTimber", "SetTextI18n"})
//            @Override
//            public void onResponse(@NonNull Call<DashboardDataResponse> call, @NonNull Response<DashboardDataResponse> response) {
//                avi_indicator.smoothToHide();
//                Log.w(TAG,"DashboardDataResponse" + new Gson().toJson(response.body()));
//                if (response.body() != null) {
//
//                    if (200 == response.body().getCode()) {
//                        if(response.body().getData() != null && response.body().getData().size()>0) {
//                            Log.w(TAG,"DashboardDataResponse size : " + response.body().getData().size());
//                            txt_latest_transaction.setText("Latest "+response.body().getData().size()+" Transactions");
//                            rv_transaction.setVisibility(View.VISIBLE);
//                            txt_no_records.setVisibility(View.GONE);
//                            setTransactionListView(response.body().getData());
//
//                        }
//                        else{
//                            rv_transaction.setVisibility(View.GONE);
//                            txt_no_records.setVisibility(View.GONE);
//                            txt_no_records.setText(getResources().getString(R.string.no_new_transactions_are_available));
//                            txt_latest_transaction.setText("Latest "+0+" Transactions");
//
//                        }
//
//                        if(response.body().getBalance() != null){
//                            if(response.body().getBalance().getCredit_amount() != 0) {
//                                txt_income.setText("\u20B9 " + response.body().getBalance().getCredit_amount());
//                            }else{
//                                txt_income.setText("\u20B9 " + 0);
//                            }
//                            if(response.body().getBalance().getDebit_amount() != 0) {
//                                txt_expense.setText("\u20B9 " + response.body().getBalance().getDebit_amount());
//                            }else{
//                                txt_expense.setText("\u20B9 " + 0);
//                            }
//                            if(response.body().getBalance().getBalance_amount() != 0) {
//                                txt_balance.setText("\u20B9 " + response.body().getBalance().getBalance_amount());
//                            }else{
//                                txt_balance.setText("\u20B9 " + 0);
//                            }
//                        }
//
//
//                    } else {
//                        showErrorLoading(response.body().getMessage());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<DashboardDataResponse> call,@NonNull Throwable t) {
//                avi_indicator.smoothToHide();
//                Log.e("DashboardDataRe flr", "--->" + t.getMessage());
//            }
//        });
//
//    }
//    private DashboardDataRequest dashboardDataRequest(String start_date,String end_date) {
//        /*
//         * transaction_type : Cash
//         * transaction_way : Debit
//         * user_id : 617a7c37eeb3a520395e2f15
//         * start_date : 23-10-2021
//         * end_date : 23-10-2021
//         */
//
//
//        DashboardDataRequest dashboardDataRequest = new DashboardDataRequest();
//        dashboardDataRequest.setTransaction_type(transaction_type);
//        dashboardDataRequest.setTransaction_way(transaction_way);
//        dashboardDataRequest.setUser_id(user_id);
//        dashboardDataRequest.setStart_date(start_date);
//        dashboardDataRequest.setEnd_date(end_date);
//
//        Log.w(TAG,"dashboardDataRequest "+ new Gson().toJson(dashboardDataRequest));
//        return dashboardDataRequest;
//    }
//
//    public void showErrorLoading(String errormesage){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
//        alertDialogBuilder.setMessage(errormesage);
//        alertDialogBuilder.setPositiveButton("ok",
//                (arg0, arg1) -> hideLoading());
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }
//    public void hideLoading(){
//        try {
//            alertDialog.dismiss();
//        }catch (Exception ignored){
//
//        }
//    }
//
//    private void setTransactionListView(List<DashboardDataResponse.DataBean> data) {
//        rv_transaction.setLayoutManager(new LinearLayoutManager(getContext()));
//        rv_transaction.setItemAnimator(new DefaultItemAnimator());
//        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(mContext, data);
//        rv_transaction.setAdapter(transactionListAdapter);
//
//    }
}