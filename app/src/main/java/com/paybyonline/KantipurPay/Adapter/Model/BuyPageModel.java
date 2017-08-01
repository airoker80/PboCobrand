package com.paybyonline.KantipurPay.Adapter.Model;

/**
 * Created by Anish on 4/24/2016.
 */
public class BuyPageModel {

    String serviceCategory;
    String serviceType;
    String serviceClassification;
    String tag;
    String logoName;
    String scstId;
    String merchantType;
    String countryName;
    String productType;


    public BuyPageModel(){}

    public BuyPageModel(String serviceCategory, String serviceType, String serviceClassification,
                        String tag, String productType, String logoName, String scstId, String merchantType,
                        String countryName) {
        this.serviceCategory = serviceCategory;
        this.serviceType = serviceType;
        this.serviceClassification = serviceClassification;
        this.tag = tag;
        this.logoName = logoName;
        this.scstId = scstId;
        this.merchantType = merchantType;
        this.countryName = countryName;
        this.productType = productType;
    }
    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceClassification() {
        return serviceClassification;
    }

    public void setServiceClassification(String serviceClassification) {
        this.serviceClassification = serviceClassification;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getScstId() {
        return scstId;
    }

    public void setScstId(String scstId) {
        this.scstId = scstId;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getProducttype() {
        return countryName;
    }

    public void setProductType(String countryName) {
        this.countryName = countryName;
    }





}
