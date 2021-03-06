package com.paybyonline.KantipurPay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;
import com.paybyonline.KantipurPay.usersession.UserDeviceDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn;
    TextView forgetPwdBtn;
    Button rechargeCodeBtn;
    TextView createAccBtn;
    EditText emailTxt;
    EditText pwdTxt;
    String email;
    String pwd;
    CoordinatorLayout coordinatorLayout;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    MyUserSessionManager myUserSessionManager;
    UserDeviceDetails userDeviceDetails;
    private HashMap<String, String> userDetails;
    PboServerRequestHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getActionBar().setDisplayShowTitleEnabled(false);
        // Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_login);
        myUserSessionManager = new MyUserSessionManager(this);
        if (myUserSessionManager.isUserLoggedIn()) {

            if (myUserSessionManager.ifUserHasCountry()) {
//				userDeviceDetails.showToast("user has country");
                startActivity(new Intent(getApplicationContext(),
                        DashBoardActivity.class));

            } else {
//				userDeviceDetails.showToast("user has no country");
                HttpAsyncTaskCountryList();
                //  startActivity(new Intent(this, CountrySelectionActivity.class));
            }
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(this);
        userDeviceDetails = new UserDeviceDetails(this);


        emailTxt = (EditText) findViewById(R.id.emailTxt);
        pwdTxt = (EditText) findViewById(R.id.pwdTxt);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        forgetPwdBtn = (TextView) findViewById(R.id.forgetPwdBtn);
        rechargeCodeBtn = (Button) findViewById(R.id.rechargeCodeBtn);
        createAccBtn = (TextView) findViewById(R.id.createAccBtn);

        emailTxt = (EditText) findViewById(R.id.emailTxt);
        pwdTxt = (EditText) findViewById(R.id.pwdTxt);

        loginBtn.setOnClickListener(this);
        forgetPwdBtn.setOnClickListener(this);
        rechargeCodeBtn.setOnClickListener(this);
        createAccBtn.setOnClickListener(this);

    }

    public boolean checkText() {

        if (emailTxt.getText().length() > 0) {
            email = emailTxt.getText().toString();
            emailTxt.setError(null);
        }else {
            emailTxt.setError("Enter Email");
            return false;
        }
        if (pwdTxt.getText().length() > 0) {

            pwd = pwdTxt.getText().toString();
            pwdTxt.setError(null);


        } else {

            pwdTxt.setError("Enter Password");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {


        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bink);
        switch (v.getId()) {

            case R.id.loginBtn:
                 loginBtn.startAnimation(animation1);
                if (checkText()) {

                    HttpAsyncTask();
                }

               // startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
                break;

            case R.id.forgetPwdBtn:
                forgetPwdBtn.startAnimation(animation1);
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;


            case R.id.rechargeCodeBtn:
                 rechargeCodeBtn.startAnimation(animation1);
                startActivity(new Intent(LoginActivity.this, UseRechargeCodeActivity.class));
                break;


            case R.id.createAccBtn:
                //  creatAccBtn.startAnimation(animation1);
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
                break;

        }

    }

    public void HttpAsyncTask() {


        RequestParams params = new RequestParams();
        Log.i("userLogin", "Processing");
        params.put("password", pwd);
        params.put("username", email);
        Log.i("userLogin", "params "+params);
        // Make Http call
        handler = PboServerRequestHandler.getInstance(coordinatorLayout, this);

        handler.makeRequest("userLogin", "Please Wait !!!", params, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("userLogin", "userLogin1");
                    HttpAsyncTaskResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void HttpAsyncTaskResponse(JSONObject response) throws JSONException {


        JSONObject json;
        String loginStatus = "";
        String username = "";
        String connectionStatus;

        try {
            //  loginBtn.setEnabled(true);
            json = response;
            Log.i("HttpAsyncTaskResponse", "" + response);


            loginStatus = json.getString("msgTitle");

            if(loginStatus.equals("Success")){

                myUserSessionManager.saveUserInformation(username,
                        response.getString("authenticationCode"), response.getString("userCode"));

                if (json.getString("countryAdded").toString()
                        .equals("present")) {
                    myUserSessionManager.addUserCountry();
                }

                if(myUserSessionManager.ifUserHasCountry()){
                    startActivity(new Intent(getApplicationContext(),
                            DashBoardActivity.class));
                }else{
//                    HttpAsyncTaskCountryList();
                    startActivity(new Intent(getApplicationContext(), AfterRegistration.class));
                }
                finish();
            } else {

                Toast.makeText(getApplicationContext(), json.getString("msg"), Toast.LENGTH_SHORT).show();
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

    public void HttpAsyncTaskCountryList() {

        RequestParams params = new RequestParams();
        // settings = getSharedPreferences(PREFS_NAME,
        // Context.MODE_PRIVATE);
        userDetails = myUserSessionManager.getUserDetails();
        Log.i("userDetails", userDetails + "");
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "getCountryDetails");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        params.put("username", userDetails.get(MyUserSessionManager.KEY_USERNAME).toString());

        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout, LoginActivity.this);
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            HttpAsyncTaskCountryListResponse(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                    }
                });


    }

    public void HttpAsyncTaskCountryListResponse(JSONObject response) throws JSONException {

        JSONObject json;
        String connectionStatus;
        json = new JSONObject();
        try {

            json = response;
            Log.i("Result", "" + response);
            //connectionStatus = json.getString("connectionStatus");
            if (json.getString("countryAdded").equals("present")) {

                myUserSessionManager.addUserCountry();


                Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            } else if (json.getString("countryAdded").equals(
                    "NotPresent")) {

                JSONObject countryDetails = json
                        .getJSONObject("result");
                Log.i("Result", "" + json.getJSONObject("result"));

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


}
