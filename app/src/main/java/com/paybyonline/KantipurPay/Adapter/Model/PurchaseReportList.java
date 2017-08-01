package com.paybyonline.KantipurPay.Adapter.Model;

public class PurchaseReportList {

	public  String transactionDate;
	public  String transactionNo;
	public  String serviceCat;
	public  String purchasedAmt;
	public  String toPay;
	public  String discount;
	public  String commission;
	public  String paidAmt;
	public  String type;
	public String rewards;
	public String dealAmount;
	public String remarks;
	
	public PurchaseReportList(String transactionDate, String transactionNo,
							  String serviceCat, String purchasedAmt, String toPay,
							  String discount, String commission, String paidAmt, String type, String rewards, String dealAmount, String remarks) {
		super();
		this.transactionDate = transactionDate;
		this.transactionNo = transactionNo;
		this.serviceCat = serviceCat;
		this.purchasedAmt = purchasedAmt;
		this.toPay = toPay;
		this.discount = discount;
		this.commission = commission;
		this.paidAmt = paidAmt;
		this.type = type;
		this.rewards=rewards;
		this.dealAmount=dealAmount;
		this.remarks=remarks;
	}
	public String getReward() {
		return rewards;
	}
	public void setReward(String rewards) {
		this.rewards = rewards;
	}
	public String getRemark() {
		return remarks;
	}
	public void setRemark(String remarks) {
		this.remarks = remarks;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getServiceCat() {
		return serviceCat;
	}
	public void setServiceCat(String serviceCat) {
		this.serviceCat = serviceCat;
	}
	public String getPurchasedAmt() {
		return purchasedAmt;
	}
	public void setPurchasedAmt(String purchasedAmt) {
		this.purchasedAmt = purchasedAmt;
	}
	public String getToPay() {
		return toPay;
	}
	public void setToPay(String toPay) {
		this.toPay = toPay;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getPaidAmt() {
		return paidAmt;
	}
	public void setPaidAmt(String paidAmt) {
		this.paidAmt = paidAmt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
