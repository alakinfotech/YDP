package com.example.empdetails_client;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class EmployeeActivity extends Activity {
	

   
	
    TextView result=null;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	String str=null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        result=(TextView)findViewById(R.id.tv);
        try{
       str= EmployeeModel.getDetails(222,"venkat");
        }catch(Exception e){
        	 result.setText(e.toString());  
        	e.printStackTrace();
        	 
        }
        result.setText(str);         
        }
        
        
    


    
    
}
