package com.company.myweb.entity.common;


import com.company.myweb.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "message", "data"})
public class ApiResponse<T> {
    @JsonProperty(value = "status")
    private String statusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonProperty("error_message")
    private String errorMessage;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> ret = new ApiResponse<>();
        ret.statusCode = Constants.OK;
        ret.data = data;
        return ret;
    }

    public static <T> ApiResponse<T> success() {
        ApiResponse<T> ret = new ApiResponse<>();
        ret.statusCode = Constants.OK;
        return ret;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> ret = new ApiResponse<>();
        ret.message = message;
        ret.statusCode = Constants.OK;
        ret.data = data;
        return ret;
    }

    public static <T> ApiResponse<T> failed(String errorMessage) {
        ApiResponse<T> ret = new ApiResponse<>();
        ret.statusCode = Constants.FAILED;
        ret.errorMessage = errorMessage;
        return ret;
    }

    public static <T> ApiResponse<T> failed(Integer errorCode, String errorMessage) {
        ApiResponse<T> ret = new ApiResponse<>();
        ret.statusCode = errorCode.toString();
        ret.errorMessage = errorMessage;
        return ret;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
