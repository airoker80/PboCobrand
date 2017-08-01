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
import com.squareup.picasso.Picasso;


/**
 * Created by mefriend24 on 9/20/16.
 */
public class ParentRecycleViewHolder extends RecyclerView.ViewHolder{

    ImageView parentImage;
    RecyclerView childRecycleView;
    LinearLayout rechargeFormLayout;
    LinearLayout serCatLayout;
    ChildRecycleViewAdapter childRecycleViewAdapter;
    Context context;
    TextView serCatTxt;
    Boolean ifClick=false;
    View holder;

    public ParentRecycleViewHolder(View itemView) {
        super(itemView);
        holder=itemView;

        parentImage = (ImageView) itemView.findViewById(R.id.parentImage);
        childRecycleView = (RecyclerView) itemView.findViewById(R.id.childRecycleView);
        rechargeFormLayout = (LinearLayout) itemView.findViewById(R.id.rechargeFormLayout);
        serCatLayout = (LinearLayout) itemView.findViewById(R.id.serCatLayout);
        serCatTxt = (TextView) itemView.findViewById(R.id.serCatTxt);

        context = itemView.getContext();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);


        childRecycleView.setHasFixedSize(true);
        childRecycleView.setLayoutManager(layoutManager);
        childRecycleView.setVisibility(View.GONE);


    }

    public void bind(final ServiceCatagoryDetails serviceCatagoryDetails, final int childLayout,
                     final CoordinatorLayout coordinatorLayout,final String servTypeId, int position){



            Log.i("msgss", "parent view binding");
            serCatTxt.setText(serviceCatagoryDetails.getServiceCategoryName());

            Picasso.with(context)
                    .load(serviceCatagoryDetails.getServiceCatImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(parentImage);

            childRecycleViewAdapter = new ChildRecycleViewAdapter(context,serviceCatagoryDetails.getServiceTypeArrayList()
                    ,rechargeFormLayout, childLayout,coordinatorLayout,serviceCatagoryDetails.getServiceCategoryId(),servTypeId,position);

            childRecycleView.setAdapter(childRecycleViewAdapter);

            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.i("msgss", "parentImage click");
                    showHideRechargeServices(ifClick);

                    //  parentName.setVisibility(View.GONE);
                }
            });

        if(!servTypeId.isEmpty()){
            showHideRechargeServices(false);
        }

        }

    public void showHideRechargeServices(Boolean showHide){
        if (showHide) {

            Log.i("msgss", "ifClick true");
            serCatTxt.setVisibility(View.VISIBLE);
            childRecycleView.setVisibility(View.GONE);
            rechargeFormLayout.setVisibility(View.GONE);
            ifClick = false;

        }else {

            Log.i("msgss", "ifClick false");
            serCatTxt.setVisibility(View.GONE);
            childRecycleView.setVisibility(View.VISIBLE);
            rechargeFormLayout.setVisibility(View.GONE);
            ifClick = true;
        }

    }


    }

