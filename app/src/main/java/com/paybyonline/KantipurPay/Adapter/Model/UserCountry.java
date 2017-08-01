package com.paybyonline.KantipurPay.Adapter.Model;

/**
 * Created by mefriend24 on 10/21/16.
 */
public class UserCountry {

    String id;
    String countryName;

    public UserCountry(String id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
