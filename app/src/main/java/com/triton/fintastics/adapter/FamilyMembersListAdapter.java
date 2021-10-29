package com.triton.fintastics.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.fintastics.R;
import com.triton.fintastics.interfaces.SelectFamilyMemberListener;
import com.triton.fintastics.responsepojo.FamilyListModel;

import java.util.List;


public class FamilyMembersListAdapter extends RecyclerView.Adapter<FamilyMembersListAdapter.ViewHolder>{

    private String TAG = "FamilyMembersListAdapter";

    private final List<FamilyListModel> familyListModelList;
    private SelectFamilyMemberListener selectFamilyMemberListener;
    private Context context;

    // RecyclerView recyclerView;
    public FamilyMembersListAdapter(List<FamilyListModel> familyListModelList,SelectFamilyMemberListener selectFamilyMemberListener,Context context){
        this.familyListModelList = familyListModelList;
        this.selectFamilyMemberListener = selectFamilyMemberListener;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.adapter_familymemberslist, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final FamilyListModel familyListModel = familyListModelList.get(position);

        if(familyListModel.getName()!=null&&!familyListModel.getName().isEmpty()){

            holder.txt_memberlist.setText(familyListModel.getName());
        }
        else {

            holder.txt_memberlist.setText("");
        }

        if(familyListModel.isSelect()){

            holder.txt_memberlist.setTextColor(context.getResources().getColor(R.color.white));

            holder.ll_personal.setBackgroundResource(R.drawable.rectangular_corner_bg_darkblue);
        }
        else {

            holder.txt_memberlist.setTextColor(context.getResources().getColor(R.color.blue));

            holder.ll_personal.setBackgroundResource(R.drawable.rectangle_corner_bg_white);
        }

        holder.ll_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<familyListModelList.size();i++){

                    if(familyListModelList.get(i).isSelect()){

                        familyListModelList.get(i).setSelect(false);
                    }
                }

                familyListModel.setSelect(true);

                notifyDataSetChanged();

                selectFamilyMemberListener.selectfamilymemberListener(familyListModel.getId(),familyListModel.getName());
            }
        });
    }


    @Override
    public int getItemCount() {
        return familyListModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_personal;
        TextView txt_memberlist;// init the item view's
        public ViewHolder(View itemView) {
            super(itemView);
            txt_memberlist = itemView.findViewById(R.id.txt_memberlist);

            ll_personal = itemView.findViewById(R.id.ll_personal);
        }
    }
}