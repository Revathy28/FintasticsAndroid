package com.triton.fintastics.budgetary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import com.triton.fintastics.R;
import com.wang.avi.AVLoadingIndicatorView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBudgetaryActivity extends AppCompatActivity {


    private static final String TAG = "PeriodicFragment";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_header)
    View include_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_name)
    EditText edt_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_period_type)
    Spinner spr_period_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_dates)
    LinearLayout ll_dates;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_startdate)
    Spinner spr_startdate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_enddate)
    Spinner spr_enddate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_amount)
    EditText edt_amount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_currency)
    Spinner spr_currency;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_categories)
    TextView txt_categories;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_account)
    Spinner spr_account;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.switchButton)
    SwitchCompat switchButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_save)
    Button btn_save;

    String budgetary_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budgetary);

        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            budgetary_mode = extras.getString("budgetary_mode");
            Log.w(TAG,"budgetary_mode "+budgetary_mode);

        }

        if(budgetary_mode!=null&&budgetary_mode.equals("Periodic")){

            ll_dates.setVisibility(View.GONE);
        }

    }
}