/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDPCardEncoder is used to encode user data into encode format
 */
package com.alakinfotech.ydpcardencoder;

/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 03 May 2013
 *
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Logfile class is used to create 
 * a new log file, if it not present
 * always appends output log data to the file.
 */

public class LogFile {
	
	 private static LogFile myObj;
	  
	 /* private constructor*/
	 private LogFile(){
	         
	    }
	 
	 
	 
	 
	 
	//-----------------------------------------------------------------------
	// Function:          getInstance()
	//
	// Parameter:
	//			In:        none		               
	//			Out:       none
	//			In/Out:    none
	//
	// Returns:        	   none
	//
	// Desc:        	   Create a static method to get instance.
	//-----------------------------------------------------------------------

	    public static LogFile getInstance(){
	        if(myObj == null){
	            myObj = new LogFile();

	        }
	        
	        return myObj;
	    }
		//-----------------------------------------------------------------------
		// Function:          createLogFile(String data)
		//
		// Parameter:
		//			In:        data - log data		               
		//			Out:       none
		//			In/Out:    none
		//
		// Returns:        	   none
		//
		// Desc:        	   Used to create new file if not present and log data is passed as argument
		//-----------------------------------------------------------------------	   
		public void createLogFile(String data) throws IOException{
	
	      File file =new File("YDPCardDataEncoder_Log.txt");
	
			//if file doesnt exists, then create it
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
			//true = append file
			FileWriter fileWritter = null;
			try {
				fileWritter = new FileWriter(file.getName(),true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		    try {
		    bufferWritter.write(data);
		    bufferWritter.newLine();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		    bufferWritter.close();
	
	  }

}
