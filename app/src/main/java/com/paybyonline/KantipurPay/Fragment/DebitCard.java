package com.paybyonline.KantipurPay.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebitCard extends Fragment {


    public DebitCard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_debit_card, container, false);

        return view;
    }


}
