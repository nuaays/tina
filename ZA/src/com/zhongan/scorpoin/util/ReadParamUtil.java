package com.zhongan.scorpoin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSONObject;

/**
 * 类CheckArgNullUtil.java的实现描述：TODO 类实现描述
 */
public class ReadParamUtil {
    public static String path = "E:\\biz\\param\\";

    public static Object getParams(Object obj, String txtpath) throws Exception {
        File file = new File(txtpath);
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "gbk");//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);

        String params = "";
        String lineTxt;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            params += lineTxt;
        }
        read.close();
        return JSONObject.parseObject(params, obj.getClass());
    }

    public static Object getParamsByName(Object obj, String name) throws Exception {

        return getParams(obj, path + name);
    }

}
