package com.alakinfotech.ydpcardencoder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class LogFile {
	
	 private static LogFile myObj;
	 

	 
	 /**
	     * Create private constructor
	     */
	    private LogFile(){
	         
	    }
	    /**
	     * Create a static method to get instance.
	     */
	    public static LogFile getInstance(){
	        if(myObj == null){
	            myObj = new LogFile();

	        }
	        
	        return myObj;
	    }

	public void createLogFile(String data) throws IOException{

             
           // the following statement is used to log any messages  


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
