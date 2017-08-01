package com.paybyonline.KantipurPay.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.BuyPageAdapter;
import com.paybyonline.KantipurPay.Adapter.Model.BuyPageModel;
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

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyProductFragment extends Fragment {

    MyUserSessionManager myUserSessionManager;
    UserDeviceDetails userDeviceDetails;
    CoordinatorLayout coordinateLayout;
    RecyclerView productList;
    ArrayList<BuyPageModel> listOfProduct = new ArrayList<BuyPageModel>();
    BuyPageAdapter buyPageAdapter;
    private GridLayoutManager lLayout;
    FloatingActionButton rechargeDetailReport;

    public BuyProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buy_product, container, false);
        myUserSessionManager = new MyUserSessionManager(getActivity());
        userDeviceDetails = new UserDeviceDetails(getActivity());
        coordinateLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        productList=(RecyclerView)view.findViewById(R.id.ServiceProviderListView);
        // int mNoOfColumns = calculateNoOfColumns();
       Log.i("calculateNoOfColumns",""+calculateNoOfColumns());
        lLayout = new GridLayoutManager(getActivity(), calculateNoOfColumns());

      //  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        productList.setHasFixedSize(true);
        productList.setLayoutManager(lLayout);

        rechargeDetailReport=(FloatingActionButton) view.findViewById(R.id.rechargeDetailReport);

        rechargeDetailReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashBoardActivity dashBoardActivity = (DashBoardActivity)getActivity();
                dashBoardActivity.selectPageToDisplay(R.id.purchaseReport,true,"1");
            }
        });


        getProductListContent();
         return view;
    }
    public void getProductListContent( ){

        RequestParams params = new RequestParams();
        // Make Http call
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "listMerchantProduct");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());



        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinateLayout,getActivity());
        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            Log.i("Listresponse", "response");
                            handleProductListContentResponse(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    public void handleProductListContentResponse(JSONObject response) throws JSONException {

        Log.i("Listresponse", "response");
        Log.i("Listresponse", response + "");
        listOfProduct = new ArrayList<BuyPageModel>();

        JSONArray productData = response.getJSONArray("advanceList");
        if(productData.length() > 0){

            for (int i = 0; i < productData.length(); i++) {

                JSONObject obs = productData.getJSONObject(i);

                Log.i("obs", obs + "");
                // Boolean isParticipated = obs.getString("isParticipated").equals("YES") ? true : false;

                listOfProduct.add(new BuyPageModel(obs.getString("serviceCategory"),
                        obs.getString("serviceType"), obs.getString("serviceClassification"),
                        obs.getString("tag"), obs.getString("productType"),
                                PayByOnlineConfig.BASE_URL+"serviceCategoryServiceTypeLogo/"+
                                        Uri.encode(obs.getString("logoName").toString()), obs.getString("scstId"),
                        obs.getString("merchantType"), obs.getString("countryName")));

            }
            buyPageAdapter = new BuyPageAdapter(
                    getActivity(), listOfProduct);
            productList.setAdapter(buyPageAdapter);
        }else{
            // emptyMessage.setVisibility(View.VISIBLE);
        }



    }
    public  int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
