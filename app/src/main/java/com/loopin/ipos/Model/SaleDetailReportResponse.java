package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaleDetailReportResponse {
    @SerializedName("data")
    @Expose
    private List<SaleDetailReportModel> data = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;
    @SerializedName("total_quantity")
    @Expose
    private Integer totalQuantity;
    @SerializedName("total_excise")
    @Expose
    private Double totalExcise;
    @SerializedName("total_wsp")
    @Expose
    private Double totalWsp;

    public List<SaleDetailReportModel> getData() {
        return data;
    }

    public void setData(List<SaleDetailReportModel> data) {
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
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
}
