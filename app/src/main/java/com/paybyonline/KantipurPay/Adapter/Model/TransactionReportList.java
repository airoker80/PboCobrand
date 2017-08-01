package com.paybyonline.KantipurPay.Adapter.Model;

public class TransactionReportList {
     private String transactionDate;
     private String transactionNumber;
     private String deposit;
     private String payment;
     private String balance;
     private String description;
     private String currency;
	public TransactionReportList(String transactionDate, String transactionNumber, String deposit,
								 String payment, String balance, String description) {
		super();
		this.transactionDate = transactionDate;
		this.transactionNumber = transactionNumber;
		this.deposit = deposit;
		this.payment = payment;
		this.balance = balance;
		this.description=description;
	}

	public TransactionReportList(String transactionDate, String transactionNumber, String deposit, String payment, String balance, String description, String currency) {
		this.transactionDate = transactionDate;
		this.transactionNumber = transactionNumber;
		this.deposit = deposit;
		this.payment = payment;
		this.balance = balance;
		this.description = description;
		this.currency = currency;
	}


	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
     
}
