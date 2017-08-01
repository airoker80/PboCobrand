package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.paybyonline.KantipurPay.Adapter.Model.ServiceCategoryDetailsGrid;
import com.paybyonline.KantipurPay.Fragment.RechargeNowFragment;
import com.paybyonline.KantipurPay.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Anish on 8/30/2016.
 */
public class PopularServiceViewHolder extends RecyclerView.ViewHolder {

    ImageView grid_image;
    View holder;

    public PopularServiceViewHolder(View itemView) {
        super(itemView);

            this.holder=itemView;
            grid_image = (ImageView) holder.findViewById(R.id.grid_image);
    }

    public void bind(final ServiceCategoryDetailsGrid grid){

        Log.i("grid",""+grid.getServiceCategoryName());

        Picasso.with(holder.getContext())
                .load(grid.getServiceCatImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(grid_image);


        grid_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Fragment fragment = new RechargeNowFragment();
                FragmentManager fragmentManager =((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("servCatName", grid.getServiceCategoryName());
                bundle.putString("servCatId", grid.getServiceCategoryId());
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();

            }
        });

    }
}
