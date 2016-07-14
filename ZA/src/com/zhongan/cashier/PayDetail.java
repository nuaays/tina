package com.zhongan.cashier;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.biz.common.CommonResponse;

import zahyHttpHelp.*;

public class PayDetail {

	public static void main (String args[]) {
	
	JSONObject merOrd = new JSONObject();
	JSONObject payDetail = new JSONObject();
	JSONObject cnfmPay = new JSONObject();

	payDetail.put("outTradeNo", "dcr_0719");// �ڰ��ṩ�Ĳ�ƷΨһ������
	payDetail.put("zaOrderNo", "90151200040120160712105715004492");// Ͷ������Ϣ
	payDetail.put("payChannelUserNo", "DIDI015");// ��������Ϣ
	payDetail.put("payChannelId", "2");//
	payDetail.put("payAccountId", "90001");// �������ڣ���ʽyyyyMMddHHmmss
	payDetail.put("payAmount", "58");//
	payDetail.put("payRequstInfo", "{\"out_trade_no\": \"dcr_0719\"}");
	
	cnfmPay.put("zaOrderNo", "90151200040120160712105715004492");
	cnfmPay.put("payTradeNo", "dcr_0714");// ����id ���ڰ��ṩ
	cnfmPay.put("payChannelUserNo", "DIDI015");// ����id ���ڰ��ṩ
	cnfmPay.put("payChannel", "wxpay");
	
	merOrd.put("outTradeNo", "dcr_0719");// �ڰ��ṩ�Ĳ�ƷΨһ������
	merOrd.put("merchantCode", "1512000401");// Ͷ������Ϣ
	merOrd.put("amt", "312.12");// ��������Ϣ
	merOrd.put("expireTime", "60");//
	merOrd.put("payChannel", "wxpay");// �������ڣ���ʽyyyyMMddHHmmss
	merOrd.put("scrType", "1");// ����id ���ڰ��ṩ
	merOrd.put("notify_url", "http://openweb.uat.zhongan.com/api/cashierTestToolAction/merchantAccept.json");// Json��ʽ��ҵ����չ�ַ���
	merOrd.put("auto_test", "true");
	merOrd.put("orderType", "1");// ����id ���ڰ��ṩ
	merOrd.put("subject", "测试");// ����id ���ڰ��ṩ
	merOrd.put("body", "测试");// ����id ���ڰ��ṩ

	System.out.println("-----------------");
	System.out.println(merOrd.toJSONString());
	System.out.println("-----------------");
	
	//String data ="{\"outTradeNo\":\"1467184878775\",\"merchantCode\":\"1512000401\",\"amt\":\"\",\"expireTime\":\"60\",\"scrType\":\"1\",\"payChannel\":\"wxpay\",\"orderType\":\"1\",\"subject\":\"自动化测试\",\"body\":\"自动化测试\",\"showUrl\":\"https://www.zhongan.com\",\"notifyUrl\":\"http://openweb.daily.zhongan.com/api/cashierTestToolAction/merchantAccept.json\",\"errorNotifyUrl\":\"\",\"returnUrl\":\"https://www.zhongan.com\",\"backUrl\":\"https://www.zhongan.com\",\"notifyInfo\":{\"产品明证\":\"abcxyz\",\"保障期限\":\"XXXX\",\"保费\":\"9.00\"},\"orderInfo\":{\"产品明证\":\"abcxyz\",\"保障期限\":\"XXXX\",\"保费\":\"9.00\"}";

			
	/*String hsfurl ="http://10.253.27.175:8086/com.zhongan.cashier.service.CashierOrderService:1.0.0/"; 
	
	String response_order = hyHttpHelp.hsfHttpPost(hsfurl+"addMerchantOrder", "com.zhongan.cashier.dto.AddCashierOrderRequest", merOrd.toString());	
	System.out.println("merchant Order----: " + response_order);*/
	

/*	String hsfurl ="http://10.253.27.175:8086/com.zhongan.cashier.service.CashierOrderService:1.0.0/"; 
	
	String response_pay = hyHttpHelp.hsfHttpPost(hsfurl+"addPayDetail", "com.zhongan.cashier.dto.AddPayDetailRequest", payDetail.toString());	
	System.out.println("Pay Detail ----: " + response_pay);
	*/
	String hsfurl ="http://10.253.27.175:8086/com.zhongan.cashier.service.CashierOrderService:1.0.0/"; 
		
	 String response_cnfm = hyHttpHelp.hsfHttpPost(hsfurl+"confirmSpecialOrderPay", "com.zhongan.cashier.dto.ConfirmOrderPayRequest", cnfmPay.toString());	
	System.out.println("Confirm Pay ----: " + response_cnfm);
	}
	
}
