package com.paybyonline.KantipurPay.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.paybyonline.KantipurPay.R;

/**
 * Created by Anish on 9/8/2016.
 */
public class AtmBankingFragment extends Fragment {

    private RadioButton radio_sct;
    private RadioButton radio_bank;
    private EditText payment_note;
    private TextView amt_val;
    private EditText profile_name;
    private CheckBox chkSave;
    private CheckBox chkAgree;
    private Button loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_atm_banking, container, false);
        initializeComponents(view);
        return view;
    }

    public void initializeComponents(View view){
        radio_sct=(RadioButton)view.findViewById(R.id.radio_sct);
        radio_bank=(RadioButton)view.findViewById(R.id.radio_bank);
        payment_note=(EditText)view.findViewById(R.id.payment_note);
        amt_val=(TextView) view.findViewById(R.id.amt_val);
        profile_name=(EditText)view.findViewById(R.id.profile_name);
        chkSave=(CheckBox)view.findViewById(R.id.chkSave);
        chkAgree=(CheckBox)view.findViewById(R.id.chkAgree);
        loginBtn=(Button)view.findViewById(R.id.loginBtn);
    }
}
