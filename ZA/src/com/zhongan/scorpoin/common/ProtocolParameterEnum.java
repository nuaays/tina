package com.zhongan.scorpoin.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定义系统级别参数
 * 
 * @author linwusheng 2015年11月9日 下午6:48:17
 */
public enum ProtocolParameterEnum {

    APP_KEY("appKey", "Y", "", "应用接入唯一key"),
    BIZ_CONTENT("bizContent", "Y", "", "业务级参数，需加密"),
    TIME_STAMP("timestamp", "Y", "", "时间搓"),
    SERVICE_NAME("serviceName", "Y", "", "服务名"),
    FORMAT("format", "N", "json", "响应格式"),
    SIGN_TYPE("signType", "Y", "RSA", "签名类型"),
    CHARSET("charset", "N", "UTF-8", "字符集"),
    VERSION("version", "N", "1.0.0", "版本"),
    SIGN("sign", "Y", "", "签名");

    private String code;

    private String isMandotary;

    private String defaultValue;

    private String description;

    private ProtocolParameterEnum(String code, String isMandotary, String defaultValue, String description) {
        this.code = code;
        this.isMandotary = isMandotary;
        this.defaultValue = defaultValue;
        this.description = description;
    }

    /**
     * @param code
     * @return
     */
    public static boolean containCode(String code) {
        for (ProtocolParameterEnum an : ProtocolParameterEnum.values()) {
            if (null != code && code.equals(an.getCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param code
     * @return
     */
    public static ProtocolParameterEnum getSystemParameterEnum(String code) {
        for (ProtocolParameterEnum an : ProtocolParameterEnum.values()) {
            if (null != code && code.equals(an.getCode())) {
                return an;
            }
        }
        return null;
    }

    /**
     * 把枚举转换成list返回
     * 
     * @return List<Map<String, String>>
     */
    public static List<Map<String, String>> getListForMap() {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (ProtocolParameterEnum an : ProtocolParameterEnum.values()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("code", an.getCode());
            map.put("isMandotary", an.getIsMandotary());
            map.put("defaultValue", an.getDefaultValue());
            map.put("description", an.getDescription());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 把枚举的code转成list返回
     * 
     * @return List<String>
     */
    public static List<String> getCodeList() {
        List<String> codeList = new ArrayList<String>();
        for (ProtocolParameterEnum ppe : ProtocolParameterEnum.values()) {
            codeList.add(ppe.getCode());
        }
        return codeList;
    }

    public String getCode() {
        return code;
    }

    public String getIsMandotary() {
        return isMandotary;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDescription() {
        return description;
    }
}
