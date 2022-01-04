package com.example.anurag_3rdjan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiModel {
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("authCode")
    @Expose
    private String authCode;
    @SerializedName("avsResultCode")
    @Expose
    private String avsResultCode;
    @SerializedName("cvvResultCode")
    @Expose
    private String cvvResultCode;
    @SerializedName("cavvResultCode")
    @Expose
    private String cavvResultCode;
    @SerializedName("transId")
    @Expose
    private String transId;
    @SerializedName("refTransID")
    @Expose
    private String refTransID;
    @SerializedName("transHash")
    @Expose
    private String transHash;
    @SerializedName("testRequest")
    @Expose
    private String testRequest;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("accountType")
    @Expose
    private String accountType;
    @SerializedName("errors")
    @Expose
    private Response.Error.Errors errors;
    @SerializedName("userFields")
    @Expose
    private Response.UserField.UserFields userFields;
    @SerializedName("transHashSha2")
    @Expose
    private String transHashSha2;
    @SerializedName("networkTransId")
    @Expose
    private String networkTransId;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAvsResultCode() {
        return avsResultCode;
    }

    public void setAvsResultCode(String avsResultCode) {
        this.avsResultCode = avsResultCode;
    }

    public String getCvvResultCode() {
        return cvvResultCode;
    }

    public void setCvvResultCode(String cvvResultCode) {
        this.cvvResultCode = cvvResultCode;
    }

    public String getCavvResultCode() {
        return cavvResultCode;
    }

    public void setCavvResultCode(String cavvResultCode) {
        this.cavvResultCode = cavvResultCode;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getRefTransID() {
        return refTransID;
    }

    public void setRefTransID(String refTransID) {
        this.refTransID = refTransID;
    }

    public String getTransHash() {
        return transHash;
    }

    public void setTransHash(String transHash) {
        this.transHash = transHash;
    }

    public String getTestRequest() {
        return testRequest;
    }

    public void setTestRequest(String testRequest) {
        this.testRequest = testRequest;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Response.Error.Errors getErrors() {
        return errors;
    }

    public void setErrors(Response.Error.Errors errors) {
        this.errors = errors;
    }

    public Response.UserField.UserFields getUserFields() {
        return userFields;
    }

    public void setUserFields(Response.UserField.UserFields userFields) {
        this.userFields = userFields;
    }

    public String getTransHashSha2() {
        return transHashSha2;
    }

    public void setTransHashSha2(String transHashSha2) {
        this.transHashSha2 = transHashSha2;
    }

    public String getNetworkTransId() {
        return networkTransId;
    }

    public void setNetworkTransId(String networkTransId) {
        this.networkTransId = networkTransId;
    }

    public class Response{
        @SerializedName("data")
        @Expose
        private ApiModel data;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("code")
        @Expose
        private Integer code;

        public ApiModel getData() {
            return data;
        }

        public void setData(ApiModel data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public class UserField {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("value")
            @Expose
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public class UserFields {

                @SerializedName("userField")
                @Expose
                private List<UserField> userField = null;

                public List<UserField> getUserField() {
                    return userField;
                }

                public void setUserField(List<UserField> userField) {
                    this.userField = userField;
                }
            }
        }
        public class Error {

            @SerializedName("errorCode")
            @Expose
            private String errorCode;
            @SerializedName("errorText")
            @Expose
            private String errorText;

            public String getErrorCode() {
                return errorCode;
            }

            public void setErrorCode(String errorCode) {
                this.errorCode = errorCode;
            }

            public String getErrorText() {
                return errorText;
            }

            public void setErrorText(String errorText) {
                this.errorText = errorText;
            }
            public class Errors {

                @SerializedName("error")
                @Expose
                private List<Error> error = null;

                public List<Error> getError() {
                    return error;
                }

                public void setError(List<Error> error) {
                    this.error = error;
                }
            }
        }
    }

}
