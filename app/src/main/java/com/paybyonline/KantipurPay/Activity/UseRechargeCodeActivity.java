package com.paybyonline.KantipurPay.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paybyonline.KantipurPay.R;

public class UseRechargeCodeActivity extends AppCompatActivity {

    EditText couponCodeEditTxt;
    EditText mobNoTxt;
    EditText confirmMobNoTxt;
    String confirmMobNo;
    String mobNo;
    String  couponCode;
    Button btnRcgh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_recharge_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        couponCodeEditTxt=(EditText)findViewById(R.id.couponCodeEditTxt);
        mobNoTxt=(EditText)findViewById(R.id.mobNoTxt);
        confirmMobNoTxt=(EditText)findViewById(R.id.confirmMobNoTxt);
        btnRcgh=(Button)findViewById(R.id.btnRchg);

        btnRcgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public  boolean checkText(){

        if (couponCodeEditTxt.getText().length()>0){

            couponCode=couponCodeEditTxt.getText().toString();

        }else{

            couponCodeEditTxt.setError("Enter the Coupon Code");
            return false;
        }
        if (mobNoTxt.getText().length()>0){

            mobNo=mobNoTxt.getText().toString();

        }else{

            mobNoTxt.setError("Enter the Coupon Code");
            return false;

        }
        if (confirmMobNoTxt.getText().length()>0){

            confirmMobNo=confirmMobNoTxt.getText().toString();

        }else{

            confirmMobNoTxt.setError("Enter Confirm Mobile Number");
            return false;

        }

        return true;
    }
  /* public void  HttpAsyncTaskUseRechargeCode(){


        RequestParams finalPayParams = new RequestParams();
        finalPayParams.put("parentTask", "userLogin");
        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,LoginActivity.this);
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Logging User", finalPayParams, new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleUseRechargeCode(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void  handleUseRechargeCode(JSONObject response) throws JSONException{
        JSONObject jo = null;

        if(response.equals("Success")){
            startActivity(new Intent(LoginActivity.this,DashBoardActivity.class));
        }else{
            userDeviceDetails.showToast("Some Error Occured");
        }

    }*/
}
