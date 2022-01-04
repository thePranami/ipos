package com.loopin.ipos.Model;

public class StockQPNModel {
    private String brandName, OQ, OP, ON, RQ, RP, RN, SQ, SP, SN, CQ, CP, CN, SVQ, SVP, SVN;

    public StockQPNModel(String brandName, String OQ, String OP, String ON, String RQ, String RP,
                         String RN, String SQ, String SP, String SN, String CQ,
                         String CP, String CN, String SVQ, String SVP, String SVN) {
        this.brandName = brandName;
        this.OQ = OQ;
        this.OP = OP;
        this.ON = ON;
        this.RQ = RQ;
        this.RP = RP;
        this.RN = RN;
        this.SQ = SQ;
        this.SP = SP;
        this.SN = SN;
        this.CQ = CQ;
        this.CP = CP;
        this.CN = CN;
        this.SVQ = SVQ;
        this.SVP = SVP;
        this.SVN = SVN;
    }

    public  String getBrandName(){
        return brandName;
    }

    public String getOQ() {
        return OQ;
    }

    public String getOP() {
        return OP;
    }

    public String getON() {
        return ON;
    }

    public String getRQ() {
        return RQ;
    }

    public String getRP() {
        return RP;
    }

    public String getRN() {
        return RN;
    }

    public String getSQ() {
        return SQ;
    }

    public String getSP() {
        return SP;
    }

    public String getSN() {
        return SN;
    }

    public String getCQ() {
        return CQ;
    }

    public String getCP() {
        return CP;
    }

    public String getCN() {
        return CN;
    }

    public String getSVQ() {
        return SVQ;
    }

    public String getSVP() {
        return SVP;
    }

    public String getSVN() {
        return SVN;
    }
}
