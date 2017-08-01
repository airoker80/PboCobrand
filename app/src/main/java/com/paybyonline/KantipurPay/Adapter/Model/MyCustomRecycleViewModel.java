package com.paybyonline.KantipurPay.Adapter.Model;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by Anish on 8/17/2016.
 */
public class MyCustomRecycleViewModel {

    String payTypeIds;
    String userPayName;
    String userPayLogo;
    Bundle bundle;

    public MyCustomRecycleViewModel(String payTypeIds, String userPayName, String userPayLogo,Bundle bundle) {

        this.payTypeIds = payTypeIds;
        this.userPayName = userPayName;
        this.userPayLogo = userPayLogo;
        this.bundle = bundle;
        Log.i("userPayName"," "+bundle);


    }
    public Bundle getBundle(){
        return bundle;
    }
    public String getPayTypeIds() {
        return payTypeIds;
    }

    public void setPayTypeIds(String payTypeIds) {
        this.payTypeIds = payTypeIds;
    }

    public String getUserPayName() {
        return userPayName;
    }

    public void setUserPayName(String userPayName) {
        this.userPayName = userPayName;
    }

    public String getUserPayLogo() {
        return userPayLogo;
    }

    public void setUserPayLogo(String userPayLogo) {
        this.userPayLogo = userPayLogo;
    }
}
