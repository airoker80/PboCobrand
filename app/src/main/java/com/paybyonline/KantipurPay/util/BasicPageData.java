package com.paybyonline.KantipurPay.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;
import com.paybyonline.KantipurPay.usersession.UserDeviceDetails;

/**
 * Created by mefriend24 on 12/9/16.
 */
public class BasicPageData {

    private MyUserSessionManager myUserSessionManager;
    private UserDeviceDetails userDeviceDetails;
	private ShowMyAlertProgressDialog showMyAlertProgressDialog;
    private CoordinatorLayout coordinatorLayout;
    private PboServerRequestHandler pboServerRequestHandler;
    private Context context;

    public BasicPageData(Context context,View view) {
        this.context = context;
		userDeviceDetails = new UserDeviceDetails(context);
        myUserSessionManager = new MyUserSessionManager(context);
		showMyAlertProgressDialog = new ShowMyAlertProgressDialog(context);
        pboServerRequestHandler = PboServerRequestHandler.getInstance(coordinatorLayout, context);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);

    }

    public MyUserSessionManager getMyUserSessionManager() {
        return myUserSessionManager;
    }

    public void setMyUserSessionManager(MyUserSessionManager myUserSessionManager) {
        this.myUserSessionManager = myUserSessionManager;
    }

    public UserDeviceDetails getUserDeviceDetails() {
        return userDeviceDetails;
    }

    public void setUserDeviceDetails(UserDeviceDetails userDeviceDetails) {
        this.userDeviceDetails = userDeviceDetails;
    }

    public ShowMyAlertProgressDialog getShowMyAlertProgressDialog() {
        return showMyAlertProgressDialog;
    }

    public void setShowMyAlertProgressDialog(ShowMyAlertProgressDialog showMyAlertProgressDialog) {
        this.showMyAlertProgressDialog = showMyAlertProgressDialog;
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }

    public void setCoordinatorLayout(CoordinatorLayout coordinatorLayout) {
        this.coordinatorLayout = coordinatorLayout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public PboServerRequestHandler getPboServerRequestHandler() {
        return pboServerRequestHandler;
    }

    public void setPboServerRequestHandler(PboServerRequestHandler pboServerRequestHandler) {
        this.pboServerRequestHandler = pboServerRequestHandler;
    }
}
