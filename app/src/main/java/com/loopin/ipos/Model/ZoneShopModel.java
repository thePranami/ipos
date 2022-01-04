package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZoneShopModel {
    @SerializedName("SHOP_NAME")
    @Expose
    private String shopName;
    @SerializedName("SHOP_KEY")
    @Expose
    private Integer shopKey;

    public ZoneShopModel(String shopName, Integer shopKey){
        this.shopName = shopName;
        this.shopKey = shopKey;
    }
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getShopKey() {
        return shopKey;
    }

    public void setShopKey(Integer shopKey) {
        this.shopKey = shopKey;
    }


   public class ZoneShopResponse{
        @SerializedName("data")
        @Expose
        private List<ZoneShopModel> data = null;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("error")
        @Expose
        private String error;

        public List<ZoneShopModel> getData() {
            return data;
        }

        public void setData(List<ZoneShopModel> data) {
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

