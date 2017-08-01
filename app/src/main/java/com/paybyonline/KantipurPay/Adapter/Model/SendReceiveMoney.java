package com.paybyonline.KantipurPay.Adapter.Model;

import java.util.LinkedHashMap;

/**
 * Created by mefriend24 on 11/24/16.
 */
public class SendReceiveMoney {

    public LinkedHashMap<String, String> childListMap;
    String id;
    String purposeName;
    String transactionNo;
    String sentDate;
    String expDate;
    String showSentTo;
    String isWalletSentAmount;
    String isPspSentAmount;
    String walletSentAmount;
    String deductedWalletSentAmount;
    String walletCharge;
    String pspSentAmount;
    String deductedPspSentAmount;
    String pspCharge;
    String sentRemarks;
    String status;
    String completionDate;

    public  SendReceiveMoney(){}

    public SendReceiveMoney(LinkedHashMap<String, String> childListMap, String id, String purposeName) {
        this.childListMap = childListMap;
        this.id = id;
        this.purposeName = purposeName;
    }


    public SendReceiveMoney(String purposeName, String transactionNo, String sentDate, String expDate, String showSentTo,
                            String isWalletSentAmount, String isPspSentAmount,
                            String walletSentAmount, String deductedWalletSentAmount, String walletCharge, String pspSentAmount,
                            String deductedPspSentAmount, String pspCharge, String sentRemarks, String status, String completionDate) {
        this.purposeName = purposeName;
        this.transactionNo = transactionNo;
        this.sentDate = sentDate;
        this.expDate = expDate;
        this.showSentTo = showSentTo;
        this.isWalletSentAmount = isWalletSentAmount;
        this.isPspSentAmount = isPspSentAmount;
        this.walletSentAmount = walletSentAmount;
        this.deductedWalletSentAmount = deductedWalletSentAmount;
        this.walletCharge = walletCharge;
        this.pspSentAmount = pspSentAmount;
        this.deductedPspSentAmount = deductedPspSentAmount;
        this.pspCharge = pspCharge;
        this.sentRemarks = sentRemarks;
        this.status = status;
        this.completionDate = completionDate;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getShowSentTo() {
        return showSentTo;
    }

    public void setShowSentTo(String showSentTo) {
        this.showSentTo = showSentTo;
    }

    public String getIsWalletSentAmount() {
        return isWalletSentAmount;
    }

    public void setIsWalletSentAmount(String isWalletSentAmount) {
        this.isWalletSentAmount = isWalletSentAmount;
    }

    public String getIsPspSentAmount() {
        return isPspSentAmount;
    }

    public void setIsPspSentAmount(String isPspSentAmount) {
        this.isPspSentAmount = isPspSentAmount;
    }

    public String getWalletSentAmount() {
        return walletSentAmount;
    }

    public void setWalletSentAmount(String walletSentAmount) {
        this.walletSentAmount = walletSentAmount;
    }

    public String getDeductedWalletSentAmount() {
        return deductedWalletSentAmount;
    }

    public void setDeductedWalletSentAmount(String deductedWalletSentAmount) {
        this.deductedWalletSentAmount = deductedWalletSentAmount;
    }

    public String getWalletCharge() {
        return walletCharge;
    }

    public void setWalletCharge(String walletCharge) {
        this.walletCharge = walletCharge;
    }

    public String getPspSentAmount() {
        return pspSentAmount;
    }

    public void setPspSentAmount(String pspSentAmount) {
        this.pspSentAmount = pspSentAmount;
    }

    public String getDeductedPspSentAmount() {
        return deductedPspSentAmount;
    }

    public void setDeductedPspSentAmount(String deductedPspSentAmount) {
        this.deductedPspSentAmount = deductedPspSentAmount;
    }

    public String getPspCharge() {
        return pspCharge;
    }

    public void setPspCharge(String pspCharge) {
        this.pspCharge = pspCharge;
    }

    public String getSentRemarks() {
        return sentRemarks;
    }

    public void setSentRemarks(String sentRemarks) {
        this.sentRemarks = sentRemarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurposeName() {
        return purposeName;
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public LinkedHashMap<String, String> getChildListMap() {
        return childListMap;
    }

    public void setChildListMap(LinkedHashMap<String, String> childListMap) {
        this.childListMap = childListMap;
    }


}
