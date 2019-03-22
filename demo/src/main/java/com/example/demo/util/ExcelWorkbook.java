package com.example.demo.util;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderFormatting;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelWorkbook {

	private static final Logger log = LoggerFactory.getLogger(ExcelWorkbook.class);
	
	//경로 설정 필요한 경우임
//	private final String resultPath = "";

	//기존에 있는 경우에, 클로즈는 endOfExcel에서 실행
	public Workbook getWorkBook(String filePath) {
		try {
			return WorkbookFactory.create(new FileInputStream(filePath));
		} catch (EncryptedDocumentException | IOException e) {
			logErrorMsg(e.getMessage());
		}
		return null;
	};

	//파일 새로 생성시에, 클로즈는 endOfexcel에서 실행
	public Workbook createWorkBook() {
		try {
			return WorkbookFactory.create(true);
		} catch (IOException e) {
			logErrorMsg(e.getMessage());
		}
		return null;
	};

	public String endOfExcel(Workbook wb, String outPath){
		//파일 생성될 경로 지정 및 쓰기 완료
//		String outPath = resultPath+File.separator+"abcde.xlsx";
		try (FileOutputStream fileout = new FileOutputStream(outPath)){
			wb.write(fileout);
			/***********************테스트 후에 이거 꼭 끄기*************************/
//			Runtime.getRuntime().exec("cmd.exe  /C" + outPath);
		}catch (IOException e) {
			logErrorMsg(e.getMessage());
		}
		return outPath;
	}
	
	
	//,를 구분자로 넣음
	public Workbook write(Workbook wb, List<Map<String, Object>> dataList, String cols, String fileName) {
		CellStyle colStyle = getColStyle(wb);
		CellStyle rowStlye = getRowStyle(wb);
		Sheet sheet = wb.createSheet();
		Row titleRow = sheet.createRow(0);
		
		//테스트 해봐야됨
		List<String> titles = Arrays.asList(cols.split(","));
		int titleSize = titles.size();
		for (int i = 0; i < titleSize; i++) {
			 CellUtil.createCell(titleRow, i, titles.get(i), colStyle);
        };
        
//        그리는 작업 시작 하면 됨
//        dataList.stream().forEach(action);
        
        
        
        //autosize는 마지막에 해야됨
        for (int i = 0; i < titleSize; i++) {
            sheet.autoSizeColumn(i, true);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+512 );
        }
		
		return wb;
	}
	
	public List<Map<String, Object>> read(){
		return null;
	}

	
	private CellStyle getColStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
	      //글자에 대한 스타일 적용
        Font font = wb.createFont();
        font.setBold(true);
//        font.setFontName("굴림체");

        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
		
		return cellStyle;
	}
	
	private CellStyle getRowStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
	      //글자에 대한 스타일 적용
        Font font = wb.createFont();
        font.setBold(true);
//        font.setFontName("굴림체");

        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return cellStyle;
	}
	
	
	
	private void logErrorMsg(String errorMsg){
		log.error("IOEecption : while creating workbook, ExcelHelper error : "+ errorMsg);
	}
	
	
	
}
