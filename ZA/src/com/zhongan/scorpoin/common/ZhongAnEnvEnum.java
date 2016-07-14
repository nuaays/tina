package com.zhongan.scorpoin.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 三套环境的众安公钥
 * 
 * @author linwusheng 2015年11月10日 下午5:46:11
 */
public enum ZhongAnEnvEnum {

    DEV(
            "dev",
            "http://120.27.167.36:8080/Gateway.do",
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB",
            "开发环境众安公钥"),
    TEST(
            "iTest",
            "http://120.27.167.36:8080/Gateway.do",
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB",
            "测试环境众安公钥"),
    UAT(
            "uat",
            "http://opengw.uat.zhongan.com/Gateway.do",
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDsNVusWhi5ZezrQFBGxZQkg303fp6sVVl8pZZolfmI4gc5KL/OjthrziPZTrvF5RMuOXFpPXvwmQnR9FfdiDIt7ci5fMnG+IwtH7WtE1jYoXugsobFVI9ZD82MvgB/i6M+ZnIBerM//5nfTDiA9f0Hf2BdfYHMOp/6OFePNkb3uQIDAQAB",
            "预发环境众安公钥"),
    PRD(
            "prd",
            "http://opengw.zhongan.com/Gateway.do",
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFNndmLlsi8NYQpvZNK/b6kSjN99lwWnWbAHxfBcBYQHx5mZBR8XkkIajSiYo29f7zmM0eAI8OSo6FY16bSt23RzThd+MvDBQC6axDCgGag5992AVGItU8LtWPBrM6XRbtN3+rjIteKhNDOUbEvp60S9/8uoEfnqekd/nEG9I4mQIDAQAB",
            "生产环境众安公钥");

    private String envCode;

    private String url;

    private String publicKey;

    private String description;

    private ZhongAnEnvEnum(String envCode, String url, String publicKey, String description) {
        this.envCode = envCode;
        this.url = url;
        this.publicKey = publicKey;
        this.description = description;

    }

    static {

        Properties p = new Properties();
        try {
            InputStream is = ZhongAnEnvEnum.class.getResourceAsStream("dev.properties");
            p.load(is);
            DEV.setUrl(p.getProperty("url"));
        } catch (FileNotFoundException e) {
            //  Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            //  Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static ZhongAnEnvEnum get(String envCode) {

        for (ZhongAnEnvEnum an : ZhongAnEnvEnum.values()) {
            if (null != envCode && envCode.equals(an.envCode)) {
                return an;
            }
        }

        return null;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
