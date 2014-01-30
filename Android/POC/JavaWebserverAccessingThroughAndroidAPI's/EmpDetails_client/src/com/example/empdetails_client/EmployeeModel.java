package com.example.empdetails_client;

import org.json.simple.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class EmployeeModel {
	 public static final String NAMESPACE = "http://controller.emp.com/";
	    public static final String URL = "http://10.0.2.2:1234/EmpDetails_Server/services/EmpController?wsdl";
	    								  		
	    public static final String SOAP_ACTION = "http://controller.emp.com/getEmployeeDetails";
	    			
	    public static final String METHOD_NAME = "getEmployeeDetails";
	public static String getDetails(int num,String name){
		SoapPrimitive  resultsRequestSOAP=null;
		
		
		try {
	        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        
	        request.addProperty("no",num); //"222"
	        request.addProperty("name",name); //"venkat"
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
	        envelope.setOutputSoapObject(request);
	       HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	      androidHttpTransport.call(SOAP_ACTION, envelope);
	      resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
	        
	          Log.d("hai",resultsRequestSOAP.toString())   ;  
	      
	     
	            
	       
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	Log.d("hai",resultsRequestSOAP.toString());
	        	
	        }
		  
		return resultsRequestSOAP.toString();     	  

		
	}

}
