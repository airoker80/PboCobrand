package com.paybyonline.KantipurPay.usersession;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public class UserDeviceDetails {

	static Context context;

	public UserDeviceDetails(Context context) {
		super();
		this.context = context;
	}

	public boolean isEmailValid(CharSequence email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	public void showToast(String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		// toast.setGravity (Gravity.TOP | Gravity.LEFT, 100, 100);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

	// returns unique Id of user device
	public String getUserDeviceId() {
		
		String resultId = "";

		PackageManager pm = context.getPackageManager();

		// Check if telephone service available for IMEI
		if (pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
			TelephonyManager TelephonyMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String szImei = TelephonyMgr.getDeviceId(); // Requires
														// READ_PHONE_STATE
			resultId += szImei;
		}

		// Check if wifi service available
		if (pm.hasSystemFeature(PackageManager.FEATURE_WIFI)) {
			WifiManager wm = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
			resultId += m_szWLANMAC;
		}
		
		String m_szAndroidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		// Sometimes Android Id is returned null, so check for it
		if(m_szAndroidID != null && (m_szAndroidID.length()>0)){
			resultId += m_szAndroidID;
		}
		if(resultId.length()>0){
			HashingData hashingData = new HashingData();
			resultId = hashingData.getSha256HashData(resultId);
		}

		return resultId;
	}

	public String getUserOsDetails() {

		String userAndroidOsVersion = "";

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion == android.os.Build.VERSION_CODES.ECLAIR) {
			userAndroidOsVersion = "ECLAIR 2.0";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.ECLAIR_0_1) {
			userAndroidOsVersion = "ECLAIR_0_1 2.0.1";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			userAndroidOsVersion = "ECLAIR_MR1 2.1";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.FROYO) {
			userAndroidOsVersion = "FROYO 2.2";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.GINGERBREAD) {
			userAndroidOsVersion = "GINGERBREAD 2.3";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
			userAndroidOsVersion = "GINGERBREAD_MR1 2.3.3";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.HONEYCOMB) {
			userAndroidOsVersion = "HONEYCOMB 3.0";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.HONEYCOMB_MR1) {
			userAndroidOsVersion = "HONEYCOMB_MR1 3.1";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
			userAndroidOsVersion = "HONEYCOMB_MR2 3.2";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			userAndroidOsVersion = "ICE_CREAM_SANDWICH 4.0";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
			userAndroidOsVersion = "ICE_CREAM_SANDWICH_MR1 4.0.3";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.JELLY_BEAN) {
			userAndroidOsVersion = "JELLY_BEAN 4.1";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
			userAndroidOsVersion = "JELLY_BEAN_MR1 4.2";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
			userAndroidOsVersion = "JELLY_BEAN_MR2 4.3";
		}
		if (currentapiVersion == android.os.Build.VERSION_CODES.KITKAT) {
			userAndroidOsVersion = "KITKAT 4.4";
		}

		return userAndroidOsVersion;
	}

	/** Get IP For mobile */
	public String getUserMobileIP() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						String ipaddress = inetAddress.getHostAddress()
								.toString();
						return ipaddress;
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("Exception", "Exception in Get IP Address: " + ex.toString());
		}
		return null;
	}

	/**
	 * Get IP address from first non-localhost interface
	 * 
	 * @param
	 *
	 * @return address or empty string
	 */
/*	public String getIPAddress(boolean useIPv4) {
		try {
			List<NetworkInterface> interfaces = Collections
					.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf
						.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress().toUpperCase();
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
						if (useIPv4) {
							if (isIPv4)
								return sAddr;
						} else {
							if (!isIPv4) {
								int delim = sAddr.indexOf('%'); // drop ip6 port
																// suffix
								return delim < 0 ? sAddr : sAddr.substring(0,
										delim);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
		} // for now eat exceptions
		return "";
	}*/

	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						String ip = Formatter.formatIpAddress(inetAddress
								.hashCode());
						Log.i("ip", "***** IP=" + ip);
						return ip;
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("ex", ex.toString());
		}
		return null;
	}
	public static boolean isXLarge()
    {
        return ((context.getResources().getConfiguration().screenLayout & 
        		Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);

    }
    
    public static boolean isTabletDevice() 
    {

        boolean xlarge = false;

        if((context.getResources().getConfiguration().screenLayout &
        		Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE || (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 3)
        {
            xlarge = true;
        }
        //boolean xlarge = ((activityContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        int test = context.getResources().getConfiguration().screenLayout & 
        		Configuration.SCREENLAYOUT_SIZE_MASK;

       /* Log.i("OSCUtil.isTabletDevice()","screenLayout = " + test);
        Log.d("OSCUtil.isTabletDevice()","screenlayout size mask = " + Configuration.SCREENLAYOUT_SIZE_MASK);*/

        if (xlarge)
        {
            DisplayMetrics metrics = new DisplayMetrics();
            Activity activity = (Activity) context;
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
                    || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
                    || metrics.densityDpi == DisplayMetrics.DENSITY_TV
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) 
            {

                return true;
            }
        }

        return false;
    }
    
    public int setGridViewColNo() {
		 
		int orient = context.getResources().getConfiguration().orientation;
        Display d = ((Activity) context).getWindowManager().getDefaultDisplay();
        boolean isTablet = isTabletDevice();
        if(orient == Configuration.ORIENTATION_LANDSCAPE && isTablet)
        {
            if(isXLarge())
            {
            return 5;
            }
            else
            {
            	return 4;
            }
        }
        else if(orient == Configuration.ORIENTATION_LANDSCAPE)
        {
        	return 3;
        }
        else if(orient == Configuration.ORIENTATION_PORTRAIT && isTablet)
        {

            if(isXLarge())
            {
            	return 4;
            }
            else
            {
            	return 3;
            }
        }
        return 2;
		
		
	}


	/**
	 * Get ISO 3166-1 alpha-2 country code for this device (or null if not available)
	 * @return country code or null
	 */
	public String getUserCountry() {
		try {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if(tm!=null){
				final String simCountry = tm.getSimCountryIso();
				if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
					return simCountry.toUpperCase(Locale.US);
				}
				else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
					String networkCountry = tm.getNetworkCountryIso();
					if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
						return networkCountry.toUpperCase(Locale.US);
					}
				}
			}

		}
		catch (Exception e) { }
		return null;
	}

}
