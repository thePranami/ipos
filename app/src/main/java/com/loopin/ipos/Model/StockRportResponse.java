package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockRportResponse {
    @SerializedName("data")
    @Expose
    private List<StockReportModel> data = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("sum_breakage_stock")
    @Expose
    private Integer sumBreakageStock;
    @SerializedName("sum_total_stock")
    @Expose
    private Integer sumTotalStock;
    @SerializedName("sum_total_excise")
    @Expose
    private Double sumTotalExcise;
    @SerializedName("sum_total_wsp")
    @Expose
    private Double sumTotalWsp;
    @SerializedName("sum_total_amount")
    @Expose
    private Double sumTotalAmount;
    @SerializedName("sum_total_custom")
    @Expose
    private Double sumTotalCustom;

    public List<StockReportModel> getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public String getError() {
        return error;
    }

    public Integer getSumBreakageStock() {
        return sumBreakageStock;
    }

    public Integer getSumTotalStock() {
        return sumTotalStock;
    }

    public Double getSumTotalExcise() {
        return sumTotalExcise;
    }

    public Double getSumTotalWsp() {
        return sumTotalWsp;
    }

    public Double getSumTotalAmount() {
        return sumTotalAmount;
    }

    public Double getSumTotalCustom() {
        return sumTotalCustom;
    }
}
