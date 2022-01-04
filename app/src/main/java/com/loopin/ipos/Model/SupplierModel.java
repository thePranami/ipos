package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierModel {
    @SerializedName("SUPP_NAME")
    @Expose
    private String suppName;

    public String getSuppName() {
        return suppName;
    }

    public void setSuppName(String suppName) {
        this.suppName = suppName;
    }
}
