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



//-----------------------------------------------------------------------
// Interface:       IYDPCardEncoder
//
// Parameter:
//		In:        none		               
//		Out:       none
//		In/Out:    none
//
// Returns:        none
//
// Desc:          Creating interface for updating progress value and for showing alerts
//-----------------------------------------------------------------------

interface IYDPCardEncoder{
	void progressupdate(int progress);
	void exceptionAlert(String exception);
	void messageAlert(String message);
}
/**
 * Creating  a class which is used to store 
 * and to retrive excel sheet data
 **/
public class EReadingData {
	
	String firstName;//reference to firstname
	String lastName;//reference to lastname
	String phoneNumber;//reference to phone number
	String patientId;//reference to patientid
	String userName;//reference to username
	String password;//reference to password
	String encodeData;//reference to encodedate
	String date;//reference to date
	String pcpname;//reference to pcpname
	String pcpaddress1;//reference to pcpaddress1
	String pcpaddress2;//reference to pcpaddress2
	String pcpphonenumber;//reference to pcpphonenumber
	String pcpemail;//reference to pcpemail
	

}
