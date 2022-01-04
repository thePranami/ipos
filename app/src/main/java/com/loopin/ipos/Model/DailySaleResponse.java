package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailySaleResponse {
    @SerializedName("data")
    @Expose
    private List<DailySaleModel> data = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("total_quantity")
    @Expose
    private Integer totalQuantity;
    @SerializedName("error")
    @Expose
    private String error;

    public List<DailySaleModel> getData() {
        return data;
    }

    public void setData(List<DailySaleModel> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
