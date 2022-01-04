package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TPReportModel {
    @SerializedName("RECEIVE_DATE")
    @Expose
    private String receiveDate;
    @SerializedName("INVOICE_NO")
    @Expose
    private String invoiceNo;
    @SerializedName("CONSIGNMENT_NO")
    @Expose
    private String consignmentNo;
    @SerializedName("SUPP_NAME")
    @Expose
    private String supName;
    @SerializedName("TOTAL_RECEVEIED")
    @Expose
    private Integer totalReceived;
    @SerializedName("WSP")
    @Expose
    private String wsp;
    @SerializedName("Excise")
    @Expose
    private String excise;
    @SerializedName("custom")
    @Expose
    private String custom;
    @SerializedName("VAT")
    @Expose
    private String vat;
    @SerializedName("TCS")
    @Expose
    private String tcs;
    @SerializedName("Total")
    @Expose
    private String total;

    public TPReportModel(String receiveDate, String invoiceNo, String consignmentNo, String supName,
                         Integer totalReceived, String wsp, String excise,
                         String custom, String vat, String tcs, String total) {
        this.receiveDate = receiveDate;
        this.invoiceNo = invoiceNo;
        this.consignmentNo = consignmentNo;
        this.supName = supName;
        this.totalReceived = totalReceived;
        this.wsp = wsp;
        this.excise = excise;
        this.custom = custom;
        this.vat = vat;
        this.tcs = tcs;
        this.total = total;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public String getSupName() {
        return supName;
    }

    public Integer getTotalReceived() {
        return totalReceived;
    }

    public String getWsp() {
        return wsp;
    }

    public String getExcise() {
        return excise;
    }

    public String getCustom() {
        return custom;
    }

    public String getVat() {
        return vat;
    }

    public String getTcs() {
        return tcs;
    }

    public String getTotal() {
        return total;
    }
}
