package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandWiseRespone {
    @SerializedName("data")
    @Expose
    private List<BrandWiseModel> data = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("sum_receveing")
    @Expose
    private Integer sumReceveing;
    @SerializedName("sum_excise")
    @Expose
    private Double sumExcise;
    @SerializedName("sum_wsp")
    @Expose
    private Double sumWsp;

    public List<BrandWiseModel> getData() {
        return data;
    }

    public void setData(List<BrandWiseModel> data) {
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

    public Integer getSumReceveing() {
        return sumReceveing;
    }

    public void setSumReceveing(Integer sumReceveing) {
        this.sumReceveing = sumReceveing;
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
}
