package com.paybyonline.KantipurPay.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
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

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendMoneyFormFragment extends Fragment {

    LinearLayout rs_Role_layout;
    LinearLayout button_layout;
    LinearLayout receiverRole_layout;
    Spinner recevierRoleList;
    Spinner purposeListSpin;
    String tag="";
    TextView sendingAmountWrapper ;
    LinearLayout sendToWrapper ;
    Button button_check;
    Button button_cancel;
    RequestParams params=new RequestParams();
    String purposeSelectString;
    String receiverRoleListString;
    LinkedHashMap<String, String> spinnerChildList;
    String sendingAmt=" ";
    String sentTo=" ";


    UserDeviceDetails userDeviceDetails;
    MyUserSessionManager myUserSessionManager;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    CoordinatorLayout coordinatorLayout;
    PboServerRequestHandler handler;

    EditText send_to;
    EditText sending_amount;

    public SendMoneyFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_money_form, container, false);
        userDeviceDetails = new UserDeviceDetails(getActivity());
        myUserSessionManager = new MyUserSessionManager(getActivity());
        ((DashBoardActivity) getActivity()).setTitle("Fund Transfer");
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);


        rs_Role_layout = (LinearLayout) view.findViewById(R.id.RS_role_layout);
        button_layout = (LinearLayout) view.findViewById(R.id.button_layout);
        receiverRole_layout = (LinearLayout) view.findViewById(R.id.receiverRole_layout);

        sendToWrapper = (LinearLayout) view.findViewById(R.id.sendToWrapper);
        sendingAmountWrapper = (TextView) view.findViewById(R.id.sendingAmountWrapper);

        purposeListSpin = (Spinner) view.findViewById(R.id.purposeList);
        recevierRoleList = (Spinner) view.findViewById(R.id.recevierroleList);
        button_check = (Button) view.findViewById(R.id.button_check);
        send_to = (EditText) view.findViewById(R.id.send_to);
        sending_amount = (EditText) view.findViewById(R.id.sending_amount);

        rs_Role_layout.setVisibility(View.GONE);
        button_layout.setVisibility(View.GONE);

        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( validateFormEmpty()){
                    params=new RequestParams();

                    params.put("totalSentAmount",sending_amount.getText().toString());
                    params.put("purposeName",purposeListSpin.getSelectedItem().toString());

                    if(send_to.getText().toString().equals("")){
                        params.put("receiverSecId",spinnerChildList.get(recevierRoleList.getSelectedItem().toString()));
                        params.put("sendTo","");

                    }else{
                        params.put("receiverSecId","");
                        params.put("sendTo",send_to.getText().toString());
                    }

                    if (!purposeListSpin.getSelectedItem().toString().equals("Send to my Another Account")){
                        checkForPboUser(send_to.getText().toString());
                    }else{
                        getSendReceiveMoneyConfirmContent();
                    }

                }
            }
        });

        purposeListSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                purposeSelectString = (String) purposeListSpin.getItemAtPosition(position);
                if (purposeSelectString.equals("-Select Purpose-")) {

                    rs_Role_layout.setVisibility(View.GONE);
                    button_layout.setVisibility(View.GONE);

                }else if(purposeSelectString.equals("Send to my Another Account")){

                    receiverRole_layout.setVisibility(View.VISIBLE);
                    rs_Role_layout.setVisibility(View.VISIBLE);
                    button_layout.setVisibility(View.VISIBLE);
                    sendToWrapper.setVisibility(View.GONE);
                    tag="rs_role";
                    recevierRoleList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            receiverRoleListString = (String) recevierRoleList.getItemAtPosition(position);
                            send_to.setText("");

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else {

                    receiverRole_layout.setVisibility(View.GONE);
                    rs_Role_layout.setVisibility(View.VISIBLE);
                    button_layout.setVisibility(View.VISIBLE);
                    sendToWrapper.setVisibility(View.VISIBLE);
                    // sentTo=sendToWrapper.getEditText().getText().toString();
                    tag="send_to";

                }
                //  recevierruleList.setSelection(0, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getSendReceiveMoneyListContent();


        return view;
    }

    public void checkForPboUser(String sentTo){

        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "checkPboUser");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("sentTo", sentTo);
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            Log.i("Listresponse", "response "+response);

                            if(response.getString("msgTitle").equals("Success")){

                                if(response.getString("msg").equals("true")){
                                    getSendReceiveMoneyConfirmContent();
                                }else{
                                    showConfirmDialog();
                                }

                            }else{
                                userDeviceDetails.showToast(response.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    public void showConfirmDialog(){
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setMessage("Not Registered User. Receiver need to register first to receive money. Wish to continue?")
                .setPositiveButton("CONTINUE", null)
                .setNegativeButton("CANCEL", null)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button btnAccept = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("msgsss","ok continue");
                        alertDialog.dismiss();
                        getSendReceiveMoneyConfirmContent();
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

//         Show the dialog
        alertDialog.show();
    }


    public void getSendReceiveMoneyListContent(){

//        RequestParams params = new RequestParams();
        // Make Http call
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "sendMoneyApp");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            Log.i("Listresponse", "response");
                            handleSendReceiveMoneyListContentResponse(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    public void handleSendReceiveMoneyListContentResponse(JSONObject response) throws JSONException {

        JSONArray childList=response.getJSONArray("childList");
        JSONArray purposeArr=response.getJSONArray("purposeList");
        spinnerChildList = new LinkedHashMap<String, String>();
        ArrayList<String> responseRoleList=new ArrayList<>();
        JSONObject jsonObject=new JSONObject();
        JSONObject jObject=new JSONObject();

        if(response.length()!=0){
            ArrayList<String> listOfPurpose = new ArrayList<String>();
            listOfPurpose.add("-Select Purpose-");
            for(int j=0;j<childList.length();j++){
                jsonObject= (JSONObject) childList.get(j);
                spinnerChildList.put(jsonObject.getString("userDetails"),jsonObject.getString("secId"));
            }
            for(int k=0;k<purposeArr.length();k++){
                jObject= (JSONObject) purposeArr.get(k);
                listOfPurpose.add(jObject.get("purposeName").toString());
            }

            ArrayAdapter adapter0 = new ArrayAdapter(getActivity(),
                    android.R.layout.simple_spinner_item, listOfPurpose);
            adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            purposeListSpin.setAdapter(adapter0);

            for (String foo : spinnerChildList.keySet()) {
                responseRoleList.add(foo);
            }

            ArrayAdapter adapter1 = new ArrayAdapter(getActivity(),
                    android.R.layout.simple_spinner_item, responseRoleList);

            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            recevierRoleList.setAdapter(adapter1);

        }
    }


    public boolean validateFormEmpty() {
        Boolean result = true;
        sendingAmt=sending_amount.getText().toString();
        sentTo=send_to.getText().toString();

        if ((sendingAmt.equals(""))) {
            sending_amount.setError("Please Enter Valid Amount");
            result = false;
        }

        if(tag.equals("send_to")){
            if(sentTo.equals("")) {
                send_to.setError("Please enter email address");
                result = false;
            }else if (userDeviceDetails.isEmailValid(sentTo) == false) {
                send_to.setError("Please enter valid email address");
                result = false;
            } else {
                result = true;
            }
        }


        if(send_to.getText().toString().equals("")){
            if(!(recevierRoleList != null && recevierRoleList.getSelectedItem() !=null)) {
                userDeviceDetails.showToast("Please select receiver role");
                result = false;
            }
        }



        return result;
    }


    public void getSendReceiveMoneyConfirmContent(){

        //  RequestParams params = new RequestParams();
        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "checkCurrencyAndLimitApp");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());

        Log.i("msgsss","params for get confirm SM "+params);

//        receiverSecId=188&parentTask=rechargeApp
// &authenticationCode=dRRVGdzoWrapwD7Xt5IzvB4sv+iGGe34fKdM91su0nWcdwpZrOtmwxwnIBzy00WXp+jv2+HJAl02MF+M5CesJC5lJ//hcVayNbgUtJLdOq8=
// &totalSentAmount=20&sendTo=&userCode=pkBFXCv4RGXjh97HKf0sSr4+qg/liwo7C5kvH39WKHXdsOhE0SjG9vApfP0YVwRY
// &childTask=checkCurrencyAndLimitApp&purposeName=Send to my Another Account

        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            handleSendReceiveMoneyConfirmContentResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    public void handleSendReceiveMoneyConfirmContentResponse(JSONObject response) throws JSONException {

        Log.i("responseMoneyConfirm", response + "");
        if(response.getString("msgTitle").equals("Success")){

            JSONObject jsonObject=response.getJSONObject("sendMoneyDetails") ;
            Fragment fragment = new SendMoneyConfirmFragment();
            FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            Bundle bundle = new Bundle();
            bundle.putString("sendMoneyDetails", jsonObject.toString());
            fragmentTransaction.addToBackStack(null);
            fragment.setArguments(bundle);
            fragmentTransaction.commit();


        } else{
            //msg,msgTitle
            showMyAlertProgressDialog.showUserAlertDialog(response.getString("msg"), response.getString("msgTitle"));
        }

    }

}
