package com.zhongan.scorpoin.util;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.zhongan.scorpoin.common.ZhongAnOpenErrorEnum;
import com.zhongan.scorpoin.common.ZhongAnOpenException;

/**
 * 类CheckArgNullUtil.java的实现描述：TODO 类实现描述
 * 
 * @author linwusheng 2016年1月19日 下午3:17:24
 */
public class CheckArgUtil {

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE       = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL        = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE_NAME = "^[\u4e00-\u9fa5]{2,5}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD      = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_CAR_NO       = "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$";

    public static final String REGEX_DATEFORMAT   = "yyyy-MM-dd";

    public static void main(String[] args) throws Exception {
        try {
            checkArg("checkCarNo", "鲁AYE2FP");
            checkArg("checkCarNo", "鲁sss");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            checkArg("checkDate", "2016-03-12");
            checkArg("checkDate", "2016/02/04");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {

            checkArg("checkMobile", "13701778185");
            checkArg("checkMobile", "你好啊");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {

            checkArg("checkEmail", "553645248@qq.com");
            checkArg("checkEmail", "你好啊");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            checkArg("checkChineseName", "号");
            checkArg("checkChineseName", "好啊好啊好啊");
            checkArg("checkChineseName", "123");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            checkArg("checkIDCard", "362301199203055632");
            checkArg("checkIDCard", "3623011992030556323");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkArg(String type, String arg) throws ZhongAnOpenException {

        Method[] methods = CheckArgUtil.class.getMethods();
        for (Method m : methods) {
            if (m.getName().equalsIgnoreCase(type)) {
                try {
                    m.invoke(null, arg);
                } catch (Exception e) {

                    throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_ILLEGAL.getCode(), e.getCause()
                            .getMessage());
                }
                return;
            }
        }

    }

    /**
     * 校验车牌号
     * 
     * @param carNo
     * @throws ZhongAnOpenException
     */
    public static void checkCarNo(String carNo) throws ZhongAnOpenException {

        if (!Pattern.matches(REGEX_CAR_NO, carNo)) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_ILLEGAL.getCode(), "车牌号:" + carNo + " 不符合规范");
        }
    }

    public static void checkDateByFormat(String dateStr, String dateFormat) throws ZhongAnOpenException {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            Date date = formatter.parse(dateStr);
            if (!dateStr.equals(formatter.format(date))) {
                throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_ILLEGAL.getCode(), "日期:" + dateStr
                        + " 不符合规范");
            }
        } catch (Exception e) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_ILLEGAL.getCode(), "日期:" + dateStr + " 不符合规范");
        }
    }

    public static void checkDate(String dateStr) throws ZhongAnOpenException {
        checkDateByFormat(dateStr, REGEX_DATEFORMAT);
    }

    public static void checkMobile(String mobile) throws ZhongAnOpenException {

        if (!Pattern.matches(REGEX_MOBILE, mobile)) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_ILLEGAL.getCode(), "手机号:" + mobile + " 不符合规范");
        }
    }

    public static void checkEmail(String email) throws ZhongAnOpenException {

        if (!Pattern.matches(REGEX_EMAIL, email)) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_ILLEGAL.getCode(), "邮箱:" + email + " 不符合规范");
        }
    }

    /**
     * 校验中文名（2-5字符串）
     * 
     * @param name
     * @throws ZhongAnOpenException
     */
    public static void checkChineseName(String name) throws ZhongAnOpenException {
        if (!Pattern.matches(REGEX_CHINESE_NAME, name)) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_ILLEGAL.getCode(), "中文名:" + name + " 不符合规范");
        }
    }

    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static void checkIDCard(String idCard) throws ZhongAnOpenException {
        if (!Pattern.matches(REGEX_ID_CARD, idCard)) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_ILLEGAL.getCode(), "身份证:" + idCard + " 不符合规范");
        }
    }

}
