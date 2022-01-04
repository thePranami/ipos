package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandWiseModel {
    @SerializedName("SUPP_NAME")
    @Expose
    private String suppName;
    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;
    @SerializedName("SIZE_VALUE")
    @Expose
    private Integer sizeValue;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("EXCISE_PRICE")
    @Expose
    private String excisePrice;
    @SerializedName("WSP")
    @Expose
    private String wsp;

    public BrandWiseModel(String suppName, String brandName, Integer sizeValue,
                          Integer total, String excisePrice, String wsp) {
        this.suppName = suppName;
        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.total = total;
        this.excisePrice = excisePrice;
        this.wsp = wsp;
    }

    public String getSuppName() {
        return suppName;
    }

    public void setSuppName(String suppName) {
        this.suppName = suppName;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
