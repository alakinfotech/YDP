/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDPCardEncoder is used to encode user data into encode format
 */


package com.alakinfotech.ydpcardencoder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.codec.binary.Base64;


import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
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

/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 03 May 2013
 *
 */

public class ExcelFileReading {
/*Reading class is used to read input data and convert into encode format  */
  private String inputFile;// sets input file
  private String outputFile;//sets output file
  private String key;//sets secret key
  private WritableCellFormat timesBoldUnderline;//Font for header in workbook
  private WritableCellFormat times;//font for content in workbook
  byte[] encryptedBytesTest;//for storing bytes data
  String encryptedStringTest;//for storing resultant data
  IYDPCardEncoder callback;
  int progressVal;
  int count;
  boolean isStopEncoding = false;
/* sets input file*/
  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }
  /* sets output file*/
  public void setOutputFile(String outputFile) {
	    this.outputFile = outputFile;
	  }
  /*sets secret key to encode function*/
  public void setKeyForEncoding(String key){
	  this.key = key;
	 
  }
  /* Reading  input file data */
  public void read() throws IOException, UnsupportedAudioFileException  {
		    
	  		File inputWorkbook = new File(inputFile);
		    Workbook w;
		    Cell cell;
		    String cellContent;
		    String fileName = inputWorkbook.getName();
    	  	// Date format
    	  	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	  	//get current date time with Date()
		   	 Date dateobj = new Date();
		   	 LogFile.getInstance().createLogFile("*********"+fileName+"*********");
		   	 
		   	 
		   	 
		    
		    try {
		      w = Workbook.getWorkbook(inputWorkbook);
		      // Get the first sheet
		      Sheet sheet = w.getSheet(0);
		     

		      // Creating array to store elements of excel sheet
		      ArrayList<EReadingData> temp = new ArrayList<EReadingData>();
		      //Loop to access row data
		      int rows = sheet.getRows();
		      for (int j = 0; j < rows; j++) {
			    	// creating object for Reading data class			
			    	EReadingData readingdata = new EReadingData();
			    	//Loop to access coloum data
			    	boolean isValidData = true;

			     for (int i = 0; i < sheet.getColumns(); i++) {
			    	 if(isStopEncoding == true){
			    		 return;
			    	 }
				          cell = sheet.getCell(i, j);
				           cellContent = cell.getContents();
//				         if(j==0){  
//				           if(!((sheet.getCell(0, 0).getContents().equalsIgnoreCase("FirstName"))&&(sheet.getCell(0, 1).getContents().equalsIgnoreCase("LastName"))&&(sheet.getCell(0,2).getContents().equalsIgnoreCase("PhoneNumber"))&&(sheet.getCell(0,3).getContents().equalsIgnoreCase("PatientId"))&&(sheet.getCell(0,4).getContents().equalsIgnoreCase("UserName"))&&(sheet.getCell(0,5).getContents().equalsIgnoreCase("Password")))){
//					    		callback.exceptionAlert("file format incorrect");
//					    		break;
//					    	}
//				         }
				       if(cellContent.isEmpty()){
				    	   count = count+1;
				    	   
				    	   LogFile.getInstance().createLogFile("WARNING:"+dateobj+" "+fileName+" "+"Record no :"+j+" "+"is not encoded,because record data format is incorrect"+"\n");  
				    	  
				        	  isValidData = false ;
				        	  break;
				          	}
			          /* For reading workbook data and storing in reading object*/
			          switch (i) {
			          		case 0:
			          			// For reading first cell data of sheet
								readingdata.firstName = cellContent;
							break;
			          		case 1:
			          			// For reading second cell data of sheet
								readingdata.lastName = cellContent;
							break;
			          		case 2:
			          			// For reading Third cell data of sheet
								readingdata.phoneNumber = cellContent;
							break;
			          		case 3:
			          			// For reading fourth cell data of sheet
								readingdata.patientId = cellContent;
							break;
			          		case 4:
			          			// For reading fifth cell data of sheet
								readingdata.userName = cellContent;
							break;
			          		case 5:
			          			// For reading sixth cell data of sheet
								readingdata.password = cellContent;
							break;
			          		default:
			          		break;
					    }
		          
		         	}
		    	  
//			    if(j==0){
//			    	
//			    	if(!((readingdata.firstName=="FirstName")&&(readingdata.lastName=="LastName")&&(readingdata.phoneNumber=="PhoneNumber")&&(readingdata.patientId=="PatientId")&&(readingdata.userName=="UserName")&&(readingdata.password=="Password"))){
//			    		callback.exceptionAlert("file format incorrect");
//			    		break;
//			    	}
////			    	else{
////			    		callback.exceptionAlert("file format incorrect");
////			    		break;
////			    	}
//			   
//			    }
			   	 
//			    else{
			     progressVal = ((100*j)/(rows-1)) ;
				   	if(progressVal > 98){
				   		callback.progressupdate(98);
				   	 }
				   	else{
				   		callback.progressupdate(progressVal);
				   	}
			    	  if(isValidData == false ){
			    		  continue;
			    	  	}
			    	  	//FirstName:William:LastName:Jones:Tel:7135551212:PatientID:2349870101:wjones(username):123456(password):
			    	  	String tempString ="FirstName:"+readingdata.firstName+":LastName:"+readingdata.lastName+":Tel:"+readingdata.phoneNumber+":PatientId:"+readingdata.patientId+":"+readingdata.userName+":"+readingdata.password+":";
			    	 
			    	  	readingdata.encodeData = encryptScanData(tempString);
			    	  	 String result = decryptScanData(readingdata.encodeData);
			    	  	 System.out.println("Decryptdata :"+result);

					   	 readingdata.date = dateFormat.format(dateobj);
					   	 temp.add(readingdata);//passing object data to array list
						 
					   	
			    }
			
//		    }
	
		  	//Creates new workbook file
		    CreateWorkBook(temp);
		    
		   progressVal = 100;
		   callback.progressupdate(progressVal);
		   if(count != 0){
			   int encodedrecords = ((sheet.getRows()-1) - count );
			   //Message to user that encoding process is completed
				  callback.messageAlert(encodedrecords+"/"+(sheet.getRows()-1) +" records are encoded sucessfully"+"\n"+"See YDPCardDataEncoder_Log.txt file for more details ");
		   }
		   else{
			   LogFile.getInstance().createLogFile("INFO:"+dateobj+" "+fileName+" "+"All records data encoded successfully");
			   callback.messageAlert("All records are encoded successfully"+"\n"+"See YDPCardDataEncoder_Log.txt file for more details ");
		   }
		 
			 
		    } catch (BiffException e) {
		      e.printStackTrace();
		    //for displaying alert to user
		      callback.exceptionAlert("Unable to read input file,bad format");
		     
		    }
		    
		    

  }
  /* Encrypting  input file data*/
  private String encryptScanData(String data){
	  			// calling cipher class to encrypt data
				Cipher cipher = new Cipher(key);
				String inputStr = data.toString();
				byte[] input =null;
				try {
						input = inputStr.getBytes("UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//for displaying exceptionAlert to user
						callback.exceptionAlert("UnsupportedEncodingException");
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
								//for displaying alert to user
								callback.exceptionAlert("UnsupportedEncoding Exception");
								
							}
						
						
						encryptedStringTest = resultData; 
						System.out.println("Encoded data : "+ " \n " + new String(resultData));
					}
				if(resultData != null){
					return new String(resultData);
				}
				else {
					return null;
				}
				
		}
	
	private String decryptScanData(String data){
	   	Cipher cipher = new Cipher(key);
	   	byte[] encryptinput=null;
	   	try {
		//scanText.trim();
		byte[] dect=data.getBytes("UTF-8");
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
		
						
  
  
  /* Creats new file with encoded data */
 public void CreateWorkBook(ArrayList<EReadingData> temp) throws IOException {
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
				//for displaying alert to user
				callback.exceptionAlert("Write exception,Unable to create workbook");
			}
		    try {
		    	createContent( excelSheet, temp);
		    	
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//for displaying alert to user
				callback.exceptionAlert("RowsExceededException araised");
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//for displaying alert to user
				callback.exceptionAlert("Write exception,Unable to create workbook");
			}

	    workbook.write();
	   
	    try {
			workbook.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//for displaying alert to user
			callback.exceptionAlert("Write exception,Unable to create workbook");
		}
	  }
 /* Creates label in output file */
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
		/* For placing content in output file*/
	private void createContent(WritableSheet sheet,ArrayList<EReadingData> temp) throws WriteException,
		      RowsExceededException {
		    // Write a few number
		    for (int i = 0; i <temp.size() ; i++) {
		      
		   EReadingData readingdataobj = (EReadingData)temp.get(i);
		   System.out.println(readingdataobj.firstName);
		      
		      addNumber(sheet, 0, i+1,i+1);
		      addLabel(sheet, 1, i+1,readingdataobj.firstName);
		      addLabel(sheet, 2, i+1,readingdataobj.lastName);
		      addLabel(sheet, 3, i+1,readingdataobj.encodeData);
		      addLabel(sheet, 4, i+1,readingdataobj.date);
			     
			     
		    }
		  }
		/*For setting caption format and add caption in output file */
	  private void addCaption(WritableSheet sheet, int column, int row, String s)
	      throws RowsExceededException, WriteException {
	    Label label;
	    label = new Label(column, row, s, timesBoldUnderline);
	    sheet.addCell(label);
	  }
	  /*For setting nuber format and add number to the  output file */
	  private void addNumber(WritableSheet sheet, int column, int row,
	      Integer integer) throws WriteException, RowsExceededException {
	    Number number;
	    number = new Number(column, row, integer, times);
	    sheet.addCell(number);
	  }
	  /*For setting content format of content to  output file */
	  private void addLabel(WritableSheet sheet, int column, int row, String s)
	      throws WriteException, RowsExceededException {
	    Label label;
	    label = new Label(column, row, s, times);
	    sheet.addCell(label);
	  }

	
} 
