package com.zhongan.scorpoin.biz.commplan.sample;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhongan.cashier.ConfirmPayReturn;
import com.zhongan.cashier.PayDtlReturn;
import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.biz.commplan.dto.CommPlanRequest;
import com.zhongan.scorpoin.common.ZhongAnApiClient;
import com.zhongan.scorpoin.common.ZhongAnEnvEnum;
import com.zhongan.scorpoin.common.ZhongAnOpenException;
import zahyHttpHelp.hyHttpHelp;

public class NewCall {

	static JSONObject params = new JSONObject();
	static Logger logger = Logger.getLogger(NewCall.class); 
	public static String strHsfUrl;

	static ZhongAnApiClient client = new ZhongAnApiClient("uat", "35c2ea3c3ea84fca9f422e5dfd991e06",
			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOw1W6xaGLll7OtAUEbFlCSDfTd+nqxVWXyllmiV+YjiBzkov86O2GvOI9lOu8XlEy45cWk9e/CZCdH0V92IMi3tyLl8ycb4jC0fta0TWNihe6CyhsVUj1kPzYy+AH+Loz5mcgF6sz//md9MOID1/Qd/YF19gcw6n/o4V482Rve5AgMBAAECgYBZM0k8TAXcNaaDrJTkNQbdxx4JT/LB57VUgf/L3R8P1zOdHUtZyM3n4D/fd4EnmXtl0GGIuaRNVq3DsL9htGYmAkOsggQnBPdTFbe7Q6KhXZLusyoNUFvlIAbQSVdCgAWCA9V6iZ6vrmNW2urj1h1KQFvAbDVllRDZ4vaejF4xVQJBAPyYSq8RsXgEAiZvk01+7rAiKvgsPpxf2wG2UHGSvatizDQZIgfME9191hz3b2yOUqi0Hha2MttLi4LWfZbF9BMCQQDvZIQ39DZiSxOYn24sxg26+qUx48Yc2Yes9m6UEzAuDuDCMapyJ+v62rjnPi9jn9X+hxWfhdpJHfj6KVO1xuaDAkBkzL1Y+b2RgD//aJ0m2tWTkj8FhFqD+riiCUg22nE4OJf23mS3KdhvlizgqFldv7n6us4bECBhZNdKoh/CEELjAkEA7PtXfECweZuSuZrSKVaijv/C+uFd5H9fNVT64HEiV+X4j6U08y8cB0fwlVJU/U1kPUSinjmWfp1CNPsmWCOfWwJBAKzoNFbjXGCnoXveyaKxzuo4Xb/otN+4pSCtikMxamASEfFkoxqLWMQFJHXGoDEheycBLFfVvodBQjh1LH1XJkU=",
			"1.0.0");

	public static String CallOpenRequest(JSONObject params, String str)
			throws ZhongAnOpenException {

		CommPlanRequest request = null;
		if (str.equals("add")) {
			System.out.println("二、投保接口 将被调用");
			logger.info("二、投保接口 将被调用");
			request = new CommPlanRequest("zhongan.open.common.addPolicy");
		} else if (str.equals("val")) {
			System.out.println("一、核报接口将 被调用");
			logger.info("一、核报接口将 被调用");
			request = new CommPlanRequest("zhongan.open.common.validatePolicy");
		}
		else if (str.equals("query")){
			logger.info("三、查询保单接口将 被调用");
			System.out.println("三、查询保单接口将 被调用");
			request = new CommPlanRequest("zhongan.open.common.queryPolicyInfo");
		}

		request.setParams(params);

		CommonResponse response = (CommonResponse) client.call(request);

		String str_addPolicy = "[" + response.getBizContent() + "]";
		/*List<AddPolicyReturnParameter> addPolicy_return = JSON.parseArray(str_addPolicy,
				AddPolicyReturnParameter.class);
		if (str.equals("add")) {

			String strPolicyNO = addPolicy_return.get(0).getPolicyNo();
			System.out.println("Policy No is :" + strPolicyNO);
			
		}*/

		return str_addPolicy;

	}

	public static String PaymentPolicy(JSONObject merOrd, JSONObject payDtl, JSONObject cnfmPay, int i) {

		String response_order = hyHttpHelp.hsfHttpPost(strHsfUrl + "addMerchantOrder",
				"com.zhongan.cashier.dto.AddCashierOrderRequest", merOrd.toString());
		
		System.out.println("merchant Order----: " + response_order);
		logger.info("merchant Order----: " + response_order);

		String str = "[" + response_order + "]";

		List<PayResonseParameter> PRP = JSON.parseArray(str, PayResonseParameter.class);

		// 得到zaOrderNo
		String strZaOrdNo = PRP.get(0).getZaOrderNo();
		System.out.print(PRP.get(0).getZaOrderNo());

		// 将zaOrdNo加入到postData
		payDtl.put("zaOrderNo", strZaOrdNo);
		// 调用 paydetail 接口
		String response_payDtl = hyHttpHelp.hsfHttpPost(strHsfUrl + "addPayDetail",
				"com.zhongan.cashier.dto.AddPayDetailRequest", payDtl.toString());
		String str_payDtl = "[" + response_payDtl + "]";

		@SuppressWarnings("all")
		List<PayDtlReturn> PDR = JSON.parseArray(str_payDtl, PayDtlReturn.class);

		// 调用confirmPay 接口
		cnfmPay.put("zaOrderNo", strZaOrdNo);
		String response_payCnfm = hyHttpHelp.hsfHttpPost(strHsfUrl + "confirmSpecialOrderPay",
				"com.zhongan.cashier.dto.ConfirmOrderPayRequest", cnfmPay.toString());
		String str_payCnfm = "[" + response_payCnfm + "]";
		@SuppressWarnings("all")
		List<ConfirmPayReturn> CPR = JSON.parseArray(str_payCnfm, ConfirmPayReturn.class);

		if (CPR.get(0).getIsSuccess().equals("true")){
			System.out.println("已经为第" + i + "个case 完成支付。");
			logger.info("已经为第" + i + "个case 完成支付。");
			return strZaOrdNo;
		}
		else 
		{
			System.out.println("第" + i + "个case 支付失败！！！");
			logger.error("第" + i + "个case 支付失败！！！");
			return null;
		}
	}

	static {
		Properties p = new Properties();

		InputStream is = ZhongAnEnvEnum.class.getResourceAsStream("dev.properties");
		try {
			p.load(is);
			NewCall.strHsfUrl = p.getProperty("hsfurl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
