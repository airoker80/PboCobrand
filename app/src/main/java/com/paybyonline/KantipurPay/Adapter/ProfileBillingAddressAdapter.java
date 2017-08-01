package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.ProfileBillingAddress;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.ProfileBillingAddressViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anish on 11/7/2016.
 */
public class ProfileBillingAddressAdapter extends RecyclerView.Adapter<ProfileBillingAddressViewHolder> {

    Context context;
    List<ProfileBillingAddress> list = Collections.emptyList();
    LayoutInflater inflater;

    public ProfileBillingAddressAdapter(Context context, List<ProfileBillingAddress> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ProfileBillingAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.user_profile_billing_address, parent, false);
        return new ProfileBillingAddressViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(ProfileBillingAddressViewHolder holder, int position) {
        final ProfileBillingAddress model = list.get(position);
        Log.i("ProfileBillingAddress", "" + model);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
