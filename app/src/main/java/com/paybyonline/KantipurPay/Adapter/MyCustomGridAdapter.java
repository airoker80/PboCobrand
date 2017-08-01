package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.MyCustomRecycleViewModel;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.MyCustomRecycleViewHolder;
import com.paybyonline.KantipurPay.R;

import java.util.Collections;
import java.util.List;


/**
 * Created by Anish on 8/17/2016.
 */
public class MyCustomGridAdapter extends RecyclerView.Adapter<MyCustomRecycleViewHolder> {

     Context mContext;
    List<MyCustomRecycleViewModel> payTypeList = Collections.emptyList();
    LayoutInflater inflater;

    public MyCustomGridAdapter(Context context,List<MyCustomRecycleViewModel> payTypeList ) {

        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.payTypeList=payTypeList;
        Log.i("userPayNamelist", " " + payTypeList);

    }


    @Override
    public MyCustomRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.my_custom_grid , parent, false);
        Log.i("my_custom_grid", " " + parent);
        return new MyCustomRecycleViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyCustomRecycleViewHolder holder, int position) {

        final MyCustomRecycleViewModel model = payTypeList.get(position);
        Log.i("userPayName1", " " + model);
    /*    holder.holderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
   /*     holder.holdItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("position", "" + position);
               *//* Intent intent = new Intent(context, BuyProductFormActivity.class);
                intent.putExtra("scstId", model.getScstId());
                context.startActivity(intent);

                int pos = recyclerView.getChildAdapterPosition(v);
                inOutMessageModel = msgBulkList.get(pos);*//*
                Intent intent = new Intent();
                intent = new Intent(context, ShowMessageActivity.class);
                intent.putExtra("id", model.getId());
                context.startActivity(intent);

            }
        });*/


        holder.bind(model);

    }

    @Override
    public int getItemCount() {
        return payTypeList.size();
    }
}
