package com.paybyonline.KantipurPay.Adapter.Model;

/**
 * Created by Sameer on 5/19/2017.
 */

public class NationalityModel {
    String nationality,nationalityCode;

    public NationalityModel(String nationality, String nationalityCode) {
        this.nationality = nationality;
        this.nationalityCode = nationalityCode;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }
}
