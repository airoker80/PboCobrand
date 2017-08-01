package com.paybyonline.KantipurPay.Adapter.Model;

import java.util.ArrayList;

/**
 * Created by mefriend24 on 9/20/16.
 */
public class ParentRecycleViewSampleModel {

    String parentName;
    String imageId;
    ArrayList<String> childArrayList;
    ArrayList<ServiceType> serviceTypeList;
    ArrayList <ServiceCatagoryDetails> serviceTypeParentList;

    public ParentRecycleViewSampleModel(String parentName, ArrayList<String> childArrayList) {

        this.parentName = parentName;
        this.childArrayList = childArrayList;
    }

    public ParentRecycleViewSampleModel(ArrayList<ServiceCatagoryDetails> serviceTypeParentList,
                                        ArrayList<ServiceType> serviceTypeList) {

        this.serviceTypeList = serviceTypeList;
        this.serviceTypeParentList = serviceTypeParentList;

    }

    public String getParentName() {
        return parentName;
    }


    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

 /*   public void setParentImageId() {
        this.imageId = serviceTypeParentList.;
    }*/

    public ArrayList<ServiceCatagoryDetails> getParentArrayList() {
        return serviceTypeParentList;
    }

    public ArrayList<ServiceType> getChildArrayList() {
        return serviceTypeList;
    }

    public void setChildArrayList(ArrayList<String> childArrayList) {
        this.childArrayList = childArrayList;
    }
}
