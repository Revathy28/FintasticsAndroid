package com.triton.fintastics.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.fintastics.R;
import com.triton.fintastics.interfaces.SelectChildMemberListner;
import com.triton.fintastics.responsepojo.FetchChildDetailsResponse;
import com.triton.fintastics.responsepojo.IncomeReportResponse;

import java.util.List;


public class ChildDetailsListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ChildDetailsListAdapter";
    private List<FetchChildDetailsResponse.DataBean> dataBeanList;
    FetchChildDetailsResponse.DataBean currentItem;
    private Context context;
    private SelectChildMemberListner selectChildMemberListner;



    public ChildDetailsListAdapter(Context context, List<FetchChildDetailsResponse.DataBean> dataBeanList,SelectChildMemberListner selectChildMemberListner) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.selectChildMemberListner = selectChildMemberListner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_childdetails, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = dataBeanList.get(position);


        if(currentItem.getUsername() != null && !currentItem.getUsername().isEmpty()) {
            holder.txt_name.setVisibility(View.VISIBLE);
           holder.txt_name.setText(currentItem.getUsername());
       }else{
            holder.txt_name.setVisibility(View.GONE);
        }
       if(currentItem.getUser_email() != null && !currentItem.getUser_email().isEmpty()) {
           holder.txt_emailid.setVisibility(View.VISIBLE);
           holder.txt_emailid.setText(currentItem.getUser_email()+"");
       }else{
           holder.txt_emailid.setVisibility(View.GONE);
       }

        if(currentItem.getContact_number() != null && !currentItem.getContact_number().isEmpty()) {
            holder.txt_contactno.setVisibility(View.VISIBLE);
            holder.txt_contactno.setText(currentItem.getContact_number()+"");
        }else{
            holder.txt_contactno.setVisibility(View.GONE);
        }

        if(currentItem.isDelete_status()){
            holder.btn_status.setText("Block");
        }else{
            holder.btn_status.setText("Unblock");
        }

        holder.btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectChildMemberListner.selectChildMemberListner(dataBeanList.get(position).get_id(),dataBeanList.get(position).isDelete_status());
            }
        });








    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_name,txt_emailid,txt_contactno;
        public Button btn_status;

        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_emailid = itemView.findViewById(R.id.txt_emailid);
            txt_contactno = itemView.findViewById(R.id.txt_contactno);
            btn_status = itemView.findViewById(R.id.btn_status);



        }




    }








}
