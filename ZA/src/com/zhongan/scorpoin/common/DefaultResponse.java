package com.zhongan.scorpoin.common;

import java.io.Serializable;

public class DefaultResponse implements Serializable {

    private static final long serialVersionUID = 7145611277913267803L;

    private String            bizContent;

    private String            charset;

    private String            format;

    private String            signType;

    private String            timestamp;

    private String            errorCode;

    private String            errorMsg;

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DefaultResponse [bizContent=");
        builder.append(bizContent);
        builder.append(", charset=");
        builder.append(charset);
        builder.append(", format=");
        builder.append(format);
        builder.append(", signType=");
        builder.append(signType);
        builder.append(", timestamp=");
        builder.append(timestamp);
        builder.append(", errorCode=");
        builder.append(errorCode);
        builder.append(", errorMsg=");
        builder.append(errorMsg);
        builder.append("]");
        return builder.toString();
    }

}
