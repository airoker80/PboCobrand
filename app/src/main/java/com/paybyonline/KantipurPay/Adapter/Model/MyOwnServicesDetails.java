package com.paybyonline.KantipurPay.Adapter.Model;

import android.content.Context;

import com.paybyonline.KantipurPay.imageLoader.ImageLoader;

/**
 * Created by Anish on 9/9/2016.
 */
public class MyOwnServicesDetails {

    private Context mContext;
    private final String servCat;
    private final String servType;
    //private final String[] web;
    private final String Imageid;
    //    private final Integer[] Imageid;
    ImageLoader imageloader;

    public MyOwnServicesDetails(String servCat,String servType ,String Imageid ) {
        //public ServiceCategoryGridListAdapter(Context c,String[] web,String[] Imageid ) {


        this.Imageid = Imageid;
        this.servCat = servCat;
        this.servType=servType;

    }

    public String getServCat() {
        return servCat;
    }

    public String getServType() {
        return servType;
    }

    public String getImageid() {
        return Imageid;
    }
}
