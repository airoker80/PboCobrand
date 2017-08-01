package com.paybyonline.KantipurPay.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Adapter.Model.ServiceCategoryServiceTypeDetails;
import com.paybyonline.KantipurPay.Adapter.ServiceCategoryServiceTypeAdapter;
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
public class FavouriteServicesFragment extends Fragment {

    RecyclerView mRecyclerView;

    UserDeviceDetails userDeviceDetails;
    MyUserSessionManager myUserSessionManager;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    CoordinatorLayout coordinatorLayout;
    PboServerRequestHandler handler;
    List<ServiceCategoryServiceTypeDetails> serviceCategoryServiceTypeDetailsList = new ArrayList<ServiceCategoryServiceTypeDetails>();

    public FavouriteServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_services, container, false);
        getActivity().setTitle("Favourite Services");
        userDeviceDetails = new UserDeviceDetails(getActivity());
        myUserSessionManager = new MyUserSessionManager(getActivity());
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.fabServiceRecycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        obtainMyFavouriteServices();

        return  view;
    }

    public void obtainMyFavouriteServices() {

        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "myFavouriteServices");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());

        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleObtainMyFavouriteServicesResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void handleObtainMyFavouriteServicesResponse(JSONObject response) throws JSONException {

        try {

            JSONArray jsonArray = response.getJSONArray("data");
            serviceCategoryServiceTypeDetailsList = new ArrayList<ServiceCategoryServiceTypeDetails>();

            if (jsonArray.length() != 0) {

                // Loop through each array element, get JSON object
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);

                    serviceCategoryServiceTypeDetailsList.add(new ServiceCategoryServiceTypeDetails(
                            obj.get("service_category_name")
                                    .toString(), obj.get("service_type_name").toString()
                            ,obj.get("scId")
                            .toString(), obj.get("stId").toString()
                            ,obj.get("id").toString(),PayByOnlineConfig.BASE_URL+"CategoryTypeLogo/"+
                            Uri.encode(obj.get("logo_name").toString()),obj.getString("is_product_enable")));

                }

                ServiceCategoryServiceTypeAdapter adapter =new ServiceCategoryServiceTypeAdapter(getActivity(),
                        serviceCategoryServiceTypeDetailsList);

                mRecyclerView.setAdapter(adapter);

            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
