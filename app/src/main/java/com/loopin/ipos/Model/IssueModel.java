package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueModel {
    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;
    @SerializedName("SIZE_VALUE")
    @Expose
    private Integer sizeValue;
    @SerializedName("PACK_SIZE")
    @Expose
    private Integer packSize;
    @SerializedName("CASE")
    @Expose
    private Integer issueCase;
    @SerializedName("ISSUE_STCOK")
    @Expose
    private Integer issueStcok;

    public IssueModel(String brandName, Integer sizeValue, Integer packSize,
                      Integer issueCase, Integer issueStcok) {
        this.brandName = brandName;
        this.sizeValue = sizeValue;
        this.packSize = packSize;
        this.issueCase = issueCase;
        this.issueStcok = issueStcok;
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

    public Integer getPackSize() {
        return packSize;
    }

    public void setPackSize(Integer packSize) {
        this.packSize = packSize;
    }

    public Integer getCase() {
        return issueCase;
    }

    public void setCase(Integer issueCase) {
        this.issueCase = issueCase;
    }

    public Integer getIssueStcok() {
        return issueStcok;
    }

    public void setIssueStcok(Integer issueStcok) {
        this.issueStcok = issueStcok;
    }
}
