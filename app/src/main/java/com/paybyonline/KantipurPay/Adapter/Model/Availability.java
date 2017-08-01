package com.paybyonline.KantipurPay.Adapter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sameer on 5/30/2017.
 */

public class Availability implements Parcelable {

    private String fuelSurcharge;
    private String infantFare;
    private String childFare;
    private String airline;
    private String adultFare;
    private String departure;
    private String aircraftType;
    private String agencyCommission;
    private String sum;
    private String child;
    private String infant;
    private String departureTime;
    private String flightDate;
    private String adult;
    private String resFare;
    private String freeBaggage;
    private String arrival;
    private String flightId;
    private String flightNo;
    private String arrivalTime;
    private String currency;
    private String airlineLogo;
    private String refundable;
    private String tax;
    private String duration;
    private String flightClassCode;
    private String childCommission;

    public Availability(String fuelSurcharge, String infantFare, String childFare, String airline, String adultFare, String departure, String aircraftType, String agencyCommission, String sum, String child, String infant, String departureTime, String flightDate, String adult, String resFare, String freeBaggage, String arrival, String flightId, String flightNo, String arrivalTime, String currency, String airlineLogo, String refundable, String tax, String duration, String flightClassCode, String childCommission) {
        this.fuelSurcharge = fuelSurcharge;
        this.infantFare = infantFare;
        this.childFare = childFare;
        this.airline = airline;
        this.adultFare = adultFare;
        this.departure = departure;
        this.aircraftType = aircraftType;
        this.agencyCommission = agencyCommission;
        this.sum = sum;
        this.child = child;
        this.infant = infant;
        this.departureTime = departureTime;
        this.flightDate = flightDate;
        this.adult = adult;
        this.resFare = resFare;
        this.freeBaggage = freeBaggage;
        this.arrival = arrival;
        this.flightId = flightId;
        this.flightNo = flightNo;
        this.arrivalTime = arrivalTime;
        this.currency = currency;
        this.airlineLogo = airlineLogo;
        this.refundable = refundable;
        this.tax = tax;
        this.duration = duration;
        this.flightClassCode = flightClassCode;
        this.childCommission = childCommission;
    }

    protected Availability(Parcel in) {
        fuelSurcharge = in.readString();
        infantFare = in.readString();
        childFare = in.readString();
        airline = in.readString();
        adultFare = in.readString();
        departure = in.readString();
        aircraftType = in.readString();
        agencyCommission = in.readString();
        sum = in.readString();
        child = in.readString();
        infant = in.readString();
        departureTime = in.readString();
        flightDate = in.readString();
        adult = in.readString();
        resFare = in.readString();
        freeBaggage = in.readString();
        arrival = in.readString();
        flightId = in.readString();
        flightNo = in.readString();
        arrivalTime = in.readString();
        currency = in.readString();
        airlineLogo = in.readString();
        refundable = in.readString();
        tax = in.readString();
        duration = in.readString();
        flightClassCode = in.readString();
        childCommission = in.readString();
    }

    public static final Creator<Availability> CREATOR = new Creator<Availability>() {
        @Override
        public Availability createFromParcel(Parcel in) {
            return new Availability(in);
        }

        @Override
        public Availability[] newArray(int size) {
            return new Availability[size];
        }
    };

    public String getFuelSurcharge() {
        return fuelSurcharge;
    }

    public void setFuelSurcharge(String fuelSurcharge) {
        this.fuelSurcharge = fuelSurcharge;
    }

    public String getInfantFare() {
        return infantFare;
    }

    public void setInfantFare(String infantFare) {
        this.infantFare = infantFare;
    }

    public String getChildFare() {
        return childFare;
    }

    public void setChildFare(String childFare) {
        this.childFare = childFare;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAdultFare() {
        return adultFare;
    }

    public void setAdultFare(String adultFare) {
        this.adultFare = adultFare;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(String agencyCommission) {
        this.agencyCommission = agencyCommission;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getInfant() {
        return infant;
    }

    public void setInfant(String infant) {
        this.infant = infant;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getResFare() {
        return resFare;
    }

    public void setResFare(String resFare) {
        this.resFare = resFare;
    }

    public String getFreeBaggage() {
        return freeBaggage;
    }

    public void setFreeBaggage(String freeBaggage) {
        this.freeBaggage = freeBaggage;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAirlineLogo() {
        return airlineLogo;
    }

    public void setAirlineLogo(String airlineLogo) {
        this.airlineLogo = airlineLogo;
    }

    public String getRefundable() {
        return refundable;
    }

    public void setRefundable(String refundable) {
        this.refundable = refundable;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFlightClassCode() {
        return flightClassCode;
    }

    public void setFlightClassCode(String flightClassCode) {
        this.flightClassCode = flightClassCode;
    }

    public String getChildCommission() {
        return childCommission;
    }

    public void setChildCommission(String childCommission) {
        this.childCommission = childCommission;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fuelSurcharge);
        dest.writeString(infantFare);
        dest.writeString(childFare);
        dest.writeString(airline);
        dest.writeString(adultFare);
        dest.writeString(departure);
        dest.writeString(aircraftType);
        dest.writeString(agencyCommission);
        dest.writeString(sum);
        dest.writeString(child);
        dest.writeString(infant);
        dest.writeString(departureTime);
        dest.writeString(flightDate);
        dest.writeString(adult);
        dest.writeString(resFare);
        dest.writeString(freeBaggage);
        dest.writeString(arrival);
        dest.writeString(flightId);
        dest.writeString(flightNo);
        dest.writeString(arrivalTime);
        dest.writeString(currency);
        dest.writeString(airlineLogo);
        dest.writeString(refundable);
        dest.writeString(tax);
        dest.writeString(duration);
        dest.writeString(flightClassCode);
        dest.writeString(childCommission);
    }
}
