package com.paybyonline.KantipurPay.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.BalanceRequestListAdapter;
import com.paybyonline.KantipurPay.Adapter.Helper.EndlessRecyclerOnScrollListener;
import com.paybyonline.KantipurPay.Adapter.Model.BalanceRequest;
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
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment implements
        View.OnClickListener, DialogInterface.OnClickListener  {

    private Spinner paymentOption;

//    private EditText purchasedAmt;
    private TextView errorMsg;
    private JSONArray payOptionList;
    private RecyclerView listView;
//    LinearLayout topup;
    private List<BalanceRequest> list = new ArrayList<BalanceRequest>();
    private BalanceRequestListAdapter listCall;

    private MyUserSessionManager myUserSessionManager;
    private UserDeviceDetails userDeviceDetails;
    private ShowMyAlertProgressDialog showMyAlertProgressDialog;
    private HashMap<String, String> userDetails;
    AppCompatActivity mActivity;
    private int totalBalanceCount;

    PboServerRequestHandler handler;
    // URL to get JSON Array
    private static String url;

    CoordinatorLayout coordinatorLayout;
    int displayStart = 0;
    String status = "Initialized";

    RelativeLayout data_not_available;
    FloatingActionButton addMoneyFab;

    EditText amt_txt;
    TextView error_txt;

    Button viewPayDetails;




    TextView mainHeading ;
    CheckBox addDisComToWallet;
    TextView addDisComToWalletVal;
    TextView heading1;
    TextView heading2;
    Button continuePayment;

    String amtType;
    String walletDepositingVal;
    String amtPayingVal;
    String totalAmt;
    String purchasedAmt;
    String disAmt;
    String disPerVal;
    String disType;

    View addMoneyDialogView ;

    Bundle infoBundle;

    LinearLayout addMoneyPayDetails;

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater
                    .inflate(R.layout.fragment_wallet, container, false);

            ((DashBoardActivity) getActivity()).setTitle("Wallet");
          //  Menu menu = ((DashBoardActivity) mActivity).mainMenu;
            userDeviceDetails = new UserDeviceDetails(getActivity());
            myUserSessionManager = new MyUserSessionManager(getActivity());
            showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
            userDetails = myUserSessionManager.getUserDetails();
//            topup=(LinearLayout)view.findViewById(R.id.topup);
            displayStart = 0;
            coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        addMoneyFab=(FloatingActionButton) view.findViewById(R.id.addMoneyFab);
            url =  PayByOnlineConfig.SERVER_URL;
            listView = (RecyclerView) view.findViewById(R.id.requestBalanceList);


            setHasOptionsMenu(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            listView.setHasFixedSize(true);
            listView.setLayoutManager(mLayoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        data_not_available=(RelativeLayout) view.findViewById(R.id.data_not_available);

        displayStart = 0;
        status = "Initialized";
        list = new ArrayList<BalanceRequest>();
        obtainWalletDepositDetails();

        addMoneyFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMoneyForm();
            }
        });

            return view;
        }

    AlertDialog alertDialog;
    public void addMoneyForm(){
        alertDialog = new AlertDialog.Builder(getActivity())
//                .setPositiveButton("View Details", null)
//                .setNegativeButton("CANCEL", null)
                .create();

        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.add_money, null);
        amt_txt=(EditText)dialogView.findViewById(R.id.amt_txt);

        amt_txt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if(addMoneyPayDetails!=null && (addMoneyPayDetails.getChildCount()>0)){
                    addMoneyPayDetails.removeAllViews();
                    viewPayDetails.setVisibility(View.VISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        error_txt=(TextView)dialogView.findViewById(R.id.error_txt);
        viewPayDetails=(Button) dialogView.findViewById(R.id.viewPayDetails);
        viewPayDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amt_txt.getText().toString().length()>0) {

                    amt_txt.setError(null);

                    obtainPaymentDetails();
                }else{
                    amt_txt.setError("Required");
                }

            }
        });
        alertDialog.setView(dialogView);
        alertDialog.setTitle("Add Money");
        amt_txt.requestFocus();

        showSoftKeyboard(dialogView);
//        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button btnAccept = alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(amt_txt.getText().toString().length()>0){

                            error_txt.setText(null);

                            obtainPaymentDetails();


                    /*        Fragment fragment = new PaymentOptionFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, fragment);
                            fragmentTransaction.addToBackStack(null);
                            Bundle bundle = new Bundle();
                            bundle.putString("amount", amt_txt.getText().toString());
                            Log.i("Enter Amount ", amt_txt.getText().toString());
                            fragment.setArguments(bundle);
                            fragmentTransaction.commit();
                            alertDialog.dismiss();*/

                        }else{
//                                    error_txt.setVisibility(View.VISIBLE);
                            amt_txt.setError("Required");
                        }


                    }
                });

                final Button btnDecline = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        addMoneyDialogView = dialogView;
        alertDialog.show();
    }



   /* msg:Success, msgTitle:Success, totalAmt:999000.0, disAmt:1000.0, amtType:Discount, disType:percent,
    disPerVal:0.1, isPer:true, isDis:true, amtPayingVal:1000000.0, walletDepositingVal:1001000.0,
    heading1:<b>Total Discount you have received</b><br><b>Total Wallet Depositing Amount</b>,
    heading1Val:1001000.00, heading2:<b>Total Amount you are paying</b>, heading2Val:1000000.00,
    mainHeading1:Wallet Deposit Discount (WD Dis), mainHeadingVal:1000.0,
    checkBoxText:Add Discount Amount in my Wallet, purchasedAmt:1000000, billingAndCode:1, showDetails:YES
*/

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public  void obtainPaymentDetails(){



        RequestParams params = new RequestParams();

        params.put("parentTask", "rechargeApp");
        params.put("childTask", "viewPaymentDetails");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        params.put("purchasedAmtAdds",amt_txt.getText().toString());

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

                SharedPreferences pref = getActivity().getSharedPreferences("PAYMENTDETAILS", 0);
                SharedPreferences.Editor editor1 = pref.edit();
                SharedPreferences.Editor editor = pref.edit();
                editor1.clear();
                // editor.remove("logged");
                // editor.remove("appUserName");
                // editor.remove("country");
                editor1.commit();

                SharedPreferences pref2 = getActivity().getSharedPreferences("PAYMENTDETAILS", 0);
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

                infoBundle=new Bundle();
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


                if(showDetails.equals("NO")){

                    editor.putString("addDiscountWallet", "on");
                    editor.commit();

                    showPaymentOptionPage();

                }else if(showDetails.equals("YES")){

                     addMoneyPayDetails = (LinearLayout)addMoneyDialogView.findViewById(R.id.addMoneyPayDetails);
                    addMoneyPayDetails.removeAllViews();
                    View paymentView = getActivity().getLayoutInflater().inflate(R.layout.add_money_payment_details, addMoneyPayDetails, false);

                    mainHeading = (TextView) paymentView.findViewById(R.id.mainHeading);
                    addDisComToWallet = (CheckBox) paymentView.findViewById(R.id.addDisComToWallet);
                    addDisComToWallet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(addDisComToWallet.isChecked()){

                                if(amtType.equals("Discount")){
                                    Double depositAmt = Double.parseDouble(purchasedAmt)+Double.parseDouble(disAmt);
                                    heading1.setText(Html.fromHtml("<b>Total Discount you have received:</b><br><b>Total Wallet Depositing Amount :</b>" + " ") + depositAmt.toString());
                                    walletDepositingVal = depositAmt.toString();
                                    heading2.setText(Html.fromHtml("<b>Total Amount you are paying :</b>") + " " +purchasedAmt);
                                    amtPayingVal = purchasedAmt;
                                }else{
                                    Double depositAmt = Double.parseDouble(purchasedAmt)-Double.parseDouble(disAmt);
                                    heading1.setText(Html.fromHtml("<b>Total Commission Amount you need to pay:</b><br><b>Total Wallet Depositing Amount :</b>" + " ") + depositAmt.toString());
                                    walletDepositingVal = depositAmt.toString();
                                    heading2.setText(Html.fromHtml("<b>Total Amount you are paying :</b>") + " " +purchasedAmt);
                                    amtPayingVal = purchasedAmt;
                                }

                            }else{
                                heading1.setText(Html.fromHtml("<b>Total Discount you have received:</b><br><b>Total Wallet Depositing Amount :</b>" + " ") + purchasedAmt);
                                walletDepositingVal = purchasedAmt;
                                heading2.setText(Html.fromHtml("<b>Total Amount you are paying :</b>") + " " +totalAmt);
                                amtPayingVal = totalAmt;
                            }
                        }
                    });

                    editor.putString("addDiscountWallet", addDisComToWallet.isChecked() ? "on" : "off");
                    editor.commit();

                    addDisComToWalletVal = (TextView) paymentView.findViewById(R.id.addDisComToWalletVal);
                    heading1 = (TextView) paymentView.findViewById(R.id.heading1);
                    heading2 = (TextView) paymentView.findViewById(R.id.heading2);
                    continuePayment = (Button) paymentView.findViewById(R.id.continuePayment);

                    if(amtType.equals("Discount")){
                        mainHeading.setText(Html.fromHtml("<b>Wallet Deposit Discount (WD Dis) :</b>" + " " +disAmt));
                        heading1.setText(Html.fromHtml("<b>Total Discount you have received:</b><br><b>Total Wallet Depositing Amount :</b>" + " ") + walletDepositingVal);
                        heading2.setText(Html.fromHtml("<b>Total Amount you are paying :</b>") + " " +amtPayingVal);
                        addDisComToWalletVal.setText(Html.fromHtml("Add Discount Amount in my Wallet"));
                    }else{
                        mainHeading.setText(Html.fromHtml("<b>Wallet Deposit Commission (WD Com) :</b>" + " " +disAmt));
                        heading1.setText(Html.fromHtml("<b>Total Commission Amount you need to pay:</b><br><b>Total Wallet Depositing Amount :</b>" + " ") + walletDepositingVal);
                        heading2.setText(Html.fromHtml("<b>Total Amount you are paying :</b>") + " " +amtPayingVal);
                        addDisComToWalletVal.setText(Html.fromHtml("Deduct Commission Amount from my Wallet"));
                    }

                    continuePayment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            showPaymentOptionPage();
                        }
                    });

                    addMoneyPayDetails.addView(paymentView);

                }

                viewPayDetails.setVisibility(View.GONE);

            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public  void showPaymentOptionPage(){
        Fragment fragment = new PaymentOptionFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
//        Bundle bundle = new Bundle();
        infoBundle.putString("amount", amt_txt.getText().toString());
        Log.i("Enter Amount ", amt_txt.getText().toString());
        fragment.setArguments(infoBundle);
        fragmentTransaction.commit();
        alertDialog.dismiss();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: // yes
                    // showToast("+++++++++++++");
                    break;
                case DialogInterface.BUTTON_NEGATIVE: // no
                    // showToast("-------------");
                    break;
                case DialogInterface.BUTTON_NEUTRAL: // neutral
                    // showToast("CCCCCCCCCC");
                    break;
                default:
                    // nothing
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // errorMsg.setVisibility(v.GONE);

        }

        class CustomListener implements View.OnClickListener {
            private final Dialog dialog;

            public CustomListener(Dialog dialog) {
                this.dialog = dialog;
            }

            @Override
            public void onClick(View v) {

                // Do whatever you want here
                // If tou want to close the dialog, uncomment the line below
                // dialog.dismiss();
                Boolean validateStatus = validateFormEmpty(v);
                if (!validateStatus) {

                } else {
                    HttpAsyncTask();
                    errorMsg.setVisibility(View.VISIBLE);
                    dialog.dismiss();


                }

            }
        }

        private Boolean validateFormEmpty(View v) {



            Boolean result = true;
            if (paymentOption.getSelectedItem().toString().equals("Choose")) {

                showMyAlertProgressDialog.showUserAlertDialog(
                        "Please Choose payment option", "Required");

                result = false;
            }
           /* if (purchasedAmt.getText().toString().length() == 0) {
                purchasedAmt.setError("Phone number Required!");
                result = false;
            }*/

            return result;
        }

        public void HttpAsyncTask() {



            RequestParams params = new RequestParams();
            params.put("parentTask", "rechargeApp");
            params.put("childTask", "requestBalance");
            params.put("userCode", myUserSessionManager.getSecurityCode());
            params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
            params.put("paymentOption",
                    paymentOption.getSelectedItem().toString());
           /* params.put("purchasedAmt",
                    purchasedAmt.getText().toString());*/



// Make Http call
            PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
            handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Obtaining Details", params, new PboServerRequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

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

            JSONObject json;
            String connectionStatus="";
            String serverResultMsgs = "";
            String serverResultMsgsTitle = "";

            try {
                json =response;

                connectionStatus = json.getString("connectionStatus");

                serverResultMsgs = "Balance Request Successfully Sent.";
                serverResultMsgsTitle = "Success";


                showMyAlertProgressDialog.showUserAlertDialog(serverResultMsgs,
                        serverResultMsgsTitle);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
               // btnSubmitNewBalanceRequest.setEnabled(false);
                e.printStackTrace();
                JSONObject res = new JSONObject();
                try {
                    res.put("connectionStatus", "failed");
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        }

          public void obtainWalletDepositDetails() {

            RequestParams params = new RequestParams();
            params.put("parentTask", "rechargeApp");
            params.put("childTask", "payOptions");
            params.put("userCode", myUserSessionManager.getSecurityCode());
            params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
            params.put("displayStart", displayStart+"");
            // Make Http call
            PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
            handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!",
                    params, new PboServerRequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {

                        handleObtainWalletDepositDetailsResponse(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                }
            });

        }

        public void handleObtainWalletDepositDetailsResponse(JSONObject response) throws JSONException {

            JSONObject json;
            String connectionStatus;
            json = new JSONObject();
            try {

                json = response;

                JSONArray output = json.getJSONArray("res");
                payOptionList = output;



                JSONArray userBalanceRequestList = json
                        .getJSONArray("balanceList");

                if(userBalanceRequestList.length()>0){

                    for (int i = 0; i < userBalanceRequestList.length(); i++) {

                        JSONObject obs = userBalanceRequestList
                                .getJSONObject(i);

                        list.add(new BalanceRequest(obs.get("status")
                                .toString(), obs.get("transactionNo")
                                .toString(), obs.get("paidUsing").toString(),
                                obs.get("requestedDate").toString(), obs.get(
                                "purchasedAmount").toString(),
                                obs.get("flatPercent").toString(), obs.get(
                                "flatDiscount").toString(),obs.get(
                                "flatAddition").toString(),
                                obs.get(
                                        "totalAmount").toString(),
                                obs.get(
                                        "discountPercent").toString(), obs.get(
                                "discountAmount").toString(), obs.get(
                                "additionAmount").toString(), obs.get(
                                "paidAmount").toString()));

                        Log.i("inserted balance", i + "");

                        //userDeviceDetails.showToast("inserted balance"+ i + "");

                    }
                    //userDeviceDetails.showToast("userBalanceRequestList2");


                    if (status.equals("Initialized")) {
                        totalBalanceCount = Integer.parseInt(json
                                .getString("iTotalRecords"));

                        Log.i("totalBalanceCount ","totalBalanceCount "+ totalBalanceCount);

                        listCall = new BalanceRequestListAdapter(getActivity(), list);

                        listView.setAdapter(listCall);

                        if (listCall != null) {
                            int checkCount = displayStart+10;
                            if(checkCount>totalBalanceCount){
                                displayStart = totalBalanceCount;
                            }else{
                                displayStart = displayStart + 10;
                            }

                        }

                        listView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
                            @Override
                            public void onLoadMore(int currentPage) {
                                Log.i("load more ","displayStart : "+ displayStart);
                                if(displayStart<=totalBalanceCount) {
                                    obtainWalletDepositDetails();
                                }

                            }
                        });

                        status = "Loaded";

                    }else if (status.equals("Loaded")) {
                        int checkCount = displayStart+10;
                        if(checkCount>totalBalanceCount){
                            displayStart = totalBalanceCount;
                        }else{
                            displayStart = displayStart + 10;
                        }
                        listCall.notifyDataSetChanged();
                    }

                }else{
                    if (status.equals("Initialized")) {
                        data_not_available.setVisibility(View.VISIBLE);
                    }

                }




            } catch (JSONException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
                JSONObject res = new JSONObject();
                try {
                    res.put("connectionStatus", "failed");
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        }

        // //////////////////////////////////////////////////////////////////////////

        AdapterView.OnItemSelectedListener onItemSelectedListener0 = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.addMoney).setVisible(true);
        super.onPrepareOptionsMenu(menu);

    }

    }


