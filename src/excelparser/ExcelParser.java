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

//    public static void readXLSFile(String p_path) throws IOException
//	{
//                ArrayList<String> arColKey=new ArrayList<>();
//                int iRow=0;
//		InputStream ExcelFileToRead = new FileInputStream(p_path);
////		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
//		XSSFWorkbook wb = new XSSFWorkbook(p_path);
//
//		XSSFSheet sheet=wb.getSheetAt(0);
//		XSSFRow row; 
//		XSSFCell cell;
//
//		Iterator rows = sheet.rowIterator();
//
//		while (rows.hasNext())
//		{  
//                    HashMap<String,String> hmColumn = new HashMap<>();
//                    row=(XSSFRow) rows.next();
//                    Iterator cells = row.cellIterator();
//                    
//                    while (cells.hasNext())
//                    {
//                            cell=(XSSFCell) cells.next();
//                            
//                            if (cell.getCellType()== XSSFCell.CELL_TYPE_STRING)
//                            {
//                                    System.out.print(cell.getStringCellValue()+" ");
//                                    if(iRow==0){
//                                        arColKey.add(cell.getStringCellValue());
//                                            }
//                                    else{
//                                        hmColumn.put(arColKey.get(iRow),cell.getStringCellValue());
//                                    }
//                            }
//                            else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
//                            {
//                                    System.out.print(cell.getNumericCellValue()+" ");
//                                    if(iRow>0){
//                                        hmColumn.put(arColKey.get(iRow),cell.getNumericCellValue()+"");
//                                    }
//                            }
//                            else
//                            {
//                                    //U Can Handel Boolean, Formula, Errors
//                            }
//                            if(iRow>0){
//                                arRowData.add(hmColumn);
//                            }
//                    }
//                    System.out.println();
//                    iRow++;
//		}
//	
//	}
    
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
                System.out.println("Reading: "+p_path);
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
                System.out.println("Finished Reading: "+iRow+" row");
	
	}
	
//	public static void writeXLSFile() throws IOException {
//		
//		String excelFileName = "D:/Test.xls";//name of excel file
//
//		String sheetName = "Sheet1";//name of sheet
//
//		HSSFWorkbook wb = new HSSFWorkbook();
//		HSSFSheet sheet = wb.createSheet(sheetName) ;
//
//		//iterating r number of rows
//		for (int r=0;r < 5; r++ )
//		{
//			HSSFRow row = sheet.createRow(r);
//	
//			//iterating c number of columns
//			for (int c=0;c < 5; c++ )
//			{
//				HSSFCell cell = row.createCell(c);
//				
//				cell.setCellValue("Cell "+r+" "+c);
//			}
//		}
//		
//		FileOutputStream fileOut = new FileOutputStream(excelFileName);
//		
//		//write this workbook to an Outputstream.
//		wb.write(fileOut);
//		fileOut.flush();
//		fileOut.close();
//	}

	public static void main(String[] args)  {
            arRowData = new ArrayList<>();
            String sPath,sGroupId;
            sPath = args[0];
            sGroupId = args[1];
            
        try {
            // read and parse file
//            sPath = "C:/Users/Damang/Downloads/20180213_device_list.xls";
//            sPath = "D:/Test.xls";
//            sPath = "C:/Users/Damang/Desktop/test export ldap.xls";
//            readXLSFile(sPath);
            readXLSFile(sPath);
            
            System.out.println(arRowData.toString());
            //execute to database
            Connector c = new Connector();
            c.InputDeviceToDatabase(0, sGroupId, arRowData);
            
        } catch (IOException ex) {
            Logger.getLogger(ExcelParser.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
		

	}
    
}
