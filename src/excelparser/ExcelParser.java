/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelparser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import logger.LoggerFile;
import mysql.Connector;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author Damang
 */
public class ExcelParser {
    static ArrayList<HashMap<String,String>> arRowData;
    static LoggerFile loggerFile;
    
    public static void readXLSFile(String p_path) throws IOException
	{
            
                ArrayList<String> arColKey=new ArrayList<>();
                int iRow=0;
		InputStream ExcelFileToRead = new FileInputStream(p_path);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();
                loggerFile.writeLog("Reading: "+p_path);
		while (rows.hasNext())
		{
                    HashMap<String,String> hmColumn = new HashMap<>();
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
                        for (int i = 0; i < row.getLastCellNum(); i++) {
                            cell = row.getCell(i,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
                            {
//					System.out.print(cell.getStringCellValue()+" ");
                                if(iRow==0){
                                    arColKey.add(cell.getStringCellValue());
                                        }
                                else{
                                    hmColumn.put(arColKey.get(i),cell.getStringCellValue());
                                }
                            }
                            else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                            {
//					System.out.print(cell.getNumericCellValue()+" ");
                                if(iRow>0){
                                    hmColumn.put(arColKey.get(i),cell.getNumericCellValue()+"");
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        String  sDateFormat =  sdf.format(cell.getDateCellValue());
                                        hmColumn.put(arColKey.get(i),sDateFormat);
                                    }
                                }
                            }
                        }
//			System.out.println();
                        if(iRow>0){
                            arRowData.add(hmColumn);
                        }
                        iRow++;
		}
                loggerFile.writeLog("Finished Reading: "+iRow+" row");
	
	}
	
	public static void main(String[] args)  {
            arRowData = new ArrayList<>();
            String sPath,sGroupId;
            sPath = args[0];
            sGroupId = args[1];
            loggerFile = new LoggerFile("/var/www/html/ssportal/application/java/deviceGroupLog/"+sGroupId, sGroupId);
//            sPath = "/Users/damangrea/Downloads/bsm2018.xls";
//            sGroupId = "42";
            
            
        try {
            readXLSFile(sPath);
            
            System.out.println(arRowData.toString());
            //execute to database
            Connector c = new Connector();
            c.InputDeviceToDatabase(0, sGroupId, arRowData,loggerFile);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		

	}
    
}
