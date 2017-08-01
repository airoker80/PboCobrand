package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.NationalityModel;
import com.paybyonline.KantipurPay.Adapter.Model.PassengerModule;
import com.paybyonline.KantipurPay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 5/19/2017.
 */

public class PassengerListAdapter extends  RecyclerView.Adapter<PassengerListAdapter.ViewHolder>{
    private int lastCheckedPosition = -1;
    Context context;
    List<PassengerModule> passengerModuleList = new ArrayList<PassengerModule>();

    public PassengerListAdapter(Context context, List<PassengerModule> passengerModuleList) {
        this.context = context;
        this.passengerModuleList = passengerModuleList;
    }

    @Override
    public PassengerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.passenger_model_view,parent,false);
        PassengerListAdapter.ViewHolder viewHolder = new PassengerListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PassengerListAdapter.ViewHolder holder, final int position) {
        final PassengerModule passengerModule = passengerModuleList.get(position);
        holder.passenger_name.setText(passengerModule.getPassengerName());
        holder.passenger_sn.setText(passengerModule.getPassengerSN());
        if (position==lastCheckedPosition){
            holder.itemView.setBackgroundColor(Color.parseColor("#0c6093"));
            holder.passenger_sn.setTextColor(Color.WHITE);
            holder.passenger_name.setTextColor(Color.WHITE);
//            holder.passenger_name.setTextColor(Color.WHITE);
            holder.flight_arrow_down.setImageResource(R.drawable.ic_flight_arrow_down_white);

        }else {
            holder.itemView.setBackgroundColor(Color.parseColor("#00000000"));
            holder.passenger_sn.setTextColor(Color.parseColor("#0c6093"));
            holder.passenger_name.setTextColor(Color.parseColor("#B7B7B7"));
            holder.flight_arrow_down.setImageResource(R.drawable.ic_flight_arrow_down_bluis);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPassengerDetailsDialog(passengerModuleList,position);
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, passengerModuleList.size());
//                holder.flight_arrow_down.setClickable(true);
//                holder.flight_arrow_down.setEnabled(true);

            }
        });
        holder.flight_arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPassengerDetailsDialog(passengerModuleList,position);
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, passengerModuleList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return passengerModuleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView passenger_sn,passenger_name;
        ImageView flight_arrow_down;
        public ViewHolder(View itemView) {
            super(itemView);
            passenger_sn = (TextView)itemView.findViewById(R.id.passenger_sn);
            passenger_name = (TextView)itemView.findViewById(R.id.passenger_name);
            flight_arrow_down = (ImageView) itemView.findViewById(R.id.flight_arrow_down);
//            flight_arrow_down.setEnabled(false);


        }
    }

    void createPassengerDetailsDialog( final List<PassengerModule> passengerModuleList,int position){
//        textView.setTextColor(Color.parseColor("#B7b7b7"));
        final PassengerModule passengerModule =passengerModuleList.get(position);
        final EditText dialog_user_1st_name,dialog_user_last_name;
        final Spinner MrMS;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.dialog_passenger_details,null);
        dialog_user_1st_name=(EditText)view.findViewById(R.id.dialog_user_1st_name);
        dialog_user_last_name=(EditText)view.findViewById(R.id.dialog_user_last_name);
        MrMS=(Spinner)view.findViewById(R.id.mmmrsId);

                dialog_user_1st_name.setText(passengerModule.getPassengerName());
                dialog_user_last_name.setText(passengerModule.getPassengerLastName());

        linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        final AlertDialog builder = new AlertDialog.Builder(context)
                .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)
                .setView(view)
                .setTitle("Passenger Details")
                .create();
        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button btnAccept = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        passengerModule.setPassengerName(dialog_user_1st_name.getText().toString());
                        passengerModule.setPassengerLastName( dialog_user_last_name.getText().toString());
                        passengerModule.setPassengerGender(MrMS.getSelectedItem().toString());
                        Log.e("name",passengerModule.getPassengerName());
                       //textView.setText(passengerModule.getPassengerName());
                        notifyDataSetChanged();
                        builder.dismiss();
                    }
                });

                final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);
                btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
            }
        });
        builder.show();
    }
}
