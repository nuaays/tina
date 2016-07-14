package com.zhongan.scorpoin.biz.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.common.DefaultRequest;
import com.zhongan.scorpoin.common.ZhongAnOpenException;

/**
 * 针对一些没有参数没有DTO的请求
 * 
 * @author linwusheng 2016年2月2日 上午11:44:32
 */
public class CommonRequest extends DefaultRequest {

    private static final long serialVersionUID = -8198093576244990220L;

    private JSONObject        params;

    public CommonRequest(String serviceName) throws ZhongAnOpenException {
        if (StringUtils.isBlank(serviceName)) {
            throw new ZhongAnOpenException("serviceName不能为空");
        }
        this.setServiceName(serviceName);
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public void checkBizParams() throws ZhongAnOpenException {
        // TODO Auto-generated method stub

    }

    @Override
    public void init() {
        this.setResponse(new CommonResponse());
    }

    /**
     * 拼装待加密加签的参数
     * 
     * @return
     */
    @Override
    public JSONObject buildEncryptSignParamMap() {
        params.put("serviceName", this.getServiceName());
        return params;
    }

}
