package com.aptech.common;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @SerializedName(value = "error_code")
    private String errorCode;
    @SerializedName(value = "error_message")
    private String message;
    @SerializedName(value = "message_detail")
    private String messageDetail;
    @SerializedName(value = "request_id")
    private String requestId;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
