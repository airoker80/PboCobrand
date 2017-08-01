package com.paybyonline.KantipurPay.Fragment;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Model.UserNotification;
import com.paybyonline.KantipurPay.Adapter.UserNotificationAdapter;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
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
public class UserNotificationFragment extends Fragment {

    MyUserSessionManager myUserSessionManager;
    UserDeviceDetails userDeviceDetails;
    CoordinatorLayout coordinateLayout;
    List<UserNotification> userNotificationList = new ArrayList<UserNotification>();

    RecyclerView userNotificationRV;
    UserNotificationAdapter userNotificationAdapter;
    RelativeLayout data_not_available;

    public UserNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_notification, container, false);
        ((DashBoardActivity) getActivity())
                .setTitle("Notification");

        myUserSessionManager = new MyUserSessionManager(getActivity());
        userDeviceDetails = new UserDeviceDetails(getActivity());
        coordinateLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);

        userNotificationRV=(RecyclerView)view.findViewById(R.id.userNotificationRV);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        userNotificationRV.setHasFixedSize(true);
        userNotificationRV.setLayoutManager(mLayoutManager);
        data_not_available=(RelativeLayout) view.findViewById(R.id.data_not_available);
        getNotificationList();

        return view;
    }


    public void getNotificationList( ){

        RequestParams params = new RequestParams();
        // Make Http call
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "getNotificationDetails");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());



        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinateLayout,getActivity());
        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait...", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            Log.i("Listresponse", "response");
                            handleGetNotificationListResponse(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    public void handleGetNotificationListResponse(JSONObject response) throws JSONException{
        try {

            JSONObject jsonObject = response.getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("aaData");
            userNotificationList = new ArrayList<UserNotification>();

            if (jsonArray.length() != 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
//                    String message, String logo, String category, String createdDate
                    userNotificationList.add(new UserNotification(
                            obj.get("message").toString(),
                            obj.get("logo").toString(),
                            obj.get("category").toString(),
                            obj.get("createdDate").toString()));
                }
                userNotificationAdapter = new UserNotificationAdapter(getActivity(), userNotificationList);
                userNotificationRV.setAdapter(userNotificationAdapter);
            }else{
                data_not_available.setVisibility(View.VISIBLE);
            }




        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
