package com.zhongan.cashier;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TradeNoGeneration {

	// out_trade_no: 系统日期 + '_' + 自动递增（0001）
	
	//得到系统日期
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	String str_df = df.format(new Date()).toString();
	int i=0;
	String tradNo;
	
	public  String TradeNoGenerated()
	{
		
		StringBuffer sb = new StringBuffer("000");//初始化StringBuffer
		for (i = 0; i < 1000; i++) {
		sb.append(i);//递增字符+i
		}
		
		tradNo = str_df+sb;
		return tradNo;
		
	}
}
