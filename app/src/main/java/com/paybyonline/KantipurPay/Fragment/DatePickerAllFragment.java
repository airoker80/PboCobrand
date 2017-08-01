package com.paybyonline.KantipurPay.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Sameer on 5/2/2017.
 */

public class DatePickerAllFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;

    public DatePickerAllFragment() {
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    private int year, month, day;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
/*        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), ondateSet,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        return datePickerDialog;*/
    }
}
