package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.PlaceFlightModel;
import com.paybyonline.KantipurPay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 5/19/2017.
 */

public class PlaceFlightAdapter extends  RecyclerView.Adapter<PlaceFlightAdapter.ViewHolder>{
    TextView setFlightPlaceTxt,setFlightPlaceTxt1,setFlightPlaceTxt2;
    Context context;
    List<PlaceFlightModel> placeFlightModelList = new ArrayList<PlaceFlightModel>();
    AlertDialog alertDialog;

    public PlaceFlightAdapter(TextView setFlightPlaceTxt, TextView setFlightPlaceTxt1, TextView setFlightPlaceTxt2, Context context, List<PlaceFlightModel> placeFlightModelList, AlertDialog alertDialog) {
        this.setFlightPlaceTxt = setFlightPlaceTxt;
        this.setFlightPlaceTxt1 = setFlightPlaceTxt1;
        this.setFlightPlaceTxt2 = setFlightPlaceTxt2;
        this.context = context;
        this.placeFlightModelList = placeFlightModelList;
        this.alertDialog = alertDialog;
    }

    @Override
    public PlaceFlightAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_flight,parent,false);
        PlaceFlightAdapter.ViewHolder viewHolder = new PlaceFlightAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlaceFlightAdapter.ViewHolder holder, final int position) {
        final PlaceFlightModel placeFlightModel = placeFlightModelList.get(position);
        holder.flight_place_name.setText(placeFlightModel.getPlace());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkPlaceName.setChecked(true);
                setFlightPlaceTxt.setText(placeFlightModel.getPlaceCode());
                setFlightPlaceTxt1.setText(placeFlightModel.getPlace());
                setFlightPlaceTxt2.setText(placeFlightModel.getPlace()+", "+placeFlightModel.getCountryName());
                final Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                };
                handler.postDelayed(runnable,500);

            }
        });

    }

    @Override
    public int getItemCount() {
        return placeFlightModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView flight_place_name;
        CheckBox checkPlaceName;
        public ViewHolder(View itemView) {
            super(itemView);
            flight_place_name = (TextView)itemView.findViewById(R.id.flight_place_name);
            checkPlaceName = (CheckBox) itemView.findViewById(R.id.checkPlaceName);


        }
    }
}
