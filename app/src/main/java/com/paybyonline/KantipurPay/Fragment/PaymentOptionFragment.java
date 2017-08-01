package com.paybyonline.KantipurPay.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Fragment.FragmentHelper.PaymentTypeViewPagerAdapter;
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
public class PaymentOptionFragment extends Fragment {

    UserDeviceDetails userDeviceDetails;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    MyUserSessionManager     myUserSessionManager;
    private HashMap<String, String> userDetails;
    CoordinatorLayout coordinatorLayout;
    private static String url;
    AppCompatActivity activity;
    PagerTabStrip pagerTabStrip;
    ViewPager mViewPager;
    String  disPerVal;
    String  disAmt;
    String  disType;
    String  amtType;
    String  totalAmt;
    String  walletDepositingVal;
    String  amtPayingVal;
    String  purchasedAmt;
    String amount;
    Bundle bundle;
    Bundle infoBundle;

    public PaymentOptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_payment_option, container, false);
        ((DashBoardActivity) getActivity()).setTitle("Payment Option");
        bundle=getArguments();
        url = PayByOnlineConfig.SERVER_URL;
        userDeviceDetails = new UserDeviceDetails(getActivity());
        myUserSessionManager = new MyUserSessionManager(getActivity());
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
        userDetails = myUserSessionManager.getUserDetails();
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.red_color));
        Log.i("BundleInfo",""+bundle);
        mViewPager.setAdapter(new PaymentTypeViewPagerAdapter(getChildFragmentManager(),
                getActivity(), bundle,coordinatorLayout));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (AppCompatActivity) context;
        }
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
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout
                ,getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!",
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
                Log.i("infoToBundle", "" + infoBundle);
                mViewPager.setAdapter(new PaymentTypeViewPagerAdapter(getChildFragmentManager(),
                        getActivity(), infoBundle,coordinatorLayout));


            }




        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
