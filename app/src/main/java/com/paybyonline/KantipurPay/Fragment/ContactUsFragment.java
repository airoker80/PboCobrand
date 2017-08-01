package com.paybyonline.KantipurPay.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.paybyonline.KantipurPay.Activity.DashBoardActivity;
import com.paybyonline.KantipurPay.R;

/**
 * Created by Anish on 9/8/2016.
 */
public class ContactUsFragment extends Fragment{

    EditText subject;
    EditText message;
    Button btnSendMail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us_page, container, false);

        ((DashBoardActivity) getActivity()).setTitle("Contact Us");

        message = (EditText)view.findViewById(R.id.message);
        subject = (EditText)view.findViewById(R.id.subject);
        btnSendMail = (Button) view.findViewById(R.id.btnSendMail);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL,
                            new String[] { "support@paybyonline.com" });
                    // email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
                    email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                    email.putExtra(Intent.EXTRA_TEXT, message.getText().toString());

                    // need this to prompts email client only
                    email.setType("message/rfc822");

                    startActivity(Intent.createChooser(email,
                            "Choose an Email client"));
                }
            }
        });
        return  view;
    }

    public Boolean validateForm(){

        if(subject.getText().toString().isEmpty()){
           subject.setError("Required");
            return false;
        }
        if(message.getText().toString().isEmpty()){
            message.setError("Required");
            return false;
        }
        return true;
    }

}
