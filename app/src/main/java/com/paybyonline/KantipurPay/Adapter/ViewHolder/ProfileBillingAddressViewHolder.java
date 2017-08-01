package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.ProfileBillingAddress;
import com.paybyonline.KantipurPay.R;

/**
 * Created by Anish on 11/7/2016.
 */
public class ProfileBillingAddressViewHolder extends RecyclerView.ViewHolder  {

    TextView id;
    TextView preference;
    TextView status;
    TextView profileName;
    TextView address1;
    TextView address2;
    TextView city;
    TextView state;
    TextView postalCode;
    TextView country;
    TextView remark;
    TextView companyName;
    LinearLayout stateLayout;


    public ProfileBillingAddressViewHolder(View itemView) {
        super(itemView);

        stateLayout=(LinearLayout)itemView.findViewById(R.id.stateLayout);
        id=(TextView)itemView.findViewById(R.id.id);
        preference=(TextView)itemView.findViewById(R.id.pref);
        profileName=(TextView)itemView.findViewById(R.id.profileName);
        status=(TextView)itemView.findViewById(R.id.status);
        address1=(TextView)itemView.findViewById(R.id.address1);
        address2=(TextView)itemView.findViewById(R.id.address2);
        city=(TextView)itemView.findViewById(R.id.city);
        state=(TextView)itemView.findViewById(R.id.state);
        postalCode=(TextView)itemView.findViewById(R.id.postalCode);
        country=(TextView)itemView.findViewById(R.id.country);
        remark=(TextView)itemView.findViewById(R.id.remark);
        companyName=(TextView)itemView.findViewById(R.id.com_name);
    }

    public void bind(final ProfileBillingAddress profileBillingAddress){

        id.setText(profileBillingAddress.getId());
        preference.setText(profileBillingAddress.getPreference());
        profileName.setText(profileBillingAddress.getName());
        status.setText(profileBillingAddress.getStatus());
        address1.setText(profileBillingAddress.getAddressLine1());
        address2.setText(profileBillingAddress.getAddressLine2());
        companyName.setText(profileBillingAddress.getCompanyName());
        city.setText(profileBillingAddress.getCity());
        if(profileBillingAddress.getState().equals("")){
            stateLayout.setVisibility(View.GONE);
        }else{
            state.setText(profileBillingAddress.getState());
        }

        postalCode.setText(profileBillingAddress.getZipPostalCode());
        country.setText(profileBillingAddress.getCountry());
        remark.setText(profileBillingAddress.getRemark());

    }
}
