package com.paybyonline.KantipurPay.Fragment;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Model.Availability;
import com.paybyonline.KantipurPay.Adapter.Model.SaveReservationModel;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Sameer on 5/19/2017.
 */

class FlightSummaryFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    List<SaveReservationModel> saveReservationModelList = new ArrayList<SaveReservationModel>();

    String adultFareIn,
    flightClassCodeOut, totalChildFareOut,
    departureIn, flightNoIn,
    flightNoOut, durationDeparture,
    totalAdultFareIn, totalOutSum,
    totalChildFare, airlinesIn,
    taxIn, airlinesOut,
    totalAdultFare, aircraftTypeIn,
    totalInSum, taxOut,
    departureTimeOut, imageOut,
    noOfChild, individualTax,
    arrivalTimeIn, totalTaxOut,
    adultFareOut, childFareIn,
    totalSum, noOfAdult,
    arrivalTimeOut, flightMode, fuelSurchargeOut, aircraftTypeOut,
    departureTimeIn, departureOut, totalChildFareIn, durationArrival,
    imageIn, individualAdultFare, flightCurrency, arrivalOut,
    flightClassCodeIn,fuelSurchargeIn,arrivalIn,childFareOut,totalFuelCharge, nationality,
    totalAdultFareOut,totalFuelChargeIn, totalFuelChargeOut, totalTaxIn,individualFuelCharge, individualChildFare,totalTax,
    bundelflightMode;

    String  departureReservationStatus,
            departureTTLTime,
            departureFlightId,
            departureAirlineID,
            departurePNRNO,
            departureTTLDate;
    String  arraivalReservationStatus,
            arraivalTTLTime,
            arraivalFlightId,
            arraivalAirlineID,
            arraivalPNRNO,
            arraivalTTLDate;
    String rindividualAdultCommission,rtaxIn ,rnoOfAdult,radultFareIn ,rflightCurrency,rtotalFlightCost ,rtaxOut,
            rtotalPboCommission ,rchildFareOut,rfuelSurchargeOut ,rfuelSurchargeIn,rnoOfChild ,rnationality,rtrip_type
            ,rindividualChildCommission,radultFareOut ,rchildFareIn;
    private MyUserSessionManager myUserSessionManager;
    CoordinatorLayout coordinatorLayout;
    Fragment fragment;
    LinearLayout continueToConfirm;
    LinearLayout thirdll,fourthll;
    ImageView outboundImage,inboundImage;
    String inboundHr,inboundMin;
    String outboundHr,outboundMin;
    TextView takeoffId1,takeoffId2,landingId1,landingId2,total_FlightPrice_summary,from_time_1,from_time_2,
            to_time_2,to_time_1,from_airline_tvm,from_travel_type_tv,from_travel_class_tv,to_airline_tv
            ,to_travel_class_tv,to_travel_type_tv,returnPassengerNumber,flightpassengerNumber
            ,place_frm_name_tv,place_to_name_tv,place_frm_name_tv1,place_to_name_tv2,to_fare_type,from_fare_type,
            inboundBaggage,outboundBaggage,inboundDuration,outboundDuration,outboundPlaceTo,inboundPlaceFrom,inboundPlaceTo
            ,outboundPlaceFrom,
            inboundWeekday,
            inboundDate,
            outboundWeekday,
            outboundDate;
    String value,totalFlightPrice,from_time,to_time,from_travel_type,from_travel_class,from_airline
            ,to_travel_type,to_travel_class,to_airline,adult,child,place_frm,place_frm_name,place_to,landing_place_name
            ,inboundPosition,outboundPosition,flight_departure_date_details,return_departure_date_details,nepFlightToken,searchId;

    LinearLayout goneSummary;

    List<Availability> inboundList = new ArrayList<Availability>();
    List<Availability> outboundList = new ArrayList<Availability>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_summary, container, false);
        ((DashBoardActivity) getActivity()).setTitle("Flight Summary");
        ((DashBoardActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        myUserSessionManager = new MyUserSessionManager(getActivity());

        inboundList = getArguments().getParcelableArrayList("inboundList");
        outboundList = getArguments().getParcelableArrayList("outboundList");


        value = getArguments().getString("flag");
        searchId = getArguments().getString("searchId");

        inboundPosition = getArguments().getString("inboundPosition");
        outboundPosition = getArguments().getString("outboundPosition");
        Log.d("position",inboundPosition + " "+ outboundPosition);
        place_to = getArguments().getString("place_to");
        flight_departure_date_details = getArguments().getString("flight_departure_date_details");
        return_departure_date_details = getArguments().getString("return_departure_date_details");
        landing_place_name = getArguments().getString("landing_place_name");
        place_frm = getArguments().getString("place_frm");
        place_frm_name = getArguments().getString("place_frm_name");
        nepFlightToken = getArguments().getString("nepFlightToken");
        adult = getArguments().getString("adult");
        child = getArguments().getString("child");
        totalFlightPrice = getArguments().getString("totalFlightPrice");
        from_time = getArguments().getString("from_time");
        to_time = getArguments().getString("to_time");
        bundelflightMode = getArguments().getString("flightMode");
        from_travel_type = getArguments().getString("from_travel_type");
        from_travel_class = getArguments().getString("from_travel_class");
        from_airline = getArguments().getString("from_airline");
        to_travel_type = getArguments().getString("to_travel_type");
        to_travel_class = getArguments().getString("to_travel_class");
        to_airline = getArguments().getString("to_airline");

        thirdll=(LinearLayout)view.findViewById(R.id.thirdll);
        fourthll=(LinearLayout)view.findViewById(R.id.fourthll);
        goneSummary=(LinearLayout)view.findViewById(R.id.goneSummary);

        inboundImage=(ImageView)view.findViewById(R.id.inboundImage);
        outboundImage=(ImageView)view.findViewById(R.id.outboundImage);

        inboundWeekday=(TextView)view.findViewById(R.id.inboundWeekday);
        inboundDate=(TextView)view.findViewById(R.id.inboundDate);
        outboundWeekday=(TextView)view.findViewById(R.id.outboundWeekday);
        outboundDate=(TextView)view.findViewById(R.id.outboundDate);
        outboundPlaceTo=(TextView)view.findViewById(R.id.outboundPlaceTo);
        inboundPlaceFrom=(TextView)view.findViewById(R.id.inboundPlaceFrom);
        inboundPlaceTo=(TextView)view.findViewById(R.id.inboundPlaceTo);
        outboundPlaceFrom=(TextView)view.findViewById(R.id.outboundPlaceFrom);
        inboundDuration=(TextView)view.findViewById(R.id.inboundDuration);
        outboundDuration=(TextView)view.findViewById(R.id.outboundDuration);
        inboundBaggage=(TextView)view.findViewById(R.id.inboundBaggage);
        outboundBaggage=(TextView)view.findViewById(R.id.outboundBaggage);
//        from_fare_type=(TextView)view.findViewById(R.id.from_fare_type);
        to_fare_type=(TextView)view.findViewById(R.id.to_fare_type);
        place_frm_name_tv1=(TextView)view.findViewById(R.id.place_frm_name_tv1);
        place_to_name_tv2=(TextView)view.findViewById(R.id.place_to_name_tv2);
        takeoffId1=(TextView)view.findViewById(R.id.takeoffId1);
        takeoffId2=(TextView)view.findViewById(R.id.takeoffId2);
        landingId1=(TextView)view.findViewById(R.id.landingId1);
        landingId2=(TextView)view.findViewById(R.id.landingId2);
        total_FlightPrice_summary=(TextView)view.findViewById(R.id.total_FlightPrice_summary);
        place_frm_name_tv=(TextView)view.findViewById(R.id.place_frm_name_tv);
        place_to_name_tv=(TextView)view.findViewById(R.id.place_to_name_tv);
        from_time_2=(TextView)view.findViewById(R.id.from_time_2);
        from_time_1=(TextView)view.findViewById(R.id.from_time_1);
        to_time_2=(TextView)view.findViewById(R.id.to_time_2);
        to_time_1=(TextView)view.findViewById(R.id.to_time_1);
        from_airline_tvm=(TextView)view.findViewById(R.id.from_airline_tv);
        from_travel_type_tv=(TextView)view.findViewById(R.id.from_travel_type_tv);
        from_travel_class_tv=(TextView)view.findViewById(R.id.from_travel_class_tv);
        to_airline_tv=(TextView)view.findViewById(R.id.to_airline_tv);
        to_travel_class_tv=(TextView)view.findViewById(R.id.to_travel_class_tv);
        to_travel_type_tv=(TextView)view.findViewById(R.id.to_travel_type_tv);
        returnPassengerNumber=(TextView)view.findViewById(R.id.returnPassengerNumber);
        flightpassengerNumber=(TextView)view.findViewById(R.id.flightpassengerNumber);

        continueToConfirm=(LinearLayout)view.findViewById(R.id.continueToConfirm);

        takeoffId1.setTypeface(NewBookFlightFragment.mediumBold);
        takeoffId2.setTypeface(NewBookFlightFragment.mediumBold);
        landingId1.setTypeface(NewBookFlightFragment.mediumBold);
        landingId2.setTypeface(NewBookFlightFragment.mediumBold);

        if (value=="false"){
            goneSummary.setVisibility(View.VISIBLE);
            try {
                takeoffId1.setText(place_frm);
                landingId1.setText(place_to);



                String split_from_time[] = from_time.split("-");
                from_time_1.setText(split_from_time[0]);
                from_time_2.setText(split_from_time[1]);

                String split_departure_date_details[] = flight_departure_date_details.split(",");
                String split_return_date_details[] = return_departure_date_details.split(",");

                inboundWeekday.setText(split_departure_date_details[0] +", ");
//                inboundDate.setText(split_departure_date_details[1]);

                outboundWeekday.setText(split_return_date_details[0]+", ");
//                outboundDate.setText(split_return_date_details[1]);

                String split_to_time[] = to_time.split("-");
                to_time_1.setText(split_to_time[0]);
                to_time_2.setText(split_to_time[1]);

                takeoffId1.setText(String.valueOf(place_frm));
                landingId1.setText(String.valueOf(place_to));
                place_frm_name_tv.setText(String.valueOf(place_frm_name));
                inboundPlaceFrom.setText(String.valueOf(place_frm_name));
                place_to_name_tv.setText(String.valueOf(landing_place_name));
                inboundPlaceTo.setText(String.valueOf(landing_place_name));

                landingId2.setText(String.valueOf(place_frm));
                takeoffId2.setText(String.valueOf(place_to));
                place_to_name_tv2.setText(String.valueOf(place_frm_name));
                outboundPlaceTo.setText(String.valueOf(place_frm_name));
                place_frm_name_tv1.setText(String.valueOf(landing_place_name));
                outboundPlaceFrom.setText(String.valueOf(landing_place_name));


                returnPassengerNumber.setText(String.valueOf(Integer.parseInt(adult)+Integer.parseInt(child)));
                flightpassengerNumber.setText(String.valueOf((Integer.parseInt(adult)+Integer.parseInt(child))));
                total_FlightPrice_summary.setText(totalFlightPrice);

//        from_travel_type_tv.setText(from_travel_type);
//        from_travel_class_tv.setText(from_travel_class);
                to_airline_tv.setText(to_airline);
//        to_travel_class_tv.setText(to_travel_class);
//        to_travel_type_tv.setText(to_travel_type);

                continueToConfirm.setOnClickListener(this);

                Availability inboundElement = inboundList.get(Integer.parseInt(inboundPosition));
                Availability outboundElement = outboundList.get(Integer.parseInt(outboundPosition));

                inboundBaggage.setText(inboundElement.getFreeBaggage());
                String splitInboundDate[] = outboundElement.getFlightDate().split("-");
                inboundDate.setText(splitInboundDate[0] +" "+splitInboundDate[1]);
//        from_fare_type.setText(inboundElement.getTy);
                String splitOutboundDate[] = inboundElement.getFlightDate().split("-");
                outboundDate.setText(splitOutboundDate[0] +" "+splitOutboundDate[1]);

                String splitInboundDuration[] = inboundElement.getDuration().split(" ");
                String splitInboundHr[] =splitInboundDuration[0].split("h");
                String splitInboundMin[] = splitInboundDuration[1].split("m");

                if (splitInboundHr[0].equals("0")){
                    inboundHr="00";
                }else {
                    inboundHr=splitInboundHr[0];
                }
                if (splitInboundMin[0].equals("0")){
                    inboundMin="00";
                }
                else {
                    inboundMin=splitInboundMin[0];
                }
                inboundDuration.setText(inboundHr+":"+inboundMin);
//                inboundDuration.setText(splitInboundHr[0]+":"+splitInboundMin[0]);

                from_travel_class_tv.setText(inboundElement.getFlightClassCode());
                from_travel_type_tv.setText(inboundElement.getAircraftType());
                from_airline_tvm.setText(inboundElement.getAirline());
                outboundBaggage.setText(outboundElement.getFreeBaggage());

                String splitOutboundDuration[] = outboundElement.getDuration().split(" ");
                String splitOutboundHr[] =splitOutboundDuration[0].split("h");
                String splitOutboundMin[] = splitOutboundDuration[1].split("m");

                if (splitOutboundHr[0].equals("0")){
                    outboundHr="00";
                }else {
                    outboundHr=splitOutboundHr[0];
                }
                if (splitOutboundMin[0].equals("0")){
                    outboundMin="00";
                }
                else {
                    outboundMin=splitOutboundMin[0];
                }
                outboundDuration.setText(outboundHr+":"+outboundMin);

//                outboundDuration.setText(splitOutboundHr[0]+":"+splitOutboundMin[0]);

//                outboundDuration.setText(outboundElement.getDuration());
                to_travel_class_tv.setText(outboundElement.getFlightClassCode());
                to_travel_type_tv.setText(outboundElement.getAircraftType());

                Picasso.with(getContext())
                        .load(inboundElement.getAirlineLogo())
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.noimg)
                        .into(inboundImage);

               /* inboundImage.setColorFilter(Color.BLACK, PorterDuff.Mode.OVERLAY);
                outboundImage.setColorFilter(Color.BLACK, PorterDuff.Mode.OVERLAY);

*/
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                inboundImage.setColorFilter(filter);

                Picasso.with(getContext())
                        .load(outboundElement.getAirlineLogo())
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.noimg)
                        .into(outboundImage);
                outboundImage.setColorFilter(filter);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            goneSummary.setVisibility(View.GONE);
            try {
                takeoffId1.setText(place_frm);
                landingId1.setText(place_to);



                String split_from_time[] = from_time.split("-");
                from_time_1.setText(split_from_time[0]);
                from_time_2.setText(split_from_time[1]);

                String split_departure_date_details[] = flight_departure_date_details.split(",");
                String split_return_date_details[] = return_departure_date_details.split(",");

                inboundWeekday.setText(split_departure_date_details[0]+",");

//                inboundDate.setText(split_departure_date_details[1]);

                outboundWeekday.setText(split_return_date_details[0]+",");
//                outboundDate.setText(split_return_date_details[1]);

                String split_to_time[] = to_time.split("-");
                to_time_1.setText(split_to_time[0]);
//                to_time_2.setText(split_to_time[1]);

                takeoffId1.setText(String.valueOf(place_frm));
                landingId1.setText(String.valueOf(place_to));
                place_frm_name_tv.setText(String.valueOf(place_frm_name));
                inboundPlaceFrom.setText(String.valueOf(place_frm_name));
                place_to_name_tv.setText(String.valueOf(landing_place_name));
                inboundPlaceTo.setText(String.valueOf(landing_place_name));

//                landingId2.setText(String.valueOf(place_frm));
//                takeoffId2.setText(String.valueOf(place_to));
//                place_to_name_tv2.setText(String.valueOf(place_frm_name));
//                outboundPlaceTo.setText(String.valueOf(place_frm_name));
//                place_frm_name_tv1.setText(String.valueOf(landing_place_name));
//                outboundPlaceFrom.setText(String.valueOf(landing_place_name));


//                returnPassengerNumber.setText(String.valueOf(Integer.parseInt(adult)+Integer.parseInt(child)));
                flightpassengerNumber.setText(String.valueOf((Integer.parseInt(adult)+Integer.parseInt(child))));
                total_FlightPrice_summary.setText(totalFlightPrice);

//        from_travel_type_tv.setText(from_travel_type);
//        from_travel_class_tv.setText(from_travel_class);
//                to_airline_tv.setText(to_airline);
//        to_travel_class_tv.setText(to_travel_class);
//        to_travel_type_tv.setText(to_travel_type);

                continueToConfirm.setOnClickListener(this);



                Availability inboundElement = inboundList.get(Integer.parseInt(inboundPosition));
//                Availability outboundElement = outboundList.get(Integer.parseInt(outboundPosition));
                String splitInboundDate[] = inboundElement.getFlightDate().split("-");
                inboundDate.setText(splitInboundDate[0] +" "+splitInboundDate[1]);
                inboundBaggage.setText(inboundElement.getFreeBaggage());
//        from_fare_type.setText(inboundElement.getTy);
                String splitInboundDuration[] = inboundElement.getDuration().split(" ");
                String splitInboundHr[] =splitInboundDuration[0].split("h");
                String splitInboundMin[] = splitInboundDuration[1].split("m");

                if (splitInboundHr[0].equals("0")){
                    inboundHr="00";
                }else {
                    inboundHr=splitInboundHr[0];
                }
                if (splitInboundMin[0].equals("0")){
                    inboundMin="00";
                }else {
                    inboundMin=splitInboundMin[0];
                }
                inboundDuration.setText(inboundHr+":"+inboundMin);
//                inboundDuration.setText(splitInboundHr[0]+":"+splitInboundMin[0]);

                from_travel_class_tv.setText(inboundElement.getFlightClassCode());
                from_travel_type_tv.setText(inboundElement.getAircraftType());
                from_airline_tvm.setText(inboundElement.getAirline());
/*
                outboundBaggage.setText(outboundElement.getFreeBaggage());
                outboundDuration.setText(outboundElement.getDuration());
                to_travel_class_tv.setText(outboundElement.getFlightClassCode());
                to_travel_type_tv.setText(outboundElement.getAircraftType());
*/

                Picasso.with(getContext())
                        .load(inboundElement.getAirlineLogo())
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.noimg)
                        .into(inboundImage);
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                inboundImage.setColorFilter(filter);

/*                Picasso.with(getContext())
                        .load(outboundElement.getAirlineLogo())
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.noimg)
                        .into(outboundImage);*/

            }catch (Exception e){
                e.printStackTrace();
            }
        }



        bookingDetails();
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.continueToConfirm:
                fragment =new FlightCheckOutFragment();
                Bundle args = new Bundle();
                args.putString("flightMode",String.valueOf(bundelflightMode));
                args.putString("searchId",String.valueOf(searchId));
                args.putString("flag",String.valueOf(value));
                args.putString("nepFlightToken",String.valueOf(nepFlightToken));
                args.putString("inboundWeekday",String.valueOf(inboundWeekday.getText()));
                args.putString("outboundWeekday",String.valueOf(outboundWeekday.getText()));
                args.putString("place_frm",String.valueOf(place_frm));
                args.putString("place_to",String.valueOf(place_to));
                args.putString("inboundPosition",String.valueOf(inboundPosition));
                args.putString("outboundPosition",String.valueOf(outboundPosition));
                args.putParcelableArrayList("inboundList", (ArrayList<? extends Parcelable>) inboundList);
                args.putParcelableArrayList("outboundList", (ArrayList<? extends Parcelable>) outboundList);
                args.putParcelableArrayList("saveReservationModelList", (ArrayList<? extends Parcelable>) saveReservationModelList);
                fragment.setArguments(args);
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();
                break;
        }
    }

    void bookingDetails(){

        try {
            Availability inboundAvailability = inboundList.get(Integer.parseInt(inboundPosition));
            Availability outboundAvailability = outboundList.get(Integer.parseInt(outboundPosition));


            RequestParams params = new RequestParams();

            params.put("parentTask", "nepFlight");
            params.put("childTask", "saveReservations");
            params.put("userCode", myUserSessionManager.getSecurityCode());
            params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
            params.put("flightId",inboundAvailability.getFlightId() );
            params.put("returnFlightId",inboundAvailability.getFlightId() );
            params.put("flightMode",bundelflightMode );
            params.put("flightNo",inboundAvailability.getFlightNo() );
            params.put("returnFlightNo",inboundAvailability.getFlightNo() );
            params.put("searchId",searchId );
            params.put("noOfAdult",inboundAvailability.getAdult() );
            params.put("noOfChild",inboundAvailability.getChild() );
            params.put("arrivalTimeIn",inboundAvailability.getArrivalTime() );
            params.put("departureTimeIn",inboundAvailability.getDepartureTime() );
            params.put("flightDateValIn",inboundAvailability.getFlightDate() );
            params.put("flightDateValOut",outboundAvailability.getFlightDate() );
            params.put("arrivalTimeOut",outboundAvailability.getArrivalTime() );
            params.put("departureTimeOut",outboundAvailability.getDepartureTime() );
            params.put("adultFareOut",outboundAvailability.getAdultFare() );
            params.put("adultFareIn",inboundAvailability.getAdultFare() );
            params.put("childFareOut",outboundAvailability.getChildFare() );
            params.put("childFareIn",inboundAvailability.getChildFare() );
            params.put("fuelSurchargeIn",inboundAvailability.getFuelSurcharge() );
            params.put("fuelSurchargeOut",outboundAvailability.getFuelSurcharge() );
            params.put("childCommissionOut",outboundAvailability.getChildCommission() );
            params.put("childCommissionIn",inboundAvailability.getChildCommission() );
            params.put("adultCommissionOut",outboundAvailability.getAgencyCommission() );
            params.put("adultCommissionIn",inboundAvailability.getAgencyCommission() );
            params.put("taxOut",outboundAvailability.getTax() );
            params.put("taxIn",inboundAvailability.getTax() );
            params.put("countryDetails","np" );
            params.put("accessToken",nepFlightToken );
            params.put("flightClassCodeOut",outboundAvailability.getFlightClassCode() );
            params.put("departureIn",inboundAvailability.getDeparture() );
            params.put("flightClassCodeIn",inboundAvailability.getFlightClassCode() );
            params.put("arrivalIn",inboundAvailability.getArrival() );
            params.put("departureOut",outboundAvailability.getDeparture() );
            params.put("arrivalOut",outboundAvailability.getArrival() );
            params.put("flightNoOut",outboundAvailability.getFlightNo() );
            params.put("airlinesOut",outboundAvailability.getAirline() );
            params.put("imageOut",outboundAvailability.getAirlineLogo() );
            params.put("imageIn",inboundAvailability.getAirlineLogo() );
            params.put("aircraftTypeOut",outboundAvailability.getAircraftType() );
            params.put("flightNoIn",inboundAvailability.getFlightNo() );
            params.put("airlinesIn",inboundAvailability.getAirline() );
            params.put("aircraftTypeIn",inboundAvailability.getAircraftType() );

            params.put("flightCurrency",inboundAvailability.getCurrency() );

            // Make Http call
            PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
            handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        Log.i("authenticateUser",""+response);
                        handleObtainPaymentDetails(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
//            Availability inboundAvailability = inboundList.get(Integer.parseInt(inboundPosition));
            Availability outboundAvailability = outboundList.get(Integer.parseInt(outboundPosition));
            RequestParams params = new RequestParams();

            params.put("parentTask", "nepFlight");
            params.put("childTask", "saveReservations");
            params.put("userCode", myUserSessionManager.getSecurityCode());
            params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
            params.put("flightId",outboundAvailability.getFlightId() );
            params.put("returnFlightId",outboundAvailability.getFlightId() );
            params.put("flightMode",bundelflightMode );
            params.put("flightNo",outboundAvailability.getFlightNo() );
            params.put("returnFlightNo",outboundAvailability.getFlightNo() );
            params.put("searchId",searchId );
            params.put("noOfAdult",outboundAvailability.getAdult() );
            params.put("noOfChild",outboundAvailability.getChild() );
            params.put("arrivalTimeIn","" );
            params.put("departureTimeIn","" );
            params.put("flightDateValIn","" );
            params.put("flightDateValOut",outboundAvailability.getFlightDate() );
            params.put("arrivalTimeOut",outboundAvailability.getArrivalTime() );
            params.put("departureTimeOut",outboundAvailability.getDepartureTime() );
            params.put("adultFareOut",outboundAvailability.getAdultFare() );
            params.put("adultFareIn","" );
            params.put("childFareOut",outboundAvailability.getChildFare() );
            params.put("childFareIn","" );
            params.put("fuelSurchargeIn","" );
            params.put("fuelSurchargeOut",outboundAvailability.getFuelSurcharge() );
            params.put("childCommissionOut",outboundAvailability.getChildCommission() );
            params.put("childCommissionIn","" );
            params.put("adultCommissionOut",outboundAvailability.getAgencyCommission() );
            params.put("adultCommissionIn","" );
            params.put("taxOut",outboundAvailability.getTax() );
            params.put("taxIn","" );
            params.put("countryDetails","np" );
            params.put("accessToken",nepFlightToken );
            params.put("flightClassCodeOut",outboundAvailability.getFlightClassCode() );
            params.put("departureIn","" );
            params.put("flightClassCodeIn","" );
            params.put("arrivalIn","" );
            params.put("departureOut",outboundAvailability.getDeparture() );
            params.put("arrivalOut",outboundAvailability.getArrival() );
            params.put("flightNoOut",outboundAvailability.getFlightNo() );
            params.put("airlinesOut",outboundAvailability.getAirline() );
            params.put("imageOut",outboundAvailability.getAirlineLogo() );
            params.put("imageIn","" );
            params.put("aircraftTypeOut",outboundAvailability.getAircraftType() );
            params.put("flightNoIn","" );
            params.put("airlinesIn","" );
            params.put("aircraftTypeIn","" );

            params.put("flightCurrency",outboundAvailability.getCurrency() );

            // Make Http call
            PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
            handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        Log.i("authenticateUser",""+response);
                        handleObtainPaymentDetails(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                }
            });
        }








    }
    public void handleObtainPaymentDetails(JSONObject response) throws JSONException{
        try{
            Log.e("asdasd",response.toString());
            adultFareIn = response.getString("adultFareIn");
            flightClassCodeOut = response.getString("flightClassCodeOut");
            totalChildFareOut = response.getString("totalChildFareOut");
            departureIn = response.getString("departureIn");
            flightNoIn = response.getString("flightNoIn");
            flightNoOut = response.getString("flightNoOut");
            durationDeparture = response.getString("durationDeparture");
            totalAdultFareIn = response.getString("totalAdultFareIn");
            totalOutSum = response.getString("totalOutSum");
            totalChildFare = response.getString("totalChildFare");
            airlinesIn = response.getString("airlinesIn");
            taxIn = response.getString("taxIn");
            airlinesOut = response.getString("airlinesOut");
            totalAdultFare = response.getString("totalAdultFare");
            aircraftTypeIn = response.getString("aircraftTypeIn");
            totalInSum = response.getString("totalInSum");
            taxOut = response.getString("taxOut");
            departureTimeOut = response.getString("departureTimeOut");
            imageOut = response.getString("imageOut");
            noOfChild = response.getString("noOfChild");
            individualTax = response.getString("individualTax");
            arrivalTimeIn = response.getString("arrivalTimeIn");
            totalTaxOut = response.getString("totalTaxOut");
            adultFareOut = response.getString("adultFareOut");
            childFareIn = response.getString("childFareIn");
            totalSum = response.getString("totalSum");
            noOfAdult = response.getString("noOfAdult");
            arrivalTimeOut = response.getString("arrivalTimeOut");
            flightMode = response.getString("flightMode");
            fuelSurchargeOut = response.getString("fuelSurchargeOut");
            aircraftTypeOut = response.getString("aircraftTypeOut");
            departureTimeIn = response.getString("departureTimeIn");
            departureOut = response.getString("departureOut");
            totalChildFareIn = response.getString("totalChildFareIn");
            durationArrival = response.getString("durationArrival");
            imageIn = response.getString("imageIn");
            individualAdultFare = response.getString("individualAdultFare");
            flightCurrency = response.getString("flightCurrency");
            arrivalOut = response.getString("arrivalOut");
            flightClassCodeIn = response.getString("flightClassCodeIn");
            fuelSurchargeIn = response.getString("fuelSurchargeIn");
            arrivalIn = response.getString("arrivalIn");
            childFareOut = response.getString("childFareOut");
            totalFuelCharge = response.getString("totalFuelCharge");
            nationality = response.getString("nationality");
            totalAdultFareOut = response.getString("totalAdultFareOut");
            totalFuelChargeIn = response.getString("totalFuelChargeIn");
            totalFuelChargeOut = response.getString("totalFuelChargeOut");
            totalTaxIn = response.getString("totalTaxIn");
            individualFuelCharge = response.getString("individualFuelCharge");
            individualChildFare = response.getString("individualChildFare");
            totalTax = response.getString("totalTax");




            try {
                JSONObject serverResponse0 = response.getJSONObject("serverResponse");


                departureReservationStatus = serverResponse0.getString("ReservationStatus");
                departureTTLTime = serverResponse0.getString("TTLTime");
                departureFlightId = serverResponse0.getString("FlightId");
                departureAirlineID = serverResponse0.getString("AirlineID");
                departurePNRNO = serverResponse0.getString("PNRNO");
                departureTTLDate = serverResponse0.getString("TTLDate");

                arraivalReservationStatus = serverResponse0.getString("ReservationStatus");
                arraivalTTLTime = serverResponse0.getString("TTLTime");
                arraivalFlightId = serverResponse0.getString("FlightId");
                arraivalAirlineID = serverResponse0.getString("AirlineID");
                arraivalPNRNO = serverResponse0.getString("PNRNO");
                arraivalTTLDate = serverResponse0.getString("TTLDate");

            }catch (Exception e){
                JSONArray serverResponse = response.getJSONArray("serverResponse");

                JSONObject serverResponse0 = serverResponse.getJSONObject(0);
                for (int j=0;j<serverResponse0.length();j++){
                    departureReservationStatus = serverResponse0.getString("ReservationStatus");
                    departureTTLTime = serverResponse0.getString("TTLTime");
                    departureFlightId = serverResponse0.getString("FlightId");
                    departureAirlineID = serverResponse0.getString("AirlineID");
                    departurePNRNO = serverResponse0.getString("PNRNO");
                    departureTTLDate = serverResponse0.getString("TTLDate");
                }
                JSONObject serverResponse1 = serverResponse.getJSONObject(1);
                for (int j=0;j<serverResponse1.length();j++){
                    arraivalReservationStatus = serverResponse0.getString("ReservationStatus");
                    arraivalTTLTime = serverResponse0.getString("TTLTime");
                    arraivalFlightId = serverResponse0.getString("FlightId");
                    arraivalAirlineID = serverResponse0.getString("AirlineID");
                    arraivalPNRNO = serverResponse0.getString("PNRNO");
                    arraivalTTLDate = serverResponse0.getString("TTLDate");
                }

            }
            JSONObject arilinesPriceList =response.getJSONObject("arilinesPriceList");
            for (int i =0;i<arilinesPriceList.length();i++){
                rindividualAdultCommission = arilinesPriceList.getString("individualAdultCommission");
                rtaxIn = arilinesPriceList.getString("taxIn");
                rnoOfAdult = arilinesPriceList.getString("noOfAdult");
                radultFareIn = arilinesPriceList.getString("adultFareIn");
                rflightCurrency = arilinesPriceList.getString("flightCurrency");
                rtotalFlightCost = arilinesPriceList.getString("totalFlightCost");
                rtaxOut = arilinesPriceList.getString("taxOut");
                rtotalPboCommission = arilinesPriceList.getString("totalPboCommission");
                rchildFareOut = arilinesPriceList.getString("childFareOut");
                rfuelSurchargeOut = arilinesPriceList.getString("fuelSurchargeOut");
                rfuelSurchargeIn = arilinesPriceList.getString("fuelSurchargeIn");
                rnoOfChild = arilinesPriceList.getString("noOfChild");
                rnationality = arilinesPriceList.getString("nationality");
                rtrip_type = arilinesPriceList.getString("trip_type");
                rindividualChildCommission = arilinesPriceList.getString("individualChildCommission");
                radultFareOut = arilinesPriceList.getString("adultFareOut");
                rchildFareIn = arilinesPriceList.getString("childFareIn");
            }
            saveReservationModelList.add(new SaveReservationModel(adultFareIn,flightClassCodeOut,totalChildFareOut,departureIn,
                    flightNoIn,flightNoOut,durationDeparture,totalAdultFareIn,totalOutSum,totalChildFare,airlinesIn,taxIn,airlinesOut,
                    totalAdultFare,aircraftTypeIn,totalInSum,taxOut,departureTimeOut,imageOut,noOfChild,individualTax,arrivalTimeIn,
                    totalTaxOut,adultFareOut,childFareIn,totalSum,noOfAdult,arrivalTimeOut,flightMode,fuelSurchargeOut,aircraftTypeOut,
                    departureTimeIn,departureOut,totalChildFareIn,durationArrival,imageIn,individualAdultFare,flightCurrency,arrivalOut,
                    flightClassCodeIn,fuelSurchargeIn,arrivalIn,childFareOut,totalFuelCharge,nationality,totalAdultFareOut,totalFuelChargeIn,
                    totalFuelChargeOut,totalTaxIn,individualFuelCharge,individualChildFare,totalTax,departureReservationStatus,departureTTLTime,
                    departureFlightId,departureAirlineID,departurePNRNO,departureTTLDate,arraivalReservationStatus,arraivalTTLTime,arraivalFlightId,
                    arraivalAirlineID,arraivalPNRNO,arraivalPNRNO,rindividualAdultCommission,rtaxIn,rnoOfAdult,radultFareIn,rflightCurrency,
                    rtotalFlightCost,rtaxOut,rtotalPboCommission,rchildFareOut,rfuelSurchargeOut,rfuelSurchargeIn,rnoOfChild,rnationality,
                    rtrip_type,rindividualChildCommission,radultFareOut,rchildFareIn));
        }catch (Exception e){


            Log.e("asdasd",response.toString());
            adultFareIn = "";
            flightClassCodeOut = response.getString("flightClassCodeOut");
            totalChildFareOut = response.getString("totalChildFareOut");
            departureIn = "";
            flightNoIn = "";
            flightNoOut = response.getString("flightNoOut");
            durationDeparture = response.getString("durationDeparture");
            totalAdultFareIn = "";
            totalOutSum = response.getString("totalOutSum");
            totalChildFare = response.getString("totalChildFare");
            airlinesIn = "";
            taxIn = "";
            airlinesOut = response.getString("airlinesOut");
            totalAdultFare = response.getString("totalAdultFare");
            aircraftTypeIn = "";
            totalInSum = "";
            taxOut = response.getString("taxOut");
            departureTimeOut = response.getString("departureTimeOut");
            imageOut = response.getString("imageOut");
            noOfChild = response.getString("noOfChild");
            individualTax = response.getString("individualTax");
            arrivalTimeIn = "";
            totalTaxOut = response.getString("totalTaxOut");
            adultFareOut = response.getString("adultFareOut");
            childFareIn = "";
            totalSum = response.getString("totalSum");
            noOfAdult = response.getString("noOfAdult");
            arrivalTimeOut = response.getString("arrivalTimeOut");
            flightMode = response.getString("flightMode");
            fuelSurchargeOut = response.getString("fuelSurchargeOut");
            aircraftTypeOut = response.getString("aircraftTypeOut");
            departureTimeIn = "";
            departureOut = response.getString("departureOut");
            totalChildFareIn = "";
            durationArrival = ""; // no value from server
            imageIn = "";
            individualAdultFare = response.getString("individualAdultFare");
            flightCurrency = response.getString("flightCurrency");
            arrivalOut = response.getString("arrivalOut");
            flightClassCodeIn = "";
            fuelSurchargeIn = "";
            arrivalIn = "";
            childFareOut = response.getString("childFareOut");
            totalFuelCharge = response.getString("totalFuelCharge");
            nationality = response.getString("nationality");
            totalAdultFareOut = response.getString("totalAdultFareOut");
            totalFuelChargeIn = "";
            totalFuelChargeOut = response.getString("totalFuelChargeOut");
            totalTaxIn = "";
            individualFuelCharge = response.getString("individualFuelCharge");
            individualChildFare = response.getString("individualChildFare");
            totalTax = response.getString("totalTax");




            try {
                JSONObject serverResponse0 = response.getJSONObject("serverResponse");


                departureReservationStatus = serverResponse0.getString("ReservationStatus");
                departureTTLTime = serverResponse0.getString("TTLTime");
                departureFlightId = serverResponse0.getString("FlightId");
                departureAirlineID = serverResponse0.getString("AirlineID");
                departurePNRNO = serverResponse0.getString("PNRNO");
                departureTTLDate = serverResponse0.getString("TTLDate");

                arraivalReservationStatus = serverResponse0.getString("ReservationStatus");
                arraivalTTLTime = serverResponse0.getString("TTLTime");
                arraivalFlightId = serverResponse0.getString("FlightId");
                arraivalAirlineID = serverResponse0.getString("AirlineID");
                arraivalPNRNO = serverResponse0.getString("PNRNO");
                arraivalTTLDate = serverResponse0.getString("TTLDate");

            }catch (Exception e1){
                JSONArray serverResponse = response.getJSONArray("serverResponse");

                JSONObject serverResponse0 = serverResponse.getJSONObject(0);
                for (int j=0;j<serverResponse0.length();j++){
                    departureReservationStatus = serverResponse0.getString("ReservationStatus");
                    departureTTLTime = serverResponse0.getString("TTLTime");
                    departureFlightId = serverResponse0.getString("FlightId");
                    departureAirlineID = serverResponse0.getString("AirlineID");
                    departurePNRNO = serverResponse0.getString("PNRNO");
                    departureTTLDate = serverResponse0.getString("TTLDate");
                }
                JSONObject serverResponse1 = serverResponse.getJSONObject(1);
                for (int j=0;j<serverResponse1.length();j++){
                    arraivalReservationStatus = serverResponse0.getString("ReservationStatus");
                    arraivalTTLTime = serverResponse0.getString("TTLTime");
                    arraivalFlightId = serverResponse0.getString("FlightId");
                    arraivalAirlineID = serverResponse0.getString("AirlineID");
                    arraivalPNRNO = serverResponse0.getString("PNRNO");
                    arraivalTTLDate = serverResponse0.getString("TTLDate");
                }

            }
            JSONObject arilinesPriceList =response.getJSONObject("arilinesPriceList");
            for (int i =0;i<arilinesPriceList.length();i++){
                rindividualAdultCommission = arilinesPriceList.getString("individualAdultCommission");
                rtaxIn = "";
                rnoOfAdult = arilinesPriceList.getString("noOfAdult");
                radultFareIn = "";
                rflightCurrency = arilinesPriceList.getString("flightCurrency");
                rtotalFlightCost = arilinesPriceList.getString("totalFlightCost");
                rtaxOut = arilinesPriceList.getString("taxOut");
                rtotalPboCommission = arilinesPriceList.getString("totalPboCommission");
                rchildFareOut = arilinesPriceList.getString("childFareOut");
                rfuelSurchargeOut = arilinesPriceList.getString("fuelSurchargeOut");
                rfuelSurchargeIn = "";
                rnoOfChild = arilinesPriceList.getString("noOfChild");
                rnationality = arilinesPriceList.getString("nationality");
                rtrip_type = arilinesPriceList.getString("trip_type");
                rindividualChildCommission = arilinesPriceList.getString("individualChildCommission");
                radultFareOut = arilinesPriceList.getString("adultFareOut");
                rchildFareIn = "";
            }
            saveReservationModelList.add(new SaveReservationModel(adultFareIn,flightClassCodeOut,totalChildFareOut,departureIn,
                    flightNoIn,flightNoOut,durationDeparture,totalAdultFareIn,totalOutSum,totalChildFare,airlinesIn,taxIn,airlinesOut,
                    totalAdultFare,aircraftTypeIn,totalInSum,taxOut,departureTimeOut,imageOut,noOfChild,individualTax,arrivalTimeIn,
                    totalTaxOut,adultFareOut,childFareIn,totalSum,noOfAdult,arrivalTimeOut,flightMode,fuelSurchargeOut,aircraftTypeOut,
                    departureTimeIn,departureOut,totalChildFareIn,durationArrival,imageIn,individualAdultFare,flightCurrency,arrivalOut,
                    flightClassCodeIn,fuelSurchargeIn,arrivalIn,childFareOut,totalFuelCharge,nationality,totalAdultFareOut,totalFuelChargeIn,
                    totalFuelChargeOut,totalTaxIn,individualFuelCharge,individualChildFare,totalTax,departureReservationStatus,departureTTLTime,
                    departureFlightId,departureAirlineID,departurePNRNO,departureTTLDate,arraivalReservationStatus,arraivalTTLTime,arraivalFlightId,
                    arraivalAirlineID,arraivalPNRNO,arraivalPNRNO,rindividualAdultCommission,rtaxIn,rnoOfAdult,radultFareIn,rflightCurrency,
                    rtotalFlightCost,rtaxOut,rtotalPboCommission,rchildFareOut,rfuelSurchargeOut,rfuelSurchargeIn,rnoOfChild,rnationality,
                    rtrip_type,rindividualChildCommission,radultFareOut,rchildFareIn));

        }

    }
}
