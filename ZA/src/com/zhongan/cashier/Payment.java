package com.zhongan.cashier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.biz.commplan.dto.CommPlanRequest;
import com.zhongan.scorpoin.biz.commplan.sample.TestValidatePolicy;
import com.zhongan.scorpoin.biz.commplan.sample.ValidatePolicyFailPara;
import com.zhongan.scorpoin.biz.commplan.sample.ValidatePolicyFullPara;
import com.zhongan.scorpoin.biz.commplan.sample.ValidatePolicyPara;
import com.zhongan.scorpoin.common.ZhongAnApiClient;
import com.zhongan.scorpoin.common.ZhongAnEnvEnum;
import com.zhongan.scorpoin.common.ZhongAnOpenException;
import com.zhongan.test.ID.CertiGeneration;

import zahyHttpHelp.hyHttpHelp;


public class Payment {
	
	public String strTradeNo;
	public String strZaOrderNo;
	public static String strHsfUrl;
	public String strPremium;
	public String strPolicyBeginDate;
	public String strChannelId;
	public String strExtraInfo;
	public String strPolicyEndDate;
	public String strSumInsured;


	public static void getProperties(){
		Properties p = new Properties();
		
		InputStream is = ZhongAnEnvEnum.class.getResourceAsStream("dev.properties");
		try {
			p.load(is);
			Payment.strHsfUrl= p.getProperty("hsfurl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

	public static String PaymentPolicy(String merOrd, String payDtl, String cnfmPay )  {
		// String strResponse=null;
 
		String response_order = hyHttpHelp.hsfHttpPost(strHsfUrl+"addMerchantOrder", "com.zhongan.cashier.dto.AddCashierOrderRequest", merOrd.toString());
		//String response_order = hyHttpHelp.hsfHttpPost(hsfurl+"addMerchantOrder", "com.zhongan.cashier.dto.AddCashierOrderRequest", merOrd.toString());	
		System.out.println("merchant Order----: " + response_order);
		return response_order;
		
		

	}

	public static void DoCallPaymentParameter(List<ValidatePolicyFullPara> lt,List<ValidatePolicyFailPara> lt_out) throws ZhongAnOpenException {
		String result = null;

		for (int i = 1; i < lt.size(); i++) {
			CertiGeneration generator = new CertiGeneration();
			String idNo=generator.getRandomCertiCode(lt.get(i).getInsureSex(), lt.get(i).getInsureBirth());
			
			Properties p = new Properties();
	        try {
	            InputStream is = ZhongAnEnvEnum.class.getResourceAsStream("dev.properties");
	            p.load(is);
	            // DEV.setUrl(p.getProperty("url"));
	            } catch (FileNotFoundException e) {
	            //  Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            //  Auto-generated catch block
	            e.printStackTrace();
	        }
			
	        JSONObject merOrd = new JSONObject();
	    	JSONObject payDetail = new JSONObject();
	    	JSONObject cnfmPay = new JSONObject();
	    	
	        merOrd.put("outTradeNo", vpp.);// �ڰ��ṩ�Ĳ�ƷΨһ������
	    	merOrd.put("merchantCode", "1512000401");// Ͷ������Ϣ
	    	merOrd.put("amt", "312.12");// ��������Ϣ
	    	merOrd.put("expireTime", "60");//
	    	merOrd.put("payChannel", "wxpay");// �������ڣ���ʽyyyyMMddHHmmss
	    	merOrd.put("scrType", "1");// ����id ���ڰ��ṩ
	    	merOrd.put("notify_url", "http://openweb.uat.zhongan.com/api/cashierTestToolAction/merchantAccept.json");// Json��ʽ��ҵ����չ�ַ���
	    	merOrd.put("auto_test", "true");
	    	merOrd.put("orderType", "1");// ����id ���ڰ��ṩ
	    	merOrd.put("subject", "测试");// ����id ���ڰ��ṩ
	    	merOrd.put("body", "测试");
	    	
			System.out.println("Prepare the policyHolder and InsureUser for case " + i);
			JSONObject policyHolderUserInfo = new JSONObject();

			policyHolderUserInfo.put("policyHolderUserName", lt.get(i).getPolicyHolderUserName());// �ڰ��ṩ�Ĳ�ƷΨһ������
			policyHolderUserInfo.put("policyHolderCertiType", lt.get(i).getPolicyHolderCertiType());// Ͷ������Ϣ
			policyHolderUserInfo.put("policyHolderCertiNo", idNo);// ��������Ϣ
			policyHolderUserInfo.put("policyHolderPhone", lt.get(i).getPolicyHolderPhone());//
			policyHolderUserInfo.put("policyHolderBirth", lt.get(i).getPolicyHolderBirth());// �������ڣ���ʽyyyyMMddHHmmss
			policyHolderUserInfo.put("policyHolderEmail", lt.get(i).getPolicyHolderEmail());// ����id ���ڰ��ṩ
			policyHolderUserInfo.put("policyHolderSex", lt.get(i).getPolicyHolderSex());// Json��ʽ��ҵ����չ�ַ���
			
			JSONObject insureUser = new JSONObject();
			insureUser.put("insureUserName", lt.get(i).getInsureUserName());// �ڰ��ṩ�Ĳ�ƷΨһ������
			insureUser.put("insureCertiType", lt.get(i).getInsureCertiType());// Ͷ������Ϣ
			insureUser.put("insureCertiNo", idNo);// ��������Ϣ
			insureUser.put("insureBirth", lt.get(i).getInsureBirth());//
			insureUser.put("insurePhone", lt.get(i).getInsurePhone());// �������ڣ���ʽyyyyMMddHHmmss
			insureUser.put("insureRelation", lt.get(i).getInsureRelation());// ����id ���ڰ��ṩ
			insureUser.put("insureSex", lt.get(i).getInsureSex());
			
			JSONArray insureUserInfo = new JSONArray();
			insureUserInfo.add(insureUser);
			
			
			System.out.println(insureUserInfo.toJSONString());
			System.out.println(policyHolderUserInfo.toJSONString());
			
			result = TestValidatePolicy.CallValidatePolicy(lt.get(i).getProductMask(),policyHolderUserInfo.toJSONString(),
					insureUserInfo.toJSONString(),  lt.get(i).getPremium(), lt.get(i).getPolicyBeginDate(), lt.get(i).getChannelId(),
					lt.get(i).getExtraInfo(), lt.get(i).getPolicyEndDate(), lt.get(i).getSumInsured());

			System.out.println("taoqilei" + result + "\n");
			System.out.println("taoqilei -------");

			String rex = "[{}]+";
			String[] str = result.split(rex);
			System.out.println(str[1]);
			System.out.println("before split "+lt.get(i).getBizContent());
			System.out.println(lt.get(i).getBizContent().split(rex));
			// 比较 lt 中 第26个 bizContent 和 response 中的 bizContent
			String[] str2 = lt.get(i).getBizContent().split(rex);
			if (str[1].toString().equals(str2[1].toString()))
			{
				System.out.println("ok -------");
			}
			else
			{
				System.out.println("fail -------");
				ValidatePolicyFailPara vpfp = new ValidatePolicyFailPara();
					
				CommPlanRequest request = new CommPlanRequest("zhongan.open.common.validatePolicy");
				
				JSONObject params = new JSONObject();
				params.put("productMask", lt.get(i).getProductMask());// �ڰ��ṩ�Ĳ�ƷΨһ������
				params.put("policyHolderUserInfo", policyHolderUserInfo.toJSONString());// Ͷ������Ϣ
				params.put("insureUserInfo", insureUserInfo.toJSONString());// ��������Ϣ
				params.put("premium", lt.get(i).getPremium());//
				params.put("policyBeginDate", lt.get(i).getPolicyBeginDate());// �������ڣ���ʽyyyyMMddHHmmss
				params.put("channelId", lt.get(i).getChannelId());// ����id ���ڰ��ṩ
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
	public static void DoCallValidatePolicy(List<ValidatePolicyPara> lt,List<ValidatePolicyFailPara> lt_out) throws ZhongAnOpenException {
		String result = null;

		for (int i = 1; i < lt.size(); i++) {
			
			
					
			result = TestValidatePolicy.CallValidatePolicy(lt.get(i).getProductMask(),  lt.get(i).getPolicyHolderUserInfo(),
					lt.get(i).getInsureUserInfo(),lt.get(i).getPremium(), lt.get(i).getPolicyBeginDate(), lt.get(i).getChannelId(),
					lt.get(i).getExtraInfo(), lt.get(i).getPolicyEndDate(), lt.get(i).getSumInsured());

			System.out.println("taoqilei" + result + "\n");
			System.out.println("taoqilei -------");

			String rex = "[{}]+";
			String[] str = result.split(rex);
			System.out.println(str[1]);
			System.out.println("before split "+lt.get(i).getBizContent());
			System.out.println(lt.get(i).getBizContent().split(rex));
			// 比较 lt 中 第26个 bizContent 和 response 中的 bizContent
			String[] str2 = lt.get(i).getBizContent().split(rex);
			if (str[1].toString().equals(str2[1].toString()))
			{
				System.out.println("ok -------");
			}
			else
			{
				System.out.println("fail -------");
				ValidatePolicyFailPara vpfp = new ValidatePolicyFailPara();
					
				CommPlanRequest request = new CommPlanRequest("zhongan.open.common.validatePolicy");
				
				JSONObject params = new JSONObject();
				params.put("productMask", lt.get(i).getProductMask());// �ڰ��ṩ�Ĳ�ƷΨһ������
				params.put("policyHolderUserInfo", lt.get(i).getPolicyHolderUserInfo());// Ͷ������Ϣ
				params.put("insureUserInfo", lt.get(i).getInsureUserInfo());// ��������Ϣ
				params.put("premium", lt.get(i).getPremium());//
				params.put("policyBeginDate", lt.get(i).getPolicyBeginDate());// �������ڣ���ʽyyyyMMddHHmmss
				params.put("channelId", lt.get(i).getChannelId());// ����id ���ڰ��ṩ
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
	
	public String getStrTradeNo() {
		return strTradeNo;
	}




	public void setStrTradeNo(String strTradeNo) {
		this.strTradeNo = strTradeNo;
	}




	public String getStrZaOrderNo() {
		return strZaOrderNo;
	}




	public void setStrZaOrderNo(String strZaOrderNo) {
		this.strZaOrderNo = strZaOrderNo;
	}




	public String getStrHsfUrl() {
		return strHsfUrl;
	}




	public void setStrHsfUrl(String strHsfUrl) {
		this.strHsfUrl = strHsfUrl;
	}




	public void setStrSumInsured(String strSumInsured) {
		this.strSumInsured = strSumInsured;
	}


}
