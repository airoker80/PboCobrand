package com.paybyonline.KantipurPay.Fragment.FragmentHelper;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.paybyonline.KantipurPay.Fragment.BankDepositFragment;
import com.paybyonline.KantipurPay.Fragment.CreditCardFragment;
import com.paybyonline.KantipurPay.Fragment.DebitCardFragment;
import com.paybyonline.KantipurPay.Fragment.NetBankingFragment;
import com.paybyonline.KantipurPay.Fragment.PaymentAggregatorFragment;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;

import java.util.HashMap;

/**
 * Created by Anish on 8/16/2016.
 */

public class  PaymentTypeViewPagerAdapter extends  FragmentPagerAdapter {


    int PAGE_COUNT;
    Bundle bundle=new Bundle();
    CoordinatorLayout coordinatorLayout;
    String userCountry = "";
    int filterTab = 1;

   /* titles = new String[] {"Bank Deposit","Credit Card","Debit Card",
            "PaymentAggregator","Online Banking"};*/

    private String titles[];
    MyUserSessionManager myUserSessionManager;
    // The key to this is to return a SpannableString,
    // containing your icon in an ImageSpan, from your PagerAdapter's getPageTitle(position) method:
    private HashMap<String, String> userDetails;
    Context context;


    public PaymentTypeViewPagerAdapter(FragmentManager fm, Context context,Bundle bundle ,
                                       CoordinatorLayout coordinatorLayout) {

        super(fm);
        this.context = context;
        this.coordinatorLayout = coordinatorLayout;
        this.bundle=bundle;
        Log.i("bundleList",""+bundle);
        myUserSessionManager = new MyUserSessionManager(context);
        userDetails = myUserSessionManager.getUserDetails();

        userCountry = myUserSessionManager.getUserCountryName();
        Log.i("msgss","userCountry "+userCountry);

        if(userCountry!=""){
            if(userCountry.equals("Nepal")){
                PAGE_COUNT = 2;
                titles = new String[] {"Bank Deposit","Online Banking"};
                filterTab = 2;
            }else{
                filterTab = 1;
                PAGE_COUNT = 4;
                titles = new String[] {"Bank Deposit","Credit Card","Debit Card",
                        "PaymentAggregator"};
            }
        }else{
            filterTab = 3;
            PAGE_COUNT = 5;
            titles = new String[] {"Bank Deposit","Credit Card","Debit Card",
                    "PaymentAggregator","Online Banking"};
        }


    }

    @Override
    public Fragment getItem(int position) {

        Log.i("filterTab ","filterTab "+filterTab);

        if(filterTab==2){
            switch (position) {
                case 0:
                    BankDepositFragment fragmenttab0 = new BankDepositFragment();
                    fragmenttab0.setArguments(bundle);
                    return fragmenttab0;
                case 1:
                    NetBankingFragment fragmenttab5 = new NetBankingFragment();
                    fragmenttab5.setArguments(bundle);
                    return fragmenttab5;
            }
        }else if(filterTab==1){
            switch (position) {
                case 0:
                    BankDepositFragment fragmenttab0 = new BankDepositFragment();
                    fragmenttab0.setArguments(bundle);
                    return fragmenttab0;
                case 1:
                    CreditCardFragment fragmenttab1 = new CreditCardFragment();
                    fragmenttab1.setArguments(bundle);
                    return fragmenttab1;
                case 2:
                    DebitCardFragment fragmenttab2 = new DebitCardFragment();
                    fragmenttab2.setArguments(bundle);
                    return fragmenttab2;
                case 3:
                    PaymentAggregatorFragment fragmenttab3 = new PaymentAggregatorFragment();
                    fragmenttab3.setArguments(bundle);
                    return fragmenttab3;
            }
        }else {
            switch (position) {
                case 0:
                    BankDepositFragment fragmenttab0 = new BankDepositFragment();
                    fragmenttab0.setArguments(bundle);
                    return fragmenttab0;
                case 1:
                    CreditCardFragment fragmenttab1 = new CreditCardFragment();
                    fragmenttab1.setArguments(bundle);
                    return fragmenttab1;
                case 2:
                    DebitCardFragment fragmenttab2 = new DebitCardFragment();
                    fragmenttab2.setArguments(bundle);
                    return fragmenttab2;
                case 3:
                    PaymentAggregatorFragment fragmenttab3 = new PaymentAggregatorFragment();
                    fragmenttab3.setArguments(bundle);
                    return fragmenttab3;
                case 4:
                    NetBankingFragment fragmenttab5 = new NetBankingFragment();
                    fragmenttab5.setArguments(bundle);
                    return fragmenttab5;
            }
        }

        return null;
    }

    public CharSequence getPageTitle(int position){

        // Generate title based on item position
        return titles[position];

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}


