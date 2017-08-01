package com.paybyonline.KantipurPay.usersession;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.Gravity;
import android.widget.Toast;

public class ShowMyAlertProgressDialog {

	Context context;
	Builder alertDialog;
	ProgressDialog progress;
	private MyUserSessionManager myUserSessionManager;

	public ShowMyAlertProgressDialog(Context context) {
		super();
		this.context = context;
	}

	public void showProgressDialog(String title, String message) {
		progress = new ProgressDialog(context);
		progress.setTitle(title);
		progress.setMessage(message);
		progress.setCancelable(false);
		progress.setIndeterminate(true);
		progress.show();
	}

	public void showProgressDialogCancelable(String title, String message) {
		progress = new ProgressDialog(context);
		progress.setTitle(title);
		progress.setMessage(message);
		progress.setCancelable(true);
		progress.setIndeterminate(true);
		progress.show();

		// progress = ProgressDialog.show(context, title, message, true, false);

	}

	public void showProgressDialogButtom() {
		progress.setMessage("Retrieving data ...");
		progress.setIndeterminate(true);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.getWindow().setGravity(Gravity.BOTTOM);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.getWindow().setGravity(Gravity.BOTTOM);
		progress.show();
	}

	public void hideProgressDialog() {
		/*
		 * if(progress.isShowing()){ progress.dismiss(); }
		 */
		progress.dismiss();
	}

	public void showUserAlertDialog(String message, String title) {

		alertDialog = new Builder(context);
		alertDialog.setMessage(Html.fromHtml(message)).setTitle(title)
		// .setNeutralButton("OK",null).create();
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create().show();
	}

	public void showShortToast(String message){
		Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
	}

	public void showLongToast(String message){
		Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
	}



	public void showUserLogOutAlertDialog(String message, String title) {


	}


}
