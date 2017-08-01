package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.paybyonline.KantipurPay.Adapter.Model.ServiceType;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.ChildRecycleViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mefriend24 on 9/20/16.
 */
public class ChildRecycleViewAdapter extends RecyclerView.Adapter<ChildRecycleViewHolder> {

    Context context;
    List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
    LayoutInflater inflater;
    LinearLayout rechargeFormLayout;
    int childLayout ;
    CoordinatorLayout coordinatorLayout;
    String serviceCatagoryId;
    String servTypeId;
    Boolean ifClick=false;
    int parentPosition;

    public ChildRecycleViewAdapter(Context context, List<ServiceType> serviceTypeList,
                                   LinearLayout rechargeFormLayout,int childLayout
            ,CoordinatorLayout coordinatorLayout,String serviceCatagoryId,String servTypeId, int parentPosition) {

        this.context = context;
        this.rechargeFormLayout = rechargeFormLayout;
        this.coordinatorLayout = coordinatorLayout;
        this.serviceTypeList = serviceTypeList;
        this.servTypeId = servTypeId;
        this.childLayout = childLayout;
        this.parentPosition = parentPosition;
        this.serviceCatagoryId = serviceCatagoryId;
        inflater = LayoutInflater.from(context);

        if(!servTypeId.isEmpty()){

            List<ServiceType> serviceTypeListTemp = new ArrayList<ServiceType>();
            for(ServiceType serviceType:serviceTypeList){
                if(serviceType.getService_type_id().equals(servTypeId)){
                    serviceTypeListTemp.add(serviceType);
                }
            }
            for(ServiceType serviceType:serviceTypeList){
                if(!(serviceType.getService_type_id().equals(servTypeId))){
                    serviceTypeListTemp.add(serviceType);
                }
            }
            this.serviceTypeList = serviceTypeListTemp;
        }

    }

    @Override
    public ChildRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(childLayout, parent, false);
        return new ChildRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChildRecycleViewHolder holder, int position) {
     /*   if(serviceTypeList.get(position).getService_type_id().equals(servTypeId)){
            ifClick=true;
            Log.i("ifClick",""+ifClick);
        }else{

            ifClick=false;
            Log.i("ifClick",""+ifClick);
        }*/
        holder.bind(serviceTypeList.get(position),rechargeFormLayout,coordinatorLayout,serviceCatagoryId,servTypeId,parentPosition);
//        holder.bind(serviceTypeList.get(position),rechargeFormLayout,coordinatorLayout,serviceCatagoryId,servTypeId,ifClick);
    }

    @Override
    public int getItemCount() {
        return serviceTypeList.size();
    }

    // Resolved view changing issue after adding this
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }


}

