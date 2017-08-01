package com.paybyonline.KantipurPay.Adapter.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sameer on 7/4/2017.
 */

public class SaveReservationModel implements Parcelable{
    String adultFareIn,
            flightClassCodeOut, totalChildFareOut,
            departureIn, flightNoIn,
            flightNoOut, durationDeparture,
            totalAdultFareIn, totalOutSum,
            totalChildFare, airlinesIn,
            taxIn, airlinesOut,
            totalAdultFare, aircraftTypeIn,
            totalInSum, taxOut,
            departureTimeOut, imageOut,
            noOfChild, individualTax,
            arrivalTimeIn, totalTaxOut,
            adultFareOut, childFareIn,
            totalSum, noOfAdult,
            arrivalTimeOut, flightMode, fuelSurchargeOut, aircraftTypeOut,
            departureTimeIn, departureOut, totalChildFareIn, durationArrival,
            imageIn, individualAdultFare, flightCurrency, arrivalOut,
            flightClassCodeIn,fuelSurchargeIn,arrivalIn,childFareOut,totalFuelCharge, nationality,
            totalAdultFareOut,totalFuelChargeIn, totalFuelChargeOut, totalTaxIn,individualFuelCharge, individualChildFare,totalTax;

    String  departureReservationStatus,
            departureTTLTime,
            departureFlightId,
            departureAirlineID,
            departurePNRNO,
            departureTTLDate;
    String  arraivalReservationStatus,
            arraivalTTLTime,
            arraivalFlightId,
            arraivalAirlineID,
            arraivalPNRNO,
            arraivalTTLDate;
    String rindividualAdultCommission,rtaxIn ,rnoOfAdult,radultFareIn ,rflightCurrency,rtotalFlightCost ,rtaxOut,
            rtotalPboCommission ,rchildFareOut,rfuelSurchargeOut ,rfuelSurchargeIn,rnoOfChild ,rnationality,rtrip_type
            ,rindividualChildCommission,radultFareOut ,rchildFareIn;

    public SaveReservationModel(String adultFareIn, String flightClassCodeOut, String totalChildFareOut, String departureIn, String flightNoIn, String flightNoOut, String durationDeparture, String totalAdultFareIn, String totalOutSum, String totalChildFare, String airlinesIn, String taxIn, String airlinesOut, String totalAdultFare, String aircraftTypeIn, String totalInSum, String taxOut, String departureTimeOut, String imageOut, String noOfChild, String individualTax, String arrivalTimeIn, String totalTaxOut, String adultFareOut, String childFareIn, String totalSum, String noOfAdult, String arrivalTimeOut, String flightMode, String fuelSurchargeOut, String aircraftTypeOut, String departureTimeIn, String departureOut, String totalChildFareIn, String durationArrival, String imageIn, String individualAdultFare, String flightCurrency, String arrivalOut, String flightClassCodeIn, String fuelSurchargeIn, String arrivalIn, String childFareOut, String totalFuelCharge, String nationality, String totalAdultFareOut, String totalFuelChargeIn, String totalFuelChargeOut, String totalTaxIn, String individualFuelCharge, String individualChildFare, String totalTax, String departureReservationStatus, String departureTTLTime, String departureFlightId, String departureAirlineID, String departurePNRNO, String departureTTLDate, String arraivalReservationStatus, String arraivalTTLTime, String arraivalFlightId, String arraivalAirlineID, String arraivalPNRNO, String arraivalTTLDate, String rindividualAdultCommission, String rtaxIn, String rnoOfAdult, String radultFareIn, String rflightCurrency, String rtotalFlightCost, String rtaxOut, String rtotalPboCommission, String rchildFareOut, String rfuelSurchargeOut, String rfuelSurchargeIn, String rnoOfChild, String rnationality, String rtrip_type, String rindividualChildCommission, String radultFareOut, String rchildFareIn) {
        this.adultFareIn = adultFareIn;
        this.flightClassCodeOut = flightClassCodeOut;
        this.totalChildFareOut = totalChildFareOut;
        this.departureIn = departureIn;
        this.flightNoIn = flightNoIn;
        this.flightNoOut = flightNoOut;
        this.durationDeparture = durationDeparture;
        this.totalAdultFareIn = totalAdultFareIn;
        this.totalOutSum = totalOutSum;
        this.totalChildFare = totalChildFare;
        this.airlinesIn = airlinesIn;
        this.taxIn = taxIn;
        this.airlinesOut = airlinesOut;
        this.totalAdultFare = totalAdultFare;
        this.aircraftTypeIn = aircraftTypeIn;
        this.totalInSum = totalInSum;
        this.taxOut = taxOut;
        this.departureTimeOut = departureTimeOut;
        this.imageOut = imageOut;
        this.noOfChild = noOfChild;
        this.individualTax = individualTax;
        this.arrivalTimeIn = arrivalTimeIn;
        this.totalTaxOut = totalTaxOut;
        this.adultFareOut = adultFareOut;
        this.childFareIn = childFareIn;
        this.totalSum = totalSum;
        this.noOfAdult = noOfAdult;
        this.arrivalTimeOut = arrivalTimeOut;
        this.flightMode = flightMode;
        this.fuelSurchargeOut = fuelSurchargeOut;
        this.aircraftTypeOut = aircraftTypeOut;
        this.departureTimeIn = departureTimeIn;
        this.departureOut = departureOut;
        this.totalChildFareIn = totalChildFareIn;
        this.durationArrival = durationArrival;
        this.imageIn = imageIn;
        this.individualAdultFare = individualAdultFare;
        this.flightCurrency = flightCurrency;
        this.arrivalOut = arrivalOut;
        this.flightClassCodeIn = flightClassCodeIn;
        this.fuelSurchargeIn = fuelSurchargeIn;
        this.arrivalIn = arrivalIn;
        this.childFareOut = childFareOut;
        this.totalFuelCharge = totalFuelCharge;
        this.nationality = nationality;
        this.totalAdultFareOut = totalAdultFareOut;
        this.totalFuelChargeIn = totalFuelChargeIn;
        this.totalFuelChargeOut = totalFuelChargeOut;
        this.totalTaxIn = totalTaxIn;
        this.individualFuelCharge = individualFuelCharge;
        this.individualChildFare = individualChildFare;
        this.totalTax = totalTax;
        this.departureReservationStatus = departureReservationStatus;
        this.departureTTLTime = departureTTLTime;
        this.departureFlightId = departureFlightId;
        this.departureAirlineID = departureAirlineID;
        this.departurePNRNO = departurePNRNO;
        this.departureTTLDate = departureTTLDate;
        this.arraivalReservationStatus = arraivalReservationStatus;
        this.arraivalTTLTime = arraivalTTLTime;
        this.arraivalFlightId = arraivalFlightId;
        this.arraivalAirlineID = arraivalAirlineID;
        this.arraivalPNRNO = arraivalPNRNO;
        this.arraivalPNRNO = arraivalTTLDate;
        this.rindividualAdultCommission = rindividualAdultCommission;
        this.rtaxIn = rtaxIn;
        this.rnoOfAdult = rnoOfAdult;
        this.radultFareIn = radultFareIn;
        this.rflightCurrency = rflightCurrency;
        this.rtotalFlightCost = rtotalFlightCost;
        this.rtaxOut = rtaxOut;
        this.rtotalPboCommission = rtotalPboCommission;
        this.rchildFareOut = rchildFareOut;
        this.rfuelSurchargeOut = rfuelSurchargeOut;
        this.rfuelSurchargeIn = rfuelSurchargeIn;
        this.rnoOfChild = rnoOfChild;
        this.rnationality = rnationality;
        this.rtrip_type = rtrip_type;
        this.rindividualChildCommission = rindividualChildCommission;
        this.radultFareOut = radultFareOut;
        this.rchildFareIn = rchildFareIn;
    }

    protected SaveReservationModel(Parcel in) {
        adultFareIn = in.readString();
        flightClassCodeOut = in.readString();
        totalChildFareOut = in.readString();
        departureIn = in.readString();
        flightNoIn = in.readString();
        flightNoOut = in.readString();
        durationDeparture = in.readString();
        totalAdultFareIn = in.readString();
        totalOutSum = in.readString();
        totalChildFare = in.readString();
        airlinesIn = in.readString();
        taxIn = in.readString();
        airlinesOut = in.readString();
        totalAdultFare = in.readString();
        aircraftTypeIn = in.readString();
        totalInSum = in.readString();
        taxOut = in.readString();
        departureTimeOut = in.readString();
        imageOut = in.readString();
        noOfChild = in.readString();
        individualTax = in.readString();
        arrivalTimeIn = in.readString();
        totalTaxOut = in.readString();
        adultFareOut = in.readString();
        childFareIn = in.readString();
        totalSum = in.readString();
        noOfAdult = in.readString();
        arrivalTimeOut = in.readString();
        flightMode = in.readString();
        fuelSurchargeOut = in.readString();
        aircraftTypeOut = in.readString();
        departureTimeIn = in.readString();
        departureOut = in.readString();
        totalChildFareIn = in.readString();
        durationArrival = in.readString();
        imageIn = in.readString();
        individualAdultFare = in.readString();
        flightCurrency = in.readString();
        arrivalOut = in.readString();
        flightClassCodeIn = in.readString();
        fuelSurchargeIn = in.readString();
        arrivalIn = in.readString();
        childFareOut = in.readString();
        totalFuelCharge = in.readString();
        nationality = in.readString();
        totalAdultFareOut = in.readString();
        totalFuelChargeIn = in.readString();
        totalFuelChargeOut = in.readString();
        totalTaxIn = in.readString();
        individualFuelCharge = in.readString();
        individualChildFare = in.readString();
        totalTax = in.readString();
        departureReservationStatus = in.readString();
        departureTTLTime = in.readString();
        departureFlightId = in.readString();
        departureAirlineID = in.readString();
        departurePNRNO = in.readString();
        departureTTLDate = in.readString();
        arraivalReservationStatus = in.readString();
        arraivalTTLTime = in.readString();
        arraivalFlightId = in.readString();
        arraivalAirlineID = in.readString();
        arraivalPNRNO = in.readString();
        arraivalTTLDate = in.readString();
        rindividualAdultCommission = in.readString();
        rtaxIn = in.readString();
        rnoOfAdult = in.readString();
        radultFareIn = in.readString();
        rflightCurrency = in.readString();
        rtotalFlightCost = in.readString();
        rtaxOut = in.readString();
        rtotalPboCommission = in.readString();
        rchildFareOut = in.readString();
        rfuelSurchargeOut = in.readString();
        rfuelSurchargeIn = in.readString();
        rnoOfChild = in.readString();
        rnationality = in.readString();
        rtrip_type = in.readString();
        rindividualChildCommission = in.readString();
        radultFareOut = in.readString();
        rchildFareIn = in.readString();
    }

    public static final Creator<SaveReservationModel> CREATOR = new Creator<SaveReservationModel>() {
        @Override
        public SaveReservationModel createFromParcel(Parcel in) {
            return new SaveReservationModel(in);
        }

        @Override
        public SaveReservationModel[] newArray(int size) {
            return new SaveReservationModel[size];
        }
    };

    public String getAdultFareIn() {
        return adultFareIn;
    }

    public void setAdultFareIn(String adultFareIn) {
        this.adultFareIn = adultFareIn;
    }

    public String getFlightClassCodeOut() {
        return flightClassCodeOut;
    }

    public void setFlightClassCodeOut(String flightClassCodeOut) {
        this.flightClassCodeOut = flightClassCodeOut;
    }

    public String getTotalChildFareOut() {
        return totalChildFareOut;
    }

    public void setTotalChildFareOut(String totalChildFareOut) {
        this.totalChildFareOut = totalChildFareOut;
    }

    public String getDepartureIn() {
        return departureIn;
    }

    public void setDepartureIn(String departureIn) {
        this.departureIn = departureIn;
    }

    public String getFlightNoIn() {
        return flightNoIn;
    }

    public void setFlightNoIn(String flightNoIn) {
        this.flightNoIn = flightNoIn;
    }

    public String getFlightNoOut() {
        return flightNoOut;
    }

    public void setFlightNoOut(String flightNoOut) {
        this.flightNoOut = flightNoOut;
    }

    public String getDurationDeparture() {
        return durationDeparture;
    }

    public void setDurationDeparture(String durationDeparture) {
        this.durationDeparture = durationDeparture;
    }

    public String getTotalAdultFareIn() {
        return totalAdultFareIn;
    }

    public void setTotalAdultFareIn(String totalAdultFareIn) {
        this.totalAdultFareIn = totalAdultFareIn;
    }

    public String getTotalOutSum() {
        return totalOutSum;
    }

    public void setTotalOutSum(String totalOutSum) {
        this.totalOutSum = totalOutSum;
    }

    public String getTotalChildFare() {
        return totalChildFare;
    }

    public void setTotalChildFare(String totalChildFare) {
        this.totalChildFare = totalChildFare;
    }

    public String getAirlinesIn() {
        return airlinesIn;
    }

    public void setAirlinesIn(String airlinesIn) {
        this.airlinesIn = airlinesIn;
    }

    public String getTaxIn() {
        return taxIn;
    }

    public void setTaxIn(String taxIn) {
        this.taxIn = taxIn;
    }

    public String getAirlinesOut() {
        return airlinesOut;
    }

    public void setAirlinesOut(String airlinesOut) {
        this.airlinesOut = airlinesOut;
    }

    public String getTotalAdultFare() {
        return totalAdultFare;
    }

    public void setTotalAdultFare(String totalAdultFare) {
        this.totalAdultFare = totalAdultFare;
    }

    public String getAircraftTypeIn() {
        return aircraftTypeIn;
    }

    public void setAircraftTypeIn(String aircraftTypeIn) {
        this.aircraftTypeIn = aircraftTypeIn;
    }

    public String getTotalInSum() {
        return totalInSum;
    }

    public void setTotalInSum(String totalInSum) {
        this.totalInSum = totalInSum;
    }

    public String getTaxOut() {
        return taxOut;
    }

    public void setTaxOut(String taxOut) {
        this.taxOut = taxOut;
    }

    public String getDepartureTimeOut() {
        return departureTimeOut;
    }

    public void setDepartureTimeOut(String departureTimeOut) {
        this.departureTimeOut = departureTimeOut;
    }

    public String getImageOut() {
        return imageOut;
    }

    public void setImageOut(String imageOut) {
        this.imageOut = imageOut;
    }

    public String getNoOfChild() {
        return noOfChild;
    }

    public void setNoOfChild(String noOfChild) {
        this.noOfChild = noOfChild;
    }

    public String getIndividualTax() {
        return individualTax;
    }

    public void setIndividualTax(String individualTax) {
        this.individualTax = individualTax;
    }

    public String getArrivalTimeIn() {
        return arrivalTimeIn;
    }

    public void setArrivalTimeIn(String arrivalTimeIn) {
        this.arrivalTimeIn = arrivalTimeIn;
    }

    public String getTotalTaxOut() {
        return totalTaxOut;
    }

    public void setTotalTaxOut(String totalTaxOut) {
        this.totalTaxOut = totalTaxOut;
    }

    public String getAdultFareOut() {
        return adultFareOut;
    }

    public void setAdultFareOut(String adultFareOut) {
        this.adultFareOut = adultFareOut;
    }

    public String getChildFareIn() {
        return childFareIn;
    }

    public void setChildFareIn(String childFareIn) {
        this.childFareIn = childFareIn;
    }

    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }

    public String getNoOfAdult() {
        return noOfAdult;
    }

    public void setNoOfAdult(String noOfAdult) {
        this.noOfAdult = noOfAdult;
    }

    public String getArrivalTimeOut() {
        return arrivalTimeOut;
    }

    public void setArrivalTimeOut(String arrivalTimeOut) {
        this.arrivalTimeOut = arrivalTimeOut;
    }

    public String getFlightMode() {
        return flightMode;
    }

    public void setFlightMode(String flightMode) {
        this.flightMode = flightMode;
    }

    public String getFuelSurchargeOut() {
        return fuelSurchargeOut;
    }

    public void setFuelSurchargeOut(String fuelSurchargeOut) {
        this.fuelSurchargeOut = fuelSurchargeOut;
    }

    public String getAircraftTypeOut() {
        return aircraftTypeOut;
    }

    public void setAircraftTypeOut(String aircraftTypeOut) {
        this.aircraftTypeOut = aircraftTypeOut;
    }

    public String getDepartureTimeIn() {
        return departureTimeIn;
    }

    public void setDepartureTimeIn(String departureTimeIn) {
        this.departureTimeIn = departureTimeIn;
    }

    public String getDepartureOut() {
        return departureOut;
    }

    public void setDepartureOut(String departureOut) {
        this.departureOut = departureOut;
    }

    public String getTotalChildFareIn() {
        return totalChildFareIn;
    }

    public void setTotalChildFareIn(String totalChildFareIn) {
        this.totalChildFareIn = totalChildFareIn;
    }

    public String getDurationArrival() {
        return durationArrival;
    }

    public void setDurationArrival(String durationArrival) {
        this.durationArrival = durationArrival;
    }

    public String getImageIn() {
        return imageIn;
    }

    public void setImageIn(String imageIn) {
        this.imageIn = imageIn;
    }

    public String getIndividualAdultFare() {
        return individualAdultFare;
    }

    public void setIndividualAdultFare(String individualAdultFare) {
        this.individualAdultFare = individualAdultFare;
    }

    public String getFlightCurrency() {
        return flightCurrency;
    }

    public void setFlightCurrency(String flightCurrency) {
        this.flightCurrency = flightCurrency;
    }

    public String getArrivalOut() {
        return arrivalOut;
    }

    public void setArrivalOut(String arrivalOut) {
        this.arrivalOut = arrivalOut;
    }

    public String getFlightClassCodeIn() {
        return flightClassCodeIn;
    }

    public void setFlightClassCodeIn(String flightClassCodeIn) {
        this.flightClassCodeIn = flightClassCodeIn;
    }

    public String getFuelSurchargeIn() {
        return fuelSurchargeIn;
    }

    public void setFuelSurchargeIn(String fuelSurchargeIn) {
        this.fuelSurchargeIn = fuelSurchargeIn;
    }

    public String getArrivalIn() {
        return arrivalIn;
    }

    public void setArrivalIn(String arrivalIn) {
        this.arrivalIn = arrivalIn;
    }

    public String getChildFareOut() {
        return childFareOut;
    }

    public void setChildFareOut(String childFareOut) {
        this.childFareOut = childFareOut;
    }

    public String getTotalFuelCharge() {
        return totalFuelCharge;
    }

    public void setTotalFuelCharge(String totalFuelCharge) {
        this.totalFuelCharge = totalFuelCharge;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTotalAdultFareOut() {
        return totalAdultFareOut;
    }

    public void setTotalAdultFareOut(String totalAdultFareOut) {
        this.totalAdultFareOut = totalAdultFareOut;
    }

    public String getTotalFuelChargeIn() {
        return totalFuelChargeIn;
    }

    public void setTotalFuelChargeIn(String totalFuelChargeIn) {
        this.totalFuelChargeIn = totalFuelChargeIn;
    }

    public String getTotalFuelChargeOut() {
        return totalFuelChargeOut;
    }

    public void setTotalFuelChargeOut(String totalFuelChargeOut) {
        this.totalFuelChargeOut = totalFuelChargeOut;
    }

    public String getTotalTaxIn() {
        return totalTaxIn;
    }

    public void setTotalTaxIn(String totalTaxIn) {
        this.totalTaxIn = totalTaxIn;
    }

    public String getIndividualFuelCharge() {
        return individualFuelCharge;
    }

    public void setIndividualFuelCharge(String individualFuelCharge) {
        this.individualFuelCharge = individualFuelCharge;
    }

    public String getIndividualChildFare() {
        return individualChildFare;
    }

    public void setIndividualChildFare(String individualChildFare) {
        this.individualChildFare = individualChildFare;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getDepartureReservationStatus() {
        return departureReservationStatus;
    }

    public void setDepartureReservationStatus(String departureReservationStatus) {
        this.departureReservationStatus = departureReservationStatus;
    }

    public String getDepartureTTLTime() {
        return departureTTLTime;
    }

    public void setDepartureTTLTime(String departureTTLTime) {
        this.departureTTLTime = departureTTLTime;
    }

    public String getDepartureFlightId() {
        return departureFlightId;
    }

    public void setDepartureFlightId(String departureFlightId) {
        this.departureFlightId = departureFlightId;
    }

    public String getDepartureAirlineID() {
        return departureAirlineID;
    }

    public void setDepartureAirlineID(String departureAirlineID) {
        this.departureAirlineID = departureAirlineID;
    }

    public String getDeparturePNRNO() {
        return departurePNRNO;
    }

    public void setDeparturePNRNO(String departurePNRNO) {
        this.departurePNRNO = departurePNRNO;
    }

    public String getDepartureTTLDate() {
        return departureTTLDate;
    }

    public void setDepartureTTLDate(String departureTTLDate) {
        this.departureTTLDate = departureTTLDate;
    }

    public String getArraivalReservationStatus() {
        return arraivalReservationStatus;
    }

    public void setArraivalReservationStatus(String arraivalReservationStatus) {
        this.arraivalReservationStatus = arraivalReservationStatus;
    }

    public String getArraivalTTLTime() {
        return arraivalTTLTime;
    }

    public void setArraivalTTLTime(String arraivalTTLTime) {
        this.arraivalTTLTime = arraivalTTLTime;
    }

    public String getArraivalFlightId() {
        return arraivalFlightId;
    }

    public void setArraivalFlightId(String arraivalFlightId) {
        this.arraivalFlightId = arraivalFlightId;
    }

    public String getArraivalAirlineID() {
        return arraivalAirlineID;
    }

    public void setArraivalAirlineID(String arraivalAirlineID) {
        this.arraivalAirlineID = arraivalAirlineID;
    }

    public String getArraivalPNRNO() {
        return arraivalPNRNO;
    }

    public void setArraivalPNRNO(String arraivalPNRNO) {
        this.arraivalPNRNO = arraivalPNRNO;
    }

    public String getArraivalTTLDate() {
        return arraivalTTLDate;
    }

    public void setArraivalTTLDate(String arraivalTTLDate) {
        this.arraivalTTLDate = arraivalTTLDate;
    }

    public String getRindividualAdultCommission() {
        return rindividualAdultCommission;
    }

    public void setRindividualAdultCommission(String rindividualAdultCommission) {
        this.rindividualAdultCommission = rindividualAdultCommission;
    }

    public String getRtaxIn() {
        return rtaxIn;
    }

    public void setRtaxIn(String rtaxIn) {
        this.rtaxIn = rtaxIn;
    }

    public String getRnoOfAdult() {
        return rnoOfAdult;
    }

    public void setRnoOfAdult(String rnoOfAdult) {
        this.rnoOfAdult = rnoOfAdult;
    }

    public String getRadultFareIn() {
        return radultFareIn;
    }

    public void setRadultFareIn(String radultFareIn) {
        this.radultFareIn = radultFareIn;
    }

    public String getRflightCurrency() {
        return rflightCurrency;
    }

    public void setRflightCurrency(String rflightCurrency) {
        this.rflightCurrency = rflightCurrency;
    }

    public String getRtotalFlightCost() {
        return rtotalFlightCost;
    }

    public void setRtotalFlightCost(String rtotalFlightCost) {
        this.rtotalFlightCost = rtotalFlightCost;
    }

    public String getRtaxOut() {
        return rtaxOut;
    }

    public void setRtaxOut(String rtaxOut) {
        this.rtaxOut = rtaxOut;
    }

    public String getRtotalPboCommission() {
        return rtotalPboCommission;
    }

    public void setRtotalPboCommission(String rtotalPboCommission) {
        this.rtotalPboCommission = rtotalPboCommission;
    }

    public String getRchildFareOut() {
        return rchildFareOut;
    }

    public void setRchildFareOut(String rchildFareOut) {
        this.rchildFareOut = rchildFareOut;
    }

    public String getRfuelSurchargeOut() {
        return rfuelSurchargeOut;
    }

    public void setRfuelSurchargeOut(String rfuelSurchargeOut) {
        this.rfuelSurchargeOut = rfuelSurchargeOut;
    }

    public String getRfuelSurchargeIn() {
        return rfuelSurchargeIn;
    }

    public void setRfuelSurchargeIn(String rfuelSurchargeIn) {
        this.rfuelSurchargeIn = rfuelSurchargeIn;
    }

    public String getRnoOfChild() {
        return rnoOfChild;
    }

    public void setRnoOfChild(String rnoOfChild) {
        this.rnoOfChild = rnoOfChild;
    }

    public String getRnationality() {
        return rnationality;
    }

    public void setRnationality(String rnationality) {
        this.rnationality = rnationality;
    }

    public String getRtrip_type() {
        return rtrip_type;
    }

    public void setRtrip_type(String rtrip_type) {
        this.rtrip_type = rtrip_type;
    }

    public String getRindividualChildCommission() {
        return rindividualChildCommission;
    }

    public void setRindividualChildCommission(String rindividualChildCommission) {
        this.rindividualChildCommission = rindividualChildCommission;
    }

    public String getRadultFareOut() {
        return radultFareOut;
    }

    public void setRadultFareOut(String radultFareOut) {
        this.radultFareOut = radultFareOut;
    }

    public String getRchildFareIn() {
        return rchildFareIn;
    }

    public void setRchildFareIn(String rchildFareIn) {
        this.rchildFareIn = rchildFareIn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adultFareIn);
        dest.writeString(flightClassCodeOut);
        dest.writeString(totalChildFareOut);
        dest.writeString(departureIn);
        dest.writeString(flightNoIn);
        dest.writeString(flightNoOut);
        dest.writeString(durationDeparture);
        dest.writeString(totalAdultFareIn);
        dest.writeString(totalOutSum);
        dest.writeString(totalChildFare);
        dest.writeString(airlinesIn);
        dest.writeString(taxIn);
        dest.writeString(airlinesOut);
        dest.writeString(totalAdultFare);
        dest.writeString(aircraftTypeIn);
        dest.writeString(totalInSum);
        dest.writeString(taxOut);
        dest.writeString(departureTimeOut);
        dest.writeString(imageOut);
        dest.writeString(noOfChild);
        dest.writeString(individualTax);
        dest.writeString(arrivalTimeIn);
        dest.writeString(totalTaxOut);
        dest.writeString(adultFareOut);
        dest.writeString(childFareIn);
        dest.writeString(totalSum);
        dest.writeString(noOfAdult);
        dest.writeString(arrivalTimeOut);
        dest.writeString(flightMode);
        dest.writeString(fuelSurchargeOut);
        dest.writeString(aircraftTypeOut);
        dest.writeString(departureTimeIn);
        dest.writeString(departureOut);
        dest.writeString(totalChildFareIn);
        dest.writeString(durationArrival);
        dest.writeString(imageIn);
        dest.writeString(individualAdultFare);
        dest.writeString(flightCurrency);
        dest.writeString(arrivalOut);
        dest.writeString(flightClassCodeIn);
        dest.writeString(fuelSurchargeIn);
        dest.writeString(arrivalIn);
        dest.writeString(childFareOut);
        dest.writeString(totalFuelCharge);
        dest.writeString(nationality);
        dest.writeString(totalAdultFareOut);
        dest.writeString(totalFuelChargeIn);
        dest.writeString(totalFuelChargeOut);
        dest.writeString(totalTaxIn);
        dest.writeString(individualFuelCharge);
        dest.writeString(individualChildFare);
        dest.writeString(totalTax);
        dest.writeString(departureReservationStatus);
        dest.writeString(departureTTLTime);
        dest.writeString(departureFlightId);
        dest.writeString(departureAirlineID);
        dest.writeString(departurePNRNO);
        dest.writeString(departureTTLDate);
        dest.writeString(arraivalReservationStatus);
        dest.writeString(arraivalTTLTime);
        dest.writeString(arraivalFlightId);
        dest.writeString(arraivalAirlineID);
        dest.writeString(arraivalPNRNO);
        dest.writeString(arraivalTTLDate);
        dest.writeString(rindividualAdultCommission);
        dest.writeString(rtaxIn);
        dest.writeString(rnoOfAdult);
        dest.writeString(radultFareIn);
        dest.writeString(rflightCurrency);
        dest.writeString(rtotalFlightCost);
        dest.writeString(rtaxOut);
        dest.writeString(rtotalPboCommission);
        dest.writeString(rchildFareOut);
        dest.writeString(rfuelSurchargeOut);
        dest.writeString(rfuelSurchargeIn);
        dest.writeString(rnoOfChild);
        dest.writeString(rnationality);
        dest.writeString(rtrip_type);
        dest.writeString(rindividualChildCommission);
        dest.writeString(radultFareOut);
        dest.writeString(rchildFareIn);
    }
}
