package com.triton.fintastics.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.accountsummary.AccountSummaryActivity;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.budgetary.BudgetaryActivity;
import com.triton.fintastics.chat.ChatActivity;
import com.triton.fintastics.expenditurereport.ExpenditureReportActivity;
import com.triton.fintastics.familyaccount.EditProfileActivity;
import com.triton.fintastics.incomereport.IncomeReportActivity;
import com.triton.fintastics.movementlist.MovementListActivity;
import com.triton.fintastics.requestpojo.DashboardDataRequest;
import com.triton.fintastics.responsepojo.DashboardDataResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.transaction.AddTransactionActivity;
import com.triton.fintastics.transactionreport.TranscationReportActivity;
import com.triton.fintastics.utils.RestUtils;


import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardNavigationDrawer extends AppCompatActivity implements View.OnClickListener{



    String TAG ="DashboardNavigationDrawer";

    private DrawerLayout drawerLayout;
    LayoutInflater inflater;
    View view, header;
    Toolbar toolbar;

    private View toolbar_layout;

    private ActionBarDrawerToggle drawerToggle;
    ImageView drawerImg;
    CircleImageView nav_header_imageView;
    FrameLayout frameLayout;
    TextView nav_header_profilename,nav_header_accountype;
    SessionManager session;
    String name, image_url, phoneNo;



    public Menu menu;




    ProgressDialog progressDialog;
    private Dialog dialog;
    private String user_id;

    Menu nav_Menu;
    private String account_type;
    private String roll_type;


    @SuppressLint({"InflateParams", "LogNotTimber", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        Log.w(TAG, "onCreate---->");

        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.navigation_drawer_layout_new, null);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        name = user.get(SessionManager.KEY_USERNAME);
        user_id = user.get(SessionManager.KEY_ID);

        account_type = user.get(SessionManager.KEY_ACCOUNT_TYPE);
        roll_type = user.get(SessionManager.KEY_ROLL_TYPE);
        Log.w(TAG,"userid-->"+user_id+" account_type : "+account_type+" roll_type : "+roll_type);


        if(user_id != null){
            dashboardDataResponseCall();
        }


        Log.w(TAG, "user details--->" + "name :" + " " + name + " " + "image_url :" + image_url);

        initUI(view);
        initToolBar(view);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @SuppressLint("NonConstantResourceId")
    private void initUI(View view) {

        //Initializing NavigationView
        NavigationView navigationView = view.findViewById(R.id.nav_view);

        navigationView.setItemIconTintList(null);

        frameLayout = view.findViewById(R.id.base_container);


        menu = navigationView.getMenu();



        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = view.findViewById(R.id.drawer_layout);
        header = navigationView.getHeaderView(0);
        nav_header_imageView = header.findViewById(R.id.img_circular);
        nav_header_profilename = header.findViewById(R.id.txt_custcare);
        nav_header_accountype = header.findViewById(R.id.txt_account_type);

        if(account_type != null && account_type.equalsIgnoreCase("Family")){
            if(roll_type != null && roll_type.equalsIgnoreCase("Admin")){
                nav_header_accountype.setText(roll_type);
            }else{
                nav_header_accountype.setText("");
            }
        }
        if(account_type != null && account_type.equalsIgnoreCase("Personal")){
            nav_header_accountype.setText(account_type);
        }

        Glide.with(this).load(R.drawable.nav_headerprofile).circleCrop().into(nav_header_imageView);


        nav_header_profilename.setText(name);

        RelativeLayout rl_editprofile = header.findViewById(R.id.rl_editprofile);
        rl_editprofile.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EditProfileActivity.class)));

        nav_Menu = navigationView.getMenu();


        navigationView.setNavigationItemSelectedListener(menuItem -> {
            //Closing drawer on item click
            drawerLayout.closeDrawers();

            //Check to see which item was being clicked and perform appropriate action
            switch (menuItem.getItemId()) {
                //Replacing the main content with ContentFragment Which is our Inbox View;

                case R.id.nav_item_one:
                    gotoAddTransaction();
                    return true;
                case R.id.nav_item_two:
                    gotoIncomeReport();
                    return true;

                // For rest of the options we just show a toast on click
                case R.id.nav_item_three:
                  gotoExpenditureReport();
                    return true;

                case R.id.nav_item_four:
                    gotoTranscationReport();
                    return true;

                case R.id.nav_item_five:
                    gotoMovementList();
                    return true;

                case R.id.nav_item_six:
                    gotoAccountSummary();
                    return true;

                case R.id.nav_item_seven:
                    gotoChangePassword();
                    return true;
                case R.id.nav_item_eight:
                    gotoBudgetary();
                    return true;

                case R.id.nav_item_nine:
                    gotoChat();
                    return true;





                default:
                    return true;

            }
        });

    }



    private void initToolBar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar_layout = view.findViewById(R.id.include_dashboard_toolbar);
        drawerImg = toolbar_layout.findViewById(R.id.img_menu);



        RelativeLayout rl_logout = toolbar_layout.findViewById(R.id.rl_logout);
        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogOutAppAlert();
            }
        });



        toggleView();
    }







    private void toggleView() {
        drawerImg.setOnClickListener(v -> {
            if (v.isClickable()) {
                drawerMethod();
            } else {

                Intent intent_re = getIntent();
                overridePendingTransition(0, 0);
                intent_re.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent_re);

            }
        });
    }
    public void drawerMethod() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }

    }
    public void setContentView(int layoutId) {
        Log.e("BaseOncreate", "setContentView");
        View activityView = inflater.inflate(layoutId, null);
        frameLayout.addView(activityView);
        super.setContentView(view);
       // drawerMethod();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_menu) {
            //drawerMethod();
        }
    }


    private void gotoChangePassword() {

        startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
    }

    private void gotoTranscationReport() {
        startActivity(new Intent(getApplicationContext(), TranscationReportActivity.class));

    }
    private void gotoAccountSummary() {
        startActivity(new Intent(getApplicationContext(), AccountSummaryActivity.class));

    }
    private void gotoBudgetary() {
        startActivity(new Intent(getApplicationContext(), BudgetaryActivity.class));

    }
    private void gotoChat() {
        startActivity(new Intent(getApplicationContext(), ChatActivity.class));

    }
    private void gotoMovementList() {
        startActivity(new Intent(getApplicationContext(), MovementListActivity.class));

    }


    private void gotoExpenditureReport() {
        startActivity(new Intent(getApplicationContext(), ExpenditureReportActivity.class));

    }

    private void gotoIncomeReport() {
        startActivity(new Intent(getApplicationContext(), IncomeReportActivity.class));

    }
    private void gotoAddTransaction() {
        startActivity(new Intent(getApplicationContext(), AddTransactionActivity.class));

    }

/*


    private void confirmLogoutDialog(){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(PetLoverNavigationDrawer.this);
        alertDialogBuilder.setMessage("Are you sure want to logout?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                        gotoLogout();


                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogBuilder.setCancelable(true);
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    private void gotoMyAppointments() {
        startActivity(new Intent(getApplicationContext(),PetMyappointmentsActivity.class));

    }
    private void gotoMyOrders() {
        startActivity(new Intent(getApplicationContext(),PetMyOrdrersActivity.class));

    }
    private void gotoSOS() {
        startActivity(new Intent(getApplicationContext(), SoSActivity.class));

    }

*/



   /* @Override
    public void soSCallListener(long phonenumber) {
        if(phonenumber != 0){
            sosPhonenumber = String.valueOf(phonenumber);
        }
    }*/

    private void showLogOutAppAlert() {
        try {

            dialog = new Dialog(DashboardNavigationDrawer.this);
            dialog.setContentView(R.layout.alert_logout_layout);
            Button btn_no = dialog.findViewById(R.id.btn_no);
            Button btn_yes = dialog.findViewById(R.id.btn_yes);

            btn_yes.setOnClickListener(view -> {
                dialog.dismiss();
                gotoLogout();

            });
            btn_no.setOnClickListener(view -> dialog.dismiss());
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void gotoLogout() {
        session.logoutUser();
        session.setIsLogin(false);
        startActivity(new Intent(getApplicationContext(), ChooseAccountTypeActivity.class));
        finish();




    }

    @SuppressLint("LogNotTimber")
    private void dashboardDataResponseCall() {
       /* avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();*/
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<DashboardDataResponse> call = apiInterface.dashboardDataResponseCall(RestUtils.getContentType(), dashboardDataRequest());
        Log.w(TAG,"DashboardDataResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<DashboardDataResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<DashboardDataResponse> call, @NonNull Response<DashboardDataResponse> response) {
                //avi_indicator.smoothToHide();
                Log.w(TAG,"DashboardDataResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getUser_count() != null){
                           int total_count = response.body().getUser_count().getTotal_count();
                            nav_Menu.findItem(R.id.nav_item_nine).setVisible(total_count > 1);
                        }



                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<DashboardDataResponse> call,@NonNull Throwable t) {
              //  avi_indicator.smoothToHide();
                Log.e("DashboardDataRe flr", "--->" + t.getMessage());
            }
        });

    }
    private DashboardDataRequest dashboardDataRequest() {
        /*
         * transaction_type : Cash
         * transaction_way : Debit
         * user_id : 617a7c37eeb3a520395e2f15
         * start_date : 23-10-2021
         * end_date : 23-10-2021
         */


        DashboardDataRequest dashboardDataRequest = new DashboardDataRequest();
        dashboardDataRequest.setTransaction_type("");
        dashboardDataRequest.setTransaction_way("");
        dashboardDataRequest.setUser_id(user_id);
        dashboardDataRequest.setStart_date("");
        dashboardDataRequest.setEnd_date("");

        Log.w(TAG,"dashboardDataRequest "+ new Gson().toJson(dashboardDataRequest));
        return dashboardDataRequest;
    }


}
