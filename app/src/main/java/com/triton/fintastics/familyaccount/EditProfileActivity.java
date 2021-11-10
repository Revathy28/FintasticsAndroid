package com.triton.fintastics.familyaccount;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.activities.DashoardActivity;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.requestpojo.UpdateProfileRequest;
import com.triton.fintastics.responsepojo.SignupResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.utils.ConnectionDetector;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private final String TAG = "EditProfileActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_firstname)
    EditText edt_firstname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_lastname)
    EditText edt_lastname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dob_day)
    TextView txt_dob_day;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dob_month)
    TextView txt_dob_month;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dob_year)
    TextView txt_dob_year;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_calendar)
    ImageView img_calendar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_emailid)
    TextView txt_emailid;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_contactno)
    EditText edt_contactno;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_save)
    Button btn_save;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_mygroup)
    LinearLayout ll_mygroup;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_share)
    ImageView img_share;

    private String userid;
    private String username = "";
    private String password = "";
    private String referalcode = "";
    private String profileimag = "";
    Dialog alertDialog;

    private final int DOB_DATE_PICKER_ID = 1;
    private int year, month, day;

    private String SelectedDOBddate= "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);
        ll_mygroup.setVisibility(View.GONE);
        Log.w(TAG,"onCreate");



        TextView txt_title = include_header.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.edit_profile));

        ImageView img_back = include_header.findViewById(R.id.img_back);
        img_back.setOnClickListener(view -> onBackPressed());

        img_calendar.setOnClickListener(view -> gotoDateOfBirth());

        SessionManager sessionManager = new SessionManager(EditProfileActivity.this);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        username = user.get(SessionManager.KEY_USERNAME);
        password = user.get(SessionManager.KEY_PASSWORD);
        profileimag = user.get(SessionManager.KEY_PROFILE_IMAGE);
        referalcode = user.get(SessionManager.KEY_REF_CODE);

       String firstname = user.get(SessionManager.KEY_FIRSTNAME);
       String lastname = user.get(SessionManager.KEY_LASTNAME);
       SelectedDOBddate = user.get(SessionManager.KEY_DOB);
       String emailid = user.get(SessionManager.KEY_EMAIL_ID);
       String contactnumber = user.get(SessionManager.KEY_CONTACTNUMBER);
       String roll_type = user.get(SessionManager.KEY_ROLL_TYPE);
        if(roll_type != null && roll_type.equalsIgnoreCase("Admin")){
            ll_mygroup.setVisibility(View.VISIBLE);
        }else{
            ll_mygroup.setVisibility(View.GONE);
        }

        ll_mygroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyGrooupActivity.class));

            }
        });

       Log.w(TAG,"dob : "+SelectedDOBddate);

       if(SelectedDOBddate != null && !SelectedDOBddate.isEmpty()){
           String[] getfrom = SelectedDOBddate.split("-");
           day = Integer.parseInt(getfrom[0]);
           month = Integer.parseInt(getfrom[1]);
           year = Integer.parseInt(getfrom[2]);

           if(day != 0){
               txt_dob_day.setText(day+"");
           }
           if(month != 0){
               txt_dob_month.setText(month+"");
           }
           if(year != 0){
               txt_dob_year.setText(year+"");
           }

           Log.w(TAG,"dob : "+"day : "+day+" month : "+month+" year : "+year);


       }

       if(firstname != null){
           edt_firstname.setText(firstname);
       }
        if(lastname != null){
            edt_lastname.setText(lastname);
        }
        if(emailid != null){
            txt_emailid.setText(emailid);
        }
        if(contactnumber != null){
            edt_contactno.setText(contactnumber);
        }

        btn_save.setOnClickListener(view -> editProfileValidator());

        img_share.setOnClickListener(view -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Fintastics");
                String shareMessage= "Your Admin " + username + " have shared a referral code to join their group kindly find the referral code as : " + referalcode;
                shareMessage = shareMessage +""+"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "share via"));
            } catch(Exception e) {
                //e.toString();
            }
        });


    }

    private void gotoDateOfBirth() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        showDialog(DOB_DATE_PICKER_ID);
    }


    @SuppressLint("LogNotTimber")
    @Override
    protected Dialog onCreateDialog(int id) {
        Log.w(TAG,"onCreateDialog id : "+id);
        if (id == DOB_DATE_PICKER_ID) {
            // open datepicker dialog.
            // set date picker for current date
            // add pickerListener listner to date picker
            // return new DatePickerDialog(this, pickerListener, year, month,day);
            DatePickerDialog dialog = new DatePickerDialog(this, pickerListener, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dialog;
        }
        return null;
    }
    private final DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @SuppressLint({"LogNotTimber", "SetTextI18n"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;



            String strdayOfMonth;
            String strMonth;
            int month1 =(month + 1);
            if(day == 9 || day <9){
                strdayOfMonth = "0"+day;
                Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
            }else{
                strdayOfMonth = String.valueOf(day);
            }

            if(month1 == 9 || month1 <9){
                strMonth = "0"+month1;
                Log.w(TAG,"Selected month1-->"+strMonth);
            }else{
                strMonth = String.valueOf(month1);
            }

            SelectedDOBddate = strdayOfMonth + "-" + strMonth + "-" + year;

            // Show selected date
            txt_dob_day.setText(strdayOfMonth);
            txt_dob_month.setText(strMonth);
            txt_dob_year.setText(year+"");

        }
    };



    public void editProfileValidator() {
        boolean can_proceed = true;

        if (Objects.requireNonNull(edt_firstname.getText()).toString().trim().equals("")
                && Objects.requireNonNull(edt_lastname.getText()).toString().trim().equals("")
                && Objects.requireNonNull(edt_contactno.getText()).toString().trim().equals("")

        ) {
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_firstname.getText().toString().trim().equals("")) {
            edt_firstname.setError("Please enter first name");
            edt_firstname.requestFocus();
            can_proceed = false;
        }
        else if (edt_lastname.getText().toString().trim().equals("")) {
            edt_lastname.setError("Please enter last name");
            edt_lastname.requestFocus();
            can_proceed = false;
        }  else if (SelectedDOBddate != null && SelectedDOBddate.isEmpty()) {
            showErrorLoading("Please select date of transaction");
            can_proceed = false;
        } else if (edt_contactno.getText().toString().trim().equals("")) {
            edt_contactno.setError("Please enter contact number");
            edt_contactno.requestFocus();
            can_proceed = false;
        }

        if (can_proceed) {
            if (new ConnectionDetector(EditProfileActivity.this).isNetworkAvailable(EditProfileActivity.this)) {
                UpdateProfileRequestCall();
            }
        }




    }



    @SuppressLint("LogNotTimber")
    private void UpdateProfileRequestCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SignupResponse> call = apiInterface.UpdateProfileRequestCall(RestUtils.getContentType(), updateProfileRequest());
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
                            SessionManager sessionManager = new SessionManager(EditProfileActivity.this);
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
                            startActivity(new Intent(getApplicationContext(),DashoardActivity.class));
                            finish();


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
    private UpdateProfileRequest updateProfileRequest() {
        /*
         * _id : 617a5371299cf71790053363
         * username : 6172b83d8dd3e15b142de045
         * password : 20-20-2021
         * user_email : mohammedimthi2395@gmail.com
         * first_name :
         * last_name :
         * dob : 20-20-2021
         * contact_number :
         * mobile_type :
         * account_type :
         * roll_type :
         * parent_of :
         * profile_img :
         */

        UpdateProfileRequest updateProfileRequest= new UpdateProfileRequest();
        updateProfileRequest.set_id(userid);
        updateProfileRequest.setUsername(username);
        updateProfileRequest.setPassword(password);
        updateProfileRequest.setUser_email(txt_emailid.getText().toString());
        updateProfileRequest.setFirst_name(edt_firstname.getText().toString());
        updateProfileRequest.setLast_name(edt_lastname.getText().toString());
        updateProfileRequest.setDob(SelectedDOBddate);
        updateProfileRequest.setContact_number(edt_contactno.getText().toString());
        updateProfileRequest.setMobile_type("Android");
        updateProfileRequest.setAccount_type("");
        updateProfileRequest.setRoll_type("");
        updateProfileRequest.setParent_of(referalcode);
        updateProfileRequest.setProfile_img(profileimag);

        Log.w(TAG,"updateProfileRequest "+ new Gson().toJson(updateProfileRequest));
        return updateProfileRequest;
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


