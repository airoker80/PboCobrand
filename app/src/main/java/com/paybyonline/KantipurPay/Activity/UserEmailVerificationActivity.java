package com.paybyonline.KantipurPay.Activity;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UserEmailVerificationActivity extends AppCompatActivity implements View.OnClickListener {
    String primaryEmail,username,brandImage,brandName;
    Button verifiyAccountEmail,submitEmail;
    TextView updateEmail,gotoDashbord,verficationUsername,welcome_to,verifiy_details_txt,teamId;
    EditText et_email_address;
    LinearLayout hiddenLinearLayout,gone_et_layout;
    ImageView brandImageView;
    MyUserSessionManager myUserSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myUserSessionManager = new MyUserSessionManager(UserEmailVerificationActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        verifiyAccountEmail=(Button)findViewById(R.id.verifiyAccountEmail);
        submitEmail=(Button)findViewById(R.id.submitEmail);
        updateEmail=(TextView) findViewById(R.id.updateEmail);
        gotoDashbord=(TextView) findViewById(R.id.gotoDashbord);
        welcome_to=(TextView) findViewById(R.id.welcome_to);
        verifiy_details_txt=(TextView) findViewById(R.id.verifiy_details_txt);
        verficationUsername=(TextView) findViewById(R.id.verficationUsername);
        teamId=(TextView) findViewById(R.id.teamId);
        et_email_address=(EditText) findViewById(R.id.et_email_address);
        brandImageView=(ImageView) findViewById(R.id.brandImageView);

        hiddenLinearLayout=(LinearLayout) findViewById(R.id.hiddenLinearLayout);
        gone_et_layout=(LinearLayout) findViewById(R.id.gone_et_layout);

        Bundle extras = getIntent().getExtras();
        username=extras.getString("username");
        brandImage=extras.getString("brandImage");
        brandName=extras.getString("brandName");

        Log.e("extras", "===>"+username + brandImage);


        verficationUsername.setText("Hello "+username+",");
        verifiyAccountEmail.setOnClickListener(this);
        submitEmail.setOnClickListener(this);
        gotoDashbord.setOnClickListener(this);
        updateEmail.setOnClickListener(this);
        if (!brandImage.equals("")){
            Picasso.with(this)
                    .load(brandImage)
                    .placeholder(R.mipmap.loading)
                    .error(R.mipmap.noimg)
                    .into(brandImageView);
        }




        if (!brandName.equals("")){
            verifiy_details_txt.setText("Verifying your "+ brandName+" account helps make "+ brandName +" even safer for everyone. When you’re verified, it means that you’ve provided a little more information about yourself to help confirm your identity. Please verify your account by clicking on button below.");
            teamId.setText(brandName+" Team");
            welcome_to.setText("Welcome To "+brandName);
        }

    }

    @Override
    public void onClick(View v) {
        int selectedId=v.getId();
        switch (selectedId){
            case R.id.verifiyAccountEmail:
                sendMailExistingUser();
                break;
            case R.id.gotoDashbord:
                startActivity(new Intent(UserEmailVerificationActivity.this,DashBoardActivity.class));
                finish();
                break;
            case R.id.updateEmail:
                gone_et_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.submitEmail:
                updateSecMail();
                break;
        }

    }

    CoordinatorLayout coordinatorLayout;
    private void sendMailExistingUser(){
        RequestParams params=new RequestParams();
        params.put("parentTask", "kyc");
        params.put("childTask", "sendMailExistingUser");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        //   params.put("authenticationCode", userSessionManager.getAuthenticationCode());

        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,
                UserEmailVerificationActivity.this);
        Log.e("params==",params.toString());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Processing..", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) throws JSONException {

                        Log.i("PboServerRequestHandler", "dashboard");
                        handleSentMailResponse(response);
                        //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void handleSentMailResponse(JSONObject response) throws JSONException {
            Log.e("responsesentMail",response.toString());
        if (response.getString("msgTitle").equals("Success")){
            hiddenLinearLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this, response.getString("msg").toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void updateSecMail(){
        RequestParams params=new RequestParams();
        params.put("parentTask", "kyc");
        params.put("childTask", "updateSecMail");
        params.put("getEmail", et_email_address.getText().toString());
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        //   params.put("authenticationCode", userSessionManager.getAuthenticationCode());

        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,
                UserEmailVerificationActivity.this);
        Log.e("params==",params.toString());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Updating..", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) throws JSONException {

                        Log.i("PboServerRequestHandler", "dashboard");
                        handleUodateMailResponse(response);
                        //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void handleUodateMailResponse(JSONObject response) throws JSONException {
        Log.e("responseUpdateMail",response.toString());
        if (response.getString("msgTitle").equals("Success")){
//            hiddenLinearLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this, response.getString("msg").toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
