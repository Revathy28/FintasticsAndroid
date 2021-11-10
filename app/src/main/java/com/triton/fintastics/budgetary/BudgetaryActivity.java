package com.triton.fintastics.budgetary;

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

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.activities.DashoardActivity;
import com.triton.fintastics.adapter.AccountSummaryListAdapter;
import com.triton.fintastics.adapter.ExpenseListAdapter;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.BudgetGetlistRequest;
import com.triton.fintastics.requestpojo.UserIdRequest;
import com.triton.fintastics.responsepojo.AccountSummaryResponse;
import com.triton.fintastics.responsepojo.BudgetGetlistResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetaryActivity extends AppCompatActivity {

    private String TAG = "BudgetaryActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_income_graph)
    TextView txt_income_graph;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_expense)
    TextView txt_expense;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_transaction)
    RecyclerView rv_transaction;

    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgetary);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        avi_indicator.setVisibility(View.GONE);
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        user_id = user.get(SessionManager.KEY_ID);


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

        if(user_id != null){
            budgetGetlistRequestCall();
        }


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

    @SuppressLint("LogNotTimber")
    private void budgetGetlistRequestCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<BudgetGetlistResponse> call = apiInterface.budgetGetlistRequestCall(RestUtils.getContentType(), budgetGetlistRequest());
        Log.w(TAG,"accountSummaryResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<BudgetGetlistResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<BudgetGetlistResponse> call, @NonNull Response<BudgetGetlistResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"BudgetGetlistResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        if(response.body().getData().getExpend_value() != 0){
                            txt_expense.setText("\u20B9 " +response.body().getData().getExpend_value());
                        }else{
                            txt_income_graph.setText("");
                        }
                        if(response.body().getData().getTotal_income() != 0){
                            txt_income_graph.setText("\u20B9 " +response.body().getData().getTotal_income());
                        }else{
                            txt_income_graph.setText("");
                        }

                        if(response.body().getData().getExpensive_data() != null && response.body().getData().getExpensive_data().size()>0){
                            setExpenseListView(response.body().getData().getExpensive_data());
                        }



                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<BudgetGetlistResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("BudgetGetlistRes flr", "--->" + t.getMessage());
            }
        });

    }
    private BudgetGetlistRequest budgetGetlistRequest() {
        /*
         * user_id : 617a7c37eeb3a520395e2f15

         */


        BudgetGetlistRequest budgetGetlistRequest = new BudgetGetlistRequest();
        budgetGetlistRequest.setUser_id(user_id);
        Log.w(TAG,"budgetGetlistRequest "+ new Gson().toJson(budgetGetlistRequest));
        return budgetGetlistRequest;
    }


    private void setExpenseListView(List<BudgetGetlistResponse.DataBean.ExpensiveDataBean> expensive_data) {
        rv_transaction.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_transaction.setItemAnimator(new DefaultItemAnimator());
        ExpenseListAdapter expenseListAdapter = new ExpenseListAdapter(getApplicationContext(), expensive_data);
        rv_transaction.setAdapter(expenseListAdapter);

    }

}