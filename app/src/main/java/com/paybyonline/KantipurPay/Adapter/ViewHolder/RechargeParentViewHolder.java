package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.ChildRecycleViewAdapter;
import com.paybyonline.KantipurPay.Adapter.Model.ParentRecycleViewSampleModel;
import com.paybyonline.KantipurPay.R;

/**
 * Created by Anish on 9/29/2016.
 */
public class RechargeParentViewHolder extends RecyclerView.ViewHolder{

    ImageView parentImage;
    RecyclerView childRecycleView;
    LinearLayout rechargeFormLayout;
    ChildRecycleViewAdapter childRecycleViewAdapter;
    Context context;
    TextView serCatTxt;
    CoordinatorLayout coordinatorLayout;
    String serviceCatagoryId;
    int childLayout;

    public RechargeParentViewHolder(View itemView) {
        super(itemView);

        parentImage = (ImageView) itemView.findViewById(R.id.parentImage);
        childRecycleView = (RecyclerView) itemView.findViewById(R.id.childRecycleView);
        rechargeFormLayout = (LinearLayout) itemView.findViewById(R.id.rechargeFormLayout);
        serCatTxt = (TextView) itemView.findViewById(R.id.serCatTxt);

        context = itemView.getContext();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false);


        childRecycleView.setHasFixedSize(true);
        childRecycleView.setLayoutManager(layoutManager);



    }

    public void bind(ParentRecycleViewSampleModel parentRecycleViewSampleModel){


        parentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childRecycleView.setVisibility(View.VISIBLE);
                //  parentName.setVisibility(View.GONE);
            }
        });

        childRecycleViewAdapter = new ChildRecycleViewAdapter(context,
                parentRecycleViewSampleModel.getChildArrayList(),
                rechargeFormLayout,childLayout,coordinatorLayout,serviceCatagoryId,"",0);

        childRecycleView.setAdapter(childRecycleViewAdapter);

    }


}
