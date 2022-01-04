package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleDetailReportModel {
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
    private String mrp;
    @SerializedName("EXCISE_PRICE")
    @Expose
    private String excisePrice;
    @SerializedName("WSP")
    @Expose
    private String wsp;

    public SaleDetailReportModel(String brandName, Integer sizeValue, Integer quantity, String mrp, String excisePrice, String wsp, Double total) {
        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.quantity = quantity;
        this.total = total;
        this.mrp = mrp;
        this.excisePrice = excisePrice;
        this.wsp = wsp;
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

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getExcisePrice() {
        return excisePrice;
    }

    public void setExcisePrice(String excisePrice) {
        this.excisePrice = excisePrice;
    }

    public String getWsp() {
        return wsp;
    }

    public void setWsp(String wsp) {
        this.wsp = wsp;
    }
}
