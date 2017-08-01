package com.paybyonline.KantipurPay.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.util.BasicPageData;

public class PboWebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressDialog progDailog;
    private BasicPageData basicPageData;
    String webViewPage;
    Boolean dataObtained = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbo_web_view);

        Bundle bundleData = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(bundleData.getString("pageTitle"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView)findViewById(R.id.webv);
        basicPageData = new BasicPageData(PboWebViewActivity.this,getWindow().getDecorView().getRootView());
        webViewPage = bundleData.getString("webViewPage");
        if(webViewPage.equals("Online Banking")){
            createOnlineBankingWebView(bundleData);
        }

    }

    public void launchWebView(String launchUrl){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        progDailog = ProgressDialog.show(PboWebViewActivity.this, "Loading","Please wait...", true);
        progDailog.setCancelable(false);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
                Log.i("msgss ","url ----- "+url);

                if(webViewPage.equals("Online Banking")){
                    handleOnlineBankingResponse(url);
                }
            }
        });
        webView.loadUrl(launchUrl);
    }


    public void createOnlineBankingWebView(Bundle bundleData){
        String shareUrl= PayByOnlineConfig.NIBL_CHECKOUT+"?"+
                "userCode="+basicPageData.getMyUserSessionManager().getSecurityCode()+
                "&authenticationCode="+basicPageData.getMyUserSessionManager().getAuthenticationCode()+
                "&payUsingIds="+bundleData.getString("payUsingIds")+
                "&confirmPayUsings="+bundleData.getString("confirmPayUsings")+
                "&confirmPaymentNotes="+bundleData.getString("confirmPaymentNotes")+
                "&confirmPurchasedAmount="+bundleData.getString("confirmPurchasedAmount")+
                "&confirmDiscountAmount="+bundleData.getString("confirmDiscountAmount")+
                "&confirmDepositingAmount="+bundleData.getString("confirmDepositingAmount")+
                "&confirmPayingAmount="+bundleData.getString("confirmPayingAmount")+
                "&payAmt="+bundleData.getString("payAmt")+
                "&payTypeValue="+bundleData.getString("payTypeValue")+
                "&confirmTotalAmount="+bundleData.getString("totalAmt")+
                "&addDiscountWallet="+bundleData.getString("addDiscountWallet")+
                "&confirmPaymentProfileNames="+bundleData.getString("confirmPaymentProfileNames")+
                "&userIp="+bundleData.getString("userIp");
        launchWebView(shareUrl);
    }

    public void handleOnlineBankingResponse(String url){
        if (url.contains("/niblCheckoutFailed?") && dataObtained != true) {
            Uri uri = Uri.parse(url);
            String msg = uri.getQueryParameter("msg");
            dataObtained= true;
            Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PboWebViewActivity.this, DashBoardActivity.class));
            finish();;
        }
        if (url.contains("/niblCheckoutResponse?") && dataObtained != true) {
            Uri uri = Uri.parse(url);
            String msg = uri.getQueryParameter("msg");
            dataObtained= true;
            Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PboWebViewActivity.this, DashBoardActivity.class));
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}
