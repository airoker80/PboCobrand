package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.PaymentProfile;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.ProfilePaymentProfileViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anish on 11/7/2016.
 */
public class ProfilePaymentProfileAdapter extends RecyclerView.Adapter<ProfilePaymentProfileViewHolder> {

    Context context;
    List<PaymentProfile> list = Collections.emptyList();
    LayoutInflater inflater;

   /* ProfilePaymentProfileAdapter   profileAdapter = new ProfilePaymentProfileAdapter(
            getActivity(), listOfProfiles){

    }*/

    public ProfilePaymentProfileAdapter(Context context, List<PaymentProfile> list) {

        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ProfilePaymentProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.user_profile_payment_profile, parent, false);
        return new ProfilePaymentProfileViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ProfilePaymentProfileViewHolder holder, int position) {
        final PaymentProfile model = list.get(position);
        Log.i("PaymentProfile ",model+"");
        holder.bind(model);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

 /*   @Override
    public ProfilePaymentProfileModel onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ProfilePaymentProfileModel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }*/
}
