package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.RechargeDetailsData;
import com.paybyonline.KantipurPay.R;

/**
 * Created by Anish on 8/17/2016.
 */
public class RechargeDetailsViewHolder  extends RecyclerView.ViewHolder {




        TextView transactionDate;
        TextView id_balance;
        TextView id_number;
        TextView id_transactionNo;
        TextView serviceCategoryType;
        TextView netCostAmt;
        TextView purchasedAmount;
        TextView msDis;
        TextView reward;
        TextView remarks;
        TextView paymentTypeWallet;
        TextView paymentTypePsp;



    public RechargeDetailsViewHolder(View itemView) {

        super(itemView);
        serviceCategoryType = (TextView) itemView
                .findViewById(R.id.serviceCategoryType);
        id_balance = (TextView) itemView
                .findViewById(R.id.id_balance);
        paymentTypeWallet = (TextView) itemView
                .findViewById(R.id.paymentTypeWallet);
        paymentTypePsp = (TextView) itemView
                .findViewById(R.id.paymentTypePsp);
        id_number = (TextView) itemView
                .findViewById(R.id.id_number);

        transactionDate = (TextView) itemView
                .findViewById(R.id.transactionDate);

        id_transactionNo = (TextView) itemView
                .findViewById(R.id.transactionNo);

        purchasedAmount = (TextView) itemView
                .findViewById(R.id.purchasedAmount);

        msDis = (TextView) itemView
                .findViewById(R.id.msDis);

        netCostAmt = (TextView) itemView
                .findViewById(R.id.netCostAmt);

      // dealDiscount = (TextView) itemView.findViewById(R.id.dealDiscount);
       remarks = (TextView) itemView.findViewById(R.id.remarks);
       reward = (TextView) itemView.findViewById(R.id.reward);


    }





    public void bind(final RechargeDetailsData rechargeDetailsData){
        Log.i("MyOwnTags", "" + rechargeDetailsData);
       // transactionDate.setText("hello");
     serviceCategoryType.setText(Html
                .fromHtml(rechargeDetailsData.getServiceCategoryType()));

        id_number.setText(rechargeDetailsData.getRechargedNumber());
        paymentTypePsp.setText(rechargeDetailsData.getPspAmount());
        paymentTypeWallet.setText(rechargeDetailsData.geteWalletAmount());

        id_balance.setText(Html
                .fromHtml(rechargeDetailsData.getServiceCategoryAmount()));
           transactionDate.setText(Html
                .fromHtml(rechargeDetailsData.getTransactionDate()));
        id_transactionNo.setText(Html
                .fromHtml( rechargeDetailsData.getTransactionNo()));
        purchasedAmount.setText(Html
                .fromHtml( rechargeDetailsData.getPurchasedAmount()));
        if(rechargeDetailsData.getPayTypeDiscount().toString().length()>0){
            msDis.setText(Html.fromHtml(rechargeDetailsData.getPayTypeDiscount()));}
        else{
            msDis.setText(Html.fromHtml( rechargeDetailsData.getPayTypeCommisssion()));}
		//dealDiscount.setVisibility(View.VISIBLE);
       reward.setVisibility(View.VISIBLE);

       remarks.setVisibility(View.VISIBLE);
        netCostAmt.setText(Html
                .fromHtml( rechargeDetailsData.getNetCostAmount()));
        /*if(rechargeDetailsData.getDealDiscount().toString().length()>0)
        {dealDiscount.setText(Html
                .fromHtml( rechargeDetailsData.getDealDiscount()));}
        else{
            dealDiscount.setVisibility(View.GONE);
        }*/
        if(rechargeDetailsData.getReward().toString().length()>0){
           reward.setText(Html.fromHtml(rechargeDetailsData.getReward()));}
        else{
           reward.setVisibility(View.GONE);
        }

        if( rechargeDetailsData.getRemarks().toString().length()>0){
           remarks.setText(Html.fromHtml( rechargeDetailsData.getRemarks()));}
        else{
           remarks.setVisibility(View.GONE);
        }
    }
}
