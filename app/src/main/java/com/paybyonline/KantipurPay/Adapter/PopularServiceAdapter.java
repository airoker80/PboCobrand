package com.paybyonline.KantipurPay.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.paybyonline.KantipurPay.Adapter.Model.ServiceCategoryDetailsGrid;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.PopularServiceViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anish on 8/31/2016.
 */
public class PopularServiceAdapter extends  RecyclerView.Adapter<PopularServiceViewHolder>{


    List<ServiceCategoryDetailsGrid> mRelatedItemList = Collections.emptyList();
    LayoutInflater inflater;
    Context mContext;


    public PopularServiceAdapter(Context context, List<ServiceCategoryDetailsGrid> itemList) {

        this.mContext = context;
        this.mRelatedItemList = itemList;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public PopularServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.popular_service_adapter, parent, false);
        return new PopularServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PopularServiceViewHolder holder, int position) {

        final ServiceCategoryDetailsGrid model = mRelatedItemList.get(position);
        holder.bind(model);

    }

    @Override
    public int getItemCount() {

        return mRelatedItemList.size();
    }





}


