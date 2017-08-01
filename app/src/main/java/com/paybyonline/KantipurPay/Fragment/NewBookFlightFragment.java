package com.paybyonline.KantipurPay.Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Model.FlightCountriesModel;
import com.paybyonline.KantipurPay.Adapter.Model.FlightSectorModel;
import com.paybyonline.KantipurPay.Adapter.Model.NationalityModel;
import com.paybyonline.KantipurPay.Adapter.Model.PlaceFlightModel;
import com.paybyonline.KantipurPay.Adapter.NationalityAdapter;
import com.paybyonline.KantipurPay.Adapter.PlaceFlightAdapter;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class NewBookFlightFragment extends Fragment implements View.OnClickListener {
    String nepFlightToken;
    TextView fromDate,toDate,selectedDate;
    String flightMode="R";
    CoordinatorLayout coordinatorLayout;
    private MyUserSessionManager myUserSessionManager;
    String place = "";
    int day, year, month;
    String date, date1;
    TextView setSelectedDateBtn, selectedWeekdays;
    Fragment fragment = null;
    boolean flag = false;
    String newvalue="0";
    ImageView flight_mode_icon;
    TextView oneway, roundtrip, place_frm, place_to, flight_date, return_date,
            adultNoTxt, childrenNoTxt, searchTxt, nationlity, nationlityHeading,
            adultHeading, childrenHeading, flightWeekdays, return_weekday,
            flight_place_name1,flight1,landing1,landing_place;
    RelativeLayout goneRll, goneRll1, searchFlight, adultRelativel, childRelativel;
    TextView departsTxt;
    ImageView searchFlightArrow;
    List<PlaceFlightModel> placeFlightModelList = new ArrayList<PlaceFlightModel>();
    List<NationalityModel> nationalityList = new ArrayList<NationalityModel>();
    public static Typeface typeFace,mediumBold;
    LinearLayout centerLL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_sdp, container, false);
        myUserSessionManager = new MyUserSessionManager(getActivity());

        setHasOptionsMenu(false);
        String dateToday = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String[] selDate = dateToday.split("/");
        int day =Integer.parseInt(selDate[2]);
        int month =Integer.parseInt(selDate[1])-1;
        int year=Integer.parseInt(selDate[0]);


        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String newFormatWeekdays = new DateFormatSymbols().getWeekdays()[calendar.get(Calendar.DAY_OF_WEEK)];
        String monthName = new DateFormatSymbols().getShortMonths()[month];
//        Log.d("testdate",newFormatWeekdays + " "+monthName+ " "+ day);

//        Log.e("testdate",day+" "+monthName+" "+" "+year);

/*       */

        ((DashBoardActivity) getActivity()).setTitle("Find Flights");

        ((DashBoardActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ((DashBoardActivity) getActivity()).shareMenu.setVisible(false);

        fromDate=new TextView(getContext());
        toDate=new TextView(getContext());

        fromDate.setText((month+1)+"/"+day+"/"+year);
        toDate.setText((month+1)+"/"+day+"/"+year);

        flight_mode_icon = (ImageView) view.findViewById(R.id.flight_mode_icon);
        oneway = (TextView) view.findViewById(R.id.onewayTxt);
        place_frm = (TextView) view.findViewById(R.id.place_frm);
        place_to = (TextView) view.findViewById(R.id.place_to);
        flight_date = (TextView) view.findViewById(R.id.flight_date);
        return_date = (TextView) view.findViewById(R.id.return_date);
        return_weekday = (TextView) view.findViewById(R.id.return_weekday);
        searchFlightArrow = (ImageView) view.findViewById(R.id.searchFlightArrow);
        roundtrip = (TextView) view.findViewById(R.id.roundTripTxt);

        goneRll = (RelativeLayout) view.findViewById(R.id.goneRll);
        goneRll1 = (RelativeLayout) view.findViewById(R.id.goneRll1);
        departsTxt = (TextView) view.findViewById(R.id.departsTxt);
        adultNoTxt = (TextView) view.findViewById(R.id.adultNoTxt);


        flight_place_name1 = (TextView) view.findViewById(R.id.flight_place_name1);
        flight1 = (TextView) view.findViewById(R.id.flight1);
        landing1 = (TextView) view.findViewById(R.id.landing1);
        landing_place = (TextView) view.findViewById(R.id.landing_place);
        flightWeekdays = (TextView) view.findViewById(R.id.flightWeekdays);
        childrenNoTxt = (TextView) view.findViewById(R.id.childrenNoTxt);
        nationlity = (TextView) view.findViewById(R.id.nationlity);
        searchTxt = (TextView) view.findViewById(R.id.searchTxt);
        adultHeading = (TextView) view.findViewById(R.id.adultHeading);
        childrenHeading = (TextView) view.findViewById(R.id.childrenHeading);
        nationlityHeading = (TextView) view.findViewById(R.id.nationalityHeading);
        searchFlight = (RelativeLayout) view.findViewById(R.id.searchFlight);
        adultRelativel = (RelativeLayout) view.findViewById(R.id.adultRelativel);
        childRelativel = (RelativeLayout) view.findViewById(R.id.childRelativel);
        centerLL = (LinearLayout) view.findViewById(R.id.centerLL);

        flight_date.setText(String.valueOf(day) + " " + monthName);
        return_date.setText(String.valueOf(day) + " " + monthName);
        flightWeekdays.setText(newFormatWeekdays + "\n" + year);
        return_weekday.setText(newFormatWeekdays + "\n" + year);

        typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-ExtraBold.ttf");
         mediumBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Bold.ttf");
        Typeface semiBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-SemiBold.ttf");

        place_to.setTypeface(typeFace);
        place_frm.setTypeface(typeFace);
        flight_date.setTypeface(typeFace);
        return_date.setTypeface(typeFace);
        adultNoTxt.setTypeface(typeFace);
        childrenNoTxt.setTypeface(typeFace);
        searchTxt.setTypeface(mediumBold);
        nationlity.setTypeface(typeFace);
        adultHeading.setTypeface(semiBold);
        childrenHeading.setTypeface(semiBold);
        nationlityHeading.setTypeface(semiBold);

        searchFlight.setOnClickListener(this);
        searchTxt.setOnClickListener(this);
        searchFlightArrow.setOnClickListener(this);
        flight_date.setOnClickListener(this);
        return_date.setOnClickListener(this);
        return_weekday.setOnClickListener(this);
        place_frm.setOnClickListener(this);
        place_to.setOnClickListener(this);
        nationlity.setOnClickListener(this);

        adultRelativel.setOnClickListener(this);
        childRelativel.setOnClickListener(this);


        obtainFlightDetails();
/*        for (int i =0 ; i<10;i++){
            placeFlightModelList.add(new PlaceFlightModel("Kathmandu","KTM","Nepal"));
            placeFlightModelList.add(new PlaceFlightModel("Pokhara","PKR","Nepal"));
        }*/
/*        for (int i =0 ; i<10;i++){
            nationalityList.add(new NationalityModel("Nepalese","np"));
            nationalityList.add(new NationalityModel("American","USA"));
        }*/
        oneway.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                oneway.setTextColor(Color.parseColor("#b20c6093"));
                oneway.setBackgroundResource(R.drawable.flight_btn_blue_drawable);
                roundtrip.setBackgroundResource(R.drawable.flight_btn_transparent_right);
                roundtrip.setTextColor(Color.WHITE);
                flight_mode_icon.setImageResource(R.drawable.ic_oneway);
                goneRll.setVisibility(View.GONE);
                goneRll1.setVisibility(View.GONE);
                flightMode= "O";
                centerLL.setGravity(Gravity.CENTER);
                flag = true;
            }
        });

        roundtrip.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                roundtrip.setTextColor(Color.parseColor("#b20c6093"));
                roundtrip.setBackgroundResource(R.drawable.flight_btn_white_drawable);
                oneway.setBackgroundResource(R.drawable.flight_btn_transparent_left);
                oneway.setTextColor(Color.WHITE);
                flight_mode_icon.setImageResource(R.drawable.ic_2way);
                goneRll.setVisibility(View.VISIBLE);
                goneRll1.setVisibility(View.VISIBLE);
                centerLL.setGravity(Gravity.START);
                flightMode="R";
                flag = false;
            }
        });
        return view;
    }
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
            if (response.toString()==""){

            }
        JSONArray countryDetails = response.getJSONArray("countryDetails");
        JSONArray sectorList = response.getJSONArray("sectorList");
        nepFlightToken = response.getString("nepFlightToken");
//        Toast.makeText(getContext(),nepFlightToken,Toast.LENGTH_LONG).show();
        for (int j=0;j<countryDetails.length();j++){
            JSONObject countryDetailsObject = countryDetails.getJSONObject(j);

            String countryCode = countryDetailsObject.getString("countryCode");
            String countryName = countryDetailsObject.getString("countryName");
            nationalityList.add(new NationalityModel(countryName,countryCode));
            String currencyName = countryDetailsObject.getString("currencyName");
            String currencySymbol = countryDetailsObject.getString("currencySymbol");
            String isServiceProviding = countryDetailsObject.getString("isServiceProviding");
        }
        for (int k=0;k<sectorList.length();k++){
            JSONObject sectorDetailsObject = sectorList.getJSONObject(k);

            String SectorName = sectorDetailsObject.getString("SectorName");
            String SectorCode = sectorDetailsObject.getString("SectorCode");
            placeFlightModelList.add(new PlaceFlightModel(SectorName,SectorCode,"Nepal"));
            //sectorCodeList.add(SectorName);
        }
    }
    public void checkFlagAndBook() {
        fragment = new RoundTripFragment();
        Bundle args = new Bundle();
        args.putString("flag",String.valueOf(flag));
        args.putString("nepFlightToken",String.valueOf(nepFlightToken));
        args.putString("fromDate",String.valueOf(fromDate.getText()));
        args.putString("toDate",String.valueOf(toDate.getText()));
        args.putString("flight_date",String.valueOf(flight_date.getText()));
        args.putString("return_date",String.valueOf(return_date.getText()));
        args.putString("from_day",String.valueOf(flightWeekdays.getText()));
        args.putString("to_day",String.valueOf(return_weekday.getText()));
        args.putString("flight_type",String.valueOf(flightMode));
        args.putString("adults",String.valueOf(adultNoTxt.getText()));
        args.putString("childs",String.valueOf(childrenNoTxt.getText()));
        args.putString("place_frm",String.valueOf(place_frm.getText()));
        args.putString("place_to",String.valueOf(place_to.getText()));
        args.putString("place_frm_name",String.valueOf(flight_place_name1.getText()));
        args.putString("place_to_name",String.valueOf(landing_place.getText()));
        args.putString("nationality","np");
        args.putString("nepFlightToken",nepFlightToken);
        Log.d("bundle",args.toString());
        fragment.setArguments(args);
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        flag=false;
        flightMode="R";
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.flight_date:
                showDatePicker(flight_date, flightWeekdays,fromDate);
                break;
            case R.id.return_date:
                showDatePicker(return_date, return_weekday,toDate);
                break;
            case R.id.place_frm:
                cretePlaceDialog(place_frm,"Select Place", placeFlightModelList,flight_place_name1,flight1);
                break;
            case R.id.place_to:
                cretePlaceDialog(place_to,"Select Place", placeFlightModelList,landing_place,landing1);
                break;
            case R.id.adultRelativel:
               // cretePassengerDialog(adultNoTxt);
                final String array_adult[] = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9"};
                 buildNumberPicker(adultNoTxt, "Select Number Of Adult", array_adult);

                  break;
            case R.id.childRelativel:
                //cretePassengerDialog(childrenNoTxt);
                final String array_children[] = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9"};
                buildNumberPicker(childrenNoTxt, "Select Number Of Children", array_children);
                break;
            case R.id.searchFlight:
                checkFlagAndBook();
                break;
            case R.id.searchTxt:
                checkFlagAndBook();
                break;
            case R.id.searchFlightArrow:
                checkFlagAndBook();
                break;
            case R.id.nationlity:
                HashSet<NationalityModel> nationalityModels = new HashSet<NationalityModel>();
                nationalityModels.addAll(nationalityList);
                    nationalityList.clear();

                nationalityList.addAll(nationalityModels);
              creteNationalityDialog(nationlity,"Select Nationality", nationalityList);
                break;
        }


    }

//    public void setDialogeBaground(final String filterName, final LinearLayout filterList) {
//        final ViewGroup parent = (ViewGroup) filterList.getParent();
//        final ScrollView scrollView = new ScrollView(getContext());
//        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        if (parent != null) {
//            parent.removeView(filterList);
//        }
//        scrollView.addView(filterList);
//        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//                .setView(scrollView)
//                .setTitle(filterName)
//                .setPositiveButton("Update", null)
//                .setNegativeButton("Cancel", null)
//                .create();
//        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                final Button buttonPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                buttonPositive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        scrollView.removeView(filterList);
//                        alertDialog.dismiss();
//                        View view = new View(getContext());
////                        checkNoticeFilterOptions(view,filterName,filterList);
//                    }
//                });
//                final Button buttonNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
////                alertDialog.dismiss();
//                buttonNegative.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        scrollView.removeView(filterList);
//                        alertDialog.dismiss();
//                    }
//                });
//
//            }
//        });
//        alertDialog.show();
//    }


    public void setCurrentDateOnView(Button fromToBtn, String calDate) {

        if (calDate.length() > 0) {

//           String[] lastPostDateVal = lastPostDate.split("-");
            fromToBtn.setText(calDate);

        } else {

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

    void showDatePicker(TextView fromToBtn, TextView flightWeekdays,TextView flightft) {
        selectedDate =flightft;
        setSelectedDateBtn = fromToBtn;
        selectedWeekdays = flightWeekdays;
        DatePickerFragment date = new DatePickerFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        String dateToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] selDate = dateToday.split("-");

        args.putInt("year", Integer.parseInt(selDate[0]));
        args.putInt("month", Integer.parseInt(selDate[1]) - 1);
        args.putInt("day", Integer.parseInt(selDate[2]));
        date.setArguments(args);
//        flightft=String.valueOf(Integer.parseInt(selDate[1])) +"-"+String.valueOf(Integer.parseInt(selDate[2]))+"-"+ String.valueOf(Integer.parseInt(selDate[0]));

        date.setCallBack(ondate);

        date.show(getActivity().getSupportFragmentManager(), "Date Picker");


    }

    DatePickerDialog.OnDateSetListener ondate =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    selectedDate.setText(String.valueOf(monthOfYear +1) +"/"+String.valueOf(dayOfMonth)+"/"+ String.valueOf(year));
                    Log.d("dateSelected","");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    String newFormatWeekdays = new DateFormatSymbols().getWeekdays()[calendar.get(Calendar.DAY_OF_WEEK)];
                    String month = new DateFormatSymbols().getShortMonths()[monthOfYear];
/*                    int month = (monthOfYear + 1);
                    String monthString = MONTHS[month];*/
                    setSelectedDateBtn.setText(dayOfMonth + " " + month);
                    selectedWeekdays.setText(newFormatWeekdays + "\n" + year);
                    Log.i("weeklday", newFormatWeekdays);

                }
            };





    public void buildNumberPicker(final TextView textView, String title, final String array[]) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(title);
        newvalue="0";

        final NumberPicker np = new NumberPicker(getActivity());
        NumberPicker.LayoutParams params1 = new NumberPicker.LayoutParams(
                NumberPicker.LayoutParams.WRAP_CONTENT, NumberPicker.LayoutParams.WRAP_CONTENT, 1f);
        np.setLayoutParams(params1);



        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(array.length - 1);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setDisplayedValues(array);
        np.setFocusable(false);
        np.setFocusableInTouchMode(false);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int pos = np.getValue();

                Log.d("NewValue", String.valueOf(newVal)+ "------NV");
                //Display the newly selected number from picker
                    newvalue = array[newVal];
                newVal=0;
            }
        });

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("newValueasda",newvalue);
                textView.setText(newvalue);
                np.setValue(0);



            }
        });


        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        linearLayout.addView(np);
        alertDialog.setView(linearLayout);
        alertDialog.setCancelable(false);
        alertDialog.create();
        alertDialog.show();
        np.setValue(0);

    }
    public void cretePlaceDialog(final TextView setPlaceText, String title, List<PlaceFlightModel> placeFlightModelList,final TextView setPlaceText2,final TextView setPlaceText3){

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setView(linearLayout)
                .setTitle(title)
                .create();
        View view = new View(getContext());
        view.setBackgroundColor(Color.BLACK);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        RecyclerView recyclerView = new RecyclerView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,8,0,0);
        recyclerView.setLayoutParams(params);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                /*recyclerView.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.));*/
        PlaceFlightAdapter placeFlightAdapter = new PlaceFlightAdapter(setPlaceText,setPlaceText2,setPlaceText3,getActivity(),placeFlightModelList,builder);
        recyclerView.setAdapter(placeFlightAdapter);
        linearLayout.addView(view);
        linearLayout.addView(recyclerView);

        builder.show();
    }
    public void creteNationalityDialog(final TextView setPlaceText, String title, List<NationalityModel> nationalityModelList){

        LinearLayout linearLayout = new LinearLayout(getContext());
//        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setView(linearLayout)
                .setTitle(title)
                .create();
        View view = new View(getContext());
        view.setBackgroundColor(Color.BLACK);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        RecyclerView recyclerView = new RecyclerView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(0,8,0,0);
        recyclerView.setLayoutParams(params);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                /*recyclerView.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.));*/
        NationalityAdapter nationalityAdapter = new NationalityAdapter(setPlaceText,getActivity(),nationalityModelList,builder);
        recyclerView.setAdapter(nationalityAdapter);
        linearLayout.addView(view);
        linearLayout.addView(recyclerView);

        builder.show();
    }
    public void creteNationalityDialogNew(final TextView setPlaceText, String title, List<NationalityModel> nationalityModelList){

        LinearLayout linearLayout = new LinearLayout(getContext());
//        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setView(linearLayout)
                .setTitle(title)
                .create();
        View view = new View(getContext());
        view.setBackgroundColor(Color.BLACK);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        RecyclerView recyclerView = new RecyclerView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(0,8,0,0);
        recyclerView.setLayoutParams(params);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                /*recyclerView.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.));*/
        NationalityAdapter nationalityAdapter = new NationalityAdapter(setPlaceText,getActivity(),nationalityModelList,builder);
        recyclerView.setAdapter(nationalityAdapter);
        linearLayout.addView(view);
        linearLayout.addView(recyclerView);

        builder.show();
    }
}
