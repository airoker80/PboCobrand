package com.paybyonline.KantipurPay.Adapter.Model;
public class BalanceRequest {

	public  String requestedDate;
	public  String purchasedAmount;
	public  String discountPercent;
	public  String discountAmount;
	public  String paidAmount;
	public  String paidUsing;
	public  String payStatus;
	public  String transactionNo;
	public  String additionAmount;
    public	String flatPercent;
   public String flatDiscount;
   public String flatAddition;
  public String totalAmt;
  
  

	
	public BalanceRequest(String status, String transactionNo, String paidUsing, String requestedDate,
						  String purchasedAmount, String flatPercent, String flatDiscount, String flatAddition, String totalAmt, String discountPercent,
						  String discountAmount, String additionAmount, String paidAmount) {
		this.requestedDate = requestedDate;
		this.purchasedAmount = purchasedAmount;
		this.discountPercent = discountPercent;
		this.discountAmount = discountAmount;
		this.paidAmount = paidAmount;
		this.paidUsing = paidUsing;
		this.payStatus = status;
		this.transactionNo = transactionNo;
		this.additionAmount = additionAmount;
		this.flatPercent=flatPercent;
		this.flatDiscount=flatDiscount;
		this.flatAddition=flatAddition;
		this.totalAmt=totalAmt;
	}
	public void settotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String gettotalAmt() {
		return totalAmt;
	}
	
	public void setflatAddition(String flatAddition) {
		this.flatAddition= flatAddition;
	}
	public String getflatAddition() {
		return flatAddition;
	}
	public void setflatDiscount(String flatDiscount) {
		this.flatDiscount= flatDiscount;
	}
	public String getflatDiscount() {
		return flatDiscount;
	}
	public void setflatPercent(String flatPercent) {
		this.flatPercent = flatPercent;
	}
	public String getflatPercent() {
		return flatPercent;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getAdditionAmount() {
		return additionAmount;
	}
	public void setAdditionAmount(String additionAmount) {
		this.additionAmount = additionAmount;
	}
	
	public String getRequestedDate() {
		return requestedDate;
	}
	public void setResquestedDate(String resquestedDate) {
		this.requestedDate = requestedDate;
	}
	public String getPurchasedAmount() {
		return purchasedAmount;
	}
	public void setPurchasedAmount(String purchasedAmount) {
		this.purchasedAmount = purchasedAmount;
	}
	public String getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(String discountPercent) {
		this.discountPercent = discountPercent;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getPaidUsing() {
		return paidUsing;
	}
	public void setPaidUsing(String paidUsing) {
		this.paidUsing = paidUsing;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String status) {
		this.payStatus = status;
	}
	
}
