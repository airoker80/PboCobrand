package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.ServiceCategoryDetailsGrid;
import com.paybyonline.KantipurPay.Interface.ItemClickListener;
import com.paybyonline.KantipurPay.R;

/**
 * Created by Anish on 8/18/2016.
 */
public class ServiceCategoryGridViewHolder extends RecyclerView.ViewHolder {

    TextView servCatView ;
    ImageView servCatTypeLogo;
   // RecyclerView  serTypelistView;
    Context context;
    private ItemClickListener clickListener;
    public ServiceCategoryGridViewHolder(View itemView) {
        super(itemView);
        // this.context=c;
        servCatView= (TextView)itemView.findViewById(R.id.grid_text1);

    }

    public void bind(final ServiceCategoryDetailsGrid serviceCategoryDetailsGrid) {

        Log.i("Hello","Hello");
        servCatView.setText("Hello");


    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


}


