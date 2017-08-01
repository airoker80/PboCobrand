package com.paybyonline.KantipurPay.Adapter;

/**
 * Created by mefriend24 on 9/20/16.
 */

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.ServiceCatagoryDetails;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.ParentRecycleViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.ArrayList;
import java.util.List;


public class ParentRecycleViewAdapter extends RecyclerView.Adapter<ParentRecycleViewHolder> {

    Context context;
    List<ServiceCatagoryDetails> serviceCategoryList =
            new ArrayList<ServiceCatagoryDetails>();
    LayoutInflater inflater;
    int childLayout;
    CoordinatorLayout coordinatorLayout;
    String servTypeId ="";


    public ParentRecycleViewAdapter(Context context, List<ServiceCatagoryDetails> serviceCategoryList,
                                    int childLayout,CoordinatorLayout coordinatorLayout,String servTypeId) {

        this.context = context;
        this.servTypeId = servTypeId;
        this.childLayout = childLayout;
        this.serviceCategoryList = serviceCategoryList;
        this.coordinatorLayout = coordinatorLayout;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ParentRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.parent_recycleview_layout, parent, false);
        return new ParentRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParentRecycleViewHolder holder, int position) {

        holder.bind(serviceCategoryList.get(position),childLayout,coordinatorLayout,servTypeId,position);
    }

    @Override
    public int getItemCount() {
        return serviceCategoryList.size();
    }

    // Resolved view changing issue after adding this
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

}
