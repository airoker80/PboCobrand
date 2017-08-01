package com.paybyonline.KantipurPay.Fragment;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Helper.EndlessRecyclerOnScrollListener;
import com.paybyonline.KantipurPay.Adapter.Model.TransactionReportList;
import com.paybyonline.KantipurPay.Adapter.TransactionReportAdapter;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class TransactionReportFragment extends Fragment implements OnClickListener {

	// URL to get JSON Array
	private static String url;
	private int displayStart;
	private int displayEnd;
	private int totalReportCount;
	String status = "Initialized";

	private MyUserSessionManager myUserSessionManager;
	private UserDeviceDetails userDeviceDetails;
	private ShowMyAlertProgressDialog showMyAlertProgressDialog;
	private HashMap<String, String> userDetails;

	String searchParameter = "";
	String startDate="null";
	String endDate="null";
	String searchOrder="asc";
	String startEndSelectedDateBtn = "";

	private int year;
	private int month;
	private int day;

	Button setSelectedDateBtn;

	private List<TransactionReportList> listOfTransactionReport = new ArrayList<TransactionReportList>();
	private TransactionReportAdapter transactionReportListAdapter;
	private RecyclerView listView;
	CoordinatorLayout coordinatorLayout;
	private TextView openingBalance;

	DecimalFormat formatter = new DecimalFormat("0.00");
	private boolean _hasLoadedOnce= false; // your boolean field
	RelativeLayout data_not_available;

	public TransactionReportFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_transaction_report, container, false);

		url =  PayByOnlineConfig.SERVER_URL;

		userDeviceDetails = new UserDeviceDetails(getActivity());
		myUserSessionManager = new MyUserSessionManager(getActivity());
		showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
		userDetails = myUserSessionManager.getUserDetails();

		coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);

		listView = (RecyclerView) view.findViewById(R.id.transactionReportList);
		setHasOptionsMenu(true);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
		listView.setHasFixedSize(true);
		listView.setLayoutManager(mLayoutManager);
		listView.setItemAnimator(new DefaultItemAnimator());
		openingBalance = (TextView)view.findViewById(R.id.openingBalance);
		data_not_available=(RelativeLayout) view.findViewById(R.id.data_not_available);
		startDate ="null";
		endDate = "null";
		searchOrder = "asc";
		displayStart = 0;
		displayEnd = 10;
		status = "Initialized";
		listOfTransactionReport = new ArrayList<TransactionReportList>();

		setHasOptionsMenu(true);
		((DashBoardActivity) getActivity())
				.setTitle("Report");

		obtainTransactionReport();
		return view;

	}

	/*@Override
	public void setUserVisibleHint(boolean isFragmentVisible_) {
		super.setUserVisibleHint(true);

		if (this.isVisible()) {
			// we check that the fragment is becoming visible
			if (isFragmentVisible_ && !_hasLoadedOnce) {
				obtainTransactionReport();
			}
		}
	}*/

	public void obtainTransactionReport() {


		RequestParams params = new RequestParams();
		params.put("parentTask", "rechargeApp");
		params.put("childTask", "serverSideTransactionBook");
		params.put("userCode", myUserSessionManager.getSecurityCode());
		params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
		params.put("displayStart", displayStart + "");
		params.put("displayEnd", displayEnd + "");
		params.put("sortingCondition", searchOrder);
		params.put("timeZone", TimeZone.getDefault().getID());

		params.put("sDate", startDate);
		params.put("eDate", endDate);

		Log.i("msgsss ","params "+params);

		// Make Http call
		PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout, getActivity());
		handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait...", params,
				new PboServerRequestListener() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

						try {
							HttpAsyncTaskTransactionReportResponse(response);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						//Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
					}
				});

	}

	public void HttpAsyncTaskTransactionReportResponse(JSONObject response) throws JSONException {

		Log.i("HttpAsync Response ",response+"");

		JSONObject json;

		json = new JSONObject();
		try {

			json = response;

			if (response.getString("msgTitle").equals("Success")){

				JSONArray reportData = json.getJSONArray("aaData");
				Log.i("msgsss","reportData "+reportData);

				if (status.equals("Initialized")) {

					totalReportCount = Integer.parseInt(json
							.getString("iTotalRecords"));

					openingBalance.setText(Html.fromHtml("<b>Opening Balance: </b>"+json.getString("openingBalance")));

					if(reportData.length()>0){

						for (int i = 0; i < reportData.length(); i++) {

							JSONObject obs = reportData
									.getJSONObject(i);

							listOfTransactionReport.add(new TransactionReportList(
									obs.get("transactionDate").toString(),
									obs.get("transactionNo").toString(),
									obs.get("deposit").toString(),
									obs.get("payment").toString(),
									obs.get("balance").toString(),
									obs.get("description").toString(),
									obs.get("currency").toString()
							));

						}


						transactionReportListAdapter = new TransactionReportAdapter(getActivity(), listOfTransactionReport);

						if (transactionReportListAdapter != null) {

							displayStart = displayStart + 10;
							displayEnd = displayEnd + 10;

						}

						listView.setAdapter(transactionReportListAdapter);

						listView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
							@Override
							public void onLoadMore(int currentPage) {
								Log.i("load more ","displayStart TR : "+ displayStart);
								if(displayStart<totalReportCount) {
									obtainTransactionReport();
								}

							}
						});

					}else{
						data_not_available.setVisibility(View.VISIBLE);
					}



				}else if (status.equals("Loaded")) {

					displayStart = displayStart + 10;
					displayEnd = displayEnd + 10;
					transactionReportListAdapter.notifyDataSetChanged();

				}

			}else{

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub



	}

	private Boolean submitDateSearch(Spinner searchSpinner ,Button dateFrom,Button dateTo){

		try {

			String selectedItem=searchSpinner.getSelectedItem().toString();
			Log.i("selectedItem ",selectedItem);
			if(selectedItem.equals("All")){
				startDate = "null";
				endDate ="null";
				Log.i("selectedItem ","All selected");

				displayStart = 0;
				displayEnd = 10;
				status = "Initialized";
				listOfTransactionReport = new ArrayList<TransactionReportList>();
                 return true;
				//obtainTransactionReport();
			}else{
				if (searchParameter.equals("dateBetween")){
					Boolean result = verifyDateSearch(dateFrom.getText()
							.toString(), dateTo.getText().toString());
					if (result) {

						displayStart = 0;
						displayEnd = 10;
						status = "Initialized";
						listOfTransactionReport = new ArrayList<TransactionReportList>();
						return true;
						//obtainTransactionReport();

					} else {
						userDeviceDetails
								.showToast("Start date must be lesser or equal than to end date");
						return false;
					}
				}else{

					displayStart = 0;
					displayEnd = 10;
					status = "Initialized";
					listOfTransactionReport = new ArrayList<TransactionReportList>();
					return true;
//					obtainTransactionReport();
				}

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  false;

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

	public void setCurrentDateOnView(Button fromToBtn, String startEnd) {

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

			if (startEndSelectedDateBtn.equals("Start")){

				startDate = setSelectedDateBtn.getText().toString();

			}else {

				endDate = setSelectedDateBtn.getText().toString();

			}

		}
	};

	public void advanceSearchModel(Context context){
		Log.i("msgss","advanceSearchModel");
		//context
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
				.setPositiveButton("Search", null)
				.setNegativeButton("CANCEL", null)
				.create();

		final LayoutInflater inflater = LayoutInflater.from(context);
		View dialogView = inflater.inflate(R.layout.advance_search_form, null);
		alertDialog.setView(dialogView);
		alertDialog.setTitle("Advance Search");

		final Spinner searchSpinner;
		Spinner sortSpinner;
		final Button dateFrom;
		final Button dateTo;
		final LinearLayout dateSearchForm;

		dateSearchForm = (LinearLayout) dialogView.findViewById(R.id.dateSearchForm);
		dateFrom = (Button) dialogView.findViewById(R.id.dateFrom);
		dateTo = (Button) dialogView.findViewById(R.id.dateTo);



		dateFrom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePicker(dateFrom,"Start");
			}
		});
		dateTo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePicker(dateTo,"End");
			}
		});
		setCurrentDateOnView(dateFrom, "Start");
		setCurrentDateOnView(dateTo, "End");
		searchSpinner = (Spinner)dialogView.findViewById(R.id.transactionSpinner);
		searchSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
									   long id) {
				Log.i("spinner ", "select");
				// TODO Auto-generated method stub

				switch (pos) {

					case 0:
						searchParameter = "All";
						dateSearchForm.setVisibility(View.GONE);
						startDate = "null";
						endDate = "null";
						break;

					case 1:
						searchParameter = "last7";
						dateSearchForm.setVisibility(View.GONE);
						startDate = "checkLast7Start";
						endDate = "checkLast7End";
						break;

					case 2:
						searchParameter = "last30";
						dateSearchForm.setVisibility(View.GONE);
						startDate = "checkLast30Start";
						endDate = "checkLast30End";
						break;

					case 3:
						searchParameter = "dateBetween";

						Calendar cal = Calendar.getInstance();
						String currentDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) +
								"-" + cal.get(Calendar.DATE);
						startDate = currentDate;
						endDate = currentDate;
						dateFrom.setText(currentDate);
						dateTo.setText(currentDate);
						dateSearchForm.setVisibility(View.VISIBLE);

						break;

				}

				Log.i("posss ", parent.getItemAtPosition(pos).toString());

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sortSpinner=(Spinner)dialogView.findViewById(R.id.transactionSortSpinner);

		sortSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {

					case 0:
						searchOrder = "asc";
						break;

					case 1:
						searchOrder = "desc";
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				final Button btnAccept = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				btnAccept.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						if(submitDateSearch( searchSpinner , dateFrom, dateTo)){
							alertDialog.dismiss();
							obtainTransactionReport();
						}
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

	private void showDatePicker(Button fromToBtn, String startEnd) {

		setSelectedDateBtn = fromToBtn;
		startEndSelectedDateBtn = startEnd;
		DatePickerFragment date = new DatePickerFragment();
		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		String[] selDate = fromToBtn.getText().toString().split("-");

		args.putInt("year", Integer.parseInt(selDate[0]));
		args.putInt("month", Integer.parseInt(selDate[1]) - 1);
		args.putInt("day", Integer.parseInt(selDate[2]));
		date.setArguments(args);
		/**
		 * Set Call back to capture selected date
		 */
		date.setCallBack(ondate);
		date.show(getActivity().getSupportFragmentManager(), "Date Picker");
	}
	@Override
	public void onResume() {
		super.onResume();
		// Set title
//		getActivity().getActionBar().setTitle(R.string.app_name);
	}
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
//		menu.findItem(R.id.trans_adv_search).setVisible(true);
		super.onPrepareOptionsMenu(menu);

	}
}
