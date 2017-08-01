package com.paybyonline.KantipurPay.Fragment;

import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.RequestParams;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ReportPieChartFragment extends Fragment implements
		OnChartValueSelectedListener, OnClickListener {

	private PieChart mChart;
	protected String[] categoryName;
	protected String[] categoryAmount;

	Spinner searchSpinner;
	// DatePicker myDatePicker;
	Button dateFrom;
	Button dateTo;
	Button searchDataBetweenDate;
/*	TextView dateFromText;
	TextView dateToText;*/
	LinearLayout dateSearchForm;
	
	private MyUserSessionManager myUserSessionManager;
	private UserDeviceDetails userDeviceDetails;
	private ShowMyAlertProgressDialog showMyAlertProgressDialog;
	private HashMap<String, String> userDetails;

	private static String url;
	String searchParameter = "";

	private int year;
	private int month;
	private int day;
//	TextView setSelectedDate;
	Button setSelectedDateBtn;
	Boolean donotCallSpinner = true;
	CoordinatorLayout coordinatorLayout;
	public ReportPieChartFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_report_pie_chart,
				container, false);
		donotCallSpinner = true ;

		coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
		userDeviceDetails = new UserDeviceDetails(getActivity());
		myUserSessionManager = new MyUserSessionManager(getActivity());
		showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
		userDetails = myUserSessionManager.getUserDetails();

		url =  PayByOnlineConfig.SERVER_URL;
		mChart = (PieChart) view.findViewById(R.id.chart1);

		// myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
		dateSearchForm = (LinearLayout) view.findViewById(R.id.dateSearchForm);
		dateFrom = (Button) view.findViewById(R.id.dateFrom);
		dateTo = (Button) view.findViewById(R.id.dateTo);
		searchDataBetweenDate = (Button) view
				.findViewById(R.id.searchDataBetweenDate);
		dateFrom.setOnClickListener(this);
		dateTo.setOnClickListener(this);
		searchDataBetweenDate.setOnClickListener(this);
		/*dateFromText = (TextView) view.findViewById(R.id.dateFromText);
		dateToText = (TextView) view.findViewById(R.id.dateToText);
*/
		setCurrentDateOnView(dateFrom);
		setCurrentDateOnView(dateTo);

		// setCurrentDateOnView();
		searchSpinner = (Spinner) view.findViewById(R.id.pieChartSpinner);
		searchSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
									   long id) {
				// TODO Auto-generated method stub
				// userDeviceDetails.showToast(parent.getItemAtPosition(pos).toString());
				// String selectedItem =
				// parent.getItemAtPosition(pos).toString();

				if(donotCallSpinner){
					donotCallSpinner = false;
					searchParameter = "last7";
					dateSearchForm.setVisibility(View.GONE);
					connectWithServer();
				}else{
					switch (pos) {
						case 0:
							searchParameter = "last7";
							dateSearchForm.setVisibility(View.GONE);
							connectWithServer();
							break;
						case 1:
							searchParameter = "last30";
							dateSearchForm.setVisibility(View.GONE);
							connectWithServer();
							break;
						default:
							searchParameter = "dateBetween";
							dateSearchForm.setVisibility(View.VISIBLE);
//						submitDateSearch();
							break;
					}
				}

//				userDeviceDetails.showToast(parent.getItemAtPosition(pos).toString());
				Log.i("posss ", parent.getItemAtPosition(pos).toString());

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		/*//default 7 days
		searchParameter = "last7";
		connectWithServer();*/

		return view;
	}

	public void setCurrentDateOnView(Button fromToBtn) {

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		fromToBtn.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(year).append("-").append(month + 1).append("-")
				.append(day));

	}

	private void connectWithServer() {
		if (!userDeviceDetails.isConnected()) {
			userDeviceDetails
					.showToast("Please Check your Internet Connection");
		} else {

			/*new HttpAsyncTaskGetPieChartData()
					.execute(url + "/getPieChartData");*/
			HttpAsyncTaskGetPieChartData();
		}
	}

	private void showDatePicker(Button fromToBtn) {
		setSelectedDateBtn = fromToBtn;
		DatePickerFragment date = new DatePickerFragment();
		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		String[] selDate = fromToBtn.getText().toString().split("-");
		/*args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));*/
		args.putInt("year", Integer.parseInt(selDate[0]));
		args.putInt("month", Integer.parseInt(selDate[1])-1);
		args.putInt("day", Integer.parseInt(selDate[2]));
		date.setArguments(args);
		/**
		 * Set Call back to capture selected date
		 */
		date.setCallBack(ondate);
		date.show(getActivity().getSupportFragmentManager(), "Date Picker");
	}

	OnDateSetListener ondate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
			/*Toast.makeText(
					getActivity(),
					String.valueOf(year) + "-" + String.valueOf(monthOfYear)
							+ "-" + String.valueOf(dayOfMonth),
					Toast.LENGTH_LONG).show();*/

			setSelectedDateBtn.setText(String.valueOf(year) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(dayOfMonth));
		}
	};

	// ///////////////////////////////////////////////////////////////////////////
	/*private class HttpAsyncTaskGetPieChartData extends
			AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			*//*showMyAlertProgressDialog.showProgressDialogCancelable("Processing...",
					"Please wait.");*//*
			showMyAlertProgressDialog.setRefreshActionButtonState(true, ((MainActivity)getActivity()).optionsMenuRefresh);
		}

		@Override
		protected String doInBackground(String... urls) {
			MyServerConnector myServerConnector = new MyServerConnector();
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("username", userDetails
					.get(MyUserSessionManager.KEY_USERNAME).toString()));
			urlParameters.add(new BasicNameValuePair("criteria",
					searchParameter));

			try {
				return myServerConnector.postToRechargeServer(urls[0],
						urlParameters);
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
				return res.toString();
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONObject json;
			String connectionStatus;
			json = new JSONObject();
			try {

				json = new JSONObject(result);
				connectionStatus = json.getString("connectionStatus");
				//showMyAlertProgressDialog.hideProgressDialog();
				showMyAlertProgressDialog.setRefreshActionButtonState(false, ((MainActivity)getActivity()).optionsMenuRefresh);
				if (connectionStatus.equals("failed")
						|| (!connectionStatus.equals("200"))) {
					Log.i("Conn", connectionStatus);
					userDeviceDetails
							.showToast("Some Error Occurred. Please try again later !!!");

				} else {
					JSONArray output = json.getJSONArray("categoryDetails");

					Boolean paidHistoryFlag = false;
					if (output.length() > 0) {
						categoryName = new String[output.length()];
						categoryAmount = new String[output.length()];
						paidHistoryFlag = true;
					}

					for (int i = 0; i < output.length(); i++) {

						JSONObject obs = output.getJSONObject(i);

						categoryName[i] = obs.get("category").toString();
						categoryAmount[i] = obs.get("amount").toString();

					}

					// preparing pie chart;
					if (paidHistoryFlag) {
						prepareChart();
					} else {
						mChart.clear();
						//userDeviceDetails.showToast("No History Available");

					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}*/
	public void HttpAsyncTaskGetPieChartData() {



		RequestParams params = new RequestParams();
		params.put("parentTask", "rechargeApp");
		params.put("childTask", "getPieChartData");
		params.put("userCode", myUserSessionManager.getSecurityCode());
		params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
		params.put("criteria",
				searchParameter);

		// Make Http call
		PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
		handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
//		handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params, new PboServerRequestListener() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

				try {
					HttpAsyncTaskGetPieChartDataResponse(response);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
			}
		});

	}

	public void HttpAsyncTaskGetPieChartDataResponse(JSONObject response) throws  JSONException{

		JSONObject json;
		String connectionStatus;
		json = new JSONObject();
		try {

			json = response;
			//	connectionStatus = json.getString("connectionStatus");
			//showMyAlertProgressDialog.hideProgressDialog();


			JSONArray output = json.getJSONArray("categoryDetails");

			Boolean paidHistoryFlag = false;
			if (output.length() > 0) {
				categoryName = new String[output.length()];
				categoryAmount = new String[output.length()];
				paidHistoryFlag = true;
			}

			for (int i = 0; i < output.length(); i++) {

				JSONObject obs = output.getJSONObject(i);

				categoryName[i] = obs.get("category").toString();
				categoryAmount[i] = obs.get("amount").toString();

			}

			// preparing pie chart;
			if (paidHistoryFlag) {
				prepareChart();
			} else {
				mChart.clear();
				//userDeviceDetails.showToast("No History Available");

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

	private void prepareChart() {

		// change the color of the center-hole
		mChart.setHoleColor(Color.rgb(235, 235, 235));

		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				"OpenSans-Regular.ttf");

		//mChart.setValueTypeface(tf);
		mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity()
				.getAssets(), "OpenSans-Light.ttf"));

		// mChart.setHoleRadius(60f);

		// radius of the center hole in percent of maximum radius
		// mChart.setHoleRadius(45f);
		// mChart.setTransparentCircleRadius(50f);

		mChart.setDescription("Recharge Transaction");

		//to use default formatting of chart values
		//mChart.setValueFormatter(null);

		//mChart.setDrawYValues(true);
		// mChart.setDrawCenterText(true);

		 mChart.setDrawHoleEnabled(true);
//		mChart.setDrawHoleEnabled(false);

		mChart.setRotationAngle(0);

		// draws the corresponding description value into the slice
		//mChart.setDrawXValues(true);

		// enable rotation of the chart by touch
		mChart.setRotationEnabled(false);

		// display percentage values
		mChart.setUsePercentValues(true);
		// mChart.setUnit(" €");
		// mChart.setDrawUnitsInChart(true);

		// add a selection listener
		mChart.setOnChartValueSelectedListener(this);
		mChart.setTouchEnabled(true);

		// mChart.setCenterText("Recharge Summary");

		// setData(category.length, 100);
		setData(categoryName.length, 100);

		mChart.animateXY(1500, 1500);
		// mChart.spin(2000, 0, 360);

		Legend l = mChart.getLegend();
		l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(5f);
	}

	private void setData(int count, float range) {

		float mult = range;

		ArrayList<Entry> yVals1 = new ArrayList<Entry>();

		// IMPORTANT: In a PieChart, no values (Entry) should have the same
		// xIndex (even if from different DataSets), since no values can be
		// drawn above each other.

		for (int i = 0; i < count; i++) {
			Log.i("categoryAmount ", categoryAmount[i] + "");
			float amt = Float.parseFloat(categoryAmount[i]);
			yVals1.add(new Entry(amt, i));

		}

		// categoryPercent
		Log.i("yVals1 ", yVals1 + "");

		ArrayList<String> xVals = new ArrayList<String>();

		for (int i = 0; i < count; i++) {
			xVals.add(categoryName[i % categoryName.length]);
		}
		Log.i("xVals ", xVals + "");

		PieDataSet set1 = new PieDataSet(yVals1, "Recharge Summary");
		set1.setSliceSpace(3f);

		// add a lot of colors
		ArrayList<Integer> colors = new ArrayList<Integer>();

		for (int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.LIBERTY_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.PASTEL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);

		colors.add(ColorTemplate.getHoloBlue());

		set1.setColors(colors);

		PieData data = new PieData(xVals, set1);
		mChart.setData(data);

		// undo all highlights
		mChart.highlightValues(null);

		mChart.invalidate();
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		if (e == null)
			return;
		Log.i("VAL SELECTED",
				"Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
						+ ", DataSet index: " + dataSetIndex);
//		userDeviceDetails.showToast("Value: " + e.getVal());
	}

	@Override
	public void onNothingSelected() {
		Log.i("PieChart", "nothing selected");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
			case R.id.dateFrom:
				showDatePicker(dateFrom);
				break;
			case R.id.dateTo:
				showDatePicker(dateTo);
				break;
			case R.id.searchDataBetweenDate:

				submitDateSearch();

				break;

			default:
				break;
		}

	}

	private void submitDateSearch(){

		try {
			Boolean result = verifyDateSearch(dateFrom.getText()
					.toString(), dateTo.getText().toString());
			if (result) {
				searchParameter = dateFrom.getText().toString() + ","
						+ dateTo.getText().toString();
				connectWithServer();
			} else {
				userDeviceDetails
						.showToast("Start date must be lesser or equal than to end date");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private Boolean verifyDateSearch(String dateFromText, String dateToText)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = sdf.parse(dateFromText);
		Date toDate = sdf.parse(dateToText);
		Boolean returnValue = false;
		if (fromDate.compareTo(toDate) > 0) {
			// userDeviceDetails.showToast("Date1 is after Date2");
			returnValue = false;
		} else if (fromDate.compareTo(toDate) < 0) {
			returnValue = true;
			// userDeviceDetails.showToast("Date1 is before Date2");
		} else if (fromDate.compareTo(toDate) == 0) {
			returnValue = true;
			// userDeviceDetails.showToast("Date1 is equal to Date2");
		} else {
			returnValue = false;
			// userDeviceDetails.showToast("How to get here?");
		}

		return returnValue;
	}

}
