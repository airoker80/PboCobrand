package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.ServiceCatagoryDetails;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.RechargeNowPageViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anish on 8/4/2016.
 */
public class RechargePageAdapter extends RecyclerView.Adapter<RechargeNowPageViewHolder> {

    Context context;
    List<ServiceCatagoryDetails> serviceCategoryList =
            new ArrayList<ServiceCatagoryDetails>();
    LayoutInflater inflater;
    int childLayout;
    CoordinatorLayout coordinatorLayout;
    String servCatId;
    String servTypeId;


    public RechargePageAdapter(Context context, List<ServiceCatagoryDetails> serviceCategoryList,
                               int childLayout,CoordinatorLayout coordinatorLayout,String servCatId,String servTypeId) {

        this.context = context;
        this.childLayout = childLayout;
        this.serviceCategoryList = serviceCategoryList;
        this.servCatId = servCatId;
        this.servTypeId = servTypeId;
        this.coordinatorLayout = coordinatorLayout;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RechargeNowPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.parent_recycleview_layout, parent, false);
        return new RechargeNowPageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RechargeNowPageViewHolder holder, int position) {

        if(servCatId.trim().equals(serviceCategoryList.get(position).getServiceCategoryId())){

            holder.bind(serviceCategoryList.get(position),childLayout,coordinatorLayout,servCatId,servTypeId);

       }

    }

    @Override
    public int getItemCount() {
        return serviceCategoryList.size();
    }

}
