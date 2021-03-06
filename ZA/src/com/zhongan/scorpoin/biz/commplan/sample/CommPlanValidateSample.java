package com.zhongan.scorpoin.biz.commplan.sample;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.biz.commplan.dto.CommPlanRequest;
import com.zhongan.scorpoin.common.ZhongAnApiClient;
import com.zhongan.scorpoin.common.ZhongAnOpenException;

public class CommPlanValidateSample {

    public static void main(String[] args) throws ZhongAnOpenException {

        //env：环境参数，在dev、iTest、uat、prd中取值
        //appKey：开发者的appKey
        //privateKey：开发者私钥
        //version:版本号,众安技术人员提供
        ZhongAnApiClient client = new ZhongAnApiClient(
                "uat",
                "35c2ea3c3ea84fca9f422e5dfd991e06",
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOw1W6xaGLll7OtAUEbFlCSDfTd+nqxVWXyllmiV+YjiBzkov86O2GvOI9lOu8XlEy45cWk9e/CZCdH0V92IMi3tyLl8ycb4jC0fta0TWNihe6CyhsVUj1kPzYy+AH+Loz5mcgF6sz//md9MOID1/Qd/YF19gcw6n/o4V482Rve5AgMBAAECgYBZM0k8TAXcNaaDrJTkNQbdxx4JT/LB57VUgf/L3R8P1zOdHUtZyM3n4D/fd4EnmXtl0GGIuaRNVq3DsL9htGYmAkOsggQnBPdTFbe7Q6KhXZLusyoNUFvlIAbQSVdCgAWCA9V6iZ6vrmNW2urj1h1KQFvAbDVllRDZ4vaejF4xVQJBAPyYSq8RsXgEAiZvk01+7rAiKvgsPpxf2wG2UHGSvatizDQZIgfME9191hz3b2yOUqi0Hha2MttLi4LWfZbF9BMCQQDvZIQ39DZiSxOYn24sxg26+qUx48Yc2Yes9m6UEzAuDuDCMapyJ+v62rjnPi9jn9X+hxWfhdpJHfj6KVO1xuaDAkBkzL1Y+b2RgD//aJ0m2tWTkj8FhFqD+riiCUg22nE4OJf23mS3KdhvlizgqFldv7n6us4bECBhZNdKoh/CEELjAkEA7PtXfECweZuSuZrSKVaijv/C+uFd5H9fNVT64HEiV+X4j6U08y8cB0fwlVJU/U1kPUSinjmWfp1CNPsmWCOfWwJBAKzoNFbjXGCnoXveyaKxzuo4Xb/otN+4pSCtikMxamASEfFkoxqLWMQFJHXGoDEheycBLFfVvodBQjh1LH1XJkU=",
                "1.0.0");
        //核保接口
        CommPlanRequest request = new CommPlanRequest("zhongan.open.common.validatePolicy");
        JSONObject params = new JSONObject();

        params.put("productMask", "ff633ad8736e6bf5b50fae903e4dad1223debc94");//众安提供的产品唯一的掩码
        params.put(
                "policyHolderUserInfo",
                "{\"policyHolderUserName\": \"雾非雾\",\"policyHolderCertiType\": \"I\",\"policyHolderCertiNo\": \"230404199711180492\",\"policyHolderPhone\": \"18600000000\",\"policyHolderBirth\": \"19971118\",\"policyHolderEmail\": \"xxx@qq.com\",\"policyHolderSex\":\"M\"}");//投保人信息
        params.put(
                "insureUserInfo",
                "[{\"insureUserName\": \"雾非雾\",\"insureCertiType\": \"I\",\"insureCertiNo\": \"230404199711180492\",\"insureBirth\": \"19971118\",\"insurePhone\": \"18600000000\",\"insureRelation\":\"父子\",\"insureSex\":\"M\"}]");//被保人信息
        params.put("premium", "58");//
        params.put("policyBeginDate", "20110709000000");//保单起期，格式yyyyMMddHHmmss
        params.put("channelId", "1140");//渠道id 由众安提供
        params.put("extraInfo", "");//Json格式的业务扩展字符串
        params.put("policyEndDate", "20120708000000");
        params.put("sumInsured", "6000");

        System.out.println("11111111111111111111111111");
    	System.out.println(params);

    	System.out.println("11111111111111111111111112");
        request.setParams(params);

        CommonResponse response = (CommonResponse) client.call(request);
        System.out.println(response);
        
        System.out.println(response.getBizContent());

    }

}
