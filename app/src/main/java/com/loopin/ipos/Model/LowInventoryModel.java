package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LowInventoryModel {

    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;
    @SerializedName("SIZE_VALUE")
    @Expose
    private Integer sizeValue;
    @SerializedName("QUANTITY")
    @Expose
    private Integer quantity;
    @SerializedName("CASE")
    @Expose
    private Integer caseUnit;

    public LowInventoryModel(String brandName, Integer sizeValue,
                             Integer quantity, Integer caseUnit) {
        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.quantity = quantity;
        this.caseUnit = caseUnit;
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

    public Integer getCase() {
        return caseUnit;
    }

    public void setCase(Integer caseUnit) {
        this.caseUnit = caseUnit;
    }

    public class Response{
        @SerializedName("data")
        @Expose
        private List<LowInventoryModel> data = null;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("error")
        @Expose
        private String error;

        public List<LowInventoryModel> getData() {
            return data;
        }

        public void setData(List<LowInventoryModel> data) {
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

    }
}
