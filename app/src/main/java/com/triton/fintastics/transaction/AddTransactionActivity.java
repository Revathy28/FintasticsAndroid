package com.triton.fintastics.transaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.activities.ChangePasswordActivity;
import com.triton.fintastics.activities.DashoardActivity;
import com.triton.fintastics.activities.ForgetPasswordActivity;
import com.triton.fintastics.activities.SignUpActivity;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.familyaccount.SharelinkFamilyMembersActivity;
import com.triton.fintastics.requestpojo.SignupRequest;
import com.triton.fintastics.requestpojo.TransactionCreateRequest;
import com.triton.fintastics.requestpojo.UserIdRequest;
import com.triton.fintastics.responsepojo.PaymentTypeListResponse;
import com.triton.fintastics.responsepojo.SignupResponse;
import com.triton.fintastics.responsepojo.SuccessResponse;
import com.triton.fintastics.responsepojo.TransactionGetBalanceResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.ConnectionDetector;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "AddTransactionActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_payment_type)
    Spinner spr_payment_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_transacation_type)
    Spinner spr_transacation_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_desc_type)
    Spinner spr_desc_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_calendar)
    ImageView img_calendar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_day)
    TextView txt_day;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_month)
    TextView txt_month;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_year)
    TextView txt_year;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_amount)
    EditText edt_amount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private List<PaymentTypeListResponse.DataBean> paymenttypeList;

    HashMap<String,String> hashMap_PaymentTypevalue = new HashMap<>();
    private String strTransactionType;
    private String strTransactionTypeId;

    HashMap<String,String> hashMap_DescTypevalue = new HashMap<>();
    private String strDescType;
    private String strDescTypeId;


    private int TRANSACTION_DATE_PICKER_ID = 1;
    private int year, month, day;

    private String SelectedTransactionddate= "";
    private String strPaymentType;
    private Dialog alertDialog;
    private String transaction_way = "";
    private String userid;
    private String refcode;

    private int Balance_amount = 0;
    private int transaction_balance = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        SessionManager sessionManager = new SessionManager(AddTransactionActivity.this);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        refcode = user.get(SessionManager.KEY_REF_CODE);

        avi_indicator.setVisibility(View.GONE);

        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.add_transaction));

        ImageView img_back = include_header.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (new ConnectionDetector(AddTransactionActivity.this).isNetworkAvailable(AddTransactionActivity.this)) {
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



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spr_payment_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.text_black));
                strPaymentType = spr_payment_type.getSelectedItem().toString();

                Log.w(TAG,"strPaymentType : "+strPaymentType);

                if(strPaymentType != null && strPaymentType.equalsIgnoreCase("Income")){
                    transaction_way = "Credit";
                }
                else if(strPaymentType != null && strPaymentType.equalsIgnoreCase("Expense")){
                    transaction_way = "Debit";
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spr_desc_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.text_black));
                strDescType = spr_desc_type.getSelectedItem().toString();
                strDescTypeId = hashMap_DescTypevalue.get(strTransactionType);

                Log.w(TAG,"strDescType : "+strDescType+" strDescTypeId :"+strDescTypeId);






            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        img_calendar.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

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

                        transactionGetBalanceAmountRequestCall();
                        if (response.body().getData() != null) {
                            paymenttypeList = response.body().getData();
                        }

                        if (paymenttypeList != null && paymenttypeList.size() > 0) {
                            setPaymenttype(paymenttypeList);
                        }

                        if(response.body().getDesc_type() != null &&  response.body().getDesc_type().size()>0){
                            setDesctype(response.body().getDesc_type());
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

    private void setDesctype(List<PaymentTypeListResponse.DescTypeBean> desc_type_list) {
        ArrayList<String> desctypeArrayList = new ArrayList<>();
        desctypeArrayList.add("Select Description Type");
        for (int i = 0; i < desc_type_list.size(); i++) {

            String desctype = desc_type_list.get(i).getDesc_type();
            hashMap_DescTypevalue.put(desc_type_list.get(i).getDesc_type(), desc_type_list.get(i).get_id());

            Log.w(TAG,"desctype-->"+desctype);
            desctypeArrayList.add(desctype);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(AddTransactionActivity.this, R.layout.spinner_item, desctypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spr_desc_type.setAdapter(spinnerArrayAdapter);



        }
    }

    private void setPaymenttype(List<PaymentTypeListResponse.DataBean> paymenttypeList) {
        ArrayList<String> paymenttypeArrayList = new ArrayList<>();
        paymenttypeArrayList.add("Select Transaction Type");
        for (int i = 0; i < paymenttypeList.size(); i++) {

            String paymenttype = paymenttypeList.get(i).getPayment_type();
            hashMap_PaymentTypevalue.put(paymenttypeList.get(i).getPayment_type(), paymenttypeList.get(i).get_id());

            Log.w(TAG,"paymenttype-->"+paymenttype);
            paymenttypeArrayList.add(paymenttype);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(AddTransactionActivity.this, R.layout.spinner_item, paymenttypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spr_transacation_type.setAdapter(spinnerArrayAdapter);



        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_calendar:
                gotoTransactionDate();
                break;

                case R.id.btn_submit:
                    transactionValidator();
                break;
        }
    }



    @SuppressLint("LogNotTimber")
    private void transactionGetBalanceAmountRequestCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<TransactionGetBalanceResponse> call = apiInterface.transactionGetBalanceAmountRequestCall(RestUtils.getContentType(), userIdRequest());
        Log.w(TAG,"transactionGetBalanceAmountRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<TransactionGetBalanceResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<TransactionGetBalanceResponse> call, @NonNull Response<TransactionGetBalanceResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"transactionGetBalanceAmountRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null) {
                            Balance_amount = response.body().getData().getBalance_amount();

                        }


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<TransactionGetBalanceResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("TransactionGetBalancflr", "--->" + t.getMessage());
            }
        });

    }
    private UserIdRequest userIdRequest() {
        /*
        * user_id : 617a7c37eeb3a520395e2f15

         */



        UserIdRequest userIdRequest = new UserIdRequest();
        userIdRequest.setUser_id(userid);
        Log.w(TAG,"userIdRequest "+ new Gson().toJson(userIdRequest));
        return userIdRequest;
    }

    @SuppressLint("LogNotTimber")
    private void transactionCreateRequestCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = apiInterface.transactionCreateRequestCall(RestUtils.getContentType(), transactionCreateRequest());
        Log.w(TAG,"transactionCreateRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"transactionCreateRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if(response.body().getData() != null) {
                           startActivity(new Intent(getApplicationContext(),DashoardActivity.class));
                           finish();
                        }


                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("SuccessResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private TransactionCreateRequest transactionCreateRequest() {
        /*
         * transaction_date : 23-10-2021 11:00 AM
         * transaction_type : Cash
         * transaction_desc : Salary
         * transaction_way : Credit
         * transaction_amount : 2000
         * transaction_balance  : 2000
         * user_id : 617a7c37eeb3a520395e2f15
         * parent_code :
         */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String currentDateandTime12hrs = simpleDateFormat.format(new Date());
        String currenttime = currentDateandTime12hrs.substring(currentDateandTime12hrs.indexOf(' ') + 1);
        String currentdate =  currentDateandTime12hrs.substring(0, currentDateandTime12hrs.indexOf(' '));



        int transactionamount = 0;

        try {
            transactionamount = Integer.parseInt(edt_amount.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        if(transaction_way != null && !transaction_way.isEmpty() && transaction_way.equalsIgnoreCase("Credit")){
            transaction_balance = Balance_amount + transactionamount;
        }
        if(transaction_way != null && !transaction_way.isEmpty() && transaction_way.equalsIgnoreCase("Debit")){
            transaction_balance = Balance_amount - transactionamount;
        }


        TransactionCreateRequest transactionCreateRequest = new TransactionCreateRequest();
        transactionCreateRequest.setTransaction_date(SelectedTransactionddate+" "+currenttime);
        transactionCreateRequest.setTransaction_type(strTransactionType);
        transactionCreateRequest.setTransaction_desc(strDescType);
        transactionCreateRequest.setTransaction_way(transaction_way);
        transactionCreateRequest.setTransaction_amount(transactionamount);
        transactionCreateRequest.setTransaction_balance(transaction_balance);
        transactionCreateRequest.setUser_id(userid);
        transactionCreateRequest.setParent_code(refcode);
        Log.w(TAG,"transactionCreateRequest "+ new Gson().toJson(transactionCreateRequest));
        return transactionCreateRequest;
    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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


    private void gotoTransactionDate() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        showDialog(TRANSACTION_DATE_PICKER_ID);
    }

    @SuppressLint("LogNotTimber")
    @Override
    protected Dialog onCreateDialog(int id) {
        Log.w(TAG,"onCreateDialog id : "+id);
        if (id == TRANSACTION_DATE_PICKER_ID) {
            // open datepicker dialog.
            // set date picker for current date
            // add pickerListener listner to date picker
            // return new DatePickerDialog(this, pickerListener, year, month,day);
            DatePickerDialog dialog = new DatePickerDialog(this, pickerListener, year, month, day);
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

            SelectedTransactionddate = strdayOfMonth + "-" + strMonth + "-" + year;

            // Show selected date
            txt_day.setText(strdayOfMonth);
            txt_month.setText(strMonth);
            txt_year.setText(year+"");

        }
    };


    public void transactionValidator() {
        boolean can_proceed = true;
        if(validdPaymentType()){
            if(validdTransactionType()){
                if(validdDescType()){
                    if (SelectedTransactionddate != null && SelectedTransactionddate.isEmpty()) {
                        showErrorLoading("Please select date of transaction");
                        can_proceed = false;
                    } else if (edt_amount.getText().toString().trim().equals("")) {
                        edt_amount.setError("Please enter amount");
                        edt_amount.requestFocus();
                        can_proceed = false;
                    }
                }
            }
        }


        if (can_proceed) {
            if (new ConnectionDetector(AddTransactionActivity.this).isNetworkAvailable(AddTransactionActivity.this)) {
                transactionCreateRequestCall();
            }
        }




    }



    public boolean validdPaymentType() {
        if(strPaymentType != null && strPaymentType.equalsIgnoreCase("Select Payment Type")){
            final AlertDialog alertDialog = new AlertDialog.Builder(AddTransactionActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_paymenttype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }
    public boolean validdTransactionType() {
        if(strTransactionType != null && strTransactionType.equalsIgnoreCase("Select Transaction Type")){
            final AlertDialog alertDialog = new AlertDialog.Builder(AddTransactionActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_transactiontype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }
    public boolean validdDescType() {
        if(strDescType != null && strDescType.equalsIgnoreCase("Select Description Type")){
            final AlertDialog alertDialog = new AlertDialog.Builder(AddTransactionActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_desctype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),DashoardActivity.class));
        finish();
    }
}