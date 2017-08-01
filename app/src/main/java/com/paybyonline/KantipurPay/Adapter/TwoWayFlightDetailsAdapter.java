package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.FlightDetailsModel;
import com.paybyonline.KantipurPay.Fragment.NewBookFlightFragment;
import com.paybyonline.KantipurPay.R;

import java.util.List;

/**
 * Created by Sameer on 5/18/2017.
 */

public class TwoWayFlightDetailsAdapter extends RecyclerView.Adapter<TwoWayFlightDetailsAdapter.ViewHolder> {
    private int lastCheckedPosition = -1;
    Context context;
    List<FlightDetailsModel> flightDetailsModelList;
    public checkboxListner checkbox;
    String to_time;

    public TwoWayFlightDetailsAdapter(Context context, List<FlightDetailsModel> flightDetailsModelList, checkboxListner checkboxListner,String to_time) {
        this.context = context;
        this.flightDetailsModelList = flightDetailsModelList;
        this.checkbox = checkboxListner;
        this.to_time = to_time;
    }

    @Override
    public TwoWayFlightDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flight_roundtrip_model, parent, false);
        TwoWayFlightDetailsAdapter.ViewHolder viewHolder = new TwoWayFlightDetailsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TwoWayFlightDetailsAdapter.ViewHolder holder, int position) {
        FlightDetailsModel flightDetailsModel = flightDetailsModelList.get(position);
        holder.airlineName.setText(flightDetailsModel.getFlightAirline());
        holder.flightTime.setText(flightDetailsModel.getFlightTime());
        holder.flightClass.setText("Class :"+flightDetailsModel.getFlightClass());
        String upperString = flightDetailsModel.getFlightType().substring(0,1).toUpperCase() + flightDetailsModel.getFlightType().substring(1);
        holder.flightType.setText(upperString);
        holder.flightTime.setText(flightDetailsModel.getFlightTime());
        holder.flightPrice.setText(flightDetailsModel.getFlightPrice());
        holder.flightPrice.setTypeface(NewBookFlightFragment.mediumBold);
        holder.flightTime.setTypeface(NewBookFlightFragment.mediumBold);
        holder.checkFlight.setChecked(position == lastCheckedPosition);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, flightDetailsModelList.size());
                if (checkbox != null) {
                    checkbox.getFlightValue(holder.flightPrice.getText().toString());
                    Log.i("pressed", "pressed and value passed");
                } else {
                    checkbox.getFlightValue("");
                    Log.i("notpressed", "value not passed");
                }
                to_time = holder.flightTime.getText().toString();
                checkbox.setTime(to_time,holder.flightType.getText().toString(),holder.flightClass.getText().toString(),holder.airlineName.getText().toString());
            }
        });
        holder.checkFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, flightDetailsModelList.size());
                if (checkbox != null) {
                    checkbox.getFlightValue(holder.flightPrice.getText().toString());
                    Log.i("pressed", "pressed and value passed");
                } else {
                    checkbox.getFlightValue("");
                    Log.i("notpressed", "value not passed");
                }
                to_time = holder.flightTime.getText().toString();
                checkbox.setTime(to_time,holder.flightType.getText().toString(),holder.flightClass.getText().toString(),holder.airlineName.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return flightDetailsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView flightTime, airlineName, flightClass, flightType, flightPrice;
        CheckBox checkFlight;

        public ViewHolder(View itemView) {
            super(itemView);
            airlineName = (TextView) itemView.findViewById(R.id.airlineName);
            flightTime = (TextView) itemView.findViewById(R.id.flightTime);
            flightClass = (TextView) itemView.findViewById(R.id.flightClass);
            flightType = (TextView) itemView.findViewById(R.id.flightType);
            flightPrice = (TextView) itemView.findViewById(R.id.flightPrice);
            checkFlight = (CheckBox) itemView.findViewById(R.id.checkFlight1);

        }
    }

    public interface checkboxListner {
        void getFlightValue(String price);
        void  setTime(String time,String traveltype,String travelClass,String airelineName);
    }

}
