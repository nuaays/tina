package com.zhongan.scorpoin.biz.commplan.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.common.ZhongAnEnvEnum;
import com.zhongan.test.ID.CertiGeneration;

import org.apache.log4j.Logger;

public class NewTest {

	static Logger logger = Logger.getLogger(NewTest.class);
	// 得到系统日期
	static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	static String str_df = df.format(new Date()).toString();

	public static List<ValidatePolicyFullPara> lt = new ArrayList<ValidatePolicyFullPara>();

	public static List<ValidatePolicyFailPara> lt_out = new ArrayList<ValidatePolicyFailPara>();
	public static List<PolicyStatus> query_policy;

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

			ValidatePolicyFullPara vpp = new ValidatePolicyFullPara();

			// 添加自动生成的tradeno 到vpp
			int rowNum = row.getRowNum();
			String str_rowNum = "0000" + rowNum;

			String tradeNo = str_df + str_rowNum;
			vpp.setTradeNo(tradeNo);

			String cellContent = null;

			for (Cell cell : row) {

				cellContent = NewTest.getStringVal(cell);
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
					vpp.setRemark(cellContent);
					break;

				// BizContent
				case 26:
					vpp.setBizContent(cellContent);
					break;
				case 27:
					vpp.setProductName(cellContent);
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

		// 写入表头
		Row row = sheet1.createRow(0);
		String[] headers = new String[] { "Case NO", "报文", "Expected Result", "Actual Result", "备注" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			String text = new HSSFRichTextString(headers[i]).toString();
			cell.setCellValue(text);
		}

		// 循环写入行数据
		for (int i = 0; i < lt_out.size(); i++) {
			Row rows = (Row) sheet1.createRow(i + 1);
			// 循环写入列数据
			for (int j = 0; j < 5; j++) {
				Cell cell = rows.createCell(j);
				switch (j) {
				case 0:
					cell.setCellValue(lt_out.get(i).getCaseNo().toString());
					break;
				case 1:
					cell.setCellValue(lt_out.get(i).getBizContent().toString());
					break;
				case 2:
					cell.setCellValue(lt_out.get(i).getExpectResult().toString());
					break;
				case 3:
					cell.setCellValue(lt_out.get(i).getActualResult().toString());
					break;
				case 4:
					if (lt_out.get(i).getRemark() != null) {
						cell.setCellValue(lt_out.get(i).getRemark().toString());
					}
					break;
				default:
					break;

				}
				// sheet1.autoSizeColumn(j);

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

	public static void setLogPath(String logpath) {
		Properties p = new Properties();

		InputStream is = ZhongAnEnvEnum.class.getResourceAsStream("log4j.properties");
		try {
			p.load(is);
			System.out.println(p.getProperty("log4j.appender.R.file").toString());
			// p.setProperty("log4j.appender.R.file", logpath+":Test.log");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		String readpath = null;
		String writepath = null;

		// 走bat传参数方式
		if (args.length > 0) {
			readpath = args[0].toString();
			writepath = args[1].toString();
		} else {
			readpath = "D:\\test\\tina\\ZA\\TestCase.xlsx";
			writepath = "E:\\ZAApp";
		}

		try {
			NewTest.read(readpath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 1; i < lt.size(); i++) {

			Boolean test_Result = false;
			System.out.println("Starting running for case" + i);
			logger.info("Starting running for case" + i);
			if (lt.get(i).getInsureCertiNo() == null) {
				// 身份证号自动生成
				CertiGeneration generator = new CertiGeneration();
				String idNo = generator.getRandomCertiCode(lt.get(i).getInsureSex(), lt.get(i).getInsureBirth());
				lt.get(i).setInsureCertiNo(idNo);

				if (lt.get(i).getInsureRelation().equals("本人")) {
					lt.get(i).setPolicyHolderCertiNo(idNo);
				} else if (lt.get(i).getPolicyHolderCertiNo() == null) {
					String idNo2 = generator.getRandomCertiCode(lt.get(i).getPolicyHolderSex(),
							lt.get(i).getPolicyHolderBirth());
					lt.get(i).setPolicyHolderCertiNo(idNo2);
				}
			}
			// 核保

			System.out.println("Prepare the policyHolder and InsureUser for case " + i);
			logger.info("Prepare the policyHolder and InsureUser for case " + i);
			JSONObject policyHolderUserInfo = new JSONObject();

			policyHolderUserInfo.put("policyHolderUserName", lt.get(i).getPolicyHolderUserName());
			policyHolderUserInfo.put("policyHolderCertiType", lt.get(i).getPolicyHolderCertiType());
			policyHolderUserInfo.put("policyHolderCertiNo", lt.get(i).getPolicyHolderCertiNo());
			policyHolderUserInfo.put("policyHolderPhone", lt.get(i).getPolicyHolderPhone());
			policyHolderUserInfo.put("policyHolderBirth", lt.get(i).getPolicyHolderBirth());
			policyHolderUserInfo.put("policyHolderEmail", lt.get(i).getPolicyHolderEmail());
			policyHolderUserInfo.put("policyHolderSex", lt.get(i).getPolicyHolderSex());

			JSONObject insureUser = new JSONObject();
			insureUser.put("insureUserName", lt.get(i).getInsureUserName());
			insureUser.put("insureCertiType", lt.get(i).getInsureCertiType());
			insureUser.put("insureCertiNo", lt.get(i).getInsureCertiNo());
			insureUser.put("insureBirth", lt.get(i).getInsureBirth());
			insureUser.put("insurePhone", lt.get(i).getInsurePhone());
			insureUser.put("insureRelation", lt.get(i).getInsureRelation());
			insureUser.put("insureSex", lt.get(i).getInsureSex());

			JSONArray insureUserInfo = new JSONArray();
			insureUserInfo.add(insureUser);

			JSONObject validate_INFO = new JSONObject();

			validate_INFO.put("productMask", lt.get(i).getProductMask());
			validate_INFO.put("policyHolderUserInfo", policyHolderUserInfo);
			validate_INFO.put("insureUserInfo", insureUserInfo);
			validate_INFO.put("premium", lt.get(i).getPremium());
			validate_INFO.put("policyBeginDate", lt.get(i).getPolicyBeginDate());
			validate_INFO.put("channelId", lt.get(i).getChannelId());
			validate_INFO.put("extraInfo", lt.get(i).getExtraInfo());
			validate_INFO.put("policyEndDate", lt.get(i).getPolicyEndDate());
			validate_INFO.put("sumInsured", lt.get(i).getSumInsured());

			String validate_BW = validate_INFO.toString();
			String str = "val";
			logger.info("调用核保所传入的字符串是： " + validate_BW);
			String response_validate = NewCall.CallOpenRequest(validate_INFO, str);

			System.out.println("核保 接口调用 后的方法返回的bizContent是：" + response_validate);
			logger.info("核保 接口调用 后的方法返回的bizContent是：" + response_validate);
			List<AddPolicyReturnParameter> return_para = JSON.parseArray(response_validate,
					AddPolicyReturnParameter.class);
			List<AddPolicyReturnParameter> exp_excel = JSON.parseArray("[" + lt.get(i).getBizContent() + "]",
					AddPolicyReturnParameter.class);

			// 核保成功与否标志
			String strValFlg = return_para.get(0).getIsSuccess();
			// Excel 中预期核保成功与否标志
			String strExpect_valFlg = exp_excel.get(0).getIsSuccess();

			if (strValFlg.equals("N")) {
				logger.error("case" + i + "核保失败");
				System.out.println("核保失败");

			}

			// 期望与实际不符的时候需要将 actual 和expected 结果 输出到excel中
			if (strValFlg.equals(strExpect_valFlg)) {
				logger.info("case" + i + "运行核保接口，实际运行结果与期望结果一致");
				System.out.println("case" + i + "运行核保接口，实际运行结果与期望结果一致");
			} else {
				System.out.println("case" + i + "运行核保接口，实际运行结果与期望结果不一致， testcase 运行失败！！！！");
				logger.error("case" + i + "运行核保接口，实际运行结果与期望结果不一致， testcase 运行失败！！！！");
				ValidatePolicyFailPara vpfp = new ValidatePolicyFailPara();
				vpfp.setCaseNo("case " + i);
				vpfp.setBizContent(validate_BW.toString());
				vpfp.setActualResult(response_validate);
				vpfp.setExpectResult("[" + lt.get(i).getBizContent() + "]");
				vpfp.setRemark(lt.get(i).getRemark());
				lt_out.add(vpfp);

				try {
					NewTest.write(writepath + File.separator + "TestResult_" + str_df + ".xlsx");
				} catch (IOException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// 当且仅当case 实际运行核保通过 且 case 期望核保通过时，才进行支付和投保
			if ((strValFlg.equals("Y")) & (strExpect_valFlg.equals("Y"))) {
				// 支付

				JSONObject merOrd = new JSONObject();
				JSONObject payDetail = new JSONObject();
				JSONObject cnfmPay = new JSONObject();

				payDetail.put("outTradeNo", lt.get(i).getTradeNo());
				payDetail.put("payChannelUserNo", "DIDI015");
				payDetail.put("payChannelId", "2");
				payDetail.put("payAccountId", "90001");
				payDetail.put("payAmount", "58");//
				payDetail.put("payRequstInfo", "{\"out_trade_no\": " + lt.get(i).getTradeNo() + "}");

				cnfmPay.put("payTradeNo", lt.get(i).getTradeNo());
				cnfmPay.put("payChannelUserNo", "DIDI015");
				cnfmPay.put("payChannel", "wxpay");

				merOrd.put("outTradeNo", lt.get(i).getTradeNo());
				merOrd.put("merchantCode", "1512000401");
				merOrd.put("amt", "312.12");
				merOrd.put("expireTime", "60");
				merOrd.put("payChannel", "wxpay");
				merOrd.put("scrType", "1");
				merOrd.put("notify_url",
						"http://openweb.uat.zhongan.com/api/cashierTestToolAction/merchantAccept.json");// Json��ʽ��ҵ����չ�ַ���
				merOrd.put("auto_test", "true");
				merOrd.put("orderType", "1");
				merOrd.put("subject", "测试");
				merOrd.put("body", "测试");

				String strZaOrdNo = NewCall.PaymentPolicy(merOrd, payDetail, cnfmPay, i);

				// 投保
				if (strZaOrdNo != null) {
					str = "add";
					validate_INFO.put("channelOrderNo", lt.get(i).getTradeNo());
					validate_INFO.put("zaOrderNo", strZaOrdNo);
					logger.info("投保接口调用时传入的报文是 : " + validate_INFO.toString());
					String response_add = NewCall.CallOpenRequest(validate_INFO, str);
					System.out.println("投保接口调用后返回的bizcont是 : " + response_add.toString());
					logger.info("投保接口调用后返回的bizcont是 : " + response_add.toString());

					List<AddPolicyReturnParameter> add = JSON.parseArray(response_add, AddPolicyReturnParameter.class);
					String strPolicyNo = add.get(0).getPolicyNo();
					String addRtnFlg = add.get(0).getIsSuccess();
					if (addRtnFlg.equals("N")) {
						System.out.println("case" + i + "投保失败");
						logger.error("case" + i + "投保失败");
					} else {
						System.out.println("case" + i + "投保成功");
						logger.info("case" + i + "投保成功");
					}

					// 查询保单
					if (addRtnFlg.equals("Y")) {
						Thread.sleep(3000);
						str = "query";
						JSONObject query = new JSONObject();
						query.put("policyNo", strPolicyNo);
						logger.info("查询保单接口是传入的报文是 : " + query.toString());
						String response_query = NewCall.CallOpenRequest(query, str);
						System.out.println("查询保单接口调用后返回的bizcont是 : " + response_query.toString());
						logger.info("查询保单接口调用后返回的bizcont是 : " + response_query.toString());
						query_policy = JSON.parseArray(response_query, PolicyStatus.class);
						if (query_policy.get(0).getIsSuccess().equals("Y")) {
							test_Result = ResultVerfication(lt, query_policy, i);
						} else {
							System.out.println("未查询到相应保单！！");
							logger.error("未查询到相应保单！！");
						}

					}

					if (test_Result) {
						System.out.println("case" + i + "运行结束，查询保单成功！！！");
						logger.info("case" + i + "运行结束，查询保单成功！！！");
					} else {
						System.out.println("case" + i + "运行结束，查询保单失败！！！");
						logger.error("case" + i + "运行结束，查询保单失败！！！");
					}

					if (query_policy.get(0).getIsSuccess().equals("N") || (!test_Result)) {

						ValidatePolicyFailPara vpfp = new ValidatePolicyFailPara();
						vpfp.setCaseNo("case " + i);
						vpfp.setBizContent(validate_BW.toString());
						vpfp.setActualResult(query_policy.toString());
						vpfp.setExpectResult("查询保单成功");
						vpfp.setRemark("查询保单失败或者查询结果与输入值不符！！！");
						lt_out.add(vpfp);

						try {
							NewTest.write(writepath + File.separator + "TestResult_" + str_df + ".xlsx");
						} catch (IOException e) { // TODO Auto-generated catch
													// block
							e.printStackTrace();
						}
					}
				}
			}

		}

	}

	public static Boolean ResultVerfication(List<ValidatePolicyFullPara> lt, List<PolicyStatus> query_policy, int j) {
		System.out.println("1" + lt.get(j).getInsureUserName());
		System.out.println("2" + (query_policy).get(0).getInsureUserName());
		System.out.println("3" + lt.get(j).getPolicyHolderUserName());
		System.out.println("4" + (query_policy).get(0).getPolicyHolderUserName());
		System.out.println("5" + lt.get(j).getPremium());
		System.out.println("6" + (query_policy).get(0).getPremium());
		System.out.println("7" + lt.get(j).getSumInsured());
		System.out.println("8" + (query_policy).get(0).getSumInsured());
		System.out.println("9" + lt.get(j).getPolicyBeginDate());
		System.out.println("10" + (query_policy).get(0).getPolicyBeginDate());
		System.out.println("11" + lt.get(j).getPolicyEndDate());
		System.out.println("12" + (query_policy).get(0).getPolicyEndDate());
		System.out.println("13" + (query_policy).get(0).getPolicyStatus());
		System.out.println("14" + lt.get(j).getProductName());
		System.out.println("15" + (query_policy).get(0).getProductName());
		System.out.println("Excel中拼凑的保单信息为： " + lt.get(j).toString());
		System.out.println("查询保单返回的保单信息为： " + (query_policy).get(0).toString());
		logger.info("Excel中拼凑的保单信息为： " + lt.get(j).toString());
		logger.info("查询保单返回的保单信息为： " + (query_policy).get(0).toString());
		float excel_sum = Float.parseFloat(lt.get(j).getSumInsured());
		float result_sum = Float.parseFloat((query_policy).get(0).getSumInsured());

		float excel_premium = Float.parseFloat(lt.get(j).getPremium());
		float result_premium = Float.parseFloat((query_policy).get(0).getPremium());

		return (lt.get(j).getInsureUserName()).equals((query_policy).get(0).getInsureUserName())
				& (lt.get(j).getPolicyHolderUserName()).equals((query_policy).get(0).getPolicyHolderUserName())
				& (excel_premium == result_premium) & (excel_sum == result_sum)
				& (lt.get(j).getPolicyBeginDate()).equals((query_policy).get(0).getPolicyBeginDate())
				& (lt.get(j).getPolicyEndDate()).equals((query_policy).get(0).getPolicyEndDate())
				& ((query_policy).get(0).getPolicyStatus()).equals("核保通过")
				& (lt.get(j).getProductName()).equals((query_policy).get(0).getProductName());

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
