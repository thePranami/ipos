package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VatReportResponse {
    @SerializedName("data")
    @Expose
    private List<VatReportModel> data = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("sum_gross_sale")
    @Expose
    private Integer sumGrossSale;
    @SerializedName("sum_vat")
    @Expose
    private Integer sumVat;
    @SerializedName("sum_total")
    @Expose
    private Integer sumTotal;

    public List<VatReportModel> getData() {
        return data;
    }

    public void setData(List<VatReportModel> data) {
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

    public Integer getSumGrossSale() {
        return sumGrossSale;
    }

    public void setSumGrossSale(Integer sumGrossSale) {
        this.sumGrossSale = sumGrossSale;
    }

    public Integer getSumVat() {
        return sumVat;
    }

    public void setSumVat(Integer sumVat) {
        this.sumVat = sumVat;
    }

    public Integer getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(Integer sumTotal) {
        this.sumTotal = sumTotal;
    }
}
