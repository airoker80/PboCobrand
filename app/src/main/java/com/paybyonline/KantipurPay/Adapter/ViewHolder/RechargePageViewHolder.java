package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.ServiceCategoryDetailsGrid;

/**
 * Created by Anish on 8/4/2016.
 */
public class RechargePageViewHolder   extends RecyclerView.ViewHolder {

    protected RecyclerView relatedItemsRecyclerView;
    protected TextView relatedMoviesHeader;

    // protected CircularProgressView progressView;

    public RechargePageViewHolder(View view) {
        super(view);
       // relatedItemsRecyclerView = (RecyclerView) view.findViewById(R.id.relatedItemsRecyclerView);
       // relatedMoviesHeader = (TextView) view.findViewById(R.id.relatedMoviesHeader);

    }
    public void bind(final ServiceCategoryDetailsGrid serviceCategoryDetailsGrid) {

        Log.i("Hello", "Hello");
        //relatedMoviesHeader.setText("Hello");


    }
}
