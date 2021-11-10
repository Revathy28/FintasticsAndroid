package com.triton.fintastics.api;


import com.triton.fintastics.requestpojo.BlockUnblockRequest;
import com.triton.fintastics.requestpojo.BudgetGetlistRequest;
import com.triton.fintastics.requestpojo.ChangePasswordRequest;
import com.triton.fintastics.requestpojo.DashboardDataRequest;
import com.triton.fintastics.requestpojo.EmailOTPRequest;
import com.triton.fintastics.requestpojo.FetchChildDetailsRequest;
import com.triton.fintastics.requestpojo.LoginRequest;
import com.triton.fintastics.requestpojo.MovementReportDataRequest;
import com.triton.fintastics.requestpojo.ReferralCodeRequest;
import com.triton.fintastics.requestpojo.ReportDataRequest;
import com.triton.fintastics.requestpojo.SignupRequest;
import com.triton.fintastics.requestpojo.TransactionCreateRequest;
import com.triton.fintastics.requestpojo.UpdateProfileRequest;
import com.triton.fintastics.requestpojo.UserIdRequest;
import com.triton.fintastics.requestpojo.YearsListRequest;
import com.triton.fintastics.responsepojo.AccountSummaryResponse;
import com.triton.fintastics.responsepojo.BudgetGetlistResponse;
import com.triton.fintastics.responsepojo.DashboardDataResponse;
import com.triton.fintastics.responsepojo.EmailOTPResponse;
import com.triton.fintastics.responsepojo.FetchChildDetailsResponse;
import com.triton.fintastics.responsepojo.IncomeReportResponse;
import com.triton.fintastics.responsepojo.LoginResponse;
import com.triton.fintastics.responsepojo.PaymentTypeListResponse;
import com.triton.fintastics.responsepojo.SignupResponse;
import com.triton.fintastics.responsepojo.SuccessResponse;
import com.triton.fintastics.responsepojo.TransactionGetBalanceResponse;
import com.triton.fintastics.responsepojo.TransactionReportResponse;
import com.triton.fintastics.responsepojo.YearsListResponse;

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

    /*Change password*/
    @POST("userdetails/mobile/update/profile")
    Call<SignupResponse> changePasswordResponseCall(@Header("Content-Type") String type, @Body ChangePasswordRequest changePasswordRequest);

    /*Payment Type List */
    @GET("payment_type/getlist")
    Call<PaymentTypeListResponse> paymentTypeListResponseCall(@Header("Content-Type") String type);

    /*Transaction get balance*/
    @POST("transaction/get_balance_amount")
    Call<TransactionGetBalanceResponse> transactionGetBalanceAmountRequestCall(@Header("Content-Type") String type, @Body UserIdRequest userIdRequest);


    /*Transaction Create*/
    @POST("transaction/create")
    Call<SuccessResponse> transactionCreateRequestCall(@Header("Content-Type") String type, @Body TransactionCreateRequest transactionCreateRequest);

    /*Update profile*/
    @POST("userdetails/mobile/update/profile")
    Call<SignupResponse> UpdateProfileRequestCall(@Header("Content-Type") String type, @Body UpdateProfileRequest updateProfileRequest);


    /*Dashboard Data*/
    @POST("transaction/dashboard/data")
    Call<DashboardDataResponse> dashboardDataResponseCall(@Header("Content-Type") String type, @Body DashboardDataRequest dashboardDataRequest);

    /*Years list*/
    @POST("userdetails/year_list")
    Call<YearsListResponse> yearsListResponseCall(@Header("Content-Type") String type, @Body YearsListRequest yearsListRequest);

    /*budget list*/
    @POST("transaction/budget_getlist_id")
    Call<BudgetGetlistResponse> budgetGetlistRequestCall(@Header("Content-Type") String type, @Body BudgetGetlistRequest budgetGetlistRequest);


    /*Income report*/
    @POST("transaction/income/report")
    Call<IncomeReportResponse> incomeReportResponseCall(@Header("Content-Type") String type, @Body ReportDataRequest reportDataRequest);


    /*Expenditure report*/
    @POST("transaction/expenditure/report")
    Call<IncomeReportResponse> expenditureReportResponseCall(@Header("Content-Type") String type, @Body ReportDataRequest reportDataRequest);

    /*Transaction report*/
    @POST("transaction/transaction/report")
    Call<IncomeReportResponse> transactionReportResponseCall(@Header("Content-Type") String type, @Body ReportDataRequest reportDataRequest);

    /*Account summary report*/
    @POST("transaction/accountsummery/data")
    Call<AccountSummaryResponse> accountSummaryResponseCall(@Header("Content-Type") String type, @Body UserIdRequest userIdRequest);

    /*Transaction report*/
    @POST("transaction/movement/report")
    Call<IncomeReportResponse> movementReportResponseCall(@Header("Content-Type") String type, @Body MovementReportDataRequest movementReportDataRequest);

   /*Fetch child details*/
    @POST("userdetails/fetch_child")
    Call<FetchChildDetailsResponse> fetchChildDetailsResponseCall(@Header("Content-Type") String type, @Body FetchChildDetailsRequest fetchChildDetailsRequest );

    /*Child block_unblock_user*/
    @POST("userdetails/block_unblock_user")
    Call<SuccessResponse> blockunblockuserResponseCall(@Header("Content-Type") String type, @Body BlockUnblockRequest blockUnblockRequest );


}
