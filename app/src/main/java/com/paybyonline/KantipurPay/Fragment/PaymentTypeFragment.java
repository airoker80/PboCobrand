package com.paybyonline.KantipurPay.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;
import com.paybyonline.KantipurPay.usersession.UserDeviceDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentTypeFragment extends Fragment {

    private FragmentTabHost mTabHost;
    UserDeviceDetails userDeviceDetails;
    MyUserSessionManager myUserSessionManager;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    private HashMap<String, String> userDetails;
    CoordinatorLayout coordinatorLayout;
    String  disPerVal;
    String  amount;
    String  disAmt;
    String  disType;
    String  amtType;
    String  totalAmt;
    String  walletDepositingVal;
    String  amtPayingVal;
    String  purchasedAmt;
    Bundle infoBundle;

    public PaymentTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((DashBoardActivity) getActivity())
                .setTitle("Recharge");

        userDeviceDetails = new UserDeviceDetails(getActivity());
        myUserSessionManager = new MyUserSessionManager(getActivity());
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
        userDetails = myUserSessionManager.getUserDetails();
        coordinatorLayout=(CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);

        infoBundle=new Bundle();


        Bundle bundle=getArguments();
        amount= bundle.getString("amount");
        Log.i("amount  ", amount);

        obtainPaymentDetails();
        Log.i("obtainPaymentDetails  ", "call");
        mTabHost = new FragmentTabHost(getActivity());
        // Locate fragment1.xml to create FragmentTabHost
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_payment_type);
        // Create Tab 1
        Log.i("infoBundle",infoBundle+"");
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Payment Option"),
                PaymentOptionFragment.class, infoBundle);
        // Create Tab 2
       // mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Load Profile"), LoadProfileFragment.class, null);
        // Create Tab 3

        return mTabHost;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {

            throw new RuntimeException(e);

        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);

        }
    }

    // Remove FragmentTabHost

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }
    public  void obtainPaymentDetails(){

        /*Bundle bundle = this.getArguments();
       // String  amt_txt = bundle.getString("amount");*/
        RequestParams params = new RequestParams();

        params.put("parentTask", "rechargeApp");
        params.put("childTask", "viewPaymentDetails");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        params.put("purchasedAmtAdds",amount);

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Accessing Payment Details",
                params, new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            Log.i("authenticateUser", "" + response);

                            handleObtainPaymentDetails(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                    }
                });


    }
    public void handleObtainPaymentDetails(JSONObject response) throws JSONException{


        Log.i("PaymentDetails", "" + response);

        try {

            // Loop through each array element, get JSON object
            JSONObject obj = response;
            String msgTitle = obj.getString("msgTitle").toString();

            if(msgTitle.equals("Failed")){
                // userDeviceDetails
                //      .showToast(obj.getString("msg").toString());
                showMyAlertProgressDialog.showUserAlertDialog(
                        obj.getString("msg").toString(), "FAILED");

            }else{

                String showDetails = obj.getString("showDetails").toString();
                disPerVal= obj.getString("disPerVal").toString();
                disAmt= obj.getString("disAmt").toString();
                disType= obj.getString("disType").toString();
                amtType=obj.getString("amtType").toString();
                totalAmt= obj.getString("totalAmt").toString();
                walletDepositingVal = obj.getString("walletDepositingVal").toString();
                amtPayingVal= obj.getString("amtPayingVal").toString();
                purchasedAmt= obj.getString("purchasedAmt").toString();

                // userDeviceDetails.showToast("amtPayingVal "+amtPayingVal);

                SharedPreferences pref =getActivity().getSharedPreferences("PAYMENTDETAILS", 0);
                SharedPreferences.Editor editor1 = pref.edit();
                SharedPreferences.Editor editor = pref.edit();
                editor1.clear();

                // editor.remove("logged");
                // editor.remove("appUserName");
                // editor.remove("country");
                editor1.commit();

                SharedPreferences pref2 =getActivity(). getSharedPreferences("PAYMENTDETAILS", 0);
                editor = pref2.edit();

                // userDeviceDetails.showToast(amtPayingVal);
                editor.putString("disPerVal", disPerVal);
                editor.putString("disAmt", disAmt);
                editor.putString("disType", disType);
                editor.putString("amtType", amtType);
                editor.putString("totalAmt", totalAmt);
                editor.putString("disPerVal", disPerVal);
                editor.putString("walletDepositingVal", walletDepositingVal);
                editor.putString("amtPayingVal", amtPayingVal);
                editor.putString("purchasedAmt", purchasedAmt);
                editor.putString("name", "flatDiscount");

                editor.commit();
               //  Bundle bundle =new  Bundle();
                infoBundle.putString("disPerVal", disPerVal);
                infoBundle.putString("disAmt", disAmt);
                infoBundle.putString("disType", disType);
                infoBundle.putString("amtType", amtType);
                infoBundle.putString("totalAmt", totalAmt);
                infoBundle.putString("disPerVal", disPerVal);
                infoBundle.putString("walletDepositingVal", walletDepositingVal);
                infoBundle.putString("amtPayingVal", amtPayingVal);
                infoBundle.putString("purchasedAmt", purchasedAmt);
                infoBundle.putString("name", "flatDiscount");


                }




        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

