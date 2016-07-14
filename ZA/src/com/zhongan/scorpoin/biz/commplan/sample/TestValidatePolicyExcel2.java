package com.zhongan.scorpoin.biz.commplan.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongan.cashier.ConfirmPayReturn;
import com.zhongan.cashier.PayDtlReturn;
import com.zhongan.scorpoin.common.ZhongAnOpenException;
import com.zhongan.test.ID.CertiGeneration;

public class TestValidatePolicyExcel2 {
	
    //得到系统日期
	static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	static String str_df = df.format(new Date()).toString();
	
	public static List<ValidatePolicyFullPara> lt = new ArrayList<ValidatePolicyFullPara>();

	public static List<ValidatePolicyFailPara> lt_out = new ArrayList<ValidatePolicyFailPara>();
	//public static List<AddPolicyReturnParameter> APRP =  new ArrayList<AddPolicyReturnParameter>();
	
	//public static List<PayDtlReturn> lt_PDR = new ArrayList<PayDtlReturn>();

	public static void read(String filePath) throws IOException {
		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
		InputStream stream = new FileInputStream(filePath);
		Workbook wb = null;

		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook(stream);
		} else {
			System.out.println("您输入的excel格式不正确");
		}

		Sheet sheet1 = wb.getSheetAt(0);

		for (Row row : sheet1) {
			System.out.println(row.getRowNum() + "  ");

			ValidatePolicyFullPara vpp = new ValidatePolicyFullPara();
			
			//添加自动生成的tradeno 到vpp
			int rowNum = row.getRowNum();
			String str_rowNum = "0000"+rowNum;
			
			String tradeNo = str_df+str_rowNum;
			vpp.setTradeNo(tradeNo);
			System.out.println("TEST !!!!!" + tradeNo);

			String cellContent = null;

			for (Cell cell : row) {
				System.out.println(cell.getColumnIndex() + "  ");
				System.out.print(TestValidatePolicyExcel.getStringVal(cell) + "  ");
				System.out.println();
				cellContent = TestValidatePolicyExcel.getStringVal(cell);
				switch (cell.getColumnIndex()) {
				case 1:
					vpp.setProductMask(cellContent);
					break;
				case 2:
					vpp.setChannelId(cellContent);
					break;
				case 3:
					vpp.setInsureUserName(cellContent);
					break;
				case 4:
					vpp.setInsureCertiType(cellContent);
					break;
				case 5:
					vpp.setInsureCertiNo(cellContent);
					break;
				case 6:
					vpp.setInsureSex(cellContent);
					break;
				case 7:
					vpp.setInsureBirth(cellContent);
					break;
				case 8:
					vpp.setInsureEmail(cellContent);
					break;
				case 9:
					vpp.setInsurePhone(cellContent);
					break;
				case 10:
					vpp.setInsureAddr(cellContent);
					break;
				case 11:
					vpp.setInsureRelation(cellContent);
					break;
				case 12:
					vpp.setPolicyHolderUserName(cellContent);
					break;
				case 13:
					vpp.setPolicyHolderCertiType(cellContent);
					break;
				case 14:
					vpp.setPolicyHolderCertiNo(cellContent);
					break;
				case 15:
					vpp.setPolicyHolderSex(cellContent);
					break;
				case 16:
					vpp.setPolicyHolderBirth(cellContent);
					break;
				case 17:
					vpp.setPolicyHolderEmail(cellContent);
					break;
				case 18:
					vpp.setPolicyHolderPhone(cellContent);
					break;
				case 19:
					vpp.setPolicyHolderAddr(cellContent);
					break;
				case 20:
					vpp.setPremium(cellContent);
					break;
				case 21:
					vpp.setPolicyBeginDate(cellContent);
					break;
				case 22:
					vpp.setExtraInfo(cellContent);
					break;
				case 23:
					vpp.setPolicyEndDate(cellContent);
					break;
				case 24:
					vpp.setSumInsured(cellContent);
					break;
				// 备注
				case 25:
					break;

				// BizContent
				case 26:
					vpp.setBizContent(cellContent);
					break;

				default:
					break;

				}
			}
			
			lt.add(vpp);
			System.out.println();
		}


	}

	public static boolean write(String outPath) throws Exception {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
		System.out.println(fileType);
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			System.out.println("您的文档格式不正确！");
			return false;
		}
		// 创建sheet对象
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");

		//写入表头
		Row  row = sheet1.createRow(0);
		String[] headers = new String[] {"Case NO", "报文", "Expected Result", "Actual Result"}; 
		for (int i = 0; i < headers.length; i++) {  
			Cell cell = row.createCell(i);  
			String text = new HSSFRichTextString(headers[i]).toString();  
			cell.setCellValue(text);  
		}
		
			
		// 循环写入行数据
		for (int i = 0; i < lt_out.size(); i++) {
			Row rows = (Row) sheet1.createRow(i+1);
			// 循环写入列数据
			for (int j = 0; j < 4; j++) {
				Cell cell = rows.createCell(j);
				switch (j) {
				case 0:
					cell.setCellValue(lt_out.get(i).getCaseNo().toString());
					break;
				case 1:
					cell.setCellValue(lt_out.get(i).getBizContent().toString());
					break;
				case 2:
					cell.setCellValue(lt_out.get(i).getActualResult().toString());
					break;
				case 3:
					cell.setCellValue(lt_out.get(i).getExpectResult().toString());
					break;
				default:
					break;

				}
				//sheet1.autoSizeColumn(j);

			}
			
			
		}
		// 创建文件流
		OutputStream stream = new FileOutputStream(outPath);
		// 写入数据
		wb.write(stream);
		// 关闭文件流
		stream.close();
		wb.close();
		return true;
	}

	public static void main(String[] args) throws Exception {

		try {
			TestValidatePolicyExcel2.read("D:\\Work\\JavaProject\\ZA0709\\ZA" + File.separator + "TestCase.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// do call ValidatePolicy
		System.out.println("Prepare to call Validate policy full");
		
		for (int i = 1; i < lt.size(); i++) 
		{
			Boolean addFlg =false;
			Boolean validateFlg =false;
			CertiGeneration generator = new CertiGeneration();
			String idNo=generator.getRandomCertiCode(lt.get(i).getInsureSex(), lt.get(i).getInsureBirth());
			
			// 核保
			
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
			
	        JSONObject params = new JSONObject();

			params.put("productMask", lt.get(i).getProductMask());// �ڰ��ṩ�Ĳ�ƷΨһ������
			params.put("policyHolderUserInfo", policyHolderUserInfo);// Ͷ������Ϣ
			params.put("insureUserInfo", insureUserInfo);// ��������Ϣ
			params.put("premium", lt.get(i).getPremium());//
			params.put("policyBeginDate", lt.get(i).getPolicyBeginDate());// �������ڣ���ʽyyyyMMddHHmmss
			params.put("channelId", lt.get(i).getChannelId());// ����id ���ڰ��ṩ
			params.put("extraInfo", lt.get(i).getExtraInfo());// Json��ʽ��ҵ����չ�ַ���
			params.put("policyEndDate", lt.get(i).getPolicyEndDate());
			params.put("sumInsured", lt.get(i).getSumInsured());
			
			System.out.println(insureUserInfo.toJSONString());
			System.out.println(policyHolderUserInfo.toJSONString());
			//CallOpenRequest(JSONObject params,Boolean add, Boolean validate)
			validateFlg= true;
			String response_validate=TestValidatePolicy.CallOpenRequest(params,addFlg,validateFlg);
			validateFlg= false;
			System.out.println("Validate 接口调用 后的方法返回值是："+response_validate);
		    
		//  支付
			
			JSONObject merOrd = new JSONObject();
			JSONObject payDetail = new JSONObject();
			JSONObject cnfmPay = new JSONObject();

			payDetail.put("outTradeNo", lt.get(i).getTradeNo());// �ڰ��ṩ�Ĳ�ƷΨһ������
			//payDetail.put("zaOrderNo", "90151200040120160712105715004492");// Ͷ������Ϣ
			payDetail.put("payChannelUserNo", "DIDI015");// ��������Ϣ
			payDetail.put("payChannelId", "2");//
			payDetail.put("payAccountId", "90001");// �������ڣ���ʽyyyyMMddHHmmss
			payDetail.put("payAmount", "58");//
			payDetail.put("payRequstInfo", "{\"out_trade_no\": "+lt.get(i).getTradeNo()+"}");
			
			//cnfmPay.put("zaOrderNo", "90151200040120160712105715004492");
			cnfmPay.put("payTradeNo", lt.get(i).getTradeNo());// ����id ���ڰ��ṩ
			cnfmPay.put("payChannelUserNo", "DIDI015");// ����id ���ڰ��ṩ
			cnfmPay.put("payChannel", "wxpay");
			
			merOrd.put("outTradeNo", lt.get(i).getTradeNo());// �ڰ��ṩ�Ĳ�ƷΨһ������
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
			
			
			String strZaOrdNo = TestValidatePolicy.PaymentPolicy(merOrd, payDetail, cnfmPay,i);
			//System.out.println("投保结果：" + pay_result);
			
		//  投保
			
			addFlg=true;
			params.put("channelOrderNo", lt.get(i).getTradeNo());
			params.put("zaOrderNo", strZaOrdNo);
			String response_add=TestValidatePolicy.CallOpenRequest(params,addFlg,validateFlg);
			

		}
		
		// write

		try {
			
			TestValidatePolicyExcel2.write("E:" + File.separator + "TestResult_"+str_df+".xlsx");
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getStringVal(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() ? "TRUE" : "False";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return null;

		}

	}
	
	
}
