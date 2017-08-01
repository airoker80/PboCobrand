package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.SendReceiveMoney;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.SendMoneyDetailsViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by mefriend24 on 11/25/16.
 */
public class SendMoneyDetailsAdapter extends RecyclerView.Adapter<SendMoneyDetailsViewHolder>  {

    Context context;
    List<SendReceiveMoney> list = Collections.emptyList();
    LayoutInflater inflater;
    CoordinatorLayout coordinatorLayout;


    public SendMoneyDetailsAdapter(Context context, List<SendReceiveMoney> list, CoordinatorLayout coordinatorLayout) {

        this.context = context;
        this.list = list;
        this.coordinatorLayout = coordinatorLayout;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SendMoneyDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.content_send_money, parent, false);
        return new SendMoneyDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SendMoneyDetailsViewHolder holder, int position) {
        final SendReceiveMoney model = list.get(position);
        holder.bind(model,coordinatorLayout);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

