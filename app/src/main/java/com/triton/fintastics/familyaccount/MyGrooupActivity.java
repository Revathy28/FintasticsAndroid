package com.triton.fintastics.familyaccount;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.adapter.ChildDetailsListAdapter;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.interfaces.SelectChildMemberListner;
import com.triton.fintastics.requestpojo.BlockUnblockRequest;
import com.triton.fintastics.requestpojo.FetchChildDetailsRequest;
import com.triton.fintastics.responsepojo.FetchChildDetailsResponse;
import com.triton.fintastics.responsepojo.SuccessResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyGrooupActivity extends AppCompatActivity implements SelectChildMemberListner {

    private final String TAG = "MyGrooupActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_childdetails)
    RecyclerView rv_childdetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;



    private String userid;
    private String username = "";
    private String password = "";
    private String referalcode = "";
    private String profileimag = "";
    Dialog alertDialog;

    private final int DOB_DATE_PICKER_ID = 1;
    private int year, month, day;

    private String SelectedDOBddate= "";
    private Dialog dialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygroup);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        Log.w(TAG,"onCreate");



        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.my_group));

        ImageView img_back = include_header.findViewById(R.id.img_back);
        img_back.setOnClickListener(view -> onBackPressed());


        SessionManager sessionManager = new SessionManager(MyGrooupActivity.this);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        username = user.get(SessionManager.KEY_USERNAME);
        password = user.get(SessionManager.KEY_PASSWORD);
        profileimag = user.get(SessionManager.KEY_PROFILE_IMAGE);
        referalcode = user.get(SessionManager.KEY_REF_CODE);

        if(referalcode != null && !referalcode.isEmpty()){
            fetchChildDetailsResponseCall();
        }







    }



    @SuppressLint("LogNotTimber")
    private void fetchChildDetailsResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<FetchChildDetailsResponse> call = apiInterface.fetchChildDetailsResponseCall(RestUtils.getContentType(), fetchChildDetailsRequest());
        Log.w(TAG,"fetchChildDetailsResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FetchChildDetailsResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FetchChildDetailsResponse> call, @NonNull Response<FetchChildDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"fetchChildDetailsResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        if(response.body().getData() != null && response.body().getData().size()>0){
                            txt_no_records.setVisibility(View.GONE);
                            rv_childdetails.setVisibility(View.VISIBLE);
                            setChildDetailsListView(response.body().getData());

                        }else{
                            rv_childdetails.setVisibility(View.GONE);
                            txt_no_records.setVisibility(View.VISIBLE);
                        }

                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<FetchChildDetailsResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("FetchChildDetails flr", "--->" + t.getMessage());
            }
        });

    }

    private void setChildDetailsListView(List<FetchChildDetailsResponse.DataBean> data) {
        rv_childdetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_childdetails.setItemAnimator(new DefaultItemAnimator());
        ChildDetailsListAdapter childDetailsListAdapter = new ChildDetailsListAdapter(getApplicationContext(), data,this);
        rv_childdetails.setAdapter(childDetailsListAdapter);
    }

    private FetchChildDetailsRequest fetchChildDetailsRequest() {
        /*
         * parent_of :
         *
         */

        FetchChildDetailsRequest fetchChildDetailsRequest = new FetchChildDetailsRequest();
        fetchChildDetailsRequest.setParent_of(referalcode);
        Log.w(TAG,"fetchChildDetailsRequest "+ new Gson().toJson(fetchChildDetailsRequest));
        return fetchChildDetailsRequest;
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

    @Override
    public void selectChildMemberListner(String id, boolean status) {
        showBlockUserAlert(id,status);

    }

    private void showBlockUserAlert(String id, boolean status) {
        try {

            dialog = new Dialog(MyGrooupActivity.this);
            dialog.setContentView(R.layout.alert_logout_layout);
            TextView alert_header_txtview = dialog.findViewById(R.id.alert_header_txtview);
            TextView txt_content = dialog.findViewById(R.id.txt_content);
            Button btn_no = dialog.findViewById(R.id.btn_no);
            Button btn_yes = dialog.findViewById(R.id.btn_yes);

            alert_header_txtview.setText(getResources().getString(R.string.app_name));
            if(status){
                txt_content.setText(getResources().getString(R.string.are_you_sure_you_want_to_unblock));
            }else{
                txt_content.setText(getResources().getString(R.string.are_you_sure_you_want_to_block));

            }

            btn_yes.setOnClickListener(view -> {
                dialog.dismiss();
                if(status){
                    blockunblockuserResponseCall(false,id);
                }else{
                    blockunblockuserResponseCall(true,id);
                }



            });
            btn_no.setOnClickListener(view -> dialog.dismiss());
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }

    @SuppressLint("LogNotTimber")
    private void blockunblockuserResponseCall(boolean status,String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = apiInterface.blockunblockuserResponseCall(RestUtils.getContentType(), blockUnblockRequest(status,id));
        Log.w(TAG,"blockunblockuserResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"blockunblockuserResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        if(referalcode != null && !referalcode.isEmpty()){
                            fetchChildDetailsResponseCall();
                        }

                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("blockunblock flr", "--->" + t.getMessage());
            }
        });

    }

    private BlockUnblockRequest blockUnblockRequest(boolean status, String id) {
        /*
         * user_id : 618230269dcc2a290e5bae9a
         * delete_status : true
         */


        BlockUnblockRequest blockUnblockRequest = new BlockUnblockRequest();
        blockUnblockRequest.setUser_id(id);
        blockUnblockRequest.setDelete_status(status);
        Log.w(TAG,"blockUnblockRequest "+ new Gson().toJson(blockUnblockRequest));
        return blockUnblockRequest;
    }


}


