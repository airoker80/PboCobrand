package com.paybyonline.KantipurPay.Fragment.FragmentHelper;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.paybyonline.KantipurPay.Fragment.AccountSummaryFragment;
import com.paybyonline.KantipurPay.Fragment.ReportChartFragment;


/**
 * Created by Anish on 9/16/2015.
 */
public class DashboardViewPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    /*private String titles[] = new String[] { "Recharge Code","Use Recharge Code", "Generate Recharge Code" };*/
    private String titles[] = new String[] {"Account Summary","Service Usage"};
//    private String titles[] = new String[] {"Summary Report"};



   // The key to this is to return a SpannableString,
   // containing your icon in an ImageSpan, from your PagerAdapter's getPageTitle(position) method:


    Context context;

    public DashboardViewPageAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                AccountSummaryFragment fragmenttab1 = new AccountSummaryFragment();
                return fragmenttab1;
            case 1:
                ReportChartFragment fragmenttab2 = new ReportChartFragment();
                return fragmenttab2;
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
