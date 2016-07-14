package com.zhongan.scorpoin.biz.commplan.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zhongan.scorpoin.common.ZhongAnOpenException;

public class TestValidatePolicyExcel {

	public static List<ValidatePolicyPara> lt = new ArrayList<ValidatePolicyPara>();

	public static List<ValidatePolicyFailPara> lt_out = new ArrayList<ValidatePolicyFailPara>();

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

			ValidatePolicyPara vpp = new ValidatePolicyPara();

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
					vpp.setInsureUserInfo(cellContent);
					break;
				case 4:
					vpp.setPolicyHolderUserInfo(cellContent);
					break;
				case 5:
					vpp.setPremium(cellContent);
					break;
				case 6:
					vpp.setPolicyBeginDate(cellContent);
					break;
				case 7:
					vpp.setExtraInfo(cellContent);
					break;
				case 8:
					vpp.setPolicyEndDate(cellContent);
					break;
				case 9:
					vpp.setSumInsured(cellContent);
					break;
				// 备注
				case 10:
					break;

				// BizContent
				case 11:
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
			TestValidatePolicyExcel.read("D:\\Work\\JavaProject\\ZA0709\\ZA" + File.separator + "TestCase2.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// do call ValidatePolicy
		TestValidatePolicy.DoCallValidatePolicy(lt, lt_out);
		// write

		try {
			TestValidatePolicyExcel.write("E:" + File.separator + "TestResult.xlsx");
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
