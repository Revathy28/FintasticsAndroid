package com.triton.fintastics.transaction;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.activities.DashoardActivity;
import com.triton.fintastics.activities.SignUpActivity;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.TransactionCreateRequest;
import com.triton.fintastics.requestpojo.UserIdRequest;
import com.triton.fintastics.responsepojo.GetCurrencyResponse;
import com.triton.fintastics.responsepojo.PaymentTypeListResponse;
import com.triton.fintastics.responsepojo.Payment_type_listResponse;
import com.triton.fintastics.responsepojo.SuccessResponse;
import com.triton.fintastics.responsepojo.TransactionGetBalanceResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.ConnectionDetector;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoiceAddTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "VoiceAddTransactionActivity";

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
    @BindView(R.id.spr_sub_desc_type)
    Spinner spr_sub_desc_type;

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
    String Place;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button btn_submit;

    HashMap<String,String> hashMap_PaymentTypevalue = new HashMap<>();
    private String strTransactionType;
    private String strTransactionTypeId;

    HashMap<String,String> hashMap_DescTypevalue = new HashMap<>();
    private String strDescType;
    private String strDescTypeId;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_description)
    TextView txt_lbl_description;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_sub_desc)
    RelativeLayout rl_sub_desc;

    private int TRANSACTION_DATE_PICKER_ID = 1;
    private int year, month, day;

    private String SelectedTransactionddate= "";
    private String strPaymentType,strSubDecrType;
    private Dialog alertDialog;
    private String transaction_way = "";
    private String userid;
    private String refcode;

    private int Balance_amount = 0;
    private int transaction_balance = 0;

    private static final int RESULT_SPEECH = 1;
    private String voicecommand;

    ArrayList<String> array_paymentypename = new ArrayList<>();
    ArrayList<String> array_sub_desc_type = new ArrayList<>();
    List<Payment_type_listResponse.DataBean.Payment_typesDatabeanList> dataBeanList;
    List<Payment_type_listResponse.DataBean.Desc_typesDatabeanList> desctypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_add_transaction);

        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        SessionManager sessionManager = new SessionManager(VoiceAddTransactionActivity.this);
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

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);

        if (month<10){
            Place = "0"+ month;
        }
        else {
            Place = String.valueOf(month);
        }

        String da = String.valueOf(day);
        String yy = String.valueOf(year);

        txt_day.setText(da);
        txt_month.setText(Place);
        txt_year.setText(yy);

        array_paymentypename.add("Select Payment Type");
        array_paymentypename.add("Income");
        array_paymentypename.add("Expense");

        array_sub_desc_type.add("Select Sub Description Type");
        array_sub_desc_type.add("Cash Sub");
        array_sub_desc_type.add("Cash Sub 1");
        array_sub_desc_type.add("Cash Sub 2");
        array_sub_desc_type.add("Cash Sub 3");

        Log.w(TAG,"array_paymentypename : "+new Gson().toJson(array_paymentypename));

        if (array_paymentypename != null && array_paymentypename.size() > 0) {
            setPaymentNametype(array_paymentypename);
        }

        if (new ConnectionDetector(VoiceAddTransactionActivity.this).isNetworkAvailable(VoiceAddTransactionActivity.this)) {
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

        spr_sub_desc_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.text_black));
                strSubDecrType = spr_sub_desc_type.getSelectedItem().toString();

                Log.w(TAG,"strsub : "+strPaymentType);

//                if(strPaymentType != null && strPaymentType.equalsIgnoreCase("Income")){
//                    transaction_way = "Credit";
//                }
//                else if(strPaymentType != null && strPaymentType.equalsIgnoreCase("Expense")){
//                    transaction_way = "Debit";
//                }
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
              //  strDescTypeId = hashMap_DescTypevalue.get(strTransactionType);

                if(strDescType.equals("   CASH")){

                    strDescTypeId = "619f82faea15b9691ae3cbb9";
                }
               else if(strDescType.equals("FOOD")){

                    strDescTypeId = "61b07759ea15b9691ae3d19d";
                }
                else if(strDescType.equals("TOUR")){

                    strDescTypeId = "61b077aeea15b9691ae3d1b2";
                }
                else if(strDescType.equals("CLOTH")){

                    strDescTypeId = "61b077b7ea15b9691ae3d1b6";
                }
                else if(strDescType.equals("DEPT")){

                    strDescTypeId = "61b077caea15b9691ae3d1bc";
                }
                else if(strDescType.equals("BORROW")){

                    strDescTypeId = "61b077d7ea15b9691ae3d1c0";
                }
                else if(strDescType.equals("RENT")){

                    strDescTypeId = "61b077eaea15b9691ae3d1d2";
                }
                else if(strDescType.equals("EDUCATION")){

                    strDescTypeId = "61b077f4ea15b9691ae3d1d6";
                }
                else if(strDescType.equals("MEDICAL")){

                    strDescTypeId = "61b07802ea15b9691ae3d1da";
                }
                else if(strDescType.equals("WATER")){

                    strDescTypeId = "61b07823ea15b9691ae3d1ec";
                }
                else if(strDescType.equals("GAS")){

                    strDescTypeId = "61b0782cea15b9691ae3d1f0";
                }
                else if(strDescType.equals("REPAIR")){

                    strDescTypeId = "61b07835ea15b9691ae3d1f4";
                }
                else if(strDescType.equals("SALARY")){

                    strDescTypeId = "61b07845ea15b9691ae3d1f8";
                }
                else if(strDescType.equals("CASH")){

                    strDescTypeId = "63453e5803321a60432e83e4";
                }
                else if(strDescType.equals("~!@~!@~!@~")){

                    strDescTypeId = "6345416203321a60432e83ee";
                }
                else if(strDescType.equals("ss")){

                    strDescTypeId = "634541c603321a60432e83f3";
                }
                else if(strDescType.equals("ssdad")){

                    strDescTypeId = "6345426c03321a60432e83f7";
                }
                else if(strDescType.equals("asdadad")){

                    strDescTypeId = "6345427403321a60432e83fb";
                }
                else if(strDescType.equals("SSSSSSS")){

                    strDescTypeId = "63454bd203321a60432e841b";
                }

                Log.w(TAG,"strDescType : "+strDescType+" strDescTypeId :"+strDescTypeId);



//                txt_lbl_description.setVisibility(View.VISIBLE);
//                rl_sub_desc.setVisibility(View.VISIBLE);

                if (array_sub_desc_type != null && array_sub_desc_type.size() > 0) {
                    setSubDescrType(array_sub_desc_type);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        img_calendar.setOnClickListener(this);
        btn_submit.setOnClickListener(this);


        ImageView img_voicecommand = findViewById(R.id.img_voicecommand);
        img_voicecommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecognizeSpeech();
            }
        });

    }

    private void startRecognizeSpeech() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        try {
            startActivityForResult(intent, RESULT_SPEECH);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(
                    getApplicationContext(),
                    "Oops! First you must download \"Voice Search\" App from Store",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
// calls when voice recognition result received
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SPEECH) {
            if (resultCode == RESULT_OK && null != data) {
                // text is received form google
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //you can find your result in text Arraylist

                Log.w(TAG, "text : " + text);
                voicecommand = String.valueOf(text);
                Log.w(TAG, "voicecommand : " + text);



                if (voicecommand != null) {
                    String chars = capitalize(voicecommand);

                    Log.w(TAG, "chars : " + chars);
                    chars = chars.replaceAll("\\[", "").replaceAll("\\]", "");
                    Log.w(TAG, "chars if  : " + chars);

                    String[] splitStr = chars.split("\\s+");
                    for (String s : splitStr) {
                        Log.w(TAG, "splitStr : " + s);

                        for(int i=0;i<array_paymentypename.size();i++){
                            if(array_paymentypename.get(i).equalsIgnoreCase(s)){
                                strPaymentType = s;
                                setPaymentNametype(array_paymentypename);
                                break;

                            }
                        }

                        for(int i=0;i<dataBeanList.size();i++){
                            if(dataBeanList.get(i).getPayment_type().equalsIgnoreCase(s)){
                                strTransactionType  = s;
                               // setTransactiontype(dataBeanList);

                                if (dataBeanList != null && dataBeanList.size() > 0) {
                                    Log.w(TAG, "Size--" + dataBeanList.size());
                                    ArrayList<String> arrayList = new ArrayList<>();
                                    arrayList.add("Select Transaction Type");
                                    for (int j = 0; j < dataBeanList.size(); j++) {
                                        String string = dataBeanList.get(j).getPayment_type();
                                        arrayList.add(string);
                                    }

                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(VoiceAddTransactionActivity.this, android.R.layout.simple_spinner_item, arrayList);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spr_transacation_type.setAdapter(adapter);
                                }
                                break;

                            }
                        }
                        for(int i=0;i<desctypeList.size();i++){
                            if(desctypeList.get(i).getDesc_type().equalsIgnoreCase(s)){
                                strDescType  = s;

                                if (desctypeList != null && desctypeList.size() > 0) {

                                    Log.w(TAG, "Size--" + desctypeList.size());
                                    ArrayList<String> arrayList = new ArrayList<>();
                                    arrayList.add("Select Description Type");
                                    for (int j = 0; j < desctypeList.size(); j++) {
                                        String string = desctypeList.get(j).getDesc_type();
                                        arrayList.add(string);

                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(VoiceAddTransactionActivity.this, android.R.layout.simple_spinner_item, arrayList);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spr_desc_type.setAdapter(adapter);

                                }
                                break;

                            }
                        }


                        Log.w(TAG, "Transactiontypelist : "+new Gson().toJson(dataBeanList));

                    }

                }
            }
        }
    }


    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }
    @SuppressLint("LogNotTimber")

//    private void jobFindResponseCall(String job_no) {
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<BD_DetailsResponse> call = apiInterface.BD_DetailsResponseCall(RestUtils.getContentType(), serviceRequest(job_no));
//        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
//        call.enqueue(new Callback<BD_DetailsResponse>() {
//            @SuppressLint("LogNotTimber")
//            @Override
//            public void onResponse(@NonNull Call<BD_DetailsResponse> call, @NonNull Response<BD_DetailsResponse> response) {
//                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));
//
//                if (response.body() != null) {
//
//                    message = response.body().getMessage();
//                    Log.d("message", message);
//
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//                            breedTypedataBeanList = response.body().getData();
//
//                            setView(breedTypedataBeanList);
//                            Log.d("dataaaaa", String.valueOf(breedTypedataBeanList));
//
//                        }
//
//                    } else if (400 == response.body().getCode()) {
//                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {
//
//                        }
//                    } else {
//
//                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<BD_DetailsResponse> call, @NonNull Throwable t) {
//                Log.e("Jobno Find ", "--->" + t.getMessage());
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private BD_DetailsRequest serviceRequest(String job_no) {
//        BD_DetailsRequest service = new BD_DetailsRequest();
//        service.setJob_id(job_no);
//        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(service));
//        return service;
//    }
//
//    private void setView(List<BD_DetailsResponse.DataBean> dataBeanList) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        activityBasedListAdapter = new BD_DetailsAdapter(getApplicationContext(), dataBeanList,this);
//        recyclerView.setAdapter(activityBasedListAdapter);
//    }

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
                            desctypeList = response.body().getData().getDesctypeList();
                            if (dataBeanList != null && dataBeanList.size() > 0) {

                                Log.w(TAG, "Size--" + dataBeanList.size());
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add("Select Transaction Type");
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    String string = dataBeanList.get(i).getPayment_type();
                                 //   String transaction_type_id = dataBeanList.get(i).get_id();
                                    arrayList.add(string);

                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(VoiceAddTransactionActivity.this, android.R.layout.simple_spinner_item, arrayList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spr_transacation_type.setAdapter(adapter);

                            }

                            if (desctypeList != null && desctypeList.size() > 0) {

                                Log.w(TAG, "Size--" + desctypeList.size());
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add("Select Description Type");
                                for (int i = 0; i < desctypeList.size(); i++) {
                                    String string = desctypeList.get(i).getDesc_type();
                                  //  String desc_id = desctypeList.get(i).get_id();
                                    arrayList.add(string);

                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(VoiceAddTransactionActivity.this, android.R.layout.simple_spinner_item, arrayList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spr_desc_type.setAdapter(adapter);

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
    private void setPaymentNametype(ArrayList<String> array_paymentypename) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(VoiceAddTransactionActivity.this, R.layout.spinner_item, array_paymentypename);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        spr_payment_type.setAdapter(spinnerArrayAdapter);

        if (strPaymentType != null) {
            int spinnerPosition = spinnerArrayAdapter.getPosition(strPaymentType);
            spr_payment_type.setSelection(spinnerPosition);
        }
        Log.w(TAG,"setPaymentNametypeList-->"+new Gson().toJson(array_paymentypename));

    }

    private void setSubDescrType(ArrayList<String> array_sub_desc_type) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(VoiceAddTransactionActivity.this, R.layout.spinner_item, array_sub_desc_type);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        spr_sub_desc_type.setAdapter(spinnerArrayAdapter);

        if (strSubDecrType != null) {
            int spinnerPosition = spinnerArrayAdapter.getPosition(strSubDecrType);
            spr_sub_desc_type.setSelection(spinnerPosition);
        }
        Log.w(TAG,"setPaymentNametypeList-->"+new Gson().toJson(array_sub_desc_type));

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
                      //  showErrorLoading(response.body().getMessage());
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
        transactionCreateRequest.setTransaction_desc(strDescTypeId);
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
        Log.w(TAG,"transactionValidator strDescType : "+strDescType);
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
            if (new ConnectionDetector(VoiceAddTransactionActivity.this).isNetworkAvailable(VoiceAddTransactionActivity.this)) {
                transactionCreateRequestCall();
                Log.d("ddddd","ffff");

            }
            else {

                Log.d("pppp","ffff");
            }
        }




    }



    public boolean validdPaymentType() {
        if(strPaymentType != null && strPaymentType.equalsIgnoreCase("Select Payment Type")){
            final AlertDialog alertDialog = new AlertDialog.Builder(VoiceAddTransactionActivity.this).create();
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
            final AlertDialog alertDialog = new AlertDialog.Builder(VoiceAddTransactionActivity.this).create();
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
            final AlertDialog alertDialog = new AlertDialog.Builder(VoiceAddTransactionActivity.this).create();
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