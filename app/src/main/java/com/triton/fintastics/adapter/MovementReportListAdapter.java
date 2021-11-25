package com.triton.fintastics.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.fintastics.R;
import com.triton.fintastics.responsepojo.IncomeReportResponse;

import java.util.List;


public class MovementReportListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "MovementReportListAdapter";
    private List<IncomeReportResponse.DataBean> dataBeanList;
    IncomeReportResponse.DataBean currentItem;
    private Context context;



    public MovementReportListAdapter(Context context, List<IncomeReportResponse.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_table_row_item, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = dataBeanList.get(position);

        holder.txt_sno.setText(holder.getAbsoluteAdapterPosition()+1+"");

        if(currentItem.getDate() != null) {
           holder.txt_date.setText(currentItem.getDate());
       }
       if(currentItem.getCredeit_amount() != 0) {
           holder.txt_added.setText("\u20B9 " +currentItem.getCredeit_amount());
       }else{
           holder.txt_added.setText("\u20B9 " +"0");
       }


        if(currentItem.getDebit_amount() != 0) {
            holder.txt_deducted.setText("\u20B9 " +currentItem.getDebit_amount());
        }else{
            holder.txt_deducted.setText("\u20B9 " +"0");
        }








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
        public TextView txt_sno,txt_date,txt_added,txt_deducted;

        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_sno = itemView.findViewById(R.id.txt_sno);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_added = itemView.findViewById(R.id.txt_added);
            txt_deducted = itemView.findViewById(R.id.txt_deducted);



        }




    }








}
