package com.paybyonline.KantipurPay.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.Model.ServiceType;
import com.paybyonline.KantipurPay.Adapter.ServiceTypeAdapter;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.imageLoader.ImageLoader;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class RechargeNowQuick extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener  {

    MyUserSessionManager myUserSessionManager;
    CoordinatorLayout coordinatorLayout;
    String servCatName;
    String servCatId;
    String [] id;
    String [] serviceTypeName;
    String [] logoName;
    String categoryLength;
    String startsWith;
    String maxVal;
    String minVal;
    String categoryType;
    String isPinRecharge;
    String pinCodePresent;
    String servlogoName;
    String     serviceAmountType;
    private String purchasedAmountType = "";
    private String rechargeTargetNumberErrorMsg;
    TextView serCatTxt;
    TextView serTxt;
    // TextView contactIcon;
    TextView purchaseAmount;
    TextView rechargeTargetNumberError;
    TextInputLayout serviceAmountValueEditText;
    EditText sending_amount;
    ListView weListView;
    private String purchasedAmountValue = "";
    Spinner serviceAmountValueSpinner;
    TextInputLayout sendingAmountWrapper ;
    TextInputLayout serviceAmountValueWrapper ;
    TextInputLayout numberWrapper ;
    private EditText confirmationPin;
    ImageView serTypeImg;
    private List<ServiceType> serviceTypeList;

    LinearLayout purLayout;
    LinearLayout rechargeForm;
    private String selectedServiceTypeId = "";
    ImageLoader imageloader;
    AlertDialog dialog;
    EditText pinEnterEditText;
    EditText pinReenterEditText;
    EditText enterPasswordEditText;
    Button rechargeBtn;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    RequestParams rechargeParams;
    TextView pinRequest;
    TextView pinCodeHeading;
    String serviceTypeId;
    String serviceCategoryId;
    String  servTypeName="";
    String servTypeId="";
    RelativeLayout loadingPanel;
    LinearLayout hideShowView;
    ServiceType serviceType;
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;
    public RechargeNowQuick() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recharge_now_test, container, false);
        ((DashBoardActivity) getActivity()).setTitle("Recharge");
        serCatTxt=(TextView)view.findViewById(R.id.serCatTxt);
        serTxt=(TextView)view.findViewById(R.id.serTxt);
        // contactIcon=(TextView)view.findViewById(R.id.contactIcon);
        // contactIcon.setOnClickListener(this);
        purchaseAmount=(TextView)view.findViewById(R.id.purchaseAmount);
        serviceAmountValueEditText=(TextInputLayout) view.findViewById(R.id.serviceAmountValueWrapper);
        sending_amount=(EditText)view.findViewById(R.id.sending_amount);
        rechargeTargetNumberError=(TextView)view.findViewById(R.id.rechargeTargetNumberError);
        serTypeImg=(ImageView)view.findViewById(R.id.serTypeImg);
        weListView=(ListView)view.findViewById(R.id.weListView);
        purLayout=(LinearLayout)view.findViewById(R.id.purLayout);
        loadingPanel=(RelativeLayout)view.findViewById(R.id.loadingPanel);
        rechargeForm=(LinearLayout)view.findViewById(R.id.rechargeForm);
        hideShowView=(LinearLayout)view.findViewById(R.id.hideShowView);
        // sendingAmountWrapper=(TextInputLayout)view.findViewById(R.id.sendingAmountWrapper);
        serviceAmountValueWrapper=(TextInputLayout)view.findViewById(R.id.serviceAmountValueWrapper);
        numberWrapper=(TextInputLayout)view.findViewById(R.id.numberWrapper);
        serviceAmountValueSpinner = (Spinner) view
                .findViewById(R.id.serviceAmountValueSpinner);
        imageloader = new ImageLoader(getContext());
        rechargeBtn=(Button)view.findViewById(R.id.rechargeBtn);
        rechargeBtn.setOnClickListener(this);
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
        Bundle bundle = getArguments();
        if (bundle != null) {

            servCatName = bundle.getString("servCatName");
            servCatId = bundle.getString("servCatId");
            servTypeName = bundle.getString("servTypeName");
            servTypeId = bundle.getString("servId");

        /*if((!(servTypeName).equals(""))&&!(servTypeId).equals("")){
                serCatTxt.setText(servCatName);

                obtainSelectedProductDetail(bundle.getString("servTypeName"),bundle.getString("servTypeId"));
            }*/

        }
        Log.i("values", servCatName + " " + servCatId);

        myUserSessionManager= new MyUserSessionManager(getContext());
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        serCatTxt.setText(servCatName);
        obtainProductDetail();

        return view;
    }
    public void obtainProductDetail() {

       /* showMyAlertProgressDialog.setRefreshActionButtonState(true,
                (MainActivity.optionsMenuRefresh));*/

        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "obtainSelectedProductDetail");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        // params.put("servCatName",servCatName);
        params.put("categoryId", servCatId);

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait...", params, new PboServerRequestListener() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    handleObtainServiceTypeDetailsResponse(response);

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }
        });

    }

    public void handleObtainServiceTypeDetailsResponse(JSONObject response) throws JSONException {


        Log.i("ServiceTypeDetails",response+"");
        JSONObject jsonObject=response.getJSONObject("data");
        setServiceDetailForm(jsonObject);
        // serviceCategoryTestListMap = new LinkedHashMap<String, ServiceTypeDetails>();
        JSONArray jArr = jsonObject.getJSONArray("categoryAllProducts");
        id =new String[jArr.length()];
        serviceTypeName =new String[jArr.length()];
        logoName =new String[jArr.length()];
        serviceTypeList=new ArrayList<>();

        for(int i=0;i<jArr.length();i++){

            JSONObject object= jArr.getJSONObject(i);
            id[i]= object.getString("id");
            serviceTypeName[i]= object.getString("serviceTypeName");
            logoName[i]= PayByOnlineConfig.BASE_URL+"CategoryTypeLogo/"+Uri.encode(object.getString("logoName"));
//            (String identification_label, String service_type_name, String service_type_id, String service_logo)
            serviceTypeList.add(new ServiceType("", object.getString("serviceTypeName"),object.getString("id")
                    , PayByOnlineConfig.BASE_URL+"CategoryTypeLogo/"+Uri.encode(object.getString("logoName"))));

        }
        ServiceTypeAdapter adapter = new ServiceTypeAdapter(
                getActivity(), serviceTypeName, logoName);

        //////////////set grid/////////////////////
        weListView.setDrawSelectorOnTop(true);


        weListView.setAdapter(adapter);
        weListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


           /*     final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Toast.makeText(getActivity(), "check", Toast.LENGTH_SHORT).show();
                        handler.postDelayed(this, 2000);
                    }
                }, 1500);*/
                //  flipTheView(view ,position);
                serviceType = serviceTypeList.get(position);
                serCatTxt.setText(servCatName);
                loadingPanel.setVisibility(View.VISIBLE);
                hideShowView.setVisibility(View.GONE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        hideShowView.setVisibility(View.VISIBLE);

                        obtainSelectedProductDetail(serviceType.getService_type_name(), serviceType.getService_type_id());
                    }
                }, 1000);

            }
        });

    }

    public void setServiceDetailForm(JSONObject jsonObject){
        loadingPanel.setVisibility(View.GONE);
        try{
            if(!jsonObject.equals("")){

                JSONObject jsonObjScst=jsonObject.getJSONObject("sCSTDetails");
                categoryLength = jsonObjScst.getString("categoryLength");
                startsWith = jsonObjScst.getString("startsWith");
                maxVal = jsonObjScst.getString("maxVal");
                minVal = jsonObjScst.getString("minVal");
                categoryType = jsonObjScst.getJSONObject("categoryType")
                        .getString("name");
                serviceTypeId = jsonObjScst.getJSONObject("serviceType")
                        .getString("id");
                serviceCategoryId = jsonObjScst.getJSONObject("serviceCategory")
                        .getString("id");
                serviceAmountType = jsonObjScst.getJSONObject("serviceAmountType")
                        .getString("name");
                isPinRecharge = jsonObjScst.getString("isPinRechargeService");
                pinCodePresent = jsonObject.getString("pinCodePresent");
                servlogoName= jsonObjScst.getString("logoName");
                imageloader.DisplayImage(PayByOnlineConfig.BASE_URL+"CategoryTypeLogo/" + servlogoName, serTypeImg);
                Log.i("iLabel", jsonObject.getString("iLabel") + "");
                // sending_amount.setHint(jsonObject.getString("iLabel"));
                numberWrapper.getEditText().setText("");
                serTxt.setText(jsonObject.getString("serviceTypeName"));
                if(jsonObjScst.getString("isPinRechargeService").equals("true")){

                    numberWrapper.setVisibility(View.GONE);
                    //       contactIcon.setVisibility(View.GONE);
                }else{

                    numberWrapper.setVisibility(View.VISIBLE);
//                    contactIcon.setVisibility(View.VISIBLE);
                    numberWrapper.setHint(jsonObject.getString("iLabel"));
                }

                // setting validation message
                setTargetNumberValidationMessage(jsonObject);
                // purchasedAmountType.setText(Html.fromHtml("Value " + stt.getCurrency()));
                // userDeviceDetails.showToast(stt.getPurchasedAmountType());
                purLayout.setVisibility(View.VISIBLE);
                if (serviceAmountType.equals("SELECT")) {

                    purchasedAmountType = "SELECT";
                    serviceAmountValueSpinner.setVisibility(View.VISIBLE);
                    //serviceAmountValueEditText.setVisibility(View.GONE);
                    serviceAmountValueWrapper.setVisibility(View.GONE);
                    String[] output = jsonObjScst.getString("serviceSelectValues").split(",");
                    Log.i("output",output+"");
                    ArrayAdapter adapter1 = new ArrayAdapter(getActivity(),
                            android.R.layout.simple_spinner_item, output);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    serviceAmountValueSpinner.setAdapter(adapter1);
                    serviceAmountValueSpinner
                            .setOnItemSelectedListener(onServiceAmountValueSelectedListener);
                } else {

                    purchasedAmountType = "";
                    purLayout.setVisibility(View.GONE);
                    serviceAmountValueWrapper.setHint("Enter Amount");
                    serviceAmountValueWrapper.getEditText().setText("");
                    serviceAmountValueWrapper.getEditText().setMaxLines(1);
                    serviceAmountValueWrapper.getEditText()
                            .setImeOptions(EditorInfo.IME_ACTION_DONE);
                    serviceAmountValueWrapper.setVisibility(View.VISIBLE);
                }

            }

        }
        catch(JSONException e){
            e.printStackTrace();
            Log.i("JSONException",""+e);
        }catch (Exception ex){
            ex.printStackTrace();
            Log.i("Exception", "" + ex);
        }


    }
    public void setTargetNumberValidationMessage(JSONObject json) {

        try {

            rechargeTargetNumberErrorMsg = json.getString("iLabel")
                    + " should have";
            if (purchasedAmountType.equals("Text")) {
                if (minVal.equals("null")) {
                    rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                            + "<br/>Min Val: ";
                } else {
                    rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                            + "<br/>Min Val: " + minVal;
                }

                if (maxVal.equals("null")) {

                    rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                            + "<br/>Max Val: ";
                } else {

                    rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                            + "<br/>Max Val: " + maxVal;
                }
            }
            if (startsWith.equals("null")) {

                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>Starts With: ";
            } else {
                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>Starts With: " + startsWith;
            }

            if (categoryLength.equals("null")) {
                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>" + json.getString("iLabel") + " Length: ";
            } else {
                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>" + json.getString("iLabel") + " Length: "
                        + categoryLength;
            }

            if (categoryType.equals("null")) {
                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>" + json.getString("iLabel") + " Type: ";
            } else {
                rechargeTargetNumberErrorMsg = rechargeTargetNumberErrorMsg
                        + "<br/>" + json.getString("iLabel") + " Type: "
                        + categoryType;
            }

            rechargeTargetNumberError.setText(Html
                    .fromHtml(rechargeTargetNumberErrorMsg));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    AdapterView.OnItemSelectedListener onServiceAmountValueSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (purchasedAmountType.equals("SELECT")) {
                purchasedAmountValue = serviceAmountValueSpinner
                        .getSelectedItem().toString();
                // viewRechargePayDetails();
            } else {
                purchasedAmountValue = serviceAmountValueEditText.getEditText().getText()
                        .toString();
                if ((purchasedAmountValue.length() > 0)) {
                    serviceAmountValueEditText
                            .setError("Purchase Value Required");
                } else {

                    //   viewRechargePayDetails();
                }

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    public void obtainSelectedProductDetail(String service_type_name,String service_type_id) {

       /* showMyAlertProgressDialog.setRefreshActionButtonState(true,
                (MainActivity.optionsMenuRefresh));*/

        serTxt.setText(service_type_name);
        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "productValidationDetails");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        // params.put("servCatName",servCatName);
        params.put("scstId", service_type_id);

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout, getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "", params, new PboServerRequestListener() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    handleObtainProductDetailsResponse(response);

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }
        });

    }

    public void handleObtainProductDetailsResponse(JSONObject response) throws JSONException {
        Log.i("productDetails", response + "");
        try {
            JSONObject data=response.getJSONObject("data");
            setServiceDetailForm(data);
        }catch (JSONException ex){
            Log.i("JSONException",""+ex);
            Toast.makeText(getContext(),"No data received",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.rechargeBtn:

                // for hiding keyboard on button click
                //  imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                rechargeTargetNumberError.setVisibility(View.GONE);
                Boolean continueRecharge = true;
                String purchasedValue = "";
                String targetNumberEditTextVal = numberWrapper.getEditText().getText()
                        .toString();

                if (isPinRecharge.equals("false")) {
                    if (targetNumberEditTextVal.length() > 0) {
                        if (purchasedAmountType.equals("SELECT")) {

                            purchasedAmountValue = purchasedValue = serviceAmountValueSpinner
                                    .getSelectedItem().toString();
                        } else {
                            if(purchasedValue.length()>0){
                                purchasedAmountValue = purchasedValue = serviceAmountValueEditText.getEditText()
                                        .getText().toString();
                                if (!minVal.equals("null")) {

                                    if ((Double.parseDouble(purchasedValue) < Double
                                            .parseDouble(minVal))) {
                                        continueRecharge = false;
                                    }
                                }

                                if (!maxVal.equals("null")) {

                                    if ((Double.parseDouble(purchasedValue) > Double
                                            .parseDouble(maxVal))) {
                                        continueRecharge = false;
                                    }
                                }
                            }else{
                                serviceAmountValueEditText.setError("Service Amount Required");
                            }

                        }

                        if (!categoryLength.equals("null")) {

                            if (!categoryLength.equals(targetNumberEditTextVal
                                    .length() + "")) {
                                continueRecharge = false;
                            }
                        }

                        if (!startsWith.equals("null")) {

                            int startsWithLength = startsWith.length();
                            String checkRechargeNumber = targetNumberEditTextVal
                                    .substring(0, startsWithLength);
                            if (!startsWith.equals(checkRechargeNumber)) {
                                continueRecharge = false;
                            }
                        }

                        if (continueRecharge) {

                            transactionConfirmModel();
                            // dialog.cancel();

                        } else {
                            // rechargeTargetNumberError.setBackgroundColor(Color.RED);
                            // rechargeTargetNumberError.setVisibility(View.VISIBLE);
                            numberWrapper.setError(rechargeTargetNumberError
                                    .getText());
                        }
                    } else {

                        numberWrapper.setError(numberWrapper.getEditText().getText()
                                + " Required");
                    }
                } else {

                    transactionConfirmModel();
                }
                break;
            case R.id.pinCodeRequest:

                pinRequest();

                break;
        /*    case R.id.contactIcon:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
                break;*/
        }
    }
    public void transactionConfirmModel() {

        rechargeTargetNumberErrorMsg="";
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogview = inflater.inflate(R.layout.confirmation_pin_code_form,
                null);
        confirmationPin = (EditText) dialogview
                .findViewById(R.id.confirmationPin);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                getActivity());
        dialog = dialogBuilder
                // .setMessage(ss)
                // .setIcon(R.drawable.ic_launcher)
                .setView(dialogview).setTitle("Transaction Confirmation")
                .setPositiveButton("Yes", this)
                .setNegativeButton("Cancel", this).setCancelable(false)
                .create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

        pinRequest = (TextView) dialogview
                .findViewById(R.id.pinCodeRequest);
        pinCodeHeading = (TextView) dialogview
                .findViewById(R.id.pinCodeHeading);

        if(pinCodePresent.equals("YES")){
            pinRequest.setVisibility(View.GONE);
            pinCodeHeading.setVisibility(View.GONE);
        }else{
            pinRequest.setVisibility(View.VISIBLE);
            pinCodeHeading.setVisibility(View.VISIBLE);
            pinRequest.setText("Create Pin Code ");
            pinRequest.setTextColor(Color.parseColor("#5F86C4"));
            pinRequest.setOnClickListener(this);
        }


        Button yesBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        yesBtn.setOnClickListener(new CustomListener(dialog));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;

        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }
        @Override
        public void onClick(View v) {

            // Do whatever you want here
            // If you want to close the dialog, uncomment the line below
            String minLength = "4";
            if (minLength.equals(confirmationPin.getText().toString().length()
                    + "")) {
                dialog.dismiss();
                // for hiding keyboard on button click
                // imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                performUserRecharge();
            } else {
                confirmationPin.setError("4 Digit Transaction Pin Code Required");
            }

        }
    }
    public void performUserRecharge() {

        // showMyAlertProgressDialog.setRefreshActionButtonState(true,
        // ((MainActivity)getActivity()).optionsMenuRefresh);


        rechargeParams=new RequestParams();

        rechargeParams.put("parentTask", "rechargeApp");
        rechargeParams.put("childTask", "saveRecharge");
        rechargeParams.put("userCode", myUserSessionManager.getSecurityCode());
        rechargeParams.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        rechargeParams.put("rechargeNumber", numberWrapper.getEditText().getText()
                .toString());
        rechargeParams.put("confirmationPin", confirmationPin.getText()
                .toString());
        rechargeParams.put("responseType", "JSON");
        rechargeParams.put("sCategory",servCatId );
        rechargeParams.put("serviceType", serviceTypeId);
        rechargeParams.put("purchasedValue", purchasedAmountValue);
        Log.i("rechargeParams",rechargeParams+"");
        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Saving Recharge Data", rechargeParams, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("jsonttttttttttttt", response + "");
                    handlePerformUserRechargeResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });

    }
    public void handlePerformUserRechargeResponse(JSONObject response) throws  JSONException{

        dialog.dismiss();

        try {

            JSONObject json = response;
            Log.i("jsonttttttttttttt", json + "");

            if (json.getString("msgTitle").equals("FAILED")) {

                if (json.getString("reason").equals("No Code")) {
                    showUserAlertDialogWithView(json.getString("msg"),
                            "msgTitle");
                } else {

                    showMyAlertProgressDialog.showUserAlertDialog(
                            json.getString("msg"),json.getString("msgTitle"));
                }

            } else {
                numberWrapper.getEditText().setText("");
                showMyAlertProgressDialog.showUserAlertDialog(
                        json.getString("msg"),json.getString("msgTitle"));

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void showUserAlertDialogWithView(String message, String title) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogview = inflater.inflate(R.layout.redirect_website_message,
                null);
        TextView redirectMsg = (TextView) dialogview
                .findViewById(R.id.redirectMsg);

        redirectMsg.setText(message);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                getActivity());
        final AlertDialog pindialog = dialogBuilder.setTitle(title)
                .setView(dialogview).setPositiveButton("Request Pin", this)
                .setNegativeButton("Cancel", this).setCancelable(false)
                .create();

        pindialog.show();
        Button saveBtn = pindialog.getButton(DialogInterface.BUTTON_POSITIVE);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pindialog.dismiss();

                pinRequest();
            }
        });

    }
    public void pinRequest() {

        dialog.dismiss();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogview = inflater.inflate(R.layout.request_pin_layout, null);
        pinEnterEditText = (EditText) dialogview.findViewById(R.id.enterPin);
        pinReenterEditText = (EditText) dialogview
                .findViewById(R.id.reenterPin);
        enterPasswordEditText = (EditText) dialogview
                .findViewById(R.id.confirmpassword);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                getActivity());
        AlertDialog pindialog = dialogBuilder
                // .setMessage(ss)
                // .setIcon(R.drawable.ic_launcher)
                .setView(dialogview).setTitle("Pin Code Request")
                .setPositiveButton("Save", this)
                .setNegativeButton("Cancel", this).setCancelable(false)
                .create();
        pindialog.show();
        pindialog.setCanceledOnTouchOutside(true);

        Button saveBtn = pindialog.getButton(DialogInterface.BUTTON_POSITIVE);
        saveBtn.setOnClickListener(new CustomListener2(pindialog));

    }
    class CustomListener2 implements View.OnClickListener {
        private final Dialog dialog;
        public CustomListener2(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {

            // Do whatever you want here
            // If you want to close the dialog, uncomment the line below
            String minLength = "4";

            if (!minLength.equals(pinEnterEditText.getText().toString()
                    .length()
                    + ""))
                pinEnterEditText
                        .setError("4 Digit Transaction Pin Code Required");

            if (!minLength.equals(pinReenterEditText.getText().toString()
                    .length()
                    + ""))
                pinReenterEditText
                        .setError("4 Digit Transaction Pin Code Required");

            if (enterPasswordEditText.getText().toString().isEmpty())
                enterPasswordEditText.setError("password required");

            if (pinEnterEditText.getText().toString().length() > 0
                    && pinReenterEditText.getText().toString().length() > 0
                    && enterPasswordEditText.getText().toString().length() > 0) {

                if (minLength.equals(pinEnterEditText.getText().toString()
                        .length()
                        + "")
                        && minLength.equals(pinReenterEditText.getText()
                        .toString().length()
                        + "")) {

                    if (pinEnterEditText.getText().toString()
                            .equals(pinReenterEditText.getText().toString())) {

						/*
						 * new HttpAsyncTaskSaveRechargePayDetails().execute(url
						 * + "/saveRecharge");
						 */
                        try {

                            RequestParams params = new RequestParams();
                            params.put("parentTask", "rechargeApp");
                            params.put("childTask", "saveUserPinCode");
                            params.put("userCode", myUserSessionManager.getSecurityCode());
                            params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
                            params.put("pin", pinEnterEditText.getText()
                                    .toString());
                            // Make Http call
                            PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
                            handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Saving Pin Details", params,
                                    new PboServerRequestListener() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                                            try {
                                                handlePerformUserRechargeResponse(response);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                                        }
                                    });

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } else {
                        pinEnterEditText.setError(" Pin not matched");
                    }

                } else {
                    pinEnterEditText
                            .setError(" 4 Digit Transaction Pin Code Required");
                }

            } else {
                enterPasswordEditText.setError("enter the data");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == getActivity().RESULT_OK) {
            Log.d("Contact Number:", "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactNumber();


        }
    }
    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID =getActivity(). getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d("Contact Id", "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone =getActivity(). getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();
        numberWrapper.getEditText().setText(contactNumber);
        Log.d("Contact Number", "Contact Phone Number: " + contactNumber);
    }
}


