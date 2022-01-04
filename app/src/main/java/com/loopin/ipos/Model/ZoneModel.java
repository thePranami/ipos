package com.loopin.ipos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZoneModel {
    @SerializedName("zone_id")
    @Expose
    private Integer zoneId;
    @SerializedName("name")
    @Expose
    private String name;

    public ZoneModel(Integer zoneId, String name) {
        this.zoneId = zoneId;
        this.name = name;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class ZoneResponse{
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("error")
        @Expose
        private String error;
        @SerializedName("data")
        @Expose
        private ZoneModel data;

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

        public ZoneModel getData() {
            return data;
        }

        public void setData(ZoneModel data) {
            this.data = data;
        }
    }
}
