package com.zhongan.scorpoin.biz.commplan.sample;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.biz.commplan.dto.CommPlanRequest;
import com.zhongan.scorpoin.common.ZhongAnApiClient;
import com.zhongan.scorpoin.common.ZhongAnOpenException;

public class CommPlanAddSample {

    public static void main(String[] args) throws ZhongAnOpenException {

        //env：环境参数，在dev、iTest、uat、prd中取值
        //appKey：开发者的appKey
        //privateKey：开发者私钥
        //version:版本号,众安技术人员提供
        ZhongAnApiClient client = new ZhongAnApiClient(
                "dev",
                "9aec94b2cd97571cfab05005e5875301",
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANh61L+DYUsn3yw560NtiKh/8WBDYcnhO7OCCiGUayvl0pT0M2CitouP1Dc3JIYA7utlvJqxWIlT+pN58g3rg6lvKlnzUEur4VRXJhMeNmd1aq4IP+Zdq9Tizq1UvVYz/AzzY+Djc9fpauOjqDIHK07imNqF/oFE0ZjPT0B4MXZxAgMBAAECgYA0/9WIUbUHPmrAHCoCJxX3EuPYioatc0w3hZXPZNPcOncU6riNZyjEAGGXZxO1DxNvZEgJo3Omo33Mj2V4jPl9lYjgOoA7hWXDw2tGuv7R5nxQBhklaHhd2+UduYRnVpE136dkx8Z4LVVst4pmwEFhRHk9pEyAId+HMj5qx6VIsQJBAPbABwoh67NUT6Xh5yfFjqTwHqE4djJbwSfG8BIwkwqCbDBdgXuNDoSWrZ4mr1kh71l8vBhGxSp+imUumaDgxTUCQQDgmE9xTY0Eo+lPfh5M/RQE2yT1kcboSfYEmYcAnwiE+BC4mhghsemDwZ9bmQ46lU6oS0Ow7zQw7BzbivHD7b/NAkB10tAJuJTR9sppjWtRhHZOsBIQLePSvBmJoubz6JnuBMUgeyXfF0X9be3NfO9yAlBGTNeMSA7R8can9g6J0YqZAkApUNuMZE/EwsJwtSqtzwCXxBiQdDi7EqAHSJblLlxK2bd5vh8iU7A5ZK0EFKvhawYFP5M8QUTAmy7T1EOVX28hAkEAl1h2epeSBydMfXKeoxFs2ebJxZfDXs4bFSfiWswbQqnpjC+naFZutlLHXmL/wiUTIQxFyoJhmjE7cXyBoWrXvw==",
                "1.0.0");
        //投保接口
        CommPlanRequest request = new CommPlanRequest("zhongan.open.common.addPolicy");
        JSONObject params = new JSONObject();

        params.put("productMask", "1416c49773a3a39d07560fe14152b21472ee721e32");//众安提供的产品唯一的掩码
        params.put("channelOrderNo", "yang321543251451451");//渠道方每次请求的传入唯一流水号
        params.put("zaOrderNo", "901000120160526090902029547");//众安订单号
        params.put(
                "policyHolderUserInfo",
                "{\"policyHolderUserName\": \"测试投保人\",\"policyHolderCertiType\": \"I\",\"policyHolderCertiNo\": \"370101198005060013\",\"policyHolderPhone\": \"18600000000\",\"policyHolderBirth\": \"19800506\",\"policyHolderEmail\": \"xxx@qq.com\",\"policyHolderSex\":\"M\"}");//投保人信息
        params.put(
                "insureUserInfo",
                "[{\"insureUserName\": \"测试被保人\",\"insureCertiType\": \"I\",\"insureCertiNo\": \"370101199505060014\",\"insureBirth\": \"19950506\",\"insurePhone\": \"18600000000\",\"insureRelation\":\"父子\",\"insureSex\":\"M\"}]");//被保人信息
        params.put("premium", "5");//保费
        params.put("policyBeginDate", "20170528000000");//保单起期，格式yyyyMMddHHmmss
        params.put("channelId", "1062");//渠道id 由众安提供
        params.put("extraInfo", "");//Json格式的业务扩展字符串

        request.setParams(params);

        CommonResponse response = (CommonResponse) client.call(request);
        System.out.println(response);

    }

}
