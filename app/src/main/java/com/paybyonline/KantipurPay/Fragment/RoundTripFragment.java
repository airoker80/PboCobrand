package com.paybyonline.KantipurPay.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.playlog.internal.LogEvent;
import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.FlightDetailsAdapterNew;
import com.paybyonline.KantipurPay.Adapter.Model.Availability;
import com.paybyonline.KantipurPay.Adapter.Model.FlightDetailsModel;
import com.paybyonline.KantipurPay.Adapter.TwoWayFlightDetailsAdapterNew;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Sameer on 5/17/2017.
 */

public class RoundTripFragment extends Fragment implements TwoWayFlightDetailsAdapterNew.checkboxListner, FlightDetailsAdapterNew.PassAdultPriceInterface, View.OnClickListener {

    String FuelSurcharge, InfantFare, ChildFare, Airline, AdultFare, Departure, AircraftType, AgencyCommission, sum,
            Child, Infant, DepartureTime, FlightDate, Adult, ResFare, FreeBaggage, Arrival, FlightId, FlightNo, ArrivalTime,
            Currency, AirlineLogo, Refundable, Tax, duration, FlightClassCode, ChildCommission;

    String searchId;

    String outboundFuelSurcharge, outboundInfantFare, outboundChildFare, outboundAirline, outboundAdultFare, outboundDeparture,
            outboundAircraftType, outboundAgencyCommission, outboundsum, outboundChild, outboundInfant,
            outboundDepartureTime, outboundFlightDate, outboundAdult, outboundResFare, outboundFreeBaggage,
            outboundArrival, outboundFlightId, outboundFlightNo, outboundArrivalTime, outboundCurrency,
            outboundAirlineLogo, outboundRefundable, outboundTax, outboundduration, outboundFlightClassCode, outboundChildCommission;

    String inboundFuelSurcharge, inboundInfantFare, inboundChildFare, inboundAirline, inboundAdultFare, inboundDeparture, inboundAircraftType, inboundAgencyCommission, inboundsum, inboundChild, inboundInfant, inboundDepartureTime, inboundFlightDate, inboundAdult, inboundResFare, inboundFreeBaggage, inboundArrival, inboundFlightId,
            inboundFlightNo, inboundArrivalTime, inboundCurrency, inboundAirlineLogo, inboundRefundable, inboundTax, inboundduration, inboundFlightClassCode, inboundChildCommission;

    String inboundPosition;
    String outboundPosition;

    String from_time = "", from_travel_type, from_travel_class, from_airline;
    String to_time = "", to_travel_type, to_travel_class, to_airline;
    Fragment fragment;
    List<FlightDetailsModel> flightDetailsModelList = new ArrayList<FlightDetailsModel>();
    List<Availability> InboundAvailabilityList = new ArrayList<Availability>();
    List<Availability> outboundAvailabilityList = new ArrayList<Availability>();
    double onewayPrice = 0.0, roundtripPrice = 0.0;
    RecyclerView onewayRecyclerView, roundTripRecyclerView;
    TextView totalFlightPrice, from_place_name_tv, landing_place_name_tv, return_place_from, return_landing_place, return_departure_date_details, flight_departure_date_details;
    LinearLayout goneDepartureLayout, continueToConfirmLL, noneGoneLL;
    private TwoWayFlightDetailsAdapterNew twoWayFlightDetailsAdapter;
    String value, fromDate, toDate, flight_type, adults, childs, nationality, nepFlightToken,
            place_frm, place_to, place_frm_name, landing_place_name, from_weekday, to_weekday, flight_date, return_date;

    CoordinatorLayout coordinatorLayout;
    private MyUserSessionManager myUserSessionManager;
    LinearLayout flightNotAvailable, shownFlight;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_round_trip, container, false);
        myUserSessionManager = new MyUserSessionManager(getActivity());
        goneDepartureLayout = (LinearLayout) view.findViewById(R.id.goneDepartureLayout);
        continueToConfirmLL = (LinearLayout) view.findViewById(R.id.continueToConfirmLL);
        noneGoneLL = (LinearLayout) view.findViewById(R.id.noneGoneLL);
        from_place_name_tv = (TextView) view.findViewById(R.id.from_place_name);
        landing_place_name_tv = (TextView) view.findViewById(R.id.landing_place_name);
        return_landing_place = (TextView) view.findViewById(R.id.return_landing_place);
        return_place_from = (TextView) view.findViewById(R.id.return_place_from);
        return_departure_date_details = (TextView) view.findViewById(R.id.return_departure_date_details);
        flight_departure_date_details = (TextView) view.findViewById(R.id.flight_departure_date_details);

        flightNotAvailable = (LinearLayout) view.findViewById(R.id.flightNotAvailable);
        shownFlight = (LinearLayout) view.findViewById(R.id.shownFlight);

        totalFlightPrice = (TextView) view.findViewById(R.id.totalFlightPrice);
        totalFlightPrice.setTypeface(NewBookFlightFragment.typeFace);


        value = getArguments().getString("flag");
        fromDate = getArguments().getString("fromDate");
        flight_type = getArguments().getString("flight_type");
        flight_date = getArguments().getString("flight_date");
        return_date = getArguments().getString("return_date");
        adults = getArguments().getString("adults");
        childs = getArguments().getString("childs");
        nationality = getArguments().getString("nationality");
        toDate = getArguments().getString("toDate");
        nepFlightToken = getArguments().getString("nepFlightToken");
        place_to = getArguments().getString("place_to");
        place_frm = getArguments().getString("place_frm");
        from_weekday = getArguments().getString("from_day");
        to_weekday = getArguments().getString("to_day");
        place_frm_name = getArguments().getString("place_frm_name");
        landing_place_name = getArguments().getString("place_to_name");

        searchFlightForBookin();
        Log.e("flag", value);
        if (value == "false") {
            goneDepartureLayout.setVisibility(View.VISIBLE);
            noneGoneLL.setVisibility(View.VISIBLE);
        } else {
            goneDepartureLayout.setVisibility(View.GONE);
        }
        ((DashBoardActivity) getActivity()).setTitle("Select a flight");
        ((DashBoardActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        onewayRecyclerView = (RecyclerView) view.findViewById(R.id.onewayRecyclerView);
        roundTripRecyclerView = (RecyclerView) view.findViewById(R.id.roundTripRecyclerView);

        continueToConfirmLL.setOnClickListener(this);

        String[] split_day_year = from_weekday.split("\n");
        String[] split_day_year_return = to_weekday.split("\n");

//        Log.e("splitWeekday",split_day_year_return[0] + split_day_year_return[1]);


        from_place_name_tv.setText(place_frm_name + "(" + place_frm + ")");
        landing_place_name_tv.setText(landing_place_name + "(" + place_to + ")");
        return_landing_place.setText(place_frm_name + "(" + place_frm + ")");
        return_place_from.setText(landing_place_name + "(" + place_to + ")");
        flight_departure_date_details.setText(split_day_year[0] + "," + flight_date + split_day_year[1]);
        return_departure_date_details.setText(split_day_year_return[0] + "," + return_date + split_day_year_return[1]);

/*
        for (int i =0;i<5;i++){
            flightDetailsModelList.add(new FlightDetailsModel("7:30 - 8:00","Saurya","Y","beech","9000"));
            flightDetailsModelList.add(new FlightDetailsModel("7:00 - 7:30","Saurya","Y","beech","8000"));
        }
*/


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        onewayRecyclerView.setLayoutManager(layoutManager);
        roundTripRecyclerView.setLayoutManager(layoutManager2);

        return view;


    }

    private void searchFlightForBookin() {

        Log.d("udtails", String.valueOf(myUserSessionManager.getUserDetails())+myUserSessionManager.getSecurityCode() +"--e"+ myUserSessionManager.getUsername());

        Log.d("token", nepFlightToken);
        RequestParams params = new RequestParams();
        params.put("fromDate", fromDate); // MM/dd/yyyy
        params.put("toDate", toDate);
        params.put("countryDetails", "np");
        params.put("nepToken", nepFlightToken);
        params.put("flightMode", flight_type);
        params.put("fromLocation", place_frm);
        params.put("toLocation", place_to);
        params.put("adults[2]", adults);
        params.put("child[2]", childs);
        params.put("parentTask", "nepFlight");
        params.put("childTask", "eachFlightDetails");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout, getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
//                    response = (JSONObject) JSONObject.wrap(getResources().getResourceName(R.raw.flightjson));
                    Log.i("authenticateUser", "" + response);
                    handleObtainPaymentDetails(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleObtainPaymentDetails(JSONObject response) throws JSONException {
        Log.d("responseObj", "asdad===" + response.toString() + "------Inbound");
        searchId = response.getString("searchId");
        Log.e("searchId","---------------"+ searchId);
        if (response.getString("msgTitle").equals("failed")) {
//            getActivity().getFragmentManager().popBackStack();
            Toast.makeText(getActivity(), response.getString("response").toString(), Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
//
        } else {
            if (value == "false") {
                JSONObject outbound = response.getJSONObject("Outbound");
                JSONObject inbound = response.getJSONObject("Inbound");

                Log.d("afafasfafafa", outbound.toString());
                JSONArray Availability;
                JSONArray outboundAvailability;
                try {

                    outboundAvailability = outbound.getJSONArray("Availability");
                    for (int k = 0; k < outboundAvailability.length(); k++) {
                        JSONObject avaibilityObj1 = outboundAvailability.getJSONObject(k);
                        FuelSurcharge = avaibilityObj1.getString("FuelSurcharge");
                        InfantFare = avaibilityObj1.getString("InfantFare");
                        ChildFare = avaibilityObj1.getString("ChildFare");
                        Airline = avaibilityObj1.getString("Airline");
                        AdultFare = avaibilityObj1.getString("AdultFare");
                        Departure = avaibilityObj1.getString("Departure");
                        AircraftType = avaibilityObj1.getString("AircraftType");
                        AgencyCommission = avaibilityObj1.getString("AgencyCommission");
                        sum = avaibilityObj1.getString("sum");
                        Child = avaibilityObj1.getString("Child");
                        Infant = avaibilityObj1.getString("Infant");
                        DepartureTime = avaibilityObj1.getString("DepartureTime");
                        FlightDate = avaibilityObj1.getString("FlightDate");
                        Adult = avaibilityObj1.getString("Adult");
                        ResFare = avaibilityObj1.getString("ResFare");
                        FreeBaggage = avaibilityObj1.getString("FreeBaggage");
                        Arrival = avaibilityObj1.getString("Arrival");
                        FlightId = avaibilityObj1.getString("FlightId");
                        FlightNo = avaibilityObj1.getString("FlightNo");
                        ArrivalTime = avaibilityObj1.getString("ArrivalTime");
                        Currency = avaibilityObj1.getString("Currency");
                        AirlineLogo = avaibilityObj1.getString("AirlineLogo");
                        Refundable = avaibilityObj1.getString("Refundable");
                        Tax = avaibilityObj1.getString("Tax");
                        duration = avaibilityObj1.getString("duration");
                        FlightClassCode = avaibilityObj1.getString("FlightClassCode");
                        ChildCommission = avaibilityObj1.getString("ChildCommission");

                        outboundAvailabilityList.add(new Availability(FuelSurcharge, InfantFare, ChildFare, Airline, AdultFare, Departure,
                                AircraftType, AgencyCommission, sum, Child, Infant, DepartureTime, FlightDate, Adult, ResFare,
                                FreeBaggage, Arrival, FlightId, FlightNo, ArrivalTime, Currency, AirlineLogo, Refundable, Tax, duration, FlightClassCode, ChildCommission));

                        flightDetailsModelList.add(new FlightDetailsModel(DepartureTime + " - " + ArrivalTime, Airline, FlightClassCode, AircraftType, sum));
//            Log.d("flightDetailsModelList", flightDetailsModelList.get(k).getFlightTime());
                    }
//                    Log.d("inboundList",InboundAvailabilityList.get(0).getAdult());


                    FlightDetailsAdapterNew flightDetailsAdapter = new FlightDetailsAdapterNew(getActivity(), this, outboundAvailabilityList, from_time);
                    onewayRecyclerView.setAdapter(flightDetailsAdapter);

                    Availability = inbound.getJSONArray("Availability");
                    for (int j = 0; j < Availability.length(); j++) {
                        JSONObject avaibilityObj = Availability.getJSONObject(j);
                        FuelSurcharge = avaibilityObj.getString("FuelSurcharge");
                        InfantFare = avaibilityObj.getString("InfantFare");
                        ChildFare = avaibilityObj.getString("ChildFare");
                        Airline = avaibilityObj.getString("Airline");
                        AdultFare = avaibilityObj.getString("AdultFare");
                        Departure = avaibilityObj.getString("Departure");
                        AircraftType = avaibilityObj.getString("AircraftType");
                        AgencyCommission = avaibilityObj.getString("AgencyCommission");
                        sum = avaibilityObj.getString("sum");
                        Child = avaibilityObj.getString("Child");
                        Infant = avaibilityObj.getString("Infant");
                        DepartureTime = avaibilityObj.getString("DepartureTime");
                        FlightDate = avaibilityObj.getString("FlightDate");
                        Adult = avaibilityObj.getString("Adult");
                        ResFare = avaibilityObj.getString("ResFare");
                        FreeBaggage = avaibilityObj.getString("FreeBaggage");
                        Arrival = avaibilityObj.getString("Arrival");
                        FlightId = avaibilityObj.getString("FlightId");
                        FlightNo = avaibilityObj.getString("FlightNo");
                        ArrivalTime = avaibilityObj.getString("ArrivalTime");
                        Currency = avaibilityObj.getString("Currency");
                        AirlineLogo = avaibilityObj.getString("AirlineLogo");
                        Refundable = avaibilityObj.getString("Refundable");
                        Tax = avaibilityObj.getString("Tax");
                        duration = avaibilityObj.getString("duration");
                        FlightClassCode = avaibilityObj.getString("FlightClassCode");
                        ChildCommission = avaibilityObj.getString("ChildCommission");

                        InboundAvailabilityList.add(new Availability(FuelSurcharge, InfantFare, ChildFare, Airline, AdultFare, Departure,
                                AircraftType, AgencyCommission, sum, Child, Infant, DepartureTime, FlightDate, Adult, ResFare,
                                FreeBaggage, Arrival, FlightId, FlightNo, ArrivalTime, Currency, AirlineLogo, Refundable, Tax, duration, FlightClassCode, ChildCommission));

                        flightDetailsModelList.add(new FlightDetailsModel(DepartureTime + " - " + ArrivalTime, Airline, FlightClassCode, AircraftType, sum));
                        Log.d("flightDetailsModelList", flightDetailsModelList.get(j).getFlightTime());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                twoWayFlightDetailsAdapter = new TwoWayFlightDetailsAdapterNew(getActivity(), InboundAvailabilityList, this, to_time);
                roundTripRecyclerView.setAdapter(twoWayFlightDetailsAdapter);
            } else {
                JSONObject outbound = response.getJSONObject("Outbound");
                JSONArray outboundAvailability;
                try {

                    outboundAvailability = outbound.getJSONArray("Availability");
                    for (int k = 0; k < outboundAvailability.length(); k++) {
                        JSONObject avaibilityObj1 = outboundAvailability.getJSONObject(k);
                        FuelSurcharge = avaibilityObj1.getString("FuelSurcharge");
                        InfantFare = avaibilityObj1.getString("InfantFare");
                        ChildFare = avaibilityObj1.getString("ChildFare");
                        Airline = avaibilityObj1.getString("Airline");
                        AdultFare = avaibilityObj1.getString("AdultFare");
                        Departure = avaibilityObj1.getString("Departure");
                        AircraftType = avaibilityObj1.getString("AircraftType");
                        AgencyCommission = avaibilityObj1.getString("AgencyCommission");
                        sum = avaibilityObj1.getString("sum");
                        Child = avaibilityObj1.getString("Child");
                        Infant = avaibilityObj1.getString("Infant");
                        DepartureTime = avaibilityObj1.getString("DepartureTime");
                        FlightDate = avaibilityObj1.getString("FlightDate");
                        Adult = avaibilityObj1.getString("Adult");
                        ResFare = avaibilityObj1.getString("ResFare");
                        FreeBaggage = avaibilityObj1.getString("FreeBaggage");
                        Arrival = avaibilityObj1.getString("Arrival");
                        FlightId = avaibilityObj1.getString("FlightId");
                        FlightNo = avaibilityObj1.getString("FlightNo");
                        ArrivalTime = avaibilityObj1.getString("ArrivalTime");
                        Currency = avaibilityObj1.getString("Currency");
                        AirlineLogo = avaibilityObj1.getString("AirlineLogo");
                        Refundable = avaibilityObj1.getString("Refundable");
                        Tax = avaibilityObj1.getString("Tax");
                        duration = avaibilityObj1.getString("duration");
                        FlightClassCode = avaibilityObj1.getString("FlightClassCode");
                        ChildCommission = avaibilityObj1.getString("ChildCommission");

                        outboundAvailabilityList.add(new Availability(FuelSurcharge, InfantFare, ChildFare, Airline, AdultFare, Departure,
                                AircraftType, AgencyCommission, sum, Child, Infant, DepartureTime, FlightDate, Adult, ResFare,
                                FreeBaggage, Arrival, FlightId, FlightNo, ArrivalTime, Currency, AirlineLogo, Refundable, Tax, duration, FlightClassCode, ChildCommission));

//                  flightDetailsModelList.add(new FlightDetailsModel(DepartureTime+ " - " +ArrivalTime,Airline,FlightClassCode,AircraftType,sum));
//            Log.d("flightDetailsModelList", flightDetailsModelList.get(k).getFlightTime());
                    }
                    FlightDetailsAdapterNew flightDetailsAdapter = new FlightDetailsAdapterNew(getActivity(), this, outboundAvailabilityList, from_time);
                    onewayRecyclerView.setAdapter(flightDetailsAdapter);


//                  flightDetailsModelList.add(new FlightDetailsModel(DepartureTime+ " - " +ArrivalTime,Airline,FlightClassCode,AircraftType,sum));


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        from_time = "";
        to_time = "";
        onewayPrice = 0.00;
        roundtripPrice = 0.00;
    }


    @Override
    public void getFlightValue(String price) {
        roundtripPrice = Double.parseDouble(price);
        totalFlightPrice.setText(String.valueOf(roundtripPrice + onewayPrice));
        Log.d("position", String.valueOf(price));
    }

    @Override
    public void setTime(String time, String traveltype, String travelClass, String airelineName, String position) {
        to_time = time;
        to_travel_type = traveltype;
        to_travel_class = travelClass;
        to_airline = airelineName;
        inboundPosition = position;
        Log.d("position", String.valueOf(position));
    }


    @Override
    public void passAvailabilityDetails(String position) {
        Log.d("NewPosition", String.valueOf(position));
    }

/*
    @Override
    public void passAvailabilityDetails(String fuelSurcharge, String infantFare, String childFare, String airline, String adultFare, String departure, String aircraftType, String agencyCommission, String sum, String child, String infant, String departureTime, String flightDate, String adult, String resFare, String freeBaggage, String arrival, String flightId, String flightNo, String arrivalTime, String currency, String airlineLogo, String refundable, String tax, String duration, String flightClassCode, String childCommission, int position) {
        outboundPosition = position;
        Log.d("passposition", String.valueOf(position) +fuelSurcharge);
    }
*/

/*
    @Override
    public void passAvailabilityDetails(String fuelSurcharge, String infantFare, String childFare, String airline, String adultFare, String departure, String aircraftType, String agencyCommission, String sum, String child, String infant, String departureTime, String flightDate, String adult, String resFare, String freeBaggage, String arrival, String flightId, String flightNo, String arrivalTime, String currency, String airlineLogo, String refundable, String tax, String duration, String flightClassCode, String childCommission, int position) {
                outboundFuelSurcharge=fuelSurcharge;
                outboundInfantFare=infantFare;
                outboundChildFare=childFare;
                outboundAirline=airline;
                outboundAdultFare=adultFare;
                outboundDeparture=departure;
                outboundAircraftType =aircraftType;
                outboundAgencyCommission=agencyCommission;
                outboundsum=sum;
                outboundChild=child;
                outboundInfant=infant;
                outboundDepartureTime=departureTime;
                outboundFlightDate=flightDate;
                outboundAdult=adult;
                outboundResFare=resFare;
                outboundFreeBaggage=freeBaggage;
                outboundArrival=arrival;
                outboundFlightId=flightId;
                outboundFlightNo=flightNo;
                outboundArrivalTime=arrivalTime;
                outboundCurrency=currency;
                outboundAirlineLogo=airlineLogo;
                outboundRefundable=refundable;
                outboundTax=tax;
                outboundduration=duration;
                outboundFlightClassCode=flightClassCode;
                outboundChildCommission=childCommission;
                outboundPosition = position;
    }

*/

    @Override
    public void passAdultPrice(double adultPrice) {
        onewayPrice = adultPrice;
        totalFlightPrice.setText(String.valueOf(roundtripPrice + onewayPrice));
    }

    @Override
    public void passTime(String time, String traveltype, String travelClass, String airelineNamem, String position) {
        from_time = time;
        from_travel_class = travelClass;
        from_travel_type = traveltype;
        from_airline = airelineNamem;
        outboundPosition = position;
        Log.d("position", position);
    }


    @Override
    public void passAvailability(int passPosition) {

    }

/*
    @Override
    public void passAvailability(String fuelSurcharge, String infantFare, String childFare, String airline, String adultFare, String departure, String aircraftType, String agencyCommission, String sum, String child, String infant, String departureTime, String flightDate, String adult, String resFare, String freeBaggage, String arrival, String flightId, String flightNo, String arrivalTime, String currency, String airlineLogo, String refundable, String tax, String duration, String flightClassCode, String childCommission, int passPosition) {
        inboundPosition =passPosition;
        Log.d("position", String.valueOf(passPosition)
        );
    }
*/


/*
    @Override
    public void passAvailability(String fuelSurcharge, String infantFare, String childFare, String airline, String adultFare, String departure, String aircraftType, String agencyCommission, String sum, String child, String infant, String departureTime, String flightDate, String adult, String resFare, String freeBaggage, String arrival, String flightId, String flightNo, String arrivalTime, String currency, String airlineLogo, String refundable, String tax, String duration, String flightClassCode, String childCommission, int passPosition) {
        inboundFuelSurcharge=fuelSurcharge;
        inboundInfantFare=infantFare;
        inboundChildFare=childFare;
        inboundAirline=airline;
        inboundAdultFare=adultFare;
        inboundDeparture=departure;
        inboundAircraftType =aircraftType;
        inboundAgencyCommission=agencyCommission;
        inboundsum=sum;
        inboundChild=child;
        inboundInfant=infant;
        inboundDepartureTime=departureTime;
        inboundFlightDate=flightDate;
        inboundAdult=adult;
        inboundResFare=resFare;
        inboundFreeBaggage=freeBaggage;
        inboundArrival=arrival;
        inboundFlightId=flightId;
        inboundFlightNo=flightNo;
        inboundArrivalTime=arrivalTime;
        inboundCurrency=currency;
        inboundAirlineLogo=airlineLogo;
        inboundRefundable=refundable;
        inboundTax=tax;
        inboundduration=duration;
        inboundFlightClassCode=flightClassCode;
        inboundChildCommission=childCommission;
        inboundPosition = passPosition;
    }
*/

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.continueToConfirmLL:
                Log.d("",InboundAvailabilityList.toString()+"=== \n"+ outboundAvailabilityList.toString());
                if (value == "false") {
                    if (onewayPrice != 0 && roundtripPrice != 0) {
                        fragment = new FlightSummaryFragment();
                        Bundle args = new Bundle();


                        args.putParcelableArrayList("inboundList", (ArrayList<? extends Parcelable>) InboundAvailabilityList);
                        args.putParcelableArrayList("outboundList", (ArrayList<? extends Parcelable>) outboundAvailabilityList);

                        args.putString("flightMode", String.valueOf(flight_type));
                        args.putString("flag", String.valueOf(value));
                        args.putString("searchId", String.valueOf(searchId));
                        args.putString("nepFlightToken", String.valueOf(nepFlightToken));
                        args.putString("flight_departure_date_details", String.valueOf(flight_departure_date_details.getText()));
                        args.putString("return_departure_date_details", String.valueOf(return_departure_date_details.getText()));
                        args.putString("inboundPosition", String.valueOf(inboundPosition));
                        args.putString("outboundPosition", String.valueOf(outboundPosition));
                        args.putString("place_frm", String.valueOf(place_frm));
                        args.putString("place_frm_name", String.valueOf(place_frm_name));
                        args.putString("place_to", String.valueOf(place_to));
                        args.putString("landing_place_name", String.valueOf(landing_place_name));
                        args.putString("adult", String.valueOf(adults));
                        args.putString("child", String.valueOf(childs));
                        args.putString("totalFlightPrice", String.valueOf(totalFlightPrice.getText()));
                        args.putString("from_time", String.valueOf(from_time));
                        args.putString("from_travel_type", String.valueOf(from_travel_type));
                        args.putString("from_travel_class", String.valueOf(from_travel_class));
                        args.putString("from_airline", String.valueOf(from_airline));
                        args.putString("to_time", String.valueOf(to_time));
                        args.putString("to_travel_type", String.valueOf(to_travel_type));
                        args.putString("to_travel_class", String.valueOf(to_travel_class));
                        args.putString("to_airline", String.valueOf(to_airline));
                        fragment.setArguments(args);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.content_frame, fragment);
                        fragmentTransaction.commit();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(v, "Please Select both flight before Proceeding", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        View snackbarview = snackbar.getView();
                        TextView textView = (TextView) snackbarview.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.RED);
                    }
                } else {
                    if (onewayPrice != 0) {
                        fragment = new FlightSummaryFragment();
                        Bundle args = new Bundle();


                        args.putParcelableArrayList("inboundList", (ArrayList<? extends Parcelable>) InboundAvailabilityList);
                        args.putParcelableArrayList("outboundList", (ArrayList<? extends Parcelable>) outboundAvailabilityList);


                        args.putString("flag", String.valueOf(value));
                        args.putString("nepFlightToken", String.valueOf(nepFlightToken));
                        args.putString("searchId", String.valueOf(searchId));
                        args.putString("flightMode", String.valueOf(flight_type));
                        args.putString("flight_departure_date_details", String.valueOf(flight_departure_date_details.getText()));
                        args.putString("return_departure_date_details", String.valueOf(return_departure_date_details.getText()));
                        args.putString("inboundPosition", String.valueOf(inboundPosition));
                        args.putString("outboundPosition", String.valueOf(outboundPosition));
                        args.putString("place_frm", String.valueOf(place_frm));
                        args.putString("place_frm_name", String.valueOf(place_frm_name));
                        args.putString("place_to", String.valueOf(place_to));
                        args.putString("landing_place_name", String.valueOf(landing_place_name));
                        args.putString("adult", String.valueOf(adults));
                        args.putString("child", String.valueOf(childs));
                        args.putString("totalFlightPrice", String.valueOf(totalFlightPrice.getText()));
                        args.putString("from_time", String.valueOf(from_time));
                        args.putString("from_travel_type", String.valueOf(from_travel_type));
                        args.putString("from_travel_class", String.valueOf(from_travel_class));
                        args.putString("from_airline", String.valueOf(from_airline));
                        args.putString("to_time", String.valueOf(to_time));
                        args.putString("to_travel_type", String.valueOf(to_travel_type));
                        args.putString("to_travel_class", String.valueOf(to_travel_class));
                        args.putString("to_airline", String.valueOf(to_airline));
                        fragment.setArguments(args);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.content_frame, fragment);
                        fragmentTransaction.commit();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(v, "Please Select the required flight before Proceeding", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        View snackbarview = snackbar.getView();
                        TextView textView = (TextView) snackbarview.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.RED);
                    }
                }

                break;
        }
    }

}
