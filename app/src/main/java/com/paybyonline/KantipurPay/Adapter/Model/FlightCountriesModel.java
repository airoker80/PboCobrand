package com.paybyonline.KantipurPay.Adapter.Model;

/**
 * Created by Sameer on 5/8/2017.
 */

public class FlightCountriesModel {
    String countryCode,countryName,currencyName,currencySymbol,isServiceProviding;

    public FlightCountriesModel(String countryCode, String countryName, String currencyName, String currencySymbol, String isServiceProviding) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.isServiceProviding = isServiceProviding;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getIsServiceProviding() {
        return isServiceProviding;
    }

    public void setIsServiceProviding(String isServiceProviding) {
        this.isServiceProviding = isServiceProviding;
    }
}
