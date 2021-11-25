package com.triton.fintastics.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.fintastics.R;
import com.triton.fintastics.responsepojo.AccountSummaryResponse;

import java.util.List;


public class AccountSummaryListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<AccountSummaryResponse.DataBean> dataBeanList;
    AccountSummaryResponse.DataBean currentItem;
    private final Context context;



    public AccountSummaryListAdapter(Context context, List<AccountSummaryResponse.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_accountsummarry, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);



    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = dataBeanList.get(position);

        if(currentItem.getTransaction_balance() != 0) {
           holder.txt_balance.setText("Balance : "+"\u20B9 " +currentItem.getTransaction_balance());
       }else{
           holder.txt_balance.setText("\u20B9 " +"0");
       }

        if(currentItem.getTransaction_date() != null && !currentItem.getTransaction_date().isEmpty()){
            holder.txt_transaction_date.setText(currentItem.getTransaction_date());
        }else{
            holder.txt_transaction_date.setText("");
        }

        if(currentItem.getTransaction_way() != null && currentItem.getTransaction_way().equalsIgnoreCase("Credit")){
            if(currentItem.getTransaction_amount() != 0) {
                holder.txt_credit.setText("\u20B9 " +" + "+currentItem.getTransaction_amount());
                holder.txt_debit.setText("\u20B9 " +"- 0");
            }else{
                holder.txt_credit.setText("\u20B9 " +"+ 0");
                holder.txt_debit.setText("\u20B9 " +"- 0");
            }


        }

        if(currentItem.getTransaction_way() != null && currentItem.getTransaction_way().equalsIgnoreCase("Debit")){
            if(currentItem.getTransaction_amount() != 0) {
                 holder.txt_debit.setText("\u20B9 "+" - " +currentItem.getTransaction_amount());
                 holder.txt_credit.setText("\u20B9 " +"+ 0");
             }else{
                holder.txt_debit.setText("\u20B9 " +"- 0");
                holder.txt_credit.setText("\u20B9 " +"+ 0");
            }
         }

        if(currentItem.getTransaction_type() != null){
            holder.txt_transctype.setText(currentItem.getTransaction_type());
        }


        if(position % 2 == 0){
            holder.ll_root.setBackgroundResource(R.drawable.rectangle_corner_bg_solidthicblue);
        }else{
            holder.ll_root.setBackgroundResource(R.drawable.rectangle_corner_bg_thicblue);
            holder.txt_balance.setTextColor(context.getResources().getColor(R.color.white));
            holder.txt_credit.setTextColor(context.getResources().getColor(R.color.white));
            holder.txt_debit.setTextColor(context.getResources().getColor(R.color.white));
            holder.txt_transctype.setTextColor(context.getResources().getColor(R.color.white));
            holder.txt_transaction_date.setTextColor(context.getResources().getColor(R.color.white));

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
        public TextView txt_balance,txt_debit,txt_credit,txt_transctype,txt_transaction_date;
        public LinearLayout ll_root;

        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_balance = itemView.findViewById(R.id.txt_balance);
            txt_debit = itemView.findViewById(R.id.txt_debit);
            txt_credit = itemView.findViewById(R.id.txt_credit);
            txt_transctype = itemView.findViewById(R.id.txt_transctype);
            txt_transaction_date = itemView.findViewById(R.id.txt_transaction_date);
            ll_root = itemView.findViewById(R.id.ll_root);



        }




    }








}
