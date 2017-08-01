package com.paybyonline.KantipurPay.Fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Helper.EndlessRecyclerOnScrollListener;
import com.paybyonline.KantipurPay.Adapter.Model.RechargeDetailsData;
import com.paybyonline.KantipurPay.Adapter.RechargeDetailsListAdapter;
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
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class RechargedDetailsFragment extends Fragment {

	private List<RechargeDetailsData> rechargeDetailsDataList = new ArrayList<RechargeDetailsData>();
	private RechargeDetailsListAdapter rechargeDetailsListAdapter;
	RecyclerView rechargeDetailsListView;
    CoordinatorLayout coordinatorLayout;
	private MyUserSessionManager myUserSessionManager;
	private UserDeviceDetails userDeviceDetails;
	private ShowMyAlertProgressDialog showMyAlertProgressDialog;
	private HashMap<String, String> userDetails;

	private int totalBalanceCount;

	// URL to get JSON Array
	private static String url;
	int displayStart = 0;
	String status = "Initialized";
	RelativeLayout data_not_available;
	FloatingActionButton addRechargeDetail;

	public RechargedDetailsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_recharged_details,
				container, false);

		coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
		userDeviceDetails = new UserDeviceDetails(getActivity());
		myUserSessionManager = new MyUserSessionManager(getActivity());
		showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
		userDetails = myUserSessionManager.getUserDetails();
		rechargeDetailsListView = (RecyclerView) view
				.findViewById(R.id.rechargeDetailsListView);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
		rechargeDetailsListView.setHasFixedSize(true);
		rechargeDetailsListView.setLayoutManager(mLayoutManager);
		url =  PayByOnlineConfig.SERVER_URL;
		data_not_available=(RelativeLayout) view.findViewById(R.id.data_not_available);
		addRechargeDetail=(FloatingActionButton) view.findViewById(R.id.addRechargeDetail);

		addRechargeDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DashBoardActivity dashBoardActivity = (DashBoardActivity)getActivity();
				String returnPage = "";
				Bundle b = getArguments();
				if(b!=null){
					returnPage = b.getString("returnPage");
					if(returnPage==null){
						returnPage = "0";
					}
				}else{
					returnPage = "0";
				}


				dashBoardActivity.selectPageToDisplay(R.id.buyPage,true,returnPage);
			}
		});

		displayStart = 0;
		status = "Initialized";
		rechargeDetailsDataList = new ArrayList<RechargeDetailsData>();
		obtainMyRechargeDetails();

		return view;

	}

	public void obtainMyRechargeDetails() {


		RequestParams params = new RequestParams();
		params.put("parentTask", "rechargeApp");
		params.put("childTask", "obtainMyRechargeDetails");
		params.put("userCode", myUserSessionManager.getSecurityCode());
		params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
		//userDeviceDetails.showToast(displayStart+"");
		params.put("displayStart", displayStart+"");

		// Make Http call
		PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout, getActivity());
		handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait...", params, new PboServerRequestListener() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

				try {
					handleObtainMyRechargeDetailsResponse(response);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

	}

	public void handleObtainMyRechargeDetailsResponse(JSONObject response) throws JSONException {

		try {
			JSONObject json = response;

			JSONArray userBalanceRequestList = json.getJSONArray("aaData");

			if(userBalanceRequestList.length()>0){
				for (int i = 0; i < userBalanceRequestList.length(); i++) {

					JSONObject obs = userBalanceRequestList.getJSONObject(i);

					rechargeDetailsDataList.add(new RechargeDetailsData(obs
									.getString("transactionDate").toString(), obs
									.getString("transactionNo").toString(), obs
									.getString("serviceCategoryType").toString(), obs
									.getString("serviceCategoryAmount").toString(), obs
									.getString("purchasedAmount").toString(), obs
									.getString("payTypeDiscount").toString(), obs
									.getString("payTypeCommission").toString(), obs
									.getString("netCostAmount").toString(), obs
									.getString("rechargedNumber").toString(), obs
									.getString("remarks").toString(),obs
									.getString("dealDiscount").toString(),
									obs.getString("reward").toString(),
									obs.getString("pspAmount").toString(),
									obs.getString("eWalletAmount").toString()
							)
					);

				}

				if (status.equals("Initialized")) {

					totalBalanceCount = Integer.parseInt(json
							.getString("iTotalRecords"));

					rechargeDetailsListAdapter = new RechargeDetailsListAdapter(
							getActivity(), rechargeDetailsDataList);
					rechargeDetailsListView.setAdapter(rechargeDetailsListAdapter);

					if (rechargeDetailsListAdapter != null) {
						displayStart = displayStart + 10;
					}


					rechargeDetailsListView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
						@Override
						public void onLoadMore(int currentPage) {
							if(displayStart<totalBalanceCount) {
								obtainMyRechargeDetails();
							}

						}
					});

					status = "Loaded";
				}else if (status.equals("Loaded")) {

					displayStart = displayStart + 10;
					rechargeDetailsListAdapter.notifyDataSetChanged();

				}
			}else{
				data_not_available.setVisibility(View.VISIBLE);
			}



		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
