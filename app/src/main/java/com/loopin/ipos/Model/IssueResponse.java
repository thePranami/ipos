package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IssueResponse {

    @SerializedName("data")
    @Expose
    private List<IssueModel> data = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("total_issue")
    @Expose
    private Integer totalIssue;
    @SerializedName("total_case")
    @Expose
    private Integer totalCase;

    public List<IssueModel> getData() {
        return data;
    }

    public void setData(List<IssueModel> data) {
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

    public Integer getTotalIssue() {
        return totalIssue;
    }

    public void setTotalIssue(Integer totalIssue) {
        this.totalIssue = totalIssue;
    }

    public Integer getTotalCase() {
        return totalCase;
    }

    public void setTotalCase(Integer totalCase) {
        this.totalCase = totalCase;
    }

}
