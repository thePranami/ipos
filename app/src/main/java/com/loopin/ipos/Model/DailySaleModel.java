package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DailySaleModel {

    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;
    @SerializedName("SIZE_VALUE")
    @Expose
    private Integer sizeValue;
    @SerializedName("QUANTITY")
    @Expose
    private Integer quantity;
    @SerializedName("Total")
    @Expose
    private Double total;
    @SerializedName("MRP")
    @Expose
    private Double mrp;

    public DailySaleModel(String brandName, Integer sizeValue, Integer quantity, Double total, Double mrp) {
        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.quantity = quantity;
        this.total = total;
        this.mrp = mrp;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

}
