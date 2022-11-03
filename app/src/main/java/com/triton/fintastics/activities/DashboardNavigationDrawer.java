package com.triton.fintastics.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.accountsummary.AccountSummaryActivity;
import com.triton.fintastics.api.APIClient;
import com.triton.fintastics.api.APPConstatnts;
import com.triton.fintastics.api.AlertYesNoListener;
import com.triton.fintastics.api.RestApiInterface;
import com.triton.fintastics.budgetary.BudgetaryActivity;
import com.triton.fintastics.chat.ChatActivity;
import com.triton.fintastics.expenditurereport.ExpenditureReportActivity;
import com.triton.fintastics.familyaccount.EditProfileActivity;
import com.triton.fintastics.incomereport.IncomeReportActivity;
import com.triton.fintastics.movementlist.MovementListActivity;
import com.triton.fintastics.requestpojo.DashboardDataRequest;
import com.triton.fintastics.requestpojo.LoginRequest;
import com.triton.fintastics.requestpojo.ProfileUpdateRequest;
import com.triton.fintastics.responsepojo.DashboardDataResponse;
import com.triton.fintastics.responsepojo.LoginResponse;
import com.triton.fintastics.responsepojo.ProfileUpdateResponse;
import com.triton.fintastics.sessionmanager.SessionManager;
import com.triton.fintastics.transaction.AddTransactionActivity;
import com.triton.fintastics.transaction.VoiceAddTransactionActivity;
import com.triton.fintastics.transactionreport.TranscationReportActivity;
import com.triton.fintastics.utils.GetPath;
import com.triton.fintastics.utils.ResizeImage;
import com.triton.fintastics.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;


import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardNavigationDrawer extends AppCompatActivity implements View.OnClickListener {
    private Dialog alertdialog;
    String TAG = "DashboardNavigationDrawer";
    private DrawerLayout drawerLayout;
    LayoutInflater inflater;
    View view, header;
    Toolbar toolbar;
    File file;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;
    private View toolbar_layout;

    private ActionBarDrawerToggle drawerToggle;
    ImageView drawerImg, EditProfileImage, imageView;
    CircleImageView nav_header_imageView;
    FrameLayout frameLayout;
    TextView nav_header_profilename, nav_header_accountype;
    SessionManager session;
    String name, image_url, phoneNo, imageFilePath = "";
    public Menu menu;
    ProgressDialog progressDialog;
    private Dialog dialog;
    private String user_id;

    Menu nav_Menu;
    private String account_type;
    private String roll_type;
    private String profileimg;

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
        profileimg = user.get(SessionManager.KEY_PROFILEIMAGE);
        EditProfileImage = findViewById(R.id.img_profile);
        imageView = findViewById(R.id.img_circular);
        account_type = user.get(SessionManager.KEY_ACCOUNT_TYPE);
        roll_type = user.get(SessionManager.KEY_ROLL_TYPE);
        Log.w(TAG, "userid-->" + user_id + " account_type : " + account_type + " roll_type : " + roll_type + " profileimg : " + profileimg);


        if (user_id != null) {
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

        if (account_type != null && account_type.equalsIgnoreCase("Family")) {
            if (roll_type != null && roll_type.equalsIgnoreCase("Admin")) {
                nav_header_accountype.setText(roll_type);
            } else {
                nav_header_accountype.setText("");
            }
        }
        if (account_type != null && account_type.equalsIgnoreCase("Personal")) {
            nav_header_accountype.setText(account_type);
        }

        if (profileimg != null && !profileimg.isEmpty()) {
            Glide.with(this).load(profileimg).circleCrop().into(nav_header_imageView);

        } else {
            Glide.with(this).load(R.drawable.nav_headerprofile).circleCrop().into(nav_header_imageView);

        }


        nav_header_profilename.setText(name);

        RelativeLayout rl_editprofile = header.findViewById(R.id.rl_editprofile);
        ImageView Editprofile = header.findViewById(R.id.img_profile);

        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPerm(99, 99, 98);
            }
        });
        //rl_editprofile.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EditProfileActivity.class)));

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

                case R.id.nav_item_ten:
                    gotoEditprofile();
                    return true;

                case R.id.nav_item_eleventh:
                    showLogOutAppAlert();
                    return true;


                default:
                    return true;

            }
        });

    }

    private void gotoEditprofile() {
        startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
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


    void checkPerm(int code, int code1, int code2) {
        if (checkPermissionsGranted(getApplicationContext(), APPConstatnts.filePermissions)) {

            getCameraPic(code1, code2);
        } else {
            ActivityCompat.requestPermissions(this, APPConstatnts.filePermissions, code);

        }
    }

    public static void showYesNoAlert(Activity activity, final String requestCode, String message, boolean cancelable, Bundle bundle,
                                      final AlertYesNoListener yesNoListener) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yesNoListener.onYesClick(which, requestCode, bundle);

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyAlertDialogStyle);
        builder.setPositiveButton("Yes", dialogClickListener);
        builder.setNegativeButton("No", dialogClickListener);

        //DialogYesNoBinding yesNoBinding = DialogYesNoBinding.inflate(activity.getLayoutInflater(), null, false);
        //builder.setView(yesNoBinding.getRoot());
        final AlertDialog alertDialog = builder.create();
        alertDialog.setMessage(message);
        //yesNoBinding.textViewMessage.setText(message);

        /*yesNoBinding.buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                yesNoListener.onNoClick(requestCode);
            }
        });*/
       /* yesNoBinding.buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                yesNoListener.onYesClick(requestCode);

            }
        });*/

        // alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // alertDialog.setMessage(message);
        if (cancelable) {
            alertDialog.setCanceledOnTouchOutside(false);
        } else {
            alertDialog.setCancelable(false);
        }
        //setButtonTextColor(activity, alertDialog);
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 99) {
            if (checkPermissionsGranted(getApplicationContext(), permissions)) {
                getCameraPic(99, 98);
            } else {

                String message = getString(R.string.cam_storage_permissions);
                if (checkPermissionsRationale(this, null, permissions)) {
                    message = getString(R.string.camera_storage_permissions_app_data);
                }
                showYesNoAlert(this, "permission", message, false,
                        getBundle(this), alertYesNoListener);


            }
        }
    }

    public static Bundle getBundle(Activity activity) {
        return activity.getIntent().getExtras() != null ? activity.getIntent().getExtras() : new Bundle();
    }

    public static void gotoPermission(Activity activity, Fragment fragment, int code) {

        Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + activity.getPackageName()));
        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        //i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (fragment != null) {
            fragment.startActivityForResult(i, code);
        } else {
            activity.startActivityForResult(i, code);
        }
    }

    AlertYesNoListener alertYesNoListener = new AlertYesNoListener() {
        @Override
        public void onYesClick(int button, String requestCode, Bundle bundle) {
            if (requestCode.equalsIgnoreCase("permission")) {
                if (button == DialogInterface.BUTTON_POSITIVE) {
                    gotoPermission(
                            DashboardNavigationDrawer.this, null, 1000
                    );
                } else {

                }
            }
        }
    };
    private void ProfileUpdateRequestCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ProfileUpdateResponse> call = apiInterface.profileupdateResponseCall(RestUtils.getContentType(), new ProfileUpdateRequest());
        Log.w(TAG, "ProfileUpdateResponse url  :%s" + " " + call.request().url());

        call.enqueue(new Callback<ProfileUpdateResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<ProfileUpdateResponse> call, @NonNull Response<ProfileUpdateResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ProfileUpdateResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    }
                }

            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("LoginResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    @SuppressLint("LogNotTimber")
    private ProfileUpdateRequest profileUpdateRequest() {

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();

        return profileUpdateRequest;
    }
    public static boolean checkPermissionsRationale(Activity activity, Fragment fragment, String[] permissions) {
        boolean granted = true;
        for (String permission : permissions) {
            if (isPermissionDenied(activity, permission)) {
                if (fragment != null) {
                    Log.i("PermissionRationale", String.valueOf(fragment.shouldShowRequestPermissionRationale(permission)));
                    if (!fragment.shouldShowRequestPermissionRationale(permission)) {
                        granted = false;
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!activity.shouldShowRequestPermissionRationale(permission)) {
                            granted = false;
                        }
                    }
                }
            }
        }
        Log.i("PermissionRationale", String.valueOf(granted));
        return granted;
    }

    void getCameraPic(int code1, int code2) {
        try {
            file = createImageFile(this);
            launchPictureIntent(this, null, view, file, code1, code2);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void launchPictureIntent(final Activity activity, final Fragment fragment, View view, final File file, final int code1, final int code2) {
        alertdialog = new Dialog(activity);
        alertdialog.setContentView(R.layout.alert_camera);
        alertdialog.setCancelable(false);

        TextView txtcancel = alertdialog.findViewById(R.id.txtcancel);
        txtcancel.setOnClickListener(v -> alertdialog.dismiss());

        TextView txttakepic = alertdialog.findViewById(R.id.txttakepic);
        txttakepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog.dismiss();
                dispatchTakePictureIntentFragment(activity, fragment, file, code1);
            }
        });
        TextView txttakeGalary = alertdialog.findViewById(R.id.txtpickgalary);
        txttakeGalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog.dismiss();
                Intent takePictureIntent;
                takePictureIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                takePictureIntent.setType("image/*");

                if (fragment != null)

                    fragment.startActivityForResult(takePictureIntent, code2);
                else activity.startActivityForResult(takePictureIntent, code2);
            }
        });
        alertdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertdialog.show();


      /*  PopupMenu popup = new PopupMenu(activity, view);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_camera, popup.getMenu());
        //inflater.inflate(R.menu.menu_pdf, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_gallery) {


                }
               *//* if (id == R.id.menu_pdf) {
                    Intent takePictureIntent = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        takePictureIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    } else {
                        takePictureIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    }
                    takePictureIntent.setType("application/pdf");

                    fragment.startActivityForResult(takePictureIntent, code3);
                }*//*
                if (id == R.id.menu_camera) {

                }
               *//* bundle.putString("currentTitle",((HomeActivity)getActivity()).getSupportActionBar().getTitle().toString());
                ChooseImageFragment fragment = new ChooseImageFragment();
                fragment.setArguments(bundle);
                fragment.setFragmentResultListener(MessagesFragment.this);
                FragmentHelper.addFragment(getActivity(), FRAME_HOME, fragment);*//*
                return false;
            }
        });*/
        // popup.show();

    }

    public static void dispatchTakePictureIntentFragment(Activity activity, Fragment fragment, File file, int code) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go

            // Continue only if the File was successfully created
            if (file != null) {
                Log.i("Image", activity.getPackageName());
                Uri photoURI = FileProvider.getUriForFile(activity,
                        activity.getPackageName() + ".provider",
                        file);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (fragment != null)
                    fragment.startActivityForResult(takePictureIntent, code);
                else
                    activity.startActivityForResult(takePictureIntent, code);
            } else {
                Toast.makeText(activity, "File is empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Intent is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public static File createImageFile(Context context) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                timeStamp,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        return image;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 98:
                if (resultCode == RESULT_OK) {
                    /*Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);*/
                    if (data != null) {
                        imageFilePath = ResizeImage.getResizedImage(user_id+"_", GetPath.getPath(this, data.getData()), 720);
                        Log.i("ImageFileGal", imageFilePath);
                        Glide.with(this).load(imageFilePath).into(nav_header_imageView);
                    }

                }

                break;
            case 99:
                if (resultCode == RESULT_OK) {
                   /* Uri selectedImage = data.getData();
                      imageView=findViewById(R.id.img_circular);

                    imageView.setImageURI(selectedImage);*/
                    imageFilePath = ResizeImage.getResizedImage(user_id+"_", file.getPath(), 720);
                    Log.i("ImageFileCam", imageFilePath);
                    Glide.with(this).load(imageFilePath).into(nav_header_imageView);

                }
                break;
        }
    }


    public static boolean isPermissionDenied(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

    void getPro() {

    }


    public static boolean checkPermissionsGranted(Context context, String[] permissions) {
        boolean granted = true;
        for (String permission : permissions) {
            if (isPermissionDenied(context, permission)) {
                granted = false;
            }
        }
        return granted;
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
        startActivity(new Intent(getApplicationContext(), VoiceAddTransactionActivity.class));
        //startActivity(new Intent(getApplicationContext(), AddTransactionActivity.class));

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
        Log.w(TAG, "DashboardDataResponse url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<DashboardDataResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<DashboardDataResponse> call, @NonNull Response<DashboardDataResponse> response) {
                //avi_indicator.smoothToHide();
                Log.w(TAG, "DashboardDataResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getUser_count() != null) {
                            int total_count = response.body().getUser_count().getTotal_count();
                            nav_Menu.findItem(R.id.nav_item_nine).setVisible(total_count > 1);
                        }


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<DashboardDataResponse> call, @NonNull Throwable t) {
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

        Log.w(TAG, "dashboardDataRequest " + new Gson().toJson(dashboardDataRequest));
        return dashboardDataRequest;
    }


}
