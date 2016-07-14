package com.zhongan.scorpoin.util;

import java.lang.reflect.Field;

import com.zhongan.scorpoin.common.ZhongAnOpenErrorEnum;
import com.zhongan.scorpoin.common.ZhongAnOpenException;

/**
 * 类CheckArgNullUtil.java的实现描述：TODO 类实现描述
 * 
 * @author linwusheng 2016年1月19日 下午3:17:24
 */
public class CheckArgNullUtil {
    @SuppressWarnings("rawtypes")
    public static void check(Object o, Class cz, String... arg) throws ZhongAnOpenException {
        if (o == null) {
            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_MISSING.getCode(), "Object为空");
        }

        Field fields[] = cz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);//修改访问权限  
            for (String attribute : arg) {
                if (attribute.equals(field.getName())) {

                    String type = "";
                    Object value = null;
                    try {
                        type = field.getType().getName();
                        value = field.get(o);
                    } catch (Exception e) {
                        //                        e.printStackTrace();
                    }
                    if ("java.lang.String".equals(type)) {
                        if (value == null) {
                            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_MISSING.getCode(), attribute
                                    + "为空");
                        }
                        if (StringUtils.isEmpty(value.toString())) {
                            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_MISSING.getCode(), attribute
                                    + "为空");
                        }
                    } else {
                        if (value == null) {
                            throw new ZhongAnOpenException(ZhongAnOpenErrorEnum.ARGUMENTS_MISSING.getCode(), attribute
                                    + "为空");
                        }
                    }

                }
            }
        }
    }
}
