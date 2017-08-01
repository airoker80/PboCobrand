package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.ServiceCategoryServiceTypeDetails;
import com.paybyonline.KantipurPay.Fragment.RechargeGridFragment;
import com.paybyonline.KantipurPay.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Anish on 9/9/2016.
 */
public class MyOwnServiceDetailsViewHolder  extends RecyclerView.ViewHolder   {

    TextView serCatTxt;
    ImageView serviceCat_img;
    public View holdItemView;
    CardView card_view;

    public MyOwnServiceDetailsViewHolder(View itemView) {
        super(itemView);

        serviceCat_img=(ImageView)itemView.findViewById(R.id.serviceCat_img);
        serCatTxt=(TextView)itemView.findViewById(R.id.serCatTxt);
        card_view=(CardView)itemView.findViewById(R.id.card_view);
        holdItemView=itemView;

    }

    public void bind(final ServiceCategoryServiceTypeDetails myOwnServicesDetails){

        serCatTxt.setText(myOwnServicesDetails.getServiceCategoryName() +"" +
                " "+ myOwnServicesDetails.getServiceTypeName());

        Picasso.with(holdItemView.getContext())
                .load(myOwnServicesDetails.getServiceLogo())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(serviceCat_img);

        card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Context context = v.getContext();
               // Toast.makeText(context,"helllllllll",Toast.LENGTH_SHORT).show();
                Fragment fragment = new RechargeGridFragment();
                FragmentManager fragmentManager =((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("servCatName", myOwnServicesDetails.getServiceCategoryName());
                bundle.putString("servCatId", myOwnServicesDetails.getServiceCategoryId());
                bundle.putString("servTypeId", myOwnServicesDetails.getServiceTypeId());
                bundle.putString("servTypeName", myOwnServicesDetails.getServiceTypeName());
                bundle.putString("scstId", myOwnServicesDetails.getScstId());

                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();


            }
        });
    }


}

