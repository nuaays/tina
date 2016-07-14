package com.zhongan.scorpoin.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.signature.SignatureUtils;
import com.zhongan.scorpoin.util.StringUtils;
import com.zhongan.scorpoin.util.ZhongAnOpenHttpUtils;

/**
 * @author linwusheng 2016年1月19日 下午4:11:56
 */
public class ZhongAnApiClient {

    private int    connectTimeout = 3000;   //3秒

    private int    readTimeout    = 80000;  //80秒

    private String env;

    private String appKey;

    private String privateKey;

    private String url;

    private String zaPublicKey;

    private String charset        = "UTF-8";

    private String signType       = "RSA";

    private String version        = "1.0.0";

    private String format         = "json";

    private String timestamp;

    public ZhongAnApiClient(String env, String appKey, String privateKey) {
        this.env = env;
        this.appKey = appKey;
        this.privateKey = privateKey;
    }

    public ZhongAnApiClient(String env, String appKey, String privateKey, String version) {
        this.env = env;
        this.appKey = appKey;
        this.privateKey = privateKey;
        this.version = version;
    }

    public ZhongAnApiClient(String env, String appKey, String privateKey, String charset, String signType,
                            String version, String format, int connectTimeout, int readTimeout) {
        this.env = env;
        this.appKey = appKey;
        this.privateKey = privateKey;
        this.charset = charset;
        this.signType = signType;
        this.version = version;
        this.format = format;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    private void _init() throws ZhongAnOpenException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        this.timestamp = sdf.format(new Date());
        sdf = null;

        ZhongAnEnvEnum envEnum = ZhongAnEnvEnum.get(this.env);
        if (null == envEnum) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_ILLEGAL.getCode(),
                    "env必须是dev,iTest,uat,prd中的一个");
        }

        this.url = envEnum.getUrl();
        this.zaPublicKey = envEnum.getPublicKey();
    }

    public DefaultResponse call(DefaultRequest req) throws ZhongAnOpenException {

        _init();

        // 初始化serviceName和response
        req.init();

        // 校验系统参数
        req.checkSysParams();

        // 校验业务参数 
        req.checkBizParams();

        JSONObject encryptSignJSON = req.buildEncryptSignParamMap();
        encryptSignJSON.put(ProtocolParameterEnum.APP_KEY.getCode(), this.appKey);
        encryptSignJSON.put(ProtocolParameterEnum.TIME_STAMP.getCode(), this.timestamp);
        encryptSignJSON.put(ProtocolParameterEnum.FORMAT.getCode(), this.format);
        encryptSignJSON.put(ProtocolParameterEnum.SIGN_TYPE.getCode(), this.signType);
        encryptSignJSON.put(ProtocolParameterEnum.CHARSET.getCode(), this.charset);
        encryptSignJSON.put(ProtocolParameterEnum.VERSION.getCode(), this.version);

        Map<String, Object> rt = doPost(encryptSignJSON);
        if (null == rt) {
            return null;
        }

        DefaultResponse response = null;
        if (null != rt.get("bizContent")) {
            if (CommonResponse.class.getName().equals(req.getResponse().getClass().getName())) {
                response = new CommonResponse();
                response.setBizContent((String) rt.get("bizContent"));
            } else {
                response = JSONObject.parseObject((String) rt.get("bizContent"), req.getResponse().getClass());
            }
        } else {
            response = req.getResponse();
        }
        if (null != rt.get("errorCode")) {
            response.setErrorCode((String) rt.get("errorCode"));
        }
        if (null != rt.get("errorMsg")) {
            response.setErrorMsg((String) rt.get("errorMsg"));
        }

        response.setCharset((String) rt.get("charset"));
        response.setFormat((String) rt.get("format"));
        response.setSignType((String) rt.get("signType"));
        response.setTimestamp((String) rt.get("timestamp"));

        return response;
    }

    protected Map<String, Object> doPost(JSONObject paramJSON) throws ZhongAnOpenException {

        Map<String, Object> encryptMap = new HashMap<String, Object>();

        // 签名，加密
        try {
            encryptMap = SignatureUtils.encryptAndSign(paramJSON, this.zaPublicKey, this.privateKey, "utf-8", true,
                    true);
        } catch (Exception e) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ENCRYPT_SIGN_FAILED.getCode(), e.getMessage(), e);
        }

        Map<String, String> params = new HashMap<String, String>();
        for (Entry<String, Object> en : encryptMap.entrySet()) {
            params.put(en.getKey(), (String) en.getValue());
        }

        String gatewayResponse = null;
        // 调用网关服务
        try {
            gatewayResponse = ZhongAnOpenHttpUtils.doPost(this.url, params, connectTimeout, readTimeout);
        } catch (IOException e) {
            throw new ZhongAnOpenException(e);
        }

        JSONObject responseJson = JSONObject.parseObject(gatewayResponse);
        // 如果bizContent为空 就不用解密了 
        boolean isDecrypt = StringUtils.isEmpty(responseJson.getString("bizContent")) ? false : true;
        try {
            String responseBizContent = SignatureUtils.checkSignAndDecrypt(responseJson, this.zaPublicKey,
                    this.privateKey, true, isDecrypt);

            responseJson.put("bizContent", responseBizContent);

        } catch (Exception e) {
            throw new ZhongAnOpenException(e);
        }

        return responseJson;
    }
}
