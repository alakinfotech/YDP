package com.alakinfotech.ydpcardencoder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Locale;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;





import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
		      ReadingData[] temp = new ReadingData[sheet.getRows()-1];
		      for (int j = 1; j < sheet.getRows(); j++) {
		    	resultStr="";			
		    	ReadingData readingdata = new ReadingData();
		    	  for (int i = 0; i < sheet.getColumns(); i++) {
		          Cell cell = sheet.getCell(i, j);

		          switch (i) {
		          		case 0:
							readingdata.FirstName = cell.getContents();
						break;
		          		case 1:
							readingdata.LastName = cell.getContents();
						break;
		          		case 2:
							readingdata.PhoneNumber = cell.getContents();
						break;
		          		case 3:
							readingdata.PatientId = cell.getContents();
						break;
		          		case 4:
							readingdata.UserName = cell.getContents();
						break;
		          		case 5:
							readingdata.Password = cell.getContents();
						break;
		          		
				default:
					break;
				}
		          
		          }
		    	//FirstName:William:LastName:Jones:Tel:7135551212:PatientID:2349870101:wjones(username):123456(password):
		    	  String tempString ="FirstName:"+readingdata.FirstName+":LastName:"+readingdata.LastName+":Tel:"+readingdata.PhoneNumber+":PatientId:"+readingdata.PatientId+":"+readingdata.UserName+":"+readingdata.Password;
		    	  readingdata.EncodeData = encryptScanData(tempString);
			    	  	// Date format
		    	  		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				   	   //get current date time with Date()
				   	   Date date = new Date();
				   	       
				   	   //get current date time with Calendar()
				   	   Calendar cal = Calendar.getInstance();
		   	   
		    	  readingdata.Date = dateFormat.format(date);
		    	  readingdata.Time = dateFormat.format(cal.getTime());
		    	  temp[j-1]=readingdata;
				 // write(encryptedStringTest);
		    	  }
	
		      YDPCardEncoder cardencode = new YDPCardEncoder();
		      cardencode.progressBar.setValue(50);
		    write(temp);
			 
		    } catch (BiffException e) {
		      e.printStackTrace();
		    }

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
  public void write(ReadingData[] temp) throws IOException {
	  File file = new File(outputFile);
	 
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    
	    workbook.createSheet("Report", 0);
	    WritableSheet excelSheet = workbook.getSheet(0);
	    try {
			createLabel(excelSheet);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
	    	createContent( excelSheet, temp);
	    	
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    workbook.write();
	   
	    try {
			workbook.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	  private void createLabel(WritableSheet sheet)
	      throws WriteException {
	    // Lets create a times font
	    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
	    // Define the cell format
	    times = new WritableCellFormat(times10pt);
	    // Lets automatically wrap the cells
	    times.setWrap(true);

	    // Create create a bold font with unterlines
	    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
	        UnderlineStyle.SINGLE);
	    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
	    // Lets automatically wrap the cells
	    timesBoldUnderline.setWrap(true);

	    CellView cv = new CellView();
	    cv.setFormat(times);
	    cv.setFormat(timesBoldUnderline);
	    //cv.setSize(85);

	    // Write a few headers
	    addCaption(sheet, 0, 0, "SNO");
	    addCaption(sheet, 1, 0,"First Name");
	    addCaption(sheet, 2, 0,"Last Name");
	    addCaption(sheet, 3, 0,"Encoded Data ");
	    addCaption(sheet, 4, 0,"Date And Time");
	    

	  }

	  private void createContent(WritableSheet sheet,ReadingData[] temparray) throws WriteException,
	      RowsExceededException {
	    // Write a few number
	    for (int i = 0; i <temparray.length ; i++) {
	      
	   ReadingData readingdataobj = (ReadingData)temparray[i];
	   System.out.println(readingdataobj.FirstName);
	      
	      addNumber(sheet, 0, i+1,i+1);
	      addLabel(sheet, 1, i+1,readingdataobj.FirstName);
	      addLabel(sheet, 2, i+1,readingdataobj.LastName);
	      addLabel(sheet, 3, i+1,readingdataobj.EncodeData);
	      addLabel(sheet, 4, i+1,readingdataobj.Date);
		     
		     
	    }
	  }

	  private void addCaption(WritableSheet sheet, int column, int row, String s)
	      throws RowsExceededException, WriteException {
	    Label label;
	    label = new Label(column, row, s, timesBoldUnderline);
	    sheet.addCell(label);
	  }

	  private void addNumber(WritableSheet sheet, int column, int row,
	      Integer integer) throws WriteException, RowsExceededException {
	    Number number;
	    number = new Number(column, row, integer, times);
	    sheet.addCell(number);
	  }

	  private void addLabel(WritableSheet sheet, int column, int row, String s)
	      throws WriteException, RowsExceededException {
	    Label label;
	    label = new Label(column, row, s, times);
	    sheet.addCell(label);
	  }

 

} 
