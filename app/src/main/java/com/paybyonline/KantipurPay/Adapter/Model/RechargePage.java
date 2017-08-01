package com.paybyonline.KantipurPay.Adapter.Model;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Anish on 8/4/2016.
 */
public class RechargePage extends  RecyclerView.ViewHolder {

    String serviceCategoryName;
    String serviceTypeName;
    String serviceCategoryId;
    String serviceTypeId;
    String scstId;



    public RechargePage(View itemView,
                        String serviceCategoryName, String serviceTypeName,
                        String serviceCategoryId, String serviceTypeId, String scstId) {
        super(itemView);
        this.serviceCategoryName = serviceCategoryName;
        this.serviceTypeName = serviceTypeName;
        this.serviceCategoryId = serviceCategoryId;
        this.serviceTypeId = serviceTypeId;
        this.scstId = scstId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(String serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getScstId() {
        return scstId;
    }

    public void setScstId(String scstId) {
        this.scstId = scstId;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }




}
