package com.paybyonline.KantipurPay.Adapter.Model;

import android.graphics.Bitmap;

/**
 * Created by Sameer on 5/16/2017.
 */

public class FlightDetailsModel {

    String flightTime,flightAirline,flightClass,flightType,flightPrice;

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public String getFlightAirline() {
        return flightAirline;
    }

    public void setFlightAirline(String flightAirline) {
        this.flightAirline = flightAirline;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(String flightPrice) {
        this.flightPrice = flightPrice;
    }

    public FlightDetailsModel(String flightTime, String flightAirline, String flightClass, String flightType, String flightPrice) {
        this.flightTime = flightTime;
        this.flightAirline = flightAirline;
        this.flightClass = flightClass;
        this.flightType = flightType;
        this.flightPrice = flightPrice;

    }
}
