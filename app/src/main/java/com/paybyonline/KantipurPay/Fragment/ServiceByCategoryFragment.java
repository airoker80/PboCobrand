package com.paybyonline.KantipurPay.Fragment;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Model.MyOwnTags;
import com.paybyonline.KantipurPay.Adapter.Model.ServiceCategoryDetailsGrid;
import com.paybyonline.KantipurPay.Adapter.MyTagListAdapter;
import com.paybyonline.KantipurPay.Adapter.PopularServiceAdapter;
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
public class ServiceByCategoryFragment extends Fragment {

    RecyclerView mRecyclerView;

    UserDeviceDetails userDeviceDetails;
    MyUserSessionManager myUserSessionManager;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    CoordinatorLayout coordinatorLayout;
    PboServerRequestHandler handler;
    private HashMap<String, String> userDetails;
    ArrayList myOwnTagslist = new ArrayList<MyOwnTags>();
    private MyTagListAdapter listCall;
    ViewPager view_pager;
    int NUM_PAGES=8;
    LinearLayout dotsLayout;
    private List<ImageView> dots;
    TextView tagName;
    ImageView gridImage;
//    RecyclerView popularSerRv;
    // GridView popularSerRv;
   PopularServiceAdapter adapter;
    //////////////////////////////////////////////

    String[] serviceCatNameList = null;
    String[] imageIdList = null;
    String[] serviceCategoryId = null;
    private List<ServiceCategoryDetailsGrid> serviceCategoryServiceTypeDetails ;
    FloatingActionButton rechargeDetailReport;

    ////////////////////////////////////////////////////////
//    private boolean _hasLoadedOnce= false; // your boolean field


    public ServiceByCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_service_by_category, container, false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.serviceCatView);

//       popularSerRv=(RecyclerView)view.findViewById(R.id.popularSerRv);
//        popularSerRv.setHasFixedSize(true);
//        GridLayoutManager gridLayoutManager = new
//                GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
//        popularSerRv.setLayoutManager(gridLayoutManager);

      //  popularSerRv=(GridView)view.findViewById(R.id.popularSerRv);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        dotsLayout = (LinearLayout)view.findViewById(R.id.dots);
        userDeviceDetails = new UserDeviceDetails(getActivity());
        myUserSessionManager = new MyUserSessionManager(getActivity());
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
        userDetails = myUserSessionManager.getUserDetails();
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);

//        obtainMyServices();
//        obtainServiceCategoryTypeDetails();
       // addDots();
        rechargeDetailReport=(FloatingActionButton) view.findViewById(R.id.rechargeDetailReport);

        rechargeDetailReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashBoardActivity dashBoardActivity = (DashBoardActivity)getActivity();
                dashBoardActivity.selectPageToDisplay(R.id.purchaseReport,true,"2");
//                dashBoardActivity.viewRechargeReportFragment(R.id.purchaseReport,true,1);
            }
        });


        obtainMyServices();
        return  view;

    }

   /* @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);

        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                obtainMyServices();
//                _hasLoadedOnce = true;
            }
        }
    }
*/
    public void obtainMyServices() {
        Log.i("msgsss"," obtainMyServices");

        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "myServiceCategory");
        params.put("isNew", "YES");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        params.put("role", "ADMIN");
        handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());

        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait...", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleMyServiceCategoryResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void handleMyServiceCategoryResponse(JSONObject response) throws JSONException {

        try {

            JSONArray jsonArray = response.getJSONArray("data");
            myOwnTagslist = new ArrayList<MyOwnTags>();
            if (jsonArray.length() != 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    myOwnTagslist.add(new MyOwnTags(obj.get("tagName")
                            .toString(), obj.get("tagCount").toString(),obj.get("tagServices").toString()));
                }
            }

            listCall = new MyTagListAdapter(getActivity(), myOwnTagslist);
            if (listCall != null) {
                mRecyclerView.setAdapter(listCall);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void obtainServiceCategoryTypeDetails() {

        Log.i("obtainSerCatTypeDetails", "obtainServiceCategoryTypeDetails inside");
        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "obtainAvailableProducts");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());

        // Make Http call
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Obtaining Services", params, new PboServerRequestListener() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    handleObtainServiceCategoryTypeDetailsResponse(response);

                } catch (JSONException e) {

                    e.printStackTrace();

                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });

    }
    public void handleObtainServiceCategoryTypeDetailsResponse(JSONObject response) throws JSONException {


        System.out.println("DetailsResponse" + response);
        JSONArray jsonArray =response.getJSONArray("data");
        Log.i("jsonArray", jsonArray + "");


        serviceCatNameList =new String[jsonArray.length()];
        serviceCategoryId =new String[jsonArray.length()];
        imageIdList =new String[jsonArray.length()];
        serviceCategoryServiceTypeDetails=new ArrayList<>();


        if(jsonArray.length()>0){

            for(int i=0;i<jsonArray.length();i++){

                JSONObject obj= jsonArray.getJSONObject(i);
                Log.i("JSONObject",""+obj);

                // if(obj.getString("categoryName").equals("NTC")){
                serviceCatNameList[i] = obj.getString("categoryName");
                serviceCategoryId[i] = obj.getString("categoryId");

                if(obj.getString("scLogo").equals("null")){

                    imageIdList[i] = PayByOnlineConfig.BASE_URL +"merchantLogo/merchantLogo/"+
                            Uri.encode(obj.getString("merchantLogo"));

                    serviceCategoryServiceTypeDetails.add(new ServiceCategoryDetailsGrid(
                            obj.getString("categoryId"), obj.getString("categoryName"),
                            imageIdList[i],obj.getString("isMerchant"))) ;

                }else{

                    imageIdList[i] = PayByOnlineConfig.BASE_URL+"CategoryTypeLogo/"+
                            Uri.encode(obj.getString("scLogo"));


                    serviceCategoryServiceTypeDetails.add( new ServiceCategoryDetailsGrid(
                            obj.get("categoryId").toString(),
                            obj.getString("categoryName"),
                            imageIdList[i], obj.getString("isMerchant"))) ;

                }

                // }


            }

            adapter=new PopularServiceAdapter(getActivity(),serviceCategoryServiceTypeDetails);
//            popularSerRv.setAdapter(adapter);

            Log.e("response:",""+response);
        }


    }
    public void addDots() {

        dots = new ArrayList<>();

        //dots.get(0).setImageDrawable(getResources().getDrawable(R.drawable.select_item));
        for(int i = 0; i < NUM_PAGES; i++) {

            Log.i("NUM_PAGES_1", "" + NUM_PAGES);

            ImageView dot = new ImageView(getActivity());

            Drawable myDrawable = getResources().getDrawable( i == 0 ? R.drawable.select_item :
                    R.drawable.nonselect_item);

            dot.setImageDrawable(myDrawable);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            Log.i("params",""+params);
            Log.i("dotsLayout",""+dotsLayout);
            Log.i("dots",""+dots);
            dotsLayout.addView(dot, params);
            params.setMargins(4, 0, 4, 0);
            dots.add(dot);

        }

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Toast.makeText(getApplicationContext(),"Scrolled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(getApplicationContext(),"Selected", Toast.LENGTH_SHORT).show();
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Toast.makeText(getApplicationContext(),"Scrolled Changed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void selectDot(int idx) {

        Resources res = getResources();

        for(int i = 0; i < NUM_PAGES; i++) {

            Log.i("NUM_PAGES_2",""+NUM_PAGES);
            int drawableId = (i==idx)?(R.drawable.select_item):(R.drawable.nonselect_item);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);

        }
    }

}





