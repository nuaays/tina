package com.zhongan.scorpoin.biz.user.sample;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.biz.user.dto.UserRequest;
import com.zhongan.scorpoin.common.ZhongAnApiClient;
import com.zhongan.scorpoin.common.ZhongAnOpenException;

public class UserSample {

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

        UserRequest request = new UserRequest("zhongan.user.person.addByCertiNo");
        JSONObject params = new JSONObject();

        params.put("userName", "");//姓名
        params.put("sex", "");//性别
        params.put("email", "");//email
        params.put("mobilePhone", "");//手机号
        params.put("address", "");//用户地址
        params.put("certiType", "");//证件类型 I-身份证，P-护照，M-军官证，GT-港台同胞证，S-士兵证
        params.put("certiNo", "");//证件号码
        params.put("birth", "");//生日

        request.setParams(params);

        CommonResponse response = (CommonResponse) client.call(request);
        System.out.println(response);

    }

}
