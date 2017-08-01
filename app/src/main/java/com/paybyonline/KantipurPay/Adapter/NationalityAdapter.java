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

import com.paybyonline.KantipurPay.Adapter.Model.NationalityModel;
import com.paybyonline.KantipurPay.Adapter.Model.PlaceFlightModel;
import com.paybyonline.KantipurPay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 5/19/2017.
 */

public class NationalityAdapter extends  RecyclerView.Adapter<NationalityAdapter.ViewHolder>{
    TextView setFlightPlaceTxt;
    Context context;
    List<NationalityModel> nationalityModelList = new ArrayList<NationalityModel>();
    AlertDialog alertDialog;

    public NationalityAdapter(TextView setFlightPlaceTxt, Context context, List<NationalityModel> nationalityModelList, AlertDialog alertDialog) {
        this.setFlightPlaceTxt = setFlightPlaceTxt;
        this.context = context;
        this.nationalityModelList = nationalityModelList;
        this.alertDialog= alertDialog;
    }

    @Override
    public NationalityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_flight,parent,false);
        NationalityAdapter.ViewHolder viewHolder = new NationalityAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NationalityAdapter.ViewHolder holder, final int position) {
        final NationalityModel nationalityModel = nationalityModelList.get(position);
        holder.flight_place_name.setText(nationalityModel.getNationality());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkPlaceName.setChecked(true);
                setFlightPlaceTxt.setText(nationalityModel.getNationality());
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
        return nationalityModelList.size();
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
