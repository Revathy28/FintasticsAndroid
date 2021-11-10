package com.triton.fintastics.sessionmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {



    String TAG = "SessionManager";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Session Manager";
    public static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL_ID = "user_email";
    public static final String KEY_REF_CODE= "parent_code";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_DOB = "dob";
    public static final String KEY_CONTACTNUMBER = "contactnumber";
    public static final String KEY_ACCOUNT_TYPE = "account_type";
    public static final String KEY_ROLL_TYPE = "roll_type";




    public static final String KEY_PROFILE_STATUS= "profilestatus";
    public static final String KEY_VERIFY_EMAIL_STATUS= "verifyemailstatus";







    public static final String KEY_PROFILE_IMAGE = "profileimage" ;


    public static final String KEEPLOGIN = "keeplogin";
    public static final String KEEPPROFILEUPDATE = "keepprofileupdate";

    public static final String KEY_RZP_KEY = "rzpkey";
    public static final String KEY_RZP_PRODUCTION = "production";


    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String id, String username,String useremail, String refcode,String password,String firstname, String lastname,String dob, String contactnumber,String account_type,String roll_type) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL_ID, useremail);
        editor.putString(KEY_REF_CODE,refcode);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_FIRSTNAME,firstname);
        editor.putString(KEY_LASTNAME,lastname);
        editor.putString(KEY_DOB,dob);
        editor.putString(KEY_CONTACTNUMBER,contactnumber);
        editor.putString(KEY_ACCOUNT_TYPE,account_type);
        editor.putString(KEY_ROLL_TYPE,roll_type);
        Log.e(TAG, "................................>> session Login Details " + "KEY_ID" + id);

        editor.commit();

    }



    public HashMap<String, String> getProfileDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_ID, pref.getString(KEY_ID, ""));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, ""));
        user.put(KEY_EMAIL_ID, pref.getString(KEY_EMAIL_ID, ""));
        user.put(KEY_REF_CODE, pref.getString(KEY_REF_CODE, ""));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, ""));
        user.put(KEY_FIRSTNAME, pref.getString(KEY_FIRSTNAME, ""));
        user.put(KEY_LASTNAME, pref.getString(KEY_LASTNAME, ""));
        user.put(KEY_DOB, pref.getString(KEY_DOB, ""));
        user.put(KEY_CONTACTNUMBER, pref.getString(KEY_CONTACTNUMBER, ""));
        user.put(KEY_ACCOUNT_TYPE, pref.getString(KEY_ACCOUNT_TYPE, ""));
        user.put(KEY_ROLL_TYPE, pref.getString(KEY_ROLL_TYPE, ""));
        return user;
    }


    public void logoutUser() {
        editor.clear();
        editor.commit();


    }




    public void setIsLogin(boolean isLoogedIn){
        editor.putBoolean(KEEPLOGIN,isLoogedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(KEEPLOGIN, false);
    }

    public boolean isProfileUpdate() {
        return pref.getBoolean(KEEPPROFILEUPDATE, false);
    }

    public void setIsProfileUpdate(boolean isProfileUpdate){
        editor.putBoolean(KEEPPROFILEUPDATE,isProfileUpdate);
        editor.apply();
    }


    public void createRazorpayDetails(String rzpkey,String production) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_RZP_KEY, rzpkey);
        editor.putString(KEY_RZP_PRODUCTION, production);

        Log.e(TAG, "................................>> session createRazorpayDetails " + "rzpkey : " + rzpkey+" production : " + production);

        editor.commit();

    }
    public HashMap<String, String> getRazorpayDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_RZP_KEY, pref.getString(KEY_RZP_KEY, ""));
        user.put(KEY_RZP_PRODUCTION, pref.getString(KEY_RZP_PRODUCTION, ""));
        return user;
    }





}
