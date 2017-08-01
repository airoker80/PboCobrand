package com.paybyonline.KantipurPay.Fragment;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Adapter.Model.FlightCountriesModel;
import com.paybyonline.KantipurPay.Adapter.Model.FlightSectorModel;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Sameer on 4/28/2017.
 */

public class BookFlightFragment extends Fragment implements View.OnClickListener {
    Spinner FromSpinner ,countriesSpinner,ToSpinner;
    ImageView updown_dwl;
    boolean flag = true;
    public TextView passenger_number,childPassengerNumber;
    HashMap<String,String> fromHashMap,toHashMap;
    Button setSelectedDateBtn;
    String date;
    public Button flight_from,flight_to;
    RadioButton radio_round_trip,radio_one_way;
    RadioGroup radio_flight_group;
    int day,year,month;
    int presentDay,presentYear,presentMonth;
    TextView passenger_number_edtxt;
    LinearLayout showBookingOption;
    RelativeLayout showPassenger;
    CoordinatorLayout coordinatorLayout;
    private MyUserSessionManager myUserSessionManager;
    int passengers=0;
    int childPassenger=0,adultPassenger=0;
    ImageView child_passenger_plus,child_passenger_minus,adult_passenger_plus,adult_passenger_minus;
    String nepFlightToken;
    ArrayList<String> fromTo,sectorCodeList;
    Button flightSearch;

    List<FlightSectorModel> flightSectorModelList =new ArrayList<FlightSectorModel>();
    List<FlightCountriesModel> FlightCountriesModelList =new ArrayList<FlightCountriesModel>();
//    ArrayList<String> sectorList =new ArrayList<String>();

    public BookFlightFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_book, container, false);
        FromSpinner=(Spinner)view.findViewById(R.id.FromSpinner);
        ToSpinner=(Spinner)view.findViewById(R.id.ToSpinner);
        countriesSpinner=(Spinner)view.findViewById(R.id.countriesSpinner);
        fromTo=new ArrayList<String>();
        sectorCodeList=new ArrayList<String>();
        flight_from=(Button)view.findViewById(R.id.flight_from);
        fromHashMap=new HashMap<String, String>();
        toHashMap=new HashMap<String, String>();
        flight_to=(Button)view.findViewById(R.id.flight_to);
        radio_round_trip=(RadioButton)view.findViewById(R.id.radio_round_trip);
        radio_one_way=(RadioButton)view.findViewById(R.id.radio_one_way);
        radio_flight_group=(RadioGroup)view.findViewById(R.id.radio_flight_group);
        passenger_number_edtxt=(TextView)view.findViewById(R.id.passenger_number_edtxt);
        showBookingOption=(LinearLayout) view.findViewById(R.id.showBookingOption);
        showPassenger=(RelativeLayout) view.findViewById(R.id.showPassenger);
        passenger_number=(TextView) view.findViewById(R.id.passenger_number);
        childPassengerNumber=(TextView) view.findViewById(R.id.childPassengerNumber);
        child_passenger_plus=(ImageView) view.findViewById(R.id.child_passenger_plus);
        child_passenger_minus=(ImageView) view.findViewById(R.id.child_passenger_minus);
        adult_passenger_plus=(ImageView) view.findViewById(R.id.adult_passenger_plus);
        adult_passenger_minus=(ImageView) view.findViewById(R.id.adult_passenger_minus);
        flightSearch=(Button)view.findViewById(R.id.flightSearch);
        flightSearch.setOnClickListener(this);
        updown_dwl=(ImageView) view.findViewById(R.id.updown_dwl);
        updown_dwl.setOnClickListener(this);
        radio_round_trip.setChecked(true);
        flight_to.setVisibility(View.VISIBLE);
        flight_to.setOnClickListener(this);
        showPassenger.setOnClickListener(this);
        passenger_number_edtxt.setOnClickListener(this);
        showBookingOption.setVisibility(View.GONE);

        child_passenger_plus.setOnClickListener(this);
        child_passenger_minus.setOnClickListener(this);
        adult_passenger_plus.setOnClickListener(this);
        adult_passenger_minus.setOnClickListener(this);

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] dateString = date.split("-");
        presentYear = Integer.parseInt(dateString[0]);
        presentMonth = Integer.parseInt(dateString[1]);
        presentDay = Integer.parseInt(dateString[2]);
        flight_from.setText(date);
        flight_to.setText(date);
/*        if (adultPassenger>0)
            adult_passenger_minus.setEnabled(true);
        else
            adult_passenger_minus.setEnabled(false);
        if (childPassenger>0)
            child_passenger_minus.setEnabled(true);
        else
            child_passenger_minus.setEnabled(false);*/

        myUserSessionManager = new MyUserSessionManager(getActivity());

        flight_from.setOnClickListener(this);
        radio_flight_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_one_way:
                            flight_to.setVisibility(View.GONE);
                            radio_round_trip.setChecked(false);

                        break;

                    case R.id.radio_round_trip:
                            flight_to.setVisibility(View.VISIBLE);
                            radio_one_way.setChecked(false);
                        break;
                }
            }
        });
        obtainFlightDetails();
        return view;
    }
    public void setCurrentDateOnView(Button fromToBtn,String calDate) {

        if(calDate.length()>0){

//           String[] lastPostDateVal = lastPostDate.split("-");
            fromToBtn.setText(calDate);

        }else{

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            // set current date into textview
            fromToBtn.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(year).append("-").append(month + 1).append("-")
                    .append(day));

        }




    }
    void showDatePicker(Button fromToBtn) {

        setSelectedDateBtn = fromToBtn;
        DatePickerFragment date = new DatePickerFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();

        String[] selDate = fromToBtn.getText().toString().split("-");

        args.putInt("year", Integer.parseInt(selDate[0]));
        args.putInt("month", Integer.parseInt(selDate[1]) - 1);
        args.putInt("day", Integer.parseInt(selDate[2]));
        date.setArguments(args);

        date.setCallBack(ondate);

        date.show(getActivity().getSupportFragmentManager(), "Date Picker");


    }

    DatePickerDialog.OnDateSetListener ondate =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    date=String.valueOf(year) + "-"
                            + String.valueOf(monthOfYear + 1) + "-"
                            + String.valueOf(dayOfMonth);

                    setSelectedDateBtn.setText(date);

                }
            };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flightSearch:
                searchFlight();
                break;
            case R.id.flight_from:
                showDatePicker(flight_from);
                break;
            case R.id.showPassenger:
                if (flag){
                    showBookingOption.setVisibility(View.VISIBLE);
                    flag=false;
                    updown_dwl.setImageResource(R.drawable.ic_expand_less);
                }
                else {
                    showBookingOption.setVisibility(View.GONE);
                    flag=true;
                    updown_dwl.setImageResource(R.drawable.ic_expand_more);
                }

                break;
            case R.id.updown_dwl:
                if (flag){
                    showBookingOption.setVisibility(View.VISIBLE);
                    flag=false;
                    updown_dwl.setImageResource(R.drawable.ic_expand_less);
                }
                else {
                    showBookingOption.setVisibility(View.GONE);
                    flag=true;
                    updown_dwl.setImageResource(R.drawable.ic_expand_more);
                }

                break;
            case R.id.flight_to:
                showDatePicker(flight_to);
                break;
            case R.id.passenger_number_edtxt:
                if (flag){
                    showBookingOption.setVisibility(View.VISIBLE);
                    flag=false;
                    updown_dwl.setImageResource(R.drawable.ic_expand_less);
                }
                else {
                    showBookingOption.setVisibility(View.GONE);
                    flag=true;
                    updown_dwl.setImageResource(R.drawable.ic_expand_more);
                }

                break;
            case R.id.adult_passenger_plus:
                adultPassenger++;
                passenger_number.setText(String.valueOf(adultPassenger));
                passenger_number_edtxt.setText(String.valueOf(adultPassenger+childPassenger)+" Passengers");

                break;
            case R.id.child_passenger_plus:
                childPassenger++;
                childPassengerNumber.setText(String.valueOf(childPassenger));
                passenger_number_edtxt.setText(String.valueOf(adultPassenger+childPassenger)+" Passengers");
                break;
            case R.id.adult_passenger_minus:

                if (adultPassenger>0){
                    adultPassenger--;

                    passenger_number.setText(String.valueOf(adultPassenger));
                    passenger_number_edtxt.setText(String.valueOf(adultPassenger+childPassenger)+" Passengers");
                }
                break;
            case R.id.child_passenger_minus:

                if (childPassenger>0) {
                    childPassenger--;

                    childPassengerNumber.setText(String.valueOf(childPassenger));
                    passenger_number_edtxt.setText(String.valueOf(adultPassenger+childPassenger)+" Passengers");
                }

                break;
        }
    }

/*
    public void HttpAsyncTask() {
        RequestParams params = new RequestParams();
        params.put("parentTask", "nepFlight");
        params.put("childTask", "findFlights");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
*//*        params.put("paymentOption",
                paymentOption.getSelectedItem().toString());*//*
           *//* params.put("purchasedAmt",
                    purchasedAmt.getText().toString());*//*



// Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Obtaining Details", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("response",response.toString());
                try {
                    HttpAsyncTaskResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });


    }
    public void HttpAsyncTaskResponse(JSONObject response) throws  JSONException{
             Log.d("res----", response.toString());
            JSONArray countryDetails = response.getJSONArray("countryDetails");
            JSONArray sectorList = response.getJSONArray("sectorList");
            String nepFlightToken = response.getString("nepFlightToken");
                for (int j=0;j<countryDetails.length();j++){
                    JSONObject countryDetailsObject = countryDetails.getJSONObject(j);

                    String countryCode = countryDetailsObject.getString("countryCode");
                    String countryName = countryDetailsObject.getString("countryName");
                    String currencyName = countryDetailsObject.getString("currencyName");
                    String currencySymbol = countryDetailsObject.getString("currencySymbol");
                    String isServiceProviding = countryDetailsObject.getString("isServiceProviding");
                    Toast.makeText(getContext(),countryName,Toast.LENGTH_LONG).show();
                    fromTo.add(countryName);
                    Log.d("fromTo",fromTo.toString());
                }
                for (int k=0;k<sectorList.length();k++){
                    JSONObject sectorDetailsObject = sectorList.getJSONObject(k);

                    String SectorName = sectorDetailsObject.getString("SectorName");
                    String SectorCode = sectorDetailsObject.getString("SectorCode");
                }



        FromSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        fromTo));

    }*/
    public  void obtainFlightDetails(){



        RequestParams params = new RequestParams();

        params.put("parentTask", "nepFlight");
        params.put("childTask", "findFlights");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());

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
    public void handleObtainPaymentDetails(JSONObject response) throws JSONException{
        Log.i("PaymentDetails",""+response);

        JSONArray countryDetails = response.getJSONArray("countryDetails");
        JSONArray sectorList = response.getJSONArray("sectorList");
        nepFlightToken = response.getString("nepFlightToken");
//        Toast.makeText(getContext(),nepFlightToken,Toast.LENGTH_LONG).show();
        for (int j=0;j<countryDetails.length();j++){
            JSONObject countryDetailsObject = countryDetails.getJSONObject(j);

            String countryCode = countryDetailsObject.getString("countryCode");
            String countryName = countryDetailsObject.getString("countryName");
            String currencyName = countryDetailsObject.getString("currencyName");
            String currencySymbol = countryDetailsObject.getString("currencySymbol");
            String isServiceProviding = countryDetailsObject.getString("isServiceProviding");
            FlightCountriesModelList.add(new FlightCountriesModel(countryCode,countryName,currencyName,currencySymbol,isServiceProviding));
        }
        for (int k=0;k<sectorList.length();k++){
            JSONObject sectorDetailsObject = sectorList.getJSONObject(k);

            String SectorName = sectorDetailsObject.getString("SectorName");
            String SectorCode = sectorDetailsObject.getString("SectorCode");
            toHashMap.put("SectorName",SectorName);
            toHashMap.put("SectorCode",SectorCode);
            flightSectorModelList.add(new FlightSectorModel(SectorName,SectorCode));
            //sectorCodeList.add(SectorName);
        }


        for(FlightSectorModel s: flightSectorModelList){
            sectorCodeList.add(s.getSectorName());

        }
        for(FlightCountriesModel c: FlightCountriesModelList){
            fromTo.add(c.getCountryName());

        }
        FromSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, sectorCodeList));
        ToSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, sectorCodeList));
        countriesSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, fromTo));


    }

    public void searchFlight(){
        String sectorCode = "";
        String fromLocation = FromSpinner.getSelectedItem().toString();

        for(int i = 0; i<flightSectorModelList.size();i++){
            if (flightSectorModelList.get(i).getSectorName().equals(fromLocation)){
                sectorCode=flightSectorModelList.get(i).getSectorCode();
                break;
            }
        }
        Toast.makeText(getContext(),sectorCode,Toast.LENGTH_LONG).show();

        String toLocation = ToSpinner.getSelectedItem().toString();
        String sectorLocation = countriesSpinner.getSelectedItem().toString();



        String flightFromDate = flight_from.getText().toString();
        String flightToDate = flight_to.getText().toString();
        String passenger_numberAdult=passenger_number.getText().toString();
        String passenger_numberChild=childPassengerNumber.getText().toString();

        RequestParams params = new RequestParams();

        params.put("fromDate", "05/08/2017"); // MM/dd/yyyy
        params.put("toDate", "05/10/2017");
        params.put("countryDetails", 1);
        params.put("nepToken", nepFlightToken);
        params.put("flightMode", "R");
        params.put("fromLocation", "KTM");
        params.put("toLocation", "PKR");
        params.put("adults[2]", 2);
        params.put("child[2]", 2);
        params.put("parentTask", "nepFlight");
        params.put("childTask", "eachFlightDetails");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());

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

