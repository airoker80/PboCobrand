package com.paybyonline.KantipurPay.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.Adapter.DashboardGridAdapter;
import com.paybyonline.KantipurPay.R;

/**
 * Created by Sameer on 4/17/2017.
 */

public class GridDashboardFragment extends Fragment  {
    String[] dashboardMenu = {
            "Load Wallet",
            "Fund Transfer",
            "Flight Ticket",
            "Recharge Topup",
            "Hotels",
            "Offers"

    };
    int[] imageId = {
            R.drawable.wallet,
            R.drawable.transfer,
            R.drawable.flight,
            R.drawable.recharge,
            R.drawable.hotel,
            R.drawable.offer
    };

    RecyclerView dashboard_grid;
    Button kycForm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_dashboard, container, false);
        ((DashBoardActivity) getActivity()).setTitle("Payments");
        dashboard_grid = (RecyclerView) view.findViewById(R.id.dashboard_grid);
        kycForm = (Button) view.findViewById(R.id.kycForm);
        ((DashBoardActivity) getActivity()).shareMenu.setVisible(true);
        dashboard_grid.setLayoutManager(new GridLayoutManager(getContext(), 2));
        DashboardGridAdapter dashboardGridAdapter = new DashboardGridAdapter(getContext(), dashboardMenu, imageId);
        dashboard_grid.setAdapter(dashboardGridAdapter);
        kycForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getContext().startActivity(new Intent(getContext(),KycFormFragment.class));
                Fragment fragment =null;
                fragment = new KycDynamicForm();
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();
            }
        });
        return view;

    }

}