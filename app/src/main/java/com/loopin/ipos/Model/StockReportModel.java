package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockReportModel {
    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;
    @SerializedName("SIZE_VALUE")
    @Expose
    private Integer sizeValue;
    @SerializedName("STORE_OPENING")
    @Expose
    private Integer storeOpening;
    @SerializedName("COUNTER_OPENING")
    @Expose
    private Integer counterOpening;
    @SerializedName("CLOSING_BALANCE")
    @Expose
    private Integer closingBalance;
    @SerializedName("BREAKAGE")
    @Expose
    private Integer breakage;
    @SerializedName("EXCISE_PRICE")
    @Expose
    private Double excisePrice;
    @SerializedName("WSP")
    @Expose
    private Double wsp;
    @SerializedName("CUSTOM_DUTY")
    @Expose
    private Double customDuty;
    @SerializedName("MRP")
    @Expose
    private Double mrp;
    @SerializedName("TOTAL_AMOUNT")
    @Expose
    private Double totalAmount;

    public StockReportModel(String brandName, Integer sizeValue, Integer storeOpening,
                            Integer counterOpening, Integer breakage, Integer closingBalance,
                            Double excisePrice, Double wsp, Double customDuty,
                            Double mrp, Double totalAmount) {
        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.storeOpening = storeOpening;
        this.counterOpening = counterOpening;
        this.breakage = breakage;
        this.closingBalance = closingBalance;
        this.excisePrice = excisePrice;
        this.wsp = wsp;
        this.customDuty = customDuty;
        this.mrp = mrp;
        this.totalAmount = totalAmount;
    }

    public String getBrandName() {
        return brandName;
    }

    public Integer getSizeValue() {
        return sizeValue;
    }

    public Integer getStoreOpening() {
        return storeOpening;
    }

    public Integer getCounterOpening() {
        return counterOpening;
    }

    public Integer getClosingBalance() {
        return closingBalance;
    }

    public Integer getBreakage() {
        return breakage;
    }

    public Double getExcisePrice() {
        return excisePrice;
    }

    public Double getWsp() {
        return wsp;
    }

    public Double getCustomDuty() {
        return customDuty;
    }

    public Double getMrp() {
        return mrp;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
