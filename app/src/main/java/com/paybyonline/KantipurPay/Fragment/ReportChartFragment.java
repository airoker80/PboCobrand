package com.paybyonline.KantipurPay.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.R;

import java.lang.reflect.Field;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ReportChartFragment extends Fragment {

	public ReportChartFragment() {
		// Required empty public constructor
	}

	 // Declare Variable
    private FragmentTabHost mTabHost;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //getActivity().getActionBar().setTitle("Report");
        // Create FragmentTabHost
        mTabHost = new FragmentTabHost(getActivity());
        // Locate fragment1.xml to create FragmentTabHost
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_report_chart);
        // Create Tab 1
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Summary Service Usage"), ReportPieChartFragment.class, null);
        // Create Tab 2
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Detail Service Usage"), ReportLineChartFragment.class, null);
        //mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Chart"), ReportChartFragment.class, null);

        return mTabHost;
    }

    // Detach FragmentTabHost
    @Override
    public void onDetach() {
        super.onDetach();
 
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
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