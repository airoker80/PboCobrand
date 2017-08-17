package com.paybyonline.KantipurPay.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class InitialLoadingActivity extends AppCompatActivity {
    String primaryEmail,username="",brandImage="",brandName="";
    String isVerified;
    MyUserSessionManager myUserSessionManager;
    ImageView ivPlayOverlay;
    ImageView refresh;
    CoordinatorLayout coordinatorLayout;
    PboServerRequestHandler pboServerRequestHandler;
    PackageInfo pInfo = null;
    RequestParams requestParams = new RequestParams();
    ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myUserSessionManager=new MyUserSessionManager(getApplicationContext());
//        getUserEmailVerificationStatus();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        progressbar=(ProgressBar)findViewById(R.id.progressbar);
        ivPlayOverlay=(ImageView)findViewById(R.id.ivPlayOverlay);
        refresh=(ImageView)findViewById(R.id.refresh);
        ViewCompat.setTranslationZ(ivPlayOverlay, 0);
        ivPlayOverlay.bringToFront();
        pboServerRequestHandler = PboServerRequestHandler.getInstance(coordinatorLayout,InitialLoadingActivity.this);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.GONE);
//                getUserEmailVerificationStatus();
                doVersionCheck();
//                loadAppPages();
            }
        });

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            requestParams.put("installedAppVersionName", pInfo.versionName+"");
//            requestParams.put("installedAppVersionCode", pInfo.versionCode+"");
//            requestParams.put("applicationName", getPackageName()+"");
//
            requestParams.put("installedAppVersionName", "1.0.10");
            requestParams.put("installedAppVersionCode", "10");
            requestParams.put("applicationName", "com.gulfsewa.paybyonline");

        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }catch (Exception ex){

        }

//        loadAppPages();
        if(pboServerRequestHandler.isConnected()){
//            getUserEmailVerificationStatus();
            doVersionCheck();

        }

    }


    public void doVersionCheck() {
        pboServerRequestHandler.makeRequest("verifyInstalledApp", "Please Wait !!!", requestParams, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                pboServerRequestHandler.showProgressDialog("Please Wait !!!");
                try {
                    Log.i("params",""+requestParams);
                    handleVersionCheckResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    pboServerRequestHandler.hideProgressDialog();
                }
            }
        });
    }
    public void handleVersionCheckResponse(JSONObject response) throws JSONException{

        JSONObject jsonObject;
        try {
            jsonObject = response;
            	Log.i("VersionCheck", "" + response);
            pboServerRequestHandler.hideProgressDialog();
            if(jsonObject.getString("isLatest").equals("NO")){
                appUpdateModel();
            }else{
//                loadAppPages();
                getUserEmailVerificationStatus();

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            pboServerRequestHandler.hideProgressDialog();
        }


    }

    public void  appUpdateModel(){

        final AlertDialog builder = new AlertDialog.Builder(this)
                .setPositiveButton("UPDATE", null)
//                .setNegativeButton("CANCEL", null)
                .setMessage("New version of application is available in play store. " +
                        "Please update.")
                .setTitle("Update Information")
                .setCancelable(false)
                .create();
        this.setFinishOnTouchOutside(false);
        builder.setCanceledOnTouchOutside(false);
        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button btnAccept = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();

                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            finish();
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            finish();
                        }

                    }
                });
               /* final Button btnCancel = builder.getButton(AlertDialog.BUTTON_NEGATIVE);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();

                    }
                });*/

            }
        });

        builder.show();

    }

    public void loadAppPages(){
        progressbar.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        if(pboServerRequestHandler.isConnected()){

            Log.e("isVerified","--->"+isVerified);
            if (isVerified.equals("Yes")){
                startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                InitialLoadingActivity.this.finish();
            }else {
                Intent intent = new Intent(this,UserEmailVerificationActivity.class);
                Log.e("putextras", username + brandImage + brandName);
                intent.putExtra("brandName",brandName);
                intent.putExtra("username",username);
                intent.putExtra("brandImage",brandImage);
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), UserEmailVerificationActivity.class));
                InitialLoadingActivity.this.finish();
            }

        }
/*        if(pboServerRequestHandler.isConnected()){startActivity(new Intent(getApplicationContext(), UserEmailVerificationActivity.class));
            InitialLoadingActivity.this.finish();
        }*/
    }

    private void getUserEmailVerificationStatus(){
        RequestParams params=new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "getEmailVerificationStatus");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        //   params.put("authenticationCode", userSessionManager.getAuthenticationCode());

        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,
                InitialLoadingActivity.this);
        Log.e("params==",params.toString());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) throws JSONException {

                        Log.i("PboServerRequestHandler", "dashboard");
                        handleStatusResponse(response);
                        //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void handleStatusResponse(JSONObject response) throws JSONException {

        Log.e("responseEmail",response.toString());
        isVerified=response.getString("isVerified");
        if (isVerified.equals("No")){
            username=response.getString("username");
            brandImage=response.getString("brandImage");
            brandName=response.getString("brandName");
        }

/*        if (isVerified.equals("No")){
            primaryEmail = response.getString("primaryEmail");
        }*/
        loadAppPages();
    }

}
