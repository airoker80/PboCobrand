package com.paybyonline.KantipurPay.serverdata;



import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Anish on 2/17/2016.
 */

public interface PboServerRequestListener {

    public void onSuccess(int statusCode, Header[] headers, JSONObject response) throws JSONException;

}