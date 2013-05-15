package com.alakinfotech.ydpcardencoder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Locale;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.codec.binary.Base64;





import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Reading {

  private String inputFile;
  private String outputFile;
  private WritableCellFormat timesBoldUnderline;
  private WritableCellFormat times;
  String resultStr = "" ; 
  byte[] encryptedBytesTest;
  String encryptedStringTest;
  String[] resultarray = new String[10];

  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }
  public void setOutputFile(String outputFile) {
	    this.outputFile = outputFile;
	  }

  public void read() throws IOException, UnsupportedAudioFileException  {
    File inputWorkbook = new File(inputFile);
    Workbook w;
    try {
      w = Workbook.getWorkbook(inputWorkbook);
      // Get the first sheet
      Sheet sheet = w.getSheet(0);
      // Loop over first 10 column and lines
      
      for (int j = 1; j < sheet.getRows(); j++) {
    	resultStr="";
    	  for (int i = 0; i < sheet.getColumns(); i++) {
          Cell cell = sheet.getCell(i, j);
          Cell headercell=sheet.getCell(i, 0);
          CellType type = cell.getType();
          if(cell.getContents()!=null&&cell.getContents().length()>0){
        	 
                  if(headercell.getContents().compareToIgnoreCase("UserName")!=0&&headercell.getContents().compareToIgnoreCase("Password")!=0)
                  resultStr =resultStr+headercell.getContents()+":";
                 
                  resultStr =resultStr+cell.getContents()+":";  
          }
          }
    	  resultStr = resultStr + "\n";
    	  System.out.println("Input  data "+"\n" + resultStr );
    	  encryptScanData(resultStr);
    	  }

	  
	 // encryptScanData(resultStr);
	  decryptScanData(encryptedStringTest);

    } catch (BiffException e) {
      e.printStackTrace();
    }

 }
  
  private String readingdata(String data){
	  
	  
	return data;
	  
  }
  private String encryptScanData(String data){

		Cipher cipher = new Cipher("Adi&Revanth");
		String inputStr = data.toString();
		byte[] input =null;
		
			try {
				input = inputStr.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		byte[] reseltByts = null;
			  
			try {
				encryptedBytesTest =  reseltByts = cipher.encrypt(input);
			 	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			             
			}
		String resultData = null;
			
			
			if(reseltByts != null){ 
				
				byte [] reseltBase64Bytes = Base64.encodeBase64(reseltByts);
					try {
						resultData = new String(reseltBase64Bytes, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				encryptedStringTest = resultData; 
				System.out.println("Encoded data : "+ " \n " + new String(resultData));
			}
				if(resultData != null)
				{

				return new String(resultData);
				}
				else 
					{ return null;
					}
				
		}
	
		
		
						
  
  
  private String decryptScanData(String resultStr){
	   	Cipher cipher = new Cipher("Adi&Revanth");
	   	byte[] encryptinput=null;
	   	try {
		//scanText.trim();
		byte[] dect=resultStr.getBytes("UTF-8");
		encryptinput = Base64.decodeBase64(dect);
	   	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	   	}

		byte[] reseltByts = null;

		try {
		reseltByts = cipher.decrypt(encryptinput);
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		String resultData = null;


		if(reseltByts != null){



			try {
		resultData = new String(reseltByts, "UTF-8");
		System.out.println("Decoded data : "+ " \n " + new String(resultData));
			} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		}
		if(resultData != null)
		{

		return new String(resultData);
		}
		else 
			{ return null;
			}
	}
//  public void write() throws IOException {
//	  File file = new File(outputFile);
//	    WorkbookSettings wbSettings = new WorkbookSettings();
//
//	    wbSettings.setLocale(new Locale("en", "EN"));
//
//	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
//	    workbook.createSheet("Report", 0);
//	    WritableSheet excelSheet = workbook.getSheet(0);
//	    try {
//			createLabel(excelSheet);
//		} catch (WriteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    try {
//			createContent(excelSheet);
//		} catch (RowsExceededException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (WriteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	    workbook.write();
//	    try {
//			workbook.close();
//		} catch (WriteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	  
//  }
//  private void createLabel(WritableSheet sheet)
//	      throws WriteException {
//	    // Lets create a times font
//	    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
//	    // Define the cell format
//	    times = new WritableCellFormat(times10pt);
//	    // Lets automatically wrap the cells
//	    times.setWrap(true);
//
//	    // Create create a bold font with unterlines
//	    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
//	        UnderlineStyle.SINGLE);
//	    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
//	    // Lets automatically wrap the cells
//	    timesBoldUnderline.setWrap(true);
//
//	    CellView cv = new CellView();
//	    cv.setFormat(times);
//	    cv.setFormat(timesBoldUnderline);
//	    cv.setAutosize(true);
//
//	    // Write a few headers
//	    addCaption(sheet, 0, 0, "Header 1");
//	    addCaption(sheet, 1, 0, "This is another header");
//	    
//
//	  }
//
//	  private void createContent(WritableSheet sheet) throws WriteException,
//	      RowsExceededException {
//	    // Write a few number
////	    for (int i = 1; i < 10; i++) {
////	      // First column
////	      addNumber(sheet, 0, i, i + 10);
////	   
////	      // Second column
////	      addNumber(sheet, 1, i, i * i);
////	    }
//	    // Lets calculate the sum of it
////	    StringBuffer buf = new StringBuffer();
////	    buf.append("SUM(A2:A10)");
////	    Formula f = new Formula(0, 10, buf.toString());
////	    sheet.addCell(f);
////	    buf = new StringBuffer();
////	    buf.append("SUM(B2:B10)");
////	    f = new Formula(1, 10, buf.toString());
////	    sheet.addCell(f);
//
//	    // Now a bit of text
//	    for (int i = 12; i < 20; i++) {
//	      // First column
//	      addLabel(sheet, 0, i, "Boring text " + i);
//	      // Second column
//	      addLabel(sheet, 1, i, "Another text");
//	    }
//	  }
//
//	  private void addCaption(WritableSheet sheet, int column, int row, String s)
//	      throws RowsExceededException, WriteException {
//	    Label label;
//	    label = new Label(column, row, s, timesBoldUnderline);
//	    sheet.addCell(label);
//	  }
//	  private void addLabel(WritableSheet sheet, int column, int row, String s)
//		      throws WriteException, RowsExceededException {
//		    Label label;
//		    label = new Label(column, row, s, times);
//		    sheet.addCell(label);
//		  }
//	  private void addNumber(WritableSheet sheet, int column, int row,
//		      Integer integer) throws WriteException, RowsExceededException {
//		    Number number;
//		    number = new Number(column, row, integer, times);
//		    sheet.addCell(number);
//		  }

  public static void main(String[] args) throws IOException {
//    Reading test = new Reading();
//    test.setInputFile("D:/AIT_PROJECTS/YDP_MOBILE/Desktop Applications/YDPCardDataEncoder/Sample_Input.xls");
//    try {
//		test.read();
//	} catch (UnsupportedAudioFileException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//    test.setOutputFile("D:/Sample1.xls");
//    test.write();
//    System.out.println("Please check the result file under 'D' folder ");
  }

} 
