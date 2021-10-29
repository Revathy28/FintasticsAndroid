package com.triton.fintastics.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.triton.fintastics.R;
import com.triton.fintastics.fragment.HomeFragment;

public class DashoardActivity extends DashboardNavigationDrawer {

    final Fragment HomeFragment = new HomeFragment();

    Fragment active = HomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashoard);

        loadFragment(HomeFragment);
    }


    @SuppressLint("LogNotTimber")
    private void loadFragment(Fragment fragment) {

            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_schedule, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

    }

}