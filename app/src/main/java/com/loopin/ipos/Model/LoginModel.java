package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("vend_id")
    @Expose
    private String vendId;
    @SerializedName("User_type")
    @Expose
    private String userType;
    @SerializedName("SHOP_NAME")
    @Expose
    private String shopName;
    @SerializedName("SHOP_ADDRESS")
    @Expose
    private String shopAddress;
    @SerializedName("PIN_CODE")
    @Expose
    private String pinCode;
    @SerializedName("SHOP_KEY")
    @Expose
    private Integer shopKey;
    @SerializedName("PHONE")
    @Expose
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVendId() {
        return vendId;
    }

    public void setVendId(String vendId) {
        this.vendId = vendId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Integer getShopKey() {
        return shopKey;
    }

    public void setShopKey(Integer shopKey) {
        this.shopKey = shopKey;
    }
}
