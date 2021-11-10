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
import com.triton.fintastics.responsepojo.DashboardDataResponse;

import java.util.List;


public class TransactionListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "TransactionListAdapter";
    private List<DashboardDataResponse.DataBean> dataBeanList;
    DashboardDataResponse.DataBean currentItem;
    private Context context;



    public TransactionListAdapter(Context context, List<DashboardDataResponse.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_transactionlist, parent, false);
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

        if(currentItem.getTransaction_date() != null) {
           holder.txt_date.setText(currentItem.getTransaction_date());
       }
       if(currentItem.getTransaction_type() != null) {
           holder.txt_transctype.setText(currentItem.getTransaction_type());
       }
       if(currentItem.getTransaction_desc() != null) {
           holder.txt_desc.setText(currentItem.getTransaction_desc());
       }
        if(currentItem.getTransaction_way() != null && currentItem.getTransaction_way().equalsIgnoreCase("Credit")){
            holder.ll_credit.setVisibility(View.VISIBLE);
            holder.ll_debit.setVisibility(View.VISIBLE);
            if(currentItem.getTransaction_amount() != 0) {
                holder.txt_credit.setText("\u20B9 "+currentItem.getTransaction_amount());
                holder.txt_debit.setText("\u20B9 "+0);
            }


        }

        if(currentItem.getTransaction_way() != null && currentItem.getTransaction_way().equalsIgnoreCase("Debit")){
             holder.ll_credit.setVisibility(View.VISIBLE);
             holder.ll_debit.setVisibility(View.VISIBLE);
             if(currentItem.getTransaction_amount() != 0) {
                 holder.txt_debit.setText("\u20B9 "+currentItem.getTransaction_amount());
                 holder.txt_credit.setText("\u20B9 "+0);
             }
         }

        if(currentItem.getTransaction_balance() != 0) {
            holder.txt_balance.setText("\u20B9 "+currentItem.getTransaction_balance());
        }







        holder.ll_root.setOnClickListener(v -> {

        /*    Intent i = new Intent(context, PetWalkinAppointmentDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("appointment_id",newAppointmentResponseList.get(position).get_id());
            i.putExtra("bookedat",newAppointmentResponseList.get(position).getBooked_at());
            i.putExtra("startappointmentstatus",newAppointmentResponseList.get(position).getStart_appointment_status());
            i.putExtra("appointmentfor",newAppointmentResponseList.get(position).getAppointment_for());
            i.putExtra("from",TAG);
            context.startActivity(i);*/


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
        public TextView txt_sno,txt_date,txt_transctype,txt_desc,txt_credit,txt_debit,txt_balance;
        public LinearLayout ll_root,ll_credit,ll_debit;


        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_sno = itemView.findViewById(R.id.txt_sno);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_transctype = itemView.findViewById(R.id.txt_transctype);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txt_credit = itemView.findViewById(R.id.txt_credit);
            txt_debit = itemView.findViewById(R.id.txt_debit);
            txt_balance = itemView.findViewById(R.id.txt_balance);
            ll_root = itemView.findViewById(R.id.ll_root);
            ll_credit = itemView.findViewById(R.id.ll_credit);
            ll_debit = itemView.findViewById(R.id.ll_debit);

        }




    }








}
