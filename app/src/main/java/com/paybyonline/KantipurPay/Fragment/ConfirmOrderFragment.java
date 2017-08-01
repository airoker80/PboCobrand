package com.paybyonline.KantipurPay.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Activity.PboWebViewActivity;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;
import com.paybyonline.KantipurPay.usersession.UserDeviceDetails;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Anish on 10/18/2016.
 */

public class ConfirmOrderFragment extends Fragment implements   DialogInterface.OnClickListener  {


    AlertDialog.Builder alertDialog;
    private MyUserSessionManager myUserSessionManager;
    private UserDeviceDetails userDeviceDetails;
    private ShowMyAlertProgressDialog showMyAlertProgressDialog;
    private HashMap<String, String> userDetails;
    private static String url;
    FragmentManager myFragmentManagerSupport;
    CoordinatorLayout coordinatorLayout;
    String msgTitle = "";
    String requestFrom;
    Bundle   bundleData;
    private static final String PAYPAL_TAG = PayByOnlineConfig.PAYPAL_TAG;
    private static final String CONFIG_ENVIRONMENT = PayByOnlineConfig.PAYPAL_ENVIRONMENT;
    private static final String CONFIG_CLIENT_ID = PayByOnlineConfig.PAYPAL_CLIENT_ID;
    private static final int REQUEST_CODE_PAYMENT = 10;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);
    Bundle bundlePayPalData=new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_confirm_order, container, false);
       // View view= inflater.inflate(R.layout.payment_details, container, false);

        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        myFragmentManagerSupport = getActivity().getSupportFragmentManager();
        userDeviceDetails = new UserDeviceDetails(getActivity());
        myUserSessionManager = new MyUserSessionManager(getActivity());
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
        userDetails = myUserSessionManager.getUserDetails();
        url = PayByOnlineConfig.SERVER_URL;


      //  Bundle   requestFromBundle = getArguments();
        bundleData = getArguments();
        Log.i("bundleData",""+bundleData);
        if (null != bundleData) {
            //Null Checking

            requestFrom= bundleData.getString("requestFrom");

            ((DashBoardActivity) getActivity()).setTitle("Confirmation");

            String confirmOrderText = bundleData.getString("confirmOrderText");

            FrameLayout myLayout = (FrameLayout)view.findViewById(R.id.confirmPaymentForm);
            View paymentView = getLayoutInflater(bundleData).inflate(R.layout.confirm_payment_form_credit_debit,
                    myLayout, false);

            TextView payDetails = (TextView) paymentView.findViewById(R.id.confirmPayDetails);
            payDetails.setText(Html.fromHtml(confirmOrderText));

            Button confirmOrderBtn = (Button)paymentView.findViewById(R.id.confirmOrderBtn);
            confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(requestFrom.equals("Paypal")){

                        createUserPaymentServer();

                    }else if(requestFrom.equals("OnlineBanking")){

                        createUserPaymentServerNibl();

                    }
                    else{

                        addMoneyWalletServer();
                    }

                }
            });

            myLayout.addView(paymentView);


        }
        return view;
    }

    public void createUserPaymentServerNibl(){

        Intent intent = new Intent(getActivity(), PboWebViewActivity.class);
        bundleData.putString("userIp",userDeviceDetails.getLocalIpAddress());
        bundleData.putString("webViewPage","Online Banking");
        bundleData.putString("pageTitle","NIBL Checkout");
        intent.putExtras(bundleData);
        startActivity(intent);
        //startActivity(new Intent(getActivity(),OnlineBankingActivity.class));
    }

    public void createUserPaymentServer(){

        RequestParams finalPayParams = new RequestParams();
        finalPayParams.put("parentTask", "rechargeApp");
        finalPayParams.put("childTask", "createUserPaymentDetailsPaypal");
        finalPayParams.put("userCode", myUserSessionManager.getSecurityCode());
        finalPayParams.put("authenticationCode", myUserSessionManager.getAuthenticationCode());

        finalPayParams.put("payUsingIds", bundleData.getString("payUsingIds"));
        finalPayParams.put("confirmPayUsings",bundleData.getString("confirmPayUsings"));
        finalPayParams.put("confirmPaymentNotes",bundleData.getString("confirmPaymentNotes"));
        finalPayParams.put("confirmPurchasedAmount", bundleData.getString("confirmPurchasedAmount"));
        finalPayParams.put("confirmDiscountAmount", bundleData.getString("confirmDiscountAmount"));
        finalPayParams.put("confirmDepositingAmount", bundleData.getString("confirmDepositingAmount"));
        finalPayParams.put("confirmPayingAmount", bundleData.getString("confirmPayingAmount"));
        finalPayParams.put("totalAmt", bundleData.getString("totalAmt"));
        finalPayParams.put("payAmt", bundleData.getString("payAmt"));
        finalPayParams.put("addDiscountWallet",bundleData.getString("addDiscountWallet"));
        finalPayParams.put("confirmPaymentProfileNames", bundleData.getString("confirmPaymentProfileNames"));
        finalPayParams.put("payUsingIds", bundleData.getString("payUsingIds"));


        finalPayParams.put("userIp", userDeviceDetails.getLocalIpAddress());

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!",
                finalPayParams, new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            handleCreateUserPaymentServer(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                    }
                });


    }

    public void handleCreateUserPaymentServer(JSONObject response) throws JSONException{

        JSONObject jo = null;
        try {

            jo = response;
            String saveStatus = jo.getString("saveStatus");

            if(saveStatus.equals("Success")){

                bundlePayPalData=new Bundle();
                bundlePayPalData.putString("purchasedAmt", jo.get("purchasedAmt").toString());
                bundlePayPalData.putString("pAmt", jo.get("pAmt").toString());
                bundlePayPalData.putString("payOptionId", jo.get("payOptionId").toString());
                bundlePayPalData.putString("transactionId", jo.get("transactionId").toString());
                bundlePayPalData.putString("paidFrom", jo.get("paidFrom").toString());
                bundlePayPalData.putString("payTypeId", jo.get("payTypeId").toString());
                bundlePayPalData.putString("addDiscountWallet", jo.get("addDiscountWallet").toString());
                bundlePayPalData.putString("paypalNetPayFinal", jo.get("paypalNetPayFinal").toString());

                callPaypalActivityForPayment(jo.get("paypalNetPayFinal").toString());

            }else{
                userDeviceDetails
                        .showToast("Some Error Occurred");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callPaypalActivityForPayment(String paypalNetPayFinal){

        SharedPreferences finalPayData =getActivity().getSharedPreferences("PAYMENTDETAILSFINAL", 0);

        PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(paypalNetPayFinal),
                PayByOnlineConfig.DEFAULT_CURRENCY, "Wallet Payment",
                PayByOnlineConfig.PAYMENT_INTENT);

        Intent intent = new Intent(getActivity(), PaymentActivity.class);


        // send the same configuration for restart resiliency

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
//        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        ConfirmOrderFragment.this.startActivityForResult(intent, REQUEST_CODE_PAYMENT);

//        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("msgsss","onActivityResult ------------ ");
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirm != null) {

                    try {

                        Log.i(PAYPAL_TAG, confirm.toJSONObject().toString(4));
                        Log.i(PAYPAL_TAG, confirm.getPayment().toJSONObject().toString(4));

                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */


                        JSONObject result = confirm.toJSONObject();
                        JSONObject paypalResponse = result.getJSONObject("response");

                        String paypalPaymentId = paypalResponse.getString("id");
                        String approved = paypalResponse.getString("state");

                        if(approved.equals("approved")){

                            updateUserPaymentDetailsPaypal(paypalPaymentId);

                        }else{

                            Toast.makeText(getContext().getApplicationContext(),
                                    "Payment Failed", Toast.LENGTH_LONG)
                                    .show();
                        }



                    } catch (JSONException e) {
                        Log.e(PAYPAL_TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(PAYPAL_TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        PAYPAL_TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    public void updateUserPaymentDetailsPaypal(String paypalPaymentId){


//       userDeviceDetails.showToast("updateUserPaymentDetailsPaypal");
//       userDeviceDetails.showToast("paypalPaymentId "+paypalPaymentId);

            Log.i("bundlePaymentDetails",""+bundleData);

       //SharedPreferences paypalTempData = getSharedPreferences("PAYMENTDETAILSPAYPAL", 0);



        RequestParams finalPayParams = new RequestParams();
//        finalPayParams.put("parentTask", "AndroidApp");
        finalPayParams.put("parentTask", "rechargeApp");
        finalPayParams.put("childTask", "updateUserPaymentDetailsPaypal");
        finalPayParams.put("userCode", myUserSessionManager.getSecurityCode());
        finalPayParams.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        finalPayParams.put("purchasedAmt", bundlePayPalData.getString("purchasedAmt", ""));
        finalPayParams.put("pAmt", bundlePayPalData.getString("pAmt", ""));
        finalPayParams.put("payOptionId", bundlePayPalData.getString("payOptionId", ""));
        finalPayParams.put("transactionId", bundlePayPalData.getString("transactionId", ""));
        finalPayParams.put("paidFrom", bundlePayPalData.getString("paidFrom", ""));
        finalPayParams.put("payTypeId", bundlePayPalData.getString("payTypeId", ""));
        finalPayParams.put("addDiscountWallet", bundlePayPalData.getString("addDiscountWallet", ""));
        finalPayParams.put("paypalPaymentId", paypalPaymentId);

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getContext());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", finalPayParams,
                new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleUpdateUserPaymentDetailsPaypal(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(getActivity(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });





    }

    public void handleUpdateUserPaymentDetailsPaypal(JSONObject response) throws JSONException{
       JSONObject jo = null;
        try {
            jo = response;
            msgTitle = jo.getString("msgTitle");

            alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setMessage(Html.fromHtml( jo.getString("msg"))).setTitle(jo.getString("msgTitle"))
                    // .setNeutralButton("OK",null).create();
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            if(msgTitle.equals("Success")){

                                Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("defaultPage", "AddMoney");
                                intent.putExtras(bundle);
                                getActivity().finish();
                                startActivity(intent);

//                                userDeviceDetails.showToast("Payment Successful");
                                /*Intent mIntent = new Intent(getActivity(), DashBoardActivity.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putString("jumpToWallet", "onClickSave");
                                mIntent.putExtras(mBundle);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(mIntent);
                                getActivity().finish();*/


                            }



                        }
                    }).setCancelable(false).create().show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addMoneyWalletServer(){


       // SharedPreferences finalPayData = getSharedPreferences("PAYMENTDETAILSFINAL", 0);
        RequestParams finalPayParams = new RequestParams();

        finalPayParams.put("parentTask", "rechargeApp");
        finalPayParams.put("childTask", "saveUserPaymentCheckout");
        finalPayParams.put("userCode", myUserSessionManager.getSecurityCode());
        finalPayParams.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        finalPayParams.put("payUsingIds", bundleData.getString("payUsingIds"));
        finalPayParams.put("confirmPayUsings",bundleData.getString("confirmPayUsings"));
        finalPayParams.put("confirmPaymentNotes",bundleData.getString("confirmPaymentNotes"));
        finalPayParams.put("confirmPurchasedAmount", bundleData.getString("confirmPurchasedAmount"));
        finalPayParams.put("confirmDiscountAmount", bundleData.getString("confirmDiscountAmount"));
        finalPayParams.put("confirmDepositingAmount", bundleData.getString("confirmDepositingAmount"));
        finalPayParams.put("confirmPayingAmount", bundleData.getString("confirmPayingAmount"));
        finalPayParams.put("confirmTotalAmount", bundleData.getString("confirmPayingAmount"));
        finalPayParams.put("totalAmt", bundleData.getString("totalAmt"));
        finalPayParams.put("payAmt", bundleData.getString("payAmt"));
        finalPayParams.put("addDiscountWallet",bundleData.getString("addDiscountWallet"));
        finalPayParams.put("confirmPaymentProfileNames", bundleData.getString("confirmPaymentProfileNames"));
        finalPayParams.put("payUsingIds", bundleData.getString("payUsingIds"));
        finalPayParams.put("userIp", userDeviceDetails.getLocalIpAddress());


        if(requestFrom.equals("CreditDebitCard")){

            finalPayParams.put("stripeToken", bundleData.getString("stripeToken"));
        }

        if(requestFrom.equals("Bank Deposit")){

            finalPayParams.put("chequeVoucherSlip", bundleData.getString("chequeVoucherSlip"));
            finalPayParams.put("chequeVoucherSlipName", bundleData.getString("chequeVoucherSlipName"));
            finalPayParams.put("confirmChequeVoucherNos", bundleData.getString("confirmChequeVoucherNos"));

        }

        // Make Http call
        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", finalPayParams,
                new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleAddMoneyWalletServer(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });


    }

    public void handleAddMoneyWalletServer(JSONObject response) throws JSONException{

        JSONObject jo = null;
        try {

            jo = response;

            msgTitle = jo.getString("msgTitle");

            alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setMessage(Html.fromHtml(jo.getString("msg")))
                    .setTitle(jo.getString("msgTitle"))
                    // .setNeutralButton("OK",null).create();
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            if(msgTitle.equals("Success")){


                                Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("defaultPage", "AddMoney");
                                intent.putExtras(bundle);
                                getActivity().finish();
                                startActivity(intent);

                                /*Intent mIntent = new Intent(getActivity(), DashBoardActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle mBundle = new Bundle();
                                mBundle.putString("jumpToWallet", "onClickSave");
                                mIntent.putExtras(mBundle);
                                startActivity(mIntent);*/

                            }



                        }
                    }).setCancelable(false).create().show();




        } catch (JSONException e) {
            e.printStackTrace();
            //  userDeviceDetails.showToast(jo);
        }


    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
