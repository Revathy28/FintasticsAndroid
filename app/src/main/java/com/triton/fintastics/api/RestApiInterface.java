package com.triton.fintastics.api;


import com.triton.fintastics.requestpojo.SignupRequest;
import com.triton.fintastics.responsepojo.SignupResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApiInterface {

  /*  *//*splash screen list*//*
    @GET("splashscreen/getlist")
    Call<SplashScreenResponse> splashScreenResponseCall(@Header("Content-Type") String type);*/


    /*Signup create*/
    @POST("userdetails/create")
    Call<SignupResponse> signupResponseCall(@Header("Content-Type") String type, @Body SignupRequest signupRequest);


   
}
