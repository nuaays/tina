package com.zhongan.scorpoin.biz.commplan.dto;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.common.DefaultRequest;
import com.zhongan.scorpoin.common.ZhongAnOpenException;

public class CommPlanRequest extends DefaultRequest {

    private static final long serialVersionUID = -3644653829018012714L;

    private JSONObject        params;

    public CommPlanRequest(String serviceName) throws ZhongAnOpenException {
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

    @Override
    public void checkBizParams() throws ZhongAnOpenException {
        // TODO Auto-generated method stub

    }

    @Override
    public void init() {
        this.setResponse(new CommonResponse());

    }

}
