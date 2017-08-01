package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Model.ServiceType;
import com.paybyonline.KantipurPay.Adapter.Model.TextViewStyling;
import com.paybyonline.KantipurPay.Fragment.BuyProductFormFragment;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;
import com.paybyonline.KantipurPay.usersession.UserDeviceDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import cz.msebera.android.httpclient.Header;


/**
 * Created by mefriend24 on 9/20/16.
 */

public class ChildRecycleViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,DialogInterface.OnClickListener {

    TextView childTextView;
//    TextView rechargeTargetNumberError;
    TextView rechargeServiceName;
    TextView prepmob;
    TextView  pinRequest;
    Spinner amtSpin;
    EditText amtEdit;
    LinearLayout childLayout;
    Context context;
    LayoutInflater inflater;
    static int previousViewColor = 0;
    AlertDialog dialog;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    private EditText confirmationPin;
    private EditText pinEnterEditText;
    private EditText pinReenterEditText;
    private EditText enterPasswordEditText;
    private EditText mobnoTxt;
    private EditText dealCode;
    private Button btnPaynow;
    String isPinRecharge;
    String scstAmountType;
    InputMethodManager imm;
    String startsWith = "";
    String categoryLength="";
    String maxVal = "";
    private String purchasedAmountType = "";
    String pinCodePresent = "NO";
    UserDeviceDetails userDeviceDetails;
    MyUserSessionManager myUserSessionManager;
    private HashMap<String, String> userDetails;
    CoordinatorLayout coordinatorLayout;
    private String rechargeTargetNumberErrorMsg = "";
    private String rechargeTargetAmountErrorMsg = "";
    private String purchasedAmountValue = "";
    String serviceTypeId;
    String serviceCatId;
    String minVal = "";
    String iLabel = "";
    View customView;
    Boolean ifFormIsShown=true;
    Boolean isRechargeFromNotShownOnLoading=true;
    private static int RESULT_LOAD_IMG = 5;
    ImageView contactIcon;
    private Uri uriContact;
    View childItemView;
    private String contactID;
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    Dialog pinRequestDialog ;

    String dealFlag = "";
    String dealRemarks = "";
    Button checkDealBtn ;
    RelativeLayout promoCodeLayout;
    Boolean promoCodeLayoutVisible = false;


    public ChildRecycleViewHolder(View itemView) {
        super(itemView);

        childTextView = (TextView)itemView.findViewById(R.id.childTextView);
        childLayout = (LinearLayout) itemView.findViewById(R.id.childLayout);
        context = itemView.getContext();

         showMyAlertProgressDialog=new ShowMyAlertProgressDialog(context);
        userDeviceDetails = new UserDeviceDetails(context);
        myUserSessionManager = new MyUserSessionManager(context);
        childItemView  = itemView;

    }

    public void showRechargeForm(LinearLayout rechargeFormLayout,ServiceType serviceType){

        Log.i(PayByOnlineConfig.PAY_BY_ONLINE_TAG_NAME,"serviceType.getIsProductEnable() "+serviceType.getIsProductEnable());

        if (rechargeFormLayout.getChildCount() > 0) {
            rechargeFormLayout.removeAllViews();
        }

        if(serviceType.getIsProductEnable().equals("Y")){
            inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.recharge_form, null);
            amtEdit = (EditText) customView.findViewById(R.id.amtEdit);

            promoCodeLayout = (RelativeLayout)customView.findViewById(R.id.promoCodeLayout);
            dealCode = (EditText) customView.findViewById(R.id.dealCode);

            if(serviceType.getEnablePromoCode().equals("Y")){
                promoCodeLayout.setVisibility(View.VISIBLE);
                promoCodeLayoutVisible = true;
            }else{
                promoCodeLayout.setVisibility(View.GONE);
                promoCodeLayoutVisible = false;
            }

            dealCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    dealFlag = "";
                    dealRemarks = "";
                }
            });

            mobnoTxt = (EditText) customView.findViewById(R.id.mobnoTxt);
            amtSpin = (Spinner) customView.findViewById(R.id.amtSpin);
            btnPaynow = (Button) customView.findViewById(R.id.btnPaynow);
            contactIcon = (ImageView) customView.findViewById(R.id.contactIcon);
            rechargeServiceName = (TextView) customView.findViewById(R.id.rechargeServiceName);
            prepmob = (TextView) customView.findViewById(R.id.prepmob);
            rechargeServiceName.setText(serviceType.getService_type_name());
            prepmob.setText(serviceType.getiLabel());
            mobnoTxt.setHint(serviceType.getiLabel());

            dealFlag = "";
            dealRemarks = "";

            if(isPinRecharge.equals("true")){
                prepmob.setVisibility(View.GONE);
                mobnoTxt.setVisibility(View.GONE);
            }

            if (serviceType.getScstAmountType().equals("SELECT")) {

                amtSpin.setVisibility(View.VISIBLE);
                amtEdit.setVisibility(View.GONE);
                contactIcon.setVisibility(View.GONE);
                String[] output = serviceType.getScst_amount_value().split(",");
                ArrayAdapter adapter1 = new ArrayAdapter(context,
                        android.R.layout.simple_spinner_item, output);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                amtSpin.setAdapter(adapter1);

                amtSpin.setOnItemSelectedListener(onServiceAmountValueSelectedListener);

            }else{
                amtEdit.setVisibility(View.VISIBLE);
                amtSpin.setVisibility(View.GONE);
            }

            setTargetNumberValidationMessage();

            btnPaynow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleRechargeBtnClick();
                }
            });

            checkDealBtn = (Button) customView.findViewById(R.id.checkDealBtn);
            checkDealBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dealCode.setError(null);
                    if(dealCode.getText().toString().length()>0){
                        verifyUserDeal();
                    }else{
                        dealCode.setError("Please enter code");
                    }
                }
            });

            contactIcon.setVisibility(View.GONE);
            contactIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent contactIntent = new Intent(Intent.ACTION_PICK,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI);

                    ((Activity)context).startActivityForResult(contactIntent, REQUEST_CODE_PICK_CONTACTS);

                }

            });
            rechargeFormLayout.addView(customView);

        }else{
            TextView textView = new TextView(context);
            textView.setPadding(10,10,10,10);
            textView.setText("Service not available now");
            rechargeFormLayout.addView(textView);
        }

        rechargeFormLayout.setVisibility(View.VISIBLE);

    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == Activity.RESULT_OK
                && null != data) {
            Log.d("Contact Number:", "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactNumber();


        }
    }*/
    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID =context. getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d("Contact Id", "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone =context. getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();
        mobnoTxt.setText(contactNumber);
        prepmob.setText(contactNumber);
        Log.d("Contact Number", "Contact Phone Number: " + contactNumber);
    }

    public void bind(final ServiceType serviceType, final LinearLayout rechargeFormLayout,
                     CoordinatorLayout coordinatorLayout,String serviceCategoryId,String servTypeId, int parentPosition) {
//                     CoordinatorLayout coordinatorLayout,String serviceCategoryId,String servTypeId,Boolean ifClick) {


        Log.i("msgss","child view  binding");

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        // to stop color from repeating in adjacent layout
        while (previousViewColor==randomAndroidColor){
            randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        }

        previousViewColor = randomAndroidColor;

        childItemView.setBackgroundColor(randomAndroidColor);


        this.coordinatorLayout = coordinatorLayout;
        serviceTypeId = serviceType.getService_type_id();
        serviceCatId = serviceCategoryId;
        childTextView.setText(serviceType.getService_type_name());
        startsWith = serviceType.getStartsWith();
        categoryLength = serviceType.getCategoryLength();
        minVal = serviceType.getMinVal();
        scstAmountType = serviceType.getScstAmountType();
        purchasedAmountType = serviceType.getScstAmountType();
        iLabel = serviceType.getiLabel();
        maxVal = serviceType.getMaxVal();
        isPinRecharge = serviceType.getIsPinRechargeService();

        childLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


/*                <objectAnimator
                android:duration="@android:integer/config_shortAnimTime"
                android:propertyName="translationZ"
                android:valueTo="8dp"
                android:valueType="floatType"/>*/
/*
                ObjectAnimator animation = ObjectAnimator.ofFloat(childLayout, "rotationY", 0.0f, 360f);
                animation.setDuration(3600);
                animation.setPropertyName("translationX");
                animation.setRepeatCount(ObjectAnimator.INFINITE);
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                animation.start();*/

                /*childLayout
                        .startAnimation(
                                AnimationUtils.loadAnimation(context, R.anim.linear_interpolator) );*/

                /*if(rechargeFormLayout.isShown()){
                    rechargeFormLayout.setVisibility(View.GONE);
                }else{
                    if(serviceCatId.equals("19")){
                        Log.i("msgss","sim tv clicked");
                        Fragment fragment = new BuyProductFormFragment();
                        FragmentManager fragmentManager = ((FragmentActivity) context).
                                getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, fragment);
                        Bundle bundle = new Bundle();
                        bundle.putString("scstId", "24");
                        fragmentTransaction.addToBackStack(null);
                        fragment.setArguments(bundle);
                        fragmentTransaction.commit();

                    }else{
                        showRechargeForm( rechargeFormLayout, serviceType);
                    }

                }*/


                if(serviceType.getServiceCategoryServiceType().equals("28")){
                    Log.i("msgss","subisu clicked");
                    Fragment fragment = new BuyProductFormFragment();
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    Bundle bundle = new Bundle();
                    bundle.putString("scstId", "28");
                    fragmentTransaction.addToBackStack(null);
                    fragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }else if(serviceType.getServiceCategoryServiceType().equals("24")){
//                }else if(serviceCatId.equals("19")){
                    Log.i("msgss","sim tv clicked");
                    Fragment fragment = new BuyProductFormFragment();
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    Bundle bundle = new Bundle();
                    bundle.putString("scstId", "24");
                    fragmentTransaction.addToBackStack(null);
                    fragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }else{
                    showRechargeForm( rechargeFormLayout, serviceType);
                }



            }
        });

        if(!servTypeId.isEmpty()){
            if((servTypeId.equals(serviceType.getService_type_id())) && (isRechargeFromNotShownOnLoading)){
                showRechargeForm( rechargeFormLayout, serviceType);
                isRechargeFromNotShownOnLoading = false;
            }

        }

    }

    public void handleRechargeBtnClick(){

        try{
            Boolean continueRecharge = false;
            if (promoCodeLayoutVisible){
                dealCode.setError(null);
                if(dealCode.getText().toString().length()>0){
                    if(dealFlag.equals("")){
                        dealCode.setError("Please check deal first");
                    }else{
                        continueRecharge = true;
                    }
                }else{
                    continueRecharge = true;
                }
            }else{
                continueRecharge = true;
            }

            if(continueRecharge){
                if (isPinRecharge.equals("false")) {
                    if(verifyRechargeNumber()){
                        verifyPaymentAmount();
                    }
                } else {
                    verifyPaymentAmount();
                }
            }

        }catch (Exception ex){
            Log.i("exception ",ex+"");
        }

    }

    public void verifyUserDeal(){
        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "checkUserDeals");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        params.put("dealName", dealCode.getText().toString());
        params.put("sCategory",serviceCatId );
        params.put("serviceType", serviceTypeId);
        params.put("purchasedValue", purchasedAmountValue);

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,context);
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context,response.getString("msg"),Toast.LENGTH_LONG).show();
                    dealFlag = response.getString("dealFlag");
                    dealRemarks = response.getString("dealRemarks");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Boolean verifyRechargeNumber(){

        Boolean continueRecharge = true;
        String targetNumberEditTextVal = mobnoTxt.getText().toString();

        if (targetNumberEditTextVal.length() > 0) {

            if ((!categoryLength.equals("null")) && (categoryLength.length()>0)) {
//            if (!categoryLength.equals("null")) {

                if (!categoryLength.equals(targetNumberEditTextVal
                        .length() + "")) {
                    continueRecharge = false;
                }
            }

//            if (!startsWith.equals("null")) {
            if ((!startsWith.equals("null")) && (startsWith.length()>0)) {

                int startsWithLength = startsWith.length();
                String checkRechargeNumber = targetNumberEditTextVal
                        .substring(0, startsWithLength);
                if (!startsWith.equals(checkRechargeNumber)) {
                    continueRecharge = false;
                }
            }

            if (!continueRecharge) {
                mobnoTxt.setError(Html.fromHtml(rechargeTargetNumberErrorMsg));
            }

        } else {

            mobnoTxt.setError("Required");
            continueRecharge = false;
        }

        return continueRecharge;
    }

    public void verifyPaymentAmount(){

        Boolean continueRecharge = true;
        String purchasedValue = "";

        if (purchasedAmountType.equals("SELECT")) {

            purchasedAmountValue = purchasedValue = amtSpin
                    .getSelectedItem().toString();
            transactionConfirmModel();

        } else {

            purchasedAmountValue = purchasedValue = amtEdit.getText().toString();

            if(purchasedValue.length()>0){
                if ((!minVal.equals("null")) && (minVal.length()>0)) {
//                if (!minVal.equals("null")) {

                    if ((Double.parseDouble(purchasedValue) < Double
                            .parseDouble(minVal))) {
                        continueRecharge = false;
                    }
                }

                if ((!maxVal.equals("null")) && (maxVal.length()>0)) {
//                if (!maxVal.equals("null")) {

                    if ((Double.parseDouble(purchasedValue) > Double
                            .parseDouble(maxVal))) {
                        continueRecharge = false;
                    }
                }

                if (continueRecharge) {
                    transactionConfirmModel();
                } else {
                    amtEdit.setError(Html.fromHtml(rechargeTargetAmountErrorMsg));
                }

            }else{
                amtEdit.setError("Required");
            }



        }
    }


    public void pinRequest() {

        dialog.dismiss();
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogview = inflater.inflate(R.layout.request_pin_layout, null);

        pinEnterEditText = (EditText) dialogview.findViewById(R.id.enterPin);
        pinReenterEditText = (EditText) dialogview.findViewById(R.id.reenterPin);

        enterPasswordEditText = (EditText) dialogview
                .findViewById(R.id.confirmpassword);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                context);
        AlertDialog pindialog = dialogBuilder
                // .setMessage(ss)
                // .setIcon(R.drawable.ic_launcher)
                .setView(dialogview).setTitle("Pin Code Request")
                .setPositiveButton("Save", this)
                .setNegativeButton("Cancel", this).setCancelable(false)
                .create();
        pindialog.show();
        pindialog.setCanceledOnTouchOutside(true);

        Button saveBtn = pindialog.getButton(DialogInterface.BUTTON_POSITIVE);
        saveBtn.setOnClickListener(new CustomListener2(pindialog));

    }

    public void showUserAlertDialogWithView(String message, String title) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogview = inflater.inflate(R.layout.redirect_website_message,
                null);
        TextView redirectMsg = (TextView) dialogview
                .findViewById(R.id.redirectMsg);

        redirectMsg.setText(message);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final AlertDialog pindialog = dialogBuilder.setTitle(title)
                .setView(dialogview).setPositiveButton("Request Pin", this)
                .setNegativeButton("Cancel", this).setCancelable(false)
                .create();

        pindialog.show();
        Button saveBtn = pindialog.getButton(DialogInterface.BUTTON_POSITIVE);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pindialog.dismiss();

                pinRequest();
            }
        });

    }

    public void transactionConfirmModel() {

        rechargeTargetNumberErrorMsg="";
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogview = inflater.inflate(R.layout.confirmation_pin_code_form,
                null);
        confirmationPin = (EditText) dialogview
                .findViewById(R.id.confirmationPin);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                context);
        dialog = dialogBuilder
                .setView(dialogview).setTitle("Transaction Confirmation")
                .setPositiveButton("Yes", this)
                .setNegativeButton("Cancel", this).setCancelable(false)
                .create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

         pinRequest = (TextView) dialogview
                .findViewById(R.id.pinCodeRequest);
        TextView pinCodeHeading = (TextView) dialogview
                .findViewById(R.id.pinCodeHeading);

        pinCodePresent = ((DashBoardActivity)context).getPinCodeStatus();

        if(pinCodePresent.equals("YES")){
            pinRequest.setVisibility(View.GONE);
            pinCodeHeading.setVisibility(View.GONE);

        }else{

            pinRequest.setVisibility(View.VISIBLE);
            pinCodeHeading.setVisibility(View.VISIBLE);
            pinRequest.setText(new TextViewStyling()
                    .textViewLink("Create Pin Code"));
         //   pinRequest.setPadding(R.dimen.margin_left,0,0,0);
            pinRequest.setTextColor(Color.parseColor("#5F86C4"));
            pinRequest.setOnClickListener(this);

        }


        Button yesBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        yesBtn.setOnClickListener(new CustomListener(dialog));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }


    public void setTargetNumberValidationMessage() {

        try {

            rechargeTargetNumberErrorMsg = iLabel
                    + " should have";

            rechargeTargetAmountErrorMsg = "Amount should have";


            if (purchasedAmountType.equals("TEXT")) {
                if (minVal.equals("null")) {

                    rechargeTargetAmountErrorMsg = rechargeTargetAmountErrorMsg
                            + "<br/>Min Val: ";
                } else {

                    rechargeTargetAmountErrorMsg = rechargeTargetAmountErrorMsg
                            + "<br/>Min Val: " + minVal;
                }

                if (maxVal.equals("null")) {

                    rechargeTargetAmountErrorMsg = rechargeTargetAmountErrorMsg
                            + "<br/>Max Val: ";
                } else {

                    rechargeTargetAmountErrorMsg = rechargeTargetAmountErrorMsg
                            + "<br/>Max Val: " + maxVal;
                }
            }


            if (startsWith.equals("null")) {

                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>Starts With: ";
            } else {
                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>Starts With: " + startsWith;
            }

            if (categoryLength.equals("null")) {
                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>" + iLabel + " Length: ";
            } else {
                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>" +iLabel + " Length: "
                        + categoryLength;
            }


//            rechargeTargetNumberError.setText(Html
//                    .fromHtml(rechargeTargetNumberErrorMsg));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("Exception",""+e);
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

            switch(view.getId()){

                case R.id.pinCodeRequest:
                    pinRequest();
                    break;

        }

    }

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;

        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {

            // Do whatever you want here
            // If you want to close the dialog, uncomment the line below
            String minLength = "4";
            if (minLength.equals(confirmationPin.getText().toString().length()
                    + "")) {
                dialog.dismiss();
                // for hiding keyboard on button click
               // imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                performUserRecharge();
            } else {
                confirmationPin
                        .setError("4 Digit Transaction Pin Code Required");
            }

        }
    }

    class CustomListener2 implements View.OnClickListener {
        private final Dialog dialog;

        public CustomListener2(Dialog dialog) {
            this.dialog = dialog;
            pinRequestDialog = dialog;
        }

        @Override
        public void onClick(View v) {

            // Do whatever you want here
            // If you want to close the dialog, uncomment the line below
            String minLength = "4";

            if (!minLength.equals(pinEnterEditText.getText().toString()
                    .length()
                    + ""))
                pinEnterEditText
                        .setError("4 Digit Transaction Pin Code Required");

            if (!minLength.equals(pinReenterEditText.getText().toString()
                    .length()
                    + ""))
                pinReenterEditText
                        .setError("4 Digit Transaction Pin Code Required");

            if (enterPasswordEditText.getText().toString().isEmpty())
                enterPasswordEditText.setError("password required");

            if (pinEnterEditText.getText().toString().length() > 0
                    && pinReenterEditText.getText().toString().length() > 0
                    && enterPasswordEditText.getText().toString().length() > 0) {

                if (minLength.equals(pinEnterEditText.getText().toString()
                        .length()
                        + "")
                        && minLength.equals(pinReenterEditText.getText()
                        .toString().length()
                        + "")) {

                    if (pinEnterEditText.getText().toString()
                            .equals(pinReenterEditText.getText().toString())) {

                        try {

                            RequestParams params = new RequestParams();
                            params.put("parentTask", "rechargeApp");
                            params.put("childTask", "saveUserPinCode");
                            params.put("userCode", myUserSessionManager.getSecurityCode());
                            params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
                            params.put("pin", pinEnterEditText.getText()
                                    .toString());
                            // Make Http call
                            PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,context);
                            handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                                    try {
                                        handlePerformUserRechargeResponse(response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                                }
                            });

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } else {
                        pinEnterEditText.setError(" Pin not matched");
                    }

                } else {
                    pinEnterEditText
                            .setError(" 4 Digit Transaction Pin Code Required");
                }

            } else {
                enterPasswordEditText.setError("enter the data");
            }
        }
    }

    AdapterView.OnItemSelectedListener onServiceAmountValueSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (purchasedAmountType.equals("SELECT")) {
                purchasedAmountValue = amtSpin
                        .getSelectedItem().toString();
                // viewRechargePayDetails();
            } else {
                purchasedAmountValue = amtEdit.getText()
                        .toString();
                if ((purchasedAmountValue.length() > 0)) {
                    amtEdit
                            .setError("Purchase Value Required");
                } else {

                    //   viewRechargePayDetails();
                }

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    public void performUserRecharge() {

        RequestParams rechargeParams=new RequestParams();

        rechargeParams.put("parentTask", "rechargeApp");
        rechargeParams.put("childTask", "performRecharge");
//        rechargeParams.put("childTask", "saveRecharge");
        rechargeParams.put("userCode", myUserSessionManager.getSecurityCode());
        rechargeParams.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        rechargeParams.put("rechargeNumber", mobnoTxt.getText()
                .toString());
        rechargeParams.put("confirmationPin", confirmationPin.getText()
                .toString());
        rechargeParams.put("responseType", "JSON");
        rechargeParams.put("sCategory",serviceCatId );
        rechargeParams.put("serviceType", serviceTypeId);
        rechargeParams.put("purchasedValue", purchasedAmountValue);
        rechargeParams.put("dealFlag", dealFlag);
        rechargeParams.put("dealRemarks", dealRemarks);
        rechargeParams.put("dealName", dealCode.getText().toString());

        Log.i("rechargeParams",rechargeParams+"");
        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,context);
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", rechargeParams,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            handlePerformUserRechargeResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                    }
                });

    }
    public void handlePerformUserRechargeResponse(JSONObject response) throws  JSONException{

        dialog.dismiss();
        if(pinRequestDialog!=null){
            pinRequestDialog.dismiss();
        }
        try {

            JSONObject json = response;
            Log.i("jsonttttttttttttt", json + "");

            if (json.getString("msgTitle").equals("FAILED")) {

                if (json.getString("reason").equals("No Code")) {

                    showUserAlertDialogWithView(json.getString("msg"),
                            "msgTitle");
                } else {

                    showMyAlertProgressDialog.showUserAlertDialog(
                            json.getString("msg"),json.getString("msgTitle"));
                }

            } else {
                amtEdit.setText("");
                mobnoTxt.setText("");
                dealCode.setText("");
                dealFlag = "";
                dealRemarks = "";

                if (response.has("accountBalance")){
                    DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
                    dashBoardActivity.updateUserBalance(response.getString("currency"),response.getString("accountBalance"),Double.parseDouble(response.getString("holdMoneyAmount")));
                }
                showMyAlertProgressDialog.showUserAlertDialog(
                        json.getString("msg"),json.getString("msgTitle"));

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }}



/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RQS_PICK_CONTACT){
            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor cursor = context.managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                String number =       cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //contactName.setText(name);
                mobnoTxt.setText(number);
                //contactEmail.setText(email);
            }
        }
    }*/
}







