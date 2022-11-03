package com.triton.fintastics.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.fintastics.R;
import com.triton.fintastics.responsepojo.AccountSummaryResponse;

import java.util.List;


public class PeriodicBudgetAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<AccountSummaryResponse.DataBean> dataBeanList;
    AccountSummaryResponse.DataBean currentItem;
    private final Context context;



    public PeriodicBudgetAdapter(Context context, List<AccountSummaryResponse.DataBean> dataBeanList) {
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

     /*   if(currentItem.getTransaction_balance() != 0) {
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

*/









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
        public TextView txt_food,txt_expense,txt_show_more;
        public SeekBar seekbar;

        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_food = itemView.findViewById(R.id.txt_food);
            txt_expense = itemView.findViewById(R.id.txt_expense);
            seekbar = itemView.findViewById(R.id.seekbar);
            txt_show_more = itemView.findViewById(R.id.txt_show_more);



        }




    }








}
