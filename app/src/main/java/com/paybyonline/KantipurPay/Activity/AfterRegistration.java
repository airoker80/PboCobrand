package com.paybyonline.KantipurPay.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Adapter.Model.UserCountry;
import com.paybyonline.KantipurPay.Offline.GenerateJsonData;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;
import com.paybyonline.KantipurPay.usersession.UserDeviceDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class AfterRegistration extends AppCompatActivity {

    private UserDeviceDetails userDeviceDetails;
    private MyUserSessionManager myUserSessionManager;
    private ShowMyAlertProgressDialog showMyAlertProgressDialog;
    private CoordinatorLayout coordinatorLayout;
    private PboServerRequestHandler handler;
    private List<UserCountry> userCountryList = new ArrayList<UserCountry>();
    private LinkedHashMap<String,String> countryMap = new LinkedHashMap<String,String>();
    private LinkedHashMap<String,String> countryAdministrativeDistrictMap = new LinkedHashMap<String,String>();
    private Spinner timezoneSpinner;
    private Spinner countrySpinner;
    private String userCurrentTimezone;
    private Boolean spinnerCalledWhileLoading = true;
    private LinearLayout administrativeDistrictsLayout;
    private Boolean calledFromCountry;
    private String selectedCountry = "";
    private Button btnContinueCountrySelection;
    private EditText firstNameEditTxt;
    private EditText lastNameEditTxt;
    private EditText mobileNoEditTxt;
    private EditText partnerCodeEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_registration);

        userDeviceDetails = new UserDeviceDetails(AfterRegistration.this);
        myUserSessionManager = new MyUserSessionManager(AfterRegistration.this);
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(AfterRegistration.this);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        timezoneSpinner = (Spinner)findViewById(R.id.timezoneSpinner);
        countrySpinner = (Spinner)findViewById(R.id.countrySpinner);
        administrativeDistrictsLayout = (LinearLayout) findViewById(R.id.administrativeDistrictsLayout);
        firstNameEditTxt = (EditText) findViewById(R.id.firstNameEditTxt);
        lastNameEditTxt = (EditText) findViewById(R.id.lastNameEditTxt);
        mobileNoEditTxt = (EditText) findViewById(R.id.mobileNoEditTxt);
        partnerCodeEditTxt = (EditText) findViewById(R.id.partnerCodeEditTxt);
        btnContinueCountrySelection = (Button)findViewById(R.id.btnContinueCountrySelection);
        btnContinueCountrySelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateFormData()){
                    handleContinueCountrySelectionClick();
                }

            }
        });

        userCurrentTimezone  = TimeZone.getDefault().getID();
        Intent intent=getIntent();
        obtainCountrySelectionDetails();
        getUserTimezone();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.after_registration_menu, menu);
         return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                final android.support.v7.app.AlertDialog builder
                        = new android.support.v7.app.AlertDialog.Builder(this)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("CANCEL", null)
                        .setMessage("Are You Sure To Logout ?")
                        .create();

                builder.setTitle("Confirmation");

                builder.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                        final Button btnAccept = builder.getButton(android.support.v7.app.
                                AlertDialog.BUTTON_POSITIVE);

                        btnAccept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                builder.dismiss();
                                myUserSessionManager.logoutUser();
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
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public Boolean validateFormData(){

        if(firstNameEditTxt.getText().toString().isEmpty()){
            firstNameEditTxt.setError("Required");
            return false;
        }
        if(lastNameEditTxt.getText().toString().isEmpty()){
            lastNameEditTxt.setError("Required");
            return false;
        }
        if(mobileNoEditTxt.getText().toString().isEmpty()){
            mobileNoEditTxt.setError("Required");
            return false;
        }

        int childCount = administrativeDistrictsLayout.getChildCount();
        if (childCount>0){
            for(int i=0; i<childCount; i++) {
                Spinner spinner = (Spinner) administrativeDistrictsLayout.getChildAt(i);
                if (spinner.getSelectedItem().toString().equals("--Choose--")){
                    showMyAlertProgressDialog.showShortToast("Administrative District Required");
                    return false;
                }

            }
        }

        return true;
    }

    public void handleContinueCountrySelectionClick(){

        String parentId = "";
        String parentName = "";
        int childCount = administrativeDistrictsLayout.getChildCount();
        if(childCount>0){

            Spinner spinner = (Spinner) administrativeDistrictsLayout.getChildAt(childCount-1);
            if(spinner.getSelectedItem().equals("Other")){
                Spinner spinner2 = (Spinner) administrativeDistrictsLayout.getChildAt(childCount-2);
                parentName = spinner2.getSelectedItem().toString();
            }else{
                parentName = spinner.getSelectedItem().toString();
            }
        }
        if (parentName.length()>0){
            parentId = countryAdministrativeDistrictMap.get(parentName);
        }


        firstNameEditTxt = (EditText) findViewById(R.id.firstNameEditTxt);
        lastNameEditTxt = (EditText) findViewById(R.id.lastNameEditTxt);
        mobileNoEditTxt = (EditText) findViewById(R.id.mobileNoEditTxt);
        partnerCodeEditTxt = (EditText) findViewById(R.id.partnerCodeEditTxt);

        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "setCountrySelectionDetails");
        params.put("parentId", parentId);
        params.put("country", selectedCountry);
        params.put("firstName", firstNameEditTxt.getText().toString());
        params.put("lastName", lastNameEditTxt.getText().toString());
        params.put("mobileNo", mobileNoEditTxt.getText().toString());
        params.put("partnerCode", partnerCodeEditTxt.getText().toString());
        params.put("timeZoneSelect", timezoneSpinner.getSelectedItem().toString());
        params.put("userEmail", myUserSessionManager.getUsername());
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());

        handler = PboServerRequestHandler.getInstance(coordinatorLayout,AfterRegistration.this);

        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleContinueCountrySelectionResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void handleContinueCountrySelectionResponse(JSONObject response) throws JSONException{
        if (response.getString("msgTitle").equals("Success")){
            myUserSessionManager.addUserCountry();
            startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
            finish();
        }
    }

    public void getUserTimezone(){

        String data= GenerateJsonData.getJsonData(getApplicationContext(), R.raw.user_timezone_list);

        try {
            JSONArray timezoneArray = new JSONArray(data);

            if(timezoneArray.length()>0){
                List<String> list = new ArrayList<String>();
                for(int i=0;i<timezoneArray.length();i++) {
                    String timezone = timezoneArray.getString(i);
                    list.add(timezone);
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                timezoneSpinner.setAdapter(dataAdapter);

                if (!userCurrentTimezone.equals(null)) {
                    if(userCurrentTimezone.equals("Asia/Katmandu")){
                        userCurrentTimezone = "Asia/Kathmandu";
                    }
                    int spinnerPosition = dataAdapter.getPosition(userCurrentTimezone);
                    timezoneSpinner.setSelection(spinnerPosition);
                }

            }

            }catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void obtainCountrySelectionDetails() {

        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "getCountrySelectionDetails");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        handler = PboServerRequestHandler.getInstance(coordinatorLayout,AfterRegistration.this);

        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleObtainCountrySelectionDetails(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void handleObtainCountrySelectionDetails(JSONObject response) throws JSONException{

        try {

            JSONArray countryArray = response.getJSONArray("countryList");

            if(countryArray.length()>0){
                List<String> list = new ArrayList<String>();
                userCountryList = new ArrayList<UserCountry>();

                for(int i=0;i<countryArray.length();i++) {

                    JSONObject object = countryArray.getJSONObject(i);
                    userCountryList.add(new UserCountry(object.getString("id"),object.getString("countryName")));
                    list.add(object.getString("countryName"));
                    countryMap.put(object.getString("countryName"),object.getString("id"));
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                countrySpinner.setAdapter(dataAdapter);

                String userCountryCode = userDeviceDetails.getUserCountry();
                if(userCountryCode!=null){
                    String country_code= GenerateJsonData.getJsonData(getApplicationContext(), R.raw.country_code);
                    try {
                        JSONObject countryCodeJsonObject = new JSONObject(country_code);
                        if(countryCodeJsonObject.has(userCountryCode)){
                            String userCountry = countryCodeJsonObject.getString(userCountryCode);
                            int spinnerPosition = dataAdapter.getPosition(userCountry);
                            if (spinnerPosition>=0){
                                countrySpinner.setSelection(spinnerPosition);
                                calledFromCountry = true;
                                selectedCountry = userCountry;
                                obtainCountryAdministrativeDetails(userCountry,"");
                            }
                        }
                    }catch (Exception ex){

                    }
                }

                countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v,
                                               int pos, long id) {
                        // TODO Auto-generated method stub

                        if (spinnerCalledWhileLoading){
                            spinnerCalledWhileLoading = false;
                        }else {

                            String selectedItem = parent.getItemAtPosition(pos).toString();
//                            showMyAlertProgressDialog.showShortToast(selectedItem);
                            calledFromCountry = true;
                            selectedCountry = selectedItem;
                            obtainCountryAdministrativeDetails(selectedItem,"");
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void obtainCountryAdministrativeDetails(String masterCountry, String parentId) {

        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "findIndividualDistrictDetails");
        params.put("masterCountry", masterCountry);
        params.put("parentId", parentId);
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        handler = PboServerRequestHandler.getInstance(coordinatorLayout,AfterRegistration.this);

        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleObtainCountryAdministrativeDetailsResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void handleObtainCountryAdministrativeDetailsResponse(JSONObject response) throws JSONException{

        if(calledFromCountry){
            administrativeDistrictsLayout.removeAllViews();
            countryAdministrativeDistrictMap = new LinkedHashMap<String,String>();
        }
        if(response.getString("flag").equals("1")){

            JSONArray data = response.getJSONArray("data");

            if (data.length()>0){

                final Spinner spinner = new Spinner(this);
                List<String> list = new ArrayList<String>();
                list.add("--Choose--");
                for (int i=0; i<data.length(); i++){

                    JSONObject object = data.getJSONObject(i);
                    list.add(object.getString("dName"));
                    countryAdministrativeDistrictMap.put(object.getString("dName"),object.getString("addId"));
                }
                list.add("Other");


                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(dataAdapter);
                spinner.setPadding(0,0,0,5);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v,
                                               int pos, long id) {
                        // TODO Auto-generated method stub

                        int childCount = administrativeDistrictsLayout.getChildCount();
                        int currentViewPosition = 0 ;
                        for(int i=0; i<childCount; i++) {
                            if(spinner==administrativeDistrictsLayout.getChildAt(i)){
                                currentViewPosition = i;
                            }
                        }

                        for (int i=(currentViewPosition+1);i<childCount;i++){
                            administrativeDistrictsLayout.removeViewAt(i);
                        }

                        String selectedItem = parent.getItemAtPosition(pos).toString();
                        if((!selectedItem.equals("--Choose--"))  &&  (!selectedItem.equals("Other"))){
//                            showMyAlertProgressDialog.showShortToast(selectedItem);
                            calledFromCountry = false;
                            obtainCountryAdministrativeDetails(selectedCountry,
                                    countryAdministrativeDistrictMap.get(selectedItem));
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });



                administrativeDistrictsLayout.addView(spinner);

            }
        }

    }

}
