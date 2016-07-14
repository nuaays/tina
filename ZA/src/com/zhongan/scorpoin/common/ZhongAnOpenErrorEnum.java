package com.zhongan.scorpoin.common;

public enum ZhongAnOpenErrorEnum {

    ARGUMENTS_MISSING("argument_missing", "缺少参数"),
    ARGUMENTS_ILLEGAL("argument_illegal", "参数不合法"),
    ENCRYPT_SIGN_FAILED("encrypt_sign_failed", "签名加密失败");

    private String code;

    private String description;

    private ZhongAnOpenErrorEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ZhongAnOpenErrorEnum getEnum(String code) throws ZhongAnOpenException {
        for (ZhongAnOpenErrorEnum an : ZhongAnOpenErrorEnum.values()) {
            if (code.equals(an.getCode())) {
                return an;
            }
        }
        throw new ZhongAnOpenException("枚举code无效: " + code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
