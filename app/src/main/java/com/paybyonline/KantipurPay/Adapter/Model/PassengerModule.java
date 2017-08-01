package com.paybyonline.KantipurPay.Adapter.Model;

import android.graphics.Bitmap;

/**
 * Created by Sameer on 4/17/2017.
 */

public class PassengerModule {
    String passengerSN,passengerName,passengerLastName,passengerType,PassengerGender,passengerContact;

    public String getPassengerSN() {
        return passengerSN;
    }

    public void setPassengerSN(String passengerSN) {
        this.passengerSN = passengerSN;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerLastName() {
        return passengerLastName;
    }

    public void setPassengerLastName(String passengerLastName) {
        this.passengerLastName = passengerLastName;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getPassengerGender() {
        return PassengerGender;
    }

    public void setPassengerGender(String passengerGender) {
        PassengerGender = passengerGender;
    }

    public PassengerModule(String passengerSN, String passengerName, String passengerLastName, String passengerType, String passengerGender) {
        this.passengerSN = passengerSN;
        this.passengerName = passengerName;
        this.passengerLastName = passengerLastName;
        this.passengerType = passengerType;
        PassengerGender = passengerGender;

    }
}
