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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.EmailOTPRequest;
import com.triton.fintastics.requestpojo.ReferralCodeRequest;
import com.triton.fintastics.requestpojo.SignupRequest;
import com.triton.fintastics.responsepojo.EmailOTPResponse;
import com.triton.fintastics.responsepojo.SignupResponse;
import com.triton.fintastics.responsepojo.SuccessResponse;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_usrname)
    EditText edt_usrname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email)
    EditText edt_email;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_verify_email)
    Button btn_verify_email;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_verifycode)
    LinearLayout ll_verifycode;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_verifycode)
    EditText edt_verifycode;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_passwd)
    LinearLayout ll_passwd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_passwd)
    EditText edt_passwd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_confirmpasswd)
    LinearLayout ll_confirmpasswd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_confirmpasswd)
    EditText edt_confirmpasswd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_referralcode)
    LinearLayout ll_referralcode;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_referralcode)
    EditText edt_referralcode;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_login)
    Button btn_login;


    private Dialog alertDialog;
    private String refferalcode = "";

    boolean can_proceed = true;
    private String emailotp = "";
    private String accounttype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Log.w("Oncreate", TAG);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            accounttype = extras.getString("accounttype");
        }

        if(accounttype != null && accounttype.equalsIgnoreCase("Personal")){
            ll_referralcode.setVisibility(View.GONE);
        }else{
            ll_referralcode.setVisibility(View.VISIBLE);
        }

        btn_verify_email.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                signUpValidator();
                break;
            case R.id.img_back:
                onBackPressed();
                break;

            case R.id.btn_verify_email:
                ValidEmailValidator();
                break;

        }
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
            if (new ConnectionDetector(SignUpActivity.this).isNetworkAvailable(SignUpActivity.this)) {
                emailOTPResponseCall(emailAddress);
            }
        }

    }

    @SuppressLint("LogNotTimber")
    private void emailOTPResponseCall(String emailid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<EmailOTPResponse> call = apiInterface.emailOTPResponseCall(RestUtils.getContentType(), emailOTPRequest(emailid));
        Log.w(TAG,"EmailOTPResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<EmailOTPResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<EmailOTPResponse> call, @NonNull Response<EmailOTPResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"EmailOTPResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if(response.body().getData() != null) {
                           emailotp = response.body().getData().getOtp();

                        }


                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<EmailOTPResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("Email OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private EmailOTPRequest emailOTPRequest(String emailid) {
        /*
         * user_email : mohammedimthi2395@gmail.com
         */
        EmailOTPRequest emailOTPRequest = new EmailOTPRequest();
        emailOTPRequest.setUser_email(emailid);
        Log.w(TAG,"EmailOTPRequest "+ new Gson().toJson(emailOTPRequest));
        return emailOTPRequest;
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

    public void signUpValidator() {
        refferalcode  = edt_referralcode.getText().toString().trim();
        Log.w(TAG,"refferalcode : "+refferalcode);
        if(refferalcode != null && refferalcode.isEmpty()){
            can_proceed = true;
        }else{
            refferalcode = "";
            can_proceed = false;
        }
        if (Objects.requireNonNull(edt_usrname.getText()).toString().trim().equals("")
                && Objects.requireNonNull(edt_email.getText()).toString().trim().equals("")
                && Objects.requireNonNull(edt_verifycode.getText()).toString().trim().equals("")
                && Objects.requireNonNull(edt_passwd.getText()).toString().trim().equals("")
                && Objects.requireNonNull(edt_confirmpasswd.getText()).toString().trim().equals("")
            ) {
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_usrname.getText().toString().trim().equals("")) {
            edt_usrname.setError("Please enter user name");
            edt_usrname.requestFocus();
            can_proceed = false;
        }
        else if (edt_email.getText().toString().trim().equals("")) {
            edt_email.setError("Please enter email");
            edt_email.requestFocus();
            can_proceed = false;
        } else if (edt_verifycode.getText().toString().trim().equals("")) {
            edt_verifycode.setError("Please enter email verify code");
            edt_verifycode.requestFocus();
            can_proceed = false;
        }else if (emailotp != null && !emailotp.equalsIgnoreCase(edt_verifycode.getText().toString().trim()) ) {
            edt_verifycode.setError("Please enter valid email verify code");
            edt_verifycode.requestFocus();
            can_proceed = false;
        }else if (edt_passwd.getText().toString().trim().equals("")) {
            edt_passwd.setError("Please enter password");
            edt_passwd.requestFocus();
            can_proceed = false;
        }else if (edt_confirmpasswd.getText().toString().trim().equals("")) {
            edt_confirmpasswd.setError("Please enter password");
            edt_confirmpasswd.requestFocus();
            can_proceed = false;
        }else if (!edt_passwd.getText().toString().trim().equalsIgnoreCase(edt_confirmpasswd.getText().toString().trim())) {
            edt_confirmpasswd.setError("Passwords did not match");
            edt_confirmpasswd.requestFocus();
            can_proceed = false;
        }else if (refferalcode != null && !refferalcode.isEmpty()) {
            refferalcode = edt_referralcode.getText().toString().trim();
            can_proceed = false;
            verifyReferralCodeResponseCall();
        }

        if (can_proceed) {
            if (new ConnectionDetector(SignUpActivity.this).isNetworkAvailable(SignUpActivity.this)) {
                signupResponseCall();
            }
        }




    }

    @SuppressLint("LogNotTimber")
    private void signupResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SignupResponse> call = apiInterface.signupResponseCall(RestUtils.getContentType(), signupRequest());
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
                            SessionManager sessionManager = new SessionManager(SignUpActivity.this);
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
                            gotoDashboard();

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
    private SignupRequest signupRequest() {
        /*
         * username : 6172b83d8dd3e15b142de045
         * password : 20-20-2021
         * user_email : mohammedimthi2395@gmail.com
         * mobile_type : Android
         * parent_of : 133we
         * account_type:
         */

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(edt_usrname.getText().toString().trim());
        signupRequest.setPassword(edt_passwd.getText().toString().trim());
        signupRequest.setUser_email(edt_email.getText().toString().trim());
        signupRequest.setMobile_type("Android");
        signupRequest.setParent_of(refferalcode);
        signupRequest.setAccount_type(accounttype);
        Log.w(TAG,"signupRequest "+ new Gson().toJson(signupRequest));
        return signupRequest;
    }
    public void gotoDashboard() {
        Intent i = new Intent(SignUpActivity.this, DashoardActivity.class);
        startActivity(i);
    }

    @SuppressLint("LogNotTimber")
    private void verifyReferralCodeResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = apiInterface.verifyReferralCodeResponseCall(RestUtils.getContentType(), referralCodeRequest());
        Log.w(TAG,"verifyReferralCodeResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"verifyReferralCodeResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        can_proceed = true;
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if(response.body().getData() != null) {

                        }


                    } else {
                        showErrorLoading(response.body().getMessage());
                        can_proceed = false;
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("verifyReferral flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private ReferralCodeRequest referralCodeRequest() {
        /*
         * parent_code : 876191
         */
        ReferralCodeRequest referralCodeRequest = new ReferralCodeRequest();
        referralCodeRequest.setParent_code(edt_referralcode.getText().toString().trim());
        Log.w(TAG,"referralCodeRequest "+ new Gson().toJson(referralCodeRequest));
        return referralCodeRequest;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}