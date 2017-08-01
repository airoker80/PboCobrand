package com.paybyonline.KantipurPay.Activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;
import com.paybyonline.KantipurPay.usersession.UserDeviceDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText emailTxt;
    Button rqstPwd;



    CoordinatorLayout coordinatorLayout;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    MyUserSessionManager myUserSessionManager;
    UserDeviceDetails userDeviceDetails;
    private HashMap<String, String> userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forget_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("FORGET PASSWORD");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(this);
        userDeviceDetails = new UserDeviceDetails(this);

        emailTxt=(EditText)findViewById(R.id.emailTxt);
        rqstPwd=(Button)findViewById(R.id.rqstPwd);
        rqstPwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String emailAddress = emailTxt.getText().toString();

                if (emailAddress.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    emailTxt.setError("Please enter a valid email address");
                } else {
                    forgetPasswordRequest();
                }

            }
        });

    }
      public void  forgetPasswordRequest(){


        RequestParams finalPayParams = new RequestParams();
        finalPayParams.put("username", emailTxt.getText().toString());
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,ForgetPasswordActivity.this);
        handler.makeRequest("forgotPassword", "Requesting Password Reset", finalPayParams, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleForgetPasswordRequest(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void  handleForgetPasswordRequest(JSONObject response) throws JSONException{

        showMyAlertProgressDialog.showUserAlertDialog(response.getString("msg"),response.getString("msgTitle"));
        if(response.getString("msgTitle").equals("Success")){
            emailTxt.setText("");
        }

    }

}
