package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.PurchaseReportList;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.PurchaseReportListViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anish on 8/17/2016.
 */
public class PurchaseReportListAdapter extends RecyclerView.Adapter<PurchaseReportListViewHolder>  {
    Context context;
    List<PurchaseReportList> list = Collections.emptyList();
    LayoutInflater inflater;

    public PurchaseReportListAdapter(Context context, List<PurchaseReportList> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public PurchaseReportListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.purchase_report_list, parent, false);
        return new PurchaseReportListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PurchaseReportListViewHolder holder, int position) {

        final PurchaseReportList model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void animateTo(List<PurchaseReportList> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }
    private void applyAndAnimateRemovals(List<PurchaseReportList> newModels) {
        for (int i = list.size() - 1; i >= 0; i--) {
            PurchaseReportList model = list.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<PurchaseReportList> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            PurchaseReportList model = newModels.get(i);
            if (!list.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<PurchaseReportList> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            PurchaseReportList model = newModels.get(toPosition);
            int fromPosition = list.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public PurchaseReportList removeItem(int position) {
        PurchaseReportList model = list.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, PurchaseReportList model) {
        list.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        PurchaseReportList model = list.remove(fromPosition);
        list.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}
