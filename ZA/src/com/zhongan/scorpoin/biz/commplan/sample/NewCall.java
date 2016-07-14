package com.zhongan.scorpoin.biz.commplan.sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import com.zhongan.cashier.ConfirmPayReturn;
import com.zhongan.cashier.PayDtlReturn;
import com.zhongan.cashier.Payment;
import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.biz.commplan.dto.CommPlanRequest;
import com.zhongan.scorpoin.common.ZhongAnApiClient;
import com.zhongan.scorpoin.common.ZhongAnEnvEnum;
import com.zhongan.scorpoin.common.ZhongAnOpenException;
import com.zhongan.test.ID.CertiGeneration;

import au.com.bytecode.opencsv.CSVWriter;
import zahyHttpHelp.hyHttpHelp;

public class NewCall {
	public String strProductMask;
	public String strPolicyHolderUserInfo;
	public String strInsureUserInfo;
	public String strPremium;
	public String strPolicyBeginDate;
	public String strChannelId;
	public String strExtraInfo;
	public String strPolicyEndDate;
	public String strSumInsured;

	static JSONObject params = new JSONObject();

	public static String strHsfUrl = "http://10.253.27.175:8086/com.zhongan.cashier.service.CashierOrderService:1.0.0/";
	static ZhongAnApiClient client = new ZhongAnApiClient("uat", "35c2ea3c3ea84fca9f422e5dfd991e06",
			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOw1W6xaGLll7OtAUEbFlCSDfTd+nqxVWXyllmiV+YjiBzkov86O2GvOI9lOu8XlEy45cWk9e/CZCdH0V92IMi3tyLl8ycb4jC0fta0TWNihe6CyhsVUj1kPzYy+AH+Loz5mcgF6sz//md9MOID1/Qd/YF19gcw6n/o4V482Rve5AgMBAAECgYBZM0k8TAXcNaaDrJTkNQbdxx4JT/LB57VUgf/L3R8P1zOdHUtZyM3n4D/fd4EnmXtl0GGIuaRNVq3DsL9htGYmAkOsggQnBPdTFbe7Q6KhXZLusyoNUFvlIAbQSVdCgAWCA9V6iZ6vrmNW2urj1h1KQFvAbDVllRDZ4vaejF4xVQJBAPyYSq8RsXgEAiZvk01+7rAiKvgsPpxf2wG2UHGSvatizDQZIgfME9191hz3b2yOUqi0Hha2MttLi4LWfZbF9BMCQQDvZIQ39DZiSxOYn24sxg26+qUx48Yc2Yes9m6UEzAuDuDCMapyJ+v62rjnPi9jn9X+hxWfhdpJHfj6KVO1xuaDAkBkzL1Y+b2RgD//aJ0m2tWTkj8FhFqD+riiCUg22nE4OJf23mS3KdhvlizgqFldv7n6us4bECBhZNdKoh/CEELjAkEA7PtXfECweZuSuZrSKVaijv/C+uFd5H9fNVT64HEiV+X4j6U08y8cB0fwlVJU/U1kPUSinjmWfp1CNPsmWCOfWwJBAKzoNFbjXGCnoXveyaKxzuo4Xb/otN+4pSCtikMxamASEfFkoxqLWMQFJHXGoDEheycBLFfVvodBQjh1LH1XJkU=",
			"1.0.0");

	public static String CallOpenRequest(JSONObject params, Boolean addFlg, Boolean validateFlg)
			throws ZhongAnOpenException {

		CommPlanRequest request = null;
		System.out.println("投保flag 为 ：" + addFlg);
		System.out.println("核保flag 为 ：" + validateFlg.toString());
		if (addFlg.equals(true)) {
			System.out.println("投保接口 被调用");
			request = new CommPlanRequest("zhongan.open.common.addPolicy");
		} else if (validateFlg.equals(true)) {
			System.out.println("核报接口 被调用");
			request = new CommPlanRequest("zhongan.open.common.validatePolicy");
		}

		request.setParams(params);

		CommonResponse response = (CommonResponse) client.call(request);

		String str_addPolicy = "[" + response.getBizContent() + "]";
		System.out.println("投核保接口 返回bizContent :" + str_addPolicy);
		List<AddPolicyReturnParameter> addPolicy_return = JSON.parseArray(str_addPolicy,
				AddPolicyReturnParameter.class);
		if (addFlg.equals(true)) {

			String strPolicyNO = addPolicy_return.get(0).getPolicyNo();
			System.out.println("Policy No is :" + strPolicyNO);
			
		}

		return str_addPolicy;

	}

	public static String PaymentPolicy(JSONObject merOrd, JSONObject payDtl, JSONObject cnfmPay, int i) {
		// String strResponse=null;

		String response_order = hyHttpHelp.hsfHttpPost(strHsfUrl + "addMerchantOrder",
				"com.zhongan.cashier.dto.AddCashierOrderRequest", merOrd.toString());
		
		System.out.println("merchant Order----: " + response_order);

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

		List<PayDtlReturn> PDR = JSON.parseArray(str_payDtl, PayDtlReturn.class);

		// 调用confirmPay 接口
		cnfmPay.put("zaOrderNo", strZaOrdNo);
		String response_payCnfm = hyHttpHelp.hsfHttpPost(strHsfUrl + "confirmSpecialOrderPay",
				"com.zhongan.cashier.dto.ConfirmOrderPayRequest", cnfmPay.toString());
		String str_payCnfm = "[" + response_payCnfm + "]";
		List<ConfirmPayReturn> CPR = JSON.parseArray(str_payCnfm, ConfirmPayReturn.class);

		System.out.println("已经为第" + i + "个case 完成支付");

		int j = CPR.size();
		System.out.println("waiting j:" + j);

		return strZaOrdNo;
		// return CPR.get(i).getSuccess();
		// return ((PRP.get(i).getSuccess())& (PDR.get(i).getIsSuccess()) &
		// (CPR.get(i).getSuccess()));

	}

	public static void DoCallValidatePolicyFull(List<ValidatePolicyFullPara> lt, List<ValidatePolicyFailPara> lt_out,
			int i, String StrpolicyHolderUserInfro, String StrinsureUserInfo) throws ZhongAnOpenException {
		String validate_result = null;

		validate_result = NewCall.CallValidatePolicy(lt.get(i).getProductMask(), StrpolicyHolderUserInfro,
				StrinsureUserInfo, lt.get(i).getPremium(), lt.get(i).getPolicyBeginDate(), lt.get(i).getChannelId(),
				lt.get(i).getExtraInfo(), lt.get(i).getPolicyEndDate(), lt.get(i).getSumInsured());

		System.out.println("taoqilei" + validate_result + "\n");
		System.out.println("taoqilei -------");

		/*
		 * String rex = "[{}]+"; String[] str = validate_result.split(rex);
		 * System.out.println(str[1]);
		 * System.out.println("before split "+lt.get(i).getBizContent());
		 * System.out.println(lt.get(i).getBizContent().split(rex)); // 比较 lt 中
		 * 第26个 bizContent 和 response 中的 bizContent String[] str2 =
		 * lt.get(i).getBizContent().split(rex);
		 */

		String str2 = lt.get(i).getBizContent();
		if (validate_result.toString().equals(str2.toString())) {
			System.out.println("ok -------");
		} else {
			System.out.println("fail -------");
			/*
			 * ValidatePolicyFailPara vpfp = new ValidatePolicyFailPara();
			 * 
			 * CommPlanRequest request = new
			 * CommPlanRequest("zhongan.open.common.validatePolicy");
			 * 
			 * JSONObject params = new JSONObject(); params.put("productMask",
			 * lt.get(i).getProductMask());// �ڰ��ṩ�Ĳ�ƷΨһ������
			 * params.put("policyHolderUserInfo",
			 * policyHolderUserInfo.toJSONString());// Ͷ������Ϣ
			 * params.put("insureUserInfo", insureUserInfo.toJSONString());//
			 * ��������Ϣ params.put("premium", lt.get(i).getPremium());//
			 * params.put("policyBeginDate", lt.get(i).getPolicyBeginDate());//
			 * �������ڣ���ʽyyyyMMddHHmmss params.put("channelId",
			 * lt.get(i).getChannelId());// ����id ���ڰ��ṩ
			 * params.put("extraInfo", lt.get(i).getExtraInfo());//
			 * Json��ʽ��ҵ����չ�ַ��� params.put("policyEndDate",
			 * lt.get(i).getPolicyEndDate()); params.put("sumInsured",
			 * lt.get(i).getSumInsured()); request.setParams(params);
			 * 
			 * vpfp.setCaseNo("case " + i);
			 * vpfp.setBizContent(params.toString());
			 * vpfp.setActualResult(validate_result.toString());
			 * vpfp.setExpectResult(str2.toString());
			 * 
			 * lt_out.add(vpfp);
			 */
		}

	}

	public static void DoCallValidatePolicy(List<ValidatePolicyPara> lt, List<ValidatePolicyFailPara> lt_out)
			throws ZhongAnOpenException {
		String result = null;

		for (int i = 1; i < lt.size(); i++) {

			result = NewCall.CallValidatePolicy(lt.get(i).getProductMask(),
					lt.get(i).getPolicyHolderUserInfo(), lt.get(i).getInsureUserInfo(), lt.get(i).getPremium(),
					lt.get(i).getPolicyBeginDate(), lt.get(i).getChannelId(), lt.get(i).getExtraInfo(),
					lt.get(i).getPolicyEndDate(), lt.get(i).getSumInsured());

			System.out.println("taoqilei" + result + "\n");
			System.out.println("taoqilei -------");

			String rex = "[{}]+";
			String[] str = result.split(rex);
			System.out.println(str[1]);
			System.out.println("before split " + lt.get(i).getBizContent());
			System.out.println(lt.get(i).getBizContent().split(rex));
			// 比较 lt 中 第26个 bizContent 和 response 中的 bizContent
			String[] str2 = lt.get(i).getBizContent().split(rex);
			if (str[1].toString().equals(str2[1].toString())) {
				System.out.println("ok -------");
			} else {
				System.out.println("fail -------");
				ValidatePolicyFailPara vpfp = new ValidatePolicyFailPara();

				CommPlanRequest request = new CommPlanRequest("zhongan.open.common.validatePolicy");

				JSONObject params = new JSONObject();
				params.put("productMask", lt.get(i).getProductMask());// �ڰ��ṩ�Ĳ�ƷΨһ������
				params.put("policyHolderUserInfo", lt.get(i).getPolicyHolderUserInfo());// Ͷ������Ϣ
				params.put("insureUserInfo", lt.get(i).getInsureUserInfo());// ��������Ϣ
				params.put("premium", lt.get(i).getPremium());//
				params.put("policyBeginDate", lt.get(i).getPolicyBeginDate());// �������ڣ���ʽyyyyMMddHHmmss
				params.put("channelId", lt.get(i).getChannelId());// ����id
																	// ���ڰ��ṩ
				params.put("extraInfo", lt.get(i).getExtraInfo());// Json��ʽ��ҵ����չ�ַ���
				params.put("policyEndDate", lt.get(i).getPolicyEndDate());
				params.put("sumInsured", lt.get(i).getSumInsured());
				request.setParams(params);

				vpfp.setCaseNo("case " + i);
				vpfp.setBizContent(params.toString());
				vpfp.setActualResult(str2[1].toString());
				vpfp.setExpectResult(str[1].toString());

				lt_out.add(vpfp);
			}

		}
	}

	/**
	 * ��ȡCSV�ļ�
	 */
	public void readeCsv() {
		try {

			int rankNumber = 7;
			ArrayList<String[]> csvList = new ArrayList<String[]>(); // ������������
			String csvFilePath = "./zhonganRead.csv";
			CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("GB2312")); // һ�����������Ϳ�����
			CSVWriter writer = new CSVWriter(new FileWriter(new File("./testReport.csv")), ',');
			List<String[]> alList = new ArrayList<String[]>();
			List<String> list = new ArrayList<String>();
			String strResponse = null;

			reader.readHeaders(); // ������ͷ �����Ҫ��ͷ�Ļ�����Ҫд��䡣

			while (reader.readRecord()) { // ���ж������ͷ������
				csvList.add(reader.getValues());
			}
			reader.close();

			System.out.println(csvList.size());

			for (int row = 0; row < csvList.size(); row++) {
				for (int rank = 0; rank < rankNumber; rank++) {
					String cell = csvList.get(row)[rank]; // ȡ�õ�row�е�0�е�����
					switch (rank) {
					case 0:
						this.setStrProductMask(cell);
						break;
					case 1:
						this.setStrPolicyHolderUserInfo(cell);
						break;
					case 2:
						this.setStrInsureUserInfo(cell);
						break;
					case 3:
						this.setStrPremium(cell);
						break;
					case 4:
						this.setStrPolicyBeginDate(cell);
						break;
					case 5:
						this.setStrChannelId(cell);
						break;
					case 6:
						this.setStrExtraInfo(cell);
						break;

					default:
						break;
					}
					// System.out.println(cell);
				}
				try {
					strResponse = this.CallValidatePolicy(this.getStrProductMask(), this.getStrPolicyHolderUserInfo(),
							this.getStrInsureUserInfo(), this.getStrPremium(), this.getStrPolicyBeginDate(),
							this.getStrChannelId(), this.getStrExtraInfo(), this.getStrPolicyEndDate(),
							this.getStrSumInsured());
				} catch (ZhongAnOpenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				list.add(strResponse);
				alList.add(list.toArray(new String[list.size()]));
			}
			writer.writeAll(alList);
			writer.close();

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public static void getProperties() {
		Properties p = new Properties();

		InputStream is = ZhongAnEnvEnum.class.getResourceAsStream("dev.properties");
		try {
			p.load(is);
			Payment.strHsfUrl = p.getProperty("hsfurl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getStrProductMask() {
		return strProductMask;
	}

	public void setStrProductMask(String strProductMask) {
		this.strProductMask = strProductMask;
	}

	public String getStrPolicyHolderUserInfo() {
		return strPolicyHolderUserInfo;
	}

	public void setStrPolicyHolderUserInfo(String strPolicyHolderUserInfo) {
		this.strPolicyHolderUserInfo = strPolicyHolderUserInfo;
	}

	public String getStrInsureUserInfo() {
		return strInsureUserInfo;
	}

	public void setStrInsureUserInfo(String strInsureUserInfo) {
		this.strInsureUserInfo = strInsureUserInfo;
	}

	public String getStrPremium() {
		return strPremium;
	}

	public void setStrPremium(String strPremium) {
		this.strPremium = strPremium;
	}

	public String getStrPolicyBeginDate() {
		return strPolicyBeginDate;
	}

	public void setStrPolicyBeginDate(String strPolicyBeginDate) {
		this.strPolicyBeginDate = strPolicyBeginDate;
	}

	public String getStrChannelId() {
		return strChannelId;
	}

	public void setStrChannelId(String strChannelId) {
		this.strChannelId = strChannelId;
	}

	public String getStrExtraInfo() {
		return strExtraInfo;
	}

	public void setStrExtraInfo(String strExtraInfo) {
		this.strExtraInfo = strExtraInfo;
	}

	public String getStrSumInsured() {
		return strSumInsured;
	}

	public void setStrsumInsured(String strSumInsured) {
		this.strSumInsured = strSumInsured;
	}

	public String getStrPolicyEndDate() {
		return strPolicyEndDate;
	}

	public void setStrPolicyEndDate(String strPolicyEndDate) {
		this.strPolicyEndDate = strPolicyEndDate;
	}

}
