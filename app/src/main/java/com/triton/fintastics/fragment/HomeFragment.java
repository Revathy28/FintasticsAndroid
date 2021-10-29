package com.triton.fintastics.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triton.fintastics.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;

    private static final String TAG = "LoginActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_expense)
    LinearLayout ll_expense;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_income)
    LinearLayout ll_income;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_expensetab)
    TextView txt_lbl_expensetab;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_incometab)
    TextView txt_lbl_incometab;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fl_root)
    FrameLayout fl_root;

    boolean showincome = true;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.w(TAG,"onCreateView-->");
        ButterKnife.bind(this,view);

        ll_expense.setOnClickListener(this);

        ll_income.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_expense:
                showExpenseTab();
                break;

            case R.id.ll_income:
                showincomeTab();
                break;
        }
    }

    private void showincomeTab() {

        showincome = true;

        fl_root.setBackgroundResource(R.drawable.rectangle_corner_primary_blue);

        ll_income.setBackgroundResource(R.drawable.tabincome_active);

        ll_expense.setBackgroundResource(R.color.white);

        txt_lbl_incometab.setTextColor(getResources().getColor(R.color.white));

        txt_lbl_expensetab.setTextColor(getResources().getColor(R.color.black));
    }

    private void showExpenseTab() {

        showincome = false;

        fl_root.setBackgroundResource(R.drawable.rectangle_corner_bgnd_white);

        ll_income.setBackgroundResource(R.drawable.tabncone_inactive);

        ll_expense.setBackgroundResource(R.color.primary);

        txt_lbl_incometab.setTextColor(getResources().getColor(R.color.black));

        txt_lbl_expensetab.setTextColor(getResources().getColor(R.color.white));
    }
}