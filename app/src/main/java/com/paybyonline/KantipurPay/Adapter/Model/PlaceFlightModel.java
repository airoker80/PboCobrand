package com.paybyonline.KantipurPay.Adapter.Model;

/**
 * Created by Sameer on 5/19/2017.
 */

public class PlaceFlightModel {
    String place,placeCode,countryName;

    public PlaceFlightModel(String place, String placeCode, String countryName) {
        this.place = place;
        this.placeCode = placeCode;
        this.countryName = countryName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
