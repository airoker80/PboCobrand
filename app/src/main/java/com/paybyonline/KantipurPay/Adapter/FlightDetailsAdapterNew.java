package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.Availability;
import com.paybyonline.KantipurPay.Adapter.Model.FlightDetailsModel;
import com.paybyonline.KantipurPay.Fragment.NewBookFlightFragment;
import com.paybyonline.KantipurPay.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sameer on 5/18/2017.
 */

public class FlightDetailsAdapterNew extends RecyclerView.Adapter<FlightDetailsAdapterNew.ViewHolder> {
    private int lastCheckedPosition = -1;
    Context context;
    PassAdultPriceInterface priceInterface;
    List<Availability> availabilityList;
    String  from_time;

    public FlightDetailsAdapterNew(Context context, PassAdultPriceInterface priceInterface, List<Availability> availabilityList, String from_time) {
        this.context = context;
        this.priceInterface = priceInterface;
        this.availabilityList = availabilityList;
        this.from_time = from_time;
    }

    @Override
    public FlightDetailsAdapterNew.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flight_selection_model,parent,false);
        FlightDetailsAdapterNew.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FlightDetailsAdapterNew.ViewHolder holder, final int position) {
        final Availability flightDetailsModel = availabilityList.get(position);

        Picasso.with(context)
                .load(flightDetailsModel.getAirlineLogo())
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.noimg)
                .into(holder.airlineImg);

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        holder.airlineImg.setColorFilter(filter);
        holder.airlineName.setText(flightDetailsModel.getAirline());
        holder.flightTime.setText(flightDetailsModel.getDepartureTime()+"-"+flightDetailsModel.getArrivalTime());
        holder.flightClass.setText("Class :"+flightDetailsModel.getFlightClassCode());
        holder.flightType.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        String upperString = flightDetailsModel.getAircraftType().substring(0,1).toUpperCase() + flightDetailsModel.getAircraftType().substring(1);
        holder.flightType.setText(upperString);
//        holder.flightTime.setText(flightDetailsModel.getFlightTime());
        holder.flightPrice.setText(flightDetailsModel.getSum());
        holder.flightPrice.setTypeface(NewBookFlightFragment.mediumBold);
        holder.flightTime.setTypeface(NewBookFlightFragment.mediumBold);
        holder.checkFlight.setChecked(position == lastCheckedPosition);
        holder. itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastCheckedPosition==holder.getAdapterPosition()){
                    priceInterface.passAdultPrice(0);
                    lastCheckedPosition= -1;
                    holder.checkFlight.setChecked(false);
//                    lastCheckedPosition = holder.getAdapterPosition();
//                    notifyItemRangeChanged(0, availabilityList.size());
                    // lastCheckedPosition = holder.getAdapterPosition();
                }else {
                    lastCheckedPosition = holder.getAdapterPosition();
                    notifyItemRangeChanged(0, availabilityList.size());
                    priceInterface.passAdultPrice(Double.parseDouble(holder.flightPrice.getText().toString()));
                    from_time = holder.flightTime.getText().toString();
                    priceInterface.passTime(from_time,holder.flightType.getText().toString(),holder.flightClass.getText().toString(),holder.airlineName.getText().toString(), String.valueOf(position));
                    priceInterface.passAvailability(position);
                }
            }
        });
        holder. checkFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastCheckedPosition==holder.getAdapterPosition()){
                   priceInterface.passAdultPrice(0);
                    lastCheckedPosition= -1;
//                    lastCheckedPosition = holder.getAdapterPosition();
//                    notifyItemRangeChanged(0, availabilityList.size());
                   // lastCheckedPosition = holder.getAdapterPosition();
                }else {
                    lastCheckedPosition = holder.getAdapterPosition();
                    notifyItemRangeChanged(0, availabilityList.size());
                    priceInterface.passAdultPrice(Double.parseDouble(holder.flightPrice.getText().toString()));
                    from_time = holder.flightTime.getText().toString();
                    priceInterface.passTime(from_time,holder.flightType.getText().toString(),holder.flightClass.getText().toString(),holder.airlineName.getText().toString(), String.valueOf(position));
                    priceInterface.passAvailability(position);
                }


//                Log.e("flight_time",from_time);
            }
        });
/*        holder.checkFlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkFlight.isChecked()){
                    priceInterface.passAdultPrice(0);
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return availabilityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView airlineImg;
        TextView flightTime,airlineName,flightClass,flightType,flightPrice;
        CheckBox checkFlight;
        public ViewHolder(View itemView) {
            super(itemView);
            airlineImg=(ImageView) itemView.findViewById(R.id.airlineImg);
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
        void  passTime(String time, String traveltype, String travelClass, String airelineNamem,String position);
        void  passAvailability(int passPosition);
    }
}
