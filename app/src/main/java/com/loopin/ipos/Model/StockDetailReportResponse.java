package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockDetailReportResponse {
    @SerializedName("data")
    @Expose
    private List<StockDetailReportModel> data = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("TOTAL_STORE_OPENING")
    @Expose
    private Integer totalStoreOpening;
    @SerializedName("TOTAL_COUNTER_OPENING")
    @Expose
    private Integer totalCounterOpening;
    @SerializedName("SUM_OPENING_BOTTLES")
    @Expose
    private Integer sumOpeningBottles;
    @SerializedName("TOTAL_RECEIVE_BOTTLES")
    @Expose
    private Integer totalReceiveBottles;
    @SerializedName("TOTAL_OPENING_BOTTLES")
    @Expose
    private Integer totalOpeningBottles;
    @SerializedName("TOTAL_DAMAGE_BOTTLES")
    @Expose
    private Integer totalDamageBottles;
    @SerializedName("TOTAL_SALE_BOTTLES")
    @Expose
    private Integer totalSaleBottles;
    @SerializedName("TOTAL_STORE_CLOSING")
    @Expose
    private Integer totalStoreClosing;
    @SerializedName("TOTAL_COUNTER_CLOSING")
    @Expose
    private Integer totalCounterClosing;
    @SerializedName("TOTAL_CLOSING_BALANCE")
    @Expose
    private Integer totalClosingBalance;
    @SerializedName("TOTAL_EXCISE")
    @Expose
    private Double totalExcise;
    @SerializedName("TOTAL_WSP")
    @Expose
    private Double totalWsp;
    @SerializedName("TOTAL_CUSTOM")
    @Expose
    private Integer totalCustom;
    @SerializedName("SUM_TOTAL_AMOUNT")
    @Expose
    private Integer sumTotalAmount;

    public List<StockDetailReportModel> getData() {
        return data;
    }

    public void setData(List<StockDetailReportModel> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getTotalStoreOpening() {
        return totalStoreOpening;
    }

    public void setTotalStoreOpening(Integer totalStoreOpening) {
        this.totalStoreOpening = totalStoreOpening;
    }

    public Integer getTotalCounterOpening() {
        return totalCounterOpening;
    }

    public void setTotalCounterOpening(Integer totalCounterOpening) {
        this.totalCounterOpening = totalCounterOpening;
    }

    public Integer getSumOpeningBottles() {
        return sumOpeningBottles;
    }

    public void setSumOpeningBottles(Integer sumOpeningBottles) {
        this.sumOpeningBottles = sumOpeningBottles;
    }

    public Integer getTotalReceiveBottles() {
        return totalReceiveBottles;
    }

    public void setTotalReceiveBottles(Integer totalReceiveBottles) {
        this.totalReceiveBottles = totalReceiveBottles;
    }

    public Integer getTotalOpeningBottles() {
        return totalOpeningBottles;
    }

    public void setTotalOpeningBottles(Integer totalOpeningBottles) {
        this.totalOpeningBottles = totalOpeningBottles;
    }

    public Integer getTotalDamageBottles() {
        return totalDamageBottles;
    }

    public void setTotalDamageBottles(Integer totalDamageBottles) {
        this.totalDamageBottles = totalDamageBottles;
    }

    public Integer getTotalSaleBottles() {
        return totalSaleBottles;
    }

    public void setTotalSaleBottles(Integer totalSaleBottles) {
        this.totalSaleBottles = totalSaleBottles;
    }

    public Integer getTotalStoreClosing() {
        return totalStoreClosing;
    }

    public void setTotalStoreClosing(Integer totalStoreClosing) {
        this.totalStoreClosing = totalStoreClosing;
    }

    public Integer getTotalCounterClosing() {
        return totalCounterClosing;
    }

    public void setTotalCounterClosing(Integer totalCounterClosing) {
        this.totalCounterClosing = totalCounterClosing;
    }

    public Integer getTotalClosingBalance() {
        return totalClosingBalance;
    }

    public void setTotalClosingBalance(Integer totalClosingBalance) {
        this.totalClosingBalance = totalClosingBalance;
    }

    public Double getTotalExcise() {
        return totalExcise;
    }

    public void setTotalExcise(Double totalExcise) {
        this.totalExcise = totalExcise;
    }

    public Double getTotalWsp() {
        return totalWsp;
    }

    public void setTotalWsp(Double totalWsp) {
        this.totalWsp = totalWsp;
    }

    public Integer getTotalCustom() {
        return totalCustom;
    }

    public void setTotalCustom(Integer totalCustom) {
        this.totalCustom = totalCustom;
    }

    public Integer getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(Integer sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }
}
