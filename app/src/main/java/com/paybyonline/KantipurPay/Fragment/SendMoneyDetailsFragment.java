package com.paybyonline.KantipurPay.Fragment;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Model.SendReceiveMoney;
import com.paybyonline.KantipurPay.Adapter.SendMoneyDetailsAdapter;
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
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendMoneyDetailsFragment extends Fragment {

    RecyclerView sendMoneyRV;

    UserDeviceDetails userDeviceDetails;
    MyUserSessionManager myUserSessionManager;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    CoordinatorLayout coordinatorLayout;
    PboServerRequestHandler handler;

    List<SendReceiveMoney> sendReceiveMoneyList = new ArrayList<SendReceiveMoney>();
    SendMoneyDetailsAdapter sendMoneyDetailsAdapter;

    RelativeLayout data_not_available;
    FloatingActionButton sendMoneyFab;

    public SendMoneyDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_money_details, container, false);

        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        userDeviceDetails = new UserDeviceDetails(getActivity());
        myUserSessionManager = new MyUserSessionManager(getActivity());
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());

        ((DashBoardActivity) getActivity()).setTitle("Send Money");
        setHasOptionsMenu(true);

        sendMoneyRV=(RecyclerView)view.findViewById(R.id.sendMoneyRV);
        data_not_available=(RelativeLayout) view.findViewById(R.id.data_not_available);
        sendMoneyFab=(FloatingActionButton) view.findViewById(R.id.sendMoneyFab);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        sendMoneyRV.setHasFixedSize(true);
        sendMoneyRV.setLayoutManager(mLayoutManager);

        obtainSendMoneyDetails();

        sendMoneyFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSendMoneyForm();
            }
        });

        return view;
    }

    public void showSendMoneyForm(){
//        DashBoardActivity dashBoardActivity = (DashBoardActivity)getContext();
//        dashBoardActivity.selectPageToDisplay(R.id.sendMoneyDetails,false);

        Fragment fragment = new SendMoneyFormFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.content_frame, fragment,String.valueOf(1172)+"tag");
        fragmentTransaction.commit();
    }

    public void obtainSendMoneyDetails() {

        Log.i("msgsss"," obtainSendMoneyDetails");

        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "sentMoneyReportTable");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());

        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleObtainSendMoneyDetailsResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void handleObtainSendMoneyDetailsResponse(JSONObject response) throws JSONException{
        Log.i("msgsss","response "+response);
        JSONArray sentMoneyList = response.getJSONArray("sentMoneyList");
        if(sentMoneyList.length()>0){
            for(int i=0;i<sentMoneyList.length();i++){

                JSONObject obj= sentMoneyList.getJSONObject(i);
                Log.i("JSONObject",""+obj);

                sendReceiveMoneyList.add(new SendReceiveMoney(
                        obj.getString("purposeName"),
                        obj.getString("transactionNo"),
                        obj.getString("sentDate"),
                        obj.getString("expDate"),
                        obj.getString("showSentTo"),
                        obj.getString("isWalletSentAmount"),
                        obj.getString("isPspSentAmount"),
                        obj.getString("walletSentAmount"),
                        obj.getString("deductedWalletSentAmount"),
                        obj.getString("walletCharge"),
                        obj.getString("pspSentAmount"),
                        obj.getString("deductedPspSentAmount"),
                        obj.getString("pspCharge"),
                        obj.getString("sentRemarks"),
                        obj.getString("status"),
                        obj.getString("completionDate")
                ));
            }

            sendMoneyDetailsAdapter=new SendMoneyDetailsAdapter(getActivity(),sendReceiveMoneyList,coordinatorLayout);
            sendMoneyRV.setAdapter(sendMoneyDetailsAdapter);

        }else{
            Log.i("msgsss","data not avail");
            data_not_available.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.sendMoney).setVisible(true);
        super.onPrepareOptionsMenu(menu);

    }

}
