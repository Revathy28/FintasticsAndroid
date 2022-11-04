package com.triton.fintastics.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.fintastics.R;
import com.triton.fintastics.responsepojo.Dash_trans_DetailsResponse;

import java.util.List;


public class Dash_trans_DetailsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetBreedTypesListAdapter";
    private Context context;
    Dash_trans_DetailsResponse.DataBean currentItem;
    private List<Dash_trans_DetailsResponse.DataBean> breedTypedataBeanList;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    String data = "";

    public Dash_trans_DetailsAdapter(Context context, List<Dash_trans_DetailsResponse.DataBean> breedTypedataBeanList) {
        this.context = context;
        this.breedTypedataBeanList = breedTypedataBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashbord_card, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("LogNotTimber")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = breedTypedataBeanList.get(position);

//        Intent intent = new Intent("message_subject_intent");
//        intent.putExtra("cust_name" , currentItem.getCUSNAME());
//        intent.putExtra("cont_no" , currentItem.getCONTNO());
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        if(currentItem.getTransaction_way() != null){
            holder.tv.setText(currentItem.getTransaction_date());
            holder.tv1.setText(currentItem.getTransaction_way());
            holder.tv2.setText(currentItem.getTransaction_amount());
            holder.tv3.setText(currentItem.getTransaction_balance());
            holder.tv4.setText(currentItem.getCreatedAt());
            holder.tv5.setText(currentItem.getTransaction_description());
        }

     //  holder.chkSelected.setChecked(currentItem.isSelected());

    }
    @Override
    public int getItemCount() {
        return breedTypedataBeanList.size();
    }

    public void filterList(List<Dash_trans_DetailsResponse.DataBean> breedTypedataBeanListFiltered) {
        breedTypedataBeanList = breedTypedataBeanListFiltered;
        Log.w(TAG,"breedTypedataBeanList : "+new Gson().toJson(breedTypedataBeanList));

        notifyDataSetChanged();
    }




    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView tv,tv1,tv2,tv3,tv4,tv5;
        public LinearLayout ll_root;
        public CheckBox chkSelected;

        public ViewHolderOne(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv5 = itemView.findViewById(R.id.tv5);

        }

    }
}
