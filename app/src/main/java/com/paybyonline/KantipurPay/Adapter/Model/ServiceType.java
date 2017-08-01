package com.paybyonline.KantipurPay.Adapter.Model;

public class ServiceType {
	
	private String identification_label;
	private String service_type_name;
	private String service_type_id;
	private String country;
	private String service_logo;
	private String scstAmountType;
	private String scst_amount_value;
	private String startsWith;
	private String minVal;
	private String maxVal;
	private String iLabel;
	private String isPinRechargeService;
	private String currency;
	private String categoryLength;
	private String isProductEnable;
	private String enablePromoCode;
	private String serviceCategoryServiceType;


public ServiceType(String startsWith,
                    String minVal,
                    String maxVal,
                    String iLabel,
                    String isPinRechargeService,
                    String categoryLength,
				   String  service_type_name,
				   String service_type_id,
				   String country,String service_logo,
				   String scstAmountType,
				   String currency,String scstAmountValue,
				   String isProductEnable,
				   String enablePromoCode,String serviceCategoryServiceType) {
	super();

	this.service_type_name = service_type_name;
	this.identification_label =  identification_label;
	this.service_type_id = service_type_id;
	this.country = country;
	this.service_logo = service_logo;
	this.scst_amount_value = scstAmountValue;
	this.scstAmountType = scstAmountType;
	this.currency = currency;
	this.startsWith = startsWith;
	this.minVal = minVal;
	this.maxVal = maxVal;
	this.iLabel = iLabel;
	this.categoryLength = categoryLength;
	this.isPinRechargeService = isPinRechargeService;
	this.isProductEnable = isProductEnable;
	this.enablePromoCode = enablePromoCode;
	this.serviceCategoryServiceType = serviceCategoryServiceType;
}

	public String getIsProductEnable() {
		return isProductEnable;
	}

	public void setIsProductEnable(String isProductEnable) {
		this.isProductEnable = isProductEnable;
	}

	public ServiceType(String identification_label, String service_type_name,
					   String service_type_id) {
		super();
		this.identification_label = identification_label;
		this.service_type_name = service_type_name;
		this.service_type_id = service_type_id;
	}

	public ServiceType(String identification_label, String service_type_name, String service_type_id, String service_logo) {
		super();
		this.identification_label = identification_label;
		this.service_type_name = service_type_name;
		this.service_type_id = service_type_id;
		this.service_logo = service_logo;
	}

	public String getSevice_logo() {
		return service_logo;
	}

	public void setSevice_logo(String sevice_logo) {
		this.service_logo = sevice_logo;
	}

	public String getIdentification_label() {
		return identification_label;
	}
	public void setIdentification_label(String identification_label) {
		this.identification_label = identification_label;
	}

	public String getService_type_name() {
		return service_type_name;
	}

	public void setService_type_name(String service_type_name) {
		this.service_type_name = service_type_name;
	}

	public String getService_type_id() {
		return service_type_id;
	}

	public void setService_type_id(String service_type_id) {
		this.service_type_id = service_type_id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getService_logo() {
		return service_logo;
	}

	public void setService_logo(String service_logo) {
		this.service_logo = service_logo;
	}

	public String getScstAmountType() {
		return scstAmountType;
	}

	public void setScstAmountType(String scstAmountType) {
		this.scstAmountType = scstAmountType;
	}

	public String getCurrency() {
		return currency;
	}

	public String getScst_amount_value() {
		return scst_amount_value;
	}

	public String getStartsWith() {
		return startsWith;
	}

	public void setStartsWith(String startsWith) {
		this.startsWith = startsWith;
	}

	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public String getIsPinRechargeService() {
		return isPinRechargeService;
	}

	public void setIsPinRechargeService(String isPinRechargeService) {
		this.isPinRechargeService = isPinRechargeService;
	}

	public String getiLabel() {
		return iLabel;
	}

	public void setiLabel(String iLabel) {
		this.iLabel = iLabel;
	}

	public String getCategoryLength() {
		return categoryLength;
	}

	public void setCategoryLength(String categoryLength) {
		this.categoryLength = categoryLength;
	}

	public void setScst_amount_value(String scst_amount_value) {
		this.scst_amount_value = scst_amount_value;

	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEnablePromoCode() {
		return enablePromoCode;
	}

	public void setEnablePromoCode(String enablePromoCode) {
		this.enablePromoCode = enablePromoCode;
	}


	public String getServiceCategoryServiceType() {
		return serviceCategoryServiceType;
	}

	public void setServiceCategoryServiceType(String serviceCategoryServiceType) {
		this.serviceCategoryServiceType = serviceCategoryServiceType;
	}
}
