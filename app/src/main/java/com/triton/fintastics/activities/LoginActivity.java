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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.LoginRequest;
import com.triton.fintastics.responsepojo.LoginResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.ConnectionDetector;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_usrname)
    EditText edt_usrname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_passwd)
    EditText edt_passwd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_login)
    Button btn_login;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_forgetpassword)
    RelativeLayout rl_forgetpassword;

    private Dialog alertDialog;
    private String accounttype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);
        Log.w("Oncreate", TAG);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            accounttype = extras.getString("accounttype");
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginValidator();
            }
        });
        rl_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgetPasswordActivity.class));

            }
        });
    }

    public void gotosignup(View view){

        Intent i=new Intent(LoginActivity.this,
                SignUpActivity.class);
        //Intent is used to switch from one activity to another.
        i.putExtra("accounttype",accounttype);

        startActivity(i);
        //invoke the SecondActivity.
    }


    public void loginValidator() {
        boolean can_proceed = true;

        if (Objects.requireNonNull(edt_usrname.getText()).toString().trim().equals("")
                && Objects.requireNonNull(edt_passwd.getText()).toString().trim().equals("")

        ) {
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_usrname.getText().toString().trim().equals("")) {
            edt_usrname.setError("Please enter email");
            edt_usrname.requestFocus();
            can_proceed = false;
        }
        else if (edt_passwd.getText().toString().trim().equals("")) {
            edt_passwd.setError("Please enter password");
            edt_passwd.requestFocus();
            can_proceed = false;
        }
        if (can_proceed) {
            if (new ConnectionDetector(LoginActivity.this).isNetworkAvailable(LoginActivity.this)) {
                loginResponseCall();
            }
        }




    }


    @SuppressLint("LogNotTimber")
    private void loginResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LoginResponse> call = apiInterface.loginResponseCall(RestUtils.getContentType(), loginRequest());
        Log.w(TAG,"ResendOTPResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<LoginResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"ResendOTPResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {

                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                        if(response.body().getData() != null) {
                            SessionManager sessionManager = new SessionManager(LoginActivity.this);
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
                                    response.body().getData().getRoll_type(),
                                    response.body().getData().getProfile_img()

                            );
                            Intent intent = new Intent(LoginActivity.this, DashoardActivity.class);
                            startActivity(intent);
                        }

                    }
                    else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("LoginResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private LoginRequest loginRequest() {
        /*
         * user_email : iddineshkumar@gmail.com
         * password : 123456
         */
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_email(edt_usrname.getText().toString());
        loginRequest.setPassword(edt_passwd.getText().toString());
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
}