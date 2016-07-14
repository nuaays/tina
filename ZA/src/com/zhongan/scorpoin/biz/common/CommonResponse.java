package com.zhongan.scorpoin.biz.common;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zhongan.scorpoin.common.DefaultResponse;

/**
 * @author linwusheng 2016年2月2日 下午2:08:29
 */
public class CommonResponse extends DefaultResponse {

    private static final long serialVersionUID = -2610781864353267960L;

    private String            bizContent;

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
}
