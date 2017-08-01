package com.paybyonline.KantipurPay.Adapter.Model;

/**
 * Created by Anish on 11/7/2016.
 */
public class PaymentProfile {

    String usedBy;
    String profImg;
    String profName;
    String bankName;
    String paymentMethod;

    public PaymentProfile(String usedBy,String profImg,String profName,String bankName,String paymentMethod){

        this.usedBy=usedBy;
        this.profImg=profImg;
        this.profName=profName;
        this.bankName=bankName;
        this.paymentMethod=paymentMethod;
    }
    public String getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(String usedBy) {
        this.usedBy = usedBy;
    }

    public String getProfImg() {
        return profImg;
    }

    public void setProfImg(String profImg) {
        this.profImg = profImg;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


}
