package com.triton.fintastics.api;


import com.triton.fintastics.requestpojo.EmailOTPRequest;
import com.triton.fintastics.requestpojo.LoginRequest;
import com.triton.fintastics.requestpojo.ReferralCodeRequest;
import com.triton.fintastics.requestpojo.SignupRequest;
import com.triton.fintastics.responsepojo.EmailOTPResponse;
import com.triton.fintastics.responsepojo.LoginResponse;
import com.triton.fintastics.responsepojo.PaymentTypeListResponse;
import com.triton.fintastics.responsepojo.SignupResponse;
import com.triton.fintastics.responsepojo.SuccessResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApiInterface {




    /*Verify Email otp*/
    @POST("userdetails/send/emailotp")
    Call<EmailOTPResponse> emailOTPResponseCall(@Header("Content-Type") String type, @Body EmailOTPRequest emailOTPRequest);

    /*Verify referral code*/
    @POST("userdetails/check_parent_code")
    Call<SuccessResponse> verifyReferralCodeResponseCall(@Header("Content-Type") String type, @Body ReferralCodeRequest referralCodeRequest);


    /*Signup create*/
    @POST("userdetails/create")
    Call<SignupResponse> signupResponseCall(@Header("Content-Type") String type, @Body SignupRequest signupRequest);

    /*Login*/
    @POST("userdetails/mobile/login")
    Call<LoginResponse> loginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest);


    /*Forgot password*/
    @POST("userdetails/forgotpassword")
    Call<SuccessResponse> forgetPasswordResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest);


    /*Payment Type List */
    @GET("payment_type/getlist")
    Call<PaymentTypeListResponse> paymentTypeListResponseCall(@Header("Content-Type") String type);



}
