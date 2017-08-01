package com.paybyonline.KantipurPay.Fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;
import com.paybyonline.KantipurPay.usersession.UserDeviceDetails;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
/**
 * A simple {@link Fragment} subclass.
 */
public class DebitCardFragment extends Fragment {

    CoordinatorLayout coordinatorLayout;
    EditText card_no;
    EditText cvc_no;
    EditText payment_note;
    EditText profile_name;
    CheckBox chkSave;
    CheckBox chkAgree;
    Button paynowBtn;
    Button id_exdate;
    Token stripeToken;
    Bundle bundle;
    TextView amtVal;
    private MyUserSessionManager myUserSessionManager;
    DecimalFormat formatter = new DecimalFormat("0.00");
    private UserDeviceDetails userDeviceDetails;
    private ShowMyAlertProgressDialog showMyAlertProgressDialog;
    private HashMap<String, String> userDetails;
    public  String PUBLISHABLE_KEY;
    String errorMessage ="";
    String yearDate;
    String monthOfYearDate;
    Integer selectedUserPayTypeIndex = -1;
    String payTypeIds[];

    TextView profile_name_title;

    public DebitCardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_debit_card, container, false);

        PUBLISHABLE_KEY = PayByOnlineConfig.STRIPE_PUBLISHABLE_KEY;


        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        // cardSpin=(Spinner)view.findViewById(R.id.cardSpin);
        card_no=(EditText)view.findViewById(R.id.card_no);
        amtVal=(TextView)view.findViewById(R.id.amtVal);
        // exp_date=(EditText)view.findViewById(R.id.exp_date);
        id_exdate=(Button)view.findViewById(R.id.exp_date);
        cvc_no=(EditText)view.findViewById(R.id.cvc_no);
        payment_note=(EditText)view.findViewById(R.id.payment_note);
        profile_name=(EditText)view.findViewById(R.id.profile_name);
        chkSave=(CheckBox)view.findViewById(R.id.chkSave);
        chkAgree=(CheckBox)view.findViewById(R.id.chkAgree);
        paynowBtn=(Button)view.findViewById(R.id.paynowBtn);
        myUserSessionManager = new MyUserSessionManager(getActivity());
        userDetails = myUserSessionManager.getUserDetails();
        userDeviceDetails = new UserDeviceDetails(getActivity());
        showMyAlertProgressDialog=new ShowMyAlertProgressDialog(getActivity());
        bundle=getArguments();

        Log.i("bundleData",""+bundle);
        amtVal.setText(bundle.getString("amtPayingVal"));
        setCurrentDateOnView(id_exdate);

        id_exdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(id_exdate);
            }
        });

        paynowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCardInformation();
            }
        });

        profile_name_title=(TextView)view.findViewById(R.id.profile_name_title);
        profile_name_title.setVisibility(View.GONE);
        profile_name.setVisibility(View.GONE);

        chkSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    profile_name_title.setVisibility(View.VISIBLE);
                    profile_name.setVisibility(View.VISIBLE);
                }else{
                    profile_name_title.setVisibility(View.GONE);
                    profile_name.setVisibility(View.GONE);

                }
            }
        });

        return view;
    }
    public void setCurrentDateOnView(Button fromToBtn) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        fromToBtn.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(year).append("-").append(month + 1).append("-")
                .append(day));

    }

    private void showDatePicker(final Button fromToBtn) {
        id_exdate=fromToBtn;
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        String[] selDate = fromToBtn.getText().toString().split("-");
        args.putInt("year", Integer.parseInt(selDate[0]));
        args.putInt("month", Integer.parseInt(selDate[1]) - 1);
        args.putInt("day", Integer.parseInt(selDate[2]));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);

        date.show(getActivity().getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            id_exdate.setText(String.valueOf(year) + "-"
                    + String.valueOf(monthOfYear + 1) + "-"
                    + String.valueOf(dayOfMonth));
            yearDate=String.valueOf(year);
            monthOfYearDate =String.valueOf(monthOfYear+1);
        }
    };


    public String getCardNumber() {
        return this.card_no.getText().toString();
    }
    public String getCvc() {
        return this.cvc_no.getText().toString();
    }
    public Integer getExpDate() {
        return Integer.parseInt(id_exdate.getText().toString());
    }
    public Integer getExpYearDate() {

        return Integer.parseInt(yearDate.toString());

    }
    public Integer getExpMonthDate() {
        return  Integer.parseInt(monthOfYearDate.toString());
    }
    public  void processCardInformation(){

        Boolean result = validateFormData();

        if(result){

            Card card = new Card(
                    getCardNumber(),
                    getExpMonthDate(),
                    getExpYearDate(),
                    getCvc());

            Log.i("CardFormat",getCardNumber()+" "+getExpMonthDate()+" "+getExpYearDate()+" "+getCvc());
            boolean validation = card.validateCard();
            if (validation) {

                showMyAlertProgressDialog.showProgressDialog("Processing...",
                        "Please wait.");
                new Stripe(getContext()).createToken(
                        card,
                        PUBLISHABLE_KEY,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                showMyAlertProgressDialog.hideProgressDialog();
                                showConfirmOrderFormCreditDebit(token);

                            }
                            public void onError(Exception error) {
                                showMyAlertProgressDialog.hideProgressDialog();
                                userDeviceDetails
                                        .showToast(error.getLocalizedMessage());
                            }
                        });
            } else if (!card.validateNumber()) {
                userDeviceDetails
                        .showToast("The card number that you entered is invalid");
            } else if (!card.validateExpiryDate()) {
                userDeviceDetails
                        .showToast("The expiration date that you entered is invalid");
            } else if (!card.validateCVC()) {
                userDeviceDetails
                        .showToast("The CVC code that you entered is invalid");
            } else {
                userDeviceDetails
                        .showToast("The card details that you entered are invalid");
            }

        }else{
            userDeviceDetails
                    .showToast(errorMessage);
        }


    }
    public Boolean validateFormData(){



       /* if(selectedUserPayTypeIndex>0){
            errorMessage = "Please Select Payment Method";
            return  false;
        }*/


        if(!(getCardNumber().length()>0)){
            errorMessage = "Please Enter Card Number";
            return  false;
        }
        if(getExpMonthDate()==0){
            errorMessage = "Please Enter Card Expiry Month";
            return  false;
        }
        if(getExpYearDate()==0){
            errorMessage = "Please Enter Card Expiry Year";
            return  false;
        }
        if(!(getCvc().length()>0)){
            errorMessage = "Please Enter CVC Number";
            return  false;
        }

        if(chkSave.isChecked() && !(profile_name.getText().toString().length()>0)){
            errorMessage = "Please Enter Profile Name";
            return  false;
        }

        if(!chkAgree.isChecked()){
            errorMessage = "Please Agree Payment Terms";
            return  false;
        }

        return true;
    }
    public void showConfirmOrderFormCreditDebit(Token token){

        showMyAlertProgressDialog.showProgressDialog("Processing...",
                "Please wait.");

        stripeToken = token;


        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "showConfirmOrderFormCreditDebit");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        // params.put("payUsingIds", payTypeIds[selectedUserPayTypeIndex]);
        params.put("payUsingIds", "9");
        params.put("amtPayingVal", bundle.getString("amtPayingVal"));

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Obtaining Confirm Order",
                params, new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            handleShowConfirmOrderFormCreditDebit(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                    }
                });

    }
    public void handleShowConfirmOrderFormCreditDebit(JSONObject response) throws JSONException{

        // userDeviceDetails.showToast(response);
        showMyAlertProgressDialog.hideProgressDialog();
        try {
            JSONObject jo = response;

            Log.i("data:", "" + jo);

            // SharedPreferences pref = getSharedPreferences("PAYMENTDETAILS", 0);


            Bundle infoBundle=new Bundle();
            infoBundle.putString("username", userDetails.get(MyUserSessionManager.KEY_USERNAME).toString());
            infoBundle.putString("payUsingIds", jo.get("userPayTypesIds").toString());
            infoBundle.putString("confirmPayUsings", jo.get("confirmPayUsings").toString());
            infoBundle.putString("confirmPaymentNotes",payment_note.getText().toString());
            infoBundle.putString("confirmPurchasedAmount", bundle.getString("purchasedAmt"));
            infoBundle.putString("confirmDiscountAmount", bundle.getString("disAmt"));
            infoBundle.putString("confirmDepositingAmount",bundle.getString("walletDepositingVal") );
            infoBundle.putString("confirmPayingAmount",bundle.getString("amtPayingVal") );
            infoBundle.putString("totalAmt",  bundle.getString("totalAmt"));
            infoBundle.putString("stripeToken",stripeToken.getId());
            infoBundle.putString("payAmt", bundle.getString("amtPayingVal"));
            infoBundle.putString("addDiscountWallet",bundle.getString("addDiscountWallet"));

            if(profile_name.getText().toString().length() >0){

                infoBundle.putString("confirmPaymentProfileNames", profile_name.getText().toString());

            }else{

                infoBundle.putString("confirmPaymentProfileNames", "");

            }

            //   editor.putString("payUsingIds", payTypeIds[selectedUserPayTypeIndex]);
            infoBundle.putString("userIp", userDeviceDetails.getLocalIpAddress());
            infoBundle.putString("card","************"+stripeToken.getCard().getLast4());
            infoBundle.putString("expiryDate", getExpMonthDate() + "/" + getExpYearDate());

            String profileName=null;
            if(profile_name.getText().toString().length()>0){

                profileName= profile_name.getText().toString();

            }else{

                profileName="-";
            }
            Double confirmTotalAmount = Double.parseDouble(bundle.getString("amtPayingVal", ""))
                    +Double.parseDouble(jo.get("payTypeAmount").toString());
            Double confirmTotalAmountUSD = confirmTotalAmount/Double.parseDouble(jo.get("userExchangeRates").toString());
            Double amtPayingVal = Double.parseDouble(bundle.getString("amtPayingVal"));
            String confirmOrderText =
                    "<br>Card No : ************\n" + stripeToken.getCard().getLast4()+
                    "<br>CVC : ***\n" +
                    "<br>Expiration : " + getExpMonthDate()+"/"+getExpYearDate()+"<br>" +
                    "<br>Payment Amount : " +formatter.format(amtPayingVal)+
                    "<br>Payment Type " +jo.get("payTypeHeading").toString()+
                    "<br>Value : " +jo.get("payTypeValue").toString()+
                    "<br>Amount :\n" +formatter.format( Double.parseDouble(jo.get("payTypeAmount").toString()))+"<br>"+
                    "<br>Net Payment Amount: " +formatter.format(confirmTotalAmount)+
                    "<br>Payment Gateway : " +jo.get("paymentGateway").toString()+
                    "<br>Payment Profile Name:"+"&nbsp;"+profileName+
                    "<br>Payment Method : " +jo.get("payTypeMethod").toString()+
                    "<br>Payment Currency : " +jo.get("paymentCurrency").toString()+
                    "<br>Exchange Rate : " +jo.get("userExchangeRates").toString()+
                    "<br>Paying Amount : USD "+formatter.format(confirmTotalAmountUSD);


            Fragment fragment = new ConfirmOrderFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            infoBundle.putString("requestFrom", "CreditDebitCard");
            infoBundle.putString("confirmOrderText", confirmOrderText);
            fragment.setArguments(infoBundle);
            fragmentTransaction.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void obtainUserPaymentType(){

        showMyAlertProgressDialog.showProgressDialog("Processing...",
                "Please wait.");




        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "obtainUserPaymentType");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        //  params.put("payMethod", bundle.getString("payMethod", ""));
        params.put("payMethod", "Debit Card");

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest("authenticateUser", "Please Wait !!!", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleObtainUserPaymentType(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });

    }
    public void handleObtainUserPaymentType(JSONObject response)throws JSONException{
        try {
            Log.i("response:",""+response);
            showMyAlertProgressDialog.hideProgressDialog();
            //  JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray =response.getJSONArray("dataList");
            if (jsonArray.length() != 0) {
                payTypeIds = new String[jsonArray.length()];
                // Loop through each array element, get JSON object
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    payTypeIds[i]= obj.get("id").toString();
                    if(i==0){
                        selectedUserPayTypeIndex = 0;
                    }

                    RadioButton button = new RadioButton(getContext());
                    button.setId(i);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((RadioGroup) view.getParent()).check(view.getId());
                            // userDeviceDetails.showToast(view.getId() + "");
                            selectedUserPayTypeIndex = view.getId();
                        }
                    });

                    button.setText(obj.get("userPayName").toString());

                    button.setChecked(i == 0);

                    //creditTypeIconRadio.addView(button);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

   /* public void showConfirmOrderFormCreditDebit(Token token){
        showMyAlertProgressDialog.showProgressDialog("Processing...",
                "Please wait.");

        stripeToken = token;

        SharedPreferences pref = getSharedPreferences("PAYMENTDETAILS", 0);
        SharedPreferences pref1 = getSharedPreferences("USERPAYPROVIDERDETAILS", 0);


        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "showConfirmOrderFormCreditDebit");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        // params.put("payUsingIds", payTypeIds[selectedUserPayTypeIndex]);
        params.put("payUsingIds",  pref1.getString("payTypeIds", ""));
        params.put("amtPayingVal", pref.getString("amtPayingVal", ""));
        // Make Http call
        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest("authenticateUser", "Obtaining Confirm Order", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleShowConfirmOrderFormCreditDebit(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void handleShowConfirmOrderFormCreditDebit(JSONObject response) throws JSONException {

        // userDeviceDetails.showToast(response);
        showMyAlertProgressDialog.hideProgressDialog();
        try {
            JSONObject jo = response;

            Log.i("data:", "" + jo);

            SharedPreferences pref = getSharedPreferences("PAYMENTDETAILS", 0);
            SharedPreferences finalPayData = getSharedPreferences("PAYMENTDETAILSFINAL", 0);
            SharedPreferences.Editor editor = finalPayData.edit();
            editor.clear();
            editor.commit();

            editor = finalPayData.edit();

     *//*       *//**//**//**//*[confirmPayUsing:userPayType.paymentTypeCode,payTypeHeading:disComm
            * // Type?(disCommType=="Commission")?"Service Charge":"Discount":"",
            payTypeValue:disCommValue,payTypeAmount:userToPayAmt,confirmPayUsings:userPayType.paymentTypeCode,
                    paymentGateway:userPayType.name,payTypeMethod:userPayType.payTypeMethod,paymentCurrency:"USD",userExchangeRates:exchangeRate[1],
                    userCountryStatus:exchangeRate[0],userPayTypesIds:userPayType.id]*//**//**//**//**//*
            editor.putString("username", userDetails.get(MyUserSessionManager.KEY_USERNAME).toString());
            editor.putString("payUsingIds", jo.get("userPayTypesIds").toString());
            editor.putString("confirmPayUsings", jo.get("confirmPayUsings").toString());
            editor.putString("confirmPaymentNotes", profile_name.getText().toString());
            editor.putString("confirmPurchasedAmount", pref.getString("purchasedAmt", ""));
            editor.putString("confirmDiscountAmount", pref.getString("disAmt", ""));
            editor.putString("confirmDepositingAmount",pref.getString("walletDepositingVal", "") );
            editor.putString("confirmPayingAmount",pref.getString("amtPayingVal", "") );
            editor.putString("totalAmt",  pref.getString("totalAmt", ""));
            editor.putString("stripeToken",stripeToken.getId());
            editor.putString("payAmt", pref.getString("amtPayingVal", ""));
            editor.putString("addDiscountWallet",pref.getString("addDiscountWallet", ""));
            if(profile_name.getText().toString().length()>0){
                editor.putString("confirmPaymentProfileNames",profile_name.getText().toString());
            }else{
                editor.putString("confirmPaymentProfileNames", "");
            }

            //   editor.putString("payUsingIds", payTypeIds[selectedUserPayTypeIndex]);
            editor.putString("userIp", userDeviceDetails.getLocalIpAddress());
            editor.putString("card","************"+stripeToken.getCard().getLast4());
            editor.putString("expiryDate",getExpMonthDate()+"/"+getExpYearDate());

            editor.commit();
            String profileName=null;
            if(profile_name.getText().toString().length()>0){
                profileName= profile_name.getText().toString();
            } else{
                profileName="-";
            }
            Double confirmTotalAmount = Double.parseDouble(pref.getString("amtPayingVal", ""))+Double.parseDouble(jo.get("payTypeAmount").toString());
            Double confirmTotalAmountUSD = confirmTotalAmount/Double.parseDouble(jo.get("userExchangeRates").toString());
            Double amtPayingVal = Double.parseDouble(pref.getString("amtPayingVal", ""));
            String confirmOrderText = "<b>Payment Details</b><br>\n" +
                    "<br>Card No : ************\n" + stripeToken.getCard().getLast4()+
                    "<br>CVC : ***\n" +
                    "<br>Expiration : " + getExpDate()+"<br>" +
                    "<br>Payment Amount : " +formatter.format(amtPayingVal)+
                    "<br>Payment Type " +jo.get("payTypeHeading").toString()+
                    "<br>Value : " +jo.get("payTypeValue").toString()+
                    "<br>Amount :\n" +formatter.format( Double.parseDouble(jo.get("payTypeAmount").toString()))+"<br>"+
                    "<br>Net Payment Amount: " +formatter.format(confirmTotalAmount)+
                    "<br>Payment Gateway : " +jo.get("paymentGateway").toString()+
                    "<br>Payment Profile Name:"+"&nbsp;"+profileName+
                    "<br>Payment Method : " +jo.get("payTypeMethod").toString()+
                    "<br>Payment Currency : " +jo.get("paymentCurrency").toString()+
                    "<br>Exchange Rate : " +jo.get("userExchangeRates").toString()+
                    "<br>Paying Amount : USD "+formatter.format(confirmTotalAmountUSD);

            Intent mIntent = new Intent(getApplicationContext(), ConfirmOrderActivity.class);
            // mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle mBundle = new Bundle();
            mBundle.putString("requestFrom", "CreditDebitCard");
            mBundle.putString("confirmOrderText", confirmOrderText);
            mIntent.putExtras(mBundle);
            startActivity(mIntent);
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/



      /*
  public Boolean validateFormData(){

        if (creditTypeIconRadio.getCheckedRadioButtonId() == -1)
        {
            errorMessage = "Please Select Payment Method";
            return  false;
        }


        if(selectedUserPayTypeIndex>0){
            errorMessage = "Please Select Payment Method";
            return  false;
        }


        if(!(getCardNumber().length()>0)){
            errorMessage = "Please Enter Card Number";
            return  false;
        }
        if(getExpMonth()==0){
            errorMessage = "Please Enter Card Expiry Month";
            return  false;
        }
        if(getExpYear()==0){
            errorMessage = "Please Enter Card Expiry Year";
            return  false;
        }
        if(!(getCvc().length()>0)){
            errorMessage = "Please Enter CVC Number";
            return  false;
        }

        if(addPayProfile.isChecked() && !(paymentProfileNameWrapper.getEditText().getText().toString().length()>0)){
            errorMessage = "Please Enter Profile Name";
            return  false;
        }

        if(!agreePayAgrement.isChecked()){
            errorMessage = "Please Agree Payment Terms";
            return  false;
        }

        return true;
    }*/


 /*  public String getCardNumber() {
        return this.cvc_no.getText().toString();
    }

    public String getCvc() {
        return this.cvc_no.getText().toString();
    }

    public Integer getExpMonth() {
        return getInteger(this.expMonth);
    }

  *//*  public Integer getExpYear() {
        return getInteger(this.yearSpinner);
    }*//*

    private Integer getInteger(Spinner spinner) {
        try {
            return Integer.parseInt(spinner.getSelectedItem().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public  void processCardInformation(){

        Boolean result = validateFormData();

        if(result){

            Card card = new Card(
                    getCardNumber(),
                    getExpMonth(),
                    getExpYear(),
                    getCvc());

            boolean validation = card.validateCard();
            if (validation) {
                showMyAlertProgressDialog.showProgressDialog("Processing...",
                        "Please wait.");
                new Stripe().createToken(
                        card,
                        PUBLISHABLE_KEY,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                showMyAlertProgressDialog.hideProgressDialog();
                                showConfirmOrderFormCreditDebit(token);

                            }
                            public void onError(Exception error) {
                                showMyAlertProgressDialog.hideProgressDialog();
                                userDeviceDetails
                                        .showToast(error.getLocalizedMessage());
                            }
                        });
            } else if (!card.validateNumber()) {
                userDeviceDetails
                        .showToast("The card number that you entered is invalid");
            } else if (!card.validateExpiryDate()) {
                userDeviceDetails
                        .showToast("The expiration date that you entered is invalid");
            } else if (!card.validateCVC()) {
                userDeviceDetails
                        .showToast("The CVC code that you entered is invalid");
            } else {
                userDeviceDetails
                        .showToast("The card details that you entered are invalid");
            }

        }else{
            userDeviceDetails
                    .showToast(errorMessage);
        }


    }

    public void showConfirmOrderFormCreditDebit(Token token){
        showMyAlertProgressDialog.showProgressDialog("Processing...",
                "Please wait.");

        stripeToken = token;

        SharedPreferences pref = getSharedPreferences("PAYMENTDETAILS", 0);
        SharedPreferences pref1 = getSharedPreferences("USERPAYPROVIDERDETAILS", 0);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "showConfirmOrderFormCreditDebit");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        // params.put("payUsingIds", payTypeIds[selectedUserPayTypeIndex]);
        params.put("payUsingIds",  pref1.getString("payTypeIds", ""));
        params.put("amtPayingVal", pref.getString("amtPayingVal", ""));
        // Make Http call
        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Obtaining Confirm Order", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleShowConfirmOrderFormCreditDebit(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void handleShowConfirmOrderFormCreditDebit(JSONObject response) throws JSONException{

        // userDeviceDetails.showToast(response);
        showMyAlertProgressDialog.hideProgressDialog();
        try {
            JSONObject jo = response;

            Log.i("data:", "" + jo);

            SharedPreferences pref = getSharedPreferences("PAYMENTDETAILS", 0);
            SharedPreferences finalPayData = getSharedPreferences("PAYMENTDETAILSFINAL", 0);
            SharedPreferences.Editor editor = finalPayData.edit();
            editor.clear();
            editor.commit();

            editor = finalPayData.edit();

            *//*[confirmPayUsing:userPayType.paymentTypeCode,payTypeHeading:disCommType?(disCommType=="Commission")?"Service Charge":"Discount":"",
                    payTypeValue:disCommValue,payTypeAmount:userToPayAmt,confirmPayUsings:userPayType.paymentTypeCode,
                    paymentGateway:userPayType.name,payTypeMethod:userPayType.payTypeMethod,paymentCurrency:"USD",userExchangeRates:exchangeRate[1],
                    userCountryStatus:exchangeRate[0],userPayTypesIds:userPayType.id]*//*
            editor.putString("username", userDetails.get(MyUserSessionManager.KEY_USERNAME).toString());
            editor.putString("payUsingIds", jo.get("userPayTypesIds").toString());
            editor.putString("confirmPayUsings", jo.get("confirmPayUsings").toString());
            editor.putString("confirmPaymentNotes", paymentNoteWrapper.getEditText().getText().toString());
            editor.putString("confirmPurchasedAmount", pref.getString("purchasedAmt", ""));
            editor.putString("confirmDiscountAmount", pref.getString("disAmt", ""));
            editor.putString("confirmDepositingAmount",pref.getString("walletDepositingVal", "") );
            editor.putString("confirmPayingAmount",pref.getString("amtPayingVal", "") );
            editor.putString("totalAmt",  pref.getString("totalAmt", ""));
            editor.putString("stripeToken",stripeToken.getId());
            editor.putString("payAmt", pref.getString("amtPayingVal", ""));
            editor.putString("addDiscountWallet",pref.getString("addDiscountWallet", ""));
            if(paymentProfileNameWrapper.getEditText().getText().toString().length()>0){
                editor.putString("confirmPaymentProfileNames", paymentProfileNameWrapper.getEditText().getText().toString());
            }else{
                editor.putString("confirmPaymentProfileNames", "");
            }

            //   editor.putString("payUsingIds", payTypeIds[selectedUserPayTypeIndex]);
            editor.putString("userIp", userDeviceDetails.getLocalIpAddress());
            editor.putString("card","************"+stripeToken.getCard().getLast4());
            editor.putString("expiryDate",getExpMonth()+"/"+getExpYear());

            editor.commit();
            String profileName=null;
            if(paymentProfileNameWrapper.getEditText().getText().toString().length()>0){
                profileName= paymentProfileNameWrapper.getEditText().getText().toString();
            }else{
                profileName="-";
            }
            Double confirmTotalAmount = Double.parseDouble(pref.getString("amtPayingVal", ""))+Double.parseDouble(jo.get("payTypeAmount").toString());
            Double confirmTotalAmountUSD = confirmTotalAmount/Double.parseDouble(jo.get("userExchangeRates").toString());
            Double amtPayingVal = Double.parseDouble(pref.getString("amtPayingVal", ""));
            String confirmOrderText = "<b>Payment Details</b><br>\n" +
                    "<br>Card No : ************\n" + stripeToken.getCard().getLast4()+
                    "<br>CVC : ***\n" +
                    "<br>Expiration : " + getExpMonth()+"/"+getExpYear()+"<br>" +
                    "<br>Payment Amount : " +formatter.format(amtPayingVal)+
                    "<br>Payment Type " +jo.get("payTypeHeading").toString()+
                    "<br>Value : " +jo.get("payTypeValue").toString()+
                    "<br>Amount :\n" +formatter.format( Double.parseDouble(jo.get("payTypeAmount").toString()))+"<br>"+
                    "<br>Net Payment Amount: " +formatter.format(confirmTotalAmount)+
                    "<br>Payment Gateway : " +jo.get("paymentGateway").toString()+
                    "<br>Payment Profile Name:"+"&nbsp;"+profileName+
                    "<br>Payment Method : " +jo.get("payTypeMethod").toString()+
                    "<br>Payment Currency : " +jo.get("paymentCurrency").toString()+
                    "<br>Exchange Rate : " +jo.get("userExchangeRates").toString()+
                    "<br>Paying Amount : USD "+formatter.format(confirmTotalAmountUSD);

            Intent mIntent = new Intent(getApplicationContext(), ConfirmOrderActivity.class);
            // mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle mBundle = new Bundle();
            mBundle.putString("requestFrom", "CreditDebitCard");
            mBundle.putString("confirmOrderText", confirmOrderText);
            mIntent.putExtras(mBundle);
            startActivity(mIntent);
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Boolean validateFormData(){

      *//*  if (creditTypeIconRadio.getCheckedRadioButtonId() == -1)
        {
            errorMessage = "Please Select Payment Method";
            return  false;
        }*//*


        *//*if(selectedUserPayTypeIndex>0){
            errorMessage = "Please Select Payment Method";
            return  false;
        }
        *//*

        if(!(getCardNumber().length()>0)){
            errorMessage = "Please Enter Card Number";
            return  false;
        }
        if(getExpMonth()==0){
            errorMessage = "Please Enter Card Expiry Month";
            return  false;
        }
        if(getExpYear()==0){
            errorMessage = "Please Enter Card Expiry Year";
            return  false;
        }
        if(!(getCvc().length()>0)){
            errorMessage = "Please Enter CVC Number";
            return  false;
        }

        if(addPayProfile.isChecked() && !(paymentProfileNameWrapper.getEditText().getText().toString().length()>0)){
            errorMessage = "Please Enter Profile Name";
            return  false;
        }

        if(!agreePayAgrement.isChecked()){
            errorMessage = "Please Agree Payment Terms";
            return  false;
        }

        return true;
    }*/
}




