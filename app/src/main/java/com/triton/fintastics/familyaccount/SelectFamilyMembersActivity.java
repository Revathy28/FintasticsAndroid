package com.triton.fintastics.familyaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.triton.fintastics.R;
import com.triton.fintastics.adapter.FamilyMembersListAdapter;
import com.triton.fintastics.interfaces.SelectFamilyMemberListener;
import com.triton.fintastics.responsepojo.FamilyListModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectFamilyMembersActivity extends AppCompatActivity implements SelectFamilyMemberListener, View.OnClickListener {

    private String TAG = "SelectFamilyMembersActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_familymemberslist)
    RecyclerView rv_familymemberslist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next_step)
    Button btn_next_step;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_family_members);
        ButterKnife.bind(this);

        Log.w(TAG,"onCreate");

        avi_indicator.setVisibility(View.GONE);

        setFamilyMembersList();

    }

    private void setFamilyMembersList() {

        List<FamilyListModel> familyListModelList = new ArrayList<>();

        familyListModelList.add(new FamilyListModel("Father",1,false));
        familyListModelList.add(new FamilyListModel("Mother",2,false));
        familyListModelList.add(new FamilyListModel("Wife",3,false));
        familyListModelList.add(new FamilyListModel("Daughter/Son",4,false));
        familyListModelList.add(new FamilyListModel("Other..",5,false));


        FamilyMembersListAdapter adapter = new FamilyMembersListAdapter(familyListModelList,this,this);

        rv_familymemberslist.setHasFixedSize(true);
        rv_familymemberslist.setLayoutManager(new LinearLayoutManager(this));
        rv_familymemberslist.setAdapter(adapter);
    }


    @Override
    public void selectfamilymemberListener(int id, String name) {

        Log.w(TAG,"ID : "+id);

        Log.w(TAG, "Name : "+name);

        btn_next_step.setVisibility(View.VISIBLE);

        btn_next_step.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_next_step:
                gotoSetProfile();
                break;
        }
    }

    public void gotoSetProfile(){

        Intent i=new Intent(SelectFamilyMembersActivity.this,
                SetPrrofileActivity.class);
        //Intent is used to switch from one activity to another.

        startActivity(i);
        //invoke the SecondActivity.
    }
}