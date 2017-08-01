package com.paybyonline.KantipurPay.Fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Adapter.Helper.EndlessRecyclerOnScrollListener;
import com.paybyonline.KantipurPay.Adapter.Model.PurchaseReportList;
import com.paybyonline.KantipurPay.Adapter.PurchaseReportListAdapter;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class PurchaseReportFragment extends Fragment {

	// URL to get JSON Array
	private static String url;

	private int totalReportCount;

	private MyUserSessionManager myUserSessionManager;
	private UserDeviceDetails userDeviceDetails;
	private ShowMyAlertProgressDialog showMyAlertProgressDialog;
	private HashMap<String, String> userDetails;

	private List<PurchaseReportList> listOfPurchaseReport = new ArrayList<PurchaseReportList>();
	private PurchaseReportListAdapter purchaseReportListAdapter;
	private RecyclerView listView;
	SwipeRefreshLayout swipeContainer;
	DecimalFormat formatter = new DecimalFormat("0.00");
	CoordinatorLayout coordinatorLayout;

	int displayStart = 0;
	String status = "Initialized";
	private boolean _hasLoadedOnce= false; // your boolean field
	RelativeLayout data_not_available;
	public PurchaseReportFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_purchase_report,
				container, false);

		url = PayByOnlineConfig.SERVER_URL;

		userDeviceDetails = new UserDeviceDetails(getActivity());
		myUserSessionManager = new MyUserSessionManager(getActivity());
		showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
		userDetails = myUserSessionManager.getUserDetails();
		coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
		listView = (RecyclerView) view.findViewById(R.id.purchaseReportList);
		setHasOptionsMenu(true);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
		listView.setHasFixedSize(true);
		listView.setLayoutManager(mLayoutManager);
		listView.setItemAnimator(new DefaultItemAnimator());
		data_not_available=(RelativeLayout) view.findViewById(R.id.data_not_available);
		displayStart = 0;
		status = "Initialized";
		listOfPurchaseReport = new ArrayList<PurchaseReportList>();
		obtainPurchaseReport();
		return view;
	}

	/*@Override
	public void setUserVisibleHint(boolean isFragmentVisible_) {
		super.setUserVisibleHint(true);

		if (this.isVisible()) {
			// we check that the fragment is becoming visible
			if (isFragmentVisible_ && !_hasLoadedOnce) {
				obtainPurchaseReport();
				_hasLoadedOnce = true;
			}
		}
	}*/

	public void obtainPurchaseReport() {

		RequestParams params = new RequestParams();
		params.put("parentTask", "rechargeApp");
		params.put("childTask", "getPurchaseReport");
		params.put("userCode", myUserSessionManager.getSecurityCode());
		params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
		params.put("displayStart", displayStart + "");

		PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout, getActivity());
		handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait", params, new PboServerRequestListener() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

				try {
					handleObtainPurchaseReportResponse(response);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

	}

	public JSONObject handleObtainPurchaseReportResponse(JSONObject response) throws JSONException {

		JSONObject json;
		try {

			json = response;
			JSONArray reportData = json.getJSONArray("aaData");

			if(reportData.length()>0){

				for (int i = 0; i < reportData.length(); i++) {

					JSONObject obs = reportData.getJSONObject(i);
					listOfPurchaseReport.add(
							new PurchaseReportList(
									obs.get("transactionDate").toString(),
									obs.get("transactionNo").toString(),
									obs.get("serviceCat").toString(),
									obs.get("purchasedAmt").toString(),
									obs.get("toPay").toString(),
									obs.get("discount").toString(),
									obs.get("commission").toString(),
									obs.get("paidAmt").toString(),
									obs.get("type").toString(),
									obs.get("rewards").toString(),
									obs.get("dealAmount").toString(),
									obs.get("remark").toString()
							)
					);

				}


				if (status.equals("Initialized")) {
					totalReportCount = Integer.parseInt(json.getString("iTotalRecords"));

					Log.i("totalReportCount ","totalReportCount "+ totalReportCount);

					purchaseReportListAdapter = new PurchaseReportListAdapter(getActivity(), listOfPurchaseReport);

					listView.setAdapter(purchaseReportListAdapter);

					if (purchaseReportListAdapter != null) {
						displayStart = displayStart + 10;
					}

					listView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
						@Override
						public void onLoadMore(int currentPage) {
							Log.i("load more ","displayStart : "+ displayStart);
							if(displayStart<totalReportCount) {
								obtainPurchaseReport();
							}

						}
					});

					status = "Loaded";

				}else if (status.equals("Loaded")) {

					displayStart = displayStart + 10;
					purchaseReportListAdapter.notifyDataSetChanged();

				}

			}else{
				data_not_available.setVisibility(View.VISIBLE);
			}



		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}



