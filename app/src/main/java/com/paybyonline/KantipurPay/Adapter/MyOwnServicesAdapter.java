package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.ServiceCatagoryDetails;
import com.paybyonline.KantipurPay.Adapter.Model.ServiceType;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.MyFavServicesViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.List;

/**
 * Created by Anish on 8/10/2016.
 */
public class MyOwnServicesAdapter  extends RecyclerView.Adapter<MyFavServicesViewHolder>{

    Context context;
    List<ServiceCatagoryDetails> serviceCategoryList ;

    public MyOwnServicesAdapter(Context context, List<ServiceCatagoryDetails> serviceCategoryList) {

        this.context = context;
        this.serviceCategoryList = serviceCategoryList;
    }

    @Override
    public MyFavServicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_fav_services_layout, parent, false);

        return new MyFavServicesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyFavServicesViewHolder holder, int position) {

        ServiceCatagoryDetails serviceCatagoryDetails = serviceCategoryList.get(position);
        Log.i("serviceCatagoryDetails", "" + serviceCatagoryDetails);
        List<ServiceType> serviceTypeList=serviceCatagoryDetails.getServiceTypeArrayList();
        holder.bind(context,serviceTypeList.get(position));


    }

    @Override
    public int getItemCount() {

        return serviceCategoryList.size();
    }
}