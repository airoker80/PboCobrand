package com.paybyonline.KantipurPay.Adapter.Model;

/**
 * Created by Anish on 11/7/2016.
 */
public class ProfileShippingAddress {
    String id;
    String preference;
    String status;
    String remark;
    String name;
    String state;
    String zipPostalCode;
    String companyName;
    String addressLine2;
    String addressLine1;
    String country;
    String city;

    public ProfileShippingAddress(String id, String preference, String status,
                                  String remark, String name, String state,
                                  String zipPostalCode, String companyName,
                                  String addressLine2, String addressLine1, String country, String city) {
        this.id = id;
        this.preference = preference;
        this.status = status;
        this.remark = remark;
        this.name = name;
        this.state = state;
        this.zipPostalCode = zipPostalCode;
        this.companyName = companyName;
        this.addressLine2 = addressLine2;
        this.addressLine1 = addressLine1;
        this.country = country;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipPostalCode() {
        return zipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
