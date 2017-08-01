package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paybyonline.KantipurPay.Adapter.Model.Availability;
import com.paybyonline.KantipurPay.Adapter.Model.FlightDetailsModel;
import com.paybyonline.KantipurPay.Fragment.NewBookFlightFragment;
import com.paybyonline.KantipurPay.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sameer on 5/18/2017.
 */

public class TwoWayFlightDetailsAdapterNew extends RecyclerView.Adapter<TwoWayFlightDetailsAdapterNew.ViewHolder> {
    private int lastCheckedPosition = -1;
    Context context;
    List<Availability> availabilityList;
    public checkboxListner checkbox;
    String to_time;

    public TwoWayFlightDetailsAdapterNew(Context context, List<Availability> availabilityList, checkboxListner checkbox, String to_time) {
        this.context = context;
        this.availabilityList = availabilityList;
        this.checkbox = checkbox;
        this.to_time = to_time;
    }

    @Override
    public TwoWayFlightDetailsAdapterNew.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flight_roundtrip_model, parent, false);
        TwoWayFlightDetailsAdapterNew.ViewHolder viewHolder = new TwoWayFlightDetailsAdapterNew.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TwoWayFlightDetailsAdapterNew.ViewHolder holder, final int position) {
        final Availability availability = availabilityList.get(position);
        holder.airlineName.setText(availability.getAirline());
        holder.flightTime.setText(availability.getDepartureTime()+"-"+availability.getArrivalTime());
        holder.flightClass.setText("Class :"+availability.getFlightClassCode());
        String upperString = availability.getAircraftType().substring(0,1).toUpperCase() + availability.getAircraftType().substring(1);
        holder.flightType.setText(upperString);
//        holder.flightTime.setText(availability.getFlightTime());
        holder.flightPrice.setText(availability.getSum());
        holder.flightPrice.setTypeface(NewBookFlightFragment.mediumBold);
        holder.flightTime.setTypeface(NewBookFlightFragment.mediumBold);
        holder.checkFlight.setChecked(position == lastCheckedPosition);


        Picasso.with(context)
                .load(availability.getAirlineLogo())
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.noimg)
                .into(holder.airlineImg);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        holder.airlineImg.setColorFilter(filter);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastCheckedPosition==holder.getAdapterPosition()){
                    checkbox.getFlightValue("0");
                    lastCheckedPosition= -1;
                    holder.checkFlight.setChecked(false);
//                    lastCheckedPosition = holder.getAdapterPosition();
//                    notifyItemRangeChanged(0, availabilityList.size());
                    // lastCheckedPosition = holder.getAdapterPosition();
                }else {
                    lastCheckedPosition = holder.getAdapterPosition();
                    notifyItemRangeChanged(0, availabilityList.size());
                    if (checkbox != null) {
                        checkbox.getFlightValue(holder.flightPrice.getText().toString());
                        Log.i("pressed", "pressed and value passed");
                    } else {
                        checkbox.getFlightValue("");
                        Log.i("notpressed", "value not passed");
                    }
                    to_time = holder.flightTime.getText().toString();
                    checkbox.setTime(to_time, holder.flightType.getText().toString(), holder.flightClass.getText().toString(), holder.airlineName.getText().toString(), String.valueOf(position));

                }

            }
        });
        holder.checkFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastCheckedPosition == holder.getAdapterPosition()) {
                    checkbox.getFlightValue("0");
                    lastCheckedPosition = -1;
                    holder.checkFlight.setChecked(false);
//                    lastCheckedPosition = holder.getAdapterPosition();
//                    notifyItemRangeChanged(0, availabilityList.size());
                    // lastCheckedPosition = holder.getAdapterPosition();
                } else {
                    lastCheckedPosition = holder.getAdapterPosition();
                    notifyItemRangeChanged(0, availabilityList.size());
                    if (checkbox != null) {
                        checkbox.getFlightValue(holder.flightPrice.getText().toString());
                        Log.i("pressed", "pressed and value passed");
                    } else {
                        checkbox.getFlightValue("");
                        Log.i("notpressed", "value not passed");
                    }
                    to_time = holder.flightTime.getText().toString();
                    Log.d("position", String.valueOf(position));
                    checkbox.setTime(to_time, holder.flightType.getText().toString(), holder.flightClass.getText().toString(), holder.airlineName.getText().toString(), String.valueOf(position));
/*
                checkbox.passAvailabilityDetails(availability.getFuelSurcharge(),availability.getInfantFare(),availability.getChildFare()
                        ,availability.getAirline(),availability.getAdultFare(),availability.getDeparture(),
                        availability.getAircraftType(),availability.getAgencyCommission(),availability.getSum(),
                        availability.getChild(),availability.getInfant(),availability.getDepartureTime(),
                        availability.getFlightDate(),availability.getAdult(),availability.getResFare(),
                        availability.getFreeBaggage(),availability.getArrival(),availability.getFlightId(),
                        availability.getFlightNo(),availability.getArrivalTime(),availability.getCurrency(),
                        availability.getAirlineLogo(),availability.getRefundable(),availability.getTax(),
                        availability.getDuration(),availability.getFlightClassCode(),availability.getChildCommission(),position);
*/
                    checkbox.passAvailabilityDetails(String.valueOf(holder.getPosition()));

                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return availabilityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView airlineImg;
        TextView flightTime, airlineName, flightClass, flightType, flightPrice;
        CheckBox checkFlight;

        public ViewHolder(View itemView) {
            super(itemView);

            airlineName = (TextView) itemView.findViewById(R.id.airlineName);
            airlineImg = (ImageView) itemView.findViewById(R.id.airlineImg);
            flightTime = (TextView) itemView.findViewById(R.id.flightTime);
            flightClass = (TextView) itemView.findViewById(R.id.flightClass);
            flightType = (TextView) itemView.findViewById(R.id.flightType);
            flightPrice = (TextView) itemView.findViewById(R.id.flightPrice);
            checkFlight = (CheckBox) itemView.findViewById(R.id.checkFlight1);

        }
    }

    public interface checkboxListner {
        void getFlightValue(String price);
        void  setTime(String time, String traveltype, String travelClass, String airelineName,String position);
        void  passAvailabilityDetails(String position);
    }
    }


