package com.loopin.ipos.Model;

public class CostCardModel {

    private String brandName;
    private Integer sizeValue, total;
    private Double excisePrice, wsp, customDuty, costPrice;

    public CostCardModel(String brandName, Integer sizeValue, Integer total, Double excisePrice,
                         Double wsp, Double customDuty, Double costPrice) {
        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.total = total;
        this.excisePrice = excisePrice;
        this.wsp = wsp;
        this.customDuty = customDuty;
        this.costPrice = costPrice;
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

    public Double getExcisePrice() {
        return excisePrice;
    }

    public void setExcisePrice(Double excisePrice) {
        this.excisePrice = excisePrice;
    }

    public Double getWsp() {
        return wsp;
    }

    public void setWsp(Double wsp) {
        this.wsp = wsp;
    }

    public Double getCustomDuty() {
        return customDuty;
    }

    public void setCustomDuty(Double customDuty) {
        this.customDuty = customDuty;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

}
