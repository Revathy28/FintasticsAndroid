package com.triton.fintastics.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.fintastics.R;
import com.triton.fintastics.interfaces.SelectFamilyMemberListener;
import com.triton.fintastics.responsepojo.FamilyListModel;

import java.util.List;


public class FamilyMembersSetProfileListAdapter extends RecyclerView.Adapter<FamilyMembersSetProfileListAdapter.ViewHolder>{

    private String TAG = "FamilyMembersSetProfileListAdapter";

    private final List<FamilyListModel> familyListModelList;
    private SelectFamilyMemberListener selectFamilyMemberListener;
    private Context context;

    // RecyclerView recyclerView;
    public FamilyMembersSetProfileListAdapter(List<FamilyListModel> familyListModelList, SelectFamilyMemberListener selectFamilyMemberListener, Context context){
        this.familyListModelList = familyListModelList;
        this.selectFamilyMemberListener = selectFamilyMemberListener;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.adapter_selectfamilymemberslist, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final FamilyListModel familyListModel = familyListModelList.get(position);

        if (familyListModel.getName() != null && !familyListModel.getName().isEmpty()) {

            holder.rb_familymembers.setText(familyListModel.getName());
        } else {

            holder.rb_familymembers.setText("");
        }

        if (familyListModel.isSelect()) {

            holder.rb_familymembers.setChecked(true);

        } else {

            holder.rb_familymembers.setChecked(false);

        }

        holder.rb_familymembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        LinearLayout ll_root;
        RadioButton rb_familymembers;// init the item view's
        public ViewHolder(View itemView) {
            super(itemView);
            rb_familymembers = itemView.findViewById(R.id.rb_familymembers);

            ll_root = itemView.findViewById(R.id.ll_root);
        }
    }
}