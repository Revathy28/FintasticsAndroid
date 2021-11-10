package com.triton.fintastics.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.ChangePasswordRequest;
import com.triton.fintastics.responsepojo.SignupResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.ConnectionDetector;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private final String TAG = "ChangePasswordActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_exstpaswd)
    EditText edt_exstpaswd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_newpaswd)
    EditText edt_newpaswd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_cnfmpaswd)
    EditText edt_cnfmpaswd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_save)
    Button btn_save;

    String userid;
    String password;
    Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        avi_indicator.setVisibility(View.GONE);


        SessionManager sessionManager = new SessionManager(ChangePasswordActivity.this);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        password = user.get(SessionManager.KEY_PASSWORD);
        Log.w(TAG,"userid--->"+userid+" password :"+password);

        ImageView img_back = include_header.findViewById(R.id.img_back);
        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.change_password));

        img_back.setOnClickListener(view -> onBackPressed());

        btn_save.setOnClickListener(view -> changePasswordValidator());
    }


    public void changePasswordValidator() {
       boolean can_proceed = true;
        if (Objects.requireNonNull(edt_exstpaswd.getText()).toString().trim().equals("")
                && Objects.requireNonNull(edt_newpaswd.getText()).toString().trim().equals("")
                && Objects.requireNonNull(edt_cnfmpaswd.getText()).toString().trim().equals("")

        ) {
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_exstpaswd.getText().toString().trim().equals("")) {
            edt_exstpaswd.setError("Please enter existing password");
            edt_exstpaswd.requestFocus();
            can_proceed = false;
        }
        else if (edt_newpaswd.getText().toString().trim().equals("")) {
            edt_newpaswd.setError("Please enter new password");
            edt_newpaswd.requestFocus();
            can_proceed = false;
        }else if(edt_newpaswd.getText().toString().trim().equalsIgnoreCase(password)) {
            edt_cnfmpaswd.setError("existing password and new password cannot be same");
            edt_cnfmpaswd.requestFocus();
            can_proceed = false;
        }
        else if (edt_cnfmpaswd.getText().toString().trim().equals("")) {
            edt_cnfmpaswd.setError("Please enter confirm new password");
            edt_cnfmpaswd.requestFocus();
            can_proceed = false;
        }else if (!edt_newpaswd.getText().toString().trim().equalsIgnoreCase(edt_cnfmpaswd.getText().toString().trim())) {
            edt_cnfmpaswd.setError("Passwords did not match");
            edt_cnfmpaswd.requestFocus();
            can_proceed = false;
        }

        if (can_proceed) {
            if (new ConnectionDetector(ChangePasswordActivity.this).isNetworkAvailable(ChangePasswordActivity.this)) {
                changePasswordResponseCall();
            }
        }




    }

    @SuppressLint("LogNotTimber")
    private void changePasswordResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SignupResponse> call = apiInterface.changePasswordResponseCall(RestUtils.getContentType(), changePasswordRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SignupResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SignupResponse> call, @NonNull Response<SignupResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if(response.body().getData() != null) {
                            SessionManager sessionManager = new SessionManager(ChangePasswordActivity.this);
                            sessionManager.setIsLogin(true);
                            sessionManager.createLoginSession(
                                    response.body().getData().get_id(),
                                    response.body().getData().getUsername(),
                                    response.body().getData().getUser_email(),
                                    response.body().getData().getParent_code(),
                                    response.body().getData().getPassword(),
                                    response.body().getData().getFirst_name(),
                                    response.body().getData().getLast_name(),
                                    response.body().getData().getDob(),
                                    response.body().getData().getContact_number(),
                                    response.body().getData().getAccount_type(),
                                    response.body().getData().getRoll_type()

                            );
                           

                        }


                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SignupResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("SignupResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private ChangePasswordRequest changePasswordRequest() {
        /*
         * _id : 617a5371299cf71790053363
         * password : 20-20-2021
         */

        ChangePasswordRequest changePasswordRequest= new ChangePasswordRequest();
        changePasswordRequest.set_id(userid);
        changePasswordRequest.setPassword(edt_cnfmpaswd.getText().toString());
       
        Log.w(TAG,"changePasswordRequest "+ new Gson().toJson(changePasswordRequest));
        return changePasswordRequest;
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
        startActivity(new Intent(getApplicationContext(), DashoardActivity.class));
        finish();
    }
}