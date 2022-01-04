package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PromoSaleModel {

    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("SHOP_CODE")
    @Expose
    private String shopCode;
    @SerializedName("SHOP_NAME")
    @Expose
    private String shopName;
    @SerializedName("total_sale")
    @Expose
    private String totalSale;
    @SerializedName("Last_day_sale")
    @Expose
    private String lastDaySale;


    public PromoSaleModel(String shopName, String totalSale, String lastDaySale) {
        this.shopName = shopName;
        this.totalSale = totalSale;
        this.lastDaySale = lastDaySale;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(String totalSale) {
        this.totalSale = totalSale;
    }

    public String getLastDaySale() {
        return lastDaySale;
    }

    public void setLastDaySale(String lastDaySale) {
        this.lastDaySale = lastDaySale;
    }

    public class PromoResponse{
        @SerializedName("data")
        @Expose
        private List<PromoSaleModel> data = null;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("error")
        @Expose
        private String error;

        public List<PromoSaleModel> getData() {
            return data;
        }

        public void setData(List<PromoSaleModel> data) {
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
