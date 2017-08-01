package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
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

public class FlightDetailsAdapter extends RecyclerView.Adapter<FlightDetailsAdapter.ViewHolder> {
    private int lastCheckedPosition = -1;
    Context context;
    PassAdultPriceInterface priceInterface;
    List<FlightDetailsModel> flightDetailsModelList;
    String  from_time;

    public FlightDetailsAdapter(Context context, PassAdultPriceInterface priceInterface, List<FlightDetailsModel> flightDetailsModelList, String from_time) {
        this.context = context;
        this.priceInterface = priceInterface;
        this.flightDetailsModelList = flightDetailsModelList;
        this.from_time = from_time;
    }

    @Override
    public FlightDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flight_selection_model,parent,false);
        FlightDetailsAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FlightDetailsAdapter.ViewHolder holder, int position) {
        FlightDetailsModel flightDetailsModel = flightDetailsModelList.get(position);
        holder.airlineName.setText(flightDetailsModel.getFlightAirline());
        holder.flightTime.setText(flightDetailsModel.getFlightTime());
        holder.flightClass.setText("Class :"+flightDetailsModel.getFlightClass());
        holder.flightType.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        String upperString = flightDetailsModel.getFlightType().substring(0,1).toUpperCase() + flightDetailsModel.getFlightType().substring(1);
        holder.flightType.setText(upperString);
        holder.flightTime.setText(flightDetailsModel.getFlightTime());
        holder.flightPrice.setText(flightDetailsModel.getFlightPrice());
        holder.flightPrice.setTypeface(NewBookFlightFragment.mediumBold);
        holder.flightTime.setTypeface(NewBookFlightFragment.mediumBold);
        holder.checkFlight.setChecked(position == lastCheckedPosition);
        holder. itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, flightDetailsModelList.size());
                priceInterface.passAdultPrice(Double.parseDouble(holder.flightPrice.getText().toString()));
                from_time = holder.flightTime.getText().toString();
                priceInterface.passTime(from_time,holder.flightType.getText().toString(),holder.flightClass.getText().toString(),holder.airlineName.getText().toString());
//                Log.e("flight_time",from_time);
            }
        });
        holder. checkFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, flightDetailsModelList.size());
                priceInterface.passAdultPrice(Double.parseDouble(holder.flightPrice.getText().toString()));
                from_time = holder.flightTime.getText().toString();
                priceInterface.passTime(from_time,holder.flightType.getText().toString(),holder.flightClass.getText().toString(),holder.airlineName.getText().toString());
//                Log.e("flight_time",from_time);
            }
        });

    }

    @Override
    public int getItemCount() {
        return flightDetailsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView flightTime,airlineName,flightClass,flightType,flightPrice;
        CheckBox checkFlight;
        public ViewHolder(View itemView) {
            super(itemView);
            airlineName=(TextView) itemView.findViewById(R.id.airlineName);
            flightTime=(TextView) itemView.findViewById(R.id.flightTime);
            flightClass=(TextView) itemView.findViewById(R.id.flightClass);
            flightType=(TextView) itemView.findViewById(R.id.flightType);
            flightPrice=(TextView) itemView.findViewById(R.id.flightPrice);
            checkFlight=(CheckBox) itemView.findViewById(R.id.checkFlight);
            checkFlight.setChecked(false);
        }
    }

    public  interface PassAdultPriceInterface{
        void  passAdultPrice(double adultPrice);
        void  passTime(String time,String traveltype,String travelClass,String airelineName);
    }
}
