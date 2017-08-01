package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.ChildRecycleViewAdapter;
import com.paybyonline.KantipurPay.Adapter.Model.ServiceCatagoryDetails;
import com.paybyonline.KantipurPay.R;

/**
 * Created by Anish on 10/6/2016.
 */
public class RechargeNowPageViewHolder extends RecyclerView.ViewHolder{

    ImageView parentImage;
    RecyclerView childRecycleView;
    LinearLayout rechargeFormLayout;
    ChildRecycleViewAdapter childRecycleViewAdapter;
    Context context;
    TextView serCatTxt;
    Boolean ifClick=false;
    View holder;

    public RechargeNowPageViewHolder(View itemView) {
        super(itemView);
        holder=itemView;
        parentImage = (ImageView) itemView.findViewById(R.id.parentImage);
        childRecycleView = (RecyclerView) itemView.findViewById(R.id.childRecycleView);
        rechargeFormLayout = (LinearLayout) itemView.findViewById(R.id.rechargeFormLayout);
        serCatTxt = (TextView) itemView.findViewById(R.id.serCatTxt);

        context = itemView.getContext();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);


        childRecycleView.setHasFixedSize(true);
        childRecycleView.setLayoutManager(layoutManager);
      //  childRecycleView.setVisibility(View.GONE);


    }

    public void bind(final ServiceCatagoryDetails serviceCatagoryDetails, final int childLayout,
                     final CoordinatorLayout coordinatorLayout,String servCatId,String servTypeId){

        if(servCatId.trim().equals(serviceCatagoryDetails.getServiceCategoryId())){
            Log.i("msgss", "parent view binding");

            serCatTxt.setText(serviceCatagoryDetails.getServiceCategoryName());

//            Picasso.with(context)
//                    .load(R.drawable.ncell)
//                    .placeholder(R.mipmap.ic_launcher)
//                    .error(R.mipmap.ic_launcher)
//                    .into(parentImage);

            childRecycleViewAdapter = new ChildRecycleViewAdapter(context,
                    serviceCatagoryDetails.getServiceTypeArrayList()
                    ,rechargeFormLayout, childLayout,coordinatorLayout,
                    serviceCatagoryDetails.getServiceCategoryId(),servTypeId,0);

            childRecycleView.setAdapter(childRecycleViewAdapter);

            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("msgss", "parentImage click");

                /*    if (ifClick) {

                        Log.i("msgss", "ifClick true");
                        serCatTxt.setVisibility(View.VISIBLE);
                        childRecycleView.setVisibility(View.GONE);
                        rechargeFormLayout.setVisibility(View.GONE);
                        ifClick = false;

                    }
                    else {*/

                        Log.i("msgss", "ifClick true");
                        serCatTxt.setVisibility(View.GONE);
                        childRecycleView.setVisibility(View.VISIBLE);
                        rechargeFormLayout.setVisibility(View.VISIBLE);
                       // ifClick = true;
                   // }

                    //  parentName.setVisibility(View.GONE);
                }
            });
        }



    }


}

