package com.zhongan.scorpoin.biz.common;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.common.ZhongAnApiClient;
import com.zhongan.scorpoin.common.ZhongAnOpenException;

public class CommonSample {

    public static void main(String[] args) throws ZhongAnOpenException {
        ZhongAnApiClient client = new ZhongAnApiClient(
                "dev",
                "10001",
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAO8h8JCJAMb1nd0uBkzZuWyNnL+atBzJKvIG7escD45ODf0AWKr8vSqLZ01HD86+a496CGjsae6GybK8C1MqiMSwaAsIv31nKD6U8xF607MPrD3r2lyjwUnmqBZY++R6yFNYz9ZDXcdiwCudESRsXunPJq7zfnnglCtEH+qqW8/VAgMBAAECgYEAnVc2gtMyKLbZTPuId65WG7+9oDB5S+ttD1xR1P1cmuRuvcYpkS/Eg6a/rJASLZULDpdbyzWqqaAUPD8QMINvAr3ZtkbwH5R0F/4aqOlx/5B0Okjsp3eSK2bQ8J2m/MmFKZxr6Aily7YUDdxcGcjLizsGi1KDkWS22JRufEeUNA0CQQD+g1XJ7ELqmUtrS4m4XnadB25f0g5QC0tMjoa3d9soMzK3q+Drkv8EZVpGSmSHEo1VlE7HUcnKNvK1BO5Nm4iXAkEA8IeZxaWmEcsRqiuFz8xmYGtKcYTmHgheGF4D+fnnFozSNP+3sS1lfgFQrjUkqUyZOoG1hPc6SDhGS4nbXwiscwJASO+gPR58yrgledkK3ZAMk9GWWtVajqu953GMv7UUU//gD+yspzXX6Q2WgkA9cMvrPtQig1I37sAya5e/JvRkfwJARzzCDEmdP9PW7YFqZjrxb0kXiTuFNAviYnEl2FltWb5nW48JBo6dao5VKONQclvfXfagnjriphUUrLatpB3bhQJAKRfJS6jDAIVKt7So5HOdzk4ipxgrMjG/QtZ1grO+VQectk4+tCwdJhOrr5blvdPQvFVqXBQfXuE7cibZrGs4sQ==",
                "1.0.0.4554");

        CommonRequest request = new CommonRequest("zhongan.cargo.getAllCargoArea");
        JSONObject params = new JSONObject();
        request.setParams(params);

        CommonResponse response = (CommonResponse) client.call(request);
        System.out.println(response);
    }
}
