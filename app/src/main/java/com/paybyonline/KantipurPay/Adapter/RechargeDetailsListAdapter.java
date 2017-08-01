package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.RechargeDetailsData;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.RechargeDetailsViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.Collections;
import java.util.List;


public class RechargeDetailsListAdapter  extends RecyclerView.Adapter<RechargeDetailsViewHolder>  {

	Context context;
	List<RechargeDetailsData> list = Collections.emptyList();
	LayoutInflater inflater;

	public RechargeDetailsListAdapter(Context context,
									  List<RechargeDetailsData> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}


	@Override
	public RechargeDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = inflater.inflate(R.layout.recharge_details_data_list, parent, false);
		return new RechargeDetailsViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(RechargeDetailsViewHolder holder, int position) {
		final RechargeDetailsData model = list.get(position);
		holder.bind(model);
	}

	@Override
	public int getItemCount() {
		return list.size();
	}
}
