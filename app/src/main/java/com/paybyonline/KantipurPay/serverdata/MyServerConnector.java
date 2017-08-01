package com.paybyonline.KantipurPay.serverdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyServerConnector {

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;
		inputStream.close();
		return result;
	}

/*	public String postToRechargeServer(String url,
			List<NameValuePair> urlParameters) throws JSONException {
		InputStream inputStream = null;
		String result = "";
		
		// HttpParams params = new BasicHttpParams();
		 // set the connection timeout value to 30 seconds (30000 milliseconds)
//		 HttpConnectionParams.setConnectionTimeout(params, 10000);
//		 HttpConnectionParams.setSoTimeout(params, 1);
//		 DefaultHttpClient clients = new DefaultHttpClient(params);
		
		Log.i("url",url);
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpResponse response = null;
		String connectionCode;
		JSONObject res = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			response = client.execute(post);
			// receive response as inputStream
			inputStream = response.getEntity().getContent();
			connectionCode =response.getStatusLine().toString();
			Log.i("connection", response.getStatusLine()
					.getStatusCode()+"");
			// convert inputstream to string
			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);
			} else {
				result = "Did not work!";
			}
//			JSONObject res = null;
			*//*if (response.getStatusLine().getStatusCode() >= 200
					&& response.getStatusLine().getStatusCode() <= 299) {*//*
			if (response.getStatusLine().getStatusCode() == 200) {
				res = new JSONObject(result.toString());
			} else {
				res = new JSONObject();
			}
			
			res.put("connectionStatus", response.getStatusLine()
					.getStatusCode()+"");
			Log.i("connection stat", response.getStatusLine()
					.getStatusCode()+"");
			client.getConnectionManager().shutdown();
			//Log.i("result ",res.toString());
			return res.toString();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			res = new JSONObject();
			res.put("connectionStatus", "failed");	
			Log.e("UnsupportedEncodingException", e1+"");
			return res.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = new JSONObject();
			res.put("connectionStatus", "failed");	
			Log.e("ClientProtocolException", e+"");
			return res.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = new JSONObject();
			res.put("connectionStatus", "failed");	
			Log.e("IOException", e+"");
			return res.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = new JSONObject();
			res.put("connectionStatus", "failed");	
			Log.e("JSONException", e+"");
			return res.toString();
		}
		
	}*/
	
}
