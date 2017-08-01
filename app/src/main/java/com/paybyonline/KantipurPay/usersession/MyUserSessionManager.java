package com.paybyonline.KantipurPay.usersession;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.paybyonline.KantipurPay.Activity.LoginActivity;

import java.util.HashMap;

public class MyUserSessionManager {

	SharedPreferences pref;
	Editor editor;
	Context _context;
	int PRIVATE_MODE = 0;
	public static final String PREFS_NAME = "LoginPrefs";
	private static final String IS_USER_LOGIN = "IsUserLoggedIn";
	public static final String KEY_USERNAME = "name";
	public static final String KEY_PROFILE_IMAGE = "profileImage";
	public static final String USER_HAS_COUNTRY = "UserCountry";
	public static final String KEY_SECURITY_CODE = "security_code";
	public static final String KEY_AUTH_CODE = "auth_code";
	public static final String KEY_ACCESS_TOKEN = "access_token";
	public static final String KEY_REFRESH_TOKEN = "refresh_token";
	public static final String KEY_DEFAULT_PAGE = "default_page";
	public static final String KEY_USER_COUNTRY = "user_country";

	public MyUserSessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void createUserAfterRegistration(String username) {
		editor.putBoolean(USER_HAS_COUNTRY, false);
		Intent i = new Intent(_context, LoginActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		_context.startActivity(i);
	}

	public String getKeyDefaultPage() {
		return pref.getString(KEY_DEFAULT_PAGE, "");
	}

	public void setKeyDefaultPage(String defaultPage) {
		editor.putString(KEY_DEFAULT_PAGE, defaultPage);
		editor.commit();
	}

	public void createUserLoginSession(String username) {
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(KEY_USERNAME, username);
		editor.commit();

	}

	public void addUserCountry() {
		editor.putBoolean(USER_HAS_COUNTRY, true);
		editor.commit();
	}
	public void addUserCountryName(String countryName) {
		editor.putString(KEY_USER_COUNTRY, countryName);
		editor.commit();
	}

	public String getUserCountryName() {
		return pref.getString(KEY_USER_COUNTRY, "");
	}

	/**
	 * Check login method will check user login status If false it will redirect
	 * user to login page Else do anything
	 * */
	public boolean checkLogin() {
		if (!this.isUserLoggedIn()) {
			Intent i = new Intent(_context, LoginActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			_context.startActivity(i);
			return true;
		}
		return false;
	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
		return user;
	}

	/**
	 * Clear session details
	 * */
	public void logoutUser() {
		editor.clear();
		editor.commit();
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("com.package.ACTION_LOGOUT");
		_context.sendBroadcast(broadcastIntent);
		Intent i = new Intent(_context, LoginActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		_context.startActivity(i);
	}

	// Check for login
	public boolean isUserLoggedIn() {
		return pref.getBoolean(IS_USER_LOGIN, false);
	}

	public String getUsername(){
		return pref.getString(KEY_USERNAME, "");
	}
	public String getAuthenticationCode(){
		return pref.getString(KEY_AUTH_CODE, "");
	}

	public String getProfileImage(){
		return pref.getString(KEY_PROFILE_IMAGE, "");
	}
	// Check for user country
	public boolean ifUserHasCountry() {
		return pref.getBoolean(USER_HAS_COUNTRY, false);
	}
	public String getSecurityCode(){
		return pref.getString(KEY_SECURITY_CODE, "");
	}
	public void saveUserInformation(String username, String authCode, String securityCode){
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(KEY_USERNAME, username);
		editor.putString(KEY_AUTH_CODE,authCode);
		editor.putString(KEY_SECURITY_CODE,securityCode);
		editor.commit();
	}

	public void saveUserToken(String username,String accessToken, String refreshToken){
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(KEY_ACCESS_TOKEN, accessToken);
		editor.putString(KEY_REFRESH_TOKEN,refreshToken);
		editor.putString(KEY_USERNAME, username);
		editor.commit();
	}



	public String getKeyAccessToken(){
		return pref.getString(KEY_ACCESS_TOKEN, "");
	}

}
