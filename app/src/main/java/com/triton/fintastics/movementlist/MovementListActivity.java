package com.triton.fintastics.movementlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.activities.DashoardActivity;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.YearsListRequest;
import com.triton.fintastics.responsepojo.PaymentTypeListResponse;
import com.triton.fintastics.responsepojo.YearsListResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.ConnectionDetector;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovementListActivity extends AppCompatActivity {

    private final String TAG = "MovementListActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_transacation_type)
    Spinner spr_transacation_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_month)
    Spinner spr_month;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_year)
    Spinner spr_year;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_submit)
    RelativeLayout rl_submit;

    private List<PaymentTypeListResponse.DataBean> paymenttypeList;
    HashMap<String,String> hashMap_PaymentTypevalue = new HashMap<>();
    private String strTransactionType;
    private Object strTransactionTypeId;
    private List<Integer> yearsList;
    private String strMonthType;
    private String strYearType;
    private int month;

    private String user_id;
    private String transaction_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_list);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        user_id = user.get(SessionManager.KEY_ID);

        avi_indicator.setVisibility(View.GONE);

        ImageView img_back = include_header.findViewById(R.id.img_back);
        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.movement_list));

        img_back.setOnClickListener(view -> onBackPressed());

        rl_submit.setOnClickListener(view -> {
            if(validTransactionType()){
                if(validMonthType()){
                    if(validYearType()){
                        gotoShowData();
                    }
                }
            }


        });


        if (new ConnectionDetector(MovementListActivity.this).isNetworkAvailable(MovementListActivity.this)) {
            paymentTypeListResponseCall();
        }

        spr_transacation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.text_black));
                strTransactionType = spr_transacation_type.getSelectedItem().toString();
                transaction_type = strTransactionType;
                strTransactionTypeId = hashMap_PaymentTypevalue.get(strTransactionType);

                Log.w(TAG,"strTransactionType : "+strTransactionType+" strTransactionTypeId :"+strTransactionTypeId+" arg2 : "+arg2);



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spr_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.text_black));
                strMonthType = spr_month.getSelectedItem().toString();
                month = arg2;
                Log.w(TAG,"strMonthType : "+strMonthType+" position "+arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spr_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.text_black));
                strYearType = spr_year.getSelectedItem().toString();
                Log.w(TAG,"strYearType : "+strYearType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashoardActivity.class));
        finish();
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
                        yearsListResponseCall();
                        Log.w(TAG, "PaymentTypeListResponse" + new Gson().toJson(response.body()));


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

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(MovementListActivity.this, R.layout.spinner_item, paymenttypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spr_transacation_type.setAdapter(spinnerArrayAdapter);



        }
    }

    @SuppressLint("LogNotTimber")
    private void yearsListResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<YearsListResponse> call = apiInterface.yearsListResponseCall(RestUtils.getContentType(), yearsListRequest());
        Log.w(TAG,"YearsListResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<YearsListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<YearsListResponse> call, @NonNull Response<YearsListResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            yearsList = response.body().getData();
                        }

                        if (yearsList != null && yearsList.size() > 0) {
                            setYearsList(yearsList);
                        }



                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<YearsListResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("YearsListResponse flr", "--->" + t.getMessage());
            }
        });

    }

    private void setYearsList(List<Integer> yearsList) {
        ArrayList<String> yearsArrayList = new ArrayList<>();
        yearsArrayList.add("Select Year");
        for (int i = 0; i < yearsList.size(); i++) {

            String years = String.valueOf(yearsList.get(i));

            Log.w(TAG,"yearslist-->"+years);
            yearsArrayList.add(years);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(MovementListActivity.this, R.layout.spinner_item, yearsArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spr_year.setAdapter(spinnerArrayAdapter);



        }
    }

    private YearsListRequest yearsListRequest() {
        /*
         * current_year : 2021
         */
        int year = Calendar.getInstance().get(Calendar.YEAR);
        YearsListRequest yearsListRequest = new YearsListRequest();
        yearsListRequest.setCurrent_year(year);
        Log.w(TAG,"transactionCreateRequest "+ new Gson().toJson(yearsListRequest));
        return yearsListRequest;
    }

    public boolean validTransactionType() {
        if(strTransactionType != null && strTransactionType.equalsIgnoreCase("Select Transaction Type")){
            final AlertDialog alertDialog = new AlertDialog.Builder(MovementListActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_transactiontype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }
    public boolean validMonthType() {
        if(strMonthType != null && strMonthType.equalsIgnoreCase("Select Month")){
            final AlertDialog alertDialog = new AlertDialog.Builder(MovementListActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_month));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }
    public boolean validYearType() {
        if(strYearType != null && strYearType.equalsIgnoreCase("Select Year")){
            final AlertDialog alertDialog = new AlertDialog.Builder(MovementListActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_year));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }

    private void gotoShowData() {
        String start_date = strYearType + "-" + month + "-" + "01";
        String end_date = strYearType + "-" + month + "-" + "31";
        Intent intent = new Intent(getApplicationContext(), MovementListShowDataActivity.class);
        intent.putExtra("user_id",user_id);
        intent.putExtra("start_date", start_date);
        intent.putExtra("end_date", end_date);
        intent.putExtra("transaction_type",transaction_type);
        startActivity(intent);
    }

}