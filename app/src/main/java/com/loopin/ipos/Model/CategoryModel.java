package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel {
    @SerializedName("LOOKUP_VALUE")
    @Expose
    private String lookupValue;

    public CategoryModel(String lookupValue) {
        this.lookupValue = lookupValue;
    }

    public String getLookupValue() {
        return lookupValue;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }
}
