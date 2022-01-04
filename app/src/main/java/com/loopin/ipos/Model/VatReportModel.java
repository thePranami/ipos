package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VatReportModel {
    @SerializedName("INV_DATE")
    @Expose
    private String invDate;
    @SerializedName("LIQUOR_TYPE")
    @Expose
    private String liquorType;
    @SerializedName("GROSS_SALE")
    @Expose
    private String grossSale;
    @SerializedName("VAT")
    @Expose
    private String vat;
    @SerializedName("TOT_SALE")
    @Expose
    private String totSale;

    public VatReportModel(String invDate, String liquorType, String grossSale, String vat, String totSale) {
        this.invDate = invDate;
        this.liquorType = liquorType;
        this.grossSale = grossSale;
        this.vat = vat;
        this.totSale = totSale;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getLiquorType() {
        return liquorType;
    }

    public void setLiquorType(String liquorType) {
        this.liquorType = liquorType;
    }

    public String getGrossSale() {
        return grossSale;
    }

    public void setGrossSale(String grossSale) {
        this.grossSale = grossSale;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getTotSale() {
        return totSale;
    }

    public void setTotSale(String totSale) {
        this.totSale = totSale;
    }
}
