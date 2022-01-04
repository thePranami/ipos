package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BreakageSaleModel {
    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;
    @SerializedName("SIZE_VALUE")
    @Expose
    private Integer sizeValue;
    @SerializedName("BRAKAGE_SALE")
    @Expose
    private Integer brakageSale;
    @SerializedName("Total")
    @Expose
    private Integer total;
    @SerializedName("MRP")
    @Expose
    private String mrp;
    @SerializedName("EXCISE_PRICE")
    @Expose
    private String excisePrice;
    @SerializedName("WSP")
    @Expose
    private String wsp;

    public BreakageSaleModel(String brandName, Integer sizeValue,
                             Integer brakageSale, Integer total, String mrp,
                             String excisePrice, String wsp) {
        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.brakageSale = brakageSale;
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

    public Integer getBrakageSale() {
        return brakageSale;
    }

    public void setBrakageSale(Integer brakageSale) {
        this.brakageSale = brakageSale;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
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
