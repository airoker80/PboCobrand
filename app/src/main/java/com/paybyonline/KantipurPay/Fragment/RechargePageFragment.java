package com.paybyonline.KantipurPay.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.R;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class RechargePageFragment extends Fragment {

    private FragmentTabHost mTabHost;
    public RechargePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        ((DashBoardActivity) getActivity()).setTitle("Recharge Page");


        mTabHost = new FragmentTabHost(getActivity());

        // Locate fragment1.xml to create FragmentTabHost
        mTabHost.setup(getActivity(), getChildFragmentManager(),R.layout.fragment_recharge_page);
        // Create Tab 1
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("List"), RechargedDetailsFragment.class, null);
        // Create Tab 2
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Use Code"), UseRechargeCodeFragment.class, null);
        // Create Tab 3

        //mTabHost.setTab
       // mTabHost.setTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.red_color));
        return mTabHost;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {

            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {

            throw new RuntimeException(e);

        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

    // Remove FragmentTabHost
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }
}
