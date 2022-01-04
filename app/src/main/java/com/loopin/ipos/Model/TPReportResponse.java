package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TPReportResponse {
    @SerializedName("data")
    @Expose
    private List<TPReportModel> data = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("sum_receveing")
    @Expose
    private Integer sumReceiving;
    @SerializedName("sum_vat")
    @Expose
    private Double sumVat;
    @SerializedName("sum_excise")
    @Expose
    private Double sumExcise;
    @SerializedName("sum_wsp")
    @Expose
    private Double sumWsp;
    @SerializedName("sum_custom")
    @Expose
    private Double sumCustom;
    @SerializedName("sum_tcs")
    @Expose
    private Double sumTcs;
    @SerializedName("sum_total_amount")
    @Expose
    private Double sumTotalAmount;

    public List<TPReportModel> getData() {
        return data;
    }

    public void setData(List<TPReportModel> data) {
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

    public Integer getSumReceiving() {
        return sumReceiving;
    }

    public void setSumReceiving(Integer sumReceiving) {
        this.sumReceiving = sumReceiving;
    }

    public Double getSumVat() {
        return sumVat;
    }

    public void setSumVat(Double sumVat) {
        this.sumVat = sumVat;
    }

    public Double getSumExcise() {
        return sumExcise;
    }

    public void setSumExcise(Double sumExcise) {
        this.sumExcise = sumExcise;
    }

    public Double getSumWsp() {
        return sumWsp;
    }

    public void setSumWsp(Double sumWsp) {
        this.sumWsp = sumWsp;
    }

    public Double getSumCustom() {
        return sumCustom;
    }

    public void setSumCustom(Double sumCustom) {
        this.sumCustom = sumCustom;
    }

    public Double getSumTcs() {
        return sumTcs;
    }

    public void setSumTcs(Double sumTcs) {
        this.sumTcs = sumTcs;
    }

    public Double getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(Double sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }
}
