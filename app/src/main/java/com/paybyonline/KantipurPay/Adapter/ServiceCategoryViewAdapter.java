package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paybyonline.KantipurPay.Adapter.Model.ServiceCategoryDetailsGrid;
import com.paybyonline.KantipurPay.Adapter.ViewHolder.ServiceCategoryGridViewHolder;
import com.paybyonline.KantipurPay.Interface.ItemClickListener;
import com.paybyonline.KantipurPay.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anish on 8/18/2016.
 */
public class ServiceCategoryViewAdapter  extends  RecyclerView.Adapter<ServiceCategoryGridViewHolder>{


    List<ServiceCategoryDetailsGrid> mRelatedItemList = Collections.emptyList();
    LayoutInflater inflater;
    Context mContext;


    public ServiceCategoryViewAdapter(Context context, List<ServiceCategoryDetailsGrid> itemList) {

        this.mContext = context;
       this.mRelatedItemList = itemList;
        inflater = LayoutInflater.from(context);
      //  mRelatedItemList = new ArrayList<>();
    }
    @Override
    public ServiceCategoryGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

     /*   View itemView;
        GenericViewHolder genericViewHolder = null;
        switch (viewType) {
            case ITEM_TYPE_NORMAL:

                itemView = inflater.inflate(R.layout.recharge_service_page_layout, parent, false);
                genericViewHolder = new ServiceCategoryGridViewHolder(itemView);

            case ITEM_TYPE_HEADER:
                itemView = inflater.inflate(R.layout.recharge_service_page_layout, parent, false);
                holdItemView = itemView;
                genericViewHolder = new RechargePageViewHolder(itemView);

            default:
                break;
        }
        return genericViewHolder;*/
        View itemView = inflater.inflate(R.layout.service_category_recycle_view, parent, false);
        return new ServiceCategoryGridViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServiceCategoryGridViewHolder holder, int position) {
        final ServiceCategoryDetailsGrid model = mRelatedItemList.get(position);
        holder.bind(model);

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                // startDetailsActivity(position);
              /*  Log.i("setClickListener", " " + position);
                holder.expandLayout.setVisibility(View.VISIBLE);
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewLayout = layoutInflater.inflate(R.layout.layout_to_inflater, null);
                holder.expandLayout.addView(viewLayout);*/
            }
        });
     /*   if (holder instanceof ServiceCategoryGridViewHolder) {
            ServiceCategoryGridViewHolder serviceCategoryGridViewHolder = (ServiceCategoryGridViewHolder) holder;
            final ServiceCategoryDetailsGrid model = mItemList.get(position);
            serviceCategoryGridViewHolder.bind(model);
        } */
    }

    @Override
    public int getItemCount() {

        return mRelatedItemList.size();
    }

/*    @Override
    public int getItemViewType(int position) {

        int type = super.getItemViewType(position);

        if (mItemList.get(position) instanceof ServiceCategoryDetailsGrid) {
            type = ITEM_TYPE_NORMAL;
        } else if (mItemList.get(position) instanceof ServiceType) {
            type = ITEM_TYPE_HEADER;
        }

        return type;
    }*/



}


