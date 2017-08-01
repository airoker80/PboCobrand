package com.paybyonline.KantipurPay.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.util.BasicPageData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class AccountSummaryFragment extends Fragment {

//	private MyUserSessionManager myUserSessionManager;
//	private CoordinatorLayout coordinatorLayout;
//	private PboServerRequestHandler handler;

	private LinearLayout head_dp;
	private LinearLayout child_dp;
	private LinearLayout head_dis;
	private LinearLayout child_dis;
	private LinearLayout head_com;
	private LinearLayout child_com;
	private LinearLayout head_rew;
	private LinearLayout child_rew;
	private LinearLayout head_par;
	private LinearLayout child_par;

	private TextView info_textDep;
	private TextView info_textTotPay;
	private TextView textTotWD;
	private TextView textTotPT;
	private TextView textTotMS;
	private TextView idTotWD;
	private TextView idTotPT;
	private TextView idTotMS;
	private TextView idRwdPt;
	private TextView idTotRwdPt;
	private TextView idReg;
	private TextView idEarnP;
	private TextView idTotPtU;
	private TextView idTotPur;
	private TextView idEarnPt;
	private TextView idEarnPurPt;
	private TextView totRwd;

	private Boolean onClickDp=false;
	private Boolean onClickD=false;
	private Boolean onClickCsc=false;
	private Boolean onClickR=false;
	private Boolean onClickP=false;

	private DecimalFormat formatter = new DecimalFormat("0.00");

	private BasicPageData basicPageData;

	public AccountSummaryFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_summary_report, container,
				false);

		initializeComponents(view);
		obtainProfileSummary();

		//show deposit and payment by default
		closeAllSummaryTabs();
		child_dp.setVisibility(View.VISIBLE);
		onClickDp=true;
		//show deposit and payment by default ends

		return view;
	}


	public void initializeComponents(View view){


		basicPageData = new BasicPageData(getActivity(),view);

		head_dp = (LinearLayout) view.findViewById(R.id.head_dp);
		child_dp = (LinearLayout) view.findViewById(R.id.child_dp);
		head_dis = (LinearLayout) view.findViewById(R.id.head_dis);
		child_dis = (LinearLayout) view.findViewById(R.id.child_dis);
		head_com = (LinearLayout) view.findViewById(R.id.head_com);
		child_com = (LinearLayout) view.findViewById(R.id.child_com);
		head_rew = (LinearLayout) view.findViewById(R.id.head_rew);
		child_rew = (LinearLayout) view.findViewById(R.id.child_rew);
		head_par = (LinearLayout) view.findViewById(R.id.head_par);
		child_par = (LinearLayout) view.findViewById(R.id.child_par);

		head_dp.setVisibility(View.VISIBLE);
		head_dis.setVisibility(View.VISIBLE);
		head_com.setVisibility(View.VISIBLE);
		head_rew.setVisibility(View.VISIBLE);
		head_par.setVisibility(View.VISIBLE);

		info_textDep = (TextView) view.findViewById(R.id.info_textDep);
		info_textTotPay = (TextView) view.findViewById(R.id.info_textTotPay);

		textTotWD = (TextView) view.findViewById(R.id.textTotWD);
		textTotPT = (TextView) view.findViewById(R.id.textTotPt);
		textTotMS = (TextView) view.findViewById(R.id.textTotMS);

		idTotWD = (TextView) view.findViewById(R.id.idTotWD);
		idTotPT = (TextView) view.findViewById(R.id.idTotPT);
		idTotMS = (TextView) view.findViewById(R.id.idTotMS);

		idRwdPt = (TextView) view.findViewById(R.id.idRwdPt);
		idTotRwdPt = (TextView) view.findViewById(R.id.idTotRwdPt);
		totRwd = (TextView) view.findViewById(R.id.totRwd);
		idReg = (TextView) view.findViewById(R.id.idReg);

		idEarnP = (TextView) view.findViewById(R.id.idEarnP);
		idTotPtU = (TextView) view.findViewById(R.id.idTotPtU);
		idTotPur = (TextView) view.findViewById(R.id.idTotPur);
		idEarnPt = (TextView) view.findViewById(R.id.idEarnPt);
		idEarnPurPt = (TextView) view.findViewById(R.id.idEarnPurPt);

		head_dp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeAllSummaryTabs();
				if(onClickDp){
					onClickDp=false;
				}else{
					child_dp.setVisibility(View.VISIBLE);
					onClickDp=true;
				}

			}
		});

		head_dis.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeAllSummaryTabs();
				if(onClickD){
					onClickD=false;
				}else{
					child_dis.setVisibility(View.VISIBLE);
					onClickD=true;
				}

			}
		});

		head_com.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeAllSummaryTabs();
				if(onClickCsc){
					onClickCsc=false;
				}else{
					child_com.setVisibility(View.VISIBLE);
					onClickCsc=true;
				}

			}
		});

		head_rew.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeAllSummaryTabs();
				if(onClickR){
					onClickR=false;
				}else{
					child_rew.setVisibility(View.VISIBLE);
					onClickR=true;
				}

			}
		});

		head_par.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeAllSummaryTabs();
				if(onClickP){
					onClickP=false;
				}else{
					onClickP=true;
					child_par.setVisibility(View.VISIBLE);
				}

			}
		});

	}

	public void closeAllSummaryTabs(){
		onClickDp=false;
		onClickD=false;
		onClickCsc=false;
		onClickR=false;
		onClickP=false;
		child_dp.setVisibility(View.GONE);
		child_dis.setVisibility(View.GONE);
		child_com.setVisibility(View.GONE);
		child_rew.setVisibility(View.GONE);
		child_par.setVisibility(View.GONE);
	}

	public void obtainProfileSummary() {

		RequestParams params = new RequestParams();
		params.put("parentTask", "rechargeApp");
		params.put("childTask", "getAccountSummary");
		params.put("userCode", basicPageData.getMyUserSessionManager().getSecurityCode());
		params.put("authenticationCode", basicPageData.getMyUserSessionManager().getAuthenticationCode());
		params.put("role", "USER");

		basicPageData.getPboServerRequestHandler().makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

				try {
					handleObtainProfileSummaryResponse(response);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});


	}

	public void handleObtainProfileSummaryResponse(JSONObject response) throws JSONException {

		try {

			JSONObject jsonObject = response;
			Log.i("res ", "" + jsonObject);

			if (jsonObject.getString("msg").equals("Success")) {


				if(response.has("userDetails")){
					JSONObject userDetails = response.getJSONObject("userDetails");

					DashBoardActivity dashBoardActivity = (DashBoardActivity) getContext();
					String userBalance = userDetails.getString("userBalance");
					String userCountry = "";
					if(userDetails.has("userCountry")){
						 userCountry = userDetails.getString("userCountry");
					}

					String currencyCode = userDetails.getString("currencyCode");
					String hasPinCode = userDetails.has("hasPinCode") ? userDetails.getString("hasPinCode") : "NO";
					String userPhoto = userDetails.getString("userPhoto");
					String notViewNotification = userDetails.getString("notViewNotification");
					String favouriteServicesCount = userDetails.getString("favouriteServicesCount");
					Double holdMoneyAmount = Double.parseDouble(userDetails.getString("holdMoneyAmount"));
					Log.i("holdMoneyAmount","holdMoneyAmount "+holdMoneyAmount);


					if(userDetails.has("gnData")){
						JSONObject obj = userDetails.getJSONObject("gnData");
						dashBoardActivity.setUserDetails(userCountry,currencyCode,userBalance,userPhoto,notViewNotification,favouriteServicesCount,hasPinCode,holdMoneyAmount,
								obj.getString("Viber").toString(),
								obj.getString("Facebook").toString(),
								obj.getString("Twitter").toString(),
								obj.getString("Google_plus").toString(),
								obj.getString("Gmail").toString(),
								obj.getString("Yahoo").toString(),
								obj.getString("Skype").toString(),
								obj.getString("Linkedin").toString(),
								obj.getString("Microsoft_Outlook").toString());
					}else{
						dashBoardActivity.setUserDetails(userCountry,currencyCode,userBalance,userPhoto,notViewNotification,favouriteServicesCount,hasPinCode,holdMoneyAmount,"", "", "", "", "", "", "", "", "");
					}

				}
				String currencySymbol = response.getString("currencySymbol");

				info_textDep.setText(currencySymbol+" "+ formatter.format(Double.parseDouble(jsonObject.getString("totalDeposit"))));
				info_textTotPay.setText(currencySymbol+" "+ formatter.format(Double.parseDouble(jsonObject.getString("totalPayment"))));

				///////////////////////////////////////////////////////////////////////////

				textTotWD.setText(currencySymbol+" "+ formatter.format(Double.parseDouble(jsonObject.getString("totalWdDiscount"))));
				textTotPT.setText(currencySymbol+" "+ formatter.format(Double.parseDouble(jsonObject.getString("totalPtDiscount"))));
				textTotMS.setText(currencySymbol+" "+ formatter.format(Double.parseDouble(jsonObject.getString("totalMsDiscount"))));


				//////////////////////////////////////////////////////////////////////////////////
				idTotWD.setText(currencySymbol+" "+ formatter.format(Double.parseDouble(jsonObject.getString("totalWdCommission"))));
				idTotPT.setText(currencySymbol+" "+ formatter.format(Double.parseDouble(jsonObject.getString("totalPtCommission"))));
				idTotMS.setText(currencySymbol+" "+ formatter.format(Double.parseDouble(jsonObject.getString("totalMsCommission"))));

				///////////////////////////////////////////////////////////////////////////////


				totRwd.setText( (jsonObject.getString("reducedRp").equals("null")) ? "0" : jsonObject.getString("reducedRp"));
				idRwdPt.setText(jsonObject.getString("myRewardPoint"));
				idTotRwdPt.setText(jsonObject.getString("myRewardPoint"));
				idReg.setText(jsonObject.getString("RegistrationLogIn"));
				idEarnP.setText((jsonObject.getString("scstRp").equals("null")) ? "0" : jsonObject.getString("scstRp"));


				idTotPtU.setText(jsonObject.getString("totalClient"));
				idTotPur.setText(currencySymbol+" "+ formatter.format(Double.parseDouble(jsonObject.getString("clientPurchaseAmount"))));
				idEarnPt.setText(jsonObject.getString("clientRegReward"));
				idEarnPurPt.setText(jsonObject.getString("clientPurchaseReward"));

				/////////////////////////////////////////////////////////////////////////////
			}

		}catch(JSONException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
