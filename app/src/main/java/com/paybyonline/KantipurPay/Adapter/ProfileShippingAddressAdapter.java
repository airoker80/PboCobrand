package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.ProfileShippingAddress;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.ProfileShippingAddressViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anish on 11/7/2016.
 */
public class ProfileShippingAddressAdapter  extends RecyclerView.Adapter<ProfileShippingAddressViewHolder> {

    Context context;
    List<ProfileShippingAddress> list = Collections.emptyList();
    LayoutInflater inflater;

    public ProfileShippingAddressAdapter(Context context, List<ProfileShippingAddress> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ProfileShippingAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.user_profile_shipping_address, parent, false);
        return new ProfileShippingAddressViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(ProfileShippingAddressViewHolder holder, int position) {
        final ProfileShippingAddress  model = list.get(position);
        Log.i("profileShippingAddress", "" + model);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
