package com.triton.fintastics.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.triton.fintastics.R;
import com.triton.fintastics.accountsummary.AccountSummaryActivity;
import com.triton.fintastics.budgetary.BudgetaryActivity;
import com.triton.fintastics.chat.ChatActivity;
import com.triton.fintastics.expenditurereport.ExpenditureReportActivity;
import com.triton.fintastics.incomereport.IncomeReportActivity;
import com.triton.fintastics.movementlist.MovementListActivity;
import com.triton.fintastics.transactionreport.TranscationReportActivity;

import java.util.HashMap;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class DashboardNavigationDrawer extends AppCompatActivity implements View.OnClickListener{



    private String TAG ="DashboardNavigationDrawer";

    private DrawerLayout drawerLayout;
    LayoutInflater inflater;
    View view, header;
    Toolbar toolbar;

    private View toolbar_layout;
    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;
    ImageView drawerImg;
    CircleImageView nav_header_imageView;
    FrameLayout frameLayout;
    TextView nav_header_profilename, nav_header_emailid;
    //SessionManager session;
    String name, image_url, phoneNo;

    public TextView toolbar_title;
    Button btnNotificationPatient;

    public Menu menu;




    ProgressDialog progressDialog;
    private Dialog dialog;


//    SessionManager session;
//
//    private double latitude, longitude;
//    private String addressLine = "";
//
//    String emailid = "",patientid = "";
//    private Dialog dialog;
//
//    private static final int REQUEST_PHONE_CALL =1 ;
//    private String sosPhonenumber;




    @SuppressLint({"InflateParams", "LogNotTimber"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        Log.w(TAG, "onCreate---->");

        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.navigation_drawer_layout_new, null);
       /* session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        name = user.get(SessionManager.KEY_FIRST_NAME);
        emailid = user.get(SessionManager.KEY_EMAIL_ID);
        phoneNo = user.get(SessionManager.KEY_MOBILE);
        String userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG, "userid : " + userid);


        Log.w(TAG, "user details--->" + "name :" + " " + name + " " + "image_url :" + image_url);
*/
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
       /* nav_header_imageView = header.findViewById(R.id.nav_header_imageView);
        nav_header_emailid = header.findViewById(R.id.nav_header_emailid);
        nav_header_profilename = header.findViewById(R.id.nav_header_profilename);

        Glide.with(this).load(R.drawable.profile).circleCrop().into(nav_header_imageView);

        nav_header_emailid.setText(emailid);
        nav_header_profilename.setText(name);

        RelativeLayout llheader = header.findViewById(R.id.llheader);
        llheader.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),PetLoverProfileScreenActivity.class)));

        TextView nav_header_edit = header.findViewById(R.id.nav_header_edit);
        nav_header_edit.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),PetLoverEditProfileActivity.class)));
*/

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            //Closing drawer on item click
            drawerLayout.closeDrawers();
            //Check to see which item was being clicked and perform appropriate action
            switch (menuItem.getItemId()) {
                //Replacing the main content with ContentFragment Which is our Inbox View;
                case R.id.nav_item_one:
                    gotoIncomeReport();
                    return true;

                // For rest of the options we just show a toast on click
                case R.id.nav_item_two:
                  gotoExpenditureReport();
                    return true;

                case R.id.nav_item_three:
                    gotoTranscationReport();
                    return true;

                case R.id.nav_item_four:
                    gotoAccountSummary();
                    return true;

                case R.id.nav_item_five:
                    gotoChangePassword();
                    return true;

                case R.id.nav_item_six:
                    gotoBudgetary();
                    return true;
                case R.id.nav_item_seven:
                    gotoChat();
                    return true;

                case R.id.nav_item_ten:
                    gotoMovementList();
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
        //drawerMethod();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.img_menu:
                drawerMethod();
                break;*/
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

            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    gotoLogout();

                }
            });
            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void gotoLogout() {
       // session.logoutUser();
        //session.setIsLogin(false);
        startActivity(new Intent(getApplicationContext(), ChooseAccountTypeActivity.class));
        finish();




    }



}
