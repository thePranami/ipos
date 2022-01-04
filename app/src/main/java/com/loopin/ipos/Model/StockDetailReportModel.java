package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockDetailReportModel {
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
    @SerializedName("OPENING_BOTTLES")
    @Expose
    private Integer openingBottles;
    @SerializedName("RECEIVE_BOTTLES")
    @Expose
    private Integer receiveBottles;
    @SerializedName("TOTAL_OPENING")
    @Expose
    private Integer totalOpening;
    @SerializedName("DAMAGE_BOTTLES")
    @Expose
    private Integer damageBottles;
    @SerializedName("SALE_BOTTLES")
    @Expose
    private Integer saleBottles;
    @SerializedName("STORE_CLOSING")
    @Expose
    private Integer storeClosing;
    @SerializedName("COUNTER_CLOSING")
    @Expose
    private Integer counterClosing;
    @SerializedName("CLOSING_BALANCE")
    @Expose
    private Integer closingBalance;
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

    public StockDetailReportModel(String brandName, Integer sizeValue, Integer storeOpening, Integer counterOpening,
                                  Integer openingBottles, Integer receiveBottles, Integer totalOpening,
                                  Integer damageBottles, Integer saleBottles, Integer storeClosing,
                                  Integer counterClosing, Integer closingBalance, Double excisePrice,
                                  Double wsp, Double customDuty, Double mrp, Double totalAmount) {

        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.storeOpening = storeOpening;
        this.counterOpening = counterOpening;
        this.openingBottles = openingBottles;
        this.receiveBottles = receiveBottles;
        this.totalOpening = totalOpening;
        this.damageBottles = damageBottles;
        this.saleBottles = saleBottles;
        this.storeClosing = storeClosing;
        this.counterClosing = counterClosing;
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

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(Integer sizeValue) {
        this.sizeValue = sizeValue;
    }

    public Integer getStoreOpening() {
        return storeOpening;
    }

    public void setStoreOpening(Integer storeOpening) {
        this.storeOpening = storeOpening;
    }

    public Integer getCounterOpening() {
        return counterOpening;
    }

    public void setCounterOpening(Integer counterOpening) {
        this.counterOpening = counterOpening;
    }

    public Integer getOpeningBottles() {
        return openingBottles;
    }

    public void setOpeningBottles(Integer openingBottles) {
        this.openingBottles = openingBottles;
    }

    public Integer getReceiveBottles() {
        return receiveBottles;
    }

    public void setReceiveBottles(Integer receiveBottles) {
        this.receiveBottles = receiveBottles;
    }

    public Integer getTotalOpening() {
        return totalOpening;
    }

    public void setTotalOpening(Integer totalOpening) {
        this.totalOpening = totalOpening;
    }

    public Integer getDamageBottles() {
        return damageBottles;
    }

    public void setDamageBottles(Integer damageBottles) {
        this.damageBottles = damageBottles;
    }

    public Integer getSaleBottles() {
        return saleBottles;
    }

    public void setSaleBottles(Integer saleBottles) {
        this.saleBottles = saleBottles;
    }

    public Integer getStoreClosing() {
        return storeClosing;
    }

    public void setStoreClosing(Integer storeClosing) {
        this.storeClosing = storeClosing;
    }

    public Integer getCounterClosing() {
        return counterClosing;
    }

    public void setCounterClosing(Integer counterClosing) {
        this.counterClosing = counterClosing;
    }

    public Integer getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Integer closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Double getExcisePrice() {
        return excisePrice;
    }

    public void setExcisePrice(Double excisePrice) {
        this.excisePrice = excisePrice;
    }

    public Double getWsp() {
        return wsp;
    }

    public void setWsp(Double wsp) {
        this.wsp = wsp;
    }

    public Double getCustomDuty() {
        return customDuty;
    }

    public void setCustomDuty(Double customDuty) {
        this.customDuty = customDuty;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
