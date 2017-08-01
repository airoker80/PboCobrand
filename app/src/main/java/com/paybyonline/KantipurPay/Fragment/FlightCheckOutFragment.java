package com.paybyonline.KantipurPay.Fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Model.Availability;
import com.paybyonline.KantipurPay.Adapter.Model.NationalityModel;
import com.paybyonline.KantipurPay.Adapter.Model.PassengerModule;
import com.paybyonline.KantipurPay.Adapter.Model.PlaceFlightModel;
import com.paybyonline.KantipurPay.Adapter.Model.SaveReservationModel;
import com.paybyonline.KantipurPay.Adapter.PassengerListAdapter;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Sameer on 5/22/2017.
 */

class FlightCheckOutFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    String contact_name_main="",contact_email="",contact_address="",contact_number="";

    LinearLayout paynowLL;
    private MyUserSessionManager myUserSessionManager;
    CoordinatorLayout coordinatorLayout;
    TextView takeoffid1,landingid1,takeoffid2,landingid2
    ,checkoutInboundDeparture,checkoutInboundArraival,inboundPassengerNumber,checkoutInboundDuration
            ,checkoutOutboundDeparture,checkoutOutboundArraival,outboundPassengerNumber,checkoutOutboundDuration
            ,checkoutInboundWeekday,checkoutOutboundWeekday,checkoutInboundDate,checkoutOutboundDate;
    List<PassengerModule> passengerModuleList = new ArrayList<PassengerModule>();
    LinearLayout topHeading1,firstll1;
    RecyclerView passengerRV;
    Typeface mediumBold;
    String value,inboundPosition,outboundPosition,place_frm,place_to,inboundWeekday,outboundWeekday;

    String bundleFlightMode;
    RelativeLayout ps_details_form;
    TextView passenger_details_tv;
    ImageView psd_flight_arrow_down;
    String inboundHr,inboundMin,searchId;
    String outboundHr,outboundMin,nepFlightToken;

    List<Availability> inboundList = new ArrayList<Availability>();
    List<Availability> outboundList = new ArrayList<Availability>();
    Availability inboundAvailability;
    Availability outboundAvailability;
    List<SaveReservationModel> saveReservationModelList = new ArrayList<SaveReservationModel>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myUserSessionManager = new MyUserSessionManager(getActivity());
        View view = inflater.inflate(R.layout.fragment_flight_checkout, container, false);
        passengerRV=(RecyclerView)view.findViewById(R.id.passengerRV);
        ((DashBoardActivity) getActivity()).setTitle("Flight Checkout");
        value = getArguments().getString("flag");
        inboundWeekday = getArguments().getString("inboundWeekday");
        outboundWeekday = getArguments().getString("outboundWeekday");
        place_frm = getArguments().getString("place_frm");
        place_to = getArguments().getString("place_to");
        searchId = getArguments().getString("searchId");
        nepFlightToken = getArguments().getString("nepFlightToken");
        inboundPosition = getArguments().getString("inboundPosition");
        outboundPosition = getArguments().getString("outboundPosition");
        bundleFlightMode = getArguments().getString("flightMode");

        Log.e("asdada---------------","----"+inboundPosition);
        inboundList = getArguments().getParcelableArrayList("inboundList");
        outboundList = getArguments().getParcelableArrayList("outboundList");
        saveReservationModelList = getArguments().getParcelableArrayList("saveReservationModelList");

//        Log.d("adafasafefafaeaasfas","=========="+saveReservationModelList.get(0).getArraivalReservationStatus().toString());
        topHeading1=(LinearLayout)view.findViewById(R.id.topHeading1);
        firstll1=(LinearLayout)view.findViewById(R.id.firstll1);

        passenger_details_tv=(TextView)view.findViewById(R.id.passenger_details_tv);
        ps_details_form=(RelativeLayout) view.findViewById(R.id.ps_details_form);
        psd_flight_arrow_down=(ImageView) view.findViewById(R.id.psd_flight_arrow_down);


        ps_details_form.setOnClickListener(this);
        psd_flight_arrow_down.setOnClickListener(this);
        passenger_details_tv.setOnClickListener(this);

        takeoffid1=(TextView)view.findViewById(R.id.takeoffid1);
        landingid1=(TextView)view.findViewById(R.id.landingid1);
        takeoffid2=(TextView)view.findViewById(R.id.takeoffid2);
        landingid2=(TextView)view.findViewById(R.id.landingid2);
        checkoutInboundDate=(TextView)view.findViewById(R.id.checkoutInboundDate);
        checkoutOutboundDate=(TextView)view.findViewById(R.id.checkoutOutboundDate);
        checkoutInboundWeekday=(TextView)view.findViewById(R.id.checkoutInboundWeekday);
        checkoutOutboundWeekday=(TextView)view.findViewById(R.id.checkoutOutboundWeekday);
        checkoutInboundDeparture=(TextView)view.findViewById(R.id.checkoutInboundDeparture);
        checkoutInboundArraival=(TextView)view.findViewById(R.id.checkoutInboundArraival);
        inboundPassengerNumber=(TextView)view.findViewById(R.id.inboundPassengerNumber);
        checkoutInboundDuration=(TextView)view.findViewById(R.id.checkoutInboundDuration);
        checkoutOutboundDeparture=(TextView)view.findViewById(R.id.checkoutOutboundDeparture);
        checkoutOutboundArraival=(TextView)view.findViewById(R.id.checkoutOutboundArraival);
        outboundPassengerNumber=(TextView)view.findViewById(R.id.outboundPassengerNumber);
        checkoutOutboundDuration=(TextView)view.findViewById(R.id.checkoutOutboundDuration);

        paynowLL=(LinearLayout)view.findViewById(R.id.paynowLL);
        paynowLL.setOnClickListener(this);
        mediumBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Bold.ttf");

        takeoffid1.setTypeface(mediumBold);
        landingid1.setTypeface(mediumBold);
        takeoffid2.setTypeface(mediumBold);
        landingid2.setTypeface(mediumBold);

        takeoffid1.setText(place_frm);
        landingid1.setText(place_to);
        takeoffid2.setText(place_to);
        landingid2.setText(place_frm);


        if (value=="false"){
            topHeading1.setVisibility(View.VISIBLE);
            firstll1.setVisibility(View.VISIBLE);
            checkoutOutboundWeekday.setText(outboundWeekday);
            checkoutInboundWeekday.setText(inboundWeekday);

            inboundAvailability = inboundList.get(Integer.parseInt(inboundPosition));
             outboundAvailability = outboundList.get(Integer.parseInt(inboundPosition));


            String splitInboundDate[] =inboundAvailability.getFlightDate().split("-") ;
            String splitOutboundDate[] =outboundAvailability.getFlightDate().split("-") ;
            checkoutOutboundDate.setText(splitOutboundDate[0]+" " +splitOutboundDate[1]);
            checkoutInboundDate.setText(splitInboundDate[0] +" "+splitInboundDate[1]);
            checkoutInboundDeparture.setText(inboundAvailability.getDeparture());
            checkoutInboundArraival.setText(inboundAvailability.getArrival());
            inboundPassengerNumber.setText(String.valueOf(Integer.parseInt(inboundAvailability.getChild()) + Integer.parseInt(inboundAvailability.getAdult())));


            String splitInboundDuration[] = inboundAvailability.getDuration().split(" ");
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
            checkoutInboundDuration.setText(inboundHr+":"+inboundMin);

//            checkoutInboundDuration.setText(inboundAvailability.getDuration());
            checkoutOutboundDeparture.setText(outboundAvailability.getDeparture());
            checkoutOutboundArraival.setText(outboundAvailability.getArrival());
            outboundPassengerNumber.setText(String.valueOf(Integer.parseInt(outboundAvailability.getChild()) + Integer.parseInt(outboundAvailability.getAdult())));

            String splitOutboundDuration[] = outboundAvailability.getDuration().split(" ");
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
            checkoutOutboundDuration.setText(outboundHr+":"+outboundMin);

//            checkoutOutboundDuration.setText(outboundAvailability.getDuration());

        }else{
            topHeading1.setVisibility(View.GONE);
            firstll1.setVisibility(View.GONE);
//            Availability inboundAvailability = inboundList.get(Integer.parseInt(inboundPosition));
            Availability outboundAvailability = outboundList.get(Integer.parseInt(outboundPosition));


            String splitInboundDate[] =outboundAvailability.getFlightDate().split("-") ;
            checkoutInboundDate.setText(splitInboundDate[0] +" "+splitInboundDate[1]);
            checkoutInboundDeparture.setText(outboundAvailability.getDeparture());
            checkoutInboundArraival.setText(outboundAvailability.getArrival());
            inboundPassengerNumber.setText(String.valueOf(Integer.parseInt(outboundAvailability.getChild()) + Integer.parseInt(outboundAvailability.getAdult())));
            String splitInboundDuration[] = outboundAvailability.getDuration().split(" ");
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
            checkoutInboundDuration.setText(inboundHr+":"+inboundMin);
//            checkoutInboundDuration.setText(inboundAvailability.getDuration());
//            checkoutOutboundDeparture.setText(outboundAvailability.getDeparture());
//            checkoutOutboundArraival.setText(outboundAvailability.getArrival());
//            outboundPassengerNumber.setText(String.valueOf(Integer.parseInt(outboundAvailability.getChild()) + Integer.parseInt(outboundAvailability.getAdult())));
//            checkoutOutboundDuration.setText(outboundAvailability.getDuration());

        }
        SaveReservationModel saveReservationModel = saveReservationModelList.get(0);
        for (int i=0;i<Integer.parseInt(saveReservationModel.getNoOfAdult().toString());i++){
            passengerModuleList.add(new PassengerModule("Adult Passenger"+" "+(i+1),"Name","","A",""));
        }
        for (int i=0;i<Integer.parseInt(saveReservationModel.getNoOfChild().toString());i++){
            passengerModuleList.add(new PassengerModule("Child Passenger"+" "+(i+1),"Name","","C",""));
        }

        PassengerListAdapter passengerListAdapter = new PassengerListAdapter(getContext(),passengerModuleList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity());
        passengerRV.setLayoutManager(layoutManager);
        passengerRV.setAdapter(passengerListAdapter);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.paynowLL:
                try {
                    Availability inboundAvailability = inboundList.get(Integer.parseInt(inboundPosition));
                    Availability outboundAvailability = outboundList.get(Integer.parseInt(inboundPosition));
/*                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("name", "N");
                    jsonObject.accumulate("country", "C");
                    jsonObject.accumulate("twitter", "T");
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                    SaveReservationModel saveReservationModel = saveReservationModelList.get(0);

                    JSONObject arilinesPriceList = new JSONObject();
                    try {
                        arilinesPriceList.put("individualAdultCommission",saveReservationModel.getRindividualAdultCommission());
                        arilinesPriceList.put("taxIn",saveReservationModel.getRtaxIn());
                        arilinesPriceList.put("numberOfAdult",saveReservationModel.getRnoOfAdult());
                        arilinesPriceList.put("adultFareIn",saveReservationModel.getRadultFareIn());
                        arilinesPriceList.put("flightCurrency",saveReservationModel.getRflightCurrency());
                        arilinesPriceList.put("totalFlightCost",saveReservationModel.getRtotalFlightCost());
                        arilinesPriceList.put("taxOut",saveReservationModel.getRtaxOut());
                        arilinesPriceList.put("totalPboCommission",saveReservationModel.getRtotalPboCommission());
                        arilinesPriceList.put("childFareOut",saveReservationModel.getRchildFareOut());
                        arilinesPriceList.put("fuelSurchargeOut",saveReservationModel.getRfuelSurchargeOut());
                        arilinesPriceList.put("fuelSurchargeIn",saveReservationModel.getRfuelSurchargeIn());
                        arilinesPriceList.put("numberOfChild",saveReservationModel.getRnoOfChild());
                        arilinesPriceList.put("nationality",saveReservationModel.getRnationality());
                        arilinesPriceList.put("trip_type",saveReservationModel.getRtrip_type());
                        arilinesPriceList.put("individualChildCommission",saveReservationModel.getRindividualChildCommission());
                        arilinesPriceList.put("adultFareOut",saveReservationModel.getRadultFareOut());
                        arilinesPriceList.put("childFareIn",saveReservationModel.getRchildFareIn());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //JSONArray jsonArrayAdult = new JSONArray();

//                JSONArray jsonArrayChild = new JSONArray();


                    RequestParams jsonObjectPost = new RequestParams();

                    Log.d("nepFlightToken",nepFlightToken);
                    jsonObjectPost.put("parentTask", "nepFlight");
                    jsonObjectPost.put("childTask", "issuePassengerTicket");


                    jsonObjectPost.put("userCode", myUserSessionManager.getSecurityCode());
//                jsonObjectPost.put("userId", myUserSessionManager.getu());
                    jsonObjectPost.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
                    jsonObjectPost.put("accessToken",nepFlightToken );
                    jsonObjectPost.put("nationality",saveReservationModel.getNationality() );
                    jsonObjectPost.put("flightId",outboundAvailability.getFlightId() );
                    jsonObjectPost.put("returnFlightId",inboundAvailability.getFlightId() );
                    jsonObjectPost.put("numberOfAdult",inboundAvailability.getAdult() );
                    jsonObjectPost.put("numberOfChild",inboundAvailability.getChild() );
                    jsonObjectPost.put("airlinesImageOut",outboundAvailability.getAirlineLogo() );
                    jsonObjectPost.put("airlinesImageIn",inboundAvailability.getAirlineLogo() );
//                    jsonObjectPost.put("post", jsonObject);
                    jsonObjectPost.put("mainContactName", contact_name_main);
                    jsonObjectPost.put("mainEmail", contact_email);
                    jsonObjectPost.put("address", contact_address);
                    jsonObjectPost.put("mainContactNumber", contact_number);
                    jsonObjectPost.put("arrival", inboundAvailability.getArrival());
                    jsonObjectPost.put("departure", inboundAvailability.getDeparture());
                    jsonObjectPost.put("arilinesPriceList", arilinesPriceList);
                    jsonObjectPost.put("searchId",searchId );
                    jsonObjectPost.put("flightMode",bundleFlightMode );
                    jsonObjectPost.put("returnFlightNo",saveReservationModel.getFlightNoOut()) ;
                    jsonObjectPost.put("flightNo",outboundAvailability.getFlightNo() );
                    for (int i=0;i<passengerModuleList.size();i++){
                        PassengerModule passengerModule = passengerModuleList.get(i);
                        if(passengerModule.getPassengerType().equals("A")) {

                            jsonObjectPost.put("adult" + (i+1), passengerModule.getPassengerGender());
                            jsonObjectPost.put("firstNameAdult" + (i+1), passengerModule.getPassengerName());
                            jsonObjectPost.put("lastNameAdult"+ (i+1), passengerModule.getPassengerLastName());

                            //jsonArrayAdult.put(jsonObject);
                        }
                    }
                    for (int i=0;i<passengerModuleList.size();i++){
                        int childcount = 0;
                        PassengerModule passengerModule = passengerModuleList.get(i);
                        if(passengerModule.getPassengerType().equals("C")) {
//                        JSONObject jsonObject = new JSONObject();
                            childcount++;
                            jsonObjectPost.put("child"+(childcount), passengerModule.getPassengerGender());
                            jsonObjectPost.put("firstNameChild"+(childcount), passengerModule.getPassengerName());
                            jsonObjectPost.put("lastNameChild"+(childcount), passengerModule.getPassengerLastName());

                            //                        jsonArrayChild.put(jsonObject);
                        }
                    }
               /*

                params.put("flightMode","R" );
                params.put("flightNo",outboundAvailability.getFlightNo() );
                params.put("returnFlightNo",inboundAvailability.getFlightNo() );
                params.put("searchId",searchId );

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
                params.put("countryDetails","Nepal" );
                */
/*                StringEntity entity = null;
                try {
                     entity = new StringEntity(jsonObjectPost.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                    // Make Http call
                    PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
                    handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", jsonObjectPost, new PboServerRequestListener() {
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
//                    Availability inboundAvailability = inboundList.get(Integer.parseInt(inboundPosition));
                    Availability outboundAvailability = outboundList.get(Integer.parseInt(outboundPosition));
/*                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("name", "N");
                    jsonObject.accumulate("country", "C");
                    jsonObject.accumulate("twitter", "T");
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                    SaveReservationModel saveReservationModel = saveReservationModelList.get(0);

                    JSONObject arilinesPriceList = new JSONObject();
                    try {
                        arilinesPriceList.put("individualAdultCommission",saveReservationModel.getRindividualAdultCommission());
                        arilinesPriceList.put("taxIn","");
                        arilinesPriceList.put("numberOfAdult",saveReservationModel.getRnoOfAdult());
                        arilinesPriceList.put("adultFareIn","");
                        arilinesPriceList.put("flightCurrency",saveReservationModel.getRflightCurrency());
                        arilinesPriceList.put("totalFlightCost",saveReservationModel.getRtotalFlightCost());
                        arilinesPriceList.put("taxOut",saveReservationModel.getRtaxOut());
                        arilinesPriceList.put("totalPboCommission",saveReservationModel.getRtotalPboCommission());
                        arilinesPriceList.put("childFareOut",saveReservationModel.getRchildFareOut());
                        arilinesPriceList.put("fuelSurchargeOut",saveReservationModel.getRfuelSurchargeOut());
                        arilinesPriceList.put("fuelSurchargeIn","");
                        arilinesPriceList.put("numberOfChild",saveReservationModel.getRnoOfChild());
                        arilinesPriceList.put("nationality",saveReservationModel.getRnationality());
                        arilinesPriceList.put("trip_type",saveReservationModel.getRtrip_type());
                        arilinesPriceList.put("individualChildCommission",saveReservationModel.getRindividualChildCommission());
                        arilinesPriceList.put("adultFareOut",saveReservationModel.getRadultFareOut());
                        arilinesPriceList.put("childFareIn","");

                    } catch (JSONException e1) {
                        e.printStackTrace();
                    }

                    //JSONArray jsonArrayAdult = new JSONArray();

//                JSONArray jsonArrayChild = new JSONArray();


                    RequestParams jsonObjectPost = new RequestParams();

                    Log.d("nepFlightToken",nepFlightToken);
                    jsonObjectPost.put("parentTask", "nepFlight");
                    jsonObjectPost.put("childTask", "issuePassengerTicket");


                    jsonObjectPost.put("userCode", myUserSessionManager.getSecurityCode());
//                jsonObjectPost.put("userId", myUserSessionManager.getu());
                    jsonObjectPost.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
                    jsonObjectPost.put("accessToken",nepFlightToken );
                    jsonObjectPost.put("nationality",saveReservationModel.getNationality() );
                    jsonObjectPost.put("flightId",outboundAvailability.getFlightId() );
                    jsonObjectPost.put("returnFlightId",outboundAvailability.getFlightId() );
                    jsonObjectPost.put("numberOfAdult",outboundAvailability.getAdult() );
                    jsonObjectPost.put("numberOfChild",outboundAvailability.getChild() );
                    jsonObjectPost.put("airlinesImageOut",outboundAvailability.getAirlineLogo() );
                    jsonObjectPost.put("airlinesImageIn","" );
//                    jsonObjectPost.put("post", jsonObject);
                    jsonObjectPost.put("mainContactName", contact_name_main);
                    jsonObjectPost.put("mainEmail", contact_email);
                    jsonObjectPost.put("address", contact_address);
                    jsonObjectPost.put("mainContactNumber", contact_number);
                    jsonObjectPost.put("arrival", outboundAvailability.getArrival());
                    jsonObjectPost.put("departure", outboundAvailability.getDeparture());
                    jsonObjectPost.put("arilinesPriceList", arilinesPriceList);
                    jsonObjectPost.put("searchId",searchId );
                    jsonObjectPost.put("flightMode",bundleFlightMode );
                    jsonObjectPost.put("returnFlightNo",saveReservationModel.getFlightNoOut()) ;
                    jsonObjectPost.put("flightNo",outboundAvailability.getFlightNo() );
                    for (int i=0;i<passengerModuleList.size();i++){
                        PassengerModule passengerModule = passengerModuleList.get(i);
                        if(passengerModule.getPassengerType().equals("A")) {

                            jsonObjectPost.put("adult" + (i+1), passengerModule.getPassengerGender());
                            jsonObjectPost.put("firstNameAdult" + (i+1), passengerModule.getPassengerName());
                            jsonObjectPost.put("lastNameAdult"+ (i+1), passengerModule.getPassengerLastName());

                            //jsonArrayAdult.put(jsonObject);
                        }
                    }
                    for (int i=0;i<passengerModuleList.size();i++){
                        int childcount = 0;
                        PassengerModule passengerModule = passengerModuleList.get(i);
                        if(passengerModule.getPassengerType().equals("C")) {
//                        JSONObject jsonObject = new JSONObject();
                            childcount++;
                            jsonObjectPost.put("child"+(childcount), passengerModule.getPassengerGender());
                            jsonObjectPost.put("firstNameChild"+(childcount), passengerModule.getPassengerName());
                            jsonObjectPost.put("lastNameChild"+(childcount), passengerModule.getPassengerLastName());

                            //                        jsonArrayChild.put(jsonObject);
                        }
                    }
               /*

                params.put("flightMode","R" );
                params.put("flightNo",outboundAvailability.getFlightNo() );
                params.put("returnFlightNo",inboundAvailability.getFlightNo() );
                params.put("searchId",searchId );

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
                params.put("countryDetails","Nepal" );
                */
/*                StringEntity entity = null;
                try {
                     entity = new StringEntity(jsonObjectPost.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                    // Make Http call
                    PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
                    handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", jsonObjectPost, new PboServerRequestListener() {
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
                break;
            case R.id.ps_details_form:
                mainPassengerDetailsForm();
                break;
            case R.id.passenger_details_tv:
                mainPassengerDetailsForm();
                break;
            case R.id.psd_flight_arrow_down:
                mainPassengerDetailsForm();
                break;
        }
    }
    public void handleObtainPaymentDetails(JSONObject response) throws JSONException{
            Log.v("res---------", response + "");
        String  scstId = response.getString("scstId");
        String  commValue = response.getString("commValue");
        String  bookingId = response.getString("bookingId");

        JSONObject serviceType = null;




        String  purchasedValue = response.getString("purchasedValue");

        JSONObject showDiscount = response.getJSONObject("showDiscount");
        String discount = showDiscount.getString("disPer");
        JSONArray categoryAllProduct = response.getJSONArray("categoryAllProduct");
        String merchantTypeName = "";
        String netAmount = response.getString("netAmount");
        JSONObject sCategory=null;
        for (int i = 0; i<categoryAllProduct.length(); i++){
            JSONObject merchantType = categoryAllProduct.getJSONObject(i);
            sCategory= merchantType.getJSONObject("serviceCategory");
            JSONObject merchant = merchantType.getJSONObject("merchantType");
            serviceType = merchantType.getJSONObject("serviceType");
            merchantTypeName = merchant.getString("name");
        }
        String sCategoryId = sCategory.getString("id");
        String serviceTypeId = serviceType.getString("id");

        Bundle args = new Bundle();
        args.putString("sCategoryId",sCategoryId);
        args.putString("serviceTypeId",serviceTypeId);
        args.putString("scstId",scstId);
        args.putString("netAmount",netAmount);
        args.putString("discount",discount);
        args.putString("commValue",commValue);
        args.putString("bookingId",bookingId);
        args.putString("purchasedValue",purchasedValue);
        args.putString("accessToken",nepFlightToken);
        args.putString("merchantTypeName",merchantTypeName);
        Log.d("argssssss",args.toString());
        Fragment fragment = new NepFlightPaymentFragment();

        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragment.setArguments(args);
        fragmentTransaction.commit();

        }
        public  void  mainPassengerDetailsForm(){
            final EditText dialog_user_contact_name,dialog_contact_email,dialog_contact_address,dialog_contact_number;
//            final Spinner MrMS;
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view=layoutInflater.inflate(R.layout.dialog_main_passenger_details,null);
            dialog_user_contact_name=(EditText)view.findViewById(R.id.dialog_user_contact_name);
            dialog_contact_email=(EditText)view.findViewById(R.id.dialog_contact_email);
            dialog_contact_address=(EditText)view.findViewById(R.id.dialog_contact_address);
            dialog_contact_number=(EditText)view.findViewById(R.id.dialog_contact_number);

            dialog_contact_address.setText(contact_address);
            dialog_contact_email.setText(contact_email);
            dialog_user_contact_name.setText(contact_name_main);
            dialog_contact_number.setText(contact_number);
//            MrMS=(Spinner)view.findViewById(R.id.mmmrsId);



            linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            final AlertDialog builder = new AlertDialog.Builder(getContext())
                    .setPositiveButton("OK", null)
                    .setNegativeButton("CANCEL", null)
                    .setView(view)
                    .setTitle("Main Passenger Details")
                    .create();
            builder.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    final Button btnAccept = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                    btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            contact_name_main = dialog_user_contact_name.getText().toString();
                            contact_email = dialog_contact_email.getText().toString();
                            contact_address = dialog_contact_address.getText().toString();
                            contact_number=dialog_contact_number.getText().toString();
                            //textView.setText(passengerModule.getPassengerName());
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

