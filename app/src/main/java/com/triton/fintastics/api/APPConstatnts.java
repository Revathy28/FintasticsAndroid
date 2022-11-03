package com.triton.fintastics.api;

import android.Manifest;
import android.content.Intent;

public interface APPConstatnts {

    int clearActivities = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK;
    String[] filePermissions = new String[]{/*Manifest.permission.CAMERA,*/Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] locationPermissions = new String[]{/*Manifest.permission.CAMERA,*/Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

}
