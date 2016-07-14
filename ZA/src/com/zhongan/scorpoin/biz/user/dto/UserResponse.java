package com.zhongan.scorpoin.biz.user.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zhongan.scorpoin.common.DefaultResponse;

public class UserResponse extends DefaultResponse {

    private static final long serialVersionUID = 3676624934702977722L;

    private String            bizContent;

    private String            errorCode;

    private String            errorMsg;

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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

}
