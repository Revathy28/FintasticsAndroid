package com.triton.fintastics.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.LoginRequest;
import com.triton.fintastics.responsepojo.SuccessResponse;
import com.triton.fintastics.utils.ConnectionDetector;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;


import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {


    private static final String TAG = "ForgetPasswordActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email)
    EditText edt_email;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_login)
    Button btn_login;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_backtologin)
    RelativeLayout rl_backtologin;

    private Dialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);
        Log.w("Oncreate", TAG);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidEmailValidator();
            }
        });

        rl_backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }



    public void ValidEmailValidator() {
        boolean can_proceed = true;
        String emailAddress = edt_email.getText().toString().trim();
        String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

        if (edt_email.getText().toString().trim().equals("")) {
            edt_email.setError("Please enter the email");
            edt_email.requestFocus();
            can_proceed = false;
        }else if(!emailAddress.matches(emailPattern)){
            edt_email.setError("Please enter correct email address");
            edt_email.requestFocus();
            can_proceed = false;
        }

        if (can_proceed) {
            if (new ConnectionDetector(ForgetPasswordActivity.this).isNetworkAvailable(ForgetPasswordActivity.this)) {
                forgetPasswordResponseCall(emailAddress);
            }
        }

    }




    @SuppressLint("LogNotTimber")
    private void forgetPasswordResponseCall(String emailAddress) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = apiInterface.forgetPasswordResponseCall(RestUtils.getContentType(), loginRequest(emailAddress));
        Log.w(TAG,"ResendOTPResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"forgetPasswordResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        showSuceessLoading(response.body().getMessage());
                    }

                    }
                    else {
                        showErrorLoading(response.body().getMessage());
                    }
                }




            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("forgetresponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private LoginRequest loginRequest(String emailAddress) {
        /*
         * user_email : iddineshkumar@gmail.com
         * password : 123456
         */
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_email(emailAddress);
        Log.w(TAG,"loginRequest"+ new Gson().toJson(loginRequest));
        return loginRequest;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showSuceessLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoadingSuccess());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                alertDialog.dismiss();
            }
        });
    }
    public void hideLoadingSuccess() {
        try {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            alertDialog.dismiss();

        } catch (Exception ignored) {

        }
    }
}