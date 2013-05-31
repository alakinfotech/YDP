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

/* Creating interface for updating progress value and for showing alerts*/
interface IYDPCardEncoder{
	void progressupdate(int progress);
	void exceptionAlert(String exception);
	void messageAlert(String message);
}
/*Creating  a class which is used to store and to retrive excel sheet data*/
public class EReadingData {
	
	String firstName;
	String lastName;
	String phoneNumber;
	String patientId;
	String userName;
	String password;
	String encodeData;
	String  date;
	

}
