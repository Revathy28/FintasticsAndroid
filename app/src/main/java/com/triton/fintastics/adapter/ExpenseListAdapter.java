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

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.triton.fintastics.R;
import com.triton.fintastics.responsepojo.AccountSummaryResponse;
import com.triton.fintastics.responsepojo.BudgetGetlistResponse;

import java.util.List;


public class ExpenseListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BudgetGetlistResponse.DataBean.ExpensiveDataBean> expensive_databeanList;
    BudgetGetlistResponse.DataBean.ExpensiveDataBean currentItem;
    private final Context context;



    public ExpenseListAdapter(Context context, List<BudgetGetlistResponse.DataBean.ExpensiveDataBean> expensive_databeanList) {
        this.context = context;
        this.expensive_databeanList = expensive_databeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_expenselist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);



    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = expensive_databeanList.get(position);

        if(currentItem.get_id() != null) {
           holder.txt_expense_desc.setText(currentItem.get_id());
       }else{
            holder.txt_expense_desc.setText("");
       }


        if(currentItem.getPercentage() != 0){
            int roundVal= (int) Math.round(currentItem.getPercentage());
            holder.progress_circular.setProgress(roundVal);
            holder.txt_income_per.setText(roundVal+"%");
        }

        if(currentItem.getPrice() != 0){
            holder.txt_expense_value.setText("\u20B9 " +currentItem.getPrice());
        }












    }

    @Override
    public int getItemCount() {
        return expensive_databeanList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_income_per,txt_expense_value,txt_expense_desc,txt_expense_type;
        public CircularProgressIndicator progress_circular;


        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_income_per = itemView.findViewById(R.id.txt_income_per);
            txt_expense_value = itemView.findViewById(R.id.txt_expense_value);
            txt_expense_desc = itemView.findViewById(R.id.txt_expense_desc);
            txt_expense_type = itemView.findViewById(R.id.txt_expense_type);
            progress_circular = itemView.findViewById(R.id.progress_circular);

            txt_expense_type.setVisibility(View.GONE);



        }




    }








}
