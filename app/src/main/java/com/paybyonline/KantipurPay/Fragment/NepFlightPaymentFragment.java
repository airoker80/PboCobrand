package com.paybyonline.KantipurPay.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Sameer on 7/6/2017.
 */

public class NepFlightPaymentFragment extends Fragment implements View.OnClickListener  {
    CoordinatorLayout coordinatorLayout;
    MyUserSessionManager myUserSessionManager;
    EditText confirmationPinEditTxt;
    TextView flightDiscount,descriptionTxt,acountDesc,flightAmmount;
    Button payFlight;
    String commValue,accessToken,bookingId,purchasedValue,discount,merchantTypeName,netAmount,
            serviceTypeId,sCategoryId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myUserSessionManager = new MyUserSessionManager(getContext());
        // Inflate the layout for this fragment
        getActivity().setTitle("Buy Product");
        Bundle bundle=getArguments();
        View view= inflater.inflate(R.layout.fragment_buy_nepflight_form, container, false);
        flightDiscount=(TextView)view.findViewById(R.id.flightDiscount);
        descriptionTxt=(TextView)view.findViewById(R.id.descriptionTxt);
        acountDesc=(TextView)view.findViewById(R.id.acountDesc);
        flightAmmount=(TextView)view.findViewById(R.id.flightAmmount);
        sCategoryId= bundle.getString("sCategoryId");
        serviceTypeId= bundle.getString("serviceTypeId");
        merchantTypeName= bundle.getString("merchantTypeName");
        commValue= bundle.getString("commValue");
        accessToken= bundle.getString("accessToken");
        netAmount= bundle.getString("netAmount");
        accessToken= bundle.getString("accessToken");
        discount= bundle.getString("discount");
        bookingId= bundle.getString("bookingId");

        purchasedValue= bundle.getString("purchasedValue");
        flightAmmount.setText(netAmount);
        flightDiscount.setText(discount);

        payFlight=(Button) view.findViewById(R.id.payFlight);
        payFlight.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.dialog_pin_enter,null);
        confirmationPinEditTxt=(EditText)view.findViewById(R.id.confirmationPinEditTxt);
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)
                .setView(view)
                .setTitle("Enter Pin Code")
                .create();
        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button btnAccept = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paymentRequest();
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

    public  void paymentRequest(){
        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(getContext());
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,
                getActivity());
        final RequestParams sendParams = new RequestParams();
        sendParams.put("responseType","JSON");
        sendParams.put("platformName", "Embedded by PBO");
        sendParams.put("purchasedValue", purchasedValue);
        sendParams.put("paidAmount", netAmount);
        sendParams.put("sCategory",sCategoryId);
        sendParams.put("serviceType", serviceTypeId);
        sendParams.put("webFrontFormNameId", 0);
        sendParams.put("dealName","");
        sendParams.put("dynamicMerchantSave","false");
        sendParams.put("parentTask", "rechargeApp");
        sendParams.put("bookingId", bookingId);
        sendParams.put("commValue", commValue);
        sendParams.put("accessToken", accessToken);
        // sendParams.put("childTask", "saveRecharge");
        sendParams.put("childTask", "performRecharge");
//        sendParams.put("childTask", "saveRecharge");
        sendParams.put("userCode", myUserSessionManager.getSecurityCode());
        sendParams.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        sendParams.put("confirmationPin", confirmationPinEditTxt.getText().toString());
        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Processing...", sendParams,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {

                            Log.i("ListresponseT", "response");
                            Log.i("params", ""+sendParams.toString());

                            handleSendFieldContentResponse(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exp",e+"");

                        }

                    }
                });
    }

    public void handleSendFieldContentResponse(JSONObject response) throws JSONException {
        if (response.getString("msgStatus").equals("Success")){
            makePDF();
        }
    }

    public void makePDF(){
        RequestParams jsonObjectPost = new RequestParams();

        Log.d("bookingId",bookingId);
        jsonObjectPost.put("parentTask", "nepFlight");
        jsonObjectPost.put("userCode", myUserSessionManager.getSecurityCode());
        jsonObjectPost.put("childTask", "issueFinalTicket");
        jsonObjectPost.put("bookingId", bookingId);
        jsonObjectPost.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        jsonObjectPost.put("accessToken",accessToken );
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", jsonObjectPost, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.i("authenticateUser",""+response);
                try {
                    handleObtainPaymentDetails(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void handleObtainPaymentDetails(JSONObject response) throws JSONException {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.dialog_success,null);
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("OK", null)
//                .setNegativeButton("CANCEL", null)
                .setView(view)
                .setTitle("Success")
                .create();

        builder.show();
    }
}

