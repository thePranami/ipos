package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BreakageModel {

    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;
    @SerializedName("SIZE_VALUE")
    @Expose
    private Integer sizeValue;
    @SerializedName("BRAKAGE")
    @Expose
    private Integer brakage;
    @SerializedName("Total")
    @Expose
    private Double total;
    @SerializedName("MRP")
    @Expose
    private Double mrp;
    @SerializedName("EXCISE_PRICE")
    @Expose
    private Double excisePrice;
    @SerializedName("WSP")
    @Expose
    private Double wsp;

    public BreakageModel(String brandName, Integer sizeValue, Integer brakage,
                         Double total, Double mrp, Double excisePrice,
                         Double wsp) {
        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.brakage = brakage;
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

    public Integer getBrakage() {
        return brakage;
    }

    public void setBrakage(Integer brakage) {
        this.brakage = brakage;
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

    public class BreakageResponse{
        @SerializedName("data")
        @Expose
        private List<BreakageModel> data = null;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("error")
        @Expose
        private String error;
        @SerializedName("total_amount")
        @Expose
        private Double totalAmount;
        @SerializedName("total_quantity")
        @Expose
        private Integer totalQuantity;
        @SerializedName("total_excise")
        @Expose
        private Double totalExcise;
        @SerializedName("total_wsp")
        @Expose
        private Double totalWsp;

        public List<BreakageModel> getData() {
            return data;
        }

        public void setData(List<BreakageModel> data) {
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

        public Double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Integer getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(Integer totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public Double getTotalExcise() {
            return totalExcise;
        }

        public void setTotalExcise(Double totalExcise) {
            this.totalExcise = totalExcise;
        }

        public Double getTotalWsp() {
            return totalWsp;
        }

        public void setTotalWsp(Double totalWsp) {
            this.totalWsp = totalWsp;
        }
    }
}


