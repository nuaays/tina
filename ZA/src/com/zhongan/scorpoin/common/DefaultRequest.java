package com.zhongan.scorpoin.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.signature.StringUtils;

public abstract class DefaultRequest implements Serializable {

    private static final long serialVersionUID = 5831082408862499143L;

    private String            serviceName;

    private DefaultResponse   response;

    /**
     * 拼装待加密加签的参数
     * 
     * @return
     */
    public JSONObject buildEncryptSignParamMap() {
        JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(this));
        result.remove("response");
        return result;
    }

    /**
     * 校验业务参数：必填参数、时间字符串格式、简单的自核规则。把一些简单的错误挡在client上，最好能提供详细的错误描述。
     * 
     * @return
     */
    public abstract void checkBizParams() throws ZhongAnOpenException;

    /**
     * 各个接口指明具体的serviceName
     */
    public abstract void init();

    /**
     * 校验系统参数
     * 
     * @throws ZhongAnOpenException
     */
    public void checkSysParams() throws ZhongAnOpenException {
        if (!StringUtils.areNotEmpty(this.getServiceName())) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_MISSING.getCode(), "缺少必填的系统参数： serviceName");
        }

    }

    public String getServiceName() {
        return serviceName;
    }

    protected void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public DefaultResponse getResponse() {
        return response;
    }

    protected void setResponse(DefaultResponse response) {
        this.response = response;
    }

}
