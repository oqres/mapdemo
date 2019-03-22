package com.example.demo.util;

import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	
	//공백 cell 인지 검증함
    public static String getCellToStr(Cell cell){
        if(cell==null || cell.toString().isEmpty()) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    // row의 첫번째 값이 공백인 곳을 찾아서 row last number 지정된 값으로써 리턴 해준다,
    // ###주의 사항 ### 첫번째 컬럼은 무조건 값이 있다는 전제로 되어 있음 주의
    public static int getPhysicalLastRowNumbers(Sheet sheet){
        int rowSize = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rowSize; i++) {
            //첫번째 row이  null이거나 cell의 첫번째가 공백이면 stop 한다
            if(sheet.getRow(i)==null||sheet.getRow(i).getCell(0)==null||sheet.getRow(i).getCell(0).toString().isEmpty()){
                return i;
            }
        }
        return rowSize;
    }
    
    
	
	

}
