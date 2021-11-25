package com.triton.fintastics.accountsummary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.activities.DashoardActivity;
import com.triton.fintastics.adapter.AccountSummaryListAdapter;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.UserIdRequest;
import com.triton.fintastics.responsepojo.AccountSummaryResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountSummaryActivity extends AppCompatActivity {
    private final String TAG = "AccountSummaryActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_accountsummary)
    RecyclerView rv_accountsummary;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_available_balance)
    TextView txt_available_balance;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_debit)
    TextView txt_debit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_credit)
    TextView txt_credit;


    private String user_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_summary);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        avi_indicator.setVisibility(View.GONE);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        user_id = user.get(SessionManager.KEY_ID);

        ImageView img_back = include_header.findViewById(R.id.img_back);
        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.account_summary));

        img_back.setOnClickListener(view -> onBackPressed());

        accountSummaryResponseCall();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashoardActivity.class));
        finish();
    }

    @SuppressLint("LogNotTimber")
    private void accountSummaryResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AccountSummaryResponse> call = apiInterface.accountSummaryResponseCall(RestUtils.getContentType(), userIdRequest());
        Log.w(TAG,"accountSummaryResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AccountSummaryResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<AccountSummaryResponse> call, @NonNull Response<AccountSummaryResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"transactionReportResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null && response.body().getData().size()>0){
                            txt_no_records.setVisibility(View.GONE);
                            rv_accountsummary.setVisibility(View.VISIBLE);
                            setAccountSummaryListView(response.body().getData());

                        }else{
                            rv_accountsummary.setVisibility(View.GONE);
                            txt_no_records.setVisibility(View.VISIBLE);
                        }

                        if(response.body().getBalance() != null){
                            txt_available_balance.setText("\u20B9 " +response.body().getBalance().getBalance_amount());
                            txt_debit.setText("\u20B9 " +response.body().getBalance().getDebit_amount());
                            txt_credit.setText("\u20B9 " +response.body().getBalance().getCredit_amount());
                        }

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<AccountSummaryResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("AccountSummaryRes flr", "--->" + t.getMessage());
            }
        });

    }
    private UserIdRequest userIdRequest() {
        /*
         * user_id : 617a7c37eeb3a520395e2f15

         */


        UserIdRequest userIdRequest = new UserIdRequest();
        userIdRequest.setUser_id(user_id);
        Log.w(TAG,"userIdRequest "+ new Gson().toJson(userIdRequest));
        return userIdRequest;
    }
    private void setAccountSummaryListView(List<AccountSummaryResponse.DataBean> data) {
        rv_accountsummary.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_accountsummary.setItemAnimator(new DefaultItemAnimator());
        AccountSummaryListAdapter accountSummaryListAdapter = new AccountSummaryListAdapter(getApplicationContext(), data);
        rv_accountsummary.setAdapter(accountSummaryListAdapter);

    }
}